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
 * 员工奖惩
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("re_pun_info")
public class RePunInfoEntity {

    /**
     * 奖惩编号
     */
    @TableId(value = "rew_id", type = IdType.AUTO)
    private Integer rewId;

    /**
     * 奖惩内容
     */
    @TableField("rew_content")
    private String rewContent;

    /**
     * 奖惩时间
     */
    @TableField("rew_time")
    private String rewTime;

    /**
     * 奖惩性质（红黑事件）
     */
    @TableField("rew_nature")
    private String rewNature;

    /**
     * 员工编号
     */
    @TableField("rew_staff")
    private String rewStaff;


}
