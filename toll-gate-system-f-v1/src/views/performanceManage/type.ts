/**
 * 绩效考核信息
 */
export type PerformanceInfo = {
    /**
     * 绩效考核内容及权重容器
     */
    contentWeight: Map<string, number> | {};
    /**
     * 绩效考核对象
     */
    perType: string;
    /**
     * 开始时间
     */
    startTime: string;
    /**
     * 结束时间
     */
    endTime: string;
}

/**
 * PerformanceReview
 */
export type PerformanceReview = {
    /**
     * 考核内容
     */
    perContent?: string;
    /**
     * 考核内容编号数组
     */
    perContentIds?: number[];
    /**
     * 考核编号
     */
    perId: number;
    /**
     * 考核结果
     */
    perResult?: string;
    /**
     * 考核职工
     */
    perStaff?: string;
    /**
     * 考核时间
     */
    perTime?: string;
    /**
     * 考核对象（收费员/车辆疏导员）
     */
    perType?: string;
    /**
     * 考核职工姓名
     */
    perStaffName?: string;
    /**
     * 职位名称
     */
    perPostName?: string;
    /**
     * 考核说明
     */
    perRemark?:string;
    [property: string]: any;
}
//PerformanceReview列表
export const performanceReviewList = ref([] as PerformanceReview[]);

/**
 * PerformType
 */
export type PerformType = {
    /**
     * 创建人
     */
    addPeople?: string;
    /**
     * 创建时间
     */
    addTime?: string;
    /**
     * 考核内容
     */
    content?: string;
    /**
     * 方式编号
     */
    typeId: number;
    /**
     * 所占权重
     */
    weight?: number;
    [property: string]: any;
}
//PerformType列表
export const performTypeList = ref([] as PerformType[]);
