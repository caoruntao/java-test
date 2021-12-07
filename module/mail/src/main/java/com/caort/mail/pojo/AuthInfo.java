package com.caort.mail.pojo;

/**
 * @author Caort
 * @date 2021/11/26 下午4:49
 */
public class AuthInfo {
    private String domain;
    private String authDomain;
    private String authMethod;
    private String authKey;
    private String authValue;
    private String authPath;

    private String dns_cname_record;
    private String dns_proxy_cname;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAuthDomain() {
        return authDomain;
    }

    public void setAuthDomain(String authDomain) {
        this.authDomain = authDomain;
    }

    public String getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getAuthValue() {
        return authValue;
    }

    public void setAuthValue(String authValue) {
        this.authValue = authValue;
    }

    public String getAuthPath() {
        return authPath;
    }

    public void setAuthPath(String authPath) {
        this.authPath = authPath;
    }

    public String getDns_cname_record() {
        return dns_cname_record;
    }

    public void setDns_cname_record(String dns_cname_record) {
        this.dns_cname_record = dns_cname_record;
    }

    public String getDns_proxy_cname() {
        return dns_proxy_cname;
    }

    public void setDns_proxy_cname(String dns_proxy_cname) {
        this.dns_proxy_cname = dns_proxy_cname;
    }
}
