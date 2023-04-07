package com.jiade.mapper;

import com.jiade.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentMapperCustom {

    public List<CommentVO> getCommentList(@Param("paramMap") Map<String, Object> map);

}