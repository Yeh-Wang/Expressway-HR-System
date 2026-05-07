package org.yeh.wang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yeh.wang.entity.UserInfoEntity;
import org.yeh.wang.result.Result;
import org.yeh.wang.service.impl.StaffInfoServiceImpl;
import org.yeh.wang.service.impl.UserInfoServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 账户表; 前端控制器
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@RestController
@CrossOrigin
@RequestMapping("/user-info-entity")
public class UserInfoController {

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

    //test
    @GetMapping("/test")
    public String test(){
        return "test";
    }

    /**
     * 根据职员id获取职员的全部信息
     * @param staffId 职员id
     */
    @GetMapping("/get-staff-info/{staffId}")
    public Result<?> getStaffInfo(@PathVariable String staffId) {
        Map<String, Object> map = new HashMap<>();
        map.put("staffInfo",  staffInfoService.getById(staffId));
        map.put("userInfo", userInfoService.getOne(new QueryWrapper<UserInfoEntity>().eq("staff_id", staffId)));
//        System.out.println(staffInfoService.getById(staffId)+","+map);
        return new Result<>(200, "获取成功", map);
    }
}
