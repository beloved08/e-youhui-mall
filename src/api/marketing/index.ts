import Axios from "@/utils/request"

/** 导入接口返回类型 */
import { resType } from "@/api/index"
import { MessagePlugin } from 'tdesign-vue-next'

/**
 * 创建通用优惠券
 * @returns 
 */
export const addUniversalCoupon = async (data: any) => {
    return await Axios<resType>({
        url: '/marketing/addUniversalCoupon',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 创建商家优惠券
 * @returns 
 */
export const addMerchantCoupon = async (data: any) => {
    return await Axios<resType>({
        url: '/marketing/addMerchantCoupon',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 分页条件搜索获取通用优惠券
 * @returns 
 */
export const getAllUniversalCouponPage = async (data: any) => {
    return await Axios<resType>({
        url: '/marketing/getAllUniversalCouponPage',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 分页条件搜索获取商家优惠券
 * @returns 
 */
export const getAllMerchantCouponPage = async (data: any) => {
    return await Axios<resType>({
        url: '/marketing/getAllMerchantCouponPage',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 删除通用优惠券
 * @returns 
 */
export const deleteUniversalCoupon = async (data: any) => {
    return await Axios<resType>({
        url: '/marketing/deleteUniversalCoupon/' + data,
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 删除商家优惠券
 * @returns 
 */
export const deleteMerchantCoupon = async (data: any) => {
    return await Axios<resType>({
        url: '/marketing/deleteMerchantCoupon/' + data,
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 分页获取促销人员列表
 * @returns 
 */
export const getPromotionPeoplePage = async (data: any) => {
    return await Axios<resType>({
        url: '/marketing/getPromotionPeoplePage',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 促销人员审核处理
 * @returns 
 */
export const approvedPromotionPeople = async (promotionPeopleId: any, status: number) => {
    return await Axios<resType>({
        url: '/marketing/approvedPromotionPeople/' + promotionPeopleId + "/" + status,
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 创建促销活动
 * @returns 
 */
export const createPromotionActivity = async (data: any) => {
    return await Axios<resType>({
        url: '/marketing/createPromotionActivity',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 上传导入促销活动商品
 * @returns 
 */
export const uploadPromotionActivityCommodity = async (data: any, promotionActivityId: any) => {
    return await Axios<resType>({
        url: '/marketing/uploadPromotionActivityCommodity?promotionActivityId=' + promotionActivityId,
        method: 'post',
        headers: {
            // 'authorization': (userStore().getToken != '' || userStore().getToken != null || !userStore().getIsLogin) ? userStore().getToken : '',
            "content-type": "multipart/form-data"
        },
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 分页条件搜索促销活动
 * @returns 
 */
export const getPromotionActivityPage = async (data: any) => {
    return await Axios<resType>({
        url: '/marketing/getPromotionActivityPage',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 分页条件搜索促销活动
 * @returns 
 */
export const getPromotionActivityCommodityPage = async (data: any) => {
    return await Axios<resType>({
        url: '/marketing/getPromotionActivityCommodityPage',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 设置当前活动
 * @returns 
 */
export const setCurrentPromotionActivity = async (promotionActivityId: any) => {
    return await Axios<resType>({
        url: '/marketing/setCurrentPromotionActivity/' + promotionActivityId,
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}
