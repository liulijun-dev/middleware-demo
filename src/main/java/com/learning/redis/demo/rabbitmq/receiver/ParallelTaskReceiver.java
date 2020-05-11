package com.learning.redis.demo.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ParallelTaskReceiver {
    @RabbitListener(queues = "#{queueForParallelTask.name}", ackMode = "AUTO")
    public void logTask(String in) {
        System.out.println("[ParallelTaskReceiver-logTask] Received from default exchange '" + in + "'");
    }

    @RabbitListener(queues = "#{queueForParallelTask.name}", ackMode = "AUTO")
    public void compressImage(String in) {
        System.out.println("[ParallelTaskReceiver - compressImage] Received from default exchange '" + in + "'");
    }
}
