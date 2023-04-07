package com.jiade.service;

import com.jiade.bo.CommentBO;
import com.jiade.pojo.Comment;
import com.jiade.utils.PagedGridResult;
import com.jiade.vo.CommentVO;

/**
 * @author: JIADE
 * @description: CommentService
 * @date: 2023/4/6 16:45
 **/
public interface CommentService {
     CommentVO createComment(CommentBO commentBO);

    /**
     * 查询评论的列表
     */
     PagedGridResult queryVlogComments(String vlogId,
                                             String userId,
                                             Integer page,
                                             Integer pageSize);

    /**
     * 删除评论
     */
     void deleteComment(String commentUserId,
                              String commentId,
                              String vlogId);

    /**
     * 根据主键查询comment
     */
     Comment getComment(String id);
}
