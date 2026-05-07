package org.yeh.wang.service;

import org.yeh.wang.entity.StaffInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.yeh.wang.result.Result;

import java.util.List;

/**
 * <p>
 * 职员 服务类
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
public interface StaffInfoService extends IService<StaffInfoEntity> {


    //add
    public boolean add(StaffInfoEntity staffInfoEntity);

    /**
     * 管理员添加职员
     * @param staffInfoEntity 职员信息
     * @return boolean
     */
    public Result<?> addStaff(StaffInfoEntity staffInfoEntity);

    /**
     * 根据登录用户编号获取职员信息
     * @param staffId 登录用户编号
     * @return 职员信息
     */
    public List<StaffInfoEntity> getStaffInfoByStaffId(String staffId);
}
