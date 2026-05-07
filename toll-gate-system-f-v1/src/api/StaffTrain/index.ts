import request from "../../requests/request.ts";
import {TrainInfo} from "../../views/TrainManagement/type.ts";

//获取所有培训计划信息
export const getStaffTrainList = async () => {
    return request.get("/staff-train/get-train-info-list").then((res) => {
        return res.data;
    });
}

//增加培训计划
export const addStaffTrain = async (data: TrainInfo) => {
    return request.post("/staff-train/add-train-info", data).then((res) => {
        return res.data;
    });
}

//更新培训计划
export const updateStaffTrain = async (data: TrainInfo) => {
    return request.post("/staff-train/update-train-info", data).then((res) => {
        return res.data;
    });
}

//删除培训计划
export const deleteStaffTrain = async (traId: number) => {
    return request.get("/staff-train/delete-train-info", {params: {traId}}).then((res) => {
        return res.data;
    })
}

//添加培训资源
export const addTrainSrc = async (data: any) => {
    return request.post("/staff-train/add-train-src", data).then((res) => {
        return res.data;
    });
}

//根据培训计划编号获取培训资源
export const getTrainSrcList = async (traId: number) => {
    return request.get("/staff-train/get-train-src/"+traId).then((res) => {
        return res.data;
    });
}

//修改培训资源
export const updateTrainSrc = async (data: any) => {
    return request.post("/staff-train/update-train-src", data).then((res) => {
        return res.data;
    });
}

//删除培训资源
export const deleteTrainSrc = async (srcId: number) => {
    return request.get("/staff-train/delete-train-src", {params: {srcId}}).then((res) => {
        return res.data;
    });
}

/**
 * 根据登录用户Id获取学习记录情况（原始数据）
 */
export const getStudyRecord = async (staffId: string) => {
    return request.get("/staff-study/get-all-study-record/"+staffId).then((res) => {
        return res.data;
    });
}

/**
 * 根据登录用户Id获取学习记录情况（处理后数据）
 */
export const getStudyRecordList = async (staffId: string,start:string,end:string,trainId:number) => {
    return request.get("/staff-study/get-study-time-by-train", {params: {staffId,start,end,trainId}}).then((res) => {
        return res.data;
    })
}

/**
 * 根据培训计划id查询学员培训成绩信息
 */
export const getTrainGradeInfoByTrainInfoId = async (traId: number) => {
    return request.get("/staff-train/get-train-score/"+traId).then((res) => {
        return res.data;
    });
}

/**
 * 修改学员培训成绩
 */
export const updateTrainGradeInfo = async (data: any) => {
    return request.post("/staff-train/update-train-score", data).then((res) => {
        return res.data;
    });
}

/**
 * 添加考试信息
 */
export const addExamInfoBack = async (data: any) => {
    return request.post("/exam-info/add-exam-info", data).then((res) => {
        return res.data;
    });
}

/**
 * 添加试卷信息
 */
export const addPaperInfoBack = async (data: any) => {
    return request.post("/exam-info/add-exam-paper-info", data).then((res) => {
        return res.data;
    });
}

/**
 * 根据培训id获取考试信息
 */
export const getExamInfoByTrainId = async (traId: number) => {
    return request.get("/exam-info/get-exam-info-list", {params: {traId}}).then((res) => {
        return res.data;
    })
}

/**
 * 更改考试信息
 */
export const updateExamInfo = async (data: any) => {
    return request.post("/exam-info/update-exam-info", data).then((res) => {
        return res.data;
    });
}

/**
 * 根据试卷id获取试卷信息
 */
export const getPaperInfoById = async (papId: number) => {
    return request.get("/exam-info/get-exam-paper-info", {params: {papId}}).then((res) => {
        return res.data;
    });
}

/**
 * 更改试卷信息
 */
export const updatePaperInfo = async (data: any) => {
    return request.post("/exam-info/update-exam-paper-info", data).then((res) => {
        return res.data;
    });
}

/**
 * 添加学习记录信息
 */
export const addStudyRecord = async (data: any) => {
    return request.post("/staff-study/add-study-record", data).then((res) => {
        return res.data;
    });
}

/**
 * 根据职员id获取学习记录信息
 */
export const getStudyRecordByStaffId = async (staffId: string) => {
    return request.get("/staff-study/get-study-record-by-staff-id/"+staffId).then((res) => {
        return res.data;
    });
}

/**
 * 获取所有考试信息
 */
export const getAllExamInfo = async () => {
    return request.get("/exam-info/get-all-exam-info-list").then((res) => {
        return res.data;
    });
}

/**
 * 根据职员id和培训编号获取培训成绩
 */
export const getTrainScoreByStaffId = async (staffId: string, traId: number) => {
    return request.get("/staff-train/get-train-score/"+staffId+"/"+traId).then((res) => {
        return res.data;
    });
}

/**
 * 添加培训成绩
 */
export const addTrainScore = async (data: any) => {
    return request.post("/staff-train/add-train-score", data).then((res) => {
        return res.data;
    });
}
