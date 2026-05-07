package org.yeh.wang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 日常打卡表
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("daily_punch")
public class DailyPunchEntity {

    /**
     * 打卡记录编号
     */
    @TableId(value = "pun_id", type = IdType.AUTO)
    private Integer punId;

    /**
     * 打卡类型
     */
    @TableField("pun_type")
    private Integer punType;

    /**
     * 上班打卡时间
     */
    @TableField("pun_start_time")
    private String punStartTime;

    /**
     * 下班打卡时间
     */
    @TableField("pun_end_time")
    private String punEndTime;

    /**
     * 上班时长
     */
    @TableField("pun_work_time")
    private double punWorkTime;

    /**
     * 职员编号
     */
    @TableField("staff_id")
    private String staffId;

    /**
     * 打卡说明（打卡地点）
     */
    @TableField("pun_remark")
    private String punRemark;

    /**
     * 打卡状态（正常打卡/迟到/早退）
     */
    @TableField("pun_status")
    private String punStatus;

}
