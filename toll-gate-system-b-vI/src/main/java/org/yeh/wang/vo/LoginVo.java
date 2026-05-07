package org.yeh.wang.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {

    /**
     * 用户编号
     */
    private String userId;
    /**
     * 职员编号
     */
    private String staffId;
    /**
     * 职员头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户真实姓名
     */
    private String username;

    /**
     * 用户所属角色数组
     */
    private String[] roles;

    /**
     * 用户Token
     */
    private String token;

}
