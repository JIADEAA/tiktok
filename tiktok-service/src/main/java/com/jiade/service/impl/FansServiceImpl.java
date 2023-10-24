package com.jiade.service.impl;

import com.github.pagehelper.PageHelper;
import com.jiade.base.BaseInfoProperties;
import com.jiade.enums.MessageEnum;
import com.jiade.enums.YesOrNo;
import com.jiade.mapper.FansMapper;
import com.jiade.mapper.FansMapperCustom;
import com.jiade.pojo.Fans;
import com.jiade.service.FansService;
import com.jiade.service.MsgService;
import com.jiade.utils.PagedGridResult;
import com.jiade.vo.FansVO;
import com.jiade.vo.VlogerVO;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FansServiceImpl extends BaseInfoProperties implements FansService {

    @Autowired
    private FansMapper fansMapper;
    @Autowired
    private FansMapperCustom fansMapperCustom;

    @Autowired
    private MsgService msgService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Sid sid;

    @Transactional
    @Override
    public void doFollow(String myId, String vlogerId) {

        String fid = sid.nextShort();

        Fans fans = new Fans();
        fans.setId(fid);
        fans.setFanId(myId);
        fans.setVlogerId(vlogerId);

        // 判断对方是否关注我，如果关注我，那么双方都要互为朋友关系
        Fans vloger = queryFansRelationship(vlogerId, myId);
        if (vloger != null) {
            fans.setIsFanFriendOfMine(YesOrNo.YES.type);

            vloger.setIsFanFriendOfMine(YesOrNo.YES.type);
            fansMapper.updateByPrimaryKeySelective(vloger);
        } else {
            fans.setIsFanFriendOfMine(YesOrNo.NO.type);
        }

        fansMapper.insert(fans);


//        // 系统消息：关注
//        msgService.createMsg(myId, vlogerId, MessageEnum.FOLLOW_YOU.type, null);
        Map msg = new HashMap();
        msg.put("fromUserId", myId);
        msg.put("toUserId", vlogerId);
        msg.put("type", MessageEnum.FOLLOW_YOU.type);
        rabbitTemplate.convertAndSend("exchange_msg", "msg_fans", msg);
    }

    public Fans queryFansRelationship(String fanId, String vlogerId) {
        Example example = new Example(Fans.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("vlogerId", vlogerId);
        criteria.andEqualTo("fanId", fanId);

        List list = fansMapper.selectByExample(example);

        Fans fan = null;
        if (list != null && list.size() > 0 && !list.isEmpty()) {
            fan = (Fans) list.get(0);
        }

        return fan;
    }

    @Transactional
    @Override
    public void doCancel(String myId, String vlogerId) {

        // 判断我们是否朋友关系，如果是，则需要取消双方的关系
        Fans fan = queryFansRelationship(myId, vlogerId);
        if (fan != null && fan.getIsFanFriendOfMine() == YesOrNo.YES.type) {
            // 抹除双方的朋友关系，自己的关系删除即可
            Fans pendingFan = queryFansRelationship(vlogerId, myId);
            pendingFan.setIsFanFriendOfMine(YesOrNo.NO.type);
            fansMapper.updateByPrimaryKeySelective(pendingFan);
        }

        // 删除自己的关注关联表记录
        fansMapper.delete(fan);
    }

    @Override
    public boolean queryDoIFollowVloger(String myId, String vlogerId) {
        Fans vloger = queryFansRelationship(myId, vlogerId);
        return vloger != null;
    }

    @Override
    public PagedGridResult queryMyFollows(String myId,
                                          Integer page,
                                          Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("myId", myId);

        PageHelper.startPage(page, pageSize);

        List<VlogerVO> list = fansMapperCustom.queryMyFollows(map);

        return setterPagedGrid(list, page);
    }

    @Override
    public PagedGridResult queryMyFans(String myId,
                                       Integer page,
                                       Integer pageSize) {




        Map<String, Object> map = new HashMap<>();
        map.put("myId", myId);

        PageHelper.startPage(page, pageSize);

        List<FansVO> list = fansMapperCustom.queryMyFans(map);

        for (FansVO f : list) {
//            String relationship = redis.get(REDIS_FANS_AND_VLOGGER_RELATIONSHIP + ":" + myId + ":" + f.getFanId());
            Long ifFriend = redis.zRank(FOLLOWS + ":" + myId, f.getFanId());
            if (ifFriend != null) {
                f.setFriend(true);
            }
        }

        return setterPagedGrid(list, page);
    }
}
