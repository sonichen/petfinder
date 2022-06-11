package com.cyj.service.comment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyj.dto.community.comment.CommentDto;
import com.cyj.mapper.CommentMapper;
import com.cyj.mapper.UserMapper;
import com.cyj.pojo.Comment;
import com.cyj.service.comment.CommentService;
import com.cyj.utils.JsonObject;
import com.cyj.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserMapper userMapper;
    /**
     * 标签列表
     * @param
     * @return
     */
    @Override
    public JsonObject<CommentDto> queryCommentList(String postId) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("postId", postId);
        wrapper.orderByDesc("id");
        List<Comment> dtoList= commentMapper.selectList(wrapper);
        List<CommentDto> result=new ArrayList<>();
        for(int i=0;i<dtoList.size();i++){
            Comment temp=dtoList.get(i);
            System.out.println(temp.getUserId());
            String username=userMapper.selectById(temp.getUserId()).getUsername();

            result.add(new CommentDto(temp.getId(),temp.getContent(),username
                    ,temp.getCreateTime()));
        }
        JsonObject<CommentDto> jsonObject =new JsonObject<>();
        jsonObject.setData(result);
        jsonObject.setCount((long)dtoList.size());
        return jsonObject;
    }

    @Override
    public int add(Comment comment){
        comment.setUserId(Long.valueOf(TokenUtil.getTokenUserId()));
        return baseMapper.insert(comment);
    }

    @Override
    public int delete(Long id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Comment comment){
        return baseMapper.updateById(comment);
    }

    @Override
    public Comment findById(Long id){
        return  baseMapper.selectById(id);
    }
}
