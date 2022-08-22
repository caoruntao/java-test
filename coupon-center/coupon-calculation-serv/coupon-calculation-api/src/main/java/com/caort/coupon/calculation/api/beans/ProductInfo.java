package com.caort.coupon.calculation.api.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author caort
 * @date 2022/8/18 13:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {
    private Long productId;

    // 商品的价格
    private long price;

    // 商品在购物车里的数量
    private Integer count;

    // 商品销售的门店
    private Long shopId;
}
