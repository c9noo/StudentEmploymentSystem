// 引入路由规范
import { RouteRecordRaw } from "vue-router";
// 定义仓库存储数据的ts类型
export interface userStoreTs{
    menuRoutes:RouteRecordRaw[],
    token: string|null,
    username: string,
    avatar: string,
    buttons:[],
    email:string,
    password:string,
    phone:string,
    name:string
}
