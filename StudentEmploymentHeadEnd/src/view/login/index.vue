<template>
    <div class="outer">
        <div class="inner">
            <div class="login">
                <span>学生就业系统</span>
                <div class="form">
                    <el-form label-width="100px" :rules="rules" :model="loginForm" ref="ruleFormRef">
                        <div v-show="screen == 1">
                            <el-form-item prop="user" inline-message>
                                <div class="label">用户名</div>
                                <el-input placeholder="请输入你的用户名" v-model="loginForm.username"
                                    style="width: 60%;height: 36px; margin: 15px 0 ;"></el-input>
                            </el-form-item>
                            <el-form-item prop="password">
                                <div class="label">密&nbsp;&nbsp;&nbsp;码</div>
                                <el-input show-password placeholder="请输入你的密码" v-model="loginForm.password" type="password"
                                    style="width: 60%;height: 36px; margin: 15px 0 ;"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <div class="label">验证码</div>
                                <!-- 验证码输入 -->
                                <el-form-item>
                                    <el-input placeholder="请输入验证码" v-model="loginForm.captcha" clearable
                                        style="width: 60%;height: 36px; margin: 15px 0 ;"
                                        @keyup.enter.native="login"></el-input>
                                </el-form-item>
                                <!-- 验证码展示 -->
                                <div @click="refreshCode" style="margin-top: 10px;">
                                    <img :src="src">
                                </div>
                            </el-form-item>
                            <div style="color: blue;margin-left: 63%;cursor: pointer;" @click="screen = 2">忘记密码？</div>
                            <el-form-item>
                                <el-button class="btn" @click="login">登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</el-button>
                            </el-form-item>
                        </div>

                        <div v-show="screen == 2">
                            <el-form-item >
                                <div class="label">邮箱</div>
                                <el-input v-model="email" placeholder="请输入您的邮箱"
                                    style="width: 60%;height: 36px; margin: 15px 0 ;"></el-input>
                            </el-form-item>
                            <div style="color: blue;margin-left: 63%;cursor: pointer;" @click="screen = 1">返回登录</div>
                            <el-form-item>
                                <el-button class="btn" @click="getEmail">确&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;定</el-button>
                            </el-form-item>
                        </div>

                        <div v-show="screen == 3">
                            <el-form-item >
                                <div class="label">邮&nbsp;&nbsp;&nbsp;箱</div>
                                <el-input v-model="resetPassForm.email" placeholder="请输入您的邮箱"
                                    style="width: 60%;height: 36px; margin: 15px 0 ;"></el-input>
                            </el-form-item>
                            <el-form-item >
                                <div class="label">密&nbsp;&nbsp;&nbsp;码</div>
                                <el-input show-password v-model="resetPassForm.password" placeholder="请输入您新的密码"
                                    style="width: 60%;height: 36px; margin: 15px 0 ;"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <div class="label">验证码</div>
                                <el-form-item>
                                    <el-input placeholder="请输入验证码" v-model="resetPassForm.text" clearable
                                        style="width: 60%;height: 36px; margin: 15px 0 ;"></el-input>
                                </el-form-item>
                                <div @click="getEmail">
                                    <el-button type="primary" size="large" style="padding: 10px;"
                                        :disabled="time <= 0 ? false : true">{{ time > 0 ? time + 's后重新获取' : '请再次获取验证码' }}</el-button>
                                </div>
                            </el-form-item>
                            <div style="color: blue;margin-left: 63%;cursor: pointer;" @click="screen = 1">返回登录</div>
                            <el-form-item>
                                <el-button class="btn" @click="resetPass">重&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;置</el-button>
                            </el-form-item>
                        </div>
                    </el-form>
                </div>

            </div>
            <div class="title" @click="showChina">
                <div class="English">
                    {{ saying }}
                </div>
                <div class="china" v-show="isShow">
                    {{ sayingChina }}
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router';
import { ElMessage } from 'element-plus'
import { reqGetCaptcha, reqGetEmail, reqResetPass } from '@/api/user'
import userStore from '@/store/modules/user'
let useUserStore = userStore()
//获取路由器
let router = useRouter()
let route = useRoute()
// 定义字符串
const saying = ref<string>("The upstream is the end point of the warrior's wind and waves, and the downstream is the coward's smooth sailing")
const sayingChina = ref<string>("上游,是勇士劈风破浪的终点;下游,是懦夫一帆风顺的归宿")
// 定义一个变量控制china的显示隐藏
const isShow = ref<boolean>(false)
// 存储验证码的路径
const src = ref<string>('')
// 定义form表单的数据
const loginForm = reactive({
    username: 'admin',
    password: 'a123456',
    captcha: '',
    uuid: ''
})
// 定义找回密码的表单数据
const resetPassForm = reactive({
    email: '',
    password: '',
    text: ''
})
// 验证码的定时器秒表
const time = ref<number>(180)
// 切换场景
let screen = ref<number>(1)
// 获取邮箱
const email = ref<string>('')
// 获取form表单的实例
const ruleFormRef = ref()
// 点击事件的回调
const showChina = () => {
    isShow.value = !isShow.value
}
//组件挂载
onMounted(() => {
    getChaptcha()
})
//登录
const login = async () => {
    // 等待表单校验结果
    await ruleFormRef.value.validate()
    // 发送请求
    useUserStore.userLogin(loginForm).then(() => {
        //编程式导航进行路由跳转首页
        //判断路由跳转的时候路径当中是否有query参数
        let redirect: any = route.query.redirect;
        router.push({ path: redirect || '/' })
        ElMessage({ type: 'success', message: '登录成功' })
    }).catch(err => {
        ElMessage({ type: 'error', message: err.code })
    })
}
// 验证用户名
// @ts-ignore
const validateUser = (rule: any, value: any, callback: any) => {
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

    if (/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/.test(value)) {
        callback()
    } else {
        callback(new Error("密码至少包含一个字母或者一个数字,并且大于六位"))
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
// 创建表单校验对象
const rules = ({
    username: [{ required: true, validator: validateUser, trigger: 'blur' }],
    password: [{ validator: validatePassword, trigger: 'blur' }],
})
// 清除上一次表单校验的结果
watch(() => screen.value, () => {
    email.value = ''
    ruleFormRef.value.clearValidate()
})
// 开启验证码的定时器
watch(() => screen.value == 3, () => {
    let timer = setInterval(() => {
        time.value -= 1
    }, 1000)
    if (time.value == 0) {
        clearInterval(timer)
    }

})
// 获取验证码
const getChaptcha = async () => {
    let result = await reqGetCaptcha()
    if (result.code == 200) {
        src.value = result.data.base64Image
        loginForm.uuid = result.data.uuid
    } else {
        ElMessage({
            type: 'error',
            message: '网络出错,请重试！'
        })
    }
}
// 点击再次刷新验证码
const refreshCode = () => {
    getChaptcha()
}
// 找回密码-获取邮箱
const getEmail = async () => {
    let result = await reqGetEmail(email.value)
    if (result.code == 200) {
        ElMessage({
            type: 'success',
            message: '已发送密码到你的邮箱,请查收'
        })
        if (screen.value == 3) {
            time.value = 180
            return
        }
        resetPassForm.email = result.data
        screen.value = 3
    } else {
        ElMessage({
            type: 'error',
            message: '发送失败' + result.msg
        })
    }
}
// 找回密码-重置密码
const resetPass = async () => {
    await reqResetPass(resetPassForm).then(() => {
        ElMessage({
            type: 'success',
            message: '重置成功,请重新登录'
        })
        screen.value = 1
    })
}
</script>

<style scoped>
* {
    margin: 0;
    padding: 0;
}

.outer {
    position: relative;
    width: 100%;
    height: 100vh;
    background: url("../../assets/images/bgs.jpg");
    background-size: 100% 100%;
}

.inner {
    position: absolute;
    width: 60%;
    height: 60%;
    left: 20%;
    display: flex;
    animation: xxx 1s 0.5s 1 cubic-bezier(1, .25, .78, 1.24) forwards;
    box-shadow: 0px 0px 5px black;
}

.login {
    width: 70%;
    height: 100%;
    background-color: #3C7EA6;
    /*DBDBDB*/
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.login span {
    color: white;
    font-size: 40px;
    margin-top: 40px;
    letter-spacing: 10px;
}

.title {
    width: 30%;
    height: 100%;
    background-color: #DBDBDB;
    /*3C7EA6*/
    box-sizing: border-box;
    padding: 33px;
    display: flex;
    flex-direction: column;
}

.English {
    width: 100%;
    height: 70%;
    font-size: 24px;
    line-height: 60px;
    column-count: 1;
    cursor: pointer;
    user-select: none;
}

.china {
    width: 100%;
    height: 30%;
    font-size: 16px;
    letter-spacing: 5px;
    line-height: 30px;
    margin-top: 20px;
    box-sizing: border-box;
    padding-top: 40px;
    padding-left: 7px;
}

@keyframes xxx {
    0% {}

    100% {
        transform: translateY(30%);
        opacity: 0.8;
        box-shadow: 0px 0px 20px black;
    }
}

.form {
    width: 100%;
    margin-top: 20px;
    margin-left: 20px;
    height: calc(100% - 100px);
    box-sizing: border-box;
    padding-top: 70px;
    padding-left: 60px;
}

.label {
    font-size: 17px;
    margin-right: 20px;
}

.btn {
    width: 130px;
    height: 50px;
    font-size: 20px;
    margin-top: 50px;
    margin-left: 28%;
}

.btn:hover {
    box-shadow: 0px 0px 7px black;

}

.el-form-item>>>.el-form-item__error {
    color: red;
    top: 80%;
    left: 72px;
}

.el-input>>>.el-input__wrapper {
    border-radius: 18px;
}

.el-input:hover {
    border-radius: 18px;
    box-shadow: 0px 0px 7px black;
}
</style>