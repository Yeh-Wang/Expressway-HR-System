import request from "../../requests/request.ts";

//获取奖惩信息
export const getRewardPunish = async (staffId:string) => {
    return request.get("/perform-view-entity/get-all-punish-review/"+staffId).then((res) => {
        return res.data
    })
}

//增加奖惩信息
export const addRewardPunish = async (data: object) => {
    return request.post("/perform-view-entity/add-rew-punish-info",data).then((res) => {
        return res.data
    })
}
