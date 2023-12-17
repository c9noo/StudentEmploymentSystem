<template>
    <div style="display: flex;">
        <span class="title" v-show="!fold">{{ setting.title }}</span>
    </div>
</template>

<script setup lang="ts">
import setting from '../../public/setting'
defineProps(['fold'])
</script>

<style scoped>
.title {
    font-size: 2.24rem;
    color: white;
    letter-spacing: 5px;
    font-family: Georgia, serif;
    margin: 20px auto;
}
</style>