import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from "./routes/index";
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import echarts from "./utils/echarts.ts";
import ElementPlus from 'element-plus'


const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

const app = createApp(App);

app.config.globalProperties.$echarts = echarts; // vue3的挂载方式（一个用于注册能够被应用内所有组件实例访问到的全局属性的对象。）

app.provide('$echarts', echarts); // vue3采用provide, inject方式使用

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(router).use(pinia).mount('#app')
