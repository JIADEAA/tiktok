package com.jiade.mapper;

import com.jiade.my.mapper.MyMapper;
import com.jiade.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface        CommentMapper extends MyMapper<Comment> {
}