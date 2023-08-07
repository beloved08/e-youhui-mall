import GoEasy from 'goeasy'
import { noticeStore } from '@/store/modules/notice'
import { notification } from 'ant-design-vue'
import { SmileOutlined } from '@ant-design/icons-vue'
import { h } from 'vue'
import router from '@/router/index'


let goeasy = GoEasy.getInstance({
    host: "hangzhou.goeasy.io",  //若是新加坡区域：singapore.goeasy.io
    appkey: "GoEasy_Key",
    modules: ['pubsub']//根据需要，传入‘pubsub’或'im’，或数组方式同时传入
})

//建立连接
goeasy.connect({
    id: "001", //pubsub选填，im必填，最大长度60字符
    data: { "avatar": "/www/xxx.png", "nickname": "Neo" }, //必须是一个对象，pubsub选填，im必填，最大长度300字符，用于上下线提醒和查询在线用户列表时，扩展更多的属性
    onSuccess: function () {  //连接成功
        console.log("连接成功") //连接成功
    },
    onFailed: function (error) { //连接失败
        console.log("连接失败, code:" + error.code + ",error:" + error.content)
    },
    onProgress: function (attempts) { //连接或自动重连中
        console.log("连接或自动重连中", attempts)
    }
})

//订阅消息
goeasy.pubsub.subscribe({
    channel: "merchant_settlement_channel",
    onMessage: function (message) {
        let msg = JSON.parse(message.content)
        noticeStore().saveMerchantSettlementData(msg)
        notification.open({
            message: msg.title,
            description: "现有新的商家入驻小程序商城平台啦，请尽快处理哦！" + '名称：' + msg.businessName + '-' + msg.shopName + '，时间：' + msg.currentTime,
            style: {
                color: '#00adb5'
            },
            icon: () => h(SmileOutlined, { style: 'color: #108ee9' }),
            onClick: () => {
                router.push({ path: '/shop' })
            },
        })
    },
    onSuccess: function () {
        console.log("merchant_settlement_channel-merchant_settlement111_channel 订阅成功。")
    },
    onFailed: function (error) {
        console.log("merchant_settlement_channel 订阅失败")
    }
})

// 订阅已完成付款待发货你的订单
goeasy.pubsub.subscribe({
    channel: "createOrderManageSys_chancel",
    onMessage: function (message) {
        let msg = JSON.parse(message.content)
        noticeStore().saveShippedOrderData(msg)
        notification.open({
            message: "待发货订单",
            description: "您有新的订单，请尽快处理哦！" + '订单号：' + msg.orderNumber + '，创建时间' + msg.createTime + '，付款时间：' + msg.strikeBargainTime,
            style: {
                color: '#00adb5'
            },
            icon: () => h(SmileOutlined, { style: 'color: #108ee9' }),
            onClick: () => {
                router.push({ path: '/order-mall' })
            },
        })
    },
    onSuccess: function () {
        console.log("createOrderManageSys_chancel 订阅成功。")
    },
    onFailed: function (error) {
        console.log("createOrderManageSys_chancel 订阅失败")
    }
})

// 订阅促销人员申请
goeasy.pubsub.subscribe({
    channel: "national_promotion_channel",
    onMessage: function (message) {
        let msg = JSON.parse(message.content)
        noticeStore().saveNationalPromotionData(msg)
        notification.open({
            message: "全民促销人员申请",
            description: "您有新的全民促销人员申请，请尽快处理哦！，电话号码：" + msg.phone + "，时间：" + msg.currentTime,
            style: {
                color: '#00adb5'
            },
            icon: () => h(SmileOutlined, { style: 'color: #108ee9' }),
            onClick: () => {
                router.push({ path: '/promotion-person' })
            },
        })
    },
    onSuccess: function () {
        console.log("createOrderManageSys_chancel 订阅成功。")
    },
    onFailed: function (error) {
        console.log("createOrderManageSys_chancel 订阅失败")
    }
})

export default goeasy
