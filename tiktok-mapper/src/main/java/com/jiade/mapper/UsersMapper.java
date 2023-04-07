package com.jiade.mapper;

import com.jiade.my.mapper.MyMapper;
import com.jiade.pojo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface UsersMapper extends MyMapper<Users> {
}