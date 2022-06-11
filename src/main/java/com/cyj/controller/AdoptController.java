package com.cyj.controller;

import com.cyj.annotation.UserLoginToken;;
import com.cyj.dto.AdoptQueryDto;
import com.cyj.pojo.Adopt;
import com.cyj.service.adopt.AdoptService;
import com.cyj.service.others.OthersService;
import com.cyj.utils.Constants;
import com.cyj.utils.JsonObject;
import com.cyj.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cyj
 * @since 2022-05-09
 */
@CrossOrigin
@Api(tags = {"领养"})
@RestController
@UserLoginToken
@RequestMapping("/adopt")
public class AdoptController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private AdoptService adoptService;
    @Resource
    private OthersService othersService;

    @ApiOperation(value = "收藏",notes = "0-不收藏，1-收藏")
    @PostMapping("/star")
    public R star(  Long adoptId,Long value){

        try{
            othersService.updateData(adoptId,value);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.fail(Constants.FAIL_MSG);
        }

    }

    @ApiOperation(value = "查询领养",notes = "deleted：0-进行中(前台)；1-结束;AdoptQueryDto对象是搜索条件,根据条件填写")
    @PostMapping("/findAdopt")
    public JsonObject<Adopt> findAdopt(  @RequestBody AdoptQueryDto adoptQueryDto ){
        JsonObject jsonObject=null;
        try{
            jsonObject=adoptService.findAdopt( adoptQueryDto);
            jsonObject.setCode(Constants.OK_CODE);
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.setCode(Constants.FAIL_CODE);
        }
        return jsonObject;
    }
    @ApiOperation(value = "查询我的收藏")
    @PostMapping("/queryMyLike")
    public JsonObject<Adopt> queryMyLike(   ){
        JsonObject jsonObject=null;
        try{
            jsonObject=adoptService.findMyStar( );
            jsonObject.setCode(Constants.OK_CODE);
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.setCode(Constants.FAIL_CODE);
        }
        return jsonObject;
    }

//    @ApiOperation(value = "查询用户收藏")
//    @PostMapping("/findMyStar")
//    public JsonObject<Adopt> findMyStar( ){
//        JsonObject jsonObject=null;
//        try{
//            jsonObject=adoptService.findMyStar();
//            jsonObject.setCode(Constants.OK_CODE);
//        }catch (Exception e){
//            e.printStackTrace();
//            jsonObject.setCode(Constants.FAIL_CODE);
//        }
//        return jsonObject;
//    }

    @ApiOperation(value = "发起收养" ,notes = "以下参数不用：createTime， deleted，updateTime,id,userId；categoriesId：10-狗；11猫；12其他")
    @UserLoginToken
    @PostMapping("/add")
    public R add(  Adopt adopt, MultipartFile[] files ){
        if(files.length==0){
            return R.fail("最少上传一张图片");
        }
       try{
            adoptService.add(adopt,files);
            return R.ok();
       }catch (Exception e){
           e.printStackTrace();
           return R.fail(Constants.FAIL_MSG);
       }
    }

    @ApiOperation(value = "结束领养")
    @UserLoginToken
    @PostMapping("/delete")
    public R delete( Long id){
        System.out.println("要删除="+id);
        try{
            adoptService.delete(id);
            return R.ok();
        }catch (Exception e){
            return R.fail(Constants.FAIL_MSG);
        }
    }

    @ApiOperation(value = "更新信息")
    @UserLoginToken
    @PostMapping("/update")
    public R update(@RequestBody Adopt adopt){
        try{
            adoptService.updateData(adopt);
            return R.ok();
        }catch (Exception e){
            return R.fail(Constants.FAIL_MSG);
        }
    }


}
