<template>
    <div class="outer">
        <!-- 头部信息 -->
        <el-card v-show="screen == 1" style="margin-bottom: 20px;" class="heard">
            <el-form inline :disabled="screen == 2">
                <el-form-item label="查询招聘信息">
                    <el-input v-model="keyWord" placeholder="请输入你要查询的内容"></el-input>
                </el-form-item>
                <el-form-item label="状态">
                    <el-select v-model="value" placeholder="请选择" style="width: 100px;" @change="valueChange">
                        <el-option v-for="item in Options" :label="item.label" :value="item.value"
                            :key="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="search">搜索</el-button>
                </el-form-item>
                <!-- 其他功能 -->
                <el-form-item>
                    <el-button type="primary" icon="plus" @click="addRecruit"
                        :disabled="activeName != 'Ordinary'">新增招聘信息</el-button>
                </el-form-item>
                <el-form-item>
                    <div style="display: flex;" v-show="activeName == 'Advanced'">
                        <div style="background-color: rgba(75, 0, 130, 0.4);width: 20px;height: 20px; margin-top: 6px; ">
                        </div>
                        <span>浏览热度高</span>
                        <div
                            style="background-color: rgba(178, 34, 34, 0.3);width: 20px;height: 20px; margin-top: 6px; margin-left: 20px;">
                        </div>
                        <span>浏览热度中</span>
                        <div
                            style="background-color: rgba(240, 230, 140, 0.2);width: 20px;height: 20px; margin-top: 6px; margin-left: 20px;">
                        </div>
                        <span>浏览热度低</span>
                        <div
                            style="background-color: rgba(255, 255, 255, 1);width: 20px;height: 20px; margin-top: 6px; margin-left: 20px;">
                        </div>
                        <span>浏览热度平淡</span>
                    </div>
                </el-form-item>
            </el-form>
        </el-card>
        <!-- 身体 -->
        <el-card style="height: 750px;">
            <el-tabs v-model="activeName" class="demo-tabs" @tab-click="tabClick">
                <el-tab-pane label="普通显示" name="Ordinary">
                    <!-- 展示列表的场景 -->
                    <div v-show="screen == 1">
                        <el-table :data="recruitList" border height="530" @select="gatId" @select-all="getIds">
                            <el-table-column label="批量操作" width="50" align="center" type="selection"></el-table-column>
                            <el-table-column label="招聘岗位" align="center" prop="jobName">
                            </el-table-column>
                            <el-table-column label="工作地址" align="center" prop="jobAddress">
                            </el-table-column>
                            <el-table-column label="所需学历" align="center" prop="education">
                            </el-table-column>
                            <el-table-column label="工作经验" align="center" prop="jobExperience">
                            </el-table-column>
                            <el-table-column label="最低工资" align="center" prop="minSalary">
                            </el-table-column>
                            <el-table-column label="最高工资" align="center" prop="maxSalary">
                            </el-table-column>
                            <el-table-column label="操作" align="center" width="240">
                                <!-- @vue-ignore -->
                                <template #="{ row, index }">
                                    <el-popconfirm :title="`你确定要删${row.jobName}吗?`" @confirm="deleteRecruit(row.id)">
                                        <template #reference>
                                            <el-button type="danger" icon="delete" title="删除招聘信息" size="small"
                                                style="margin-right: 10px;"></el-button>
                                        </template>
                                    </el-popconfirm>
                                    <el-switch v-model="row.switch" @change="updateStatus(row.status, row.id)" />
                                    <el-button type="warning" icon="edit" title="修改招聘信息" size="small"
                                        style="margin-left: 10px;" @click="updateRecruit(row.id)"></el-button>
                                    <el-button type="info" icon="InfoFilled" title="查看信息" size="small"
                                        style="margin-left: 10px;" @click="getRecruitDetail(row.id)"></el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <!-- 分页器 -->
                        <Paging :currentPage="currentPage" :pageSize="pageSize" :total="total"
                            @sizeChange="handleSizeChange" @currentChange="handleCurrentChange"
                            :pageSizes="[3, 5, 7, 10]" />
                    </div>
                    <!-- 修改或者添加的场景 -->
                    <div v-show="screen == 2" class="update">
                        <span>{{ addOrUpdateParams.id ? '修改招聘信息' : '新增招聘信息' }}</span>
                        <el-button type="success" @click="selectScreen = !selectScreen"
                            style="margin-left: 60px;">切换模式</el-button>
                        <div class="form">
                            <el-form :model="addOrUpdateParams" style="margin-top: 30px;width:43%;height: 600px;"
                                label-width="100px" ref="ruleFormRef" :rules="rules">
                                <div style="display: flex;flex-wrap: wrap;width: 100%;height: 100%;" v-show="selectScreen">
                                    <el-form-item label="招聘岗位" prop="jobName">
                                        <el-input v-model="addOrUpdateParams.jobName" placeholder="请输入招聘岗位"></el-input>
                                    </el-form-item>
                                    <el-form-item label="工作地址" prop="jobAddress">
                                        <el-input v-model="addOrUpdateParams.jobAddress" placeholder="请输入工作地址"></el-input>
                                    </el-form-item>
                                    <el-form-item label="所需学历" prop="education">
                                        <el-input v-model="addOrUpdateParams.education" placeholder="请输入所需学历"></el-input>
                                    </el-form-item>
                                    <el-form-item label="工作经验" prop="jobExperience">
                                        <el-input v-model="addOrUpdateParams.jobExperience"
                                            placeholder="请输入工作经验"></el-input>
                                    </el-form-item>
                                    <el-form-item label="最低工资" prop="minSalary">
                                        <el-input v-model="addOrUpdateParams.minSalary" placeholder="请输入最低工资"></el-input>
                                    </el-form-item>
                                    <el-form-item label="最高工资" prop="maxSalary">
                                        <el-input v-model="addOrUpdateParams.maxSalary" placeholder="请输入最高工资"></el-input>
                                    </el-form-item>
                                    <el-form-item label="所需人数" prop="needPeople">
                                        <el-input v-model="addOrUpdateParams.needPeople" placeholder="请输入所需人数"></el-input>
                                    </el-form-item>
                                    <el-form-item style="margin-top: 30px;margin-left: 50px;">
                                        <el-button type="" size="default" @click="cancel">取消</el-button>
                                        <el-button color="#3F81A9" size="default" @click="save">确定</el-button>
                                    </el-form-item>
                                </div>
                                <!-- 第二种选择模式 -->
                                <RecruitSelect v-show="!selectScreen" :addOrUpdateParams="addOrUpdateParams" />
                                <el-form-item style="margin-top: 63px;margin-left: 50px;">
                                    <el-button type="" size="default" @click="cancel">取消</el-button>
                                    <el-button color="#3F81A9" size="default" @click="save">确定</el-button>
                                </el-form-item>
                            </el-form>
                            <!-- 富文本编辑器 -->
                            <div class="richText">
                                <Editor id="tinymce" :init="init" v-model="tinymceHtml"></Editor>
                            </div>
                        </div>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="高级显示" name="Advanced">
                    <el-table :data="recruitList" @expand-change="expandChange" height="600"
                        :row-class-name="tableRowClassName">
                        <el-table-column sortable :filters="filters" label="创建时间" align="center" prop="createTime"
                            :filter-method="filterHandler"></el-table-column>
                        <el-table-column label="招聘岗位" align="center" prop="jobName">
                        </el-table-column>
                        <el-table-column label="工作地址" align="center" prop="jobAddress">
                        </el-table-column>
                        <el-table-column label="所需学历" align="center" prop="education">
                        </el-table-column>
                        <el-table-column label="工作经验" align="center" prop="jobExperience">
                        </el-table-column>
                        <el-table-column sortable label="最低工资" align="center" prop="minSalary">
                        </el-table-column>
                        <el-table-column sortable label="最高工资" align="center" prop="maxSalary">
                        </el-table-column>
                        <el-table-column label="操作" align="center" width="240" prop="button">
                            <!-- @vue-ignore -->
                            <template #="{ row, index }">
                                <el-popconfirm :title="`你确定要删${row.jobName}吗?`" @confirm="deleteRecruit(row.id)">
                                    <template #reference>
                                        <el-button type="danger" icon="delete" title="删除招聘信息" size="small"
                                            style="margin-right: 10px;"></el-button>
                                    </template>
                                </el-popconfirm>
                                <el-switch v-model="row.switch" @change="updateStatus(row.status, row.id)" />
                                <el-button type="warning" icon="edit" title="修改招聘信息" size="small" style="margin-left: 10px;"
                                    @click="updateRecruit(row.id)"></el-button>
                                <el-button type="success" icon="Top" size="small" v-if="row.isPinned"
                                    @click="recruitTop(row)"></el-button>
                                <el-button type="info" icon="Bottom" size="small" v-else
                                    @click="recruitTop(row)"></el-button>
                            </template>
                        </el-table-column>
                        <el-table-column type="expand" title="详细信息">
                            <!-- @vue-ignore -->
                            <template #="{ row }">
                                <div v-html="recruitDetail.content" class="recruitContent">
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                    <Paging :currentPage="currentPage" :pageSize="advancedPageSize" :total="total"
                        @sizeChange="handleSizeChange" @currentChange="handleCurrentChange"
                        :pageSizes="[20, 30, 70, 100]" />
                </el-tab-pane>
                <el-tab-pane label="描述显示" name="Description" :disabled="true">
                    <div class="descriptions">
                        <div class="leftImg">
                            <img :src="recruitDetail.companyAvatar">
                        </div>
                        <div class="rightContent">
                            <el-descriptions :title="`${recruitDetail.companyName}的全部信息`" direction="vertical" :column="4"
                                size="large" border>
                                <el-descriptions-item label="企业名称">{{ recruitDetail.companyName }}</el-descriptions-item>
                                <el-descriptions-item label="招聘信息">{{ recruitDetail.jobName }}</el-descriptions-item>
                                <el-descriptions-item label="企业信息" :span="4">
                                    <div style="display: flex;">
                                        <span>招聘状态:{{ recruitDetail.status == 0 ? '正常' : '禁用' }}</span>
                                        <span style="margin-left: 10px;">所需人数:{{ recruitDetail.needPeople }}</span>
                                        <span style="margin-left: 10px;">浏览总数:{{ recruitDetail.viewCount }}</span>
                                    </div>
                                </el-descriptions-item>
                                <el-descriptions-item label="要求">
                                    <el-tag size="small">{{ recruitDetail.education }}</el-tag>
                                    <el-tag size="small" style="margin-left: 10px;">{{ recruitDetail.jobExperience
                                    }}</el-tag>
                                </el-descriptions-item>
                                <el-descriptions-item label="工作地点">
                                    {{ recruitDetail.jobAddress }}
                                </el-descriptions-item>
                                <el-descriptions-item label="最低工资">{{ recruitDetail.minSalary }}</el-descriptions-item>
                                <el-descriptions-item label="最高工资">{{ recruitDetail.maxSalary }}</el-descriptions-item>
                            </el-descriptions>
                        </div>
                    </div>
                    <el-scrollbar height="300px">
                        <el-descriptions size="large" direction="vertical">
                            <el-descriptions-item :label="`${recruitDetail.companyName}的招聘信息`">
                                <div v-html="recruitDetail.content" >

                                </div>
                            </el-descriptions-item>
                        </el-descriptions>
                    </el-scrollbar>
                </el-tab-pane>

            </el-tabs>
        </el-card>
    </div>
</template>

<script setup lang="ts">
import Paging from '@/components/Paging.vue'
import tinymce from 'tinymce/tinymce'
import 'tinymce/themes/silver/theme'
import Editor from '@tinymce/tinymce-vue'
import { ref, onMounted, reactive } from 'vue';
import { reqRecruitDetail, reqGetMyRecruit, reqAddRecruit, reqUpdateRecruit, reqDeleteRecruit, reqModifyRecruit, reqUpdatePinned } from '@/api/company'
import axios from 'axios'
import { Records, recruitParams } from '@/type/api/recruit'
import { ElMessage } from 'element-plus';
import type { TableColumnCtx } from 'element-plus'
import { GET_TOKEN } from '@/tools/token';
import RecruitSelect from './recruitSelect.vue';
import otherStore from '@/store/modules/other'
let useOtherStore = otherStore()
defineOptions({
    name: 'tinymce'
})
// table表格的ts类型
interface User {
    createTime: string
    jobName: string
    jobAddress: string
    education: string
    jobExperience: string
    minSalary: string
    maxSalary: string
    button: string
}
// 搜索内容
let keyWord = ref<string>('');
// select选中的数值
let value = ref('normal');
// option的值
let Options = [
    {
        value: 'normal',
        label: '正在招聘'
    },
    {
        value: 'disable',
        label: '招聘结束'
    }
]
// tabs标签默认显示
let activeName = ref<string>('Ordinary')
// 总条数
let total = ref<number>(0)
// 当前第几页
let currentPage = ref<number>(1)
// 一页几条数据
let pageSize = ref<number>(10)
// 高级table一页几条数据
let advancedPageSize = ref<number>(30)
// 场景值
let screen = ref<number>(1)
let selectScreen = ref<boolean>(false)
// 获取form表单的实例
let ruleFormRef = ref()
// 收集选中的id
let idArry = ref([])
// filters过滤的数组
let filters = ref<Array<{ text: string, value: string }>>([])
// 请求携带的参数
let recruitParams = reactive({
    page: 1,
    pageSize: '10',
    condition: '',
    status: 0
})
// 更新或添加的参数
let addOrUpdateParams = reactive<recruitParams>({
    content: '',
    education: '',
    jobAddress: '',
    jobExperience: '',
    jobName: '',
    maxSalary: '',
    minSalary: '',
    needPeople: '',
})
// 招聘的详细信息
let recruitDetail = reactive({
    companyName: '',
    companyAvatar: '',
    jobName: '',
    jobAddress: '',
    education: '',
    minSalary: 0,
    maxSalary: 0,
    jobExperience: '',
    needPeople: '',
    createTime: '',
    viewCount: 0,
    status: 0,
    content: ''
})
// 富文本上传的url
let url = ref<any>()
// 富文本里面的数据
let tinymceHtml = ref('')
// 上传文件的方法
const upfile = (data: any) => {
    return axios({
        url: '/admin/common/upload',
        method: 'post',
        headers: {
            'Content-Type': 'multipart/form-data;boundary=----WebKitFormBoundarysyCsUaLV5WrP69tB',
            'token': GET_TOKEN()
        },
        data
    })
}
// tinymce初始化的数据
let init = reactive({
    language_url: '/tinymce/zh-Hans.js',
    language: 'zh-Hans',
    skin_url: '/tinymce/skins/ui/oxide',
    height: 600,
    plugins: 'link lists image code table colorpicker textcolor wordcount contextmenu',
    toolbar:
        'bold italic underline strikethrough | fontsizeselect | forecolor backcolor | alignleft aligncenter alignright alignjustify | bullist numlist | outdent indent blockquote | undo redo | link unlink image code | removeformat',
    branding: false,
    promotion: false,
    placeholder: '请在此输入招聘信息的内容..............如果需要上传图片请注意上传的尺寸大小',
    menubar: 'edit insert view format table tools help',
    //自定义文件选择器的回调内容
    // @ts-ignore
    file_picker_callback: (cb: any, value: any, meta: any) => {
        const input = document.createElement('input');
        input.setAttribute('type', 'file');
        input.setAttribute('accept', 'image/*');
        input.addEventListener('change', (e) => {
            // @ts-ignore
            const file = e.target.files[0];
            url.value = file
            const reader = new FileReader();
            reader.addEventListener('load', async () => {
                const formData = new FormData()
                formData.append('file', file);
                let result = await upfile(formData)
                // @ts-ignore
                if (result.status == 200) {
                    cb(result.data.data, { title: file.name });
                    return
                } else {
                    ElMessage({
                        type: 'error',
                        message: '上传错误可能是文件太大'
                    })
                }
            });
            reader.readAsDataURL(file);
        });
        input.click();
    },
})
// tabs标签的点击事件
const tabClick = (TabsPaneContext: any) => {
    if (TabsPaneContext.props.name == 'Advanced') {
        getHasRecruitList(1, 0, advancedPageSize.value)
    }
    if (TabsPaneContext.props.name == 'Ordinary' || TabsPaneContext.props.name == 'Description') {
        getHasRecruitList()
    }
}
// 存储用户列表信息
let recruitList = ref<Records>([])
onMounted(() => {
    getHasRecruitList()
    // @ts-ignore
    tinymce.init({});
})
// table筛选数据过滤使用的方法
const filterHandler = (
    value: string,
    row: User,
    column: TableColumnCtx<User>
) => {
    const property = column['property']
    // @ts-ignore
    return row[property] === value
}
// table展开行时候的回调
const expandChange = async (row: any, expandedRows: any) => {
    if (expandedRows.length > 0) {
        recruitDetail.content = ''
    }
    await reqRecruitDetail(row.id).then(res => {
        Object.assign(recruitDetail, res.data)
    }, () => {
        ElMessage({
            type: 'error',
            message: '拉取失败检查网络'
        })
    })
}
// table表格状态的回调
const tableRowClassName = ({
    // @ts-ignore
    row,
    rowIndex,
}: {
    row: any
    rowIndex: number
}) => {
    if (row.viewCount >= 20) {
        rowIndex = 1
    } else if (row.viewCount > 15 && row.viewCount < 20) {
        rowIndex = 2
    } else if (row.viewCount < 15 && row.viewCount > 7) {
        rowIndex = 3
    } else {
        return ''
    }
    if (rowIndex === 1) {
        return 'purple'
    }
    if (rowIndex === 2) {
        return 'red'
    }
    if (rowIndex === 3) {
        return 'yellow'
    }
}
// 获取页面的信息
let getHasRecruitList = async (pager = 1, stus = 0, pagesize = 10) => {
    recruitParams.page = pager
    recruitParams.pageSize = pagesize + ''
    recruitParams.status = stus
    await reqGetMyRecruit(recruitParams.condition, recruitParams.page, recruitParams.pageSize, recruitParams.status).then(res => {
        total.value = res.data.total
        recruitList.value = res.data.records
        // 处理时间格式
        recruitList.value.map(item => {
            item.createTime = item.createTime.split('T')[0]
            filters.value.push({ text: item.createTime, value: item.createTime })
        })
        // filters数组对象去重
        let map = new Map();
        for (let item of filters.value) {
            if (!map.has(item.text)) {
                map.set(item.text, item);
            };
        };
        filters.value = [...map.values()];
        // 插入switch开关可以识别的值
        recruitList.value.map(item => {
            if (item.status == 0) {
                // @ts-ignores
                item.switch = true
            } else {
                // @ts-ignore
                item.switch = false
            }
        })
    })
}
// 搜索API方法
const searchAPI = async (keyword: string) => {
    recruitParams.condition = keyword

    await reqGetMyRecruit(recruitParams.condition, recruitParams.page, recruitParams.pageSize, recruitParams.status).then(res => {
        total.value = res.data.total
        recruitList.value = res.data.records
        recruitList.value.map(item => {
            if (item.status == 0) {
                // @ts-ignore
                item.switch = true
            } else {
                // @ts-ignore
                item.switch = false
            }
        })
        ElMessage({
            type: 'success',
            message: '搜索成功'
        })
    })
}
// 搜索招聘信息
let search = async () => {
    searchAPI(keyWord.value)
}
// 新增招聘信息
let addRecruit = () => {
    tinymceHtml.value = ''
    Object.assign(addOrUpdateParams, {
        id: '',
        content: '',
        education: '',
        jobAddress: '',
        jobExperience: '',
        jobName: '',
        maxSalary: '',
        minSalary: '',
        needPeople: '',
    })
    screen.value = 2
}
// 删除招聘信息
let deleteRecruit = async (id: string) => {
    await reqDeleteRecruit(id).then(() => {
        ElMessage({
            type: 'success',
            message: '删除成功'
        })
        getHasRecruitList()
    })
}
// 修改状态
let valueChange = (value: any) => {
    if (value == 'disable') {
        getHasRecruitList(1, 1)
    } else {
        getHasRecruitList(1, 0)
    }
}
// table高级显示置顶回调
const recruitTop = async (row: any) => {
    if (row.isPinned) {
        await reqUpdatePinned(row.id, 0).then(() => {
            ElMessage({
                type: 'info',
                message: '取消置顶'
            })
            getHasRecruitList(currentPage.value, 0, advancedPageSize.value)
        })
    } else {
        await reqUpdatePinned(row.id, 1).then(() => {
            ElMessage({
                type: 'success',
                message: '置顶成功'
            })
            getHasRecruitList(currentPage.value, 0, advancedPageSize.value)
        })
    }
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
// 修改企业的状态
const updateStatus = async (status: number, id: string) => {
    if (status == 0) {
        status = 1
        await reqModifyRecruit(status, id).then(() => {
            ElMessage({
                type: 'info',
                message: '已修改为：招聘结束'
            })
            getHasRecruitList()
        })
    } else {
        status = 0
        await reqModifyRecruit(status, id).then(() => {
            ElMessage({
                type: 'success',
                message: '已修改为：正在招聘'
            })
            getHasRecruitList(1, 1)
        })
    }
}
// 分页器展示列表发生改变
const handleSizeChange = (value: any) => {
    // 由于组件是二次封装所以需要更新当前的pageSize列表的数据
    if (activeName.value == 'Ordinary') {
        pageSize.value = value
    }
    if (activeName.value == 'Advanced') {
        advancedPageSize.value = value
    }
    getHasRecruitList(1, 0, value)
}
// 获取当前页面的数据
const handleCurrentChange = (data: any) => {
    const { value, limit } = data
    // 由于组件是二次封装所以需要更新当前的当前页码和pageSize的数据
    currentPage.value = value;
    getHasRecruitList(currentPage.value, 0, limit)
}
// 获取企业列表详细信息
const getRecruitDetail = async (id: any) => {
    await reqRecruitDetail(id).then(res => {
        Object.assign(recruitDetail, res.data)
        activeName.value = 'Description'
    }, () => {
        ElMessage({
            type: 'error',
            message: '获取失败请检查网络'
        })
    })
}
// 添加更新按钮取消的回调
const cancel = () => {
    // 清除上一次的表单校验效果
    ruleFormRef.value.clearValidate()
    // 清除子组件里遗留的数据
    addOrUpdateParams.jobAddress = ''
    // 清除子组件保存仓库中的数据
    useOtherStore.selectedOptions = ['']
    Object.assign(addOrUpdateParams, {
        content: '',
        education: '',
        jobAddress: '',
        jobExperience: '',
        jobName: '',
        maxSalary: '',
        minSalary: '',
        needPeople: '',
    })
    tinymceHtml.value = ''
    screen.value = 1

}
// 详细信息按钮确定的回调
const save = async () => {
    if (addOrUpdateParams.id) {
        addOrUpdateParams.content = tinymceHtml.value
        // 判断招聘信息是否为空
        if (tinymceHtml.value == '') {
            ElMessage({
                type: 'info',
                message: '请输入招聘信息'
            })
            return
        }
        let result: any = await reqUpdateRecruit(addOrUpdateParams)
        if (result.code == 200) {
            ElMessage({
                type: 'success',
                message: '修改成功'
            })
            screen.value = 1
            getHasRecruitList(currentPage.value)
        } else {
            ElMessage({
                type: 'error',
                message: result.msg
            })
        }
    } else {
        await ruleFormRef.value.validate()
        // 判断招聘信息是否为空
        if (tinymceHtml.value == '') {
            ElMessage({
                type: 'info',
                message: '请输入招聘信息'
            })
            return
        }
        // 把字符串类型转换为number类型
        addOrUpdateParams.maxSalary = (+addOrUpdateParams.maxSalary)
        addOrUpdateParams.minSalary = (+addOrUpdateParams.minSalary)
        addOrUpdateParams.needPeople = (+addOrUpdateParams.needPeople)
        addOrUpdateParams.content = tinymceHtml.value
        let result: any = await reqAddRecruit(addOrUpdateParams)
        if (result.code == 200) {
            ElMessage({
                type: 'success',
                message: '添加成功'
            })
            screen.value = 1
            getHasRecruitList()
        } else {
            ElMessage({
                type: 'error',
                message: result.msg
            })
        }
    }

}
// 修改招聘信息
const updateRecruit = async (id: string) => {
    screen.value = 2
    await reqRecruitDetail(id).then(res => {
        Object.assign(addOrUpdateParams, res.data)
        tinymceHtml.value = addOrUpdateParams.content
    })
}
// 校验招聘岗位
// @ts-ignore
const validateJobName = (rule: any, value: any, callback: any) => {
    if (!/^[\u4E00-\u9FA5A-Za-z]{2,20}$/.test(value)) {
        callback('请输入合理的工作岗位信息,请不要输入数字')
    } else {
        callback()
    }
}
// 校验工作地址
// @ts-ignore
const validateJobAddress = (rule: any, value: any, callback: any) => {
    if (!/^[\u4E00-\u9FA5A-Za-z0-9]{3,20}$/.test(value)) {
        callback('请输入合理的地址信息')
    } else {
        callback()
    }
}
// 校验所需学历
// @ts-ignore
const validateEducation = (rule: any, value: any, callback: any) => {
    if (!/^[\u4E00-\u9FA5A-Za-z]{2,20}$/.test(value)) {
        callback('请输入合理的学历信息,请不要输入数字')
    } else {
        callback()
    }
}
// 校验工作经验
// @ts-ignore
const validateJobExperience = (rule: any, value: any, callback: any) => {
    if (!/^[\u4E00-\u9FA50-9]{2,20}$/.test(value)) {
        callback('请输入合理的工作经验,请不要输入26位字母')
    } else {
        callback()
    }
}
// 校验最低工资
// @ts-ignore
const validateMinSalary = (rule: any, value: any, callback: any) => {
    if (!/^\d{3,10}$/.test(value)) {
        callback('请输入合理的工资,只能输入数字,最小3位')
    } else {
        callback()
    }
}
// 校验最高工资
// @ts-ignore
const validateMaxSalary = (rule: any, value: any, callback: any) => {
    if (!/^\d{3,10}$/.test(value)) {
        callback('请输入合理的工资,只能输入数字,最小3位')
    } else {
        callback()
    }
}
// 校验所需人数
// @ts-ignore
const validateNeedPeople = (rule: any, value: any, callback: any) => {
    if (!/^\d{1,10}$/.test(value)) {
        callback('请输入合理的人数')
    } else {
        callback()
    }
}
// 校验对象
const rules = reactive({
    jobName: [{ validator: validateJobName, trigger: 'blur' }],
    jobAddress: [{ validator: validateJobAddress, trigger: 'blur' }],
    education: [{ validator: validateEducation, trigger: 'blur' }],
    jobExperience: [{ validator: validateJobExperience, trigger: 'blur' }],
    minSalary: [{ validator: validateMinSalary, trigger: 'blur' }],
    maxSalary: [{ validator: validateMaxSalary, trigger: 'blur' }],
    needPeople: [{ validator: validateNeedPeople, trigger: 'blur' }],
})
</script>

<style scoped lang="scss">
.outer {
    width: 100%;
    height: 100%;

    .heard {
        .el-input {
            margin-top: -5px;
        }
    }
}

.recruitContent {
    width: 100%;
    padding: 20px 40px;
    background-color: #F8FBFB;
}

.descriptions {
    padding: 30px;
    box-sizing: border-box;
    display: flex;
    flex-wrap: wrap;

    .leftImg {
        width: 20%;
        border: 0.5px solid black;

        img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
    }

    .rightContent {
        width: 80%;
        padding: 20px;
    }
}

.update {
    .el-button {
        height: 42px;
        width: 100px;
    }
}

.el-input {
    height: 42px;
    width: 300px;
    margin-top: 7px;
}

.avatar-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
    box-sizing: border-box;
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

.form {
    width: 100%;
    height: 100%;
    display: flex;

    // from的label信息
    .el-form ::v-deep .el-form-item__label {
        line-height: 50px;
        margin-top: 6px;
    }

    // 错误提示信息
    .el-form-item ::v-deep .el-form-item__error {
        top: 95%;
    }

    .richText {
        width: 57%;
        height: 600px;
    }

}

.el-table ::v-deep .purple {
    // --el-color-warning-light-9
    --el-table-tr-bg-color: rgba(75, 0, 130, 0.4);
}

.el-table ::v-deep .red {
    // --el-color-warning-light-9
    --el-table-tr-bg-color: rgba(178, 34, 34, 0.3);
}

.el-table ::v-deep .yellow {
    // --el-color-warning-light-9
    --el-table-tr-bg-color: rgba(240, 230, 140, 0.2);
}

.el-descriptions {
    margin-top: 20px;
}
</style>