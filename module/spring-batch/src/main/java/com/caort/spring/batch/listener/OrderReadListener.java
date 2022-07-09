package com.caort.spring.batch.listener;

import com.caort.spring.batch.pojo.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;

/**
 * @author Caort
 * @date 2022/7/9 12:12
 */
public class OrderReadListener implements ItemReadListener<Order> {
    private static final Logger log = LoggerFactory.getLogger(OrderReadListener.class);

    @Override
    public void beforeRead() {

    }

    @Override
    public void afterRead(Order item) {

    }

    @Override
    public void onReadError(Exception ex) {
        log.error("An exception occurred while reading data", ex);
    }
}
