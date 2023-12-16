<template>
    <div class="outer">
        <div style="display: flex;margin-top: 10px;position: relative;width: 100%;height: 100%;">
            <!-- logo -->
            <div class="img" v-show="searchList.length < 1"></div>
            <!-- input输入框 -->
            <div class="input" style="z-index: 10;">
                <el-input class="inputDeep" v-model="input" placeholder="请输入你要查询的招聘信息"
                    @keyup.enter.native="search"></el-input>
            </div>
            <!-- icon图标 -->
            <div class="search" style="z-index: 12;" @click="search">
                <el-icon size="20" color="#000">
                    <Promotion />
                </el-icon>
            </div>
            <!-- 滚动区域 -->
            <div class="scrollbar" style="z-index: 9;box-sizing: border-box;padding-top: 100px;">
                <el-scrollbar style="height: 100%;" ref="scrollbar" @scroll="scrollMenu">
                    <div style="height: 100%;">
                        <div v-for="(item, index) in searchList" :key="index" style="display: flex;flex-wrap: wrap;">
                            <!-- 搜索记录 -->
                            <el-card style="margin-top: 50px;width: 100%;">
                                <div style="display: flex;justify-content: space-between;">
                                    {{ defaultShow(item) }}
                                    <el-icon @click="removeItem(index)">
                                        <Minus />
                                    </el-icon>
                                </div>
                            </el-card>
                            <!-- 搜索结果 -->
                            <el-card style="margin-top: 5px;width: 48%;margin-left: 10px;"
                                v-for="(item, index) in allSearchList" :key="index" @click="detailInfo(item.id)">
                                <template #header>
                                    <div v-show="item" class="header">
                                        <span>{{ item.jobName }}</span>
                                        <span style="margin-left: 200px;color: #FE7C4A;">{{ item.minSalary / 1000 + "K" }} -
                                            {{
                                                item.maxSalary
                                                / 1000 +
                                                "K" }}</span>
                                    </div>
                                    <div class="body">
                                        <el-tag color="#F8F8F8">{{ item.jobAddress }}</el-tag>
                                        <el-tag color="#F8F8F8">{{ item.education }}</el-tag>
                                        <el-tag color="#F8F8F8">{{ item.jobExperience }}</el-tag>
                                    </div>
                                </template>
                                <div style="display: flex;justify-content: space-between;">
                                    <span>公司详情</span>
                                    <el-icon >
                                        <ArrowRight />
                                    </el-icon>
                                </div>
                            </el-card>
                            <!-- 提示信息 -->
                            <div style="height: 300px;width: 100%;display: flex;" v-show="hidden == 1">
                                <span style="font-size: 36px;font-weight: 800;margin: 20px auto;">没有更多数据了.......</span>
                            </div>
                        </div>
                    </div>
                </el-scrollbar>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ElMessage } from 'element-plus';
import { ref, reactive } from 'vue'
import { reqGetRecruit } from '@/api/company'
import { Records } from '@/type/api/recruit'
import layoutStore from '@/store/modules/layout'
let useLayoutStore = layoutStore()
// 存储input的值
let input = ref<string>('')
// 存储此次搜索记录
let searchList = ref<string[]>([])
// 存储全部搜索记录
let allSearchList = ref<Records>([])
// 存储搜索结果
let searchResList = ref<Records>([])
// 当前页
let page = ref<number>(1)
// 请求携带的参数
let recruitForm = reactive({
    page: 1,
    pageSize: '10',
    condition: '',
    status:0
})
// 获取滚动条实例
let scrollbar = ref()
// 控制提示信息的显示与隐藏
let hidden = ref<number>(0)
// 判断第二次请求的变量
const flag = ref<number>(0)
// 搜索按钮提交
const search = async () => {
    input.value = input.value.trim()
    flag.value++
    searchList.value = []
    searchList.value.push(input.value)
    // 整理页码参数
    recruitForm.page = page.value
    // 非第一次发送请求 相同的请求
    if (recruitForm.page != 1 && input.value == recruitForm.condition) {
        // 判断上一次的结果是否为空
        if (searchResList.value.length < 1) {
            return
        }
        await reqGetRecruit(recruitForm.page,recruitForm.pageSize,recruitForm.condition,recruitForm.status).then(res => {
            if (res.data.records.length < 1) {
                hidden.value = 1
            }
            // 接收数据
            searchResList.value = res.data.records
            // 存储到全部数据里面
            allSearchList.value.push(...searchResList.value)
        }).catch(err => {
            ElMessage({
                type: 'error',
                message: err.msg
            })
        })
        // 清空上一次搜索记录
        input.value = ''
        return
    }
    // 第二次 不一样的请求
    if (recruitForm.condition != input.value) {
        page.value = 1
        // 整理页码参数
        recruitForm.page = page.value
        // 清空上一次的数组
        allSearchList.value = []
        hidden.value = 0
        // 整理搜索内容参数
        recruitForm.condition = input.value
        // 发送请求
        await reqGetRecruit(recruitForm.page,recruitForm.pageSize,recruitForm.condition,recruitForm.status).then(res => {
            // 接收数据
            searchResList.value = res.data.records
            // 存储到全部数据里面
            allSearchList.value.push(...searchResList.value)
        }).catch(err => {
            ElMessage({
                type: 'error',
                message: err.msg
            })
        })
        // 清空上一次搜索记录
        input.value = ''
        return
    } else {
        // 第二次一样的请求
        if (flag.value != 1) return
    }
    // 第一次发送请求
    // 整理搜索内容参数
    recruitForm.condition = input.value
    // 发送请求
    await reqGetRecruit(recruitForm.page,recruitForm.pageSize,recruitForm.condition,recruitForm.status).then(res => {
        // 接收数据
        searchResList.value = res.data.records
        // 存储到全部数据里面
        allSearchList.value.push(...searchResList.value)
    }).catch(err => {
        ElMessage({
            type: 'error',
            message: err.msg
        })
    })
    // 清空上一次搜索记录
    input.value = ''
}
// 删除按钮的回调
const removeItem = (index: number) => {
    // 初始化所有数据
    allSearchList.value = []
    page.value = 1
    recruitForm.page = 1
    searchResList.value = []
    recruitForm.condition = ""
    flag.value = 0
    hidden.value = 0
    searchList.value.splice(index, 1)
}
// 空字符串的默认显示
const defaultShow = (item: any) => {

    if (item.trim() === "") {
        return item = '热门推荐'
    } else {
        return item
    }
}
// 查看详情的回调
const detailInfo = (item:any) => {
    // 通过仓库修改场景
    useLayoutStore.masks = 3
    // 传递数据
    // @ts-ignore
    useLayoutStore.dataArr = allSearchList.value
    // 传递索引
    useLayoutStore.id = item
}
// 监听向下滚动的距离
const scrollMenu = () => {
    // clientHeight:内容的高度
    // scrollTop:距离顶部的边距
    // scrollHeight:一个元素内容高度 盒子的高度
    page.value += 1
        search()
}
</script>

<style scoped lang="scss">
.outer {
    width: 100%;
    height: 100%;
}

// 去掉input的边框
.inputDeep {
    :deep(.el-input__wrapper) {
        box-shadow: 0 0 0 0px var(--el-input-border-color, var(--el-border-color)) inset;
        background-color: white;
        cursor: default;

        .el-input__inner {
            cursor: default !important;
        }
    }
}

.input {
    position: absolute;
    width: 50%;
    height: 50px;
    box-shadow: 0px 0px 10px #ccc;
    border-radius: 50px;
    overflow: hidden;
    bottom: 50px;
    left: 25%;

    :deep(.el-input__inner) {
        height: 50px;
        font-size: 16px;
    }
}

.search {
    position: absolute;
    bottom: 62px;
    left: 73%;
}

.scrollbar {
    margin-left: 20%;
    width: 60%;
    height: 100%;

    .el-card ::v-deep .el-card__body {
        background-color: #F9FCFB;
    }
}

.img {
    position: absolute;
    top: 20%;
    left: 40%;
    width: 20%;
    height: 30%;
    background: url('@/assets/images/bg-logo.jpg') no-repeat;
    background-size: 100% 100%;
}

.header {
    display: flex;

}

.body {
    margin: 10px 0px;
    letter-spacing: 3px;

    .el-tag {
        color: #7E667E;
        border: none;
        margin-left: 5px;
    }

}
</style>