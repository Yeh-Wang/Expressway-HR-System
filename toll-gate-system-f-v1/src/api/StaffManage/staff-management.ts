//获取登录用户相对应的员工信息
import request from "../../requests/request.ts";
import {useUser} from "../../store/home.ts";

import {staffInfo, staffUserInfo} from "../../views/staffManage/type.ts";

const useStore = useUser()

//获取职员信息
export const getStaffInfo = async () => {
    return request.get("/staff-entity/get-all-staff-info/" + useStore.loginUser.userId).then((res) => {
        return res.data
    });
}
// 更改职员信息
export const changeStaffInfo = async (data: staffUserInfo) => {
    // data.staffPost = data.staffPost
    return request.post("/staff-entity/update-staff-info", data).then((res) => {
        return res.data
    });
}

//  添加职员信息
export const addStaffInfo = async (data: staffUserInfo) => {
    let staffEntity = {} as staffInfo
    staffEntity.staffName = data.staffName;
    staffEntity.staffNation = data.staffNation;
    staffEntity.gender = data.gender;
    staffEntity.age = data.age;
    staffEntity.staffDegree = data.staffDegree;
    staffEntity.staffPost = data.staffPost;
    staffEntity.staffPolitics = data.staffPolitics;
    staffEntity.staffPhone = data.staffPhone;
    staffEntity.staffAddress = data.staffAddress;
    staffEntity.staffCard = data.staffCard;
    staffEntity.staffStation = data.staffStation
    return request.get("/staff-entity/add-staff-info", { params: staffEntity }).then((res)=>{
        // @ts-ignore
        alert(res.message)
        return res.data
    })
}

//  删除职员信息
export const deleteStaffInfo = async (staffId: string) => {
    return request.get("/staff-entity/delete-staff-info/" + staffId).then((res) => {
        return res.data
    });
}
