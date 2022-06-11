package com.cyj.service.comment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyj.dto.community.comment.CommentDto;
import com.cyj.pojo.Comment;
import com.cyj.utils.JsonObject;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyj
 * @since 2022-04-20
 */
public interface CommentService extends IService<Comment> {


    /**
     * 标签列表
     * @param
     * @return
     */
    JsonObject<CommentDto> queryCommentList(String postId);

    /**
     * 添加
     *
     * @param comment 
     * @return int
     */
    int add(Comment comment);

    /**
     * 删除
     *
     * @param id 主键
     * @return int
     */
    int delete(Long id);

    /**
     * 修改
     *
     * @param comment 
     * @return int
     */
    int updateData(Comment comment);

    /**
     * id查询数据
     *
     * @param id id
     * @return Comment
     */
    Comment findById(Long id);
}
