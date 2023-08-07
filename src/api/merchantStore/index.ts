import Axios from "@/utils/request"
import { URL } from "@/config"

/** 导入接口返回类型 */
import { resType } from "@/api/index"
import { MessagePlugin } from 'tdesign-vue-next'

/**
 * 分页、条件查询商家店铺数据
 * @returns 
 */
export const getMerchantStorePage = async (data: any) => {
    return await Axios<resType>({
        url: '/merchantStores/wx/getMerchantStorePage',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 根据店铺id获取人员信息
 * @returns 
 */
export const getBusinessUser = async (data: string) => {
    return await Axios<resType>({
        url: '/merchantStores/wx/getBusinessUserById/' + data,
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 商家店铺审核
 * @returns 
 */
export const residentAudit = async (data: any) => {
    return await Axios<resType>({
        url: '/merchantStores/wx/businessResidentAudit',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 商家店铺注销
 * @returns 
 */
export const logOffBusinessById = async (data: any) => {
    return await Axios<resType>({
        url: '/merchantStores/wx/logOffBusiness/' + data,
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 获取所有商家名称
 * @returns 
 */
export const getAllBusinessName = async (data: any) => {
    return await Axios<resType>({
        url: '/merchantStores/wx/getBusinessNameList?businessName=' + data,
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 获取所有商家数量
 * @returns 
 */
export const getTotalBusiness = async () => {
    return await Axios<resType>({
        url: '/merchantStores/wx/getTotalBusiness',
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 获取所有商家
 * @returns 
 */
export const getAllBusinessMap = async () => {
    return await Axios<resType>({
        url: '/merchantStores/wx/getAllBusinessMap',
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}
