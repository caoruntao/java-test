package com.caort.coupon.customer.api.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author caort
 * @date 2022/8/24 17:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestCoupon {
    // 用户领券
    @NotNull
    private Long userId;

    // 券模板ID
    @NotNull
    private Long couponTemplateId;
}
