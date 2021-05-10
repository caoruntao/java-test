package com.caort.mq.listener;

import com.caort.mq.config.QueueConfig;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Reed
 * @date 2021/5/10 上午11:01
 */
@Component
public class MessageHandler {
    private static final Logger log = LoggerFactory.getLogger(MessageHandler.class);

    @RabbitListener(queues = QueueConfig.QUEUE)
    public void processMessageOnExpireQueue(String content, Message message, Channel channel) {
        try {
            Thread.sleep(1000L);
            log.info("receive message from [queue] is [{}] [{}]", content, message.getMessageProperties().getExpiration());
            log.info("receive message deliveryTag is [{}]", message.getMessageProperties().getDeliveryTag());
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = QueueConfig.DELAY_QUEUE)
    public void processMessageOnDeadLetterQueue(String content, Message message, Channel channel) {
        log.info("receive message from [delay.queue] is [{}]", content);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
