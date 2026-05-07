package org.yeh.wang.service.impl;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.yeh.wang.entity.TrainInfoEntity;
import org.yeh.wang.mapper.TrainInfoMapper;
import org.yeh.wang.service.TrainInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 培训计划表; 服务实现类
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Service
public class TrainInfoServiceImpl extends ServiceImpl<TrainInfoMapper, TrainInfoEntity> implements TrainInfoService {

    @Override
    public void test() {
        System.out.println("定时任务测试");
    }
}
