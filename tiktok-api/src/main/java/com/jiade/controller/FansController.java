package com.jiade.controller;

import com.jiade.base.BaseInfoProperties;
import com.jiade.result.GraceJSONResult;
import com.jiade.result.ResponseStatusEnum;
import com.jiade.pojo.Users;
import com.jiade.service.FansService;
import com.jiade.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "FansController 粉丝相关业务功能的接口")
@RequestMapping("fans")
@RestController
public class FansController extends BaseInfoProperties {

    @Autowired
    private UserService userService;
    @Autowired
    private FansService fansService;

    @PostMapping("follow")
    public GraceJSONResult follow(@RequestParam String myId,
                                  @RequestParam String vlogerId) {

        // 判断两个id不能为空
        if (StringUtils.isBlank(myId) || StringUtils.isBlank(vlogerId)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.SYSTEM_ERROR);
        }

        // 判断当前用户，自己不能关注自己
        if (myId.equalsIgnoreCase(vlogerId)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.SYSTEM_RESPONSE_NO_INFO);
        }

        // 判断两个id对应的用户是否存在
        Users vloger = userService.getUser(vlogerId);
        Users myInfo = userService.getUser(myId);

        if (myInfo == null || vloger == null) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.SYSTEM_RESPONSE_NO_INFO);
        }

        // 保存粉丝关系到数据库
        fansService.doFollow(myId, vlogerId);

        /**
         *原本采用String维护粉丝关注现在采用ZSET，加入关注时间
         // 博主的粉丝+1，我的关注+1
         redis.increment(REDIS_MY_FOLLOWS_COUNTS + ":" + myId, 1);
         redis.increment(REDIS_MY_FANS_COUNTS + ":" + vlogerId, 1);

         // 我和博主的关联关系，依赖redis，不要存储数据库，避免db的性能瓶颈
         redis.set(REDIS_FANS_AND_VLOGGER_RELATIONSHIP + ":" + myId + ":" + vlogerId, "1");
         */
        redis.zAdd(FOLLOWS + ":" + myId, vlogerId);
        redis.zAdd(FANS + ":" + vlogerId, myId);

        return GraceJSONResult.ok();
    }

    @PostMapping("cancel")
    public GraceJSONResult cancel(@RequestParam String myId,
                                  @RequestParam String vlogerId) {

        // 删除业务的执行
        fansService.doCancel(myId, vlogerId);
        /**
         *原本采用String维护粉丝关注现在采用ZSET，加入关注时间
         // 博主的粉丝-1，我的关注-1
         redis.decrement(REDIS_MY_FOLLOWS_COUNTS + ":" + myId, 1);
         redis.decrement(REDIS_MY_FANS_COUNTS + ":" + vlogerId, 1);

         // 我和博主的关联关系，依赖redis，不要存储数据库，避免db的性能瓶颈
         redis.del(REDIS_FANS_AND_VLOGGER_RELATIONSHIP + ":" + myId + ":" + vlogerId);
         */
        redis.zRemove(FOLLOWS + ":" + myId, vlogerId);
        redis.zRemove(FANS + ":" + vlogerId, myId);

        return GraceJSONResult.ok();
    }

    @GetMapping("queryDoIFollowVloger")
    public GraceJSONResult queryDoIFollowVloger(@RequestParam String myId,
                                                @RequestParam String vlogerId) {
        return GraceJSONResult.ok(fansService.queryDoIFollowVloger(myId, vlogerId));
    }

    @GetMapping("queryMyFollows")
    public GraceJSONResult queryMyFollows(@RequestParam String myId,
                                          @RequestParam Integer page,
                                          @RequestParam Integer pageSize) {
        return GraceJSONResult.ok(
                fansService.queryMyFollows(
                        myId,
                        page,
                        pageSize));
    }

    @GetMapping("queryMyFans")
    public GraceJSONResult queryMyFans(@RequestParam String myId,
                                       @RequestParam Integer page,
                                       @RequestParam Integer pageSize) {
        return GraceJSONResult.ok(
                fansService.queryMyFans(
                        myId,
                        page,
                        pageSize));
    }
}
