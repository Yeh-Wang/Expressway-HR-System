
/**
 * TrainInfo 培训计划实体
 */
export type TrainInfo = {
    /**
     * 培训编号
     */
    traId: number;
    /**
     * 培训模式
     */
    traMode?: number;
    /**
     * 开始时间
     */
    traStartTime?: string;
    /**
     * 结束时间
     */
    traEndTime?: string;
    /**
     * 计划状态
     */
    traStatus?: string;
    /**
     * 培训主题
     */
    traTheme?: string;
    /**
     * 培训类型
     */
    traType?: number;
    [property: string]: any;
}

/**
 * StudySrc  学习资源实体
 */
export type StudySrc = {
    /**
     * 资源编号
     */
    srcId?: number;
    /**
     * 分值
     */
    srcScore?: number;
    /**
     * 选项
     */
    srcSelect?: string;
    /**
     * 题目
     */
    srcTitle?: string;
    /**
     * 答案解析
     */
    srcAnalysis?: string;
    /**
     * 答案
     */
    srcAnswer?: string;
    /**
     * 所属培训计划
     */
    srcTrain?: number;
    /**
     * 资源类型标识 （1：单选题，2：判断题）
     */
    srcType?: string;
    [property: string]: any;
}

/**
 * StudyRecord 学习记录实体
 */
export type StudyRecord = {
    /**
     * 职员编号
     */
    staffId?: string;
    /**
     * 结束时间
     */
    stuEndTime?: string;
    /**
     * 记录编号
     */
    stuId: number;
    /**
     * 开始时间
     */
    stuStartTime?: string;
    /**
     * 学习时长
     */
    stuTotalTime?: number;
    /**
     * 职员姓名
     */
    staffName?: string;
    /**
     * 所属培训计划编号
     */
    stuTrain?: number;
    /**
     * 所属计划名称
     */
    trainName?: string;
    [property: string]: any;
}

/**
 * TrainGradeInfo
 */
export type TrainGradeInfo = {
    /**
     * 培训成绩编号
     */
    graId: number;
    /**
     * 培训成绩说明
     */
    graRemark?: string;
    /**
     * 培训成绩
     */
    graScore?: string;
    /**
     * 职员编号
     */
    staffId?: string;
    /**
     * 职员姓名
     */
    staffName?: string;
    /**
     * 职员岗位
     */
    postName?: string;

    /**
     * 培训编号
     */
    traId?: number;
    [property: string]: any;
}

/**
 * ExamInfo
 */
export type ExamInfo = {
    /**
     * 考试编号
     */
    exId: number;
    /**
     * 考试名称
     */
    exName?: string;
    /**
     * 结束时间
     */
    exEndTime?: string;
    /**
     * 开始时间
     */
    exStartTime?: string;
    /**
     * 总时长
     */
    exTotalTime?: number;
    /**
     * 试卷编号
     */
    papId?: number;
    /**
     * 培训编号
     */
    traId?: number;
    /**
     * 培训名称
     */
    traName?: string;
    [property: string]: any;
}

/**
 * ExamPaperInfo
 */
export type ExamPaperInfo = {
    /**
     * 试卷编号
     */
    papId: number;
    /**
     * 试卷说明
     */
    papRemark?: string;
    /**
     * 试卷总题目数
     */
    papTotalIssues?: number;
    /**
     * 试卷总分
     */
    papTotalScore?: number;
    /**
     * 试卷问题编号数组
     */
    papIssueIds?: string;
    /**
     * 试卷试题编号数组
     */
    papQuestionIds?: number[];
    [property: string]: any;
}
