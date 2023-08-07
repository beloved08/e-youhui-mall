var url;
if (process.env.NODE_ENV === 'development') {
	// 开发环境
	// url = 'http://localhost:10010'
	url = 'https://eyh-shop.com'
} else {
	// 生成环境
	url = 'https://www.uinav.com'
}
export const baseUrl = url
