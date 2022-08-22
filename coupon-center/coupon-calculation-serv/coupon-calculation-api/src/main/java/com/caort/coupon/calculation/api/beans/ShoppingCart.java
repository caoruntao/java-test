package com.caort.coupon.calculation.api.beans;

import com.caort.coupon.template.api.beans.CouponInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author caort
 * @date 2022/8/18 13:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {
    @NotEmpty
    private List<ProductInfo> productList;

    private Long couponId;

    private long cost;

    // 目前只支持单张优惠券
    // 但是为了以后的扩展考虑，你可以添加多个优惠券的计算逻辑
    private List<CouponInfo> couponInfoList;

    @NotNull
    private Long userId;
}
