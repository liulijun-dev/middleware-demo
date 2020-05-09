package com.learning.redis.demo.rabbitmq.sender;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EventSender {
    private final RabbitTemplate rabbitTemplate;
    private TopicExchange topicExchangeForNotify;

    public EventSender(RabbitTemplate rabbitTemplate, TopicExchange topicExchangeForNotify) {
        this.rabbitTemplate = rabbitTemplate;
        this.topicExchangeForNotify = topicExchangeForNotify;
    }

    @Scheduled(fixedDelay = 1000)
    public void send() {
        String message = "ID 1234567 maybe not a rean user";
        rabbitTemplate.convertAndSend(topicExchangeForNotify.getName(), "critical.rate_limit", message);
        System.out.println(" [EventSender] Sent '" + message + "'");
    }
}
