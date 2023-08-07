/**
 * 获取用户订单物流详情
 */
export const getExpressOrderDetail = (orderNumber) => {
	return uni.$http.get('/logistics/getExpressOrderDetail/' + orderNumber)
}

/**
 * 获取用户订单物流轨迹详情
 */
export const getLogisticsTrackDetail = (expressOrderId) => {
	return uni.$http.get('/logistics/getLogisticsTrackDetail/' + expressOrderId)
}