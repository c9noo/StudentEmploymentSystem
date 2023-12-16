<template>
    <div class="outer">
        <!-- 头像 -->
        <div :class="{ img : fold ? false: true }" v-show="!fold">
            <img :src="useUserStore.avatar" class="avatar" />
        </div>
        <div :class="{ fold_img : fold ? true: false }" v-show="fold">
            <img :src="useUserStore.avatar" class="avatar" />
        </div>
        <!-- 文字 -->
        <transition name="fade">
            <div class="text" v-show="!fold">
            <span>admin</span>
            <el-icon size="24" @click="showClick">
                <MoreFilled />
            </el-icon>
        </div>
        </transition>
        <!-- 下拉菜单 -->
        <div class="dropdown">
            <el-dropdown ref="dropdown" trigger="contextmenu" style="margin-right: 30px;">
                <span class="el-dropdown-link"> </span>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item @click="getInfo"
                            style="--el-dropdown-menuItem-hover-fill:white;--el-dropdown-menuItem-hover-color:black;"><el-icon>
                                <UserFilled />
                            </el-icon>用户信息</el-dropdown-item>
                        <el-dropdown-item @click="updateInfo"
                            style="--el-dropdown-menuItem-hover-fill:white;--el-dropdown-menuItem-hover-color:black;"><el-icon>
                                <Tools />
                            </el-icon>修改信息</el-dropdown-item>
                        <el-dropdown-item @click="setPassword"
                            style="--el-dropdown-menuItem-hover-fill:white;--el-dropdown-menuItem-hover-color:black;"><el-icon>
                                <Lock />
                            </el-icon>修改密码</el-dropdown-item>
                        <el-dropdown-item @click="logout"
                            style="--el-dropdown-menuItem-hover-fill:white;--el-dropdown-menuItem-hover-color:black;"><el-icon>
                                <BottomLeft />
                            </el-icon>退出登陆</el-dropdown-item>

                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
        <!--  -->
    </div>
</template>

<script setup lang="ts">
import "@/assets/styles/el-dropdown.css"
import { ref } from 'vue'
import userStore from '@/store/modules/user'
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";
let rouer = useRouter()
let useUserStore = userStore()
// 控制下拉菜单显示隐藏
let dropdown = ref()
defineProps(['fold'])
// icon点击事件的回调
const showClick = () => {
    if (!dropdown.value) return
    dropdown.value.handleOpen()
}
// 定义发送给父组件的自定义事件
const emit = defineEmits(['showMasks'])
// 用户信息的回调函数
const getInfo = () => {
    emit('showMasks', true, 1)
}
// 修改用户信息的回调函数
const updateInfo = () => {
    emit('showMasks', true, 2)
}
// 修改密码
const setPassword = () => {
    emit('showMasks', true, 4)
}
// 用户信息的回调函数
const logout = async () => {
    await useUserStore.userLogout().then(() => {
        ElMessage({
            type: 'success',
            message: '退出成功'
        })
        rouer.push('/login')
    }).catch(() => {
        ElMessage({
            type: 'error',
            message: '退出失败请检查当前网络'
        })
    })
}
</script>

<style scoped lang="scss">
.outer {
    position: relative;
    width: 100%;
    height: 100%;
    display: flex;

    .img {
        width: 20%;
        height: 100%;
        border-radius: 20px;
        padding: 3px;
        box-sizing: border-box;
        margin-left: 30px;
        overflow: hidden;

        .avatar {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
    }

    .fold_img {
        width: 60px;
        margin-left: 5px;
        border-radius: 20px;
        padding: 3px;
        box-sizing: border-box;
        overflow: hidden;
        height: 100%;

        .avatar {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
    }

    .text {
        width: 60%;
        display: flex;
        justify-content: space-between;
        font-size: 20px;
        margin-top: 17px;
        margin-left: 20px;
    }

    .dropdown {
        position: absolute;
        left: 50%;

    }
}

.el-dropdown-menu {
    background-color: white;
    width: 280px;
}

::v-deep .el-dropdown-menu__item {
    color: black;
    font-size: 16px;
}
.fade-enter-from {
    opacity: 0;
}

.fade-enter-active {
    transition: all 1s 0.5s;
}

.fade-enter-to {
    opacity: 1;
}
.outer{
    width: 100%;
    height: 100%;
}
</style>