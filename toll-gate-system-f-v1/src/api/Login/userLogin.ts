import request from "../../requests/request.ts";
import {useUser} from "../../store/home.ts";
import {list, menuType} from "../../components/menu/optionType/options.ts";
import {cloneDeep} from "lodash";


export type loginVo = {
    username: string;
    password: string;
}

export type loginRes = {
    "userId": string
    "staffId": string,
    "avatar": string,
    "nickname": string,
    "username": string,
    "roles": string[],
    "token": string
}
const useStore = useUser()

export const userLogin = async (data: loginVo) => {
    return request.post("/login-res-entity/login", {}, {
        params: {
            username: data.username,
            password: data.password
        }
    }).then((res) => {
        useStore.setUser(res.data)
        // console.log(useStore.loginUser)
        return res
    })
}


export type role_auth_view = {
    "roleAuthId": 1,
    "roleId": 1,
    "roleName": "班长",            //对应meta.roles
    "roleRemark": "",
    "authId": 5,
    "authName": "schedule-management",  //对应路由名称
    "authRemark": "排班管理"
}
export const listAuth = [{
    roleName: "" as string,
    listRoute: [] as string[]
}]

//获取所有角色权限并过滤路由
export const getRoleAuth = async () => {
    return request.get("/role-auth-view-entity/get-role-auth").then(async (res) => {
        const all_role_auth_list: role_auth_view[] = res.data
        listAuth.splice(0, listAuth.length)
        //将all_role_auth_list根据roleName分类
        all_role_auth_list.forEach((item) => {
            if (listAuth.findIndex((value) => value.roleName === item.roleName) === -1) {
                listAuth.push({
                    roleName: item.roleName,
                    listRoute: [item.authName]
                })
            } else {
                listAuth[listAuth.findIndex((value) => value.roleName === item.roleName)].listRoute.push(item.authName)
            }
        })
        const menuNames = listAuth.filter((item) => item.roleName === useStore.loginUser.roles[0])[0].listRoute
        const list_1 = cloneDeep(list.value)
        list.value = [] as menuType[]
        list_1.forEach((item) => {
            if (item.child?.length === 0) {
                if (menuNames.includes(item.name)) {
                    list.value.push(item)
                }
            } else {
                if (menuNames.includes(item.name)) {
                    list.value.push(
                        {
                            label: item.label,
                            name: item.name,
                            icon: item.icon,
                            path: item.path,
                            url: item.url,
                            child: []
                        }
                    )
                }
                item.child!.forEach((index) => {
                    if (menuNames.includes(index.name)) {
                        list.value[list.value.length - 1].child!.push(index)
                    }
                })
            }
        })
        // for (let i = list_1.length - 1; i >= 0; i--) {
        //     if (list_1[i].child == undefined) {
        //         let j
        //         for (j = 0; j < menuNames.length; j++) {
        //             if (list_1[i].name == menuNames[j]) {
        //                 break;
        //             }
        //         }
        //         if (j == menuNames.length) {
        //             list.value.splice(i, 1)
        //         }
        //     } else {
        //         for (let k = list_1[i].child!.length - 1; k >= 0; k--) {
        //             let j
        //             for (j = 0; j < menuNames.length; j++) {
        //                 //@ts-ignore
        //                 if (list_1[i].child[k]!.name == menuNames[j]) {
        //                     break;
        //                 }
        //             }
        //             if (j == menuNames.length) {
        //                 list_1[i].child!.splice(k, 1)
        //             }
        //         }
        //     }
        // }
        useUser().setListMenu(list.value)
    });
}
