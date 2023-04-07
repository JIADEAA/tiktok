package com.jiade.service;

import com.jiade.mo.MessageMO;

import java.util.List;
import java.util.Map;

/**
 * @author: JIADE
 * @description: MsgService
 * @date: 2023/4/6 16:45
 **/
public interface MsgService {
      void createMsg(String fromUserId,
                          String toUserId,
                          Integer type,
                          Map msgContent);

    /**
     * 查询消息列表
     */
     List<MessageMO> queryList(String toUserId,
                                     Integer page,
                                     Integer pageSize);
}
