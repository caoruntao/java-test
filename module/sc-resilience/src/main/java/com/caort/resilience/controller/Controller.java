package com.caort.resilience.controller;

import com.caort.resilience.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Caort
 * @date 2022/7/17 10:15
 */
@RestController
@RequestMapping("/mvc")
public class Controller {
    @Autowired
    private TestService service;

    @GetMapping("/circuit_breaker")
    public String circuitBreaker(){
        return service.circuitBreaker();
    }
}
