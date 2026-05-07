package org.yeh.wang.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色用户关联表;
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Getter
@Setter
@AllArgsConstructor
@Accessors(chain = true)
@TableName("user_role")
public class UserRoleEntity {

    /**
     * 角色编号
     */
    @TableField("role_id")
    private Integer roleId;

    /**
     * 用户编号
     */
    @TableField("user_id")
    private String userId;


}
