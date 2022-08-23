package com.caort.coupon.calculation.template.impl;

import com.caort.coupon.calculation.template.AbstractRuleTemplate;
import com.caort.coupon.template.api.beans.rules.Discount;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 随机减钱
 *
 * @author caort
 * @date 2022/8/23 14:33
 */
@Component
public class RandomReductionTemplate extends AbstractRuleTemplate {
    @Override
    protected long calculateDiscountCost(long cost, Discount discount) {
        Long quota = discount.getQuota();
        // 计算使用优惠券之后的价格
        long maxBenefit = Math.min(cost, quota);
        int reductionAmount = new Random().nextInt((int) maxBenefit);
        return cost - reductionAmount;
    }
}
