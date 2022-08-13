package com.caort.order.controller;

import com.caort.order.feign.ProductFeign;
import com.caort.order.pojo.dto.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Caort
 * @date 2022/2/19 11:44
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private ProductFeign productFeign;
    @PostMapping
    public Boolean submitOrder(@RequestBody Order order){
        String productId = order.getProductId();
        Integer number = order.getNumber();
        log.info("开始下单,商品[{}],数量[{}]", productId, number);
        try {
            productFeign.buyProduct(productId, number);
        }catch (Exception e){
            log.info("下单失败", e);
            return Boolean.FALSE;
        }
        log.info("下单成功");
        return Boolean.TRUE;
    }
}
