package org.yeh.wang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 考勤表
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Getter
@Setter
@ToString
@Data
@Accessors(chain = true)
@TableName("check_sheet")
public class CheckSheetEntity {

    /**
     * 考勤号
     */
    @TableId(value = "ch_id", type = IdType.AUTO)
    private Integer chId;

    /**
     * 月份
     */
    @TableField("ch_month")
    private String chMonth;

    /**
     * 出任务次数
     */
    @TableField("ch_task_times")
    private Integer chTaskTimes;

    /**
     * 请假天数
     */
    @TableField("ch_vacate_day")
    private double chVacateDay;

    /**
     * 加班时长
     */
    @TableField("ch_over_time")
    private double chOverTime;

    /**
     * 应出勤天数
     */
    @TableField("ch_set_day")
    private Integer chSetDay;

    /**
     * 实出勤天数
     */
    @TableField("ch_real_day")
    private Integer chRealDay;

    /**
     * 职工编号
     */
    @TableField("staff_id")
    private String staffId;


}
