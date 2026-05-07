package org.yeh.wang.service;

import org.yeh.wang.entity.UserRoleEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色用户关联表; 服务类
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
public interface UserRoleService extends IService<UserRoleEntity> {

    /**
     * 根据传入userId添加默认角色
     * @param userId 用户id
     * @return boolean
     */
    public boolean addUserRole(String userId);
}
