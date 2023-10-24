package com.jiade.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@Slf4j
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_MSG = "exchange_msg";

    public static final String QUEUE_MSG_LIKE = "queue_msg_like";
    public static final String QUEUE_MSG_COMMENT = "queue_msg_comment";
    public static final String QUEUE_MSG_FANS = "queue_msg_fans";

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);

        //设置消息投递失败的策略，有两种策略：自动删除或返回到客户端。
        //true是返回客户端，false是自动删除
        rabbitTemplate.setMandatory(true);
        // 消息只要被rabbit broker接收到就会执行confirmCallback
        // 被broker执行只能保证消息到达服务器，并不能保证一定被投递到目标queue里
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if (ack) {
                    log.info("ConfirmCallback 关联数据：{},投递成功,确认情况：{}", correlationData, ack);
                } else {
                    log.info("ConfirmCallback 关联数据：{},投递失败,确认情况：{}，原因：{}", correlationData, ack, cause);
                }
            }
        });

        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                // 请注意!如果你使用了延迟队列插件，那么一定会调用该callback方法，因为数据并没有提交上去，
                // 而是提交在交换器中，过期时间到了才提交上去，并非是bug！你可以用if进行判断交换机名称来捕捉该报错

                log.info("ReturnsCallback 消息：{},回应码：{},回应信息：{},交换机：{},路由键：{}"
                        , returnedMessage.getMessage(), returnedMessage.getReplyCode()
                        , returnedMessage.getReplyText(), returnedMessage.getExchange()
                        , returnedMessage.getRoutingKey());
            }
        });

        return rabbitTemplate;
    }


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
