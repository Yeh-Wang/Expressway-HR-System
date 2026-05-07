package org.yeh.wang.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色权限表;
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("role_auth")
public class RoleAuthEntity {

    /**
     * 角色编号
     */
    @TableField("role_id")
    private Integer roleId;

    /**
     * 权限编号
     */
    @TableField("auth_id")
    private Integer authId;


}
