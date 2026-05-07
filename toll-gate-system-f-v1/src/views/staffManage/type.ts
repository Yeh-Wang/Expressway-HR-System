//职员信息（数据库表）
export type staffInfo = {
    "staffId": string,  //员工id
    "userId": string,  //用户id
    "staffName": string,  //员工姓名
    "staffNation": string, //员工民族
    "gender": string, //员工性别
    "age": number,  //员工年龄
    "staffDegree": string, //员工学历
    "staffPost": number, //员工职务
    "staffPolitics": string, //员工政治面貌
    "staffRemark": string, //员工备注
    "staffPhone": string, //员工电话
    "staffAvatar": string, //员工头像
    "staffAddress": string, //员工地址
    "staffCard": string, //员工身份证
    "staffStation": string, //员工所属收费站
    "postName"?: string,
}
//具体的更加详细的信息（视图）
export type staffUserInfo = {
    "staffId": string,
    "userId": string,
    "staffName": string,
    "staffNation": string,
    "gender": string,
    "age": number,
    "staffDegree": string,
    "staffPost": number,
    "postName": string,
    "staffPolitics": string,
    "staffRemark": string,
    "staffPhone": string,
    "staffAvatar": string,
    "staffAddress": string,
    "staffCard": string,
    "staffStation": string,
}

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
    // {
    //     label: '收费站',
    //     prop: 'staffStation',
    // },
]