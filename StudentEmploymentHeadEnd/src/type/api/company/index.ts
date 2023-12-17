// 返回企业基本信息的每一项ts类型
export interface companyPageItem {
    id : string,
    avatar :string ,
    name : string ,
    username :  string ,
    status : number|string,
    password :  string ,
    switch?: boolean
}
// 返回企业基本信息的全部列表
export type companyRecords = companyPageItem[]

// 返回Excel上传的信息
export interface companyExcelItem {
   email: string;
   name: string;
   password: string;
   phone: string;
   username: string;
}
// 返回Excel上传的全部信息
export type companyExcel = companyExcelItem[] 
