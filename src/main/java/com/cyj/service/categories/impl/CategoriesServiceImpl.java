package com.cyj.service.categories.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyj.mapper.CategoriesMapper;
import com.cyj.mapper.UserMapper;
import com.cyj.pojo.Categories;
import com.cyj.service.categories.CategoriesService;

import com.cyj.utils.JsonObject;
import com.cyj.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cyj
 * @since 2022-04-20
 */
@Service
public class CategoriesServiceImpl extends ServiceImpl<CategoriesMapper, Categories> implements CategoriesService {

    @Autowired
    CategoriesMapper categoriesMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public JsonObject<Categories> findCategories(String module) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("module" , module);
        List<Categories> categories=baseMapper.selectList(wrapper);
        JsonObject<Categories> jsonObject=new JsonObject<>();
        jsonObject.setCount((long) categories.size());
        jsonObject.setData(categories);
        return jsonObject;
    }

    @Override
    public int add(Categories categories){
        return baseMapper.insert(categories);
    }

    @Override
    public int delete(Long id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Categories categories){
        return baseMapper.updateById(categories);
    }

    @Override
    public Categories findById(Long id){
        return  baseMapper.selectById(id);
    }
}
