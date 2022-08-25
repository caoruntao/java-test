package com.caort.coupon.customer.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author caort
 * @date 2022/8/24 17:46
 */
@Getter
@AllArgsConstructor
public enum CouponStatus {

    AVAILABLE("未使用", 1),
    USED("已用", 2),
    INACTIVE("已经注销", 3);

    private final String desc;
    private final Integer code;

    public static CouponStatus convert(Integer code) {
        return Arrays.stream(values())
                .filter(couponStatus -> couponStatus.getCode().equals(code))
                .findAny()
                .orElse(null);
    }
}
