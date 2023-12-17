//layout配置相关的仓库
import { defineStore } from "pinia";

let useLayoutSettingStore = defineStore('SerringStore',{
    state: () => {
        return{
            fold:false,//控制用户的菜单时折叠还是展开
            masks:0,//控制蒙版显示和隐藏
            dataArr:[],//组件之间传递的数量
            id:'',//数据的索引
            loading:true
        }
    }
})
export default useLayoutSettingStore