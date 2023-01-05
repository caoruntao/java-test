package com.caort.spring.i18n.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caort
 * @date 2023/1/5 10:50
 */
@RestController
public class I18nController {
    @GetMapping("/str")
    public String str(){
        throw new RuntimeException("200");
    }
}
