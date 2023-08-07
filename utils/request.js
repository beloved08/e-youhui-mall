// 导入网络请求的包
import {
	$http
} from '@escook/request-miniprogram'
import {
	baseUrl
} from '@/utils/environment.js'

import userStore from '@/store/modules/user.js'

const requestService = $http

// 请求的根路径
$http.baseUrl = baseUrl

// 请求拦截器
$http.beforeRequest = function(options) {
	options.header = {
		'Content-Type': 'application/json;charset=UTF-8'
	}
	if (userStore.state.isLogin) {
		options.header = {
			'authorization': userStore.state.token,
			'Content-Type': 'application/json;charset=UTF-8'
		}
	}else{
		options.header = {
			'authorization': '',
			'Content-Type': 'application/json;charset=UTF-8'
		}
	}
	// console.log(options)
	uni.showLoading({
		title: '数据加载中...'
	})
}

// 响应拦截器
$http.afterRequest = (response) => {
	uni.hideLoading()
	if (response.statusCode === 200) {
		return response
	} else {
		let error_message = ""
		if (response.statusCode === 400) {
			error_message = "错误请求"
		} else if (response.statusCode === 401) {
			error_message = "未授权请登录"
		} else if (response.statusCode === 403) {
			error_message = "拒绝访问"
		} else if (response.statusCode === 404) {
			error_message = "资源未找到"
		} else if (response.statusCode === 405) {
			error_message = "请求方法不允许"
		} else if (response.statusCode === 408) {
			error_message = "请求超时"
		} else if (response.statusCode === 500) {
			error_message = "服务器端出错"
		} else if (response.statusCode === 501) {
			error_message = "网络未实现"
		} else if (response.statusCode === 502) {
			error_message = "网络错误"
		} else if (response.statusCode === 503) {
			error_message = "服务不可用"
		} else if (response.statusCode === 504) {
			error_message = "网络超时"
		} else if (response.statusCode === 505) {
			error_message = "http版本不支持"
		} else {
			error_message = '服务未启动'
		}

		uni.showToast({
			title: error_message,
			duration: 2000,
			image: '/static/request_icon/error.png'
		})

		return Promise.reject(error_message)
	}

}

export default requestService
