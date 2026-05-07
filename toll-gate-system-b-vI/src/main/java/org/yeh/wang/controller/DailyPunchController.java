package org.yeh.wang.controller;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yeh.wang.entity.DailyPunchEntity;
import org.yeh.wang.entity.StaffDailyPunchEntity;
import org.yeh.wang.result.Result;
import org.yeh.wang.service.impl.DailyPunchServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static cn.hutool.core.util.NumberUtil.round;

/**
 * 日常考勤打卡接口
 */
@RestController
@CrossOrigin
@RequestMapping("/daily-punch-entity")
public class DailyPunchController {

    private DailyPunchServiceImpl dailyPunchService;

    @Autowired
    public void setDailyPunchService(DailyPunchServiceImpl dailyPunchService) {
        this.dailyPunchService = dailyPunchService;
    }

    /**
     * 添加日常考勤打卡记录并返回这条记录
     */
    @RequestMapping("/add-daily-punch")
    public Result<?> addDailyPunch(DailyPunchEntity dailyPunchEntity) {
        if (Objects.equals(dailyPunchEntity.getStaffId(), "") || dailyPunchEntity.getStaffId() == null) {
            return new Result<>(500, "添加失败", null);
        }
        Date start = DateUtil.parse(dailyPunchEntity.getPunStartTime());
        Date end = DateUtil.parse(dailyPunchEntity.getPunEndTime());
        double between = DateUtil.between(start, end, DateUnit.MINUTE);
        double hour = round(between / 60,2).doubleValue();
        dailyPunchEntity.setPunWorkTime(hour);
        if (dailyPunchService.save(dailyPunchEntity)) {
            return new Result<>(200, "添加成功", dailyPunchEntity);
        } else {
            return new Result<>(500, "添加失败", null);
        }
    }

    /**
     * 根据职员ID获取日常考勤打卡记录
     * @param staffId 职员ID
     * @return 日常考勤打卡记录
     */
    @RequestMapping("/get-daily-punch/{staffId}")
    public Result<?> getDailyPunchByStaffId(@PathVariable String staffId) {
        return new Result<>(200, "获取成功", dailyPunchService.list(new QueryWrapper<DailyPunchEntity>().eq("staff_id", staffId)));
    }

    /**
     * 根据用户id获取所有日常考勤打卡记录
     * @param userId 管理员用户职员id
     * @return 所有日常考勤打卡记录
     */
    @RequestMapping("/get-all-daily-punch/{userId}")
    public Result<List<StaffDailyPunchEntity>> getAllDailyPunchByStaffId(@PathVariable String userId) {
        return dailyPunchService.getAllDailyPunchByStaffId(userId);
    }

    /**
     * 根据考勤打卡id更改日常考勤打卡记录
     * @param dailyPunchEntity 日常考勤打卡记录
     */
    @RequestMapping("/update-daily-punch")
    public Result<?> updateDailyPunch(@RequestBody DailyPunchEntity dailyPunchEntity) {
        DailyPunchEntity dailyPunchEntity1  = new DailyPunchEntity();
        dailyPunchEntity1.setPunId(dailyPunchEntity.getPunId());
        dailyPunchEntity1.setPunStartTime(dailyPunchEntity.getPunStartTime());
        dailyPunchEntity1.setPunEndTime(dailyPunchEntity.getPunEndTime());
        dailyPunchEntity1.setPunRemark(dailyPunchEntity.getPunRemark());
        dailyPunchEntity1.setPunWorkTime(dailyPunchEntity.getPunWorkTime());
        if (dailyPunchService.updateById(dailyPunchEntity1)) {
            return new Result<>(200, "更新成功", dailyPunchEntity);
        } else {
            return new Result<>(500, "更新失败", null);
        }
//        Date start = DateUtil.parse(dailyPunchEntity.getPunStartTime());
//        Date end = DateUtil.parse(dailyPunchEntity.getPunEndTime());
//        if (DateUtil.isSameDay(start, end)) {
//            double between = DateUtil.between(start, end, DateUnit.HOUR);
//            dailyPunchEntity1.setPunWorkTime(between);
//            if (dailyPunchService.updateById(dailyPunchEntity1)) {
//                return new Result<>(200, "更新成功", dailyPunchEntity);
//            } else {
//                return new Result<>(500, "更新失败", null);
//            }
//        } else {
//            return new Result<>(500, "打卡时间不在同一天", null);
//        }
    }

    /**
     * 根据考勤打卡id删除日常考勤打卡记录
     * @param punId 考勤打卡id
     * @return 是否删除成功
     */
    @RequestMapping("/delete-daily-punch/{punId}")
    public Result<?> deleteDailyPunch(@PathVariable Integer punId) {
        if (dailyPunchService.removeById(punId)) {
            return new Result<>(200, "删除成功", null);
        } else {
            return new Result<>(500, "删除失败", null);
        }
    }

    /**
     * 添加日常考勤打卡记录
     */
    @RequestMapping("/add-daily-punch-info")
    public Result<?> addDailyPunchInfo(@RequestBody DailyPunchEntity dailyPunchEntity) {
        System.out.println(dailyPunchEntity);
        if(dailyPunchEntity.getPunType() == 1){
            dailyPunchService.save(dailyPunchEntity);
        }else{
            Date start = DateUtil.parse(dailyPunchEntity.getPunStartTime());
            Date end = DateUtil.parse(dailyPunchEntity.getPunEndTime());
            double between = DateUtil.between(start, end, DateUnit.MINUTE);
            double hour = round(between / 60,2).doubleValue();
            dailyPunchEntity.setPunWorkTime(hour);
            dailyPunchService.updateById(dailyPunchEntity);
        }
        return new Result<>(200, "添加成功", dailyPunchEntity);
    }
}
