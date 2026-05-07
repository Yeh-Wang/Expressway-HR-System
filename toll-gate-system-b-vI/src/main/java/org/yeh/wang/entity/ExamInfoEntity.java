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
 * 考试表;
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("exam_info")
public class ExamInfoEntity {

    /**
     * 考试编号
     */
    @TableId(value = "ex_id", type = IdType.AUTO)
    private Integer exId;

    /**
     * 考试名称
     */
    @TableField("ex_name")
    private String exName;

    /**
     * 开始时间
     */
    @TableField("ex_start_time")
    private String exStartTime;

    /**
     * 结束时间
     */
    @TableField("ex_end_time")
    private String exEndTime;

    /**
     * 总时长
     */
    @TableField("ex_total_time")
    private Integer exTotalTime;

    /**
     * 培训编号
     */
    @TableField("tra_id")
    private Integer traId;

    /**
     * 试卷编号
     */
    @TableField("pap_id")
    private Integer papId;

    /**
     * 培训名称
     */
    @TableField(exist = false)
    private String traName;
}
