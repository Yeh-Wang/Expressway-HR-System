package org.yeh.wang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yeh.wang.entity.StudySrcEntity;
import org.yeh.wang.entity.TrainGradeInfoEntity;
import org.yeh.wang.entity.TrainInfoEntity;
import org.yeh.wang.result.Result;
import org.yeh.wang.result.ResultEnum;
import org.yeh.wang.service.impl.StudySrcServiceImpl;
import org.yeh.wang.service.impl.TrainGradeInfoServiceImpl;
import org.yeh.wang.service.impl.TrainInfoServiceImpl;

import java.util.List;

/**
 * 员工培训控制器
 */
@RestController
@CrossOrigin
@RequestMapping("/staff-train")
public class StaffTrainController {

    private TrainInfoServiceImpl trainInfoService;
    private StudySrcServiceImpl studySrcService;
    private TrainGradeInfoServiceImpl trainGradeInfoService;
    @Autowired
    public void setTrainInfoService(TrainInfoServiceImpl trainInfoService) {
        this.trainInfoService = trainInfoService;
    }
    @Autowired
    public void setStudySrcService(StudySrcServiceImpl studySrcService) {
        this.studySrcService = studySrcService;
    }
    @Autowired
    public void setTrainGradeInfoService(TrainGradeInfoServiceImpl trainGradeInfoService) {
        this.trainGradeInfoService = trainGradeInfoService;
    }

    /**
     * 获取培训计划列表
     */
    @RequestMapping("/get-train-info-list")
    public Result<?> getTrainInfoList() {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), trainInfoService.list());
    }

    /**
     * 添加培训计划
     */
    @RequestMapping("/add-train-info")
    public Result<?> addTrainInfo(@RequestBody TrainInfoEntity trainInfoEntity) {
        trainInfoService.save(trainInfoEntity);
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), trainInfoEntity);
    }

    /**
     * 修改培训计划
     */
    @RequestMapping("/update-train-info")
    public Result<?> updateTrainInfo(@RequestBody TrainInfoEntity trainInfoEntity) {
        trainInfoService.updateById(trainInfoEntity);
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 删除培训计划
     */
    @RequestMapping("/delete-train-info")
    public Result<?> deleteTrainInfo(Integer traId) {
        trainInfoService.removeById(traId);
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 根据培训计划id获取培训资源
     */
    @RequestMapping("/get-train-src/{traId}")
    public Result<?> getTrainSrc(@PathVariable Integer traId) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), studySrcService.list(new QueryWrapper<StudySrcEntity>().eq("src_train", traId)));
    }

    /**
     * 添加培训资源
     */
    @RequestMapping("/add-train-src")
    public Result<?> addTrainSrc(@RequestBody List<StudySrcEntity> list) {
        studySrcService.saveBatch(list);
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), list);
    }

    /**
     * 修改培训资源
     */
    @RequestMapping("/update-train-src")
    public Result<?> updateTrainSrc(@RequestBody StudySrcEntity studySrcEntity) {
        studySrcService.updateById(studySrcEntity);
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), studySrcEntity);
    }

    /**
     * 删除培训资源
     */
    @RequestMapping("/delete-train-src")
    public Result<?> deleteTrainSrc(Integer srcId) {
        studySrcService.removeById(srcId);
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 根据培训计划id查询学员培训成绩信息
     */
    @RequestMapping("/get-train-score/{traId}")
    public Result<?> getTrainGradeInfoByTrainInfoId(@PathVariable Integer traId) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), trainGradeInfoService.getTrainGradeInfoByTrainInfoId(traId));
    }

    /**
     * 修改学员培训成绩
     */
    @RequestMapping("/update-train-score")
    public Result<?> updateTrainGradeInfo(@RequestBody TrainGradeInfoEntity trainGradeInfoEntity) {
        trainGradeInfoService.updateById(trainGradeInfoEntity);
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), trainGradeInfoEntity);
    }

    /**
     * 根据职员id和培训id获取培训成绩
     */
    @RequestMapping("/get-train-score/{staffId}/{traId}")
    public Result<?> getTrainGradeInfoByStaffIdAndTrainId(@PathVariable String staffId, @PathVariable Integer traId) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), trainGradeInfoService.getOne(new QueryWrapper<TrainGradeInfoEntity>().eq("staff_id", staffId).eq("tra_id", traId)));
    }

    /**
     * 添加考试成绩
     */
    @RequestMapping("/add-train-score")
    public Result<?> addTrainGradeInfo(@RequestBody TrainGradeInfoEntity trainGradeInfoEntity) {
        trainGradeInfoService.save(trainGradeInfoEntity);
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), trainGradeInfoEntity);
    }


}
