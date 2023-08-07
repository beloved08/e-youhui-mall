import Axios from "@/utils/request"
import { URL } from "@/config"

/** 导入接口返回类型 */
import { resType } from "@/api/index"
import { MessagePlugin } from 'tdesign-vue-next'

/**
 * 分页获取用户信息
 * @returns 
 */
export const getMallUserPage = async (data: any) => {
    return await Axios<resType>({
        url: '/user/getAllOrdinaryMallUserByPage',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 获取所有商城用户的用户名称
 * @returns 
 */
export const getAllMallUserName = async () => {
    return await Axios<resType>({
        url: '/user/getAllMallUserName',
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 分页获取用户收货地址信息
 * @returns 
 */
export const getAllUserAddressPage = async (data: any) => {
    return await Axios<resType>({
        url: '/user/getAllUserAddress',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 分页获取用户收藏数据
 * @returns 
 */
export const getAllUserCollectPage = async (data: any) => {
    return await Axios<resType>({
        url: '/user/getAllUserCollect',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 获取用户
 * @returns 
 */
export const getMallUserByUserId = async (data: any) => {
    return await Axios<resType>({
        url: '/user/getMallUserByUserId/' + data,
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 获取总用户数量
 * @returns 
 */
export const getTotalUser = async () => {
    return await Axios<resType>({
        url: '/user/getTotalUser',
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 获取总会员数量
 * @returns 
 */
export const getTotalMember = async () => {
    return await Axios<resType>({
        url: '/user/getTotalMember',
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 获取今日新增会员数量
 * @returns 
 */
export const getTodayAddMember = async () => {
    return await Axios<resType>({
        url: '/user/getTodayAddMember',
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}
