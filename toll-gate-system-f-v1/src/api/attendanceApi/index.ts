import request from "../../requests/request";
import { StaffDailyPunch, dailyPunch } from "../../views/attendanceManage/type";

// 获取所有职员打开记录
export const getStaffDailyPunch = async (staffId: string) => {
    const res = await request.post('/daily-punch-entity/get-all-daily-punch/' + staffId);
    return res.data;
}
/**
 * 根据职员编号获取职员打卡记录
 * @param staffId 职员编号
 */
export const getStaffDailyPunchByStaffId = async (staffId: string) => {
    return request.get('/daily-punch-entity/get-daily-punch/' + staffId).then((res) => {
        return res.data;
    });
}

// 更改职员打卡记录
export const changeStaffDailyPunch = async (data: dailyPunch) => {
    await request.post('/daily-punch-entity/update-daily-punch', data).then((res) => {
        ElMessage.success("修改成功")
        return res.data;
    });
}

//获取所有月度考勤记录
export const getAllCheckSheet = async () => {
   const res = await request.get('/perform-view-entity/get-all-check-sheet')
    return res.data;
}

//获取申请记录
export const getUnapprovedLeave = async (processType:number,processView:string) => {
    return request.get('/apply-feedback-entity/get-all-apply-feedback/'+processType+'/'+processView).then((res) => {
        return res.data;
    });
}

//修改申请记录
export const changeUnapprovedLeave = async (data: any) => {
    return  request.post('/apply-feedback-entity/update-apply-feedback', data).then((res) => {
        ElMessage.success("修改成功")
        return res.data;
    });
}

//添加申请记录
export const addUnapprovedLeave = async (data: any) => {
    return request.post('/apply-feedback-entity/add-apply-feedback', data).then((res) => {
        return res.data;
    });
}

/**
 * 根据职员id获取申请记录
 */
export const getUnapprovedLeaveByStaffId = async (staffId: string) => {
    return request.get('/apply-feedback-entity/get-all-apply-info-staff-id/' + staffId).then((res) => {
        return res.data;
    });
}

/**
 * 根据记录编号取消申请记录
 */
export const deleteUnapprovedLeave = async (applyFeedbackId: number) => {
    return request.get('/apply-feedback-entity/delete-apply-feedback/' + applyFeedbackId).then((res) => {
        return res.data;
    });
}

/**
 * 添加员工打卡记录
 */
export const addStaffDailyPunch = async (data: dailyPunch) => {
    return request.post('/daily-punch-entity/add-daily-punch-info', data).then((res) => {
        return res.data;
    });
}

/**
 * 根据职员id和月份生成月度考勤统计
 */
export const generateCheckSheet = async (staffId: string, month: string) => {
    return request.get('/perform-view-entity/generate-check-sheet/' + staffId + '/' + month).then((res) => {
        return res.data;
    });
}

