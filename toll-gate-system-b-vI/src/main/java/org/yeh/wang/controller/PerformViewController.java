package org.yeh.wang.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yeh.wang.dto.PerformanceInfoDto;
import org.yeh.wang.entity.*;
import org.yeh.wang.result.Result;
import org.yeh.wang.result.ResultEnum;
import org.yeh.wang.service.impl.*;
import org.yeh.wang.utils.pairs;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 员工绩效考核前端控制器
 */
@RestController
@CrossOrigin
@RequestMapping("/perform-view-entity")
public class PerformViewController {

    private PerformanceReviewServiceImpl performanceReviewService;
    private PerformTypeServiceImpl performTypeService;
    private CheckSheetServiceImpl checkSheetService;
    private RePunInfoServiceImpl rePunInfoService;
    private StaffInfoServiceImpl staffInfoService;
    private StaffUserServiceImpl staffUserService;

    @Autowired
    public void setPerformanceReviewService(PerformanceReviewServiceImpl performanceReviewService) {
        this.performanceReviewService = performanceReviewService;
    }

    @Autowired
    public void setCheckSheetService(CheckSheetServiceImpl checkSheetService) {
        this.checkSheetService = checkSheetService;
    }

    @Autowired
    public void setRePunInfoService(RePunInfoServiceImpl rePunInfoService) {
        this.rePunInfoService = rePunInfoService;
    }

    @Autowired
    public void setPerformTypeService(PerformTypeServiceImpl performTypeService) {
        this.performTypeService = performTypeService;
    }

    @Autowired
    public void setStaffInfoService(StaffInfoServiceImpl staffInfoService) {
        this.staffInfoService = staffInfoService;
    }

    @Autowired
    public void setStaffUserService(StaffUserServiceImpl staffUserService) {
        this.staffUserService = staffUserService;
    }

    /**
     * 根据管理员id获取所有职员奖惩信息
     *
     * @param staffId 管理员用户职员id
     * @return 所有职员奖惩信息
     */
    @RequestMapping("/get-all-punish-review/{staffId}")
    public Result<?> getAllPerformReviewByStaffId(@PathVariable String staffId) {
        return performanceReviewService.getAllPunInfoByStaffId(staffId);
    }

    /**
     * 根据职员id获取该职员奖惩信息
     *
     * @param staffId 职员id
     */
    @RequestMapping("/get-rew-punish-review/{staffId}")
    public Result<?> getPerformReviewByStaffId(@PathVariable String staffId) {
        return new Result<>(200, "获取成功", rePunInfoService.list(new QueryWrapper<RePunInfoEntity>().eq("rew_staff", staffId)));
    }

    /**
     * 添加职员奖惩信息
     *
     * @param rePunInfo 职员奖惩信息
     * @return 是否添加成功
     */
    @RequestMapping("/add-rew-punish-info")
    public Result<?> addPerformReview(@RequestBody RePunInfoEntity rePunInfo) {
        return performanceReviewService.addRePunInfo(rePunInfo);
    }

    /**
     * 根据奖惩信息id删除奖惩信息
     *
     * @param rewId 奖惩信息id
     */
    @RequestMapping("/delete-rew-punish-info/{rewId}")
    public Result<?> deletePerformReview(@PathVariable Integer rewId) {
        return performanceReviewService.deleteRePunInfo(rewId);
    }

    /**
     * 根据职员id和月份生成考勤表并返回（站长）/ 返回指定月份的考勤表（站长和职员）
     *
     * @param staffId 职员id
     * @param month   月份
     */
    @RequestMapping("/generate-check-sheet/{staffId}/{month}")
    public Result<?> generateCheckSheetByStaffId(@PathVariable String staffId, @PathVariable String month) {
        Date nowTime = DateUtil.parse(new Date().toString());
        var nowDate = nowTime.toString().substring(0, 7);
        StaffInfoEntity staffInfoEntity = staffInfoService.getById(staffId);
        if (staffInfoEntity.getStaffRemark().equals("leader")) {
            checkSheetService.generateCheckSheetByStation(staffId, month);
        }
//        if(nowDate.equals(month)){
//            checkSheetService.generateCheckSheetByStation(staffId,month);
//        }
        return checkSheetService.getCheckSheetByStaff(staffId, month);
    }

    /**
     * 站长获取所有职员考勤表
     */
    @GetMapping("/get-all-check-sheet")
    public Result<?> getAllCheckSheet() {
        return checkSheetService.getAllCheckSheet();
    }


    /**
     * 获取所有职员绩效考核信息
     */
    @GetMapping("/get-all-perform-review/{staffId}")
    public Result<?> getAllPerformReview(@PathVariable String staffId) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), performanceReviewService.getPerformReviewByStaffId(staffId));
    }

    /**
     * 获取所有绩效考核方式信息
     */
    @GetMapping("/get-all-perform-type")
    public Result<?> getAllPerformType() {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), performTypeService.list());
    }

    /**
     * 更改绩效考核信息
     */
    @PostMapping("/update-perform-type")
    public Result<?> updatePerformType(@RequestBody PerformTypeEntity performTypeEntity) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), performTypeService.updateById(performTypeEntity));
    }

    /**
     * 生成绩效考核表
     */
    @PostMapping("/generate-perform-review")
    public Result<?> generatePerformReview(@RequestBody PerformanceInfoDto performanceInfoDto) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), performanceReviewService.generatePerformReview(performanceInfoDto));
    }

    /**
     * 获取总分项所有绩效考核表
     */
    @GetMapping("/get-all-perform-review-total")
    public Result<?> getAllPerformReview() {
        List<PerformanceReviewEntity> list = performanceReviewService.list(new QueryWrapper<PerformanceReviewEntity>().eq("per_remark", "总分"));
        list.forEach(item -> {
            StaffUserEntity staffUserEntity = staffUserService.getOne(new QueryWrapper<StaffUserEntity>().eq("staff_id", item.getPerStaff()));
            item.setPerStaffName(staffUserEntity.getStaffName());
            item.setPerPostName(staffUserEntity.getPostName());
        });
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), list);
    }

    /**
     * 根据员工id获取他的绩效考核总分项记录
     */
    @GetMapping("/get-perform-review-total/{staffId}")
    public Result<?> getPerformReviewTotal(@PathVariable String staffId) {
        PerformanceReviewEntity performanceReviewEntity = performanceReviewService.getOne(new QueryWrapper<PerformanceReviewEntity>().eq("per_staff", staffId).eq("per_remark", "总分"));
        StaffUserEntity staffUserEntity = staffUserService.getOne(new QueryWrapper<StaffUserEntity>().eq("staff_id", staffId));
        performanceReviewEntity.setPerStaffName(staffUserEntity.getStaffName());
        performanceReviewEntity.setPerPostName(staffUserEntity.getPostName());
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), performanceReviewEntity);
    }

    /**
     * 根据员工id和时间获取绩效考核表
     */
    @GetMapping("/get-perform-review/{staffId}/{time}")
    public Result<?> getPerformReview(@PathVariable String staffId, @PathVariable String time) {
        List<PerformanceReviewEntity> list = performanceReviewService.list(new QueryWrapper<PerformanceReviewEntity>().eq("per_staff", staffId).eq("per_time", time));
        list.forEach(item -> {
            StaffUserEntity staffUserEntity = staffUserService.getOne(new QueryWrapper<StaffUserEntity>().eq("staff_id", item.getPerStaff()));
            item.setPerStaffName(staffUserEntity.getStaffName());
            item.setPerPostName(staffUserEntity.getPostName());
        });
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), list);
    }

    /**
     * 获取尚未打分的绩效考核信息
     */
    @GetMapping("/get-unrated-perform-review")
    public Result<?> getUnratedPerformReview() {
        List<PerformanceReviewEntity> list = performanceReviewService.list(new QueryWrapper<PerformanceReviewEntity>().eq("per_result", ""));
        list.forEach(item -> {
            StaffUserEntity staffUserEntity = staffUserService.getOne(new QueryWrapper<StaffUserEntity>().eq("staff_id", item.getPerStaff()));
            item.setPerStaffName(staffUserEntity.getStaffName());
            item.setPerPostName(staffUserEntity.getPostName());
        });
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), list);
    }

    /**
     * 修改绩效考核信息
     */
    @PostMapping("/update-perform-review")
    public Result<?> updatePerformReview(@RequestBody PerformanceReviewEntity performanceReviewEntity) {
        //将该项的分数加到总分项
        PerformanceReviewEntity performanceReviewEntity1 = performanceReviewService.getOne(new QueryWrapper<PerformanceReviewEntity>().eq("per_staff", performanceReviewEntity.getPerStaff()).eq("per_time", performanceReviewEntity.getPerTime()).eq("per_remark", "总分"));
        performanceReviewEntity1.setPerResult(String.valueOf(Double.parseDouble(performanceReviewEntity1.getPerResult()) + Double.parseDouble(performanceReviewEntity.getPerResult())));
        performanceReviewService.updateById(performanceReviewEntity1);
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), performanceReviewService.updateById(performanceReviewEntity));
    }
}
