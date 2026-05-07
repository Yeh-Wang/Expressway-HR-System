/**
 * 全局类型定义
 */

export const tableColumns = [
    {
        label: '员工姓名',
        prop: 'staffName',
    },
    {
        label: '员工性别',
        prop: 'gender',
    },
    {
        label: '员工年龄',
        prop: 'age',
    },
    {
        label: '员工民族',
        prop: 'staffNation',
    },
    {
        label: '员工学历',
        prop: 'staffDegree',
    },
    {
        label: '员工职务',
        prop: 'postName',
    },
    {
        label: '员工政治面貌',
        prop: 'staffPolitics',
    },
    {
        label: '员工电话',
        prop: 'staffPhone',
    },
    {
        label: '员工地址',
        prop: 'staffAddress',
    },
    {
        label: '员工身份证',
        prop: 'staffCard',
    },
    {
        label: '收费站',
        prop: 'staffStation',
    },
]

export type showData<T> = {
    nowDataList: T,
    conditionData:string
}

export type tokenType = {
    token: string,  //token
    expiresTime: number,  //过期时间
    startTime: Date,  //开始时间
}








// export enum genderEnum{
//     "男" = 1,
//     "女" = 2
// }
// //定义职工学历词典
// export enum staffDegreeEnum{
//     "初中" = 1,
//     "高中" = 2,
//     "大专" = 3,
//     "本科" = 4,
//     "硕士" = 5,
//     "博士" = 6
// }
// //定义职工政治面貌词典
// export enum staffPoliticsEnum{
//     "中国共产党" = 1,
//     "中国共青团" = 2,
//     "中国民主党派" = 3,
//     "无党派人士" = 4
// }
//定义职工职务词典
// export enum staffPostEnum{
//     "站长" = 1,
//     "巡逻人员" = 2,
//     "职员" = 3,
//     "值班人员" = 4,
//     "财务人员" = 5
// }
