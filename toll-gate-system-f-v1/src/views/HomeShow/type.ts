
/**
 * PublicNoticeVo
 */
export type PublicNoticeVo = {
    /**
     * 公告内容
     */
    pubContent?: null | string;
    /**
     * 公告创建者编号
     */
    pubCreate?: null | string;
    /**
     * 公告发布人姓名
     */
    pubCreateName?: null | string;
    /**
     * 公告编号
     */
    pubId?: number | null;
    /**
     * 公告状态
     */
    pubStatus?: number | null;
    /**
     * 公告发布时间
     */
    pubTime?: null | string;
    /**
     * 公告名称
     */
    pubTitle?: null | string;
    [property: string]: any;
}

export const listPublicNotice = reactive({
    list: [] as PublicNoticeVo[]
})
