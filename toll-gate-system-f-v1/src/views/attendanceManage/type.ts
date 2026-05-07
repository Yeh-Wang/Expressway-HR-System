/**
 * StaffDailyPunchEntity
 */
export type StaffDailyPunch = {
    /**
     * 下班打卡时间
     */
    punEndTime: null | string;
    /**
     * 打卡记录编号
     */
    punId: number | null;
    /**
     * 上班打卡时间
     */
    punStartTime: null | string;
    /**
     * 打卡类型
     */
    punType: number | null;
    /**
     * 上班时长
     */
    punWorkTime: null | number;
    /**
     * 职员名字
     */
    staffName: string;
    /**
     * 职员说明
     */
    staffRemark: null | string;
    /**
     * 职员打卡地点
     */
    staffStation: null | string;
}
export type dailyPunch = {
    /**
     * 下班打卡时间
     */
    punEndTime: null | string;
    /**
     * 打卡记录编号
     */
    punId: number | null;
    /**
     * 上班打卡时间
     */
    punStartTime: null | string;
    /**
     * 打卡类型
     */
    punType: number | null;
    /**
     * 上班时长
     */
    punWorkTime: null | number;
    /**
     * 职员编号
     */
    staffId: string;
    /**
     * 职员打卡地点
     */
    punRemark: null | string;
    /**
     * 打卡状态（正常打卡/迟到/早退）
     */
    punStatus?: string;
}

//表格的列
export const tableColumns = [
    {
        label: '员工姓名',
        prop: 'staffName',
    },
    // {
    //     label: '员工说明',
    //     prop: 'staffRemark',
    // },
    {
        label: '打卡地点',
        prop: 'staffStation',
    },
    {
        label: '上班打卡时间',
        prop: 'punStartTime',
    },
    {
        label: '下班打卡时间',
        prop: 'punEndTime',
    },
    // {
    //     label: '打卡类型',
    //     prop: 'punType',
    // },
    {
        label: '上班时长(小时)',
        prop: 'punWorkTime',
    },
]

/**
 * CheckSheet
 */
export type CheckSheet = {
    /**
     * 考勤号
     */
    chId: number;
    /**
     * 月份
     */
    chMonth?: string;
    /**
     * 加班时长（后改成总工时）<待定>
     */
    chOverTime?: number;
    /**
     * 实出勤天数
     */
    chRealDay?: number;
    /**
     * 应出勤天数
     */
    chSetDay?: number;
    /**
     * 出任务次数
     */
    chTaskTimes?: number;
    /**
     * 请假天数
     */
    chVacateDay?: number;
    /**
     * 职工编号
     */
    staffId: string;
    /**
     * 职工姓名
     */
    staffName?: string;
}

//请假申请表
export type ApplyFeedback = {
    "processId": number,
    "processContent": string,
    "processTime": string,
    "processType": number,
    "cfoView": string,
    "cfoTime": string,
    "cfoId": string,
    "cfoName": string,
    "isVerify": number,
    "staffId": string,
    "staffName": string
}
export const applyFeedbackList = reactive({
    applyFeedback: [] as ApplyFeedback[],
})
