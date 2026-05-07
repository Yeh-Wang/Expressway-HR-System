package org.yeh.wang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 公告
 * </p>
 *
 * @author YehWang
 * @since 2024-04-26 11:40:45
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("public_notice")
public class PublicNoticeEntity {

    /**
     * 公告编号
     */
    @TableId(value = "pub_id", type = IdType.AUTO)
    private Integer pubId;

    /**
     * 公告内容
     */
    @TableField("pub_content")
    private String pubContent;

    /**
     * 公告发布时间
     */
    @TableField("pub_time")
    private String pubTime;

    /**
     * 公告创建者编号
     */
    @TableField("pub_create")
    private String pubCreate;

    /**
     * 公告名称
     */
    @TableField("pub_title")
    private String pubTitle;

    /**
     * 公告状态
     */
    @TableField("pub_status")
    private Integer pubStatus;

}
