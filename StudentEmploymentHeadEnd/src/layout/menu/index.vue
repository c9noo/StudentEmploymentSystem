<template>
    <template v-for="item in menuList" :key="item.path">
        <!-- 一级路由 -->
        <template v-if="!item.children">
            <el-menu-item :index="item.path" v-if="!item.meta.hidden" @click="goRoute" class="zoom">
                <ElIcon size="20">
                    <component :is="item.meta.icon"></component>
                </ElIcon>
                <template #title>
                    <span>{{ item.meta.title }}</span>
                </template>
            </el-menu-item>
        </template>
        <!-- 有二级路由只有一个 -->
        <template v-if="item.children && item.children.length == 1">
            <el-menu-item :index="item.children[0].path" v-if="!item.children[0].meta.hidden" @click="goRoute" class="zoom">
                <ElIcon size="20">
                    <component :is="item.children[0].meta.icon"></component>
                </ElIcon>
                <template #title>
                    <span>{{ item.children[0].meta.title }}</span>
                </template>
            </el-menu-item>
        </template>

        <!-- 二级路由 -->
        <el-sub-menu :index="item.path" v-if="item.children && item.children.length > 1" style="--el-menu-bg-color:#ccc;">
          <template #title>
            <ElIcon>
                <component :is="item.meta.icon"></component>
            </ElIcon>
            <span>{{ item.meta.title }}</span>
          </template>
          <Menu :menuList="item.children"></Menu>
        </el-sub-menu>

    </template>
</template>

<script setup lang="ts">
import { ElIcon } from 'element-plus';
import { useRouter } from 'vue-router'
let router = useRouter()
// 路由跳转的回调
const  goRoute =(vc:any) =>{
    router.push(vc.index)
}
defineProps(["menuList"])
defineOptions({
    name:'Menu'
})
</script>

<style scoped>
.el-menu-item {
    color: white;
    /* background-color: transparent; */
    background-color: #3F81A9;
    margin: 5px;
    transition: 0.6s all linear;
}

.el-menu-item:hover {
    border-radius: 10px;
    box-shadow: 0px 0px 10px white;
}
.el-sub-menu{
    padding: 5px;
}
.el-sub-menu>>>.el-sub-menu__title {
    color: white;
    /* background-color: transparent; */
    background-color: #3F81A9 !important;
    transition: 0.6s all linear;
}

.el-sub-menu>>>.el-sub-menu__title:hover {
    border-radius: 10px;
    box-shadow: 0px 0px 10px white;
}

.el-sub-menu>>>.el-menu-item {
    /* background-color: transparent; */
    background-color: #3F81A9;

}
.el-icon{
    right: 5px;
}
span {
    font-size: 16px;
}
.el-sub-menu >>>.el-menu--inline{
    background-color: #3F81A9;
}


</style>