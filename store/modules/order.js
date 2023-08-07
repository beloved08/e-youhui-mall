const orderStore = {
	state: {
		// 订单的地址
		orderAddress: {},
		// 要下单的商品
		commodityList: []
	},
	getters: {
		getOrderAddress(state){
			return state.orderAddress
		},
		getCommodityList(state){
			return state.commodityList
		}
	},
	mutations: {
		setOrderAddress(state,address){
			state.orderAddress = address
		},
		setCommodityList(state,commodity){
			state.commodityList = []
			commodity.map(c => {
				state.commodityList.push(c)
			})
		}
	},
	actions: {
		selectOrderAddress(context, address){
			context.commit('setOrderAddress', address)
		},
		saveCommodityList(context, commodity){
			context.commit('setCommodityList', commodity)
		}
	}
}

export default orderStore
