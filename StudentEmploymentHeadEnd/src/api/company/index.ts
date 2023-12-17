import request from '@/tools/request'

enum API{
    GETRECRUIT_URL = '/recruit/page',
    RECRUITDETAIL_URL = '/recruit/',
    GETCOMPANY_URL = '/company/page',
    GETCOMPANYDETAIL_URL = '/company/',
    UPDATESTATUS_URL = '/company/',
    DELETECOMPANY_URL = '/company',
    UPDATECOMPANY_URL = '/company',
    ADDCOMPANY_URL = '/company',
    BATCHREMOVE_URL = '/company',
    BATCHIMPORT_URL = '/company/importCompany',
    GETMYRECRUIT_URL = '/recruit/myPage',
    ADDRECRUIT_UTL = '/recruit',
    DELETERECRUIT_URL = '/recruit',
    MODIFYRECRUIT_URL = '/recruit/',
    UPDATERECRUIT_URL = '/recruit',
    UPDATEPINNED_URL = '/recruit/pinned/'

}
// 查询招聘信息
export const reqGetRecruit = (page:number,pageSize:string|number,condition:string,status:number) => request.get<any,any>(API.GETRECRUIT_URL+`?page=${page}&pageSize=${pageSize}&condition=${condition}&status=${status}`)
// 查询的详情信息
export const reqRecruitDetail = (id:string) => request.get<any,any>(API.RECRUITDETAIL_URL + `${id}`)
// 查询企业列表信息
export const reqGetCompany = (page:number,pageSize:string|number,name:string,status:number) => request.get<any,any>(API.GETCOMPANY_URL+`?page=${page}&pageSize=${pageSize}&name=${name}&status=${status}`)
// 根据id查看详细信息
export const reqGetCompanyDetail = (id:string) => request.get<any,any>(API.GETCOMPANYDETAIL_URL + `${id}`)
// 修改企业的状态
export const reqUpdateStatus = (status:number,id:string) => request.post<any,any>(API.UPDATESTATUS_URL + `${status}?id=${id}`)
// 删除企业
export const reqRemoveCompany = (id:string) => request.delete<SyntaxError,any>(API.DELETECOMPANY_URL + `?id=${id}`)
// 更新企业信息
export const reqUpdateCompany = (data:any) => request.put<any,any>(API.UPDATECOMPANY_URL,data)
// 新增企业信息
export const reqAddCompany = (data:any) => request.post<any,any>(API.ADDCOMPANY_URL,data)
// 批量删除
export const reqBatchRemove = (ids:any) => request.delete<any,any>(API.BATCHREMOVE_URL + `?id=${ids.value}`)
// 批量插入数据
export const reqBatchImport =  (data:any) => request.post<any,any>(API.BATCHIMPORT_URL,data)
// 查看自己的招聘信息
export const reqGetMyRecruit = (condition:string,page:number,pageSize:string,status:number) => request.get<any,any>(API.GETMYRECRUIT_URL+`?condition=${condition}&page=${page}&pageSize=${pageSize}&status=${status}`)
// 新增招聘信息
export const reqAddRecruit = (data:any) => request.post<any,any>(API.ADDRECRUIT_UTL,data)
// 删除招聘信息
export const reqDeleteRecruit = (id:string) => request.delete<any,any>(API.DELETERECRUIT_URL+`?id=${id}`)
// 修改招聘信息的状态
export const reqModifyRecruit = (status:number,id:string) => request.post<any,any>(API.MODIFYRECRUIT_URL + `${status}?id=${id}`)
// 更新招聘信息
export const reqUpdateRecruit = (data:any) => request.put<any,any>(API.UPDATERECRUIT_URL,data)
// 是否置顶的回调
export const reqUpdatePinned = (id:string,pinned:string|number) => request.put<any,any>(API.UPDATEPINNED_URL+`${id}?pinned=${pinned}`)




