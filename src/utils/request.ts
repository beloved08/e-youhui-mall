import axios, { AxiosRequestConfig } from "axios"
import { URL, TIMEOUT } from "@/config"
import router from '@/router/index'
import { Modal } from 'ant-design-vue'
import { createVNode } from 'vue'
import { CloseCircleFilled } from '@ant-design/icons-vue'
import { userStore } from '@/store/modules/user'

/**
 * @说明 接口请求返回信息
 */
// export interface requestReturnType<T = any> {
//   /**
//    * @说明 返回code状态码
//    */
//   code: number;
//   /**
//    * @说明 返回信息说明
//    */
//   msg: string;
//   /**
//    * @说明 返回总体数据
//    */
//   data: T;
// }
1
export interface requestReturnType<T = any> {
    /**
     * @说明 返回code状态码
     */
    code: number
    /** 
     * @说明 数据内容 
     */
    data: T
    /**
     * @说明 返回的字符串信息
     */
    msg: string
}

/** 创建axios实例 */
const instance = axios.create({
    timeout: TIMEOUT, // 超时时间
    baseURL: URL,
    headers: {
        "Content-Type": "application/json;charset=utf-8",
    }
})

/** 添加请求拦截 */
instance.interceptors.request.use(
    (config: any) => {
        // 如果token存在，则让每个请求携带token-- ['authorization']为自定义key
        config.headers['authorization'] = (userStore().getToken != '' || userStore().getToken != null || !userStore().getIsLogin) ? userStore().getToken : ''
        config.headers['sessionId'] = (userStore().getToken != '' || userStore().getToken != null || !userStore().getIsLogin) ? userStore().getUserInfo?.sessionId : ''
        // console.log(config)
        return config
    },
    (err) => {
        return Promise.reject(err)
    }
)

/**  响应拦截 */
instance.interceptors.response.use(
    (response) => {
        return response
    },
    (error) => {
        // tryHideFullScreenLoading()
        console.log(error)
        if (error && error.response) {
            switch (error.response.status) {
                case 400:
                    error.message = "错误请求"
                    break
                case 401:
                    error.message = "未授权，请重新登录"
                    // 前往登录页面进行登录
                    Modal.confirm({
                        title: '授权信息失效，请重新登录授权！！！',
                        icon: createVNode(CloseCircleFilled),
                        okText: '立即登录授权',
                        cancelText: '暂不登录',
                        centered: true,
                        onOk () {
                            router.push({ path: "/" })
                        },
                        onCancel () { },
                    })
                    break
                case 403:
                    error.message = "拒绝访问"
                    break
                case 404:
                    error.message = "请求错误,未找到该资源"
                    break
                case 405:
                    error.message = "请求方法未允许"
                    break
                case 408:
                    error.message = "请求超时"
                    break
                case 500:
                    error.message = "服务器端出错"
                    break
                case 501:
                    error.message = "网络未实现"
                    break
                case 502:
                    error.message = "网络错误"
                    break
                case 503:
                    error.message = "服务不可用"
                    break
                case 504:
                    error.message = "网络超时"
                    break
                case 505:
                    error.message = "http版本不支持该请求"
                    break
                default:
                    error.message = `连接错误${ error.response.status }`
            }
        } else {
            error.message = "连接到服务器失败"
        }
        return Promise.reject(error.message)
    }
)
/** 代理请求 */
// 注意此处的泛型T，默认值时any；兼容未提供指定类型的方式
async function Axios<T = any> (config: AxiosRequestConfig): Promise<requestReturnType<T>> {
    let resData = await instance(config)
    return resData.data
}

export default Axios;

