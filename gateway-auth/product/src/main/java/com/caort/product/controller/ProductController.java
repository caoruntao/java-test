package com.caort.product.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Caort
 * @date 2022/2/19 11:53
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/{productId}/{number}")
    public Boolean buyProduct(@PathVariable("productId") String productId, @PathVariable("number") Integer number) {
        log.info("购买商品[{}],数量[{}],库存更新成功", productId, number);
        return Boolean.TRUE;
    }
}
