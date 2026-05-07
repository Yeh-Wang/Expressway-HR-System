package org.yeh.wang.service.impl;

import org.yeh.wang.entity.UserRoleEntity;
import org.yeh.wang.mapper.UserRoleMapper;
import org.yeh.wang.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色用户关联表; 服务实现类
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleEntity> implements UserRoleService {

    @Override
    public boolean addUserRole(String userId) {
        return this.save(new UserRoleEntity(3, userId));
    }
}
