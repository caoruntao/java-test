package com.caort.coupon.calculation.controller;

import com.alibaba.fastjson.JSON;
import com.caort.coupon.calculation.api.beans.ShoppingCart;
import com.caort.coupon.calculation.api.beans.SimulationOrder;
import com.caort.coupon.calculation.api.beans.SimulationResponse;
import com.caort.coupon.calculation.service.CouponCalculationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/calculator")
public class CouponCalculationController {

    @Autowired
    private CouponCalculationService calculationService;

    // 优惠券结算
    @PostMapping("/checkout")
    public ShoppingCart calculateOrderPrice(@Valid @RequestBody ShoppingCart settlement) {
        log.info("do calculation: {}", JSON.toJSONString(settlement));
        return calculationService.calculateOrderPrice(settlement);
    }

    // 优惠券列表挨个试算
    // 给客户提示每个可用券的优惠额度，帮助挑选
    @PostMapping("/simulate")
    public SimulationResponse simulate(@Valid @RequestBody SimulationOrder simulator) {
        log.info("do simulation: {}", JSON.toJSONString(simulator));
        return calculationService.simulateOrder(simulator);
    }
}
