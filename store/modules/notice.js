const noticeStore = {
	state: {
		// 每一种类型的消息缓存的最大消息数
		noticeStorageNumber: 3,
		// 商家
		businessData: uni.getStorageSync('user_business') != '' ? uni.getStorageSync('user_business') : [],
		// 余额
		balanceData: uni.getStorageSync('user_balance') != '' ? uni.getStorageSync('user_balance') : [],
		// 积分
		integralData: uni.getStorageSync('user_integral') != '' ? uni.getStorageSync('user_integral') : [],
		// 开通会员
		purchaseVipData: uni.getStorageSync('user_purchase_vip') != '' ? uni.getStorageSync('user_purchase_vip') : [],
		// 创建订单
		createOrderData: uni.getStorageSync('user_create_order') != '' ? uni.getStorageSync('user_create_order') : [],
		// 促销人员申请
		promotionPeopleData: uni.getStorageSync('promotion_people_data') != '' ? uni.getStorageSync('promotion_people_data') : [],
	},
	getters: {
		getBusinessData(state) {
			return state.businessData
		},
		getBalanceData(state) {
			return state.balanceData
		},
		getIntegralData(state) {
			return state.integralData
		},
		getPurchaseVipData(state) {
			return state.purchaseVipData
		},
		getCreateOrderData(state) {
			return state.createOrderData
		},
		getPromotionPeopleData(state) {
			return state.promotionPeopleData
		}
	},
	mutations: {
		// 保存用户余额
		setBalanceData(state, v) {
			// 判断当前缓存中消息数量
			if(state.balanceData.length >= state.noticeStorageNumber){
				// uni.removeStorageSync('user_balance')
				const removeNotice = state.balanceData[0]
				state.balanceData.splice(removeNotice,1)
			}
			state.balanceData.push(v)
			// uni.setTabBarBadge({
			//   index: 2,
			//   text: state.balanceData.length + ''
			// })
			uni.removeStorageSync('user_balance')
			uni.setStorageSync('user_balance', state.balanceData)
		},
		// 保存用户积分
		setIntegralData(state, v) {
			// 判断当前缓存中消息数量
			if(state.integralData.length >= state.noticeStorageNumber){
				const removeNotice = state.integralData[0]
				state.integralData.splice(removeNotice,1)
			}
			state.integralData.push(v)
			// uni.setTabBarBadge({
			//   index: 2,
			//   text: '2'
			// })
			uni.removeStorageSync('user_integral')
			uni.setStorageSync('user_integral', state.integralData)
		},
		// 保存商家入驻申请等
		setBusinessData(state, v) {
			// 判断当前缓存中消息数量
			if(state.businessData.length >= state.noticeStorageNumber){
				const removeNotice = state.businessData[0]
				state.businessData.splice(removeNotice,1)
			}
			state.businessData.push(v)
			// uni.setTabBarBadge({
			//   index: 2,
			//   text: '2'
			// })
			uni.removeStorageSync('user_business')
			uni.setStorageSync('user_business', state.businessData)
		},
		// 保存用户开通会员
		setPurchaseVipData(state, v) {
			// 判断当前缓存中消息数量
			if(state.purchaseVipData.length >= state.noticeStorageNumber){
				const removeNotice = state.purchaseVipData[0]
				state.purchaseVipData.splice(removeNotice,1)
			}
			state.purchaseVipData.push(v)
			// uni.setTabBarBadge({
			//   index: 2,
			//   text: '2'
			// })
			uni.removeStorageSync('user_purchase_vip')
			uni.setStorageSync('user_purchase_vip', state.purchaseVipData)
		},
		// 保存用户创建订单
		setCreateOrderData(state, v) {
			// 判断当前缓存中消息数量
			if(state.createOrderData.length >= state.noticeStorageNumber){
				const removeNotice = state.createOrderData[0]
				state.createOrderData.splice(removeNotice,1)
			}
			state.createOrderData.push(v)
			// uni.setTabBarBadge({
			//   index: 2,
			//   text: '2'
			// })
			uni.removeStorageSync('user_create_order')
			uni.setStorageSync('user_create_order', state.createOrderData)
		},
		// 保存用户申请成为促销人员
		setPromotionPeopleData(state, v) {
			// 判断当前缓存中消息数量
			if(state.promotionPeopleData.length >= state.noticeStorageNumber){
				const removeNotice = state.promotionPeopleData[0]
				state.promotionPeopleData.splice(removeNotice,1)
			}
			state.promotionPeopleData.push(v)
			// uni.setTabBarBadge({
			//   index: 2,
			//   text: '2'
			// })
			uni.removeStorageSync('promotion_people_data')
			uni.setStorageSync('promotion_people_data', state.promotionPeopleData)
		}
	},
	actions: {
		saveBusinessDataSync(context, data) {
			context.commit('setBusinessData', data)
		},
		saveBalanceDataSync(context, data) {
			context.commit('setBalanceData', data)
		},
		saveIntegralDataSync(context, data) {
			context.commit('setIntegralData', data)
		},
		savePurchaseVipDataSync(context, data) {
			context.commit('setPurchaseVipData', data)
		},
		savePromotionPeopleDataSync(context, data) {
			context.commit('setPromotionPeopleData', data)
		},
	}
}

export default noticeStore
