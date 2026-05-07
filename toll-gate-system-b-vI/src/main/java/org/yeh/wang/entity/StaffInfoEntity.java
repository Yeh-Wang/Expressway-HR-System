package org.yeh.wang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.yeh.wang.vo.RegisterVo;

import java.util.UUID;

/**
 * <p>
 * 职员
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Getter
@Setter
@Data
@Accessors(chain = true)
@TableName("staff_info")
public class StaffInfoEntity {

    /**
     * 职员编号
     */
    @TableId(value = "staff_id")
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
     * 职员地址
     */
    @TableField("staff_address")
    private String staffAddress;

    /**
     * 职员身份证号
     */
    @TableField("staff_card")
    private String staffCard;

    /**
     * 职员所在收费站
     */
    @TableField("staff_station")
    private String staffStation;

    // 将注册数据赋值给职员信息，创建职员信息
    public void setStaffInfoEntity(RegisterVo registerVo) {
        this.staffId = UUID.randomUUID().toString().replace("-", "");
        this.staffName = registerVo.getStaffName();
        this.staffNation = registerVo.getStaffNation();
        this.gender = registerVo.getGender();
        this.age = registerVo.getAge();
        this.staffDegree = registerVo.getStaffDegree();
        this.staffPost = registerVo.getStaffPost();
        this.staffPolitics = registerVo.getStaffPolitics();
        this.staffRemark = registerVo.getStaffRemark();
        this.staffPhone = registerVo.getStaffPhone();
        this.staffAddress = registerVo.getStaffAddress();
        this.staffCard = registerVo.getStaffCard();
    }

}
