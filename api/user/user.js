/**
 * 前后端网络请求测试
 */
export const test = async () => {
	return await uni.$http.get('/test/start')
}

/**
 * 微信快捷登录接口
 */
export const wxQuickLogin = (data) => {
	return uni.$http.post('/jurisdiction/wx/wxQuickLogin', data)
}

/**
 * 账号密码注册时发送验证码
 */
export const wxAccountPwdRegisterSendCode = (data) => {
	return uni.$http.get('/jurisdiction/wx/wxAccountPwdRegisterSendCode/' + data)
}

/**
 * 校验账号密码注册验证码
 */
export const checkWxRegisterCode = (data) => {
	return uni.$http.get('/jurisdiction/wx/checkWxRegisterCode', data)
}

/**
 * 账号密码注册
 */
export const wxAccountPwdRegister = (data) => {
	return uni.$http.post('/jurisdiction/wx/wxAccountPwdRegister', data)
}

/**
 * 账号密码登录
 */
export const wxAccountPwdLogin = (data) => {
	return uni.$http.post('/jurisdiction/wx/wxAccountPwdLogin', data)
}

/**
 * 手机号验证登录时发送验证码
 */
export const wxPhoneLoginSendCode = (data) => {
	return uni.$http.get('/jurisdiction/wx/wxPhoneLoginSendCode/' + data)
}

/**
 * 手机号验证登录
 */
export const wxPhoneCodeLogin = (data) => {
	return uni.$http.get('/jurisdiction/wx/wxPhoneCodeLogin', data)
}

/**
 * 绑定电话号码是发送验证码
 */
export const getWxBindPhoneCode = (data) => {
	return uni.$http.get('/jurisdiction/wx/getWxBindPhoneCode/' + data)
}

/**
 * wx绑定电话号码
 */
export const wxBindPhone = (phone, code, userId) => {
	return uni.$http.get('/jurisdiction/wx/wxBindPhone/' + phone + "/" + code + "/" + userId)
}

/**
 * 获取用户地址信息
 */
export const getUserAddress = (userId) => {
	return uni.$http.get('/user/getUserAddress/'  + userId)
}

/**
 * 新增收货地址
 */
export const userAddAddress = (data) => {
	return uni.$http.post(
		'/user/addUserAddress',
		data
		)
}

/**
 * 删除收货地址
 */
export const deleteUserAddress = (addressId,userId) => {
	return uni.$http.get(
		'/user/deleteUserAddress/' + addressId + '/' + userId
		)
}

/**
 * 编辑收货地址
 */
export const editUserAddress = (data) => {
	return uni.$http.post(
		'/user/editUserAddress',
		data
		)
}

/**
 * 通过ID获取地址信息
 */
export const getAddressById = (adressId) => {
	return uni.$http.get('/user/getAddressById/'  + adressId)
}


/**
 * 添加用户收藏（商品、商家收藏）
 */
export const addUserCollect = (data) => {
	return uni.$http.post(
		'/user/addUserCollect',
		data
		)
}

/**
 * 获取用户收藏数
 */
export const getUserCollectCount = (userId) => {
	return uni.$http.get('/user/getCollectCount/'  + userId)
}

/**
 * 获取用户收藏信息
 */
export const getUserCollectList = (userId) => {
	return uni.$http.get('/user/getUserCollectList/'  + userId)
}

/**
 * 获取用户领取优惠券数量
 */
export const getUserCouponCount = (userId) => {
	return uni.$http.get('/user/getUserCouponCount/'  + userId)
}

/**
 * 获取用户领取的优惠券信息
 */
export const getUserCouponList = (userId) => {
	return uni.$http.get('/user/getUserCouponList/'  + userId)
}

/**
 * 用户领取优惠券
 */
export const drawCoupon = (data) => {
	return uni.$http.post('/user/drawCoupon', data)
}

/**
 * 用户购买会员
 */
export const userPurchaseVip = (userId) => {
	return uni.$http.get('/user/userPurchaseVip/'  + userId)
}

/**
 * 检查该用户是否收藏该商家
 */
export const checkUserIsCollectBusiness = (userId,businessId) => {
	return uni.$http.get('/user/checkUserIsCollectBusiness/'  + userId + "/" + businessId)
}

/**
 * 用户取消收藏
 */
export const cancelCollect = (collectId) => {
	return uni.$http.get('/user/cancelCollect/'  + collectId)
}
