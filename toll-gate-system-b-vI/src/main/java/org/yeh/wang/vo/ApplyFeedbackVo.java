package org.yeh.wang.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import org.yeh.wang.entity.ApplyFeedbackEntity;

/**
 * 申请反馈Vo
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplyFeedbackVo{

    /**
     * 流程编号
     */
    private Integer processId;
    /**
     * 流程内容
     */
    private String processContent;
    /**
     * 流程创建时间
     */
    private String processTime;
    /**
     * 流程类型（请假，工作反馈）
     */
    private Integer processType;
    /**
     * 上级意见（上级打分）
     */
    private String cfoView;
    /**
     * 上级处理时间
     */
    private String cfoTime;
    /**
     * 上级编号
     */
    private String cfoId;
    /**
     * 员工是否确认（仅请假时填写，代表请假天数）
     */
    private double isVerify;
    /**
     * 职员编号
     */
    private String staffId;
    /**
     * 申请人姓名
     */
    private String staffName;
    /**
     * 负责的领导姓名
     */
    private String cfoName;

}
