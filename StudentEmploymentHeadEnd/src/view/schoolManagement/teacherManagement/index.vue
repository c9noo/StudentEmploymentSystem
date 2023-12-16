<template>
    <div class="outer">
        <!-- 头部 -->
        <el-card>
            <!-- 搜索表单 -->
            <el-form inline>
                <el-form-item label="查询企业">
                    <el-input v-model="search" placeholder="请输入你要查询的教师"></el-input>
                </el-form-item>
                <el-form-item label="状态">
                    <el-select v-model="value" placeholder="请选择" style="width: 100px;">
                        <el-option v-for="item in Options" :label="item.label" :value="item.value"
                            :key="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="searchTeacher">搜索</el-button>
                </el-form-item>
                <!-- 其他功能 -->
                <el-form-item>
                    <el-button type="primary" icon="plus" @click="addTeacher">新增教师</el-button>
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
                <el-table-column label="教师名称" prop="name"></el-table-column>
                <el-table-column label="教师账号" prop="username"></el-table-column>
                <el-table-column label="教师用户密码" prop="password"></el-table-column>
                <el-table-column label="教师号码" prop="phone"></el-table-column>
                <el-table-column label="教师邮箱" prop="email"></el-table-column>
            </el-table>
        </el-dialog>
        <!-- 身体部分 -->
        <el-card style="margin-top: 10px;">
            <div v-show="screen == 1">
                <el-table :data="teacherList" border>
                    <el-table-column label="批量操作" width="100" align="center" type="selection"></el-table-column>
                    <el-table-column label="头像" align="center">
                        <template #="{ row }">
                            <img :src="row.avatar" style="width: 70px;height: 70px;object-fit: cover;">
                        </template>
                    </el-table-column>
                    <el-table-column label="教师名称" align="center" prop="name"></el-table-column>
                    <el-table-column label="教师用户名" align="center" prop="username"></el-table-column>
                    <el-table-column label="操作" align="center">
                        <template #="{ row }">
                            <el-popconfirm :title="`你确定要删${row.name}吗?`" @confirm="deleteTeacher(row.id)">
                                <template #reference>
                                    <el-button type="danger" icon="delete" title="删除企业" size="default"
                                        style="margin-right: 20px;"></el-button>
                                </template>
                            </el-popconfirm>
                            <el-switch v-model="row.switch" @change="updateStatus(row.status, row.id)"  />
                            <el-button type="warning" icon="edit" title="修改教师信息" size="default" @click="updateTeacher(row.id)"
                                style="margin-left: 20px;"></el-button>
                            <el-button type="info" icon="InfoFilled" title="查看信息" size="default" @click="getTeacherDetail(row.id)"
                                style="margin-left: 20px;"></el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <Paging :pageSizes="[3, 5, 7, 10]" :currentPage="currentPage" :pageSize="pageSize" :total="total"
                    @sizeChange="handleSizeChange" @currentChange="handleCurrentChange" />
            </div>
            <!-- 修改或者添加的场景 -->
            <div v-show="screen == 2" class="update">
                <span>{{ addOrUpdateParams.id != '' ? '修改教师信息' : '新增教师' }}</span>
                <el-form :model="addOrUpdateParams" style="margin-top: 20px;width: 500px;" label-width="120px"
                    ref="ruleFormRef" :rules="rules">
                    <el-form-item label="教师头像">
                        <el-upload class="avatar-uploader" action='/admin/common/upload' :headers="headers"
                            :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload"
                            limit="3" ref="upload">
                            <img v-if="addOrUpdateParams.avatar" :src="addOrUpdateParams.avatar" class="avatar" />
                            <el-icon v-else class="avatar-uploader-icon">
                                <Plus />
                            </el-icon>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="教师名称" prop="name">
                        <el-input v-model="addOrUpdateParams.name" placeholder="请输入企业名称"></el-input>
                    </el-form-item>
                    <el-form-item label="教师用户名" prop="username">
                        <el-input v-model="addOrUpdateParams.username" placeholder="请输入企业用户名"
                            :disabled="addOrUpdateParams.id != ''"></el-input>
                    </el-form-item>
                    <el-form-item label="教师用户密码" prop="password">
                        <el-input v-model="addOrUpdateParams.password" placeholder="请输入企业密码" type="password"></el-input>
                    </el-form-item>
                    <el-form-item label="教师电话" prop="phone">
                        <el-input v-model="addOrUpdateParams.phone" placeholder="请输入企业电话"></el-input>
                    </el-form-item>
                    <el-form-item label="教师邮箱" prop="email">
                        <el-input v-model="addOrUpdateParams.email" placeholder="请输入企业邮箱"></el-input>
                    </el-form-item>
                    <el-form-item style="margin-top: 30px;margin-left: 100px;">
                        <el-button type="" size="default" @click="cancel">取消</el-button>
                        <el-button color="#3F81A9" size="default" @click="save">确定</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-card>
    </div>
</template>

<script setup lang="ts">
import { reqGetTeacher, reqBatchTeacher, reqAddTeacher, reqUpdateTeacher, reqDeleteTeacher, reqUpdateStatus, reqGetTeacherDetail } from '@/api/school'
import { teacheRecords, teacherExcel } from '@/type/api/campus'
import { download } from '@/tools/download'
import { ElMessage } from 'element-plus';
import { ref, onMounted, reactive } from 'vue'
import { GET_TOKEN } from '@/tools/token'
import Paging from '@/components/Paging.vue'
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
// 搜索框
let search = ref<string>('')
// 总条数
let total = ref<number>(0)
// 当前第几页
let currentPage = ref<number>(1)
// 一页几条数据
let pageSize = ref<number>(10)
// 场景值
let screen = ref<number>(1)
// 上传头像的实例对象
let upload = ref()
// 收集选中的id
let idArry = ref([])
// 存储Excel上传的信息
let excelParams = ref<teacherExcel>([])
// 对话框的显示和隐藏
let dialogTableVisible = ref<boolean>(false)
// 存储table警告的下标
let errArry = ref<number[]>([])
// 获取原生input的实例
let excelUpload = ref()
// 动态生成table
let tableShow = ref<boolean>(true)
// 获取form表单的实例
let ruleFormRef = ref()
// 抽屉的显示和隐藏
let drawer = ref<boolean>(false)
// 列表的数据
let teacherList = ref<teacheRecords>([])
onMounted(() => {
    getHasTeacherList()
})
// 获取教师列表的参数
let teacherParams = reactive({
    page: 1,
    name: '',
    pageSize: '10',
    status: 0
})
// 新增或者修改的params参数
let addOrUpdateParams = reactive({
    id: '',
    avatar: '',
    email: '',
    name: '',
    password: '',
    phone: '',
    username: ''
})
// 列表详细信息
let teacherDetail = reactive({
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
// 获取教师列表数据
const getHasTeacherList = async (pager = 1, stus = 0) => {
    teacherParams.page = pager
    teacherParams.status = stus
    let result = await reqGetTeacher(teacherParams.page, teacherParams.pageSize, teacherParams.name, teacherParams.status)
    if (result.code == 200) {
        total.value = result.data.total
        teacherList.value = result.data.records
        teacherList.value.map(item => {
            if (item.status == 0) {
                item.switch = true
            } else {
                item.switch = false
            }
        })
    }
}
// 分页器展示列表发生改变
const handleSizeChange = (value: any) => {
    pageSize.value = value
    getHasTeacherList()
}
// 分页器改变页码
const handleCurrentChange = (data: any) => {
    currentPage.value = data
    getHasTeacherList(currentPage.value)
}
// 搜索的回调
const searchTeacher = async () => {
    // 清空select选中的值
    value.value = ''
    // 合并参数
    teacherParams.name = search.value
    if (value.value == 'normal') {
        teacherParams.status = 0
    } else {
        teacherParams.status = 1
    }
    await reqGetTeacher(teacherParams.page, teacherParams.pageSize, teacherParams.name, teacherParams.status).then(() => {
        ElMessage({
            type: 'success',
            message: '搜索成功'
        })
        getHasTeacherList()
    })
}
// 新增教师
const addTeacher = () => {
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
//用户上传成功之后的回调
// @ts-ignore
const handleAvatarSuccess = (response: any, uploadFile: any) => {
    // addOrUpdateParams.avatar = URL.createObjectURL(uploadFile.raw!)
    addOrUpdateParams.avatar = response.data
    upload.value.clearFiles(); //上传成功之后清除历史记录
}
// 获取token
let headers = reactive({
    Token: GET_TOKEN()
})
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
// 数据导出
const companyExport = async () => {
    download('get', '/admin/company/exportCompany', '全部企业信息')
}
// 批量删除
const batchDelete = async () => {
    if (idArry.value.length > 1) {
        await reqDeleteTeacher(idArry).then(() => {
            ElMessage({
                type: 'success',
                message: '删除成功'
            })
            getHasTeacherList()
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
// 清除上一次table表格样式的数据
const clearErrArry = () => {
    errArry.value = []
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
        let result = await reqBatchTeacher(excelParams.value)
        if (result.code == 200) {
            ElMessage({
                type: 'success',
                message: '插入成功'
            })
            dialogTableVisible.value = false
            getHasTeacherList()
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
// 删除企业
const deleteTeacher = async (id: string) => {
    await reqRemoveCompany(id).then(() => {
        ElMessage({
            type: 'success',
            message: '删除成功'
        })
        getHasTeacherList(teacherList.value.length > 1 ? currentPage.value : currentPage.value - 1)
    })
}
// 点击修改企业的信息
const updateTeacher = async (id: any) => {
    // 获取详细信息
    await reqGetTeacherDetail(id).then(res => {
        Object.assign(addOrUpdateParams, res.data)
        screen.value = 2
    })
}
// 获取企业列表详细信息
const getTeacherDetail = async (id: any) => {
    await reqGetTeacherDetail(id).then(res => {
        Object.assign(teacherDetail, res.data)
        drawer.value = true
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
            getHasTeacherList()
        })
    } else {
        status = 0
        await reqUpdateStatus(status, id).then(() => {
            ElMessage({
                type: 'success',
                message: '已恢复'
            })
            getHasTeacherList(1, 1)
        })
    }
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
        await reqUpdateTeacher(newObj).then(() => {
            ElMessage({
                type: 'success',
                message: '更新成功'
            })
            screen.value = 1
            getHasTeacherList(currentPage.value)
        })
    } else {
        let result = await reqAddTeacher(addOrUpdateParams)
        if (result.code == 200) {
            ElMessage({
                type: 'success',
                message: '添加成功'
            })
            screen.value = 1
            getHasTeacherList()
        } else {
            ElMessage({
                type: 'error',
                message: result.msg
            })

        }
    }
}
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