package com.caort.mq.conf;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Caort
 * @date 2022/7/7 21:23
 */
@Configuration
public class MqConfig {
    @Bean
    public DirectExchange directExchange(){
        return ExchangeBuilder.directExchange("directExchange").build();
    }

    @Bean
    public Queue directQueue(){
        return QueueBuilder.durable("directQueue").build();
    }

    @Bean
    public Binding directRoutingKey(DirectExchange directExchange, Queue directQueue){
        return BindingBuilder.bind(directQueue).to(directExchange).with("direct");
    }

}
