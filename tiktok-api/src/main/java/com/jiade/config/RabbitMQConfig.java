package com.jiade.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Exchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: JIADE
 * @description: RabbitMQConfig
 * @date: 2023/4/18 10:10
 **/

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_MSG = "exchange_msg";

    public static final String QUEUE_MSG_LIKE = "queue_msg_like";
    public static final String QUEUE_MSG_COMMENT = "queue_msg_comment";
    public static final String QUEUE_MSG_FANS = "queue_msg_fans";

    @Bean(EXCHANGE_MSG)
    public Exchange exchange() {
        return ExchangeBuilder
                .topicExchange(EXCHANGE_MSG)
                .durable(true)
                .build();
    }

    @Bean(QUEUE_MSG_LIKE)
    public Queue queueLike() {
        return new Queue(QUEUE_MSG_LIKE, true);
    }

    @Bean(QUEUE_MSG_COMMENT)
    public Queue queueCOMMENT() {
        return new Queue(QUEUE_MSG_COMMENT, true);
    }

    @Bean(QUEUE_MSG_FANS)
    public Queue queueFANS() {
        return new Queue(QUEUE_MSG_FANS, true);
    }

    @Bean
    public Binding bindingFANS(@Qualifier(EXCHANGE_MSG) Exchange exchange,
                           @Qualifier(QUEUE_MSG_FANS) Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("msg_fans")
                .noargs();
    }

    @Bean
    public Binding bindingCOMMENT(@Qualifier(EXCHANGE_MSG) Exchange exchange,
                           @Qualifier(QUEUE_MSG_COMMENT) Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("msg_comment")
                .noargs();
    }

    @Bean
    public Binding bindingLIKE(@Qualifier(EXCHANGE_MSG) Exchange exchange,
                           @Qualifier(QUEUE_MSG_LIKE) Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("msg_like")
                .noargs();
    }
}
