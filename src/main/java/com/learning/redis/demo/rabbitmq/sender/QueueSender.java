package com.learning.redis.demo.rabbitmq.sender;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {
    private final RabbitTemplate rabbitTemplate;
    private final Queue receiveMessageFromSenderDirectly;

    public QueueSender(RabbitTemplate rabbitTemplate, Queue receiveMessageFromSenderDirectly) {
        this.rabbitTemplate = rabbitTemplate;
        this.receiveMessageFromSenderDirectly = receiveMessageFromSenderDirectly;
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        String message = "Hello World!";
        this.rabbitTemplate.convertAndSend(receiveMessageFromSenderDirectly.getName(), message);
        System.out.println("[QueueSender] Sent '" + message + "'");
    }
}
