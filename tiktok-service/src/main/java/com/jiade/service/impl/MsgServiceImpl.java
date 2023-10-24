package com.jiade.service.impl;

import com.jiade.base.BaseInfoProperties;
import com.jiade.enums.MessageEnum;
import com.jiade.mo.MessageMO;
import com.jiade.pojo.Users;
import com.jiade.repository.MessageRepository;
import com.jiade.service.MsgService;
import com.jiade.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.utils.SystemDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MsgServiceImpl extends BaseInfoProperties implements MsgService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Override
    public void createMsg(String fromUserId,
                          String toUserId,
                          Integer type,
                          Map msgContent) {

        Users fromUser = userService.getUser(fromUserId);

        MessageMO messageMO = new MessageMO();

        messageMO.setFromUserId(fromUserId);
        messageMO.setFromNickname(fromUser.getNickname());
        messageMO.setFromFace(fromUser.getFace());

        messageMO.setToUserId(toUserId);

        messageMO.setMsgType(type);
        if (msgContent != null) {
            messageMO.setMsgContent(msgContent);
        }
        log.info(SystemDateUtils.getStrDate());

        messageMO.setCreateTime(SystemDateUtils.getDaDate());

        messageRepository.save(messageMO);
    }

    @Override
    public List<MessageMO> queryList(String toUserId,
                                     Integer page,
                                     Integer pageSize) {

        Pageable pageable = PageRequest.of(page,
                pageSize,
                Sort.Direction.DESC,
                "createTime");

        List<MessageMO> list = messageRepository
                .findAllByToUserIdEqualsOrderByCreateTimeDesc(toUserId,
                        pageable);
        for (MessageMO msg : list) {
            // 如果类型是关注消息，则需要查询我之前有没有关注过他，用于在前端标记“互粉”“互关”
            if (msg.getMsgType() != null && msg.getMsgType() == MessageEnum.FOLLOW_YOU.type) {
                Map map = msg.getMsgContent();
                if (map == null) {
                    map = new HashMap();
                }

//                String relationship = redis.get(REDIS_FANS_AND_VLOGGER_RELATIONSHIP + ":" + msg.getToUserId() + ":" + msg.getFromUserId());
//                if (StringUtils.isNotBlank(relationship) && relationship.equalsIgnoreCase("1")) {
//                    map.put("isFriend", true);
//                } else {
//                    map.put("isFriend", false);
//                }
                Long ifFriend = redis.zRank(FOLLOWS + ":" + msg.getToUserId(), msg.getFromUserId());
                if (ifFriend == null) {
                    map.put("isFriend", false);
                } else map.put("isFriend", true);


                msg.setMsgContent(map);
            }
        }
        return list;
    }
}
