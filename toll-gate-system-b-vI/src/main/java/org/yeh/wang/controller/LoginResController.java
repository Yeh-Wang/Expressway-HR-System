package org.yeh.wang.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.yeh.wang.entity.StaffInfoEntity;
import org.yeh.wang.entity.UserInfoEntity;
import org.yeh.wang.result.Result;
import org.yeh.wang.result.ResultEnum;
import org.yeh.wang.service.impl.StaffInfoServiceImpl;
import org.yeh.wang.service.impl.UserInfoServiceImpl;
import org.yeh.wang.utils.UtilsCollect;
import org.yeh.wang.vo.RegisterVo;

/**
 *   登录资源表; 前端控制器
 */
@RestController
@CrossOrigin
@RequestMapping("/login-res-entity")
public class LoginResController {

    private UserInfoServiceImpl userInfoService;
    private StaffInfoServiceImpl staffInfoService;

    @Autowired
    public void setUserInfoService(UserInfoServiceImpl userInfoService) {
        this.userInfoService = userInfoService;
    }
    @Autowired
    public void setStaffInfoService(StaffInfoServiceImpl staffInfoService) {
        this.staffInfoService = staffInfoService;
    }


    /**
     * 注册用户
     * @param registerVo 注册信息
     * @return 是否注册成功
     */
    @RequestMapping("/register")
    public boolean register(RegisterVo registerVo) {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        StaffInfoEntity staffInfoEntity = new StaffInfoEntity();
        userInfoEntity.setUserInfoEntity(registerVo);
        staffInfoEntity.setStaffInfoEntity(registerVo);
        try {
            userInfoService.add(userInfoEntity);
            staffInfoService.add(staffInfoEntity);
            userInfoEntity.setStaffId(staffInfoEntity.getStaffId());
            staffInfoEntity.setUserId(userInfoEntity.getUserId());
            userInfoService.updateById(userInfoEntity);
            staffInfoService.updateById(staffInfoEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 用户名密码登录返回token
     * @param username 用户名
     * @param password 密码
     *                 @return token
     */
    @RequestMapping("/login")
    public Result<?> login(@RequestParam String username,@RequestParam String password) {
        return userInfoService.login(username, password);
    }

    /**
     * 修改密码
     */
    @RequestMapping("/update-password")
    public Result<?> updatePassword(@RequestBody UserInfoEntity userInfoEntity) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), userInfoService.updateById(userInfoEntity));
    }
}
