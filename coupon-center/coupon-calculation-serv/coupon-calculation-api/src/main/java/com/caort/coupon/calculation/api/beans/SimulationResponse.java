package com.caort.coupon.calculation.api.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author caort
 * @date 2022/8/18 13:33
 */
@Data
@NoArgsConstructor
public class SimulationResponse {
    // 最省钱的coupon
    private Long bestCouponId;

    // 每一个coupon对应的order价格
    private Map<Long, Long> couponToOrderPrice = new HashMap<>();
}
