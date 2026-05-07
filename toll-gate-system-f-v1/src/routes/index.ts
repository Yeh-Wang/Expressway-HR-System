import {createRouter, createWebHistory} from "vue-router";
import {ElMessage} from "element-plus";
import moment from "moment";
import {useUser} from "../store/home";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'Login',
            component: () => import('../views/Login/Login.vue'),
        },
        {
            path: '/home',
            name: 'home',
            // redirect: '/home/index',
            component: () => import('../views/Home/index.vue'),
            meta: {
                requiresAuth: true,
                title: '首页',
            },
            children: [
                {
                    path: '/home/index',
                    name: 'show-home',
                    component: () => import('../views/HomeShow/index.vue'),
                    meta: {
                        requiresAuth: true,
                        title: '首页',
                        roles: ['站长']
                    }
                },
                {
                    path: '/home/staff-management',
                    name: 'staff-management',
                    component: () => import('../views/staffManage/index.vue'),
                    meta: {
                        requiresAuth: true,
                        title: '职工管理',
                        roles: ['站长']
                    }
                },
                {
                    path: '/home/rew-management',
                    name: 'rew-management',
                    component: () => import('../views/SundryManage/RewManage.vue'),
                    meta: {
                        requiresAuth: true,
                        title: '职工奖惩',
                        roles: ['站长']
                    }
                },
                {
                    path: '/home/performance-management',
                    name: 'performance-management',
                    component: () => import('../views/performanceManage/index.vue'),
                    meta: {
                        requiresAuth: true,
                        title: '绩效考核',
                        roles: ['站长']
                    }
                },
                {
                    path: '/home/attendance-management',
                    name: 'attendance-management',
                    component: () => import('../views/attendanceManage/index.vue'),
                    meta: {
                        requiresAuth: true,
                        title: '打卡管理',
                        roles: ['站长']
                    }
                },
                {
                    path: '/home/check-sheet',
                    name: 'check-sheet',
                    component: () => import('../views/attendanceManage/CheckSheet.vue'),
                    meta: {
                        requiresAuth: true,
                        title: '月度考勤',
                        roles: ['站长']
                    }
                },
                {
                    path: '/home/schedule-management',
                    name: 'schedule-management',
                    component: () => import('../views/scheduleManage/index.vue'),
                    meta: {
                        requiresAuth: true,
                        title: '排班管理',
                        roles: ['站长']
                    }
                },
                {
                    path: '/home/training-management',
                    name: 'training-management',
                    component: () => import('../views/TrainManagement/index.vue'),
                    meta: {
                        requiresAuth: true,
                        title: '培训管理',
                        roles: ['站长']
                    }
                },
                {
                    path: '/home/total-management',
                    name: 'total-management',
                    component: () => import('../components/main/TotalManage.vue'),
                    meta: {
                        requiresAuth: true,
                        title: '综合管理',
                        roles: ['站长']
                    }
                },
                {
                    path: '/home/personal-center',
                    name: 'personal-center',
                    component: () => import('../views/PersonalCenter/index.vue'),
                    meta: {
                        requiresAuth: true,
                        title: '个人中心',
                        roles: ['站长', '班长', '职员']
                    }
                },
                {
                    path: '/home/user/index',
                    name: 'user-show-home',
                    component: () => import('../views/StaffView/HomeShow.vue'),
                    meta: {
                        requiresAuth: true,
                        title: '职员首页',
                        roles: ['职员']
                    }
                },
                {
                    path: '/home/user/daily-punch',
                    name: 'user-daily-punch',
                    component: () => import('../views/StaffView/DailyPunchView.vue'),
                    meta: {
                        requiresAuth: true,
                        title: '职员考勤',
                        roles: ['职员']
                    }
                },
                {
                    path: '/home/user/study-center',
                    name: 'user-study-center',
                    component: () => import('../views/StaffView/StudyView.vue'),
                    meta: {
                        requiresAuth: true,
                        title: '学习中心',
                        roles: ['职员']
                    }
                },

                // {
                //     path: '/#/user',
                //     name: 'user-home',
                //     redirect: '/#/user/index',
                //     component: () => import('../views/StaffView/HomeShow.vue'),
                //     meta: {
                //         requiresAuth: true,
                //         title: '职员首页',
                //     },
                //     children: [
                //
                //     ]
                // }
            ]
        },
        {
            path: '/happy-exam-view',
            name: 'user-exam-view',
            component: () => import('../views/StaffView/ExamView.vue'),
            meta: {
                requiresAuth: false,
                title: '考试界面',
                roles: ['职员']
            }
        }
    ]
})

//@ts-ignore
// 路由拦截判断是否登录
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token')
    if (to.meta.requiresAuth) {
        if (token) {
            // console.log(router.getRoutes())
            const expiresTime = moment(localStorage.getItem('expiresTime'))
            // 将expiresTime与现在时间作比较
            const nowTime = moment()
            if (!nowTime.isBefore(expiresTime)) {
                ElMessage.error('登录过期，请重新登录')
                useUser().clearUser()
                useUser().clearStorage()
                next('/')
            }
            next()
        } else {
            ElMessage.error('请先登录')
            next('/')
        }
    } else {
        next()
    }
})

router.afterEach((to, from) => {
    if (to.path === '/' && from.path != '/') {
        location.reload()
    }
})
// 导出
export default router
