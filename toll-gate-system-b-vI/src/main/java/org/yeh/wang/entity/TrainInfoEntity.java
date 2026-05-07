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
 * 培训计划表;
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("train_info")
public class TrainInfoEntity {

    /**
     * 培训编号
     */
    @TableId(value = "tra_id", type = IdType.AUTO)
    private Integer traId;

    /**
     * 培训类型
     */
    @TableField("tra_type")
    private String traType;

    /**
     * 开始时间
     */
    @TableField("tra_start_time")
    private String traStartTime;

    /**
     * 结束时间
     */
    @TableField("tra_end_time")
    private String traEndTime;

    /**
     * 培训模式
     */
    @TableField("tra_mode")
    private String traMode;

    /**
     * 培训主题
     */
    @TableField("tra_theme")
    private String traTheme;

    /**
     * 计划状态
     */
    @TableField("tra_status")
    private String traStatus;


}
