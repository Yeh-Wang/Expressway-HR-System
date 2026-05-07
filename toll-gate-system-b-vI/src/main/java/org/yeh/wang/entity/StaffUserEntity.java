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
 * @since 2024-04-29 15:37:00
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("staff_user")
public class StaffUserEntity {

    /**
     * 职员编号
     */
    @TableField("staff_id")
    private String staffId;

    /**
     * 账号编号
     */
    @TableField("user_id")
    private String userId;

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
     * 岗位名称
     */
    @TableField("post_name")
    private String postName;

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
     * 职员头像
     */
    @TableField("staff_avatar")
    private String staffAvatar;

    /**
     * 职员籍贯
     */
    @TableField("staff_address")
    private String staffAddress;

    /**
     * 职员身份证号
     */
    @TableField("staff_card")
    private String staffCard;

    /**
     * 所属收费站
     */
    @TableField("staff_station")
    private String staffStation;


}
