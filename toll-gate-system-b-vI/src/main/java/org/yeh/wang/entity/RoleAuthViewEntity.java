package org.yeh.wang.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author YehWang
 * @since 2024-04-30 14:22:46
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("role_auth_view")
public class RoleAuthViewEntity {

    /**
     * 编号
     */
    @TableField("role_auth_id")
    private Integer roleAuthId;

    /**
     * 角色编号
     */
    @TableField("role_id")
    private Integer roleId;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 角色说明
     */
    @TableField("role_remark")
    private String roleRemark;

    /**
     * 权限编号
     */
    @TableField("auth_id")
    private Integer authId;

    /**
     * 权限名称
     */
    @TableField("auth_name")
    private String authName;

    /**
     * 权限说明
     */
    @TableField("auth_remark")
    private String authRemark;


}
