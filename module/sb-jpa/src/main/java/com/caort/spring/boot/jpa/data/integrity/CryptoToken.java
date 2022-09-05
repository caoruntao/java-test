package com.caort.spring.boot.jpa.data.integrity;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author caort
 * @date 2022/9/2 10:32
 */
public interface CryptoToken {
    Key getKey();

    PublicKey getPublicKey();

    PrivateKey getPrivateKey();

    String getSignProviderName();
}
