import Axios from "@/utils/request"
import { URL } from "@/config"

/** 导入接口返回类型 */
import { resType } from "@/api/index"
import { MessagePlugin } from 'tdesign-vue-next'
import { userStore } from '@/store/modules/user'

/**
 * 上传商品分类图标
 * @returns 
 */
export const uploadClassificationIcon = async (data: any, name: string) => {
    return await Axios<resType>({
        url: '/commodity/uploadClassificationIcon/' + name,
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
 * 添加商品分类数据
 * @returns 
 */
export const addCommodityClassification = async (data: any) => {
    return await Axios<resType>({
        url: '/commodity/addCommodityClassification',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 获取一级分类名称集合列表
 * @returns 
 */
export const getParentNameList = async (parentNameSearch: any) => {
    return await Axios<resType>({
        url: '/commodity/getParentNameList?parentNameSearch=' + parentNameSearch,
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 获取一级分类名称的ID
 * @returns 
 */
export const getParentId = async (parentName: string) => {
    return await Axios<resType>({
        url: '/commodity/getParentId?parentName=' + parentName,
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 分页获取商品分类数据
 * @returns 
 */
export const getClassificationListPage = async (data: any) => {
    return await Axios<resType>({
        url: '/commodity/getClassificationByPage',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 删除商品分类数据
 * @returns 
 */
export const deleteClassificationById = async (data: any) => {
    return await Axios<resType>({
        url: '/commodity/deleteClassification/' + data?.classificationGrade + '/' + data?.classificationId,
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 上传商品分类Excel
 * @returns 
 */
export const uploadBatchLeade = async (data: any, grade: any) => {
    return await Axios<resType>({
        url: '/commodity/uploadBatchLeade/' + grade,
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
 * 获取一级分类下的所有二级分类
 * @returns 
 */
export const getTwoLevelNameList = async (oneLevelName: any) => {
    return await Axios<resType>({
        url: '/commodity/getTwoLevelNameList?oneLevelName=' + oneLevelName,
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 上传商品图标
 * @returns 
 */
export const uploadCommodityImage = async (data: any, businessName: string, oneClassificationName: string,
    twoClassificationName: string, commodityName: string) => {
    return await Axios<resType>({
        url: '/commodity/uploadCommodityImage?businessName='
            + businessName + "&oneClassificationName="
            + oneClassificationName + "&twoClassificationName=" + twoClassificationName + "&commodityName=" + commodityName,
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
 * 添加商品
 * @returns 
 */
export const addCommodity = async (data: any) => {
    return await Axios<resType>({
        url: '/commodity/addCommodity',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 分页 + 条件搜索获取商品数据
 * @returns 
 */
export const getCommodityByPage = async (data: any) => {
    return await Axios<resType>({
        url: '/commodity/getCommodityByPage',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 将商品批量上架
 * @returns 
 */
export const batchLaunchCommodity = async (data: any) => {
    return await Axios<resType>({
        url: '/commodity/batchLaunchCommodity',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 将商品批量上下架
 * @returns 
 */
export const batchOffShelfCommodity = async (data: any) => {
    return await Axios<resType>({
        url: '/commodity/batchOffShelfCommodity',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 将商品批量推荐为新品
 * @returns 
 */
export const batchRecommendationCommodity = async (data: any) => {
    return await Axios<resType>({
        url: '/commodity/batchRecommendationCommodity',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 将推荐新品批量修改为不推荐
 * @returns 
 */
export const batchNoRecommendationCommodity = async (data: any) => {
    return await Axios<resType>({
        url: '/commodity/batchNoRecommendationCommodity',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 批量进货
 * @returns 
 */
export const bulkPurchaseCommodity = async (data: any) => {
    return await Axios<resType>({
        url: '/commodity/bulkPurchaseCommodity',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 批量进货
 * @returns 
 */
export const putInRecycleBinCommodity = async (data: any) => {
    return await Axios<resType>({
        url: '/commodity/putInRecycleBinCommodity',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 批量导入商品数据
 * @returns 
 */
export const uploadBatchCommodity = async (data: any) => {
    return await Axios<resType>({
        url: '/commodity/uploadBatchCommodity',
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
 * 分页条件查询购物车
 * @returns 
 */
export const getAllCart = async (data: any) => {
    return await Axios<resType>({
        url: '/commodity/getAllCartByPage',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 分页条件查询商品库存信息
 * @returns 
 */
export const getCommodityStockPageList = async (data: any) => {
    return await Axios<resType>({
        url: '/commodity/stock/getCommodityStockPage',
        method: 'post',
        data
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 出库记录详情
 * @returns 
 */
export const getCommodityStockDetail = async (orderNumber: any) => {
    return await Axios<resType>({
        url: '/commodity/stock/getCommodityStockDetail/' + orderNumber,
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 商品详情
 * @returns 
 */
export const getCommodityDetail = async (commodityId: any) => {
    return await Axios<resType>({
        url: '/commodity/getCommodityDetail/' + commodityId,
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 商品分类情况统计
 * @returns 
 */
export const getClassificationSituation = async () => {
    return await Axios<resType>({
        url: '/commodity/getClassificationSituation',
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 商品评论
 * @returns 
 */
export const getCommodityCommentById = async (commodityId: any) => {
    return await Axios<resType>({
        url: '/commodity/getCommodityCommentById/' + commodityId,
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 数据可视化大屏获取商品列表
 * @returns 
 */
export const getCommodityDataVList = async () => {
    return await Axios<resType>({
        url: '/commodity/getCommodityDataVList',
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}

/**
 * 数据可视化大屏获取商品分类占比
 * @returns 
 */
export const getClassificationCommodityProportion = async () => {
    return await Axios<resType>({
        url: '/commodity/getClassificationCommodityProportion',
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}
