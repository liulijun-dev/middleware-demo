package com.learning.redis.demo.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueReceiver {
    @RabbitListener(queues = "#{receiveMessageFromSenderDirectly.name}", ackMode = "AUTO")
    public void receive(String in) {
        System.out.println("[QueueReceiver] Received from default exchange '" + in + "'");
    }
}
