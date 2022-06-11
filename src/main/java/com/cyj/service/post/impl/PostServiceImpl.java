package com.cyj.service.post.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyj.dto.community.post.PostDto;
import com.cyj.dto.community.post.PostQueryDto;
import com.cyj.mapper.CommentMapper;
import com.cyj.mapper.OthersMapper;
import com.cyj.mapper.PostMapper;
import com.cyj.mapper.UserMapper;
import com.cyj.pojo.Others;
import com.cyj.pojo.Post;
import com.cyj.pojo.User;
import com.cyj.service.comment.CommentService;
import com.cyj.service.others.OthersService;
import com.cyj.service.post.PostService;
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
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Autowired
    PostMapper postMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    OthersMapper othersMapper;
    @Autowired
    OthersService othersService;
    @Autowired
    CommentService commentService;


    public JsonObject<Post> queryMyLikePostList(PostQueryDto postQueryDto) {
        QueryWrapper wrapper=new QueryWrapper();
        List<Others> othersList= null;
        Page<Others> page=new Page<>(postQueryDto.getPage(),postQueryDto.getPageSize());
        wrapper.eq("userId",TokenUtil.getTokenUserId());
        wrapper.eq("module","community");
        wrapper.eq("value","1");
        othersMapper.selectPage(page,wrapper);
        othersList=page.getRecords();
        System.out.println("检测="+othersList);
        if(othersList.size()==0){
            return null;
        }
        ///////////////////////
        List result=new ArrayList();
        for(int i=0;i< othersList.size();i++){
            Post post=baseMapper.selectById(othersList.get(i).getPostId());
            System.out.println("Post");
            String username=userMapper.selectById(post.getUserId()).getUsername();
            int likeCount=othersMapper.queryTotalLike(post.getId()+"");
            Long myStar=(long)othersMapper.queryMyLike(post.getId()+"",TokenUtil.getTokenUserId());
            Long commentCount=commentService.queryCommentList(post.getId()+"").getCount();
            result.add(new PostDto(post.getId(),username,post.getContent(),(long)likeCount,post.getVersion()-1,post.getCreateTime(),commentCount,myStar));

        }
        JsonObject<Post> jsonObject =new JsonObject<>();
        jsonObject.setData(result);
        jsonObject.setCount((long)result.size());

        return jsonObject;
    }



    public JsonObject<Post> queryMyPost(PostQueryDto postQueryDto) {
        QueryWrapper wrapper=new QueryWrapper();
        List<Post> posts= null;
        Page<Post> page=new Page<>(postQueryDto.getPage(),postQueryDto.getPageSize());
        wrapper.eq("userId",TokenUtil.getTokenUserId());
            baseMapper.selectPage(page,wrapper);
            posts=page.getRecords();
        List result=new ArrayList();
        for(int i=0;i< posts.size();i++){
            Post post=posts.get(i);
            String username=userMapper.selectById(post.getUserId()).getUsername();
            int likeCount=othersMapper.queryTotalLike(post.getId()+"");
            Long myStar=(long)othersMapper.queryMyLike(post.getId()+"",TokenUtil.getTokenUserId());
            Long commentCount=commentService.queryCommentList(post.getId()+"").getCount();
            result.add(new PostDto(post.getId(),username,post.getContent(),(long)likeCount,post.getVersion()-1,post.getCreateTime(),commentCount,myStar));

        }
        JsonObject<Post> jsonObject =new JsonObject<>();
        jsonObject.setData(result);
        jsonObject.setCount((long)posts.size());

        return jsonObject;
    }

    /**
     * 标签列表
     * @param
     * @return
     */
    @Override
    public JsonObject<Post> queryPostList(PostQueryDto postQueryDto) {
        QueryWrapper wrapper=new QueryWrapper();
        List<Post> posts= null;
        Page<Post> page=new Page<>(postQueryDto.getPage(),postQueryDto.getPageSize());
        if(postQueryDto.getCategoriesId().equals("-1")){
            baseMapper.selectPage(page,null);
            posts=page.getRecords();
        }else{
//            wrapper.likeLeft("content",postQueryDto.getKeyword()+"%");
//            wrapper.likeRight("content","%"+postQueryDto.getKeyword());
            wrapper.eq("categoriesId",postQueryDto.getCategoriesId());
            baseMapper.selectPage(page,wrapper);
            posts=page.getRecords();
        }
        List result=new ArrayList();
        for(int i=0;i< posts.size();i++){
            Post post=posts.get(i);
            String username=userMapper.selectById(post.getUserId()).getUsername();
            int likeCount=othersMapper.queryTotalLike(post.getId()+"");
//            System.out.println("TokenUtil.getTokenUserId()="+TokenUtil.getTokenUserId());
            Long myStar=0l;
            try{
                myStar=(long)othersMapper.queryMyLike(post.getId()+"",TokenUtil.getTokenUserId());
            }catch (Exception e){
                myStar=0l;
            }
            Long commentCount=commentService.queryCommentList(post.getId()+"").getCount();
            result.add(new PostDto(post.getId(),username,post.getContent(),(long)likeCount,post.getVersion()-1,post.getCreateTime(),commentCount,myStar));

        }
        JsonObject<Post> jsonObject =new JsonObject<>();
        jsonObject.setData(result);
        jsonObject.setCount((long)posts.size());

        return jsonObject;
    }

    @Override
    public int add(Post post){
        post.setUserId(Long.valueOf(TokenUtil.getTokenUserId()));
       int result= baseMapper.insert(post);
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("content",post.getContent());
        wrapper.eq("userId",TokenUtil.getTokenUserId());
        Post post1=baseMapper.selectOne(wrapper);
        System.out.println("post1="+post1);
        List<User> list=userMapper.selectList(null);

        for(int i=0;i<list.size();i++){
            Others others=new Others();
            others.setUserId(list.get(i).getId());
            others.setValue(0l);
            others.setModule("community");
            others.setType("like");
            others.setPostId(post1.getId());
            othersMapper.insert(others);
        }
        return result;
    }

    @Override
    public int delete(Long id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Post post){
        return baseMapper.updateById(post);
    }

    @Override
    public int addHit(Long id){
        Post post=baseMapper.selectById(id);
        return baseMapper.updateById(post);
    }

    @Override
    public PostDto findById(Long id){
       Post post=    baseMapper.selectById(id);
//        List result=new ArrayList();
//        for(int i=0;i< othersList.size();i++){
//            Post post=baseMapper.selectById(othersList.get(i).getPostId());
            String username=userMapper.selectById(post.getUserId()).getUsername();
            int likeCount=othersMapper.queryTotalLike(post.getId()+"");
          Long myStar=0l;
           try{
                 myStar=(long)othersMapper.queryMyLike(post.getId()+"",TokenUtil.getTokenUserId());
           }catch (Exception e){
                 myStar=0l;
           }
            Long commentCount=commentService.queryCommentList(post.getId()+"").getCount();
            PostDto postDto=(new PostDto(post.getId(),username,post.getContent(),(long)likeCount,post.getVersion()-1,post.getCreateTime(),commentCount,myStar));
return postDto;
//        }
//        JsonObject<Post> jsonObject =new JsonObject<>();
//        jsonObject.setData(postDto);
//        jsonObject.setCount((long)result.size());

    }
}
