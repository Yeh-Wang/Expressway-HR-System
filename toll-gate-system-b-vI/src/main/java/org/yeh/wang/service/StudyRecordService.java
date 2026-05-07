package org.yeh.wang.service;

import org.yeh.wang.entity.StudyRecordEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.yeh.wang.vo.StudyRecordVo;

import java.util.List;

/**
 * <p>
 * 学习记录表 服务类
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
public interface StudyRecordService extends IService<StudyRecordEntity> {

    /**
     * 根据职员id获取所有学习记录
     */
    List<StudyRecordVo> selectAllStudyTime(String staffId, String startTime, String endTime);

    /**
     * 根据职员id获取指定培训计划学习记录
     */
    List<StudyRecordEntity> selectStudyTimeByTrain(String staffId, String startTime, String endTime, Integer trainId);

}
