package com.learning.redis.demo.rabbitmq.receiver;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class TopicExchangeReceiver implements ChannelAwareMessageListener {
    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("Received <" + message + ">");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        latch.countDown();
    }
}
