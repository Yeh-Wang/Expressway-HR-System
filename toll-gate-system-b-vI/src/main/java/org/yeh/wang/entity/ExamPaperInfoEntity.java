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
 * 考试试卷表;
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("exam_paper_info")
public class ExamPaperInfoEntity {

    /**
     * 试卷编号
     */
    @TableId(value = "pap_id", type = IdType.AUTO)
    private Integer papId;

    /**
     * 试卷说明
     */
    @TableField("pap_remark")
    private String papRemark;

    /**
     * 试卷总题目数
     */
    @TableField("pap_total_issues")
    private Integer papTotalIssues;

    /**
     * 试卷总分
     */
    @TableField("pap_total_score")
    private Integer papTotalScore;

    /**
     * 试卷问题编号数组
     */
    @TableField("pap_issue_ids")
    private String papIssueIds;

    /**
     * 试卷问题编号数组
     */
    @TableField(exist = false)
    private Integer[] papQuestionIds;

}
