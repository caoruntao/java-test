package com.caort.mq.controller;

import com.caort.mq.config.QueueConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Caort.
 * @date 2021/5/10 下午1:24
 */
@RestController
@RequestMapping("/msg")
public class MessageController {
    private static final Logger log = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("/send/{content}")
    public void sendMessage(@PathVariable String content) {
        amqpTemplate.convertAndSend(QueueConfig.EXCHANGE, QueueConfig.KEY, content);
        log.info("message sent [{}] successfully", content);
    }

    @GetMapping("/send/{content}/{ttl}")
    public void sendMessageTTL(@PathVariable String content, @PathVariable String ttl) {
        amqpTemplate.convertAndSend(QueueConfig.EXCHANGE, QueueConfig.KEY, content, message -> {
            message.getMessageProperties().setExpiration(ttl);
            return message;
        });
        log.info("message sent [{}] successfully, ttl is [{}]", content, ttl);
    }
}
