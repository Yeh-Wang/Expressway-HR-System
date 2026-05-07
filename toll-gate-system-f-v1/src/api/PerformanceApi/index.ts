import request from "../../requests/request.ts";
import {PerformanceInfo, PerformanceReview, PerformType} from "../../views/performanceManage/type.ts";

//获取所有绩效考核方式信息
export const getAllPerformType = async () => {
    return request.get("/perform-view-entity/get-all-perform-type").then(res => {
        return res.data;
    })
}

/**
 * 修改绩效考核方式信息
 */
export const updatePerformType = async (data: PerformType) => {
    return request.post("/perform-view-entity/update-perform-type", data).then(res => {
        return res.data;
    })
}

/**
 * 生成绩效考核信息
 */
export const generatePerform = async (data: any) => {
    return request.post("/perform-view-entity/generate-perform-review", data).then(res => {
        return res.data;
    })
}

/**
 * 获取所有总分项绩效考核信息
 */
export const getAllPerformInfoTotal = async () => {
    return request.get("/perform-view-entity/get-all-perform-review-total").then(res => {
        return res.data;
    })
}

/**
 * 根据员工id和时间获取绩效考核表
 */
export const getPerformInfoByStaffIdAndTime = async (staffId: string, time: string) => {
    return request.get("/perform-view-entity/get-perform-review/" + staffId + "/" + time).then(res => {
        return res.data;
    })
}

/**
 * 获取尚未打分的绩效考核信息
 */
export const getUnratedPerformInfo = async () => {
    return request.get("/perform-view-entity/get-unrated-perform-review").then(res => {
        return res.data;
    })
}

/**
 * 修改绩效考核信息
 */
export const updatePerformInfo = async (data: PerformanceReview) => {
    return request.post("/perform-view-entity/update-perform-review", data).then(res => {
        return res.data;
    })
}
