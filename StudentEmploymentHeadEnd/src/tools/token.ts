//封装本地存储和读取的方法
export const SET_TOKEN = (token:string) =>{
    localStorage.setItem('TOKEN',token)
}
//获取的方法
export const GET_TOKEN = () =>{
    return localStorage.getItem('TOKEN')
}
//删除的方法
export const REMOVE_TOKEN = () =>{
    return localStorage.removeItem('TOKEN')
}