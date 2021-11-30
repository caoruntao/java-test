package com.caort.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Caort.
 * @date 2021/5/10 上午10:46
 */
@Component
public class QueueConfig {
    public static final String QUEUE = "queue";
    public static final String KEY = "key";
    public static final String EXCHANGE = "exchange";
    public static final String DELAY_QUEUE = "delay.queue";
    public static final String DELAY_KEY = "delay.key";
    public static final String DELAY_EXCHANGE = "delay.exchange";

    @Bean
    Queue queue() {
        Queue queue = new Queue(QUEUE);
        queue.addArgument("x-message-ttl", 20000);
        queue.addArgument("x-dead-letter-exchange", DELAY_EXCHANGE);
        queue.addArgument("x-dead-letter-routing-key", DELAY_KEY);
        return queue;
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(KEY);
    }

    @Bean
    Queue delayQueue() {
        return new Queue(DELAY_QUEUE);
    }

    @Bean
    DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE);
    }

    @Bean
    Binding delayBinding(Queue delayQueue, DirectExchange delayExchange) {
        return BindingBuilder.bind(delayQueue).to(delayExchange).with(DELAY_KEY);
    }
}
