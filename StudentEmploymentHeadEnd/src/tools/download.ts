// download.js
import axios from 'axios'
//引入用户相关的仓库
import useUserStore from '@/store/modules/user'
  //获取用户的token 登录成功之后的token
  let userStore = useUserStore()
export function download(method: string, url: string,title = "企业信息模板") {
    axios({
        method,
        url,
        headers:{
            token:userStore.token
        },
        // 二进制流文件，一定要设置成blob，默认是json
        responseType: 'blob'
    }).then(res => {
        const name = title
        const link = document.createElement('a')
        const blob = new Blob([res.data], { type: 'application/vnd.ms-excel' })
        link.style.display = 'none'
        link.href = URL.createObjectURL(blob)
        link.setAttribute('download', `${name}.xlsx`)
        document.body.appendChild(link)
        link.click()
        document.body.removeChild(link)
    })
}
