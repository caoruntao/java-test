package com.caort.spring.boot.jpa.data.integrity;

import java.util.Arrays;

/**
 * @author caort
 * @date 2022/9/2 13:18
 */
public enum ProtectVersionEnum {
    UNKNOWN(-1),
    ASYMMETRICAL(1);

    private int code;

    ProtectVersionEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static ProtectVersionEnum convert(int code) {
        return Arrays.stream(values())
                .filter(protectVersionEnum -> protectVersionEnum.code == code)
                .findFirst()
                .orElse(UNKNOWN);
    }
}
