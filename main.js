import App from './App'

// 引入路由
import {router,RouterMount} from '@/router/router.js'
Vue.use(router)

// #ifndef VUE3
import Vue from 'vue'
Vue.config.productionTip = false
App.mpType = 'app'

import uView from 'uview-ui'
Vue.use(uView)
// 如此配置即可
uni.$u.config.unit = 'rpx'

import goeasy from './js/goeasy/initGoeasy.js'

// vuex状态管理
import  store from '@/store/index.js' 

// 网络请求
import  requestService  from '@/utils/request.js'
uni.$http = requestService

const app = new Vue({
	store,
    ...App
})

// app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'

export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif

//v1.3.5起 H5端 你应该去除原有的app.$mount();使用路由自带的渲染方式
// #ifdef H5
	RouterMount(app,router,'#app')
// #endif

// #ifndef H5
	app.$mount(); //为了兼容小程序及app端必须这样写才有效果
// #endif