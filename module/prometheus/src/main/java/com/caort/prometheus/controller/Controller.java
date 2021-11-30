package com.caort.prometheus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Caort.
 * @date 2021/8/27 上午10:05
 */
@RestController
public class Controller {
    @GetMapping("/test")
    public String test(){
        return "hello world";
    }
}
