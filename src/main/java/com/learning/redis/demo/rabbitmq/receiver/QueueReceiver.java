package com.learning.redis.demo.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = {"receive_message_from_sender_directly"})
public class QueueReceiver {
    @RabbitHandler
    public void receive(String in) {
        System.out.println(" [QueueReceiver] Received from default exchange '" + in + "'");
    }
}
