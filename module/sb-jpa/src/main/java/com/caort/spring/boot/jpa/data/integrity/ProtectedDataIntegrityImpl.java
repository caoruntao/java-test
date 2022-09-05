package com.caort.spring.boot.jpa.data.integrity;

import com.caort.spring.boot.jpa.exception.DatabaseProtectionException;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

/**
 * @author caort
 * @date 2022/9/2 09:33
 */
public class ProtectedDataIntegrityImpl implements ProtectedDataImpl {
    private static final Logger log = LoggerFactory.getLogger(ProtectedDataIntegrityImpl.class);

    private String table;

    public void setTableName(String table) {
        this.table = table;
    }

    public void protectData(ProtectedData obj) throws DatabaseProtectionException {
        if (ProtectedDataConfiguration.useDatabaseIntegrityProtection(this.table)) {
            String keyId = ProtectedDataConfiguration.getKeyId(this.table);
            int protectVersion = ProtectedDataConfiguration.getProtectVersion(keyId);
            String originStr = obj.getProtectString(protectVersion);
            String protection = calculateProtection(protectVersion, keyId, originStr);
            String rowProtection = protectVersion + ":" + keyId + ":" + protection;

            if (log.isTraceEnabled()) {
                log.trace("Protected string [{}] [{}]", this.table, originStr);
                log.trace("Protecting row string with protection [{}]", rowProtection);
            }

            obj.setRowProtection(rowProtection);
        }
    }

    public void verifyData(ProtectedData obj) throws DatabaseProtectionException {
        if (ProtectedDataConfiguration.useDatabaseIntegrityVerification(this.table)) {
            String rowProtection = obj.getRowProtection();
            if (StringUtils.hasText(rowProtection)) {
                String[] datas = rowProtection.split(":");
                if (datas.length != 3) {
                    throw new DatabaseProtectionException("Illegal signature data: " + rowProtection);
                }
                int protectVersion = Integer.parseInt(datas[0]);
                String keyid = datas[1];
                String realProt = datas[2];

                String originStr = obj.getProtectString(protectVersion);
                if (log.isTraceEnabled()) {
                    log.trace("Verifying row string [{}] [{}]", this.table, originStr);
                    log.trace("RowProtection [{}] ProtectVersion [{}] KeyId [{}] ", rowProtection, protectVersion, keyid);
                }
                this.verifyProtection(rowProtection, originStr, protectVersion, keyid, realProt, obj);
                return;
            }
            throw new DatabaseProtectionException("Illegal signature data: " + rowProtection);
        }
    }

    public String calculateProtection(ProtectedData obj) throws DatabaseProtectionException {
        String keyid = ProtectedDataConfiguration.getKeyId(this.table);
        int protectVersion = ProtectedDataConfiguration.getProtectVersion(keyid);
        return calculateProtection(protectVersion, keyid, obj.getProtectString(protectVersion));
    }

    private static String calculateProtection(int protectVersion, String keyid, String toBesigned) throws DatabaseProtectionException {
        if (log.isTraceEnabled()) {
            log.trace("Using keyid [{}] to calculate protection.", keyid);
        }

        try {
            CryptoToken token = ProtectedDataConfiguration.getCryptoToken(keyid);
            switch (ProtectVersionEnum.convert(protectVersion)) {
                case ASYMMETRICAL:
                    PrivateKey key = token.getPrivateKey();
                    String sigalg = ProtectedDataConfiguration.getSigAlg(keyid);
                    Signature signature = Signature.getInstance(sigalg, token.getSignProviderName());
                    signature.initSign(key);
                    signature.update(toBesigned.getBytes(StandardCharsets.UTF_8));
                    byte[] bytes = signature.sign();
                    return new String(Hex.encode(bytes));
                case UNKNOWN:
                default:
                    throw new DatabaseProtectionException("Unknown protectVersion: " + protectVersion);
            }
        } catch (Exception e) {
            log.error("calculateProtection catch Exception", e);
            throw new DatabaseProtectionException(e);
        }
    }

    private void throwException(String originStr, String realprot, Exception cause, ProtectedData obj) throws DatabaseProtectionException {
        String msg = String.format("originStr[%s], realprot[%s]", originStr, realprot);
        DatabaseProtectionException dpe = new DatabaseProtectionException(msg, obj);
        if (cause != null) {
            dpe.initCause(cause);
        }

        throw dpe;
    }

    private void verifyProtection(String prot, String data, int protectVersion, String keyid, String realProt, ProtectedData obj) throws DatabaseProtectionException {
        boolean result;
        try {
            switch (ProtectVersionEnum.convert(protectVersion)) {
                case ASYMMETRICAL:
                    result = verifySignature(realProt, data, keyid);
                    break;
                case UNKNOWN:
                default:
                    result = false;
            }
        } catch (Exception e) {
            this.throwException(data, realProt, e, obj);
            return;
        }

        if (!result) {
            this.throwException(data, realProt, null, obj);
        }

        if (log.isTraceEnabled()) {
            log.trace("Verifying row string ok");
        }
    }

    private static boolean verifyHmac(String macInHex, String data, String keyid, int protectVersion) throws DatabaseProtectionException {
        String mustbeprot = calculateProtection(protectVersion, keyid, data);
        return mustbeprot.equals(macInHex);
    }

    private static boolean verifySignature(String signatureInHex, String data, String keyid) throws Exception {
        CryptoToken token = ProtectedDataConfiguration.getCryptoToken(keyid);
        String sigalg = ProtectedDataConfiguration.getSigAlg(keyid);
        Signature signature = Signature.getInstance(sigalg, token.getSignProviderName());
        PublicKey pubKey = token.getPublicKey();
        signature.initVerify(pubKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        return signature.verify(Hex.decode(signatureInHex));
    }

    public void onDataVerificationError(DatabaseProtectionException e) throws DatabaseProtectionException {
        log.error("签名验证失败", e);
        if (ProtectedDataConfiguration.errorOnVerifyFail(this.table)) {
            throw e;
        }
    }
}
