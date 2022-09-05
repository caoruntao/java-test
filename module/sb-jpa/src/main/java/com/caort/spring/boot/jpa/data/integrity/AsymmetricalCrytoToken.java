package com.caort.spring.boot.jpa.data.integrity;

import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author caort
 * @date 2022/9/2 10:34
 */
public class AsymmetricalCrytoToken implements CryptoToken {
    private KeyPair keyPair;

    private String signProviderName;

    @Override
    public Key getKey() {
        return null;
    }

    @Override
    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    @Override
    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    @Override
    public String getSignProviderName() {
        return this.signProviderName;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public void setSignProviderName(String signProviderName) {
        this.signProviderName = signProviderName;
    }
}
