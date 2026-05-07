package org.yeh.wang.service;

import org.yeh.wang.entity.TrainInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 培训计划表; 服务类
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
public interface TrainInfoService extends IService<TrainInfoEntity> {


    //定时任务测试
    void test();
}
