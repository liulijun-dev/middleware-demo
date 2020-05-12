package com.learning.redis.demo.rabbitmq.sender;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RpcSender {
    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange rpcDirectExchange;
    private int start = 0;

    public RpcSender(RabbitTemplate rabbitTemplate, DirectExchange rpcDirectExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.rpcDirectExchange = rpcDirectExchange;
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        System.out.println("[RpcSender] Requesting fib(" + start + ")");
        Integer response = (Integer) rabbitTemplate.convertSendAndReceive
                (rpcDirectExchange.getName(), "rpc.request", start++);
        System.out.println("[RpcSender] Got '" + response + "'");
    }
}
