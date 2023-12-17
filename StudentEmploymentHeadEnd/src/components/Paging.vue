<template>
    <div>
        <el-pagination class="pagination" v-model:current-page="props.currentPage" v-model:page-size="props.pageSize"
                    :page-sizes="props.pageSizes" layout="sizes,->,prev, pager, next,->,total" :total="props.total"
                    @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    </div>
</template>

<script setup lang="ts">
const props =  defineProps(['currentPage','pageSize','total','pageSizes'])
const $emit = defineEmits(['sizeChange','currentChange'])
// 
const handleSizeChange = (value:any) => {
    $emit('sizeChange',value)
}
// 
const handleCurrentChange = (value:any) =>{  
    $emit('currentChange',{value,limit:props.pageSize})
}
// 
</script>

<style scoped>
.pagination{
    margin-top: 10px;
}
</style>