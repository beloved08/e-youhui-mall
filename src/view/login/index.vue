<template>
    <t-layout class="h-screen bg">
        <t-content>
            <LoginHeader />
            <LoginForm />
        </t-content>
        <t-footer>
            <LoginFooter />
        </t-footer>
    </t-layout>
</template>
<script setup lang="ts">
import LoginHeader from "@/view/login/header/index.vue"
import LoginForm from "@/view/login/form/index.vue"
import LoginFooter from "@/view/login/footer/index.vue"

</script>

<style scoped>
.bg {
    background-image: url("https://vue.frsimple.cn/assets/loginbg.1669814980573.png");
    background-position: center;
    background-attachment: fixed;
}
</style>
