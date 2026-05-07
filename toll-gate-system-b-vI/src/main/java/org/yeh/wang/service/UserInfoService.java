package org.yeh.wang.service;

import org.yeh.wang.entity.UserInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.yeh.wang.result.Result;

/**
 * <p>
 * 账户表; 服务类
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
public interface UserInfoService extends IService<UserInfoEntity> {

    //注册用户
    public boolean add(UserInfoEntity userInfoEntity);

    //用户名密码登录返回token
    Result<?> login(String username, String password);

    /**
     * 判断用户是否存在
     * @param username 用户名
     * @return boolean
     */
    public boolean isExist(String username);
}
