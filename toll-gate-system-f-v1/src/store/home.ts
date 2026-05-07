import { defineStore } from 'pinia'
import { loginRes } from "../api/Login/userLogin.ts";
import { menuType } from "../components/menu/optionType/options.ts";
import { showData } from '../api/types.ts';
import request from '../requests/request.ts';
import { UserInfo } from '../views/PersonalCenter/type.ts';
import { staffInfo } from '../views/staffManage/type.ts';

/**
 * personInfo
 */
const personInfo = {
    userInfo: {} as UserInfo,
    staffInfo: {} as staffInfo,
}
//存储当前登录的用户权限，角色等信息
export const useUser = defineStore('user', {
    state: () => {
        return {
            // 所有这些属性都将自动推断其类型,如果推断失败可以试下 as xxx
            loginUser: {} as loginRes,
            listMenu: reactive( [{}] as menuType[]),
            personInfo
        }
    },
    actions: {
        setListMenu(list: menuType[]) {
            this.listMenu = list
        },

        setUser(data: loginRes) {
            this.loginUser.userId = data.userId
            this.loginUser.staffId = data.staffId
            this.loginUser.avatar = data.avatar
            this.loginUser.nickname = data.nickname
            this.loginUser.username = data.username
            this.loginUser.roles = data.roles
            this.loginUser.token = data.token
        },
        setPersonInfo(userInfo: UserInfo, staffInfo: staffInfo) {
            this.personInfo.userInfo = userInfo
            this.personInfo.staffInfo = staffInfo
        },
        clearUser() {
            this.loginUser.userId = ''
            this.loginUser.staffId = ''
            this.loginUser.avatar = ''
            this.loginUser.nickname = ''
            this.loginUser.username = ''
            this.loginUser.roles = ['']
            this.loginUser.token = ''
            this.listMenu = [{}] as menuType[]
            this.personInfo.userInfo = {} as UserInfo
            this.personInfo.staffInfo = {} as staffInfo
        },
        clearStorage() {
            localStorage.removeItem('token')
            localStorage.removeItem('expiresTime')
            localStorage.removeItem('loginAuth')
            localStorage.removeItem('loginPost')
        }
    },
    persist: {
        paths: ['loginUser', 'listMenu', 'personInfo'],
        key: "loginAuth"
    },
})

type post_type = {
    postId: number;
    postName: string;
    postRemark: string
}

//创建当前main界面操作的所有数据
export const useMain = defineStore('main', {
    state: () => {
        return {
            postList: [] as post_type[]
        }
    },
    actions: {
        // async setPostList() {
        //     await request.get("/post-type-entity/get-all-post-type").then((res) => {
        //         this.postList = res.data
        //     })
        // }
        setPostList() {
            this.postList = [
                {
                    postId: 1,
                    postName: '站长',
                    postRemark: '2'
                },
                {
                    postId: 2,
                    postName: '收费员',
                    postRemark: '4'
                },
                {
                    postId: 3,
                    postName: '车辆疏导员',
                    postRemark: '1'
                },
            ]
        }
    },
    persist: {
        paths: ['postList'],
        key: "loginPost"
    },
})


// useMain  可以是 useUser、useCart 之类的名字
// defineStore('main',{..}) 在devtools 就使用 main 这个名
// export const useMain = defineStore('main', {
//     // 相当于data
//     state: () => {
//         return {
//             // 所有这些属性都将自动推断其类型,如果推断失败可以试下 as xxx
//             counter: 0,
//             name: 'Eduardo',
//         }
//     },
//     // 相当于计算属性
//     getters: {
//         doubleCount: (state) => {
//             return state.counter * 2
//         },
//     },
//     // 相当于vuex的 mutation + action，可以同时写同步和异步的代码
//     actions: {
//         increment() {
//             this.counter++;
//         },
//         randomizeCounter() {
//             setTimeout(() => {
//                 this.counter = Math.round(100 * Math.random())
//             }, 0);
//         },
//     },
// })
