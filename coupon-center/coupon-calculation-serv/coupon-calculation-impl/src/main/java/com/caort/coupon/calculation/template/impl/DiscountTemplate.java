package com.caort.coupon.calculation.template.impl;

import com.caort.coupon.calculation.template.AbstractRuleTemplate;
import com.caort.coupon.template.api.beans.rules.Discount;
import org.springframework.stereotype.Component;

/**
 * 打折优惠券
 *
 * @author caort
 * @date 2022/8/23 14:22
 */
@Component
public class DiscountTemplate extends AbstractRuleTemplate {
    @Override
    protected long calculateDiscountCost(long cost, Discount discount) {
        if(cost < discount.getThreshold()){
            return cost;
        }
        return convertToDecimal(cost * (discount.getQuota().doubleValue() / 100));
    }
}
