package org.yeh.wang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yeh.wang.result.Result;
import org.yeh.wang.result.ResultEnum;
import org.yeh.wang.service.impl.RoleAuthViewServiceImpl;
import org.yeh.wang.service.impl.RoleInfoServiceImpl;

/**
 * 角色权限 前端控制器
 *
 * @author YehWang
 * @since 2024-04-30 14:22:46
 */
@RestController
@CrossOrigin
@RequestMapping("/role-auth-view-entity")
public class RoleAuthViewController {

    private RoleAuthViewServiceImpl roleAuthViewService;

    @Autowired
    public void setRoleAuthViewService(RoleAuthViewServiceImpl roleAuthViewService) {
        this.roleAuthViewService = roleAuthViewService;
    }


    /**
     * 获取所有角色权限信息
     * @return 所有角色权限信息
     */
    @GetMapping("/get-role-auth")
    public Result<?> getRoleAuth() {
        return new Result<>(ResultEnum.SUCCESS.getCode(), "获取成功", roleAuthViewService.list());
    }
}
