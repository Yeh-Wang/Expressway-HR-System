package org.yeh.wang.controller;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yeh.wang.entity.StaffInfoEntity;
import org.yeh.wang.entity.StaffUserEntity;
import org.yeh.wang.entity.UserInfoEntity;
import org.yeh.wang.result.Result;
import org.yeh.wang.result.ResultEnum;
import org.yeh.wang.service.impl.StaffInfoServiceImpl;
import org.yeh.wang.service.impl.StaffUserServiceImpl;
import org.yeh.wang.service.impl.UserInfoServiceImpl;
import org.yeh.wang.utils.UtilsCollect;
import org.yeh.wang.vo.ScheduleStaffVo;

import java.util.*;

/**
 * 职员信息控制器
 */
@RestController
@CrossOrigin
@RequestMapping("/staff-entity")
public class StaffController {

    private StaffInfoServiceImpl staffInfoService;
    private UserInfoServiceImpl userInfoService;
    private StaffUserServiceImpl staffUserService;
    @Autowired
    public void setStaffInfoService(StaffInfoServiceImpl staffInfoService) {
        this.staffInfoService = staffInfoService;
    }
    @Autowired
    public void setUserInfoService(UserInfoServiceImpl userInfoService) {
        this.userInfoService = userInfoService;
    }
    @Autowired
    public void setStaffUserService(StaffUserServiceImpl staffUserService) {
        this.staffUserService = staffUserService;
    }



    /**
     * 前端传入职员信息，添加职员信息
     * @param staffInfoEntity 职员信息
     * @return 是否添加成功
     */
    @RequestMapping("/add-staff-info")
    public Result<?> addStaffInfo(StaffInfoEntity staffInfoEntity) {
        if (staffInfoEntity == null) {
            return new Result<>(ResultEnum.ERROR.getCode(), "职员信息不能为空", null); //code:500
        }
        return staffInfoService.addStaff(staffInfoEntity);
    }

    /**
     * 根据职员ID获取职员信息
     * @param staffId 职员ID
     *                用于获取职员信息
     * @return 职员信息
     */
    @RequestMapping("/get-staff-info/{staffId}")
    public Result<?> getStaffInfo(@PathVariable String staffId) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), "查询成功", staffInfoService.getById(staffId)); //code:200
    }

    /**
     * 根据传入id获取所有职员信息 如果是站长则可以获取所有职员信息 如果是班长则只能获取自己所在班组的职员信息
     * @return 所有职员信息
     */
    @GetMapping("/get-all-staff-info/{userId}")
    public Result<?> getAllStaffInfo(@PathVariable String userId) {
        ArrayList<StaffUserEntity> list = new ArrayList<>();
        //判断是否有上级
        StaffInfoEntity staffInfoEntity = staffInfoService.getById(userInfoService.getById(userId).getStaffId());
        if(Objects.equals(staffInfoEntity.getStaffRemark(), "leader")){
            //获取所有职员信息
            list = (ArrayList<StaffUserEntity>) staffUserService.list();
        }else{
            list = (ArrayList<StaffUserEntity>) staffUserService.list(new QueryWrapper<StaffUserEntity>().eq("staff_remark",userId));
        }
        return new Result<>(ResultEnum.SUCCESS.getCode(), "查询成功", list); //code:200
    }

    /**
     * 更改职员信息
     * @param staffUserInfo 职员信息
     */
    @RequestMapping("/update-staff-info")
    public Result<?> updateStaffInfo(@RequestBody StaffUserEntity staffUserInfo) {
        if (staffUserInfo == null) {
            return new Result<>(ResultEnum.ERROR.getCode(), "职员信息不能为空", null); //code:500
        }
        StaffInfoEntity staffInfoEntity = new StaffInfoEntity();
        staffInfoEntity.setStaffId(staffUserInfo.getStaffId());
        staffInfoEntity.setStaffName(staffUserInfo.getStaffName());
        staffInfoEntity.setStaffNation(staffUserInfo.getStaffNation());
        staffInfoEntity.setGender(staffUserInfo.getGender());
        staffInfoEntity.setAge(staffUserInfo.getAge());
        staffInfoEntity.setStaffDegree(staffUserInfo.getStaffDegree());
        staffInfoEntity.setStaffPost(staffUserInfo.getStaffPost());
        staffInfoEntity.setStaffPolitics(staffUserInfo.getStaffPolitics());
        staffInfoEntity.setStaffRemark(staffUserInfo.getStaffRemark());
        staffInfoEntity.setStaffPhone(staffUserInfo.getStaffPhone());
        staffInfoEntity.setStaffAvatar(staffUserInfo.getStaffAvatar());
        staffInfoEntity.setStaffAddress(staffUserInfo.getStaffAddress());
        staffInfoEntity.setStaffCard(staffUserInfo.getStaffCard());
        staffInfoEntity.setStaffStation(staffUserInfo.getStaffStation());
        return new Result<>(ResultEnum.SUCCESS.getCode(), "修改成功", staffInfoService.updateById(staffInfoEntity)); //code:200
    }

    /**
     * 删除职员信息
     * @param staffId 职员ID
     */
    @RequestMapping("/delete-staff-info/{staffId}")
    public Result<?> deleteStaffInfo(@PathVariable String staffId) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), "删除成功", staffInfoService.removeById(staffId)); //code:200
    }

    /**
     * 根据岗位获取职员信息
     * @param postName 岗位
     */
    @RequestMapping("/get-staff-by-post/{postName}")
    public Result<?> getStaffByPost(@PathVariable String postName) {
        List<StaffUserEntity> list = staffUserService.list(new QueryWrapper<StaffUserEntity>().eq("post_name", postName));
        List<ScheduleStaffVo> scheduleStaffVoList = new ArrayList<>();
        for (StaffUserEntity staffInfoEntity : list) {
            ScheduleStaffVo scheduleStaffVo = new ScheduleStaffVo();
            scheduleStaffVo.setStaffId(staffInfoEntity.getStaffId());
            scheduleStaffVo.setStaffName(staffInfoEntity.getStaffName());
            scheduleStaffVoList.add(scheduleStaffVo);
        }
        return new Result<>(ResultEnum.SUCCESS.getCode(), "查询成功", scheduleStaffVoList); //code:200
    }
}
