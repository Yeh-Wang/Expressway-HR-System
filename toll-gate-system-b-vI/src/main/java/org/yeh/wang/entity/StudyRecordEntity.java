package org.yeh.wang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学习记录表
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("study_record")
public class StudyRecordEntity {

    /**
     * 记录编号
     */
    @TableId(value = "stu_id", type = IdType.AUTO)
    private Integer stuId;

    /**
     * 学习时长
     */
    @TableField("stu_total_time")
    private double stuTotalTime;

    /**
     * 开始时间
     */
    @TableField("stu_start_time")
    private String stuStartTime;

    /**
     * 结束时间
     */
    @TableField("stu_end_time")
    private String stuEndTime;

    /**
     * 职员编号
     */
    @TableField("staff_id")
    private String staffId;

    /**
     * 职员姓名
     */
    @TableField(exist = false)
    private String staffName;

    /**
     * 所属培训计划编号
     */
    @TableField("stu_train")
    private Integer stuTrain;

    /**
     * 所属培训计划名称
     */
    @TableField(exist = false)
    private String trainName;

}
