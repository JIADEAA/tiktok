package com.jiade.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsersVO {
    private String id;
    private String mobile;
    private String nickname;
    private String imoocNum;
    private String face;
    private Integer sex;
    private Date birthday;
    private String country;
    private String province;
    private String city;
    private String district;
    private String description;
    private String bgImg;
    private Integer canImoocNumBeUpdated;
    @JsonFormat(locale = "zh", timezone = "Asia/Shanghai", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    @JsonFormat(locale = "zh", timezone = "Asia/Shanghai", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

    private String userToken;       // 用户token，传递给前端

    private Integer myFollowsCounts;
    private Integer myFansCounts;
//    private Integer myLikedVlogCounts;
    private Integer totalLikeMeCounts;

}