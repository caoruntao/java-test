package com.caort.coupon.calculation.template;

import com.caort.coupon.calculation.api.beans.ProductInfo;
import com.caort.coupon.calculation.api.beans.ShoppingCart;
import com.caort.coupon.template.api.beans.CouponInfo;
import com.caort.coupon.template.api.beans.rules.Discount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Caort
 * @date 2022/8/22 20:54
 */
public abstract class AbstractRuleTemplate implements RuleTemplate {
    @Override
    public ShoppingCart calculate(ShoppingCart settlement) {
        List<ProductInfo> productList = settlement.getProductList();
        long originCost = getTotalCost(productList);

        CouponInfo couponInfo = settlement.getCouponInfoList().get(0);
        Discount discount = couponInfo.getTemplate().getRule().getDiscount();

        long discountCost;
        // 全店通用券
        if (couponInfo.getShopId() == null) {
            discountCost = calculateDiscountCost(originCost, discount);
        } else {
            // 店铺券
            Map<Long, Long> totalPriceGroupByShop = getTotalPriceGroupByShop(productList);
            // 购物车中没有优惠券门店的产品
            if (!totalPriceGroupByShop.containsKey(couponInfo.getShopId())) {
                settlement.setCost(originCost);
                settlement.setCouponInfoList(Collections.emptyList());
                return settlement;
            }

            Long canDiscountProductCost = totalPriceGroupByShop.get(couponInfo.getShopId());
            long partProductDiscountCost = calculateDiscountCost(canDiscountProductCost, discount);
            // 如果优惠前后价格一样，说明没到使用门槛，和不使用优惠券一样
            if (partProductDiscountCost == canDiscountProductCost) {
                settlement.setCost(originCost);
                settlement.setCouponInfoList(Collections.emptyList());
                return settlement;
            }
            discountCost = originCost - canDiscountProductCost + partProductDiscountCost;
        }
        discountCost = Math.max(discountCost, minCost());

        settlement.setCouponId(couponInfo.getId());
        settlement.setCost(discountCost);
        return settlement;
    }

    /**
     * @param cost     可以使用优惠的原总价
     * @param discount  优惠力度
     */
    protected abstract long calculateDiscountCost(long cost, Discount discount);

    protected long minCost() {
        return 1L;
    }

    protected long convertToDecimal(Double value) {
        return new BigDecimal(value).setScale(0, RoundingMode.HALF_UP).longValue();
    }

    protected long getTotalCost(List<ProductInfo> productList) {
        return productList.stream()
                .mapToLong(productInfo -> productInfo.getPrice() * productInfo.getCount())
                .sum();
    }

    /**
     * 根据门店维度计算每个门店下商品价格
     * key = shopId
     * value = 门店商品总价
     *
     */
    private Map<Long, Long> getTotalPriceGroupByShop(List<ProductInfo> productInfoList) {
        return productInfoList.stream()
                .collect(Collectors.groupingBy(ProductInfo::getShopId,
                        Collectors.summingLong(p -> p.getPrice() * p.getCount()))
                );
    }
}
