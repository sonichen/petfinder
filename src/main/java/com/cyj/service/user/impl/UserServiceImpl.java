package com.cyj.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyj.dto.UserDto;
import com.cyj.dto.user.UserSearchDto;
import com.cyj.pojo.User;
import com.cyj.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyj.service.user.UserService;
import com.cyj.utils.Encrypt;
import com.cyj.utils.JsonObject;
import com.cyj.utils.SnowflakeIdUtils;
import com.cyj.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cyj
 * @since 2022-04-18
 */
@Service

public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;



    /**
     * 修改密码
     * @param userId
     * @param oldPwd
     * @param password
     * @param checkNewPwd
     * @return
     * @throws Exception
     */
    @Override
    public int updatePwd(long userId, String oldPwd, String password, String checkNewPwd) throws Exception {
        User user=baseMapper.selectById(userId);
        user.setPassword(Encrypt.md5AndSha(password));
        return baseMapper.updateById(user);
    }

    /**
     * 通过手机号码查询用户
     * @param username
     * @return
     */
    @Override
    public User queryUserByTel(String username) {
        return userMapper.queryUserByTel(username);
    }

    @Override
    public JsonObject<User> findListByPage(UserSearchDto condition) {
        return null;
    }

    /**
     * 查询用户列表
     * @param page
     * @param pageCount
     * @return
     */

    public JsonObject<User> findListByPage(Integer page, Integer pageCount){
        JsonObject<User> jsonObject =new JsonObject<>();
        IPage<User> wherePage = new Page<>(page, pageCount);
        userMapper.selectPage(wherePage,null);
        jsonObject.setData(wherePage.getRecords());
        jsonObject.setCount(wherePage.getTotal());
        return jsonObject;
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public int add(User user){
        if(user.getUsername()==null){
            long n = (long)(Math.random() % 29) + 1;
            long n1 = (long)(Math.random() % 29) + 1;
            user.setUsername((new SnowflakeIdUtils(n,n1).nextId())+"");//默认用户名
        }
        user.setAvatar("https://img1.baidu.com/it/u=4117401193,3509866561&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500");//默认头像
        return baseMapper.insert(user);
    }

    /**
     * 删除
     * @param id 主键
     * @return
     */
    @Override
    public int delete(Long id){
        return baseMapper.deleteById(id);
    }

    /**
     * 更新数据
     * @param user
     * @return
     */
    @Override
    public int updateData(UserDto user){
        User newUser=  new User();
        newUser.setId(Long.valueOf(TokenUtil.getTokenUserId()));
        newUser.setAvatar(user.getAvatar());
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setIntroduce(user.getIntroduce());
        newUser.setLocation(user.getLocation());
//        newUser.setb
        return baseMapper.updateById(newUser);
    }

    /**
     * 返回用户个人信息
     * @param id id
     * @return
     */
    @Override
    public UserDto findById(Long id){
        User user=  baseMapper.selectById(id);
        UserDto userDto=new UserDto();
        userDto.setId(id);
        userDto.setAvatar(user.getAvatar());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setTel(user.getTel());
        userDto.setCreateTime(user.getCreateTime());
        userDto.setIntroduce(user.getIntroduce());
        return userDto;
    }

    /**
     * 通过Id查询用户
     * @param id
     * @return
     */
    @Override
    public User findUser(Long id){
        return baseMapper.selectById(id);
    }

//
///////////////////////////////
    @Override
    public int changeToNormal(Long userId) {
       return userMapper.changeToNormal(userId);
    }

    @Override
    public String checkPwd(Long id) {
        return userMapper.checkPwd(id);
    }

//    /**
//     * 修改密码
//     * @param userId
//     * @param oldPwd
//     * @param password
//     * @param checkNewPwd
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public int updatePwd(long userId, String oldPwd, String password, String checkNewPwd) throws Exception {
//        User user=baseMapper.selectById(userId);
//        user.setPassword(Encrypt.md5AndSha(password));
//        return baseMapper.updateById(user);
//    }
//
//    /**
//     * 通过手机号码查询用户
//     * @param username
//     * @return
//     */
//    @Override
//    public User queryUserByTel(String username) {
//        return userMapper.queryUserByTel(username);
//    }
//
//    /**
//     * 查询用户列表
//     * @param page
//     * @param pageCount
//     * @return
//     */
//    @Override
//    public JsonObject<User> findListByPage(UserSearchDto condition){
//        JsonObject<User> jsonObject =new JsonObject<>();
//        QueryWrapper wrapper=new QueryWrapper();
//        wrapper.eq("role","1");
//        IPage<User> wherePage = new Page<>(condition.getPage(), condition.getPageCount());
//        userMapper.selectPage(wherePage,wrapper);
//        jsonObject.setData(wherePage.getRecords());
//        jsonObject.setCount(wherePage.getTotal());
//        return jsonObject;
//    }
//
//    /**
//     * 注册用户
//     * @param user
//     * @return
//     */
//    @Override
//    public int add(User user){
////        long n = (long)(Math.random() % 29) + 1;
////        long n1 = (long)(Math.random() % 29) + 1;
////        user.setUsername((new SnowflakeIdUtils(n,n1).nextId())+"");//默认用户名
//        user.setAvatar("https://img1.baidu.com/it/u=4117401193,3509866561&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500");//默认头像
//        user.setLocation("福建省福州市");
//        user.setIntroduce("我的签名");
//        user.setRole(1l);
//        user.setIdcard("0");
//        return baseMapper.insert(user);
//    }
//
//    /**
//     * 删除
//     * @param id 主键
//     * @return
//     */
//    @Override
//    public int delete(Long id){
//        return baseMapper.deleteById(id);
//    }
//
//    /**
//     * 更新数据
//     * @param user
//     * @return
//     */
//    @Override
//    public int updateData(UserDto user){
//        User newUser=  new User();
//        newUser.setId(Long.valueOf(TokenUtil.getTokenUserId()));
//        newUser.setAvatar(user.getAvatar());
//        newUser.setUsername(user.getUsername());
//        newUser.setEmail(user.getEmail());
//        newUser.setIntroduce(user.getIntroduce());
//        return baseMapper.updateById(newUser);
//    }
//
//    /**
//     * 返回用户个人信息
//     * @param id id
//     * @return
//     */
//    @Override
//    public UserDto findById(Long id){
//        User user=  baseMapper.selectById(id);
//        UserDto userDto=new UserDto();
//        userDto.setId(id);
//        userDto.setIdcard(user.getIdcard());
//        userDto.setLocation(user.getLocation());
//        userDto.setAvatar(user.getAvatar());
//        userDto.setUsername(user.getUsername());
//        userDto.setEmail(user.getEmail());
//        userDto.setTel(user.getTel());
//        userDto.setCreateTime(user.getCreateTime());
//        userDto.setIntroduce(user.getIntroduce());
//        return userDto;
//    }
//
//    /**
//     * 通过Id查询用户
//     * @param id
//     * @return
//     */
//    @Override
//    public User findUser(Long id){
//       return baseMapper.selectById(id);
//    }
}
