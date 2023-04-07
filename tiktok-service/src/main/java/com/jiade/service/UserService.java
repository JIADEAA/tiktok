package com.jiade.service;

import com.jiade.bo.UpdatedUserBO;
import com.jiade.pojo.Users;

/**
 * @author: JIADE
 * @description: UserService
 * @date: 2023/4/6 16:45
 **/
public interface UserService {

    /**
     * 判断用户是否存在，如果存在则返回用户信息
     */
     Users queryMobileIsExist(String mobile);


    /**
     * 创建用户信息，并且返回用户对象
     */
     Users createUser(String mobile);

    /**
     * 根据用户主键查询用户信息
     */
     Users getUser(String userId);

    /**
     * 用户信息修改
     */
     Users updateUserInfo(UpdatedUserBO updatedUserBO);

    /**
     * 用户信息修改
     */
     Users updateUserInfo(UpdatedUserBO updatedUserBO, Integer type);
}
