package com.jiade.config;

import com.jiade.service.MsgService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;


@Component
@Slf4j
public class RabbitMQConsumer {
    @Autowired
    private MsgService msgService;

    @RabbitListener(queues = {RabbitMQConfig.QUEUE_MSG_FANS})
    public void watchQueueFANS(HashMap message, Channel channel, Message msg) throws IOException {


         try {
            log.info("ReceiverMessage 收到消息：{}", msg);


                msgService.createMsg(message.remove("fromUserId").toString(),
                message.remove("toUserId").toString(),
                (Integer) message.remove("type"),
                message);
            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
        }  catch (Exception e) {
            log.info("回调的相关数据:{} ack:{} cause:{} ");
            if (msg.getMessageProperties().getRedelivered()) {

                log.error("消息已重复处理失败,拒绝再次接收...");
                channel.basicReject(msg.getMessageProperties().getDeliveryTag(), false);
            } else {

                log.error("消息即将再次返回队列处理...");

                channel.basicNack(msg.getMessageProperties().getDeliveryTag(), false, true);
                throw new RuntimeException("自定义");
            }
        }

    }
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_MSG_LIKE})
    public void watchQueueLIKE(HashMap message, Channel channel,Message msg) throws IOException {

        try {
        log.info(message.toString());
        msgService.createMsg(message.remove("fromUserId").toString(),
                message.remove("toUserId").toString(),
                (Integer) message.remove("type"),
                message);

              channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
        }  catch (Exception e) {
            log.info("回调的相关数据:{} ack:{} cause:{} ");
            if (msg.getMessageProperties().getRedelivered()) {

                log.error("消息已重复处理失败,拒绝再次接收...");
                channel.basicReject(msg.getMessageProperties().getDeliveryTag(), false);
            } else {

                log.error("消息即将再次返回队列处理...");

                channel.basicNack(msg.getMessageProperties().getDeliveryTag(), false, true);
                throw new RuntimeException("自定义");
            }
        }

    }
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_MSG_COMMENT})
    public void watchQueueCOMMENT(HashMap message,Channel channel,Message msg) throws IOException {
        try {
        log.info(message.toString());
        msgService.createMsg(message.remove("fromUserId").toString(),
                message.remove("toUserId").toString(),
                (Integer) message.remove("type"),
                message);

              channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
        }  catch (Exception e) {
            log.info("回调的相关数据:{} ack:{} cause:{} ");
            if (msg.getMessageProperties().getRedelivered()) {

                log.error("消息已重复处理失败,拒绝再次接收...");
                channel.basicReject(msg.getMessageProperties().getDeliveryTag(), false);
            } else {

                log.error("消息即将再次返回队列处理...");

                channel.basicNack(msg.getMessageProperties().getDeliveryTag(), false, true);
                throw new RuntimeException("自定义");
            }
        }
    }
}
