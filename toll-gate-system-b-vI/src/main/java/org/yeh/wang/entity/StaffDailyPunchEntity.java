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
 * @since 2024-04-30 23:09:03
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("staff_daily_punch")
public class StaffDailyPunchEntity {

    /**
     * 打卡记录编号
     */
    @TableField("pun_id")
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
    private String punWorkTime;

    /**
     * 职员名字
     */
    @TableField("staff_name")
    private String staffName;

    /**
     * 职员说明
     */
    @TableField("staff_remark")
    private String staffRemark;

    /**
     * 职员打卡地点
     */
    @TableField("staff_station")
    private String staffStation;

}
