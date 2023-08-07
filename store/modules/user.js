const userStore = {
	state: {
		isLogin: uni.getStorageSync('LOGIN_USER_DETAIL') ? uni.getStorageSync('LOGIN_USER_DETAIL').isLogin : false,
		userInfo: uni.getStorageSync('LOGIN_USER_DETAIL') ? uni.getStorageSync('LOGIN_USER_DETAIL').userInfo : 
				'https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/avatar/avatar.png',
		token: uni.getStorageSync('LOGIN_USER_DETAIL')  ? uni.getStorageSync('LOGIN_USER_DETAIL').token : ''
	},
	getters: {
		getIsLogin(state) {
			return state.isLogin
		},
		getUserInfo(state) {
			return state.userInfo
		},
		getToken(state) {
			return state.token
		}
	},
	mutations: {
		setIsLogin(state, v) {
			state.isLogin = v
		},
		setUserInfo(state, v) {
			state.userInfo = v
		},
		setToken(state, v) {
			state.token = v
		}
	},
	actions: {
		// 保存用户信息
		saveUserDetail(context, userDetail) {
			context.commit('setIsLogin', userDetail.isLogin)
			context.commit('setUserInfo', userDetail.userInfo)
			context.commit('setToken', userDetail.token)

			uni.setStorageSync("LOGIN_USER_DETAIL", userDetail)
		},
		// 移除用户信息
		removeUserDetail(context) {
			context.commit('setIsLogin', false)
			context.commit('setUserInfo', '')
			context.commit('setToken', '')
			uni.removeStorageSync("LOGIN_USER_DETAIL")
		}
	}
}

export default userStore
