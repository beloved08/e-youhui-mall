import GoEasy from 'goeasy'
import noticeStore from '@/store/modules/notice.js'

// 初始化GoEasy
let goeasy = GoEasy.getInstance({
	host: "hangzhou.goeasy.io", 
	appkey: "GoEasy_Key",
	modules: ['pubsub']
})

//建立连接
goeasy.connect({
	id: "uniapp",
	data: {
		"avatar": "/www/xxx.png",
		"nickname": "Neo"
	}, 
	onSuccess: function() { 
		console.log("GoEasy connect successfully.")
	},
	onFailed: function(error) { 
		console.log("Failed to connect GoEasy, code:" + error.code + ",error:" + error
			.content);
	},
	onProgress: function(attempts) { 
		console.log("GoEasy is connecting", attempts);
	}
})

//订阅商家入驻、通过、不通过等消息
goeasy.pubsub.subscribe({
	channel: "business_channel",
	onMessage: function(message) {
		const msg = JSON.parse(message.content)
		noticeStore.mutations.setBusinessData(noticeStore.state,msg)
		uni.showTabBarRedDot({
			index: 2
		})
	},
	onSuccess: function() {
		console.log("notice_channel pubsub successful");
	},
	onFailed: function(error) {
		console.log("notice_channel pubsub fail")
	}
})

// 订阅用户余额变化事件通道
goeasy.pubsub.subscribe({
	channel: "balance_channel",
	onMessage: function(message) {
		const msg = JSON.parse(message.content)
		noticeStore.mutations.setBalanceData(noticeStore.state,msg)
		uni.showTabBarRedDot({
			index: 2
		})
	},
	onSuccess: function() {
		console.log("balance_channel pubsub successful");
	},
	onFailed: function(error) {
		console.log("balance_channel pubsub fail")
	}
})

// 订阅用户积分变化事件通道
goeasy.pubsub.subscribe({
	channel: "integral_channel",
	onMessage: function(message) {
		const msg = JSON.parse(message.content)
		noticeStore.mutations.setIntegralData(noticeStore.state,msg)
		uni.showTabBarRedDot({
			index: 2
		})
	},
	onSuccess: function() {
		console.log("integral_channel pubsub successful");
	},
	onFailed: function(error) {
		console.log("integral_channel pubsub fail")
	}
})

// 订阅用户购买会员事件通道
goeasy.pubsub.subscribe({
	channel: "userPurchaseVip_channel",
	onMessage: function(message) {
		const msg = JSON.parse(message.content)
		noticeStore.mutations.setPurchaseVipData(noticeStore.state,msg)
		uni.showTabBarRedDot({
			index: 2
		})
	},
	onSuccess: function() {
		console.log("userPurchaseVip_channel pubsub successful");
	},
	onFailed: function(error) {
		console.log("userPurchaseVip_channel pubsub fail")
	}
})

// 订阅用户创建订单事件通道
goeasy.pubsub.subscribe({
	channel: "createOrder_chancel",
	onMessage: function(message) {
		const msg = JSON.parse(message.content)
		noticeStore.mutations.setCreateOrderData(noticeStore.state,msg)
		uni.showTabBarRedDot({
			index: 2
		})
	},
	onSuccess: function() {
		console.log("createOrder_chancel pubsub successful");
	},
	onFailed: function(error) {
		console.log("createOrder_chancel pubsub fail")
	}
})

// 订阅用户申请成为促销人员消息通道
goeasy.pubsub.subscribe({
	channel: "promotion_people_channel",
	onMessage: function(message) {
		const msg = JSON.parse(message.content)
		noticeStore.mutations.setPromotionPeopleData(noticeStore.state,msg)
		uni.showTabBarRedDot({
			index: 2
		})
	},
	onSuccess: function() {
		console.log("promotion_people_channel pubsub successful");
	},
	onFailed: function(error) {
		console.log("promotion_people_channel pubsub fail")
	}
})

export default goeasy
