package com.caort.coupon.calculation.service.impl;

import com.caort.coupon.calculation.api.beans.ProductInfo;
import com.caort.coupon.calculation.api.beans.ShoppingCart;
import com.caort.coupon.calculation.api.beans.SimulationOrder;
import com.caort.coupon.calculation.api.beans.SimulationResponse;
import com.caort.coupon.calculation.service.CouponCalculationService;
import com.caort.coupon.template.api.beans.CouponInfo;
import com.caort.coupon.template.api.beans.CouponTemplateInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Caort
 * @date 2022/8/22 20:34
 */
@Service
public class CouponCalculationServiceImpl implements CouponCalculationService {

    @Override
    public ShoppingCart calculateOrderPrice(ShoppingCart cart) {
        List<CouponInfo> couponInfoList = cart.getCouponInfoList();
        List<ProductInfo> productList = cart.getProductList();
        long minCost = Long.MAX_VALUE;
        for (CouponInfo couponInfo : couponInfoList) {
            AtomicLong cost = new AtomicLong(0L);
            productList.stream().filter(productInfo -> {
                if(couponInfo.getShopId().equals(productInfo.getShopId())){
                    return true;
                }
                cost.addAndGet(productInfo.getCount() * productInfo.getPrice());
                return false;
            }).forEach(productInfo -> {
                CouponTemplateInfo template = couponInfo.getTemplate();
                template.getRule()
            });
        }
        return null;
    }

    @Override
    public SimulationResponse simulateOrder(SimulationOrder cart) {
        return null;
    }
}
