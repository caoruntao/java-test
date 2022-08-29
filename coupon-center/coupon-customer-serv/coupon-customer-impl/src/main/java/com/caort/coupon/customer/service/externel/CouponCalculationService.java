package com.caort.coupon.customer.service.externel;

import com.caort.coupon.calculation.api.beans.ShoppingCart;
import com.caort.coupon.calculation.api.beans.SimulationOrder;
import com.caort.coupon.calculation.api.beans.SimulationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author caort
 * @date 2022/8/26 13:47
 */
@Service
public class CouponCalculationService {
    @Autowired
    private RestTemplate restTemplate;

    public ShoppingCart calculateOrderPrice(ShoppingCart cart) {
        URI uri = URI.create("http://coupon-calculation-serv/calculator/checkout");
        ResponseEntity<ShoppingCart> responseEntity =
                restTemplate.postForEntity(uri, cart, ShoppingCart.class);

        return responseEntity.getBody();
    }

    public SimulationResponse simulateOrder(SimulationOrder order) {
        URI uri = URI.create("http://coupon-calculation-serv/calculator/simulate");
        ResponseEntity<SimulationResponse> responseEntity =
                restTemplate.postForEntity(uri, order, SimulationResponse.class);

        return responseEntity.getBody();
    }
}
