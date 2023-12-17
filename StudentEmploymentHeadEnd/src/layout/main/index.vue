<template>
    <div class="outer">
        <router-view v-slot="{ Component }">
            <Transition name="fade">
                <component :is="Component"></component>
            </Transition>
        </router-view>
    </div>
</template>

<script setup lang="ts">
</script>

<style scoped>
.fade-enter-from {
    opacity: 0;
}

.fade-enter-active {
    transition: all 1s;
}

.fade-enter-to {
    opacity: 1;
}
.outer{
    width: 100%;
    height: 100%;
}
</style>