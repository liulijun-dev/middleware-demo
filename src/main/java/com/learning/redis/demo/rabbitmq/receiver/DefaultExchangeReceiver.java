package com.learning.redis.demo.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = {"default-queue"})
public class DefaultExchangeReceiver {
    @RabbitHandler
    public void receive(String in) {
        System.out.println(" [x] Received from default exchange '" + in + "'");
    }
}
