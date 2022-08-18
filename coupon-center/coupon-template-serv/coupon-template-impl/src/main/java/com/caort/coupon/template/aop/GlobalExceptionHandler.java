package com.caort.coupon.template.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author caort
 * @date 2022/8/17 17:42
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // TODO 可以做一个统一的返回对象
    @ExceptionHandler(IllegalArgumentException.class)
    public void illegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException:{}", e.getMessage(), e);
        throw e;
    }
}
