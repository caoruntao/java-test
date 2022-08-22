package com.caort.coupon.calculation.template;

import com.caort.coupon.calculation.api.beans.ShoppingCart;

/**
 * @author Caort
 * @date 2022/8/22 20:54
 */
public interface RuleTemplate {
    // 计算优惠券
    ShoppingCart calculate(ShoppingCart settlement);
}
