package com.cyj.mapper;

import com.cyj.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cyj
 * @since 2022-04-18
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    int changeToNormal(Long userId);
    /**
     * 通过手机号码查询用户是否存在（登陆注册）
     * @param tel
     * @return
     */
    User queryUserByTel(String tel);

    /**
     * 校验密码
     * @param id
     * @return
     */
    String checkPwd(Long id);
}
