package com.caort.spring.batch.conf.convert;

import com.caort.spring.batch.constant.OrderStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Caort
 * @date 2022/7/9 10:14
 */
@Converter(autoApply = true)
public class OrderStatusConvert implements AttributeConverter<OrderStatus, String> {
    @Override
    public String convertToDatabaseColumn(OrderStatus orderStatus) {
        if (orderStatus == null) {
            return null;
        }
        return orderStatus.getCode();
    }

    @Override
    public OrderStatus convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
        return OrderStatus.findByCode(code);
    }
}
