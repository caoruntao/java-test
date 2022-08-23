package com.caort.coupon.calculation.template.impl;

import com.caort.coupon.calculation.template.AbstractRuleTemplate;
import com.caort.coupon.template.api.beans.rules.Discount;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * @author caort
 * @date 2022/8/23 14:35
 */
@Component
public class LonelyNightTemplate extends AbstractRuleTemplate {
    @Override
    protected long calculateDiscountCost(long cost, Discount discount) {
        Long quota = discount.getQuota();

        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (hourOfDay >= 23 || hourOfDay < 2) {
            quota *= 2;
        }

        long benefitAmount = cost < quota ? cost : quota;
        return cost - benefitAmount;
    }
}
