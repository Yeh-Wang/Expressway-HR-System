package org.yeh.wang.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.yeh.wang.entity.ApplyFeedbackEntity;
import org.yeh.wang.entity.CheckSheetEntity;
import org.yeh.wang.entity.StaffInfoEntity;
import org.yeh.wang.mapper.CheckSheetMapper;
import org.yeh.wang.result.Result;
import org.yeh.wang.service.CheckSheetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.yeh.wang.vo.CheekSheetVo;
import org.yeh.wang.vo.CountApplyVo;
import org.yeh.wang.vo.CountDailyVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 考勤表 服务实现类
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Service
@EnableScheduling
public class CheckSheetServiceImpl extends ServiceImpl<CheckSheetMapper, CheckSheetEntity> implements CheckSheetService {

    private DailyPunchServiceImpl dailyPunchService;
    private ApplyFeedbackServiceImpl applyFeedbackService;
    private StaffInfoServiceImpl staffInfoService;

    @Autowired
    public void setDailyPunchService(DailyPunchServiceImpl dailyPunchService) {
        this.dailyPunchService = dailyPunchService;
    }
    @Autowired
    public void setApplyFeedbackService(ApplyFeedbackServiceImpl applyFeedbackService) {
        this.applyFeedbackService = applyFeedbackService;
    }
    @Autowired
    public void setStaffInfoService(StaffInfoServiceImpl staffInfoService) {
        this.staffInfoService = staffInfoService;
    }

    @Override
    @Scheduled(cron = "0 0 23 L * ?")
    public void generateCheckSheet() {
        //查询有多少请假的申请
        var applyFeedbackEntityList = applyFeedbackService.list(new QueryWrapper<ApplyFeedbackEntity>().eq("process_type", 1));
        //查询出所有的职员信息
        var staffInfoEntityList = staffInfoService.list();
        //查询出这个月所有职员信息的职员编号和请假天数放在List中
        Date nowTime = DateUtil.parse(new Date().toString());
        var nowDate = nowTime.toString().substring(0,7);
        var month = nowDate + "%";
        var countApplyVoList = applyFeedbackService.getBaseMapper().selectGroupByStaff(month);
        //查询出所有职员信息的编号，打卡天数和加班时长
        var countDailyVoList = dailyPunchService.getBaseMapper().selectGroupByStaff(month);
        //生成考勤表
        this.remove(new QueryWrapper<CheckSheetEntity>().like("ch_month",month));
        generateSheet(staffInfoEntityList,countApplyVoList,countDailyVoList,nowDate);
    }

    /**
     * 给与站长编号和月份生成本站的考勤表（站长专属）
     * @param staffId 职员编号
     * @param month 月份
     */
    @Override
    public void generateCheckSheetByStation(String staffId,String month) {
        //查询有多少请假的申请
//        List<ApplyFeedbackEntity> applyFeedbackEntityList = applyFeedbackService.list(new QueryWrapper<ApplyFeedbackEntity>().eq("process_type", 1));
        //查询出所有的职员信息
        List<StaffInfoEntity> staffInfoEntityList = staffInfoService.getStaffInfoByStaffId(staffId);
        //查询出这个月所有职员信息的职员编号和请假天数放在List中
        String monthStr = month + "%";
        List<CountApplyVo> countApplyVoList = applyFeedbackService.getBaseMapper().selectGroupByStaff(monthStr);
        //查询出所有职员信息的编号，打卡天数和加班时长
        List<CountDailyVo> countDailyVoList = dailyPunchService.getBaseMapper().selectGroupByStaff(monthStr);
        //生成考勤表
        this.remove(new QueryWrapper<CheckSheetEntity>().like("ch_month",monthStr));
        generateSheet(staffInfoEntityList,countApplyVoList,countDailyVoList,month);
    }

    @Override
    public Result<?> getCheckSheetByStaff(String staffId, String month) {
        StaffInfoEntity staffInfoEntity = staffInfoService.getById(staffId);
        month = month + "%";
        if (staffInfoEntity.getStaffRemark().equals("leader")){
            List<CheckSheetEntity> checkSheetEntityList = this.list(new QueryWrapper<CheckSheetEntity>().like("ch_month", month));
            List<CheekSheetVo> cheekSheetVoList = new ArrayList<>();
            for (CheckSheetEntity checkSheetEntity : checkSheetEntityList){
                StaffInfoEntity staffInfo = staffInfoService.getById(checkSheetEntity.getStaffId());
                CheekSheetVo cheekSheetVo = new CheekSheetVo(checkSheetEntity, staffInfo.getStaffName());
                cheekSheetVoList.add(cheekSheetVo);
            }
            return new Result<>(200, "获取成功", cheekSheetVoList);
        } else {
            CheckSheetEntity checkSheetEntity = this.getOne(new QueryWrapper<CheckSheetEntity>().eq("staff_id", staffId).like("ch_month", month));
            if(checkSheetEntity == null){
                return new Result<>(500, "暂无数据", null);
            }
            CheekSheetVo cheekSheetVo = new CheekSheetVo(checkSheetEntity, staffInfoEntity.getStaffName());
            return new Result<>(500, "获取成功", cheekSheetVo);
        }
    }

    @Override
    public Result<?> getAllCheckSheet() {
        List<CheckSheetEntity> checkSheetEntityList = this.list();
        List<CheekSheetVo> cheekSheetVoList = new ArrayList<>();
        for (CheckSheetEntity checkSheetEntity : checkSheetEntityList){
            StaffInfoEntity staffInfo = staffInfoService.getById(checkSheetEntity.getStaffId());
            CheekSheetVo cheekSheetVo = new CheekSheetVo(checkSheetEntity, staffInfo.getStaffName());
            cheekSheetVoList.add(cheekSheetVo);
        }
        return new Result<>(200, "获取成功", cheekSheetVoList);
    }

    /**
     * 生成考勤表
     */
    public void generateSheet(List<StaffInfoEntity> staffInfoEntityList,List<CountApplyVo> countApplyVoList,List<CountDailyVo> countDailyVoList, String month){
        //生成考勤表
        for (StaffInfoEntity staffInfo : staffInfoEntityList){
            CheckSheetEntity checkSheetEntity = new CheckSheetEntity();
            checkSheetEntity.setStaffId(staffInfo.getStaffId());
            checkSheetEntity.setChMonth(month);
            checkSheetEntity.setChTaskTimes(0);
            checkSheetEntity.setChVacateDay(0);
            checkSheetEntity.setChOverTime(0);
            checkSheetEntity.setChSetDay(20);
            checkSheetEntity.setChRealDay(0);
            countApplyVoList.forEach(countApplyVo -> {
                if (countApplyVo.getStaffId().equals(staffInfo.getStaffId())){
                    checkSheetEntity.setChVacateDay(countApplyVo.getDayCount());
                }
            });
            countDailyVoList.forEach(countDailyVo -> {
                if (countDailyVo.getStaffId().equals(staffInfo.getStaffId())){
                    checkSheetEntity.setChRealDay(countDailyVo.getTotalDay());
                    checkSheetEntity.setChOverTime(NumberUtil.round(countDailyVo.getTotalTime(),2).doubleValue());
                }
            });
            this.save(checkSheetEntity);
        }
    }
}
