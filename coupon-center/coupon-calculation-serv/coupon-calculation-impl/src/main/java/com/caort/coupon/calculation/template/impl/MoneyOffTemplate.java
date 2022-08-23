package com.caort.coupon.calculation.template.impl;

import com.caort.coupon.calculation.template.AbstractRuleTemplate;
import com.caort.coupon.template.api.beans.rules.Discount;
import org.springframework.stereotype.Component;

/**
 * 满减优惠券计算规则
 *
 * @author caort
 * @date 2022/8/23 14:31
 */
@Component
public class MoneyOffTemplate extends AbstractRuleTemplate {
    @Override
    protected long calculateDiscountCost(long cost, Discount discount) {
        Long quota = discount.getQuota();
        // 如果当前门店的商品总价<quota，那么最多只能扣减shopAmount的钱数
        long benefitAmount = cost < quota ? cost : quota;
        return cost - benefitAmount;
    }
}
