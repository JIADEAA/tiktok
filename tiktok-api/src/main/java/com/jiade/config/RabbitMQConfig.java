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

    public static final String QUEUE_SYS_MSG = "queue_sys_msg";

    @Bean(EXCHANGE_MSG)
    public Exchange exchange(){
        return ExchangeBuilder
                .topicExchange(EXCHANGE_MSG)
                .durable(true)
                .build();
    }
    @Bean(QUEUE_SYS_MSG)
    public Queue queue(){
        return new Queue(QUEUE_SYS_MSG, true);
    }

    @Bean
    public Binding binding(@Qualifier(EXCHANGE_MSG) Exchange exchange,
                           @Qualifier(QUEUE_SYS_MSG) Queue queue){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("sys_msg_*")
                .noargs();
    }
}