package com.caort.coupon.customer.convert;

import com.caort.coupon.customer.dao.entity.Coupon;
import com.caort.coupon.template.api.beans.CouponInfo;

/**
 * @author caort
 * @date 2022/8/25 09:07
 */
public class CouponConverter {

    public static CouponInfo convertToCoupon(Coupon coupon) {

        return CouponInfo.builder()
                .id(coupon.getId())
                .status(coupon.getStatus().getCode())
                .templateId(coupon.getTemplateId())
                .shopId(coupon.getShopId())
                .userId(coupon.getUserId())
                .template(coupon.getTemplateInfo())
                .build();
    }
}
