package com.jiade.service;

import com.jiade.bo.CommentBO;
import com.jiade.bo.VlogBO;
import com.jiade.pojo.Comment;
import com.jiade.pojo.Vlog;
import com.jiade.utils.PagedGridResult;
import com.jiade.vo.CommentVO;
import com.jiade.vo.IndexVlogVO;

/**
 * @author: JIADE
 * @description: VlogService
 * @date: 2023/4/6 16:46
 **/
public interface VlogService {


    /**
     * 新增vlog视频
     */
     void createVlog(VlogBO vlogBO);


    /**
     * 查询首页/搜索的vlog列表
     */
     PagedGridResult getIndexVlogList(String userId,
                                            String search,
                                            Integer page,
                                            Integer pageSize);

    /**
     * 根据视频主键查询vlog
     */
     IndexVlogVO getVlogDetailById(String userId, String vlogId);

    /**
     * 用户把视频改为公开/私密的视频
     */
     void changeToPrivateOrPublic(String userId,
                                        String vlogId,
                                        Integer yesOrNo);

    /**
     * 查询用的公开/私密的视频列表
     */
     PagedGridResult queryMyVlogList(String userId,
                                Integer page,
                                Integer pageSize,
                                Integer yesOrNo);

    /**
     * 用户点赞/喜欢视频
     */
     void userLikeVlog(String userId, String vlogId);

    /**
     * 用户取消点赞/喜欢视频
     */
     void userUnLikeVlog(String userId, String vlogId);

    /**
     * 获得用户点赞视频的总数
     */
     Integer getVlogBeLikedCounts(String vlogId);

    /**
     * 查询用户点赞过的短视频
     */
     PagedGridResult getMyLikedVlogList(String userId,
                                           Integer page,
                                           Integer pageSize);

    /**
     * 查询用户关注的博主发布的短视频列表
     */
     PagedGridResult getMyFollowVlogList(String myId,
                                              Integer page,
                                              Integer pageSize);

    /**
     * 查询朋友发布的短视频列表
     */
     PagedGridResult getMyFriendVlogList(String myId,
                                               Integer page,
                                               Integer pageSize);

    /**
     * 根据主键查询vlog
     */
     Vlog getVlog(String id);
}
