// 返回数据的每一项ts类型
export interface recruitPageItem {
     id:  string,
     jobName: string,
     jobAddress: string,
     education: string,
     minSalary: number,
     maxSalary: number,
     jobExperience: string,
     name?:string,
     status:number,
     createTime:string,
     isPinned:boolean
}
// 返回数据的全部列表
export type Records = recruitPageItem[]

// 返回详情数据的每一项ts类型
export interface recruitDetailItem {
     id: string,
     companyName: string,
     companyAvatar: string,
     jobName: string,
     jobAddress: string,
     education: string,
     minSalary: number,
     maxSalary: number,
     jobExperience: string,
     needPeople: string,
     createTime: string,
     viewCount: number,
     status:number
}
// 返回详情数据的全部列表
export type data = recruitDetailItem[]

// 添加或者更新的参数
export interface recruitParams {
     content: string;
     education: string;
     id?: string;
     jobAddress: string;
     jobExperience: string;
     jobName: string;
     maxSalary: number | string;
     minSalary: number | string;
     needPeople: number | string;
     status?: number;
}



