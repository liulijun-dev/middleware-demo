package com.learning.redis.demo.rabbitmq.sender;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EventSender {
    private final RabbitTemplate rabbitTemplate;
    private TopicExchange topicExchangeForEvent;

    public EventSender(RabbitTemplate rabbitTemplate, TopicExchange topicExchangeForEvent) {
        this.rabbitTemplate = rabbitTemplate;
        this.topicExchangeForEvent = topicExchangeForEvent;
    }

    @Scheduled(fixedDelay = 1000)
    public void send() {
        String message = "ID 1234567 maybe not a rean user";
        rabbitTemplate.convertAndSend(topicExchangeForEvent.getName(), "critical.rate_limit", message);
        System.out.println("[EventSender] Sent '" + message + "'");
    }
}
