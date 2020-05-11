package com.learning.redis.demo.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EventReceiver {

    @RabbitListener(queues = "#{criticalQueue.name}", ackMode = "AUTO")
    public void receiveCriticalEvent(String in) {
        System.out.println("[ReceiveCriticalEvent] Received from topic exchange '" + in + "'");
    }

    @RabbitListener(queues = "#{rateLimitQueue.name}", ackMode = "AUTO")
    public void receiveRateLimitEvent(String in) {
        System.out.println("[ReceiveRateLimitEvent] Received from topic exchange '" + in + "'");
    }
}
