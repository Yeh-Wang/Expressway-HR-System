//员工奖惩信息
export interface RewManageType {
    "rewId": number | null,
    "rewContent": string,
    "rewTime": string,
    "rewNature": string,
    "rewStaff": string,
    "staffName"?: string,
    [key: string]: any;
}
//自定义前端用户信息
export type UserInfoType = {
    "index"?:number,
    "staffId": string,
    "userId": string,
    "staffName": string,
    "staffNation"?: string,
    "gender"?: string,
    "age"?: number,
    "staffDegree"?: string,
    "staffPost"?: number,
    "postName"?: string,
    "staffPolitics"?: string,
    "staffRemark"?: string,
    "staffPhone"?: string,
    "staffAvatar"?: string,
    "staffAddress"?: string,
    "staffCard"?: string,
    "staffStation"?: string,
}

export const listRew = reactive({
    list: [] as RewManageType[]
})
export const listStaff = reactive({
    list: [] as UserInfoType[]
})
