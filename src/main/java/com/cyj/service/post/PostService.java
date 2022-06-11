package com.cyj.service.post;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyj.dto.community.post.PostDto;
import com.cyj.dto.community.post.PostQueryDto;
import com.cyj.pojo.Post;
import com.cyj.utils.JsonObject;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyj
 * @since 2022-04-20
 */
public interface PostService extends IService<Post> {

     JsonObject<Post> queryMyPost(PostQueryDto postQueryDto);
    JsonObject<Post> queryMyLikePostList(PostQueryDto postQueryDto);
    /**
     * 标签列表
     * @param
     * @return
     */
    JsonObject<Post> queryPostList(PostQueryDto postQueryDto);

    /**
     * 添加
     *
     * @param post 
     * @return int
     */
    int add(Post post);

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
     * @param post 
     * @return int
     */
    int updateData(Post post);
    public int addHit(Long id);
    /**
     * id查询数据
     *
     * @param id id
     * @return Post
     */
    PostDto findById(Long id);
}
