package com.cyj.service.user;

import com.cyj.dto.UserDto;
import com.cyj.dto.user.UserSearchDto;
import com.cyj.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyj.utils.JsonObject;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyj
 * @since 2022-04-18
 */
public interface UserService extends IService<User> {

    int changeToNormal(Long userId);

    String checkPwd(Long id);
    /**
     * 修改密码
     */
    int updatePwd(long userId,String oldPwd,String newPwd,String checkNewPwd) throws Exception;

    User queryUserByTel(String tel);
    /**
     * 查询用户列表
     * @param page
     * @param pageCount
     * @return
     */
    JsonObject<User> findListByPage(UserSearchDto condition);

    /**
     * 添加
     *
     * @param user 
     * @return int
     */
    int add(User user);

    /**
     * 删除
     *
     * @param id 主键
     * @return int
     */
    int delete(Long id);

    /**
     * 修改
     *
     * @param user 
     * @return int
     */
    int updateData(UserDto user);

    /**
     * id查询数据
     *
     * @param id id
     * @return User
     */
    UserDto findById(Long id);
    User findUser(Long id);
}
