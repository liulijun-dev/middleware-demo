package com.learning.redis.demo.rabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RpcReceiver {

    @RabbitListener(queues = "#{rpcRequestQueue.name}")
    // @SendTo("tut.rpc.replies") used when the
    // client doesn't set replyTo.
    public int fibonacci(int n) {
        System.out.println("[RpcReceiver] Received request for " + n);
        int result = fib(n);
        System.out.println("[RpcReceiver] Returned " + result);
        return result;
    }

    public int fib(int n) {
        return n == 0 ? 0 : n == 1 ? 1 : (fib(n - 1) + fib(n - 2));
    }
}
