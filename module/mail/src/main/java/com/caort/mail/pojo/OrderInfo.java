package com.caort.mail.pojo;

import java.util.List;

/**
 * @author Caort
 * @date 2021/11/26 下午1:15
 */
public class OrderInfo {
    private String partnerOrderId;
    private String user;
    private String stateCode;
    private String productBrand;

    private String adminFirstName;
    private String adminLastName;
    private String adminEmail;

    private String orgName;
    private PKIInfo pki;
    private List<AuthInfo> auths;

    public String getPartnerOrderId() {
        return partnerOrderId;
    }

    public void setPartnerOrderId(String partnerOrderId) {
        this.partnerOrderId = partnerOrderId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getAdminFirstName() {
        return adminFirstName;
    }

    public void setAdminFirstName(String adminFirstName) {
        this.adminFirstName = adminFirstName;
    }

    public String getAdminLastName() {
        return adminLastName;
    }

    public void setAdminLastName(String adminLastName) {
        this.adminLastName = adminLastName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public PKIInfo getPki() {
        return pki;
    }

    public void setPki(PKIInfo pki) {
        this.pki = pki;
    }

    public List<AuthInfo> getAuths() {
        return auths;
    }

    public void setAuths(List<AuthInfo> auths) {
        this.auths = auths;
    }
}
