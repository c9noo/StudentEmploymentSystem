<template>
    <div class="outer">
        <!-- 左侧 -->
        <div class="left">
            <!-- icon -->
            <el-icon @click="switchs">
                <component :is="useLayoutStore.fold?'Expand':'Fold'"></component>
            </el-icon>
            <!-- 面包屑 -->
            <div class="bread">
                <el-breadcrumb :separator-icon="ArrowRight"
                    style="--el-text-color-regular:white;--el-text-color-primary:white;">
                    <el-breadcrumb-item v-for="(item,index) in router.matched" :key="index" v-show="item.meta.title" :to="item.path" >
                        <span>{{ item.meta.title }}</span>
                    </el-breadcrumb-item>
                </el-breadcrumb>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ElIcon } from 'element-plus';
import { ArrowRight } from '@element-plus/icons-vue'
import { useRoute } from 'vue-router'
// 获取相关路由
let router = useRoute()
// 引入layout仓库
import layoutStore from '@/store/modules/layout'
let useLayoutStore = layoutStore()
// 切换场景的回调
const switchs = () => {
    useLayoutStore.fold = !useLayoutStore.fold
}
</script>

<style scoped>
.outer {
    position: relative;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: space-between;
    color: white;
}

.left {
    margin: 9px;
    display: flex;
}

.el-icon {
    font-size: 36px;
    transition: 0.5s all linear;
}

.el-icon:hover {
    font-size: 42px;

}

.bread {
    position: absolute;
    left: 42px;
    margin: 12px;
}
</style>