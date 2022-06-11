package com.cyj.controller;

import com.cyj.annotation.UserLoginToken;
import com.cyj.dto.UserDto;
import com.cyj.dto.user.UserSearchDto;
import com.cyj.mapper.UserMapper;
import com.cyj.pojo.Logs;
import com.cyj.service.logs.LogsService;
import com.cyj.service.user.UserService;
import com.cyj.utils.*;
import com.cyj.utils.constants.LogConstants;
import com.cyj.utils.constants.UserConstants;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyj.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author cyj
 * @since 2022-04-18
 */
@CrossOrigin
@Api(tags = {"用户管理"})
@RestController
public class UserController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private UserService userService;
    @Resource
    private LogsService logsService;


    @ApiOperation(value = "实名认证")
    public R checkInfo(){
        try{
            return R.ok();
        }catch (Exception e){
            return R.fail(Constants.FAIL_MSG);
        }
    }
    @UserLoginToken
    @ApiOperation(value = "更新密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPwd", value = "旧密码"),
            @ApiImplicitParam(name = "newPwd", value = "新密码"),
            @ApiImplicitParam(name = "checkNewPwd", value = "再次输入新密码")
    })
    @PostMapping("/updatePwd")
    public R updatePwd(String oldPwd, String newPwd, String checkNewPwd, HttpServletRequest request) {
        System.out.println("oldPwd="+oldPwd);
        System.out.println("newPwd="+oldPwd);
        System.out.println("checkNewPwd="+oldPwd);
//        检查两次输入的密码
        if (!newPwd.equals(checkNewPwd)) {
            return R.fail(UserConstants.WrongInputPwd);
        }
        try {
            long userId = Long.valueOf(TokenUtil.getTokenUserId());
            //校验密码
            if (!Encrypt.md5AndSha(oldPwd).equals(userService.checkPwd(userId))) {
                return R.fail(UserConstants.WrongPassword);
            }
            //修改密码
            if(  userService.updatePwd(userId, oldPwd, newPwd, checkNewPwd)!=1){
                return R.fail(Constants.FAIL_MSG);
            }
            logsService.add(new Logs(LogConstants.ChangePwdLog, Long.valueOf(TokenUtil.getTokenUserId()), GetIPAddress.getIpAddress(request)));
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail(Constants.FAIL_MSG);
        }
    }
    @UserLoginToken
    @ApiOperation(value = "更新个人信息", notes = "1，前端不能显示Id；2.创建时间和手机号码不允许修改，前端设置这两个禁止输入")
    @PostMapping("/update")
    public R update(@RequestBody UserDto user) {
        try {
            if(  userService.updateData(user)!=1){
                return R.fail(Constants.FAIL_MSG);
            }
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail(Constants.FAIL_MSG);
        }
    }
    @UserLoginToken
    @ApiOperation(value = "查询个人信息")
    @PostMapping("/information")
    public ObjectData information() {
        ObjectData objectData=new ObjectData();
        try {
            UserDto userDto=userService.findById(Long.valueOf(TokenUtil.getTokenUserId()));
            objectData.setData(userDto);
            objectData.setCode(Constants.OK_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            objectData.setCode(Constants.FAIL_CODE);
        }
        return objectData;
    }


    @UserLoginToken
    @ApiOperation(value = "上传头像", notes = "返回的地址请前端存入要修改的用户的avatar")
    @PostMapping("/user/upload")
    public ObjectData add(@RequestParam("file") MultipartFile file) throws Exception {
        ObjectData objectData = new ObjectData();
        File newFile = multipartFileToFile(file);
        try {
            String result = UploadUtils.upload(newFile);
            objectData.setData(result);
            objectData.setCode(Constants.OK_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            objectData.setCode(Constants.FAIL_CODE);
        }
        return objectData;
    }

    public static File multipartFileToFile(@RequestParam MultipartFile file) throws Exception {

        java.io.File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @UserLoginToken
    @ApiOperation(value = "查看用户列表",notes = "搜索条件还没定，谢卓补充下")
    @PostMapping("/admin/findListByPage")
    public JsonObject<User> findListByPage(UserSearchDto condition){
        JsonObject <User> jsonObject=null;
        try{
            jsonObject= userService.findListByPage(condition);
            jsonObject.setCode(Constants.OK_CODE);
            jsonObject.setMsg(Constants.OK_MSG);
        }catch (Exception e){
            jsonObject.setCode(Constants.FAIL_CODE);
            jsonObject.setMsg(Constants.FAIL_MSG);
        }
        return jsonObject;
    }


    @UserLoginToken
    @ApiOperation(value = "封锁用户账号")
    @PostMapping("/admin/blacklist")
    public R blacklist(Long userId){
        try{
            userService.delete(userId);
            return R.ok();
        }catch (Exception e){
            return R.fail(Constants.FAIL_MSG);
        }
    }
    @UserLoginToken
    @ApiOperation(value = "解封用户")
    @PostMapping("/admin/normal")
    public R normal(Long userId){
        try{
            userService.changeToNormal(userId);
            return R.ok();
        }catch (Exception e){
            return R.fail(Constants.FAIL_MSG);
        }
    }
}
