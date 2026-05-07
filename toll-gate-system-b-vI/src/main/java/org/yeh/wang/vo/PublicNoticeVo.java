package org.yeh.wang.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class PublicNoticeVo {
    /**
     * 公告编号
     */
    private Integer pubId;
    /**
     * 公告内容
     */
    private String pubContent;
    /**
     * 公告发布时间
     */
    private String pubTime;
    /**
     * 公告创建者编号
     */
    private String pubCreate;
    /**
     * 公告名称
     */
    private String pubTitle;
    /**
     * 公告状态
     */
    private Integer pubStatus;
    /**
     * 公告发布人姓名
     */
    private String pubCreateName;
}
