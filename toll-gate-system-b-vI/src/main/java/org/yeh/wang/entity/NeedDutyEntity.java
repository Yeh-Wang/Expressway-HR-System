package org.yeh.wang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 需要值班的地点信息
 * </p>
 *
 * @author YehWang
 * @since 2024-05-28 15:43:31
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("need_duty")
@ToString
public class NeedDutyEntity {

    /**
     * 地点编号
     */
    @TableId(value = "duty_id", type = IdType.AUTO)
    private Integer dutyId;

    /**
     * 地点名称
     */
    @TableField("duty_name")
    private String dutyName;

    /**
     * 地点说明
     */
    @TableField("duty_remark")
    private String dutyRemark;


}
