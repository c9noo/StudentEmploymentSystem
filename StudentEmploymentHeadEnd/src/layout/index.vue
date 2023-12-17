<template>
    <div class="layout">
        <!-- 左侧菜单 -->
        <div class="layout_menu" :class="{ fold: useLayoutStore.fold ? true : false }">
            <Logo :fold="useLayoutStore.fold" />
            <ElScrollbar class="scrollbar">
                <el-menu default-active="1" style="--el-menu-bg-color:#3F81A9;"
                    :collapse="useLayoutStore.fold ? true : false" background-color="#3F81A9">
                    <Menu :menuList="useUserStore.menuRoutes" />
                </el-menu>
            </ElScrollbar>
                <PersonalCenter @showMasks="updateIsShow" class="personalCenter" :fold="useLayoutStore.fold"/>
        </div>
        <!-- 右侧 -->
        <div class="layout_right" :class="{ fold: useLayoutStore.fold ? true : false }">
            <!-- 顶部面包屑导航 -->
            <div class="layout_tabbar">
                <Tabbar />
            </div>
            <!-- 展示内容区 -->
            <div class="layout_main">
                <Main />
            </div>
        </div>
        <!-- 蒙版 -->
        <div class="masks" v-show="isShow">
            <!-- 查看用户信息 -->
            <div class="getInfo" v-show="scenario == 1">
                <el-card style="width: 50%;height: 500px;margin: 0 auto;">
                    <template #header>
                        <div class="card-header">
                            <span style="font-size: 24px;">查看用户信息</span>
                            <el-icon style="float: right;" size="24" @click="isShow = false">
                                <Close />
                            </el-icon>
                        </div>
                    </template>
                    <el-row>
                        <el-col :span="10">
                            <div class="avatar">
                                <img :src="updateForm.avatar" style="width: 100%;height: 100%;object-fit: cover;" alt="">
                            </div>
                        </el-col>
                        <el-col :span="14">
                            <div class="text">
                                <div class="textItem"><el-icon>
                                        <User />
                                    </el-icon><span>用户名:&nbsp;&nbsp;{{ useUserStore.name }}</span> </div>
                                <div class="textItem"><el-icon>
                                        <UserFilled />
                                    </el-icon><span>登录账号:&nbsp;&nbsp;{{ useUserStore.username }}</span></div>
                                <div class="textItem"><el-icon>
                                        <Lock />
                                    </el-icon><span>登录密码:&nbsp;&nbsp;*************</span></div>
                                <div class="textItem"><el-icon>
                                        <Iphone />
                                    </el-icon><span>电话:&nbsp;&nbsp;{{ useUserStore.phone }}</span></div>
                                <div class="textItem"><el-icon>
                                        <MessageBox />
                                    </el-icon><span>邮箱:&nbsp;&nbsp;{{ useUserStore.email }}</span></div>
                            </div>
                        </el-col>
                    </el-row>
                </el-card>
            </div>
            <!-- 修改用户信息 -->
            <div class="setInfo" v-show="scenario == 2">
                <el-card style="width: 50%;height: 500px;margin: 0 auto;">
                    <template #header>
                        <div class="card-header">
                            <span style="font-size: 24px;">修改用户信息</span>
                            <el-icon style="float: right;" size="24" @click="closeUpdate">
                                <Close />
                            </el-icon>
                        </div>
                    </template>
                    <el-row>
                        <el-col :span="10">
                            <div class="avatar">
                                <el-upload class="avatar-uploader" action="/admin/common/upload" :headers="headers"
                                    :show-file-list="false" :on-success="handleAvatarSuccess"
                                    :before-upload="beforeAvatarUpload">
                                    <img :src="updateForm.avatar" style="width:200px;height:200px;object-fit: cover;" />
                                </el-upload>
                            </div>
                        </el-col>
                        <el-col :span="14">
                            <div class="form">
                                <el-form :model="updateForm" label-width="60px" :rules="rules" ref="ruleFormRef">
                                    <el-form-item label="用户名" class="item" prop="name">
                                        <el-input v-model="updateForm.name"></el-input>
                                    </el-form-item>
                                    <el-form-item label="手机" class="item" prop="phone">
                                        <el-input v-model="updateForm.phone"></el-input>
                                    </el-form-item>
                                    <el-form-item label="邮箱" class="item" prop="email">
                                        <el-input v-model="updateForm.email"></el-input>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button plain size="large" style="margin-left: 100px;"
                                            @click="submit">确&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;定</el-button>
                                    </el-form-item>
                                </el-form>
                            </div>
                        </el-col>
                    </el-row>
                </el-card>
            </div>
            <!-- 显示首页详情信息 -->
            <div class="detailInfo" v-show="scenario == 3">
                <div class="carousel">
                    <el-carousel indicator-position="none" height="650px" :autoplay="false" ref="carousel" arrow="never">
                        <el-carousel-item>
                            <el-row>
                                <el-col :span="2">
                                    <div style="width: 100%;height: 100%;z-index: 1000;">
                                        <el-icon style="margin-top:280px;font-size: 50px;" @click="prevItem">
                                            <ArrowLeftBold />
                                        </el-icon>
                                    </div>
                                </el-col>
                                <el-col :span="7">
                                    <div class="left">
                                        <img :src="recruit.companyAvatar" alt="">
                                        <span>招聘人信息:{{ recruit.companyName }}</span>
                                    </div>
                                    <hr>
                                </el-col>
                                <el-col :span="13">
                                    <div class="right">
                                        <span class="fontColor">招聘岗位: {{ recruit.jobName }}</span>
                                        <span>工作地点: {{ recruit.jobAddress }}</span>
                                        <span>要求学历: {{ recruit.education }}</span>
                                        <span class="fontColor">工作薪资: {{ recruit.minSalary / 1000 + "K" }} -
                                            {{
                                                recruit.maxSalary
                                                / 1000 +
                                                "K" }}</span>
                                        <span>工作经验: {{ recruit.jobExperience }}</span>
                                        <span class="fontColor">所需人数: {{ recruit.needPeople + "人" }}</span>
                                        <span>招聘时间: {{ recruit.createTime.split('T')[0] }}</span>
                                        <span>浏览总数: {{ recruit.viewCount + "次" }}</span>
                                    </div>
                                    <hr>
                                </el-col>
                                <el-col :span="2">
                                    <div style="width: 100%;height: 100%;position: relative;">
                                        <el-icon style="margin-top:280px;font-size: 50px;margin-left: 20px;"
                                            @click="nextItem">
                                            <ArrowRightBold />
                                        </el-icon>
                                        <el-icon style="font-size: 36px;position: absolute;right: 0;"
                                            @click="quitDetailInfo">
                                            <Close />
                                        </el-icon>
                                    </div>
                                </el-col>
                            </el-row>
                            <el-scrollbar height="300px">
                                <!--  -->
                                <div v-html="recruit.content" class="recruitContent" >
                                </div>
                            </el-scrollbar>
                        </el-carousel-item>
                    </el-carousel>
                </div>
            </div>
            <!-- 修改密码 -->
            <div class="setPass" v-show="scenario == 4">
                <el-form label-width="100" :rules="setPassRules" ref="setPassRule" :model="password">
                    <el-form-item label="旧密码">
                        <el-input v-model="password.oldPassword" placeholder="请输入您之前的密码"></el-input>
                    </el-form-item>
                    <el-form-item label="新密码" prop="newPassword">
                        <el-input v-model="password.newPassword" placeholder="请输入您现在的密码" show-password></el-input>
                    </el-form-item>
                    <el-form-item label="确认密码" prop="newsPass">
                        <el-input v-model="password.newsPass" placeholder="请确定您现在的密码" show-password></el-input>
                    </el-form-item>
                    <el-form-item style="margin-left: 42px;">
                        <el-button @click="cancelSetPass">取消</el-button>
                        <el-button @click="setPass" color="#3F81A9">确定</el-button>
                    </el-form-item>
                </el-form>
                
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive, watch } from 'vue'
import Logo from './logo.vue'
import Menu from './menu/index.vue'
import Tabbar from './tabbar/index.vue'
import Main from './main/index.vue'
import { ElMessage, ElScrollbar } from 'element-plus';
import userStore from '@/store/modules/user';
import PersonalCenter from './personalCenter/index.vue'
import { GET_TOKEN } from '@/tools/token'
import { reqUpdateUser, reqSetPass } from '@/api/user'
import { useRouter } from 'vue-router'
import { reqRecruitDetail } from '@/api/company'
// 引入layout仓库
import layoutStore from '@/store/modules/layout'
let useLayoutStore = layoutStore()
// 控制蒙版的显示和隐藏
const isShow = ref<boolean>(false)
// 蒙版里面的场景切换
const scenario = ref<number>(0)
// 获取用户仓库
let useUserStore = userStore()
// 收集的信息
let updateForm = reactive({
    name: useUserStore.name,
    password: useUserStore.password,
    phone: useUserStore.phone,
    email: useUserStore.email,
    avatar: useUserStore.avatar
})
let router = useRouter()
// 存储原来的仓库中的数据 非响应式
const obj = {
    name: useUserStore.name,
    password: useUserStore.password,
    phone: useUserStore.phone,
    email: useUserStore.email,
    avatar: useUserStore.avatar
}
// 获取修改用户表单实例对象
let ruleFormRef = ref()
// 获取修改密码表单实例对象
let setPassRule = ref()
// 获取token
let headers = reactive({
    Token: GET_TOKEN()
})
// 当前显示照片的id
let id = ref<string>()
// 存储所有数据的id
let idArry = ref([])
// 获取轮播图的实例
let carousel = ref()
// 存储招聘详情的数据
let recruit = reactive({
    id: 0,
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
    content: ''
})
// 收集修改密码的参数
let password = reactive({
    oldPassword: '',
    newPassword: '',
    newsPass:''
})
// 轮播图默认下标
let index = ref<number>(0)
// 监听仓库的数据
watch(() => useLayoutStore.masks, async () => {
    if (useLayoutStore.masks == 1) return
    isShow.value = true
    scenario.value = useLayoutStore.masks
    console.log(useLayoutStore.dataArr.length);
    useLayoutStore.dataArr.forEach(item => {
        // @ts-ignore
        idArry.value.push(item.id)
    })
    id.value = useLayoutStore.id
    await reqRecruitDetail(id.value).then(res => {
        Object.assign(recruit, res.data)
    })

})
// 下一页的回调
const nextItem = async () => {
    if (useLayoutStore.dataArr.length == 1) return
    index.value = idArry.value.findIndex(item => {
        return item == id.value
    })
    if (index.value + 1 != idArry.value.length) {
        index.value++
        await reqRecruitDetail(idArry.value[index.value]).then(res => {
            Object.assign(recruit, res.data)
            id.value = idArry.value[index.value]
        })
        carousel.value.next()
    } else {
        index.value = 0
        await reqRecruitDetail(idArry.value[index.value]).then(res => {
            Object.assign(recruit, res.data)
            id.value = idArry.value[index.value]
        })
        carousel.value.next()
    }

}
// 上一页的回调
const prevItem = async () => {
    if (useLayoutStore.dataArr.length == 1) return
    index.value = idArry.value.findIndex(item => {
        return item == id.value
    })
    if (index.value == 0) {
        index.value = idArry.value.length - 1
        await reqRecruitDetail(idArry.value[index.value]).then(res => {

            Object.assign(recruit, res.data)
            id.value = idArry.value[index.value]
        })
        carousel.value.prev()
    } else {
        index.value--

        await reqRecruitDetail(idArry.value[index.value]).then(res => {
            Object.assign(recruit, res.data)
            id.value = idArry.value[index.value]
        })
        carousel.value.prev()
    }

}
// 接受子组件传递过来的数据
const updateIsShow = (show: boolean, scen: number) => {
    isShow.value = show
    scenario.value = scen
}
// 图片上传之前的回调
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
    updateForm.avatar = response.data
}
// 修改用户信息的回调
const submit = async () => {
    // 验证校验是否通过
    await ruleFormRef.value.validate()
    // 校验是否是上一次的数据
    if (obj === updateForm) {
        isShow.value = false
        return
    }
    // 发送请求
    await reqUpdateUser(updateForm).then(async () => {
        ElMessage({
            type: 'success',
            message: '修改成功'
        })

    })

}
// 取消修改密码的回调
const cancelSetPass = () => {
    isShow.value = false
    // 清除上一次的表单校验效果
    setPassRule.value.clearValidate()
    password.oldPassword = ''
    password.newPassword = ''
    password.newsPass = ''
}
// 修改密码的回调
const setPass = async () => {
    // 验证校验是否通过
    await ruleFormRef.value.validate()
    // 判断是否填写每一个选项
    if (password.newPassword != '' && password.newsPass != '' && password.oldPassword != '') {
        // 判断两次输入的密码是否一样
        if (password.newPassword === password.newsPass) {
            const {oldPassword,newPassword} = password
            const data = {oldPassword,newPassword}
            let result = await reqSetPass(data)
            if (result.code == 200) {
                ElMessage({
                    type: 'success',
                    message: '修改成功'
                })
                isShow.value = false
                // 重新登录
                alert('当前登录已失效 请重新登录')
                // 发送退出登录请求
                await useUserStore.userLogout().then(() => {
                    router.push('/login')
                })
            } else {
                ElMessage({
                    type: 'error',
                    message: '修改失败' + result.msg
                })
            }
        } else {
            ElMessage({
                type: 'info',
                message: '您两次输入的密码不一样请重新输入'
            })
        }
    } else {
        ElMessage({
            type: 'info',
            message: '请您输入以下的选项'
        })
        return
    }
}
// 自定义表单校验规则
// @ts-ignore
const validateName = (rule: any, value: any, callback: any) => {
    if (value.length >= 3 && value.length < 12) {
        callback()
    } else {
        callback(new Error('用户名必须在3-12位之间'))
    }
}
// @ts-ignore
const validatePhone = (rule: any, value: any, callback: any) => {
    let deg = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
    if (deg.test(value)) {
        callback()
    } else {
        callback(new Error('请输入正确的手机号码'))
    }
}
// @ts-ignore
const validatePassword = (rule: any, value: any, callback: any) => {
    if (/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/.test(value)) {
        callback()
    } else {
        callback(new Error("密码至少包含一个字母或者一个数字,并且大于六位"))
    }
}
// @ts-ignore
const validateEmail = (rule: any, value: any, callback: any) => {
    let deg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
    if (deg.test(value)) {
        callback()
    } else {
        callback(new Error('请输入合格的邮箱'))
    }
}
// 修改用户信息表单校验对象
const rules = reactive({
    name: [{ validator: validateName, trigger: 'blur' }],
    phone: [{ validator: validatePhone, trigger: 'blur' }],
    email: [{ validator: validateEmail, trigger: 'blur' }],
})
// 修改密码的表单校验对象
const setPassRules = reactive({
    newsPass: [{ validator: validatePassword, trigger: 'blur' }],
    newPassword: [{ validator: validatePassword, trigger: 'blur' }]
})
// 退出修改页面的回调
const closeUpdate = () => {
    isShow.value = false
    // 清除上一次输入的数据
    Object.assign(updateForm, obj)
    // 清除上一次的表单校验效果
    ruleFormRef.value.clearValidate()

}
// 退出首页详情页面的回调
const quitDetailInfo = () => {
    // 关闭蒙版以及还原仓库数据
    isShow.value = false
    useLayoutStore.dataArr = []
    useLayoutStore.masks = 1
    idArry.value = []
}
</script>

<style scoped lang="scss">
$base-menu-mini-width: 65px;

.layout {
    position: relative;
    width: 100vw;
    height: 100vh;
    display: flex;

    .masks {
        position: absolute;
        width: 100%;
        height: 100%;
        z-index: 99;
        background-color: rgba(0, 0, 0, 0.7);
        // background-color: black;
        // opacity: 0.8;

        .getInfo {
            box-sizing: border-box;
            padding-top: 200px;

            .avatar {
                width: 200px;
                height: 200px;
                margin: 100px auto;
                border-radius: 50%;
                overflow: hidden;
                box-shadow: 0px 0px 10px black;
            }

            .text {
                width: 100%;
                height: 100%;
                display: flex;
                flex-direction: column;

                .textItem {
                    box-sizing: border-box;
                    height: 20%;
                    width: 100%;
                    margin-top: 10px;
                    padding: 20px 10px;
                    font-size: 20px;

                    span {
                        margin-left: 5px;
                        color: black;
                        font-weight: 600;
                    }
                }
            }
        }

        .setInfo {
            box-sizing: border-box;
            padding-top: 200px;

            .avatar {
                width: 200px;
                height: 200px;
                margin: 100px auto;
                border-radius: 50%;
                overflow: hidden;
                box-shadow: 0px 0px 10px black;
            }

            .text {
                width: 100%;
                height: 100%;
                display: flex;
                flex-direction: column;
            }

            .form {
                width: 100%;
                height: 100%;
                box-sizing: border-box;
                padding-top: 90px;

                .el-input {
                    width: 70%;
                    height: 40px;
                }
            }
        }

        .detailInfo {
            box-sizing: border-box;
            padding-top: 120px;

            .carousel {
                position: relative;
                width: 50%;
                margin: 0 auto;
                border: 4px solid black;
                background-color: rgba(255, 255, 255, 1);
                box-shadow: 0px 0px 10px black;
                user-select: none;

                .recruitContent {
                    width: 100%;
                    padding: 20px 40px;
                    background-color: #F8FBFB;
                }

                .left {
                    height: 245px;
                    width: 90%;
                    display: flex;
                    flex-direction: column;
                    margin-top: 70px;

                    img {
                        width: 70%;
                        height: 70%;
                        object-fit: cover;
                        border-radius: 70%;
                        border: 1px solid black;
                        margin-left: 20px;
                        margin-top: 20px;
                    }

                    span {
                        font-size: 24px;
                        margin: 10px 24px;
                        font-weight: 700;
                    }
                }

                .right {
                    height: 245px;
                    width: 100%;
                    display: flex;
                    flex-direction: column;
                    margin-left: 10px;
                    margin-top: 70px;
                    flex-wrap: wrap;

                    .fontColor {
                        color: #FE574A;
                    }

                    span {
                        font-size: 20px;
                        margin-top: 10px;
                        margin-left: 20px;
                    }
                }
            }
        }

        .setPass {
            width: 30%;
            height: 330px;
            background-color: white;
            margin: 300px auto;
            padding: 50px;
            box-sizing: border-box;
            border-radius: 10px;
            box-shadow: 0px 0px 10px black;

            .el-input {
                height: 40px;
                width: 70%;
                margin-top: 10px;
            }

            .el-form ::v-deep .el-form-item__label {
                line-height: 58px;
            }
        }
    }
}

.layout_menu {
    position: relative;
    width: 15%;
    height: 100%;
    background-color: #3F81A9;
    display: flex;
    flex-direction: column;
    box-shadow: 0px 0px 20px black;
    transition: 0.5s all cubic-bezier(1, .25, .78, 1.24);

    &.fold {
        width: $base-menu-mini-width ;
    }

    .personalCenter {
        position: absolute;
        bottom: 10px;
        width: 100%;
        height: 6%;
        background-color: #3F81A9;
        box-shadow: 0px 0px 10px black;

    }
}

.scrollbar {
    width: 100%;
    height: 85%;
    .el-menu {
        border-right: none;

    }
}

.title {
    font-size: 2.24rem;
    color: white;
    letter-spacing: 5px;
    margin: 0 auto;
    font-family: Georgia, serif;
    margin: 20px auto;
}

.layout_right {
    width: 85%;
    height: 100%;
    transition: 0.5s all cubic-bezier(1, .25, .78, 1.24);

    &.fold {
        width: calc(100vw - $base-menu-mini-width);
        left: $base-menu-mini-width;
    }
}

.layout_tabbar {
    width: 100%;
    height: 6%;
    background-color: #3F81A9;
    box-sizing: border-box;
    border-left: 0.1px solid white;
    box-shadow: 0px 0px 20px black;

}

.layout_main {
    width: 100%;
    height: 94%;
    box-sizing: border-box;
    padding: 10px;
}

.el-card ::v-deep .el-card__body {
    padding: 0px;
    background-color: #3F81A9;
    height: 100%;
}

.el-card ::v-deep .el-card__header {
    background-color: #3F81A9;
    border-bottom: none;
}

.el-card {
    border: none;
    box-shadow: 0px 0px 10px white;
}

.item ::v-deep .el-form-item__label {
    color: black;
    font-size: 16px;
    font-weight: 600;
}
</style>