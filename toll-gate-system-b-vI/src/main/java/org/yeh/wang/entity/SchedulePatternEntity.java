package org.yeh.wang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 排班方式表
 * </p>
 *
 * @author YehWang
 * @since 2024-05-09 15:47:09
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("schedule_pattern")
public class SchedulePatternEntity {

    /**
     * 班次编号
     */
    @TableId(value = "sch_id", type = IdType.AUTO)
    private Integer schId;

    /**
     * 班次名称
     */
    @TableField("sch_name")
    private String schName;

    /**
     * 班次开始时间
     */
    @TableField("sch_start")
    private String schStart;

    /**
     * 班次结束时间
     */
    @TableField("sch_end")
    private String schEnd;

    /**
     * 备注（备注适用于哪个岗位的班次）
     */
    @TableField("sch_remark")
    private String schRemark;


}
