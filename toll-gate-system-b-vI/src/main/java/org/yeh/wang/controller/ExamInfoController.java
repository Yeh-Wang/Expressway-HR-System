package org.yeh.wang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yeh.wang.entity.ExamInfoEntity;
import org.yeh.wang.entity.ExamPaperInfoEntity;
import org.yeh.wang.result.Result;
import org.yeh.wang.result.ResultEnum;
import org.yeh.wang.service.impl.ExamInfoServiceImpl;
import org.yeh.wang.service.impl.ExamPaperInfoServiceImpl;
import org.yeh.wang.service.impl.TrainInfoServiceImpl;

import java.util.List;

/**
 * 考试信息控制器
 */
@RestController
@CrossOrigin
@RequestMapping("/exam-info")
public class ExamInfoController {

    private ExamInfoServiceImpl examInfoService;
    private ExamPaperInfoServiceImpl examPaperInfoService;
    private TrainInfoServiceImpl trainInfoService;

    @Autowired
    public void setExamInfoService(ExamInfoServiceImpl examInfoService) {
        this.examInfoService = examInfoService;
    }

    @Autowired
    public void setExamPaperInfoService(ExamPaperInfoServiceImpl examPaperInfoService) {
        this.examPaperInfoService = examPaperInfoService;
    }
    @Autowired
    public void setTrainInfoService(TrainInfoServiceImpl trainInfoService) {
        this.trainInfoService = trainInfoService;
    }

    /**
     * 根据培训编号获取考试信息列表
     */
    @RequestMapping("/get-exam-info-list")
    public Result<?> getExamInfoList(@RequestParam Integer traId) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), examInfoService.list(new QueryWrapper<ExamInfoEntity>().eq("tra_id", traId)));
    }

    /**
     * 获取所有考试信息列表
     */
    @RequestMapping("/get-all-exam-info-list")
    public Result<?> getAllExamInfoList() {
        List<ExamInfoEntity> examInfoEntityList = examInfoService.list();
        examInfoEntityList.forEach(examInfoEntity -> {
            examInfoEntity.setTraName(trainInfoService.getById(examInfoEntity.getTraId()).getTraTheme());
        });
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), examInfoEntityList);
    }

    /**
     * 添加考试信息
     */
    @RequestMapping("/add-exam-info")
    public Result<?> addExamInfo(@RequestBody ExamInfoEntity examInfoEntity) {
        examInfoService.save(examInfoEntity);
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), examInfoEntity);
    }

    /**
     * 修改考试信息
     */
    @RequestMapping("/update-exam-info")
    public Result<?> updateExamInfo(@RequestBody ExamInfoEntity examInfoEntity) {
        examInfoService.updateById(examInfoEntity);
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), examInfoEntity);
    }

    /**
     * 增加试卷信息
     */
    @RequestMapping("/add-exam-paper-info")
    public Result<?> addExamPaperInfo(@RequestBody ExamPaperInfoEntity examPaperInfoEntity) {
        examPaperInfoService.save(examPaperInfoEntity);
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), examPaperInfoEntity);
    }

    /**
     * 修改试卷信息
     */
    @RequestMapping("/update-exam-paper-info")
    public Result<?> updateExamPaperInfo(@RequestBody ExamPaperInfoEntity examPaperInfoEntity) {
        //题目编号数组转字符串
//        if (examPaperInfoEntity.getPapQuestionIds() != null) {
//            StringBuilder papIssueIds = new StringBuilder();
//            for (Integer papQuestionId : examPaperInfoEntity.getPapQuestionIds()) {
//                papIssueIds.append(papQuestionId).append(",");
//            }
//            examPaperInfoEntity.setPapIssueIds(papIssueIds.toString());
//        }
        examPaperInfoService.updateById(examPaperInfoEntity);
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), examPaperInfoEntity);
    }

    /**
     * 根据试卷编号获取试卷信息
     */
    @RequestMapping("/get-exam-paper-info")
    public Result<?> getExamPaperInfo(@RequestParam Integer papId) {
//        题目编号字符串转数组
        ExamPaperInfoEntity examPaperInfoEntity = examPaperInfoService.getById(papId);
        if (examPaperInfoEntity.getPapIssueIds() != null) {
            String[] papIssueIds = examPaperInfoEntity.getPapIssueIds().split(",");
            Integer[] papQuestionIds = new Integer[papIssueIds.length];
            for (int i = 0; i < papIssueIds.length; i++) {
                papQuestionIds[i] = Integer.parseInt(papIssueIds[i]);
            }
            examPaperInfoEntity.setPapQuestionIds(papQuestionIds);
        }
        return new Result<>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), examPaperInfoEntity);
    }
}
