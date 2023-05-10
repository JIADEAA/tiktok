package com.jiade.config;

import com.jiade.service.MsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: JIADE
 * @description: RabbitMQConsumer
 * @date: 2023/5/9 10:03
 **/
@Component
@Slf4j
public class RabbitMQConsumer {
    @Autowired
    private MsgService msgService;

    @RabbitListener(queues = {RabbitMQConfig.QUEUE_MSG_FANS})
    public void watchQueueFANS(HashMap message) {
        log.info(message.toString());
        msgService.createMsg(message.remove("fromUserId").toString(),
                message.remove("toUserId").toString(),
                (Integer) message.remove("type"),
                message);
    }
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_MSG_LIKE})
    public void watchQueueLIKE(HashMap message) {
        log.info(message.toString());
        msgService.createMsg(message.remove("fromUserId").toString(),
                message.remove("toUserId").toString(),
                (Integer) message.remove("type"),
                message);
    }
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_MSG_COMMENT})
    public void watchQueueCOMMENT(HashMap message) {
        log.info(message.toString());
        msgService.createMsg(message.remove("fromUserId").toString(),
                message.remove("toUserId").toString(),
                (Integer) message.remove("type"),
                message);
    }
}
