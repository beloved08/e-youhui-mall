const commodityStore = {
	state: {
		commodityInfo: {}
	},
	getters: {
		getCommodityInfo(state) {
			return state.commodityInfo
		}
	},
	mutations: {
		setCommodityInfo(state, v) {
			state.commodityInfo = v
		}
	},
	actions: {
		// 保存商品信息
		saveCommodityInfoDetail(context, commodityInfo) {
			context.commit('setCommodityInfo', commodityInfo)
		}
	}
}

export default commodityStore
