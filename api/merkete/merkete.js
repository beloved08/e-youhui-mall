/**
 * 获取商家优惠券
 */
export const selectAllMerchantCoupon = async () => {
	return await uni.$http.get('/marketing/selectAllMerchantCoupon')
}

/**
 * 获取通用优惠券
 */
export const selectUniversalCoupon = async () => {
	return await uni.$http.get('/marketing/selectUniversalCoupon')
}

/**
 * 发送促销申请验证码
 */
export const sendNationalPromotionPeopleCode = async (phone) => {
	return await uni.$http.get('/marketing/sendNationalPromotionPeopleCode/' + phone)
}

/**
 * 发送促销申请
 */
export const verifyNationalPromotion = async (phone,code,userId) => {
	return await uni.$http.get('/marketing/verifyNationalPromotion/' + phone + "/" + code + "/" + userId)
}

/**
 * 检测当前用户是否提交全民促销申请
 */
export const getPromotionByUserId = async (userId) => {
	return await uni.$http.get('/marketing/getPromotionUserId/' + userId)
}

/**
 * 获取秒杀商品
 */
export const getLimitedTimeFlashKillCommodity = async () => {
	return await uni.$http.get('/marketing/getLimitedTimeFlashKillCommodity')
}

/**
 * 获取秒杀商品
 */
export const getTimeKillCommodityPage = async (currentPage,pageSize) => {
	return await uni.$http.get('/marketing/getTimeKillCommodityPage/' + currentPage + "/" + pageSize)
}

/**
 * 获取秒杀活动
 */
export const getProgressPromotionActivity = async () => {
	return await uni.$http.get('/marketing/getProgressPromotionActivity')
}

/**
 * 获取所有秒杀活动
 */
export const getAllPromotionActivity = async () => {
	return await uni.$http.get('/marketing/getAllPromotionActivity')
}

/**
 * 获取秒杀活动
 */
export const getPromotionActivityCommodityById = async (promotionActivityCommodityId) => {
	return await uni.$http.get('/marketing/getPromotionActivityCommodityById/' + promotionActivityCommodityId)
}

/**
 * 生成分享二维码
 */
export const getPromotionActivityShareQr = async (userId,promotionActivityId) => {
	return await uni.$http.get('/marketing/getPromotionActivityShareQr/' + userId + "/" + promotionActivityId)
}
