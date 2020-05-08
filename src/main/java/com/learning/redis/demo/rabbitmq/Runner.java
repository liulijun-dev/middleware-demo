package com.learning.redis.demo.rabbitmq;

import com.learning.redis.demo.rabbitmq.receiver.TopicExchangeReceiver;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final TopicExchangeReceiver receiver;
    private final Queue queue;

    public Runner(TopicExchangeReceiver receiver, RabbitTemplate rabbitTemplate, Queue queue) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {
            System.out.println("================");
            System.out.println("correlationData = " + correlationData);
            System.out.println("ack = " + ack);
            System.out.println("cause = " + cause);
            System.out.println("================");
        });
        rabbitTemplate.convertAndSend(RabbitmqConfig.TOPIC_EXCHANGE_NAME, "foo.bar.baz", "Hello from RabbitMQ!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }

}
