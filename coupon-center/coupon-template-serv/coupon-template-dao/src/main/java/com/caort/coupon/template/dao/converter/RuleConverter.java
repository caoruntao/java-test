package com.caort.coupon.template.dao.converter;

import com.alibaba.fastjson.JSON;
import com.caort.coupon.template.api.beans.rules.TemplateRule;

import javax.persistence.AttributeConverter;

/**
 * @author caort
 * @date 2022/8/9 10:22
 */
public class RuleConverter implements AttributeConverter<TemplateRule, String> {
    @Override
    public String convertToDatabaseColumn(TemplateRule attribute) {
        return JSON.toJSONString(attribute);
    }

    @Override
    public TemplateRule convertToEntityAttribute(String dbData) {
        return JSON.parseObject(dbData, TemplateRule.class);
    }
}
