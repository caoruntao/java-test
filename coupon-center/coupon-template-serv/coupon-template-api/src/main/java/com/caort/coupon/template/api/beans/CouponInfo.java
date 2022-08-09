package com.caort.coupon.template.api.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装优惠券信息
 *
 * @author caort
 * @date 2022/8/9 09:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponInfo {
    private Long id;

    private Long userId;

    private Long templateId;

    private Long shopId;

    private Integer status;

    private CouponTemplateInfo template;
}
