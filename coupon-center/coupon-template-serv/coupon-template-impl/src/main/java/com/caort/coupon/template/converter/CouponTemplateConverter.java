package com.caort.coupon.template.converter;

import com.caort.coupon.template.api.beans.CouponTemplateInfo;
import com.caort.coupon.template.api.enums.CouponType;
import com.caort.coupon.template.dao.entity.CouponTemplate;

/**
 * @author caort
 * @date 2022/8/17 16:51
 */
public class CouponTemplateConverter {
    public static CouponTemplateInfo entity2Dto(CouponTemplate entity) {
        return CouponTemplateInfo.builder()
                .id(entity.getId())
                .name(entity.getName())
                .desc(entity.getDescription())
                .type(entity.getCategory().getCode())
                .shopId(entity.getShopId())
                .rule(entity.getRule())
                .available(entity.getAvailable())
                .build();
    }

    public static CouponTemplate dto2Entity(CouponTemplateInfo dto) {
        return CouponTemplate.builder()
                .name(dto.getName())
                .description(dto.getDesc())
                .category(CouponType.convert(dto.getType()))
                .shopId(dto.getShopId())
                .rule(dto.getRule())
                .available(dto.getAvailable())
                .build();
    }
}
