<template>
    <div class="container">
        <div class="screen" ref="screen">
            <div class="top">
                <Header/>
            </div>
            <div class="main">
                <TotalEmploymentRate/>
            </div>
            <div class="left">
                <div class="pc">
                    <PopularCities/>
                </div>
                <div class="pm">
                    <PopularMajors/>
                </div>
                <div class="pd">
                    <PopularDegrees/>
                </div>
            </div>
            <div class="bottom">
                <CompanyRatio/>
            </div>
            <div class="right">
                <SalaryRatios/>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
// 引入子元素
import Header from './components/header/index.vue'
import PopularCities from './components/popularCities/index.vue'
import PopularDegrees from './components/popularDegrees/index.vue'
import PopularMajors from './components/popularMajors/index.vue'
import CompanyRatio from './components/companyRatio/index.vue'
import SalaryRatios from './components/SalaryRatios/index.vue'
import TotalEmploymentRate from './components/totalEmploymentRate/index.vue'
// 获取数据大屏展示内容盒子的DOM元素
let screen = ref()
onMounted(() => {
    screen.value.style.transform = `scale(${getScale()}) translate(-50%,-50%)`
})
// 定义大屏适配缩放的比例
function getScale(w = 1920, h = 1080) {
    const ww = window.innerWidth / w
    const wh = window.innerHeight / h
    return ww < wh ? ww : wh

}
// 监听视口的变化
window.onresize = () => {
    screen.value.style.transform = `scale(${getScale()}) translate(-50%,-50%)`
}

</script>

<style scoped lang="scss">
.container {
    width: 100vw;
    height: 100vh;
    background: url('./images/screen.jpg') no-repeat;
    background-size: 100% 100%;

    .screen {
        position: fixed;
        width: 1920px;
        height: 1080px;
        top: 50%;
        left: 50%;
        transform-origin: left top;

        .top{
            width: 100%;
            height: 7%;
        }
        .main{
            position: absolute;
            width: 60%;
            height: 65%;
            // background-color: yellow;
            left: 20%;
            margin-left: 10px;
        }
        .left{
            position: absolute;
            width: 20%;
            height: 93%;
            display: flex;
            flex-direction: column;

            .pc{
                flex: 1;
            }
            .pm{
                flex: 1;
            }
            .pd{
                flex: 1;
            }
        }
        .bottom{
            position: absolute;
            width: 80%;
            height: 28%;
            // background-color: orange;
            left: 20%;
            bottom: 0;
            margin-left: 10px;
        }
        .right{
            position: absolute;
            width: 20%;
            height: 65%;
            // background-color: purple;
            right: 0;
        }
    }
}
</style>