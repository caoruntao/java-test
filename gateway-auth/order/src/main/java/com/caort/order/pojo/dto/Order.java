package com.caort.order.pojo.dto;

/**
 * @author Caort
 * @date 2022/2/19 12:13
 */
public class Order {
    private String productId;
    private Integer number;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
