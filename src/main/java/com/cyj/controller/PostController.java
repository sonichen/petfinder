package com.cyj.controller;

import com.cyj.annotation.UserLoginToken;
import com.cyj.dto.community.post.PostQueryDto;
import com.cyj.pojo.Post;
import com.cyj.service.others.OthersService;
import com.cyj.service.post.PostService;
import com.cyj.utils.Constants;
import com.cyj.utils.JsonObject;
import com.cyj.utils.ObjectData;
import com.cyj.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cyj
 * @since 2022-05-09
 */
@Api(tags = {"社区"})
@RestController
@CrossOrigin
@RequestMapping("/post")
public class PostController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private PostService postService;
    @Autowired
private OthersService othersService;
    @ApiOperation(value = "查询我的发帖")
    @UserLoginToken
    @PostMapping("/queryMyPost")
    public JsonObject<Post> queryMyPost(PostQueryDto postQueryDto){
        JsonObject jsonObject=null;
        try{
            jsonObject=postService.queryMyPost(postQueryDto);
            jsonObject.setCode(Constants.OK_CODE);
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.setCode(Constants.FAIL_CODE);
        }
        return jsonObject;
    }
    @ApiOperation(value = "查询我的收藏")
    @UserLoginToken
    @PostMapping("/findMyLike")
    public JsonObject<Post> findMyLike(PostQueryDto postQueryDto){
        JsonObject jsonObject=null;
        try{
            jsonObject=postService.queryMyLikePostList(postQueryDto);
            jsonObject.setCode(Constants.OK_CODE);
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.setCode(Constants.FAIL_CODE);
        }
        return jsonObject;
    }
    @ApiOperation(value = "查询",notes = "page当前页面；pagesize页面显示得条数;categoriesId=-1查询全部；")
    @PostMapping("/findPost")
    public JsonObject<Post> findPost(PostQueryDto postQueryDto){
        JsonObject jsonObject=null;
        try{
            jsonObject=postService.queryPostList(postQueryDto);
            jsonObject.setCode(Constants.OK_CODE);
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.setCode(Constants.FAIL_CODE);
        }
        return jsonObject;
    }

    @ApiOperation(value = "发帖",notes = "填categoriesId，content就好")
    @UserLoginToken
    @PostMapping("/add")
    public R add(@RequestBody Post post){
       try{
            postService.add(post);
            return R.ok();
       }catch (Exception e){
           e.printStackTrace();
           return R.fail(Constants.FAIL_MSG);
       }
    }

    @ApiOperation(value = "删除")
    @UserLoginToken
    @PostMapping("/delete")
    public R delete( Long id){
        try{
            postService.delete(id);
            return R.ok();
        }catch (Exception e){
            return R.fail(Constants.FAIL_MSG);
        }
    }
//
    @ApiOperation(value = "增加点击量")
    @PostMapping("/hit")
    public R hit(Long postId){
        try{
            postService.addHit(postId);
            return R.ok();
        }catch (Exception e){
            return R.fail(Constants.FAIL_MSG);
        }

    }
    @ApiOperation(value = "点赞/取消",notes = "like:0-不赞；1-赞")
    @UserLoginToken
    @PostMapping("/like")
    public R hit(Long postId,Long like){
        try{
            othersService.likePost(postId,like);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.fail(Constants.FAIL_MSG);
        }
    }
    @ApiOperation(value = "查询ById",notes = "")
    @PostMapping("/findById")
    public ObjectData findById(Long id){
        ObjectData objectData=new ObjectData();
        try{
            objectData.setData(postService.findById(id));
            objectData.setCode(Constants.OK_CODE);
        }catch (Exception e){
            e.printStackTrace();
            objectData.setCode(Constants.FAIL_CODE);
        }
        return objectData;
    }

}
