import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from "unplugin-auto-import/vite"
import Components from 'unplugin-vue-components/vite';
import Icons from 'unplugin-icons/vite'
import IconsResolver from 'unplugin-icons/resolver'
//element
import {ElementPlusResolver, NaiveUiResolver} from 'unplugin-vue-components/resolvers'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue(),
        AutoImport({
            //安装两行后你会发现在组件中不用再导入ref，reactive等
            imports: ['vue', 'vue-router', 'pinia', {
                'naive-ui': [
                    'useDialog',
                    'useMessage',
                    'useNotification',
                    'useLoadingBar'
                ]
            }],
            //存放的位置
            dts: "src/auto-import.d.ts",
            resolvers: [ElementPlusResolver(),
                // Auto import icon components
                // 自动导入图标组件
                IconsResolver({
                    prefix: 'Icon',
                }),
            ],

        }),
        Components({
            // 引入组件的,包括自定义组件
            resolvers: [
                // Auto register icon components
                // 自动注册图标组件
                IconsResolver({
                    enabledCollections: ['ep'],
                }),
                ElementPlusResolver(), NaiveUiResolver()],
            // 存放的位置
            dts: "src/components.d.ts",
        }),
        Icons({
            autoInstall: true,
        }),
    ],
})
