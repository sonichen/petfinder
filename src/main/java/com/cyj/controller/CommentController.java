package com.cyj.controller;

import com.cyj.annotation.UserLoginToken;
import com.cyj.dto.community.comment.CommentDto;
import com.cyj.pojo.Comment;
import com.cyj.service.comment.CommentService;
import com.cyj.utils.Constants;
import com.cyj.utils.JsonObject;
import com.cyj.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
@CrossOrigin
@Api(tags = {"评论"})
@RestController
//@UserLoginToken
@RequestMapping("/comment")
public class CommentController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private CommentService commentService;

    @ApiOperation(value = "查询某个帖子评论")
    @PostMapping("/findComment")
    public JsonObject<CommentDto> findComment(String postId){
        JsonObject jsonObject=null;
        try{
            jsonObject=commentService.queryCommentList(postId);
            jsonObject.setCode(Constants.OK_CODE);
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.setCode(Constants.FAIL_CODE);
        }
        return jsonObject;
    }

    @ApiOperation(value = "新增评论",notes = "填入postId和content")
    @UserLoginToken
    @PostMapping("/add")
    public R add(@RequestBody Comment comment){
       try{
            commentService.add(comment);
            return R.ok();
       }catch (Exception e){
           return R.fail(Constants.FAIL_MSG);
       }
    }

    @ApiOperation(value = "删除")
    @UserLoginToken
    @PostMapping("/delete")
    public R delete( Long id){
        System.out.println("要删除="+id);
        try{
            commentService.delete(id);
            return R.ok();
        }catch (Exception e){
            return R.fail(Constants.FAIL_MSG);
        }
    }

//    @ApiOperation(value = "更新信息")
//    @UserLoginToken
//    @PostMapping("/update")
//    public R update(@RequestBody Comment comment){
//        try{
//            commentService.updateData(comment);
//            return R.ok();
//        }catch (Exception e){
//            return R.fail(Constants.FAIL_MSG);
//        }
//    }


}
