<template>
    <div class="">管理员登录首页</div>
</template>

<script setup lang="ts">
// import { ref } from "vue"
// import GoEasy from 'goeasy'
// const merchantSettlementChannelData = ref<any>()

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
//         console.log("接收到的消息：->" + message.content)
//         merchantSettlementChannelData.value = message.content
//     },
//     onSuccess: function () {
//         console.log("Channel-merchant_settlement_channel订阅成功。")
//     },
//     onFailed: function (error) {
//         console.log("Channel订阅失败, 错误编码：" + error.code + " 错误信息：" + error.content)
//     }
// })

// goeasy.pubsub.publish({
//     channel: "merchant_settlement_channel",
//     message: "Hello GoEasy!-----",
//     onSuccess: function () {
//         console.log("消息发布成功。")
//     },
//     onFailed: function (error) {
//         console.log("消息发送失败，错误编码：" + error.code + " 错误信息：" + error.content)
//     },
//     wxmpTemplateMsg: undefined
// })


</script>

<style scoped></style> 
