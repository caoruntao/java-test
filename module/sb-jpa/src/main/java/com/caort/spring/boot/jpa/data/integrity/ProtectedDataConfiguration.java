package com.caort.spring.boot.jpa.data.integrity;

import com.caort.spring.boot.jpa.exception.DatabaseProtectionException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author caort
 * @date 2022/9/2 09:43
 */
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "trustcenter.data.protected")
public class ProtectedDataConfiguration {
    private static final boolean DEAFULT_ENABLE_PROTECTION = false;
    private static final boolean DEAFULT_ENABLE_VERIFICATION = false;
    private static final boolean DEAFULT_ERROR_ON_VERIFY_FAIL = true;

    public static ProtectedDataConfiguration INSTANCE;

    private Map<String, TableConfiguration> tableConfigurationMap;
    private Map<String, KeyConfiguration> keyConfigurationMap;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @PostConstruct
    public void init() throws UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, NoSuchProviderException {
        INSTANCE = this;
        // 加载CryptoToken
        for (Map.Entry<String, KeyConfiguration> entry : keyConfigurationMap.entrySet()) {
            KeyConfiguration value = entry.getValue();
            value.init();
        }
    }

    public static boolean useDatabaseIntegrityProtection(String tableName) {
        if (INSTANCE.tableConfigurationMap.containsKey(tableName)) {
            TableConfiguration tableConfiguration = INSTANCE.tableConfigurationMap.get(tableName);
            return tableConfiguration.getEnableSign();
        }
        return DEAFULT_ENABLE_PROTECTION;
    }

    public static boolean useDatabaseIntegrityVerification(String tableName) {
        if (INSTANCE.tableConfigurationMap.containsKey(tableName)) {
            TableConfiguration tableConfiguration = INSTANCE.tableConfigurationMap.get(tableName);
            return tableConfiguration.getEnableVerify();
        }
        return DEAFULT_ENABLE_VERIFICATION;
    }

    public static boolean errorOnVerifyFail(String tableName) {
        if (INSTANCE.tableConfigurationMap.containsKey(tableName)) {
            TableConfiguration tableConfiguration = INSTANCE.tableConfigurationMap.get(tableName);
            return tableConfiguration.getErrorOnVerifyFail();
        }
        return DEAFULT_ERROR_ON_VERIFY_FAIL;
    }

    public static String getKeyId(String tableName) {
        if (INSTANCE.tableConfigurationMap.containsKey(tableName)) {
            TableConfiguration tableConfiguration = INSTANCE.tableConfigurationMap.get(tableName);
            return tableConfiguration.getKeyId();
        }
        throw new DatabaseProtectionException("根据tableName:" + tableName + "找不到KeyId");
    }

    public static Integer getProtectVersion(String keyId) {
        if (INSTANCE.keyConfigurationMap.containsKey(keyId)) {
            KeyConfiguration tableConfiguration = INSTANCE.keyConfigurationMap.get(keyId);
            return tableConfiguration.getProtectVersion();
        }
        throw new DatabaseProtectionException("根据keyId:" + keyId + "找不到ProtectVersion");
    }

    public static String getSigAlg(String keyId) {
        if (INSTANCE.keyConfigurationMap.containsKey(keyId)) {
            KeyConfiguration tableConfiguration = INSTANCE.keyConfigurationMap.get(keyId);
            return tableConfiguration.getSigAlg();
        }
        throw new DatabaseProtectionException("根据keyId:" + keyId + "找不到SigAlg");
    }

    public static CryptoToken getCryptoToken(String keyId) {
        if (INSTANCE.keyConfigurationMap.containsKey(keyId)) {
            KeyConfiguration tableConfiguration = INSTANCE.keyConfigurationMap.get(keyId);
            return tableConfiguration.getCryptoToken();
        }
        throw new DatabaseProtectionException("根据keyId:" + keyId + "找不到CryptoToken");
    }

    public Map<String, KeyConfiguration> getKeyConfigurationMap() {
        return keyConfigurationMap;
    }

    public void setKeyConfigurationMap(Map<String, KeyConfiguration> keyConfigurationMap) {
        this.keyConfigurationMap = keyConfigurationMap;
    }

    public Map<String, TableConfiguration> getTableConfigurationMap() {
        return tableConfigurationMap;
    }

    public void setTableConfigurationMap(Map<String, TableConfiguration> tableConfigurationMap) {
        this.tableConfigurationMap = tableConfigurationMap;
    }

    public static class KeyConfiguration {
        private Integer protectVersion;
        private String path;
        // KeyStore和访问密钥密码(两个要设为一样的)
        private String password;
        private CryptoToken cryptoToken;
        private String sigAlg;

        public void init() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, NoSuchProviderException {
            Assert.hasText(path, "Key文件路径不能为空");

            KeyStore keyStore = KeyStore.getInstance("PKCS12", BouncyCastleProvider.PROVIDER_NAME);
            char[] pwdChars = StringUtils.hasText(password) ? password.toCharArray() : null;

            URL keyStoreUrl = ResourceUtils.getURL(path);
            try (InputStream inputStream = keyStoreUrl.openStream()) {
                keyStore.load(inputStream, pwdChars);
            }

            switch (ProtectVersionEnum.convert(protectVersion)) {
                case ASYMMETRICAL:
                    Enumeration<String> aliases = keyStore.aliases();
                    while (aliases.hasMoreElements()) {
                        String alias = aliases.nextElement();
                        final Key key = keyStore.getKey(alias, pwdChars);

                        final Certificate cert = keyStore.getCertificate(alias);
                        final PublicKey publicKey = cert.getPublicKey();

                        AsymmetricalCrytoToken crytoToken = new AsymmetricalCrytoToken();
                        crytoToken.setKeyPair(new KeyPair(publicKey, (PrivateKey) key));
                        crytoToken.setSignProviderName(BouncyCastleProvider.PROVIDER_NAME);
                        this.cryptoToken = crytoToken;
                    }
                    break;
                case UNKNOWN:
                default:
                    break;
            }
        }

        public Integer getProtectVersion() {
            return protectVersion;
        }

        public void setProtectVersion(Integer protectVersion) {
            this.protectVersion = protectVersion;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public CryptoToken getCryptoToken() {
            return cryptoToken;
        }

        public void setCryptoToken(CryptoToken cryptoToken) {
            this.cryptoToken = cryptoToken;
        }

        public String getSigAlg() {
            return sigAlg;
        }

        public void setSigAlg(String sigAlg) {
            this.sigAlg = sigAlg;
        }
    }

    public static class TableConfiguration {
        private Boolean enableSign = DEAFULT_ENABLE_PROTECTION;
        private Boolean enableVerify = DEAFULT_ENABLE_VERIFICATION;
        private Boolean errorOnVerifyFail = DEAFULT_ERROR_ON_VERIFY_FAIL;
        private String keyId;

        public Boolean getEnableSign() {
            return enableSign;
        }

        public void setEnableSign(Boolean enableSign) {
            this.enableSign = enableSign;
        }

        public Boolean getEnableVerify() {
            return enableVerify;
        }

        public void setEnableVerify(Boolean enableVerify) {
            this.enableVerify = enableVerify;
        }

        public Boolean getErrorOnVerifyFail() {
            return errorOnVerifyFail;
        }

        public void setErrorOnVerifyFail(Boolean errorOnVerifyFail) {
            this.errorOnVerifyFail = errorOnVerifyFail;
        }

        public String getKeyId() {
            return keyId;
        }

        public void setKeyId(String keyId) {
            this.keyId = keyId;
        }
    }
}
