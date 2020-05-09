package com.learning.redis.demo.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RabbitListener(queues = {"rate_limit"})
@Component
public class RateLimitEventReceiver {

    @RabbitHandler
    public void receive(String in) {
        System.out.println(" [RateLimitEventReceiver] Received from topic exchange '" + in + "'");
    }
}
