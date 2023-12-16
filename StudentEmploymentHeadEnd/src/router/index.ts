// 引入创建路由实例和history模式
import {createRouter,createWebHashHistory} from 'vue-router'
// 引入常量异步通用路由
import {constRoutes} from './routes'
let router = createRouter({
    history:createWebHashHistory(),
    routes:constRoutes,
    //路由滚动行为
    scrollBehavior(){
        return{
            top:0,
            left:0
        }
    }

})
export default router

