package org.yeh.wang.service;

import org.yeh.wang.entity.CheckSheetEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.yeh.wang.result.Result;

/**
 * <p>
 * 考勤表 服务类
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
public interface CheckSheetService extends IService<CheckSheetEntity> {

    /**
     * 定时每月最后一天23点执行一次，生成每个职员一周的考勤报表
     */
    void generateCheckSheet();

    /**
     * 给与站长编号和月份生成本站的考勤表（站长专属）
     * @param staffId 职员编号
     * @param month 月份
     */
    public void generateCheckSheetByStation(String staffId,String month);

    /**
     * 根据职员id查询指定月份的考勤表
     * @param staffId 职员编号
     * @param month 月份
     */
    public Result<?> getCheckSheetByStaff(String staffId, String month);

    /**
     * 站长获取所有考勤表
     */
    public Result<?> getAllCheckSheet();
}
