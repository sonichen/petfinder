package com.cyj.service.others;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyj.pojo.Others;
import com.cyj.utils.JsonObject;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyj
 * @since 2022-04-20
 */
public interface OthersService extends IService<Others> {


    /**
     * 标签列表
     * @param
     * @return
     */
    JsonObject<Others> queryOthersList( );

    /**
     * 添加
     *
     * @param others 
     * @return int
     */
    int add(Others others);

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
     * @param others 
     * @return int
     */
    int updateData(Others others);
    int updateData( Long adoptId,Long value);
    int likePost(Long postId,Long like );
    /**
     * id查询数据
     *
     * @param id id
     * @return Others
     */
    Others findById(Long id);
}
