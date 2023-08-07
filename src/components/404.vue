<template>
    <div class="w-full h-screen not-found" @click="backHome"></div>
</template>

<script setup lang="ts">
import router from '@/router/index'
const backHome = () => {
    router.push({ path: "/dashboard" })
}
</script>

<style scoped>
.not-found {
    background-image: url("https://eyouhui.oss-cn-hangzhou.aliyuncs.com/manage/err_404.png");
    background-position: center;
    background-repeat: no-repeat;
}
</style>