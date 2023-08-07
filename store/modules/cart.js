import { addCart,deleteCart } from '@/api/commodity/commodity.js'
import userStore from '@/store/modules/user.js'

const cartStore = {
	state: {
		cartCommodityInfo: [],
	},
	getters: {
		getCartCommodityInfo(state) {
			return state.cartCommodityInfo
		}
	},
	mutations: {
		setCartCommodityInfo(state, v) {
			// TODO 判断当前添加的商品是否已在购物车中
			state.cartCommodityInfo.map(c => {
				if(v.commodity.commodityId == c.commodity.commodityId){
					//存在，增加数量
					let count = c.quantity
					state.cartCommodityInfo.splice(c,1)
					v.quantity += count
					state.cartCommodityInfo.push(v)
				}else{
					//不在购物车中
					state.cartCommodityInfo.push(v)
				}
			})
		},
		resetCartCommodityInfo(state,v){
			state.cartCommodityInfo = []
			v.map(m=>{
				state.cartCommodityInfo.push(m)
			})
		}
	},
	actions: {
		// 保存购物车商品信息
		async saveCartCommodityInfo(context, commodityInfo) {
			context.commit('setCartCommodityInfo', commodityInfo)
			const c = {
				"userId": userStore.state.userInfo.userId,
				"commodityId": commodityInfo.commodity.commodityId,
				"businessId": commodityInfo.business.businessId,
			}
			const { data: res } = await addCart(c)
		},
		// 重新保存购物车商品信息
		 async resetSaveCartCommodityInfo(context, commodityInfo) {
			context.commit('resetCartCommodityInfo', commodityInfo.commodityList)
			
			const m = {
				"userId": userStore.state.userInfo.userId,
				"commodityId": commodityInfo.deleteItem.commodity.commodityId,
				"businessId": commodityInfo.deleteItem.business.businessId,
			}
			const { data: res } = await deleteCart(m)			
		}
	}
}

export default cartStore
