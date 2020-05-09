package com.learning.redis.demo.rabbitmq;

import com.learning.redis.demo.rabbitmq.receiver.TopicExchangeReceiver;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    static final String TOPIC_EXCHANGE_NAME = "topic_exchange";

    private static final String queueName = "spring_boot";

    @Bean
    public Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                                    MessageListenerAdapter listenerAdapter,
                                                    TopicExchangeReceiver receiver) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        //container.setMessageListener(listenerAdapter);
        container.setMessageListener(receiver);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(TopicExchangeReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    public Queue receiveMessageFromSenderDirectly() {
        return new Queue("receive_message_from_sender_directly");
    }

    private static class DirectExchangeConfig {
        private static final String DIRECT_EXCHANGE_NAME = "color_direct-exchange";
        private static final String DIRECT_EXCHANGE_QUEUE_NAME = "binding_to_multi_exchange";
        @Bean
        public DirectExchange directExchange() {
            return new DirectExchange(DIRECT_EXCHANGE_NAME);
        }

        @Bean
        public Queue directExchangeQueue() {
            return new Queue(DIRECT_EXCHANGE_QUEUE_NAME);
        }

        @Bean
        public Binding bindingToColorExchange(DirectExchange directExchange, Queue directExchangeQueue) {
            return BindingBuilder.bind(directExchangeQueue).to(directExchange).with("orange");
        }

        /*@Bean
        public Binding bindingToAmqRabbitmqTraceExchange() {

        }*/
    }

    private static class TopicExchangeConfig {
        @Bean
        public TopicExchange topicExchangeForNotify() {
            return new TopicExchange("topic_exchange_for_notify", false, true);
        }

        @Bean
        public Queue criticalQueue() {
            return new Queue("critical", false);
        }

        @Bean
        public Queue rateLimitQueue() {
            return new Queue("rate_limit", false);
        }

        @Bean
        public Binding bindingCriticalQueue(TopicExchange topicExchangeForNotify, Queue criticalQueue) {
            return BindingBuilder.bind(criticalQueue).to(topicExchangeForNotify).with("critical.*");
        }

        @Bean
        public Binding bindingRateLimitQueue(TopicExchange topicExchangeForNotify, Queue rateLimitQueue) {
            return BindingBuilder.bind(rateLimitQueue).to(topicExchangeForNotify).with("*.rate_limit");
        }
    }
}
