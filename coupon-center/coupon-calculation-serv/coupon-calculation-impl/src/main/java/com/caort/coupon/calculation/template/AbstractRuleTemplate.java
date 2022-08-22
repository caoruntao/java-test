package com.caort.coupon.calculation.template;

import com.caort.coupon.calculation.api.beans.ProductInfo;
import com.caort.coupon.calculation.api.beans.ShoppingCart;
import com.caort.coupon.template.api.beans.CouponInfo;
import org.springframework.util.CollectionUtils;

/**
 * @author Caort
 * @date 2022/8/22 20:54
 */
public abstract class AbstractRuleTemplate implements RuleTemplate {
    @Override
    public ShoppingCart calculate(ShoppingCart settlement) {
        long originCost = 0L;
        for (ProductInfo productInfo : settlement.getProductList()) {
            originCost += productInfo.getPrice() * productInfo.getCount();
        }
        long minCost = 0L;
        for (CouponInfo couponInfo : settlement.getCouponInfoList()) {
            calculateDiscountCost();
        }

        if (CollectionUtils.isEmpty(settlement.getCouponInfoList())){

        }
        return null;
    }

    protected abstract long calculateDiscountCost(ShoppingCart cart);
}
