package org.yeh.wang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 职员排班表
 * </p>
 *
 * @author YehWang
 * @since 2024-05-09 15:47:09
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("staff_schedule")
@ToString
public class StaffScheduleEntity {

    /**
     * 排班编号
     */
    @TableId(value = "pro_id", type = IdType.AUTO)
    private Integer proId;

    /**
     * 班次员工
     */
    @TableField("pro_staff")
    private String proStaff;

    /**
     * 员工姓名
     */
    @TableField(exist = false)
    private String staffName;

    /**
     * 排班方式编号
     */
    @TableField("pro_schedule")
    private Integer proSchedule;

    /**
     * 排班开始时间
     */
    @TableField(exist = false)
    private String schStart;

    /**
     * 排班结束时间
     */
    @TableField(exist = false)
    private String schEnd;

    /**
     * 值班岗位信息
     */
    @TableField("pro_post")
    private Integer proPost;

    /**
     * 值班岗位名称
     */
    @TableField(exist = false)
    private String dutyName;

    /**
     * 分配日期
     */
    @TableField("pro_time")
    private String proTime;


}
