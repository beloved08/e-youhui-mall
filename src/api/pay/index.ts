import Axios from "@/utils/request"
import { URL } from "@/config"

/** 导入接口返回类型 */
import { resType } from "@/api/index"
import { MessagePlugin } from 'tdesign-vue-next'

/**
 * 分页获取用户余额数据
 * @returns 
 */
export const getAllUserBalancePage = async (data: any) => {
    return await Axios<resType>({
        url: '/pay/getUserBalancePage',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 分页获取用户订单
 * @returns 
 */
export const getAllOrderListPage = async (data: any) => {
    return await Axios<resType>({
        url: '/pay/order/getAllOrderListPage',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 获取订单详情
 * @returns 
 */
export const getOrderDetail = async (orderId: any) => {
    return await Axios<resType>({
        url: '/pay/order/getOrderDetail/' + orderId,
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}
/**
 * 获取支付方式
 * @returns 
 */
export const getAllPayWayList = async () => {
    return await Axios<resType>({
        url: '/pay/getAllPayWay',
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 立即发货
 * @returns 
 */
export const shipImmediatelyByNow = async (data: any) => {
    return await Axios<resType>({
        url: '/pay/order/shipImmediately',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 获取订单数和销售总额
 * @returns 
 */
export const getOrderCount = async () => {
    return await Axios<resType>({
        url: '/pay/order/getOrderCount',
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 获取近一个月的订单趋势
 * @returns 
 */
export const getOrderTrendsPastMonth = async () => {
    return await Axios<resType>({
        url: '/pay/order/getOrderTrends',
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 订单状态数
 * @returns 
 */
export const getOrderStatusSituation = async () => {
    return await Axios<resType>({
        url: '/pay/order/getOrderStatusSituation',
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 获取今日订单数和今日销售额
 * @returns 
 */
export const getToadyOrderCount = async () => {
    return await Axios<resType>({
        url: '/pay/order/getToadyOrderCount',
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 获取商家总收入
 * @returns 
 */
export const getBusinessTotalIncome = async () => {
    return await Axios<resType>({
        url: '/pay/order/getBusinessTotalIncome',
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 获取地区销量
 * @returns 
 */
export const getRegionalSalesRanking = async () => {
    return await Axios<resType>({
        url: '/pay/order/getRegionalSalesRanking',
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}
