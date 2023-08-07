// router.js
import {
	RouterMount,
	createRouter
} from 'uni-simple-router';
import store from '@/store/modules/user.js'

const router = createRouter({
	platform: process.env.VUE_APP_PLATFORM,
	routes: [...ROUTES]
});

//全局路由前置守卫
router.beforeEach((to, from, next) => {
	// 判断是否需要登录
	if (to.meta) {
		// 需要登录
		// 判读是否已经登录
		if (store.state.isLogin) {
			next()
		} else {
			// 未登录
			uni.showModal({
				title: '警告',
				content: '您还未登录，是否前往登录？',
				cancelText: "暂不登录",
				confirmText: "前往登录",
				confirmColor: '#ff0000',
				cancelColor: '#000000',
				success: function(res) {
					if (res.confirm) {
						// next()
						next({
							path: '/subPages/personal-pages/login/wx-login/wx-login',
							query: {
								loginTo: to.path
							}
						})
					} else {
						next({
							path: from.path
						})
					}
				}
			})
		}
	} else {
		// 不需要登录
		next()
	}
});

// 全局路由后置守卫
router.afterEach((to, from) => {
	console.log('跳转结束')
})

export {
	router,
	RouterMount
}
