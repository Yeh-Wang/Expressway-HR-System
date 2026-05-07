package org.yeh.wang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 职员申请或反馈表
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("apply_feedback")
public class ApplyFeedbackEntity {

    /**
     * 流程编号
     */
    @TableId(value = "process_id", type = IdType.AUTO)
    private Integer processId;

    /**
     * 流程内容
     */
    @TableField("process_content")
    private String processContent;

    /**
     * 流程创建时间
     */
    @TableField("process_time")
    private String processTime;

    /**
     * 流程类型（请假，工作反馈）
     */
    @TableField("process_type")
    private Integer processType;

    /**
     * 上级意见（上级打分）
     */
    @TableField("cfo_view")
    private String cfoView;

    /**
     * 上级处理时间
     */
    @TableField("cfo_time")
    private String cfoTime;

    /**
     * 上级编号
     */
    @TableField("cfo_id")
    private String cfoId;

    /**
     * 上级姓名
     */
    @TableField(exist = false)
    private String cfoName;

    /**
     * 员工是否确认（仅请假时填写，代表请假天数）
     */
    @TableField("is_verify")
    private double isVerify;

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

    public void setApplyFeedbackEntity(ApplyFeedbackEntity applyFeedbackEntity) {
        this.processContent = applyFeedbackEntity.getProcessContent();
        this.processTime = applyFeedbackEntity.getProcessTime();
        this.processType = applyFeedbackEntity.getProcessType();
        this.cfoView = applyFeedbackEntity.getCfoView();
        this.cfoTime = applyFeedbackEntity.getCfoTime();
        this.cfoId = applyFeedbackEntity.getCfoId();
        this.isVerify = applyFeedbackEntity.getIsVerify();
        this.staffId = applyFeedbackEntity.getStaffId();
    }
}
