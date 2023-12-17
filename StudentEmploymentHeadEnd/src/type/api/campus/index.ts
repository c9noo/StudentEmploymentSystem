// 每个教师的信息
export interface teacherItem {
    avatar: string,
    id: string,
    name: string,
    password: string,
    status: number,
    username: string,
    switch?: boolean
}
// 教师列表的信息
export type teacheRecords = teacherItem[]

// 返回Excel上传的信息
export interface teacherExcelItem {
    email: string;
    name: string;
    password: string;
    phone: string;
    username: string;
 }
 // 返回Excel上传的全部信息
 export type teacherExcel = teacherExcelItem[] 