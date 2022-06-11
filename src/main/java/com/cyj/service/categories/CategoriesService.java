package com.cyj.service.categories;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyj.pojo.Categories;
import com.cyj.utils.JsonObject;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyj
 * @since 2022-04-20
 */
public interface CategoriesService extends IService<Categories> {



    JsonObject<Categories> findCategories(String module);
    /**
     * 添加
     *
     * @param categories 
     * @return int
     */
    int add(Categories categories);

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
     * @param categories 
     * @return int
     */
    int updateData(Categories categories);

    /**
     * id查询数据
     *
     * @param id id
     * @return Categories
     */
    Categories findById(Long id);
}
