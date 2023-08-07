/**
 * 获取商品分类数据
 */
export const getCommodityClassificationList = (data) => {
	return uni.$http.post(
			'/commodity/getClassificationByPage',
			data
	)
}

/**
 * 通过分类id获取商品数据
 */
export const getCommodityListByClassification = (data) => {
	return uni.$http.get(
			'/commodity/getCommodityByClassificationId/' + data
	)
}

/**
 * 通过分类id获取商品、商家等详细数据
 */
export const getCommodityDetail = (data) => {
	return uni.$http.get(
			'/commodity/getCommodityDetail/' + data
	)
}

/**
 * 添加购物车
 */
export const addCart = (data) => {
	return uni.$http.post(
			'/commodity/addCart',
			data
	)
}

/**
 * 移除购物车
 */
export const deleteCart = (data) => {
	return uni.$http.post(
			'/commodity/deleteCart',
			data
	)
}
/**
 * 添加商品评论
 */
export const addCommodityComment = (data) => {
	return uni.$http.post(
			'/commodity/addCommodityComment',
			data
	)
}

/**
 * 获取新品推荐商品
 */
export const getCommodityRecommend = () => {
	return uni.$http.get('/commodity/getRecommendCommodity')
}

/**
 * 获取轮播图商品
 */
export const getRotationChartCommodity = () => {
	return uni.$http.get('/commodity/getRotationChartCommodity')
}

/**
 * 获取热卖商品
 */
export const getBestSellersCommodity = () => {
	return uni.$http.get('/commodity/getBestSellersCommodity')
}

/**
 * 获取首页展示的商品
 */
export const getIndexShowCommodity = (currentPage,pageSize) => {
	return uni.$http.get('/commodity/getIndexShowCommodity/' + currentPage + "/" + pageSize)
}

/**
 * 获取用户购物车数据
 */
export const getUserCart = (userId) => {
	return uni.$http.get('/commodity/getUserCart/' + userId)
}
/**
 * 商品搜索
 */
export const searchCommodity = (commodityName) => {
	return uni.$http.get('/commodity/searchCommodity/' + commodityName)
}

/**
 * 获取某商家所有商品数据
 */
export const getCommodityByBusinessIdPage = (businessId,currentPage,pageSize) => {
	return uni.$http.get('/commodity/getCommodityByBusinessIdPage/' + businessId + "/" + currentPage + "/" + pageSize)
}