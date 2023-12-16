//引入pinia
import { defineStore } from 'pinia'
// 引入路由
import { constRoutes } from '@/router/routes'
// 引入ts类型
import { userStoreTs } from '@/type/store/user'
// 引入请求
import { reqUserLogin, reqUserInfo, reqLogoutUser } from '@/api/user'
// 引入token工具库
import { SET_TOKEN, GET_TOKEN,REMOVE_TOKEN } from '@/tools/token'

// 创建一个小仓库user
let userStore = defineStore('User', {
    state: (): userStoreTs => {
        return {
            menuRoutes: constRoutes,
            token: GET_TOKEN(),
            username: '',
            avatar: '',
            buttons: [],
            email:'',
            password:'',
            phone:'',
            name:''
        }
    },
    actions: {
        async userLogin(data: any) {
            await reqUserLogin(data).then(res => {
                // 存储到仓库中
                this.token = (res.data.token as string)
                // 本地存储
                SET_TOKEN(res.data.token as string)
                return 'ok'
            }).catch(err => {
                console.log(err);
                
                return Promise.reject(err)
            })
        },
        async userInfo() {
            await reqUserInfo().then(res => {
                this.username = res.data.user.name
                this.avatar = res.data.user.avatar
                this.email = res.data.user.email
                this.password = res.data.user.password
                this.phone = res.data.user.phone
                this.name = res.data.user.name
                return 'ok'
            }).catch(err => {
                return Promise.reject(new Error(err.msg))
            })
        },
        async userLogout() {
            await reqLogoutUser().then(() => {
                //清除token和username并avatar
                this.token = ''
                this.username = ''
                this.avatar = ''
                REMOVE_TOKEN()
                return 'ok'
            }).catch(err => {
                return Promise.reject(new Error(err.msg))
            })
        }
    },
    getters: {

    }
})
export default userStore