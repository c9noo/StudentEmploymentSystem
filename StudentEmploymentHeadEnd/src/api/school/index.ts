import request from '@/tools/request'

enum API{
    GETTEACHERlIST_URL = '/teacher/page',
    BATCHTEACHER_URL = '/teacher/importTeacher',
    ADDTEACHER_URL = '/teacher',
    UPDATETEACHER_URL = '/teacher',
    DELETETEACHER_URL = '/teacher',
    UPDATESTATUS_URL = '/teacher/',
    GETTEACHERDETAIL_URL = '/teacher/'
}
// 分页查询老师
export const reqGetTeacher = (page:string|number,pageSize:string,name:string,status:string|number) => request.get<any,any>(API.GETTEACHERlIST_URL+`?page=${page}&pageSize=${pageSize}&name=${name}&status=${status}`)
// 批量增加老师
export const reqBatchTeacher = (data:any) => request.post<any,any>(API.BATCHTEACHER_URL,data) 
// 新增老师
export const reqAddTeacher = (data:any) => request.post<any,any>(API.BATCHTEACHER_URL,data)
// 修改老师信息
export const reqUpdateTeacher = (data:any) => request.put<any,any>(API.BATCHTEACHER_URL,data)
// 删除老师
export const reqDeleteTeacher = (id:any) => request.delete<any,any>(API.DELETETEACHER_URL + `?id=${id.value}`)
// 修改老师状态
export const reqUpdateStatus = (status:number,id:string) => request.post<any,any>(API.UPDATESTATUS_URL + `${status}?id=${id}`)
// 获取老师详情
export const reqGetTeacherDetail = (id:string) => request.get<any,any>(API.GETTEACHERDETAIL_URL+`${id}`)
