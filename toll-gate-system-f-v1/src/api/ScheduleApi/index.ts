import request from "../../requests/request.ts";
import {StaffSchedule} from "../../views/scheduleManage/type.ts";

//获取员工排班信息
export const getSchedule = async () => {
    return request.get("/schedule/getAll").then((res) => {
        return res.data
    })
}

//获取排班方式信息
export const getSchedulePattern = async () => {
    return request.get("/schedule/getSchedulePattern").then((res) => {
        return res.data
    })
}

//获取值班岗位信息
export const getNeedDuty = async () => {
    return request.get("/schedule/getNeedDuty").then((res) => {
        return res.data
    })
}

//获得指定岗位的职员信息
export const getScheduleStaff = async (postName: string) =>{
    return request.get(`/staff-entity/get-staff-by-post/`+postName).then((res) => {
        return res.data
    })
}

//修改排班信息
export const updateStaffSchedule = async (data: StaffSchedule) => {
    return request.post("/schedule/update-schedule", data).then((res) => {
        return res.data
    })
}

/**
 * 修改或添加排班规则
 */
export const addOrUpdateSchedulePattern = async (data: any) => {
    return request.post("/schedule/addOrUpdateSchedulePattern", data).then((res) => {
        return res.data
    })
}

/**
 * 删除排班规则
 */
export const deleteSchedulePattern = async (schId: number) => {
    return request.post("/schedule/deleteSchedulePattern/" + schId).then((res) => {
        return res.data
    })
}

/**
 * 根据输入日期自动排班
 */
export const autoScheduleByDate = async (date: string) => {
    return request.get("/schedule/autoSchedule/" + date).then((res) => {
        return res
    })
}

/**
 * 根据职员id查询该职员的排班信息
 */
export const getStaffScheduleByStaffId = async (staffId: string) => {
    return request.get("/schedule/get-schedule-info-by-staff-id/" + staffId).then((res) => {
        return res.data
    })
    }
