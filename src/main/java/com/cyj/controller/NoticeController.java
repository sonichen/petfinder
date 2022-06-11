package com.cyj.controller;

import com.cyj.annotation.UserLoginToken;
import com.cyj.dto.NoticeDto;
import com.cyj.dto.NoticeQueryDto;
import com.cyj.pojo.Notice;
import com.cyj.service.notice.NoticeService;
import com.cyj.utils.Constants;
import com.cyj.utils.JsonObject;
import com.cyj.utils.ObjectData;
import com.cyj.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cyj
 * @since 2022-05-09
 */
@Api(tags = {"公告管理"})
@RestController

@CrossOrigin
@RequestMapping("/notice")
public class NoticeController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private NoticeService noticeService;

    @ApiOperation(value = "查询公告",notes = "显示全部时不用传参数")
    @PostMapping("/findNotice")
    public JsonObject<NoticeDto> findNotice(NoticeQueryDto noticeQueryDto){
        JsonObject jsonObject=null;
        try{
            jsonObject=noticeService.findNotice(noticeQueryDto);
            jsonObject.setCode(Constants.OK_CODE);
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.setCode(Constants.FAIL_CODE);
        }
        return jsonObject;
    }
    @ApiOperation(value = "查询公告ById",notes = "")
    @PostMapping("/findById")
    public ObjectData findById(Long id){
        ObjectData objectData=new ObjectData();
        try{
            objectData.setData(noticeService.findById(id));
            objectData.setCode(Constants.OK_CODE);
        }catch (Exception e){
            e.printStackTrace();
            objectData.setCode(Constants.FAIL_CODE);
        }
        return objectData;
    }

    @ApiOperation(value = "新增公告",notes = "填入categoriesId,title,content")
    @UserLoginToken
    @PostMapping("/add")
    public R add(@RequestBody Notice notice){
       try{
            noticeService.add(notice);
            return R.ok();
       }catch (Exception e){
           return R.fail(Constants.FAIL_MSG);
       }
    }

    @ApiOperation(value = "删除",notes = "公告id")
    @UserLoginToken
    @PostMapping("/delete")
    public R delete(Long id){
        try{
            noticeService.delete(id);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.fail(Constants.FAIL_MSG);
        }
    }

    @ApiOperation(value = "更新公告")
    @UserLoginToken
    @PostMapping("/update")
    public R update(@RequestBody Notice notice){
        try{
            noticeService.updateData(notice);
            return R.ok();
        }catch (Exception e){
            return R.fail(Constants.FAIL_MSG);
        }
    }


    @ApiOperation(value = "浏览量增加")
    @PostMapping("/click")
    public R click(@RequestBody Notice notice){
        try{
            noticeService.updateDataClick(notice);
            return R.ok();
        }catch (Exception e){
            return R.fail(Constants.FAIL_MSG);
        }
    }
    @UserLoginToken
    @ApiOperation(value = "点赞")
    @PostMapping("/like")
    public R like(@RequestBody Notice notice){
        try{
            noticeService.updateData(notice);
            return R.ok();
        }catch (Exception e){
            return R.fail(Constants.FAIL_MSG);
        }
    }
}
