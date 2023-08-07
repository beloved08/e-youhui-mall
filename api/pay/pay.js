/**
 * 用户充值余额
 */
export const userBalanceRecharge = (data) => {
	return uni.$http.post('/pay/userBalanceRecharge', data)
}

/**
 * 获取用户可用余额
 */
export const getUserBalanceNumber = (userId) => {
	return uni.$http.get('/pay/getUserBalanceNumber/' + userId)
}

/**
 * 获取用户可用余额变动明细
 */
export const getUserBalanceChangeDetail = (userId,type) => {
	return uni.$http.get('/pay/getUserBalanceChangeDetail/' + userId + '/' + type)
}

/**
 * 获取用户可用积分
 */
export const getUserIntegralNumber = (userId) => {
	return uni.$http.get('/pay/getUserIntegralNumber/' + userId)
}

/**
 * 获取用户可用积分变动明细
 */
export const getUserIntegralChangeDetail = (userId,type) => {
	return uni.$http.get('/pay/getUserIntegralChangeDetail/' + userId + '/' + type)
}

/**
 * 创建订单
 */
export const createOrder = (order) => {
	return uni.$http.post('/pay/order/createOrder', order)
}

/**
 * 获取待付款订单
 */
export const getPendingPaymentOrderById = (orderId) => {
	return uni.$http.get('/pay/order/getPendingPaymentOrderById/' + orderId)
}

/**
 * 待付款页面继续付款按钮事件
 */
export const pendingPaymentContinue = (order) => {
	return uni.$http.post('/pay/order/pendingPaymentContinue',order)
}

/**
 * 获取待付款订单
 */
export const getPaymentCompletedOrderById = (orderId) => {
	return uni.$http.get('/pay/order/getPaymentCompletedOrderById/' + orderId)
}

/**
 * 获取用户所有订单
 */
export const getUserOrderList = (userId) => {
	return uni.$http.get('/pay/order/getUserOrder/' + userId)
}

/**
 * 获取订单详情
 */
export const getOrderDetailByNumber = (orderNumber) => {
	return uni.$http.get('/pay/order/getOrderDetailByNumber/' + orderNumber)
}

/**
 * 用户确认收货
 */
export const userConfirmReceipt = (expressOrderId,orderNumber) => {
	return uni.$http.get('/pay/order/userConfirmReceipt/' + expressOrderId + "/" + orderNumber)
}
