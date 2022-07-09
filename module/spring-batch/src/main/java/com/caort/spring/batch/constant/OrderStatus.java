package com.caort.spring.batch.constant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.ObjectUtils;

import java.util.function.Predicate;

/**
 * @author Caort
 * @date 2022/7/9 10:08
 */
public enum OrderStatus {
    NEW("new", "新订单"),
    COMPLETED("completed", "已完成"),
    EXPIRED("expired", "已失效"),
    WAIT_RENEW("wait_renew", "待续费"),
    UNKNOWN("unknown", "未知");

    private String code;
    @JsonValue
    private String describe;

    OrderStatus(String code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public static OrderStatus findByCode(String code) {
        Predicate<OrderStatus> predicate = orderStatus -> ObjectUtils.nullSafeEquals(orderStatus.code, code);
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (predicate.test(orderStatus)) {
                return orderStatus;
            }
        }
        return OrderStatus.UNKNOWN;
    }

    public String getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }
}
