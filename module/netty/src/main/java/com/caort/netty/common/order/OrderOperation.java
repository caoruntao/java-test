package com.caort.netty.common.order;


import com.caort.netty.common.Operation;
import com.google.common.util.concurrent.Uninterruptibles;
import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
public class OrderOperation extends Operation {

    private int tableId;
    private String dish;

    public OrderOperation(int tableId, String dish) {
        this.tableId = tableId;
        this.dish = dish;
    }

    @Override
    public OrderOperationResult execute() {
        System.out.println("order's executing startup with orderRequest: " + toString());
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        System.out.println("order's executing complete");
        OrderOperationResult orderResponse = new OrderOperationResult(tableId, dish, true);
        return orderResponse;
    }
}
