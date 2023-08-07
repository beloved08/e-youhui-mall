import Axios from "@/utils/request"

/** 导入接口返回类型 */
import { resType } from "@/api/index"
import { MessagePlugin } from 'tdesign-vue-next'

/**
 * 管理员登录
 * @returns 
 */
export const adminLogin = async (data: any) => {
    return await Axios<resType>({
        url: '/jurisdiction/login',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}
/**
 * 管理员退出登录
 * @returns 
 */
export const adminLogout = async () => {
    return await Axios<resType>({
        url: '/jurisdiction/logout',
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}
/**
 * 管理员登录
 * @returns 
 */
export const checkoutAdmin = async () => {
    return await Axios<resType>({
        url: '/jurisdiction/checkoutAdmin',
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 获取菜单按钮信息
 * @returns 
 */
export const adminMenu = async () => {
    return await Axios<resType>({
        url: '/jurisdiction/selectUserMenu',
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 获取所有admin集合
 * @returns 
 */
export const getAllAdminList = async (type: number) => {
    return await Axios<resType>({
        url: '/user/adminList/' + type,
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}
