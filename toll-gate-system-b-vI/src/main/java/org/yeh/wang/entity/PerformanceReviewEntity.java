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
 * 绩效考核
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("performance_review")
public class PerformanceReviewEntity {

    /**
     * 考核编号
     */
    @TableId(value = "per_id", type = IdType.AUTO)
    private Integer perId;

    /**
     * 考核类型
     */
    @TableField("per_type")
    private String perType;

    /**
     * 考核内容
     */
    @TableField("per_content")
    private String perContent;

    /**
     * 考核时间
     */
    @TableField("per_time")
    private String perTime;

    /**
     * 考核职工
     */
    @TableField("per_staff")
    private String perStaff;

    /**
     * 考核职工姓名
     */
    @TableField(exist = false)
    private String perStaffName;

    /**
     * 职位名称
     */
    @TableField(exist = false)
    private String perPostName;

    /**
     * 考核结果
     */
    @TableField("per_result")
    private String perResult;

    /**
     * 考核说明
     */
    @TableField("per_remark")
    private String perRemark;


}
