package org.yeh.wang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yeh.wang.entity.ApplyFeedbackEntity;
import org.yeh.wang.result.Result;
import org.yeh.wang.service.impl.ApplyFeedbackServiceImpl;

import java.util.List;

/**
 * 申请反馈前端控制器
 */
@RestController
@CrossOrigin
@RequestMapping("/apply-feedback-entity")
public class ApplyFeedbackController {

    private ApplyFeedbackServiceImpl applyFeedbackService;
    @Autowired
    public void setApplyFeedbackService(ApplyFeedbackServiceImpl applyFeedbackService) {
        this.applyFeedbackService = applyFeedbackService;
    }

    /**
     * 添加申请信息
     * @param applyFeedback 申请信息
     * @return 是否添加成功
     */
    @RequestMapping("/add-apply-feedback")
    public Result<?> addApplyFeedback(@RequestBody ApplyFeedbackEntity applyFeedback) {
        ApplyFeedbackEntity applyFeedbackEntity = new ApplyFeedbackEntity();
        applyFeedbackEntity.setApplyFeedbackEntity(applyFeedback);
        if (applyFeedbackService.save(applyFeedbackEntity)){
            return Result.success("添加成功", null);
        } else {
            return Result.failed("添加失败");
        }
    }

    /**
     * 根据申请编号删除申请信息
     * @param applyFeedbackId 申请编号
     * @return 是否删除成功
     */
    @RequestMapping("/delete-apply-feedback/{applyFeedbackId}")
    public Result<?> deleteApplyFeedback(@PathVariable Integer applyFeedbackId) {
        if (applyFeedbackService.removeById(applyFeedbackId)){
            return Result.success("删除成功", null);
        } else {
            return Result.failed("删除失败");
        }
    }

    /**
     * 修改申请信息
     * @param applyFeedback 申请信息
     * @return 是否修改成功
     */
    @RequestMapping("/update-apply-feedback")
    public Result<?> updateApplyFeedback(@RequestBody ApplyFeedbackEntity applyFeedback) {
        if (applyFeedbackService.updateById(applyFeedback)){
            return Result.success("修改成功", null);
        } else {
            return Result.failed("修改失败");
        }
    }

    /**
     * 根据职员编号获取该职员下属所有申请信息
     * @param staffId 职员编号
     * @return 该职员下属所有申请信息
     */
    @RequestMapping("/get-all-apply-feedback/{staffId}")
    public Result<?> getAllApplyFeedbackByStaffId(@PathVariable String staffId) {
        return Result.success(applyFeedbackService.listByStaffId(staffId));
    }

    /**
     * 获取所有请假信息
     */
    @RequestMapping("/get-all-apply-feedback/{processType}/{processView}")
    public Result<?> getAllApplyFeedback( @PathVariable int processType, @PathVariable String processView) {
        List<ApplyFeedbackEntity> applyFeedbackEntities = applyFeedbackService.getBaseMapper().selectByTypeView(processType, processView);
        return new Result<>(200, "获取成功", applyFeedbackEntities);
    }

    /**
     * 根据职员id获取所有申请信息
     */
    @RequestMapping("/get-all-apply-info-staff-id/{staffId}")
    public Result<?> getAllApplyInfoByStaffId(@PathVariable String staffId) {
        return new Result<>(200, "获取成功", applyFeedbackService.getBaseMapper().selectByStaffId(staffId));
    }
}
