import { createApp } from 'vue'
import './style.css'
import App from './App.vue'

import './index.css'

import TDesign from 'tdesign-vue-next'
import Antd from 'ant-design-vue'

// 引入组件库全局样式资源
import 'tdesign-vue-next/es/style/index.css'
import 'ant-design-vue/dist/antd.css'

// 引入Pinia
import store from './store'

// 引入路由 router
import router from './router/index'
import BaiduMap from 'vue-baidu-map-3x'

import '@/utils/goEasy'

import DataVVue3 from '@kjgl77/datav-vue3'
import Particles from "vue3-particles"

const app = createApp(App)

app.use(BaiduMap, {
    // ak 是在百度地图开发者平台申请的密钥 详见 http://lbsyun.baidu.com/apiconsole/key */
    ak: '百度地图开发者平台申请的密钥'
    // v:'2.0',  // 默认使用3.0
    // type: 'WebGL' // ||API 默认API  (使用此模式 BMap=BMapGL)
})

app.use(Particles)
app.use(DataVVue3)
app.use(store)
app.use(Antd)
app.use(router)
app.use(TDesign)


app.mount('#app')
