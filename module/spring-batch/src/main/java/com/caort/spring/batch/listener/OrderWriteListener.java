package com.caort.spring.batch.listener;

import com.caort.spring.batch.pojo.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Caort
 * @date 2022/7/9 11:40
 */
public class OrderWriteListener implements ItemWriteListener<Order> {
    private static final Logger log = LoggerFactory.getLogger(OrderWriteListener.class);

    @Override
    public void beforeWrite(List<? extends Order> items) {

    }

    @Override
    public void afterWrite(List<? extends Order> items) {
        log.info("Successfully wrote order to the database, orderId[{}]",
                items.stream()
                        .map(item -> item.getId() + ":" + item.getStatus())
                        .collect(Collectors.joining(", ")));
    }

    @Override
    public void onWriteError(Exception exception, List<? extends Order> items) {
        log.error("An exception occurred while writing data, data[{}]",
                items.stream()
                        .map(item -> String.valueOf(item.getId()))
                        .collect(Collectors.joining(", ")),
                exception);
    }
}
