import request from "../../requests/request.ts";
import { staffInfo } from "../../views/staffManage/type.ts";


export const getUserStaffInfo = async (staffId: string) => {
    return request.get("/user-info-entity/get-staff-info/"+staffId).then((res) => {
        return res.data
    })
}

export const changeUserStaffInfo = async (data: staffInfo) => {
    return request.post("/staff-entity/update-staff-info", data).then((res) => {
        return res.data
    })
}

/**
 * 修改用户信息
 */
export const changeUserInfo = async (data: any) => {
    return request.post("/login-res-entity/update-password", data).then((res) => {
        return res.data
    })
}
