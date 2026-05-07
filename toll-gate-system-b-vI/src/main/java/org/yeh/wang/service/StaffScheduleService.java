package org.yeh.wang.service;

import org.yeh.wang.entity.SchedulePatternEntity;
import org.yeh.wang.entity.StaffScheduleEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 职员排班表 服务类
 * </p>
 *
 * @author YehWang
 * @since 2024-05-09 15:47:09
 */
public interface StaffScheduleService extends IService<StaffScheduleEntity> {


    /**
     * 自动排班
     */
    List<StaffScheduleEntity> autoSchedule(List<String> dates);

    /**
     * 获取所有排班信息
     */
    List<StaffScheduleEntity> getAll();
}
