package com.caort.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Caort
 * @date 2022/2/19 11:48
 */
@FeignClient("product-service")
public interface ProductFeign {
    @PostMapping("/product/{productId}/{number}")
    Boolean buyProduct(@PathVariable("productId") String productId, @PathVariable("number") Integer number);
}
