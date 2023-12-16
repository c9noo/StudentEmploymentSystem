import { createApp } from 'vue'
import App from './App.vue'
// 引入全局样式
import './style.css'
// 引入element-plus以及样式
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
// 引入路由
import router from "@/router"
// 引入路由守卫
import './permisstion'
// 引入pinia
import store from '@/store'
//@ts-ignore忽略当前文件ts类型的检测否则有红色提示(打包会失败)
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
// 创建vue的实例
const app = createApp(App)

// use把所有的组件应用到vue的实例上
app.use(store)
app.use(ElementPlus, {
    locale: zhCn,
})
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }
app.use(router)
app.mount('#app')
