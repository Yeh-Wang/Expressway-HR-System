package org.yeh.wang.service;

import org.yeh.wang.entity.TrainGradeInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 培训考核成绩表; 服务类
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
public interface TrainGradeInfoService extends IService<TrainGradeInfoEntity> {

    /**
     * 根据培训计划id查询学员培训成绩信息
     */
    List<TrainGradeInfoEntity> getTrainGradeInfoByTrainInfoId(Integer trainInfoId);
}
