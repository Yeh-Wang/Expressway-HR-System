package org.yeh.wang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 培训考核成绩表;
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("train_grade_info")
public class TrainGradeInfoEntity {

    /**
     * 培训成绩编号
     */
    @TableId(value = "gra_id", type = IdType.AUTO)
    private Integer graId;

    /**
     * 培训成绩说明
     */
    @TableField("gra_remark")
    private String graRemark;

    /**
     * 培训成绩
     */
    @TableField("gra_score")
    private String graScore;

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
     * 职员职位
     */
    @TableField(exist = false)
    private String postName;

    /**
     * 培训编号
     */
    @TableField("tra_id")
    private Integer traId;


}
