package com.learning.redis.demo.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RabbitListener(queues = {"critical"})
@Component
public class CriticalEventReceiver {

    @RabbitHandler
    public void receive(String in) {
        System.out.println(" [CriticalEventReceiver] Received from topic exchange '" + in + "'");
    }
}
