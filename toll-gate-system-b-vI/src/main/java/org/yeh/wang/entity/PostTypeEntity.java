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
 * 岗位信息表
 * </p>
 *
 * @author YehWang
 * @since 2024-05-09 15:47:09
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("post_type")
public class PostTypeEntity {

    /**
     * 岗位编号
     */
    @TableId(value = "post_id", type = IdType.AUTO)
    private Integer postId;

    /**
     * 岗位名称
     */
    @TableField("post_name")
    private String postName;

    /**
     * 岗位说明
     */
    @TableField("post_remark")
    private String postRemark;


}
