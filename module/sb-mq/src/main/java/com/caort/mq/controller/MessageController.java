package com.caort.mq.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Caort
 * @date 2022/7/7 21:18
 */
@RestController
@RequestMapping("/msg")
public class MessageController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/push/{msg}")
    public String push(@PathVariable("msg") String msg) {
        rabbitTemplate.convertAndSend("directExchange", "direct", msg);
        return msg;
    }
}
