<template>
    <t-layout class="h-screen">
        <Header />
        <t-layout class="w-full overflow-y-auto">
            <Aside />
            <t-layout class="w-full overflow-y-auto">
                <t-content class="overflow-y-scroll">
                    <div class="p-2 m-1 bg-white">
                        <router-view class="overflow-y-auto" />
                    </div>
                    <div class="flex justify-center align-middle">
                        <Footer />
                    </div>
                </t-content>
            </t-layout>
        </t-layout>
    </t-layout>
</template>
<script setup lang="ts">
import Header from "@/layout/header/index.vue"
import Aside from "@/layout/aside/index.vue"
import Footer from "@/layout/footer/index.vue"
</script>

<style scoped>
::-webkit-scrollbar {
    display: none;
}
</style>