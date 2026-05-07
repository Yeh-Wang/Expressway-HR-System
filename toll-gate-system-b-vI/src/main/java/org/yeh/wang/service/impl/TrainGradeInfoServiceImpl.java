package org.yeh.wang.service.impl;

import org.yeh.wang.entity.TrainGradeInfoEntity;
import org.yeh.wang.mapper.TrainGradeInfoMapper;
import org.yeh.wang.service.TrainGradeInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 培训考核成绩表; 服务实现类
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Service
public class TrainGradeInfoServiceImpl extends ServiceImpl<TrainGradeInfoMapper, TrainGradeInfoEntity> implements TrainGradeInfoService {

    @Override
    public List<TrainGradeInfoEntity> getTrainGradeInfoByTrainInfoId(Integer trainInfoId) {
        return this.baseMapper.getTrainGradeInfoByTrainInfoId(trainInfoId);
    }
}
