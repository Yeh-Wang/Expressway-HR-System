package org.yeh.wang.service;

import org.yeh.wang.entity.DailyPunchEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.yeh.wang.result.Result;

/**
 * <p>
 * 日常打卡表 服务类
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
public interface DailyPunchService extends IService<DailyPunchEntity> {


    /**
     * 获取所有日常打卡信息根据站长或班长编号
     * @param userId 站长或班长编号
     * @return 所有日常打卡信息
     */
    public Result<?> getAllDailyPunchByStaffId(String userId);
}
