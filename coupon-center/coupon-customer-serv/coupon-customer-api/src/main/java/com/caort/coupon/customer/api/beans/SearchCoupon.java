package com.caort.coupon.customer.api.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author caort
 * @date 2022/8/24 17:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchCoupon {
    @NotNull
    private Long userId;

    private Long shopId;

    private Integer couponStatus;
}
