package com.caort.coupon.customer.dao.convert;

import com.caort.coupon.customer.api.enums.CouponStatus;

import javax.persistence.AttributeConverter;

/**
 * @author caort
 * @date 2022/8/25 08:46
 */
public class CouponStatusConverter implements AttributeConverter<CouponStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(CouponStatus attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public CouponStatus convertToEntityAttribute(Integer dbData) {
        return CouponStatus.convert(dbData);
    }
}
