package com.cyj.controller;


import com.cyj.annotation.UserLoginToken;
import com.cyj.dto.UserDto;
import com.cyj.pojo.Logs;
import com.cyj.pojo.User;
import com.cyj.service.logs.LogsService;
import com.cyj.service.token.TokenService;
import com.cyj.service.user.UserService;
import com.cyj.utils.*;
import com.cyj.utils.constants.LogConstants;
import com.cyj.utils.constants.UserConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Api(tags = {"登录注册"})
@RestController

@RequestMapping("/login")
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    @Resource
    private LogsService logsService;

    @ApiOperation(value = "登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "手机号码"),
            @ApiImplicitParam(name = "password", value = "密码"),
    })
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Object login(String tel, String password, HttpServletResponse response, HttpServletRequest request) {
        ObjectData objectData = new ObjectData();
        try {
            User userForBase = userService.queryUserByTel(tel);
            //检查用户是否存在
            if (userForBase == null) {
                objectData.setData(UserConstants.NullUser);
                objectData.setCode(Constants.FAIL_CODE);
            } else {
                //检查密码
                if (!Encrypt.md5AndSha(password).equals(userService.checkPwd(userForBase.getId()))) {
                    objectData.setData(UserConstants.WrongPassword);
                    objectData.setCode(Constants.FAIL_CODE);
                    return objectData;
                }
                //用户存在，获取token
                String token = tokenService.getToken(userForBase);
                Map map = new HashMap();
                map.put("token", token);
                Cookie cookie = new Cookie("token", token);
                cookie.setPath("/");
                response.addCookie(cookie);
                //返回用户信息
                UserDto returnUser = userService.findById(userForBase.getId());
                map.put("user", returnUser);
                //记录日志
                logsService.add(new Logs(LogConstants.LoginLog, returnUser.getId(), GetIPAddress.getIpAddress(request)));
                objectData.setData(map);
                objectData.setCode(Constants.OK_CODE);
                objectData.setMsg(Constants.OK_MSG);
            }
        } catch (Exception e) {
            e.printStackTrace();
            objectData.setCode(Constants.FAIL_CODE);
            objectData.setMsg(Constants.FAIL_MSG);

        }
        return objectData;
    }

    @ApiOperation("注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "手机号码"),
            @ApiImplicitParam(name = "password", value = "密码"),
    })
    @PostMapping("/register")
    public R register(String username,String tel, String password) {
        //校验手机号码
        if (!MobileUtil.checkPhone(tel)) {
            return R.fail(UserConstants.WrongMobileFormat);
        }
        //检查用户是否存在
        if(userService.queryUserByTel(tel)!=null){
            return R.fail(UserConstants.HasUser);
        }
        //添加用户
        try {
            User user = new User(username, Encrypt.md5AndSha(password),tel);
            if( userService.add(user)==1){
                return R.ok();
            }else {
                return R.fail(Constants.FAIL_MSG);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail(Constants.FAIL_MSG);
        }
    }

    @UserLoginToken
    @ApiOperation(value = "测试用token是否能获取信息")
    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    public String getMessage() {
        System.out.println(TokenUtil.getTokenUserId());
        return "您已经通过验证";
    }

}
