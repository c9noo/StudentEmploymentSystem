//layout配置相关的仓库
import { defineStore } from "pinia";

let otherStore = defineStore('SerringStore',{
    state: () => {
        return{
            selectedOptions:[''],//存储recruitSelect子组件的数据
        }
    }
})
export default otherStore