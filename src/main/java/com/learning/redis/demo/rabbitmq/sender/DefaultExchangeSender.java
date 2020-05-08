package com.learning.redis.demo.rabbitmq.sender;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DefaultExchangeSender {
    private final RabbitTemplate rabbitTemplate;
    private final Queue defaultExchangeQueue;

    public DefaultExchangeSender(RabbitTemplate rabbitTemplate, Queue defaultExchangeQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.defaultExchangeQueue = defaultExchangeQueue;
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        String message = "Hello World!";
        this.rabbitTemplate.convertAndSend(defaultExchangeQueue.getName(), message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
