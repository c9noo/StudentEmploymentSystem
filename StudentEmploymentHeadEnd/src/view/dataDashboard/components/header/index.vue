<template>
    <div class="outer">
        <!-- 左侧 -->
        <div class="left" ref="left">
            <div class="btn" @click="goHome">
                <span >首页</span>
            </div>
        </div>
        <!-- 中间 -->
        <div class="logo" ref="logo">
            <span>学生就业数据大屏</span>
        </div>
        <!-- 右侧 -->
        <div class="right" ref="right">
            <div class="img" @click="logout">
                <span>退出</span>
            </div>
            <div class="time">
                <span>时间:{{ time }}</span>
            </div>
        </div>
    </div>
</template>

<script  lang="ts" setup>
import { ref, onMounted, onBeforeUnmount, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import moment from 'moment'
// 获取路由器对象
let router = useRouter()
// 存储当前时间
let time = ref(moment().format('YYYY年MM月DD日:hh:mm:ss'))
let timer = ref(0)
// 获取logo组件实例
let logo = ref()
let left = ref()
let right = ref()
// 按钮点击的回调
const goHome = () => {
    router.push('/home')
}
// 退出按钮的回调
const logout = () => {
    
}

// 组件挂载完毕更新当前的时间
onMounted(() => {
    (timer.value as any) = setInterval(() => {
        time.value = moment().format('YYYY年MM月DD日:hh:mm:ss')
    }, 1000)

    // console.log(logo.value.style.marginTop);ref获取dom只能获取结构 原来的样式是获取不到的
    // 只读数据(获取原来的属性数据) 不能改写
    let logoStyle = window.getComputedStyle(logo.value)
    let leftStyle = window.getComputedStyle(left.value)
    let rightStyle = window.getComputedStyle(right.value)
    logoStyle.marginTop ; leftStyle.left ; rightStyle.right
 
    nextTick(() => {
        logo.value.style.marginTop = "3px"
        right.value.style.right = "20px"
        left.value.style.left = "20px"
    })

})
// 组件销毁之前清空定时器
onBeforeUnmount(() => {
    clearInterval(timer.value)
})
</script>

<style scoped lang="scss">
.outer {
    position: relative;
    width: 100%;
    height: 100%;

    .logo {
        position: absolute;
        width: 30%;
        height: 90%;
        background: url('../../images/line.png') no-repeat;
        background-size: 100% 100%;
        margin-left: 35%;
        display: flex;
        margin-top: -70px;
        transition: all 1s 0.5s linear;

        span {
            color: white;
            font-size: 42px;
            font-family: Georgia, serif;
            margin: 15px auto;
            letter-spacing: 10px;
        }
    }

    .left {
        position: absolute;
        width: 30%;
        height: 80%;
        margin-top: 10px;
        left: -710px;
        transition: all 1s 0.5s linear;

        .btn {
            background: url('../../images/bg01bigindex.png') no-repeat;
            width: 25%;
            height: 100%;
            margin-left: 75%;
            display: flex;

            span {
                color: white;
                font-size: 24px;
                font-family: Georgia, serif;
                margin: 20px auto;
                letter-spacing: 10px;
            }
        }
    }

    .right {
        position: absolute;
        width: 30%;
        height: 80%;
        margin-top: 10px;
        right: -710px;
        display: flex;
        transition: all 1s 0.5s linear;

        .img {
            background: url('../../images/bg01bigindex.png') no-repeat;
            width: 25%;
            height: 100%;
            display: flex;

            span {
                color: white;
                font-size: 24px;
                font-family: Georgia, serif;
                margin: 20px auto;
                letter-spacing: 10px;
            }
        }

        .time {
            width: 75%;
            height: 100%;
            display: flex;
            background: url('../../images/title-bg.png') no-repeat;
            background-size: 100% 70%;
            margin-left: 10px;
            margin-top: 10px;

            span {
                color: white;
                font-size: 24px;
                font-family: 新宋体;
                margin-left: 20px;
                margin-top: 10px;
                letter-spacing: 2px;
                text-shadow: 0px 0px 10px white;
            }
        }
    }
}
</style>