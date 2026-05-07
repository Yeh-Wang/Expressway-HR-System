package org.yeh.wang.service.impl;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.yeh.wang.entity.StaffInfoEntity;
import org.yeh.wang.entity.UserInfoEntity;
import org.yeh.wang.mapper.StaffInfoMapper;
import org.yeh.wang.result.Result;
import org.yeh.wang.result.ResultEnum;
import org.yeh.wang.service.StaffInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.yeh.wang.utils.UtilsCollect;

import java.util.*;

/**
 * <p>
 * 职员 服务实现类
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Service
public class StaffInfoServiceImpl extends ServiceImpl<StaffInfoMapper, StaffInfoEntity> implements StaffInfoService {

    private StaffInfoMapper staffInfoMapper;
    private UserInfoServiceImpl userInfoService;
    private UserRoleServiceImpl userRoleService;

    @Autowired
    public void setStaffInfoMapper(StaffInfoMapper staffInfoMapper) {
        this.staffInfoMapper = staffInfoMapper;
    }
    @Autowired
    public void setUserInfoService(UserInfoServiceImpl userInfoService) {
        this.userInfoService = userInfoService;
    }
    @Autowired
    public void setUserRoleService(UserRoleServiceImpl userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Override
    public boolean add(StaffInfoEntity staffInfoEntity) {
        return staffInfoMapper.insert(staffInfoEntity)>0;
    }

    @Override
    public Result<?> addStaff(StaffInfoEntity staffInfoEntity) {
        staffInfoEntity.setStaffId(UtilsCollect.createUUID());
        String nickname = PinyinUtil.getPinyin(staffInfoEntity.getStaffName(), "");
        if (userInfoService.isExist(nickname)) {  //判断是否重复
            nickname = nickname + staffInfoEntity.getStaffCard().substring(14);
        }
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setUserId(UtilsCollect.createUUID());
        userInfoEntity.setUserNickname(nickname);
        userInfoEntity.setUserPwd("123456");
        if (userInfoService.add(userInfoEntity)) {
            userInfoEntity.setStaffId(staffInfoEntity.getStaffId());
            staffInfoEntity.setUserId(userInfoEntity.getUserId());
            if(this.save(staffInfoEntity) && userInfoService.updateById(userInfoEntity)){
                userRoleService.addUserRole(userInfoEntity.getUserId());
                Map<String,String> map = new HashMap<>();
                map.put("username", nickname);
                map.put("password", "123456");
                return new Result<>(ResultEnum.SUCCESS.getCode(), "用户名："+nickname+"  默认密码：123456", staffInfoEntity); //code:200
            } else {
                return new Result<>(ResultEnum.ERROR.getCode(), "添加失败", null);  //code:500
            }
        } else {
            return new Result<>(ResultEnum.ERROR.getCode(), "添加失败", null);  //code:500
        }
    }

    @Override
    public List<StaffInfoEntity> getStaffInfoByStaffId(String staffId) {
        StaffInfoEntity staffInfoEntity = this.getById(staffId);
        List<StaffInfoEntity> list = new ArrayList<>();
        if (Objects.equals(staffInfoEntity.getStaffRemark(), "leader")){
            list = this.list(new QueryWrapper<StaffInfoEntity>().eq("staff_station", staffInfoEntity.getStaffStation()));
        } else {
            list = this.list(new QueryWrapper<StaffInfoEntity>().eq("staff_remark", staffInfoEntity.getUserId()));
        }
        return list; //code:200
    }
}
