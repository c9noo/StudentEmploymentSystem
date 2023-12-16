import request from '@/tools/request'

enum API{
    USERLOGIN_URL = '/user/login',
    USERINFO_URL = '/user/getInfo',
    USERLOGOUT_URL = '/user/logout',
    USERUPDATE_URL = '/user',
    USERCAPTCHA_URL = '/user/captcha',
    USERSETPASS_URL = '/user/password',
    USERGETEMAIIL_URL = '/common/email',
    USERRESETPASS_URL = '/common/reset'
}
// 用户登录
export const reqUserLogin = (data:any) => request.post<any,any>(API.USERLOGIN_URL,data)
// 获取用户信息
export const reqUserInfo = () => request.get<any,any>(API.USERINFO_URL)
// 退出登录
export const reqLogoutUser = () => request.get<any,any>(API.USERLOGOUT_URL)
// 修改用户信息
export const reqUpdateUser = (data:any) => request.put<any,any>(API.USERUPDATE_URL,data)
// 获取验证码
export const reqGetCaptcha = () => request.get<any,any>(API.USERCAPTCHA_URL)
// 修改密码
export const reqSetPass = (data:any) => request.put<any,any>(API.USERSETPASS_URL,data)
// 获取邮箱验证码
export const reqGetEmail = (email:string) => request.get<any,any>(API.USERGETEMAIIL_URL+`?email=${email}`)
// 重置用户密码
export const reqResetPass = (data:any) => request.put<any,any>(API.USERRESETPASS_URL,data)

