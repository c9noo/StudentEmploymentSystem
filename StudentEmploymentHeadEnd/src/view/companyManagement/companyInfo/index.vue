<template>
    <div class="outer">
        <!-- 头部 -->
        <el-card>
            <!-- 搜索表单 -->
            <el-form inline :disabled="screen == 2">
                <el-form-item label="查询企业">
                    <el-input v-model="search" placeholder="请输入你要查询的企业"></el-input>
                </el-form-item>
                <el-form-item label="状态">
                    <el-select v-model="value" placeholder="请选择" style="width: 100px;" @change="valueChange">
                        <el-option v-for="item in Options" :label="item.label" :value="item.value"
                            :key="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="searchCompany">搜索</el-button>
                </el-form-item>
                <!-- 其他功能 -->
                <el-form-item>
                    <el-button type="primary" icon="plus" @click="addCompany">新增企业</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="danger" @click="batchDelete">批量删除</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="batchImport">批量导入</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="success" @click="companyExport">导出</el-button>
                </el-form-item>
            </el-form>
        </el-card>
        <!-- 批量导入对话框 -->
        <el-dialog v-model="dialogTableVisible" @close="clearErrArry">
            <div class="body">
                <div class="left" style="display: flex;">
                    <el-button type="primary" @click="templateDownload">导入模板下载</el-button>
                    <el-upload style="margin-left: 10px;" action='/admin/common/importExcel' :headers="headers"
                        :show-file-list="false" :on-success="handleExcel" :before-upload="beforeExcel" limit="3"
                        ref="excelUpload">
                        <el-button type="primary">导入</el-button>
                    </el-upload>
                </div>
                <div class="right">
                    <el-button type="success" @click="excelSave">保存</el-button>
                </div>
            </div>
            <el-table :data="excelParams" v-show="excelParams.length >= 1" :row-class-name="tableRowClassName"
                v-if="tableShow">
                <el-table-column label="企业名称" prop="name"></el-table-column>
                <el-table-column label="登陆账号" prop="username"></el-table-column>
                <el-table-column label="密码" prop="password"></el-table-column>
                <el-table-column label="企业号码" prop="phone"></el-table-column>
                <el-table-column label="企业邮箱" prop="email"></el-table-column>
            </el-table>
        </el-dialog>
        <!-- 身体 -->
        <el-card style="margin-top: 20px;height: 750px;">
            <!-- 展示列表的场景 -->
            <div v-show="screen == 1">
                <el-table :data="companyList" border height="680" @select="gatId" @select-all="getIds">
                    <el-table-column label="批量操作" width="100" align="center" type="selection"></el-table-column>
                    <el-table-column label="头像" align="center">
                        <template #="{ row }">
                            <img :src="row.avatar" style="width: 70px;height: 70px;object-fit: cover;">
                        </template>
                    </el-table-column>
                    <el-table-column label="企业名" align="center" prop="name">
                    </el-table-column>
                    <el-table-column label="登陆账号" align="center" prop="username">
                    </el-table-column>
                    <el-table-column label="操作" align="center">
                        <template #="{ row }">
                            <el-popconfirm :title="`你确定要删${row.name}吗?`" @confirm="deleteCompany(row.id)">
                                <template #reference>
                                    <el-button type="danger" icon="delete" title="删除企业" size="default"
                                        style="margin-right: 20px;"></el-button>
                                </template>
                            </el-popconfirm>
                            <el-switch v-model="row.switch" @change="updateStatus(row.status, row.id)" />
                            <el-button type="warning" icon="edit" title="修改企业信息" size="default" style="margin-left: 20px;"
                                @click="updatecompany(row.id)"></el-button>
                            <el-button type="info" icon="InfoFilled" title="查看信息" size="default" style="margin-left: 20px;"
                                @click="getCompanyDetail(row.id)"></el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <!-- 分页器 -->
                    <Paging :pageSizes="[3,5,7,10]" :currentPage="currentPage" :pageSize="pageSize" :total="total" @sizeChange="handleSizeChange" @currentChange="handleCurrentChange"/>
            </div>
            <!-- 修改或者添加的场景 -->
            <div v-show="screen == 2" class="update">
                <span>{{ addOrUpdateParams.id != '' ? '修改企业信息' : '新增企业' }}</span>
                <el-form :model="addOrUpdateParams" style="margin-top: 20px;width: 500px;" label-width="100px"
                    ref="ruleFormRef" :rules="rules">
                    <el-form-item label="用户头像">
                        <el-upload class="avatar-uploader" action='/admin/common/upload' :headers="headers"
                            :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload"
                            limit="3" ref="upload">
                            <img v-if="addOrUpdateParams.avatar" :src="addOrUpdateParams.avatar" class="avatar" />
                            <el-icon v-else class="avatar-uploader-icon">
                                <Plus />
                            </el-icon>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="企业名称" prop="name">
                        <el-input v-model="addOrUpdateParams.name" placeholder="请输入企业名称"></el-input>
                    </el-form-item>
                    <el-form-item label="企业用户名" prop="username">
                        <el-input v-model="addOrUpdateParams.username" placeholder="请输入企业用户名"
                            :disabled="addOrUpdateParams.id != ''"></el-input>
                    </el-form-item>
                    <el-form-item label="企业密码" prop="password">
                        <el-input v-model="addOrUpdateParams.password" placeholder="请输入企业密码" type="password"></el-input>
                    </el-form-item>
                    <el-form-item label="企业电话" prop="phone">
                        <el-input v-model="addOrUpdateParams.phone" placeholder="请输入企业电话"></el-input>
                    </el-form-item>
                    <el-form-item label="企业邮箱" prop="email">
                        <el-input v-model="addOrUpdateParams.email" placeholder="请输入企业邮箱"></el-input>
                    </el-form-item>
                    <el-form-item style="margin-top: 30px;margin-left: 100px;">
                        <el-button type="" size="default" @click="cancel">取消</el-button>
                        <el-button color="#3F81A9" size="default" @click="save">确定</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-card>
        <!-- 详细信息 -->
        <el-drawer v-model="drawer" title="I am the title" :with-header="false">
            <span style="font-size: 24px;">企业详情信息</span>
            <div class="item">
                <img :src="companyDetail.avatar" alt="" style="width: 200px;height: 200px;object-fit: cover;">
                <div style="display: flex;flex-direction: column;margin-top: 30px;">
                    <span>企业名称:{{ companyDetail.name }}</span>
                    <span>企业用户名:{{ companyDetail.username }}</span>
                    <span>最新企业名称:{{ companyDetail.updateUsername }}</span>
                </div>
                <div style="display: flex;flex-direction: column;margin-top: -140px;">
                    <span>联系电话:{{ companyDetail.phone }}</span>
                    <span>公司邮箱:{{ companyDetail.email }}</span>
                    <span>创建时间:{{ companyDetail.createTime }}</span>
                    <span>更新时间:{{ companyDetail.updateTime }}</span>
                </div>
            </div>
        </el-drawer>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue'
import { download } from '@/tools/download'
import { reqGetCompany, reqGetCompanyDetail, reqUpdateStatus, reqRemoveCompany, reqUpdateCompany, reqAddCompany, reqBatchRemove, reqBatchImport } from '@/api/company'
import { companyRecords, companyExcel } from '@/type/api/company'
import { ElMessage } from 'element-plus';
import { GET_TOKEN } from '@/tools/token'
import Paging from '@/components/Paging.vue'
// 搜索内容
let search = ref<string>('')
// select选中的数值
let value = ref('normal')
// option的值
let Options = [
    {
        value: 'normal',
        label: '正常'
    },
    {
        value: 'disable',
        label: '禁用'
    }
]
// 企业列表
let companyList = ref<companyRecords>([])
// 列表详细信息
let companyDetail = reactive({
    id: '',
    name: '',
    username: '',
    avatar: '',
    phone: '',
    email: '',
    status: 0,
    createTime: '',
    updateTime: '',
    isDelete: '',
    updateUsername: ''
})
// 获取原生input的实例
let excelUpload = ref()
// 对话框的显示和隐藏
let dialogTableVisible = ref<boolean>(false)
// 抽屉的显示和隐藏
let drawer = ref<boolean>(false)
// 总条数
let total = ref<number>(0)
// 当前第几页
let currentPage = ref<number>(1)
// 一页几条数据
let pageSize = ref<number>(10)
// 场景值
let screen = ref<number>(1)
// 上传图片的实例
let upload = ref()
// 获取form表单的实例
let ruleFormRef = ref()
// 获取token
let headers = reactive({
    Token: GET_TOKEN()
})
// 动态生成table
let tableShow = ref<boolean>(true)
// 存储table警告的下标
let errArry = ref<number[]>([])
// 收集选中的id
let idArry = ref([])
onMounted(() => {
    getHasCompanyList()
})
// 查询参数
let companyParams = reactive({
    page: 1,
    name: '',
    pageSize: '10',
    status: 0
})
// 修改添加的参数
let addOrUpdateParams = reactive({
    id: '',
    name: '',
    username: '',
    password: '',
    avatar: '',
    phone: '',
    email: ''
})
// 存储Excel上传的信息
let excelParams = ref<companyExcel>([])
// 获取企业列表信息
const getHasCompanyList = async (pager = 1, stus = 0) => {
    companyParams.page = pager
    companyParams.pageSize = pageSize.value + ''
    companyParams.status = stus
    await reqGetCompany(companyParams.page, companyParams.pageSize, companyParams.name, companyParams.status).then(res => {
        total.value = res.data.total
        companyList.value = res.data.records
        companyList.value.map(item => {
            if (item.status == 0) {
                item.switch = true
            } else {
                item.switch = false
            }
        })
    })
}
// 获取企业列表详细信息
const getCompanyDetail = async (id: any) => {
    await reqGetCompanyDetail(id).then(res => {
        Object.assign(companyDetail, res.data)
        drawer.value = true
    }, () => {
        ElMessage({
            type: 'error',
            message: '获取失败请检查网络'
        })
    })
}
// 修改企业的状态
const updateStatus = async (status: number, id: string) => {
    if (status == 0) {
        status = 1
        await reqUpdateStatus(status, id).then(() => {
            ElMessage({
                type: 'info',
                message: '已禁用'
            })
            getHasCompanyList()
        })
    } else {
        status = 0
        await reqUpdateStatus(status, id).then(() => {
            ElMessage({
                type: 'success',
                message: '已恢复'
            })
            getHasCompanyList(1, 1)
        })
    }
}
// 分页器展示列表发生改变
const handleSizeChange = (value:any) => {
    pageSize.value = value
    getHasCompanyList()
}
// 分页器改变页码
const handleCurrentChange = (data:any) => {
    currentPage.value = data
    getHasCompanyList(currentPage.value)
}
// 删除企业
const deleteCompany = async (id: string) => {
    await reqRemoveCompany(id).then(() => {
        ElMessage({
            type: 'success',
            message: '删除成功'
        })
        getHasCompanyList(companyList.value.length > 1 ? currentPage.value : currentPage.value - 1)
    })
}
// 图片上传的回调
const beforeAvatarUpload = (file: any) => {
    let reg = /(.*)\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$/;
    if (!reg.test(file.name)) {
        ElMessage({
            type: 'error',
            message: '图片格式错误'
        })
        return false
    } else if (file.size / 1024 / 1024 > 1) {
        ElMessage.error('图片大小不能超过2MB!')
        return false
    }
    return true
}
//用户上传成功之后的回调
// @ts-ignore
const handleAvatarSuccess = (response: any, uploadFile: any) => {
    // addOrUpdateParams.avatar = URL.createObjectURL(uploadFile.raw!)
    addOrUpdateParams.avatar = response.data
    upload.value.clearFiles(); //上传成功之后清除历史记录
}
// 点击修改企业的信息
const updatecompany = async (id: any) => {
    // 获取详细信息
    await reqGetCompanyDetail(id).then(res => {
        Object.assign(addOrUpdateParams, res.data)
        screen.value = 2
    })
}
// 点击添加企业的信息
const addCompany = () => {
    Object.assign(addOrUpdateParams, {
        id: '',
        name: '',
        username: '',
        password: '',
        avatar: '',
        phone: '',
        email: ''
    })
    screen.value = 2
}
// 取消上传的回调
const cancel = () => {
    Object.assign(addOrUpdateParams, {
        id: '',
        name: '',
        username: '',
        password: '',
        avatar: '',
        phone: '',
        email: ''
    })
    // 清除上一次的表单校验效果
    ruleFormRef.value.clearValidate()
    screen.value = 1
}
// 确定上传的回调
const save = async () => {
    if (addOrUpdateParams.id != '') {
        const { id, name, phone, email, password, avatar } = addOrUpdateParams
        let newObj = { id, name, phone, email, password, avatar }
        // 验证校验是否通过
        await ruleFormRef.value.validate()
        await reqUpdateCompany(newObj).then(() => {
            ElMessage({
                type: 'success',
                message: '更新成功'
            })
            screen.value = 1
            getHasCompanyList(currentPage.value)
        })
    } else {
        let result = await reqAddCompany(addOrUpdateParams)
        if (result.code == 200) {
            ElMessage({
                type: 'success',
                message: '添加成功'
            })
            screen.value = 1
            getHasCompanyList()
        } else {
            ElMessage({
                type: 'error',
                message: result.msg
            })

        }
    }
}
// 搜索企业的回调
const searchCompany = async () => {
    // 清空select选中的值
    value.value = ''
    // 合并参数
    companyParams.name = search.value
    if (value.value == 'normal') {
        companyParams.status = 0
    } else {
        companyParams.status = 1
    }
    await reqGetCompany(companyParams.page, companyParams.pageSize, companyParams.name, companyParams.status).then(() => {
        ElMessage({
            type: 'success',
            message: '搜索成功'
        })
        getHasCompanyList()
    })
}
// 数据导出
const companyExport = async () => {
    download('get', '/admin/company/exportCompany', '全部企业信息')
}
// 收集id
// @ts-ignore
const gatId = (selection: any, row: any) => {
    //@ts-ignore
    idArry.value.push(row.id + '')
}
const getIds = (selection: any) => {
    idArry.value = selection.map((item: any) => {
        return item.id
    })
}
// 批量删除
const batchDelete = async () => {
    if (idArry.value.length > 1) {
        await reqBatchRemove(idArry).then(() => {
            ElMessage({
                type: 'success',
                message: '删除成功'
            })
            getHasCompanyList()
        })
    } else {
        ElMessage({
            type: 'info',
            message: '请选择你要删除的信息'
        })
    }

}
// 批量导入
const batchImport = () => {
    excelParams.value = []
    dialogTableVisible.value = true
}
// 自定义检验规则
// 验证用户名
// @ts-ignore
const validateUsername = (rule: any, value: any, callback: any) => {
    if (value.trim().length >= 5) {
        if (/^[A-Za-z]{5,16}$/.test(value)) {
            callback()
        } else {
            callback(new Error("用户名不能是汉字或者数字"))
        }
    } else {
        callback(new Error("用户名必须大于5位"))
    }
}
// 验证密码
// @ts-ignore
const validatePassword = (rule: any, value: any, callback: any) => {
    if (/^.{6,100}$/.test(value)) {
        callback()
    } else {
        callback(new Error("密码至少包含一个字母或者一个数字,并且大于六位"))
    }
}
// 验证手机
// @ts-ignore
const validatePhone = (rule: any, value: any, callback: any) => {
    let deg = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
    if (deg.test(value)) {
        callback()
    } else {
        callback(new Error('请输入正确的手机号码'))
    }
}
// 验证邮箱
// @ts-ignore
const validateEmail = (rule: any, value: any, callback: any) => {
    let deg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
    if (deg.test(value)) {
        callback()
    } else {
        callback(new Error('请输入合格的邮箱'))
    }
}
// 表单校验对象
const rules = reactive({
    name: [{ required: true, max: 12, min: 1, message: '用户名必须在1-12位之间', trigger: 'blur' }],
    username: [{ required: true, validator: validateUsername, trigger: 'blur' }],
    password: [{ required: true, validator: validatePassword, trigger: 'blur' }],
    email: [{ required: true, validator: validateEmail, trigger: 'blur' }],
    phone: [{ required: true, validator: validatePhone, trigger: 'blur' }]
})
// select状态发生改变触发该请求
const valueChange = async (value: any) => {
    if (value == 'disable') {
        getHasCompanyList(1, 1)
    } else {
        getHasCompanyList(1, 0)
    }
}
// 模板下载
const templateDownload = () => {
    // 导入第三方方法发送请求下载
    download('get', '/admin/common/export')
}
// Excel上传文件成功之后
// @ts-ignore
const handleExcel = (response: any, uploadFile: any) => {
    // 清除上一次的table样式
    errArry.value = []
    excelUpload.value.clearFiles(); //上传成功之后清除历史记录
    excelParams.value = response
}
// Excel上传之前
const beforeExcel = (file: any) => {
    let reg = /(.*)\.(xlsx)$/;
    if (!reg.test(file.name)) {
        ElMessage({
            type: 'error',
            message: '文件格式错误'
        })
        return false
    }
    return true
}
// excel数据上传
const excelSave = async () => {
    if (excelParams.value.length >= 1) {
        let result = await reqBatchImport(excelParams.value)
        if (result.code == 200) {
            ElMessage({
                type: 'success',
                message: '插入成功'
            })
            dialogTableVisible.value = false
            getHasCompanyList()
        } else {
            if (result.msg.length <= 20) {
                ElMessage({
                    type: 'error',
                    message: result.msg
                })
            } else {
                arryProcessor(result)
                ElMessage({
                    type: 'error',
                    message: '企业导入信息格式有误,请看如下提示'
                })
            }
        }
    } else {
        ElMessage({
            type: 'error',
            message: '请先导入Excel的模板信息'
        })
    }
}
// 收集所有错误的下标
const arryProcessor = (result: any) => {
    // 截取出所有的下标
    let arr = result.msg.split("[")
    let arrs = arr.map((item: any) => {
        return item.split("]")[0]
    })
    // 准备一个空数组来去重
    const newArr: any = []
    arrs.splice(0, 1)
    arrs.forEach((item: any) => {
        if (!newArr.includes(item)) {
            newArr.push(item)
        }
    })
    errArry.value = newArr
    // 修改table的样式
    tableShow.value = false
    tableShow.value = true
}
// 批量上传导入错误样式
const tableRowClassName = ({
    // @ts-ignore
    row,
    rowIndex,
}: {
    row: any,
    rowIndex: number
}) => {
    // @ts-ignore
    let result = errArry.value.indexOf(rowIndex + '')
    if (result != -1) {
        // 设置警告样式
        return 'warning-row'
    }
    return ''
}
// 清除上一次table表格样式的数据
const clearErrArry = () => {
    errArry.value = []
}
</script>

<style scoped lang="scss">
.outer {
    width: 100%;
    height: 100%;
}

.update {
    .el-button {
        height: 42px;
        width: 100px;
    }
}

.item {
    width: 100%;
    height: 70%;
    margin-top: 20px;
    box-sizing: border-box;
    padding: 20px;
    display: flex;
    flex-wrap: wrap;

    img {
        border-radius: 100px;
        border: 2px solid black;
    }

    span {
        font-size: 20px;
        margin: 20px 0px 20px 20px;
    }
}

.el-input {
    height: 42px;
}

.avatar-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
    border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    text-align: center;
}

.avatar {
    width: 100%;
    height: 100%;
}

.body {
    width: 100%;
    height: 50px;
    display: flex;
    justify-content: space-between;

    .el-button {
        height: 40px;
        width: 100px;
    }
}

.el-table ::v-deep .warning-row {
    // --el-color-warning-light-9
    --el-table-tr-bg-color: #CCCC00;
}
</style>