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
 * 绩效考核方式及权重
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("perform_type")
public class PerformTypeEntity {

    /**
     * 方式编号
     */
    @TableId(value = "type_id", type = IdType.AUTO)
    private Integer typeId;

    /**
     * 考核内容
     */
    @TableField("content")
    private String content;

    /**
     * 所占权重
     */
    @TableField("weight")
    private double weight;

    /**
     * 创建人
     */
    @TableField("add_people")
    private String addPeople;

    /**
     * 创建时间
     */
    @TableField("add_time")
    private String addTime;


}
