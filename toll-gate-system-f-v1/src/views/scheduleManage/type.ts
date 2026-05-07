
// 排班管理-排班信息
export type StaffSchedule = {
    proId: number;  // 排班编号
    proStaff?: string;  //员工编号
    staffName?: string;  //员工姓名
    proSchedule?: number;  //班次编号
    schStart?: string;  //班次开始时间
    schEnd?: string;  //班次结束时间
    proPost?: number;  //值班岗位编号
    dutyName?: string;  //值班岗位名称
    proTime?: string;  //值班日期
    [property: string]: any;
}

// 排班管理-排班方式信息
export type SchedulePattern = {
    schId: number;  // 班次编号
    schName?: string;  // 班次名称
    schRemark?: string;  // 备注（备注适用于哪个岗位的班次）
    schStart?: string;  // 班次开始时间
    schEnd?: string; // 班次结束时间
    // timeRange?: [];  // 班次时间范围
    [property: string]: any;
}

// 排班管理-值班岗位信息
export type NeedDuty = {
    dutyId: number;  // 岗位编号
    dutyName: string;  // 岗位名称
    dutyRemark?: string;  // 岗位说明
    [property: string]: any;
}

// 排班管理-指定岗位的职员信息
export type ScheduleStaff = {
    staffId: string;  // 员工编号
    staffName: string;  // 员工姓名
    [property: string]: any;
}
