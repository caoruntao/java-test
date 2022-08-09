package com.caort.coupon.template.dao.converter;

import com.caort.coupon.template.api.enums.CouponType;

import javax.persistence.AttributeConverter;

/**
 * @author caort
 * @date 2022/8/9 10:16
 */
public class CouponTypeConverter implements AttributeConverter<CouponType, String> {
    @Override
    public String convertToDatabaseColumn(CouponType attribute) {
        return attribute == null ? CouponType.UNKNOWN.getCode() : attribute.getCode();
    }

    @Override
    public CouponType convertToEntityAttribute(String dbData) {
        return CouponType.convert(dbData);
    }
}
