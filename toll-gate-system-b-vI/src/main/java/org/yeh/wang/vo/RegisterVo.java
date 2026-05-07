package org.yeh.wang.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RegisterVo {
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
     * 职员名字
     */
    @TableField("staff_name")
    private String staffName;

    /**
     * 职员民族
     */
    @TableField("staff_nation")
    private String staffNation;

    /**
     * 职员性别
     */
    @TableField("gender")
    private String gender;

    /**
     * 职员年龄
     */
    @TableField("age")
    private Integer age;

    /**
     * 职员学历
     */
    @TableField("staff_degree")
    private String staffDegree;

    /**
     * 职员职位
     */
    @TableField("staff_post")
    private Integer staffPost;

    /**
     * 政治面貌
     */
    @TableField("staff_politics")
    private String staffPolitics;

    /**
     * 职员说明
     */
    @TableField("staff_remark")
    private String staffRemark;

    /**
     * 职员电话
     */
    @TableField("staff_phone")
    private String staffPhone;

    /**
     * 职员地址
     */
    @TableField("staff_address")
    private String staffAddress;

    /**
     * 职员身份证号
     */
    @TableField("staff_card")
    private String staffCard;
}
