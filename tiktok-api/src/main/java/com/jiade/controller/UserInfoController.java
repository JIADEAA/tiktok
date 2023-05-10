package com.jiade.controller;


import com.jiade.base.BaseInfoProperties;
import com.jiade.bo.UpdatedUserBO;
import com.jiade.config.MinIOConfig;
import com.jiade.enums.FileTypeEnum;
import com.jiade.enums.UserInfoModifyType;
import com.jiade.pojo.Users;
import com.jiade.result.GraceJSONResult;
import com.jiade.result.ResponseStatusEnum;
import com.jiade.service.UserService;
import com.jiade.utils.MinIOUtils;
import com.jiade.vo.UsersVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Api(tags = "UserInfoController 用户信息接口模块")
@RequestMapping("userInfo")
@RestController
public class UserInfoController extends BaseInfoProperties {

    @Autowired
    private UserService userService;

    @GetMapping("query")
    public GraceJSONResult query(@RequestParam String userId) throws Exception {

        Users user = userService.getUser(userId);

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(user, usersVO);


        /**
         *
         // 我的关注博主总数量
         String myFollowsCountsStr = redis.get(REDIS_MY_FOLLOWS_COUNTS + ":" + userId);
         // 我的粉丝总数
         String myFansCountsStr = redis.get(REDIS_MY_FANS_COUNTS + ":" + userId);
         // 用户获赞总数，视频博主（点赞/喜欢）总和
         String likedVlogCountsStr = redis.get(REDIS_VLOG_BE_LIKED_COUNTS + ":" + userId);
         String likedVlogerCountsStr = redis.get(REDIS_VLOGER_BE_LIKED_COUNTS + ":" + userId);
         Integer myFollowsCounts = 0;
         Integer myFansCounts = 0;
         Integer likedVlogCounts = 0;
         Integer likedVlogerCounts = 0;
         Integer totalLikeMeCounts = 0;

         if (StringUtils.isNotBlank(myFollowsCountsStr)) {
         myFollowsCounts = Integer.valueOf(myFollowsCountsStr);
         }
         if (StringUtils.isNotBlank(myFansCountsStr)) {
         myFansCounts = Integer.valueOf(myFansCountsStr);
         }
         //        if (StringUtils.isNotBlank(likedVlogCountsStr)) {
         //            likedVlogCounts = Integer.valueOf(likedVlogCountsStr);
         //        }
         if (StringUtils.isNotBlank(likedVlogerCountsStr)) {
         likedVlogerCounts = Integer.valueOf(likedVlogerCountsStr);
         }
         *
         */
        Integer myFollowsCounts = 0;
        Integer myFansCounts = 0;
        Integer totalLikeMeCounts = 0;
        Long myFollowsCountLong = redis.zCard(FOLLOWS + ":" + userId);
        Long myFansCountLong = redis.zCard(FANS + ":" + userId);
        String likedVloggerCount = redis.get(VLOGGER_BE_LIKED_COUNTS + ":" + userId);

        if (myFollowsCountLong != null) {
            myFollowsCounts = Integer.valueOf(myFollowsCountLong.toString());
        }

        if (myFansCountLong != null) {
            myFansCounts = Integer.valueOf(myFansCountLong.toString());
        }

        if (StringUtils.isNotBlank(likedVloggerCount)) {
            totalLikeMeCounts = Integer.valueOf(likedVloggerCount);
        }
        usersVO.setMyFollowsCounts(myFollowsCounts);
        usersVO.setMyFansCounts(myFansCounts);
        usersVO.setTotalLikeMeCounts(totalLikeMeCounts);

        return GraceJSONResult.ok(usersVO);
    }

    @PostMapping("modifyUserInfo")
    public GraceJSONResult modifyUserInfo(@RequestBody UpdatedUserBO updatedUserBO,
                                          @RequestParam Integer type)
            throws Exception {

        UserInfoModifyType.checkUserInfoTypeIsRight(type);

        Users newUserInfo = userService.updateUserInfo(updatedUserBO, type);

        return GraceJSONResult.ok(newUserInfo);
    }

    @Autowired
    private MinIOConfig minIOConfig;

    @PostMapping("modifyImage")
    public GraceJSONResult modifyImage(@RequestParam String userId,
                                       @RequestParam Integer type,
                                       MultipartFile file) throws Exception {

        if (type != FileTypeEnum.BGIMG.type && type != FileTypeEnum.FACE.type) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.FILE_UPLOAD_FAILD);
        }

        String fileName = file.getOriginalFilename();

        MinIOUtils.uploadFile(minIOConfig.getBucketName(),
                fileName,
                file.getInputStream());

        String imgUrl = minIOConfig.getFileHost()
                + "/"
                + minIOConfig.getBucketName()
                + "/"
                + fileName;


        // 修改图片地址到数据库
        UpdatedUserBO updatedUserBO = new UpdatedUserBO();
        updatedUserBO.setId(userId);

        if (type == FileTypeEnum.BGIMG.type) {
            updatedUserBO.setBgImg(imgUrl);
        } else {
            updatedUserBO.setFace(imgUrl);
        }
        Users users = userService.updateUserInfo(updatedUserBO);

        return GraceJSONResult.ok(users);
    }
}
