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
    static final String TOPIC_EXCHANGE_NAME = "topic-exchange";

    private static final String queueName = "spring-boot";

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

    private static class DefaultExchangeConfig {
        private static final String DEFAULT_EXCHANGE_NAME = "default-exchange";
        private static final String DEFAULT_QUEUE_NAME = "default-queue";

        @Bean
        public Queue defaultExchangeQueue() {
            return new Queue(DEFAULT_QUEUE_NAME);
        }
    }
}
