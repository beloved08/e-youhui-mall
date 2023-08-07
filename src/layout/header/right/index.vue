<template>
    <t-space :size=" space_size ">
        <!-- <t-button variant="text" shape="square">
            <template #icon><t-icon name="search" :size=" icon_size " /></template>
        </t-button> -->
        <a-popover placement="bottomRight" trigger="click">
            <template #content>
                <GlobalMessage />
            </template>
            <template #title>
                <span class="text-sm font-bold">消息通知</span>
            </template>
            <t-badge
                :count=" merchantSettlementChannelData.length + shippedOrderData.length + nationalPromotionData.length "
                size="small">
                <t-button variant="text" shape="square">
                    <template #icon><t-icon name="mail" :size=" icon_size " /></template>
                </t-button>
            </t-badge>
        </a-popover>
        <!-- <t-button variant="text" shape="square">
            <template #icon><t-icon name="setting" :size=" icon_size " /></template>
        </t-button> -->
        <t-button variant="text" shape="square">
            <template #icon><t-icon :name=" fullscreenName " @click=" click " :size=" icon_size " /></template>
        </t-button>
        <t-dropdown :options=" options ">
            <t-button variant="text" shape="square">
                <template #icon><t-icon name="user" :size=" icon_size " /></template>
            </t-button>
        </t-dropdown>
    </t-space>
</template>

<script setup lang="ts">
import { ref } from "vue"
import { MessagePlugin } from 'tdesign-vue-next'
import { userStore } from '@/store/modules/user'
import router from '@/router/index'
import { adminLogout } from '@/api/admin/index'
import GlobalMessage from '@/components/globalMessages/index.vue'

import { noticeStore } from '@/store/modules/notice'

import screenfull from "screenfull"

const fullscreenName = ref<any>('fullscreen')

const click = () => {
    // 判断是否支持
    if (!screenfull?.isEnabled) {
        MessagePlugin.warning("不支持全屏")
        return false
    }
    screenfull.toggle()

    fullscreenName.value = screenfull.isFullscreen ? 'fullscreen' : 'fullscreen-exit'
}

const state = noticeStore()
const merchantSettlementChannelData = ref<any>(state.getMerchantSettlementData)
const shippedOrderData = ref<any>(state.shippedOrderData)
const nationalPromotionData = ref<any>(state.nationalPromotionData)


const space_size = ref<number>(16)
const icon_size = ref<string>("large")

const toHome = () => {
    MessagePlugin.success('前往首页')
    router.push({ path: '/dashboard' })
}

// 退出登录
const logout = () => {
    adminLogout().then((res: any) => {
        if (res?.code === 0) {
            userStore().setIsLogin(false)
            userStore().setToken('')
            userStore().setUserInfo(null)
            userStore().setUserMenu(null)
            MessagePlugin.success('正在退出系统...')

            setTimeout(() => {
                //需要延迟的代码 :2秒后延迟跳转到首页，可以加提示什么的
                router.push({ path: "/" })
                MessagePlugin.success('退出成功')
                //延迟时间：2秒
            }, 1000)
        }
    })
}

const options = [
    {
        content: '前往首页',
        value: 1,
        onClick: () => toHome(),
    },
    {
        content: '退出登录',
        value: 2,
        onClick: () => logout(),
    }
]

// const merchantSettlementChannelData = ref<any>([])

// let goeasy = GoEasy.getInstance({
//     host: "hangzhou.goeasy.io",  //若是新加坡区域：singapore.goeasy.io
//     appkey: "BC-901623b011ed45b688b8fc505db60d26",
//     modules: ['pubsub']//根据需要，传入‘pubsub’或'im’，或数组方式同时传入
// })

// //建立连接
// goeasy.connect({
//     id: "001", //pubsub选填，im必填，最大长度60字符
//     data: { "avatar": "/www/xxx.png", "nickname": "Neo" }, //必须是一个对象，pubsub选填，im必填，最大长度300字符，用于上下线提醒和查询在线用户列表时，扩展更多的属性
//     onSuccess: function () {  //连接成功
//         console.log("连接成功") //连接成功
//     },
//     onFailed: function (error) { //连接失败
//         console.log("连接失败, code:" + error.code + ",error:" + error.content)
//     },
//     onProgress: function (attempts) { //连接或自动重连中
//         console.log("连接或自动重连中", attempts)
//     }
// })

// //订阅消息
// goeasy.pubsub.subscribe({
//     channel: "merchant_settlement_channel",//替换为您自己的channel
//     onMessage: function (message) { //收到消息
//         merchantSettlementChannelData.value.push(JSON.parse(message.content))
//         notification.open({
//             message: '商家入驻通知',
//             description: "接收到商家入驻E优汇小程序平台的申请，请尽快审核处理",
//             icon: () => h(SmileOutlined, { style: 'color: #108ee9' }),
//         })
//     },
//     onSuccess: function () {
//         console.log("Channel-merchant_settlement_channel订阅成功。")
//     },
//     onFailed: function (error) {
//         console.log("Channel订阅失败, 错误编码：" + error.code + " 错误信息：" + error.content)
//     }
// })

</script>

<style scoped></style>