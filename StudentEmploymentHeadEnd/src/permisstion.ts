//路由鉴权文件 主要让每个路由都有属于自己的权限
import router from '@/router'
//@ts-ignore
import nprogress from 'nprogress'
import 'nprogress/nprogress.css'
//引入用户的token数据 在此文件中引入仓库 需要引入大仓库
import pinia from '@/store'
import useUserStore from './store/modules/user'
import setting from '../public/setting'
let userStore = useUserStore(pinia)
//路由的前置守卫
// @ts-ignore
router.beforeEach(async (to: any, from: any, next: any) => {
    document.title = setting.title + '--' +to.meta.title
    //进度条开始
    nprogress.start()
    //获取token
    let token = userStore.token
    let username = userStore.username
    //判断用户是否登录
    if (token) {
        //登录成功
        if (to.path == '/login') {
            next({ path: '/' })
        } else {
            if (username) {
                next()
            } else {
                await userStore.userInfo().then(() => {
                    // 万一刷新的时候是异步路由 有可能获取到用户信息 异步路由还没有加载完毕 出现空白的现象
                    // 等待加载完毕 才放行
                    next({...to})
                }).catch (async () => {
                    //token过期或者手动修改本地的token
                    await userStore.userLogout()
                    next({path:'/login',query:{redirect:to.path}})
                })

            }
        }
    } else {
        //未登录
        if (to.path == '/login') {
            next()
        } else {
            next({ path: '/login', query: { redirect: to.path } })
        }
    }
})

//路由的后置守卫
// @ts-ignore
router.afterEach((to: any, from: any) => {
    //进度条结束
    nprogress.done()
})

