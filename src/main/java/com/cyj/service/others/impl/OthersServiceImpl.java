package com.cyj.service.others.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyj.mapper.OthersMapper;
import com.cyj.mapper.UserMapper;
import com.cyj.pojo.Others;
import com.cyj.service.others.OthersService;
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
public class OthersServiceImpl extends ServiceImpl<OthersMapper, Others> implements OthersService {

    @Autowired
    OthersMapper othersMapper;
    @Autowired
    UserMapper userMapper;


    /**
     * 标签列表
     * @param
     * @return
     */
    @Override
    public JsonObject<Others> queryOthersList() {

        List<Others> dtoList= othersMapper.selectList(null);
        JsonObject<Others> jsonObject =new JsonObject<>();
        jsonObject.setData(dtoList);
        jsonObject.setCount((long)dtoList.size());
        return jsonObject;
    }

    @Override
    public int add(Others others){
        others.setModule("like");
        others.setType("community");
        others.setValue(0l);
        others.setUserId(Long.valueOf(TokenUtil.getTokenUserId()));
        return baseMapper.insert(others);
    }

    @Override
    public int delete(Long id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Others others){
        return baseMapper.updateById(others);
    }

    @Override
    public int updateData(Long adoptId,Long value) {
        QueryWrapper wrapper=new QueryWrapper();
        String userId=TokenUtil.getTokenUserId();
        wrapper.eq("userId",userId);
        wrapper.eq("module","adopt");
        wrapper.eq("type","star");
        wrapper.eq("postId",adoptId);
        Others others=baseMapper.selectOne(wrapper);
        others.setValue(value);
        return baseMapper.updateById(others);
    }

    @Override
    public int likePost(Long postId, Long like) {
        System.out.println("like="+like);
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("userId",TokenUtil.getTokenUserId());
        wrapper.eq("postId",postId);
        Others others=othersMapper.selectOne(wrapper);
        if(like==1){
            others.setValue(1l);
        }else{
            others.setValue(0l);
        }
        return baseMapper.updateById(others);
    }

    @Override
    public Others findById(Long id){
        return  baseMapper.selectById(id);
    }


}
