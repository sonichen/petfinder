package com.cyj.mapper;

import com.cyj.pojo.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cyj
 * @since 2022-06-04
 */
@Repository
public interface PostMapper extends BaseMapper<Post> {
        List<Post> queryByKeywords(String keyword);
}
