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
 * 学习资源表;
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("study_src")
public class StudySrcEntity {

    /**
     * 资源编号
     */
    @TableId(value = "src_id", type = IdType.AUTO)
    private Integer srcId;

    /**
     * 资源类型标识
     */
    @TableField("src_type")
    private String srcType;

    /**
     * 题目
     */
    @TableField("src_title")
    private String srcTitle;

    /**
     * 答案
     */
    @TableField("src_answer")
    private String srcAnswer;

    /**
     * 分值
     */
    @TableField("src_score")
    private Integer srcScore;

    /**
     * 选项
     */
    @TableField("src_select")
    private String srcSelect;

    /**
     * 答案解析
     */
    @TableField("src_analysis")
    private String srcAnalysis;

    /**
     * 所属培训
     */
    @TableField("src_train")
    private Integer srcTrain;


}
