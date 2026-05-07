package org.yeh.wang.service;

import org.yeh.wang.dto.PerformanceInfoDto;
import org.yeh.wang.entity.PerformanceReviewEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.yeh.wang.entity.RePunInfoEntity;
import org.yeh.wang.result.Result;

import java.util.List;

/**
 * <p>
 * 绩效考核 服务类
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
public interface PerformanceReviewService extends IService<PerformanceReviewEntity> {


    /**
     * 获取所有奖惩信息根据站长或班长编号
     * @param staffId 站长或班长员工编号
     * @return 所有奖惩考核信息
     */
    public Result<?> getAllPunInfoByStaffId(String staffId);

    /**
     * 添加奖惩信息
     */
    public Result<?> addRePunInfo(RePunInfoEntity rePunInfo);

    /**
     * 根据奖惩信息id删除奖惩信息
     * @param rewId 奖惩信息id
     */
    public Result<?> deleteRePunInfo(Integer rewId);

    /**
     * 根据职员id获取职员绩效考核信息
     */
    public List<PerformanceReviewEntity> getPerformReviewByStaffId(String staffId);

    /**
     * 生成绩效考核结果表
     */
    public List<PerformanceReviewEntity> generatePerformReview(PerformanceInfoDto performanceInfoDto);
}
