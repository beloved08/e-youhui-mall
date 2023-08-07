<template>
    <t-header class="m-1">
        <t-head-menu default-value="2-1" expand-type="popup">
            <template #logo>
                <img class="h-12 ml-4 w-24" src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/logo/logo.png"
                    alt="logo" />
            </template>
            <t-menu-item value="1-1" @click=" toAddress('http://119.91.213.165:8848/nacos') ">
                <template #icon>
                    <t-icon name="precise-monitor" />
                </template>
                Nacos
            </t-menu-item>
            <t-menu-item value="1-2" @click=" toAddress('http://101.43.88.178:15672') ">
                <template #icon>
                    <t-icon name="link-unlink" />
                </template>
                RabbitMQ
            </t-menu-item>
            <template #operations>
                <div class="mr-10">
                    <Right />
                </div>
            </template>
        </t-head-menu>
    </t-header>
</template>

<script setup lang="ts">
import Right from '@/layout/header/right/index.vue'

const toAddress = (url: string) => {
    window.open(url)
}
</script>

<style lang="less" scoped>
.t-menu__operations {
    .t-button {
        margin-left: 4px;
    }
}

.t-demo-menu--dark {
    .t-button {
        color: #fff;

        &:hover {
            background-color: #4b4b4b;
            border-color: transparent;
            --ripple-color: #383838;
        }
    }
}
</style>