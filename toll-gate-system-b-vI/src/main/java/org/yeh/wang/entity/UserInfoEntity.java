package org.yeh.wang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.yeh.wang.vo.RegisterVo;

import java.util.UUID;

/**
 * <p>
 * 账户表;
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_info")
public class UserInfoEntity {

    /**
     * 用户编号
     */
    @TableId(value = "user_id")
    private String userId;

    /**
     * 用户昵称
     */
    @TableField("user_nickname")
    private String userNickname;

    /**
     * 用户密码
     */
    @TableField("user_pwd")
    private String userPwd;

    /**
     * 职员编号
     */
    @TableField("staff_id")
    private String staffId;

    //将注册信息转换为用户信息，并创建用户
    public void setUserInfoEntity(RegisterVo registerVo){
        this.userId = UUID.randomUUID().toString().replace("-", "");
        this.userNickname = registerVo.getUserNickname();
        this.userPwd = registerVo.getUserPwd();
    }
}
