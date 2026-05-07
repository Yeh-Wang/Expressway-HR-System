package org.yeh.wang.controller;

import cn.hutool.core.date.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yeh.wang.entity.SchedulePatternEntity;
import org.yeh.wang.entity.StaffScheduleEntity;
import org.yeh.wang.result.Result;
import org.yeh.wang.service.impl.NeedDutyServiceImpl;
import org.yeh.wang.service.impl.SchedulePatternServiceImpl;
import org.yeh.wang.service.impl.StaffScheduleServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 面向前端排班的控制器
 */
@RestController
@CrossOrigin
@RequestMapping("/schedule")
public class ScheduleController {

    private StaffScheduleServiceImpl staffScheduleService;
    private SchedulePatternServiceImpl schedulePatternService;
    private NeedDutyServiceImpl needDutyService;

    @Autowired
    public void setStaffScheduleService(StaffScheduleServiceImpl staffScheduleService) {
        this.staffScheduleService = staffScheduleService;
    }
    @Autowired
    public void setSchedulePatternService(SchedulePatternServiceImpl schedulePatternService) {
        this.schedulePatternService = schedulePatternService;
    }
    @Autowired
    public void setNeedDutyService(NeedDutyServiceImpl needDutyService) {
        this.needDutyService = needDutyService;
    }

    /**
     * 自动排班
     */
    @RequestMapping("/autoSchedule/{date}")
    public Result<?> autoSchedule(@PathVariable String date) {
        //判断数据库中是否已经有排班信息
        if (staffScheduleService.count(new QueryWrapper<StaffScheduleEntity>().eq("pro_time",date)) > 0) {
            return new Result<>(400, "自动排班失败，已经有排班信息", null);
        }
        //获取当前周的所有日期
        DateTime start = DateUtil.beginOfWeek(DateUtil.parse(date, "yyyy-MM-dd"));
        DateTime end = DateUtil.endOfWeek(DateUtil.parse(date, "yyyy-MM-dd"));
        List<DateTime> weekDates = DateUtil.rangeToList(start, end, DateField.DAY_OF_WEEK);
        List<String> dates = new ArrayList<>();
        for (DateTime weekDate : weekDates) {
            dates.add(DateUtil.format(weekDate, "yyyy-MM-dd"));
        }
        // 调用排班服务，自动排班
        List<StaffScheduleEntity> list = staffScheduleService.autoSchedule(dates);
        // 存入数据库
        staffScheduleService.saveBatch(list);
        return new Result<>(200, "自动排班成功", staffScheduleService.getAll());
    }

    /**
     * 获取所有排班信息
     */
    @RequestMapping("/getAll")
    public Result<?> getAll() {
        return new Result<>(200, "获取成功", staffScheduleService.getAll());
    }

    /**
     * 获取所有排班方式信息
     */
    @RequestMapping("/getSchedulePattern")
    public Result<?> getSchedulePattern() {
        return new Result<>(200, "获取成功", schedulePatternService.list());
    }

    /**
     * 获取所有需要值班的岗位信息
     */
    @RequestMapping("/getNeedDuty")
    public Result<?> getNeedDuty() {
        return new Result<>(200, "获取成功", needDutyService.list());
    }

    /**
     * 修改排班信息
     */
    @PostMapping("/update-schedule")
    public Result<?> updateSchedule(@RequestBody StaffScheduleEntity staffScheduleEntity) {
        staffScheduleService.updateById(staffScheduleEntity);
        return new Result<>(200, "修改成功", null);
    }

    /**
     * 添加或修改排班规则
     */
    @PostMapping("/addOrUpdateSchedulePattern")
    public Result<?> addOrUpdateSchedulePattern(@RequestBody List<SchedulePatternEntity> list) {
        schedulePatternService.saveOrUpdateBatch(list);
        return new Result<>(200, "添加或修改成功", null);
    }

    /**
     * 根据id删除一条排班规则
     */
    @PostMapping("/deleteSchedulePattern/{schId}")
    public Result<?> deleteSchedulePattern(@PathVariable int schId) {
        schedulePatternService.removeById(schId);
        return new Result<>(200, "删除成功", null);
    }

    /**
     * 根据员工id获取该员工的所有排班信息
     */
    @RequestMapping("/get-schedule-info-by-staff-id/{staffId}")
    public Result<?> getScheduleByStaffId(@PathVariable String staffId) {
        return new Result<>(200, "获取成功", staffScheduleService.getBaseMapper().selectScheduleInfoById(staffId));
    }

}
