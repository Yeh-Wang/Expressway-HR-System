package org.yeh.wang.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yeh.wang.entity.ApplyFeedbackEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.yeh.wang.result.Result;

/**
 * <p>
 * 职员申请或反馈表 服务类
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
public interface ApplyFeedbackService extends IService<ApplyFeedbackEntity> {


    /**
     * 根据职员编号获取该职员下属所有申请信息
     *
     * @param staffId 职员编号
     * @return 该职员下属所有申请信息
     */
    public Result<?> listByStaffId(String staffId);

    /**
     * 根据时间和职员编号判断该职员是否有申请信息
     */
    public boolean isApply(String staffId, String time);
}
