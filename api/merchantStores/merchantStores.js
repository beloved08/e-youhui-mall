/**
 * 上传商家店铺logo
 */
export const uploadLogo = (data) => {
	return uni.$http.post('/merchantStores/wx/uploadLogo/' + data)
}

/**
 * 商家入驻
 */
export const addBusiness = (data) => {
	return uni.$http.post(
		'/merchantStores/wx/addBusiness',
		data
	)
}

/**
 * 获取商家入驻信息
 */
export const getMerchantStoresList = (data) => {
	return uni.$http.get('/merchantStores/wx/getBusinessByUserId/' + data)
}

/**
 * 通过商家ID获取商家信息
 */
export const getBusinessById = (businessId) => {
	return uni.$http.get('/merchantStores/wx/getBusinessById/' + businessId)
}
