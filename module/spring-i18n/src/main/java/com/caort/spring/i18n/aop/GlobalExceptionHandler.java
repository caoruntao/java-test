package com.caort.spring.i18n.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

/**
 * @author caort
 * @date 2023/1/5 11:04
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler
    public String handle(Exception e) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(e.getMessage(), null, "error", locale);
    }
}
