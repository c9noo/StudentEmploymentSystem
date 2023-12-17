<template>
    <div class="outer" ref="outer">
        <div class="charts" ref="charts">

        </div>
    </div>
</template>

<script setup lang="ts">
import * as echarts from 'echarts';
import {ref,onMounted,nextTick} from 'vue'
// 获取dom元素
let charts = ref()
let outer = ref()
onMounted(()=>{
    let mycharts = echarts.init(charts.value)
    mycharts.setOption({
        title:{
            text:'热门学历',
            textStyle:{
                color:'#fff',
                fontSize:24
            }
        }
    })
    // 获取dom身上原来的属性
    let outerStyle = window.getComputedStyle(outer.value)
    outerStyle.transition
    nextTick(()=>{
        outer.value.style.transform = "scale(1)"
    })
})
</script>

<style scoped>
.outer{
    background: url('../../images/line.png') no-repeat;
    background-size: 100% 100%;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    transform: scale(0.2);
    transition: all 1s 0.5s linear;
    .charts{
        width: 100%;
        height: 100%;
    }
}
</style>