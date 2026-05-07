package org.yeh.wang.controller;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yeh.wang.entity.StaffInfoEntity;
import org.yeh.wang.entity.StudyRecordEntity;
import org.yeh.wang.entity.TrainInfoEntity;
import org.yeh.wang.result.Result;
import org.yeh.wang.service.impl.StaffInfoServiceImpl;
import org.yeh.wang.service.impl.StudyRecordServiceImpl;
import org.yeh.wang.service.impl.TrainInfoServiceImpl;

import java.util.Date;
import java.util.List;

import static cn.hutool.core.util.NumberUtil.round;

/**
 * 员工学习控制器
 */
@RestController
@CrossOrigin
@RequestMapping("/staff-study")
public class StaffStudyController {
    private StudyRecordServiceImpl studyRecordService;
    private StaffInfoServiceImpl staffInfoService;
    private TrainInfoServiceImpl trainInfoService;

    @Autowired
    public void setStudyRecordService(StudyRecordServiceImpl studyRecordService) {
        this.studyRecordService = studyRecordService;
    }
    @Autowired
    public void setStaffInfoService(StaffInfoServiceImpl staffInfoService) {
        this.staffInfoService = staffInfoService;
    }
    @Autowired
    public void setTrainInfoService(TrainInfoServiceImpl trainInfoService) {
        this.trainInfoService = trainInfoService;
    }

    /**
     * 添加或修改学习记录
     * @param studyRecord 学习记录
     */
    @RequestMapping("/add-study-record")
    public Result<?> addStudyRecord(@RequestBody StudyRecordEntity studyRecord) {
        if(studyRecord.getStuId() == 0 ){
//            StudyRecordEntity studyRecordEntity = new StudyRecordEntity();
//            studyRecordEntity.setStaffId(studyRecord.getStaffId());
//            studyRecordEntity.setStuStartTime(studyRecord.getStuStartTime());
//            studyRecordEntity.setStuEndTime(studyRecord.getStuEndTime());
//            //计算学习时长
//            Date start = DateUtil.parse(studyRecord.getStuStartTime());
//            Date end = DateUtil.parse(studyRecord.getStuEndTime());
//            double between = DateUtil.between(start, end, DateUnit.MINUTE);
//            double hour = round(between / 60,2).doubleValue();
//            studyRecordEntity.setStuTotalTime(hour);
            if (studyRecordService.save(studyRecord)) {
                return new Result<>(200, "添加成功", studyRecord);
            } else {
                return new Result<>(500, "添加失败", null);
            }
        }else {
            //计算学习时长
            Date start = DateUtil.parse(studyRecord.getStuStartTime());
            Date end = DateUtil.parse(studyRecord.getStuEndTime());
            double between = DateUtil.between(start, end, DateUnit.MINUTE);
            double hour = round(between / 60,2).doubleValue();
            studyRecord.setStuTotalTime(hour);
            if (studyRecordService.updateById(studyRecord)) {
                return new Result<>(200, "修改成功", studyRecord);
            } else {
                return new Result<>(500, "修改失败", null);
            }
        }
    }

    /**
     * 根据职员id和培训id获取所有学习记录
     */
    @RequestMapping("/get-all-study-record/{staffId}/{traId}")
    public Result<?> getAllStudyRecord(@PathVariable String staffId, @PathVariable Integer traId) {
        StaffInfoEntity staffInfoEntity = staffInfoService.getById(staffId);
        if(staffInfoEntity.getStaffRemark().equals("leader")){
            List<StudyRecordEntity> list = studyRecordService.list(new QueryWrapper<StudyRecordEntity>().eq("stu_train", traId));
            list.forEach(studyRecordEntity -> {
                StaffInfoEntity staffInfoEntity1 = staffInfoService.getById(studyRecordEntity.getStaffId());
                studyRecordEntity.setStaffName(staffInfoEntity1.getStaffName());
            });
            return new Result<>(200, "获取成功", list);
        } else {
            List<StudyRecordEntity> list = studyRecordService.list(new QueryWrapper<StudyRecordEntity>().eq("staff_id", staffId).eq("stu_train", traId));
            list.forEach(studyRecordEntity -> {
                studyRecordEntity.setStaffName(staffInfoEntity.getStaffName());
            });
            return new Result<>(200, "获取成功", list);
        }
    }
    /**
     * 根据职员id获取所有学习记录
     */
    @RequestMapping("/get-study-record-by-staff-id/{staffId}")
    public Result<?> getStudyRecordByStaffId(@PathVariable String staffId) {
        List<StudyRecordEntity> list = studyRecordService.list(new QueryWrapper<StudyRecordEntity>().eq("staff_id", staffId));
        //培训编号转换为培训名称
        list.forEach(studyRecordEntity -> {
            TrainInfoEntity trainInfoEntity = trainInfoService.getById(studyRecordEntity.getStuTrain());
            studyRecordEntity.setTrainName(trainInfoEntity.getTraTheme());
        });
        return new Result<>(200, "获取成功", list);
    }


    /**
     * 计算每个职员指定日期区间的学习时长
     */
    @RequestMapping("/get-study-time/{staffId}/{start}/{end}")
    public Result<?> getStudyTime(@PathVariable String staffId, @PathVariable String start, @PathVariable String end) {
        return new Result<>(200, "获取成功", studyRecordService.selectAllStudyTime(staffId, start, end));
    }

    /**
     * 计算每个职员指定日期区间指定培训计划的学习时长
     */
    @RequestMapping("/get-study-time-by-train")
    public Result<?> getStudyTimeByTrain(@RequestParam String staffId, @RequestParam String start, @RequestParam String end, @RequestParam Integer trainId) {
        System.out.println(start+":"+end);
        return new Result<>(200, "获取成功", studyRecordService.selectStudyTimeByTrain(staffId, start, end, trainId));
    }

}
