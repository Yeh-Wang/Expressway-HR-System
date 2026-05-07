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
 * @since 2024-04-28 10:45:31
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("user_role_view")
public class UserRoleViewEntity {

    /**
     * 用户编号
     */
    @TableField("user_id")
    private String userId;

    /**
     * 用户昵称
     */
    @TableField("username")
    private String username;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;


}
