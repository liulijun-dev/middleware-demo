package com.learning.redis.demo.rabbitmq.sender;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ParallelTaskSender {
    private final RabbitTemplate rabbitTemplate;
    private final FanoutExchange fanoutExchangeForParallelTask;

    public ParallelTaskSender(RabbitTemplate rabbitTemplate, FanoutExchange fanoutExchangeForParallelTask) {
        this.rabbitTemplate = rabbitTemplate;
        this.fanoutExchangeForParallelTask = fanoutExchangeForParallelTask;
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        String message = "{id:'123456', path:'/users/123456/pictures'}";
        this.rabbitTemplate.convertAndSend(fanoutExchangeForParallelTask.getName(), "fanout.parallel_task", message);
        System.out.println("[ParallelTaskSender] Sent '" + message + "'");
    }
}
