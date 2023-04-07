package com.jiade.repository;

import com.jiade.mo.MessageMO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface MessageRepository extends MongoRepository<MessageMO, String> {

    // 通过实现Repository，自定义条件查询
    List<MessageMO> findAllByToUserIdEqualsOrderByCreateTimeDesc(String toUserId,
                                                           Pageable pageable);
//    void deleteAllByFromUserIdAndToUserIdAndMsgType();
}
