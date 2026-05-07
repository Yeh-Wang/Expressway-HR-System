import axios from 'axios'
import router from "../routes";
import moment from 'moment';
// 创建axios实例
const request = axios.create({
    baseURL: 'http://localhost:8080',// 所有的请求地址前缀部分
    timeout: 80000, // 请求超时时间(毫秒)
    // withCredentials: true,// 异步请求携带cookie
    // headers: {
    // 设置后端需要的传参类型
    // 'Content-Type': 'application/json',
    // 'token': x-auth-token',//一开始就要token
    // 'X-Requested-With': 'XMLHttpRequest',
    // },
})

// request拦截器
request.interceptors.request.use((config) => {
    // 在发送请求之前做些什么
    //判断是不是登录
    if (config.url !== '/login-res-entity/login') {
        config.headers['token'] = localStorage.getItem('token')
        return config
    }else {
        return config
    }
})

// response 拦截器
request.interceptors.response.use(
    response => {
        // 对响应数据做点什么
        if(response.data === 404){
            localStorage.removeItem('token')
            router.replace('/').then()
        }
        // if(response.status === 200){
        //     localStorage.setItem('expireTime',moment().add(1,'days').format('YYYY-MM-DD HH:mm:ss'))
        // }
        return response.data
    },
    error => {
        // 对响应错误做点什么
        return Promise.reject(error)
    }
)
export default request
