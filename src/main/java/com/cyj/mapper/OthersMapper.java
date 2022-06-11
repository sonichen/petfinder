package com.cyj.mapper;

import com.cyj.pojo.Others;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cyj
 * @since 2022-06-04
 */
@Repository
public interface OthersMapper extends BaseMapper<Others> {
        int queryTotalLike(String postId);
        int queryMyLike(String postId,String userId);
}
