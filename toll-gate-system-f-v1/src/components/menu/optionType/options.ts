import {RouteLocationNormalizedLoaded} from "vue-router";

//定义所有菜单选项
export const list = ref<menuType[]>([
    {
        path: '/home/index',
        name: 'show-home',
        label: '首页',
        icon: 'house',
        url: '/home/index',
        child:[]
    },
    {
        path: '/home/staff-management',
        name: 'staff-management',
        label: '职员信息',
        icon: 'user',
        url: '/staff-management',
        child:[]
    },
    {
        label: '绩效考核',
        name: 'performance-management',
        icon: 'document',
        path: '/home/performance-management',
        url: '/performance-management',
        child:[]
    },
    {
        label: '考勤管理',
        name: 'attendance-management',
        icon: 'timer',
        path: '/home/attendance-management',
        url: '/attendance-management',
        child: [
            {
                label: '打卡管理',
                name: 'attendance-management',
                icon: 'list',
                path: '/home/attendance-management',
                url: '/attendance-management',
            },
            {
                label: '月度考勤',
                name: 'check-sheet',
                icon: 'trendCharts',
                path: '/home/check-sheet',
                url: '/check-sheet',
            }
            ]
    },
    {
        label: '排班管理',
        name: 'schedule-management',
        icon: 'paperclip',
        path: '/home/schedule-management',
        url: '/schedule-management',
        child:[]
    },
    {
        label: '培训管理',
        name: 'training-management',
        icon: 'sunny',
        path: '/home/training-management',
        url: '/training-management',
        child:[]
    },
    {
        label: '综合管理',
        name: 'total-management',
        icon: 'setting',
        path: '/home/total-management',
        url: '/total-management',
        child: [
            {
                label: '职员奖惩',
                name: 'rew-management',
                icon: 'medal',
                path: '/home/rew-management',
                url: '/post-management'
            },
            {
                label: '公告管理',
                name: 'total-management',
                icon: 'eleme',
                path: '/home/total-management',
                url: '/total-management',
            }
        ]
    },
    {
        label: '首页',
        name: 'user-show-home',
        icon: 'sunny',
        path: '/home/user/index',
        url: '/user-show-home',
        child:[]
    },
    {
        label: '考勤界面',
        name: 'user-daily-punch',
        icon: 'calendar',
        path: '/home/user/daily-punch',
        url: '/user-daily-punch',
        child:[]
    },
    {
        label: '学习中心',
        name: 'user-study-center',
        icon: 'medal',
        path: '/home/user/study-center',
        url: '/user-study-center',
        child:[]
    },
]);

export type menuType = {
    label: string,
    name: string,
    icon: string,
    path?: string,
    url?: string,
    child?: menuType[]
}
export const pathList = reactive({
    path: "",
    title: ""
})

export const setPathList = reactive([
    {
        path: "",
        name: "",
        label: ""
    }
])
