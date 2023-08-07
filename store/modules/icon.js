const iconStore = {
	state: {
		// 所有图标颜色
		iconColor: uni.getStorageSync('iconColor') != '' ? uni.getStorageSync('iconColor') : '#ff0000',
		// 首页工具栏列表图标大小
		indexIconSize: 48,
		// 首页工具栏列表图标
		indexIconList: [{
				name: 'photo',
				title: '新品推荐',
				index: 0
			},
			{
				name: 'lock',
				title: '限时特惠',
				index: 1
			},
			{
				name: 'star',
				title: '秒杀专场',
				index: 2
			},
			{
				name: 'hourglass',
				title: '领券中心',
				index: 3
			},
			{
				name: 'home',
				title: '每日签到',
				index: 4
			},
			{
				name: 'star',
				title: '会员中心',
				index: 5
			}, {
				name: 'order',
				title: '积分商城',
				index: 6
			}, {
				name: 'grid',
				title: '每日爆款',
				index: 7
			}
		],
		// 个人中心页面图标大小
		personalIconSize: 20,
		// 个人中心页面图标
		personalIconList: {
			orderIconList: [{
				index: 0,
				name: "wallet",
				title: "待付款"
			}, {
				index: 1,
				name: "house",
				title: "待发货"
			}, {
				index: 2,
				name: "transport",
				title: "待收货"
			}, {
				index: 3,
				name: "shop",
				title: "已完成"
			}, {
				index: 4,
				name: "redpacket",
				title: "售后"
			}],
			serviceUtilIconList: [{
				index: 0,
				name: "coupon",
				title: "会员中心"
			}, {
				index: 1,
				name: "explore",
				title: "领券中心"
			}, {
				index: 2,
				name: "signin",
				title: "积分中心"
			}, {
				index: 3,
				name: "gps",
				title: "地址管理"
			}, {
				index: 4,
				name: "manage",
				title: "收藏中心"
			}, {
				index: 5,
				name: "kefu",
				title: "官方客服"
			}, {
				index: 6,
				name: "evaluate",
				title: "图标设置"
			}, {
				index: 7,
				name: "setup",
				title: "系统设置"
			}]
		}
	},
	getters: {
		getIconColor(state) {
			return state.iconColor
		},
		getIndexIconSize(state){
			return state.indexIconSize
		},
		getIndexIconList(state) {
			return state.indexIconList
		},
		getPersonalIconSize(state){
			return state.personalIconSize
		},
		getPersonalIconList(state){
			return state.personalIconList
		}
	},
	mutations: {
		setIconColor(state, v) {
			state.iconColor = v
		}
	},
	actions: {
		// 设置图标颜色
		setIconColorAsync(context, v) {
			uni.setStorageSync('iconColor', v);
			context.commit('setIconColor', v)
			uni.showToast({
				title: '设置成功',
				duration: 2000
			})
		},
		// 恢复图标默认颜色
		restoreDefaultIconColor(context){
			context.commit('setIconColor', '#ff0000')
			uni.showToast({
				title: '恢复成功',
				duration: 2000
			})
		}
	}
}

export default iconStore
