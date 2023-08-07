import Axios from "@/utils/request"
import { URL } from "@/config"

/** 导入接口返回类型 */
import { resType } from "@/api/index"
import { MessagePlugin } from 'tdesign-vue-next'

/**
 * 获取所有物流公司信息
 * @returns 
 */
export const getLogisticsCompany = async () => {
    return await Axios<resType>({
        url: '/logistics/getLogisticsCompany',
        method: 'get',
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 获取所有物流公司名称信息
 * @returns 
 */
export const getLogisticsCompanyName = async () => {
    return await Axios<resType>({
        url: '/logistics/getLogisticsCompanyName',
        method: 'get',
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 禁用某个物流公司
 * @returns 
 */
export const forbiddenisLogisticsCompany = async (logisticsCompanyId: string, status: number) => {
    return await Axios<resType>({
        url: '/logistics/forbiddenisLogisticsCompany/' + logisticsCompanyId + "/" + status,
        method: 'get',
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 禁用快递订单
 * @returns 
 */
export const getExpressOrderPage = async (data: any) => {
    return await Axios<resType>({
        url: '/logistics/getExpressOrderPage' ,
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 禁用某个快递订单物流详情
 * @returns 
 */
export const getLogisticsTrackDetail = async (expressOrderId: string) => {
    return await Axios<resType>({
        url: '/logistics/getLogisticsTrackDetail/' + expressOrderId ,
        method: 'get',
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}
