package org.yeh.wang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.yeh.wang.entity.DailyPunchEntity;
import org.yeh.wang.entity.StaffDailyPunchEntity;
import org.yeh.wang.entity.StaffInfoEntity;
import org.yeh.wang.mapper.DailyPunchMapper;
import org.yeh.wang.result.Result;
import org.yeh.wang.service.DailyPunchService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 日常打卡表 服务实现类
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Service
public class DailyPunchServiceImpl extends ServiceImpl<DailyPunchMapper, DailyPunchEntity> implements DailyPunchService {

    private StaffInfoServiceImpl staffInfoService;
    private StaffDailyPunchServiceImpl staffDailyPunchService;

    @Autowired
    public void setStaffInfoService(StaffInfoServiceImpl staffInfoService) {
        this.staffInfoService = staffInfoService;
    }
    @Autowired
    public void setStaffDailyPunchService(StaffDailyPunchServiceImpl staffDailyPunchService) {
        this.staffDailyPunchService = staffDailyPunchService;
    }

    @Override
    public Result<List<StaffDailyPunchEntity>> getAllDailyPunchByStaffId(String userId) {
        StaffInfoEntity staffInfoEntity = staffInfoService.getById(userId);
        List<StaffDailyPunchEntity> data_list = new ArrayList<>();
        if(Objects.equals(staffInfoEntity.getStaffRemark(), "leader")){
            data_list = staffDailyPunchService.list();
            return new Result<>(200, "获取成功", data_list);
        } else {
            data_list = staffDailyPunchService.list(new QueryWrapper<StaffDailyPunchEntity>().eq("staff_remark", staffInfoEntity.getUserId()));
            return new Result<>(200, "获取成功", data_list);
        }
    }
}
