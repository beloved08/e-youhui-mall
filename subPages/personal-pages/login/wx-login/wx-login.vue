<template>
	<view class="container">
		<view class="back-box"></view>
		<view class="title-box">
			<view class="title-main">
				<text>登录</text>
			</view>
			<view class="title-desc">
				<text>欢迎来到E优汇商城</text>
			</view>
		</view>
		<view class="top-box">
			<view class="login-form">
				<view class="login-form-top">
					<text>微信快捷登录</text>
				</view>
				<view class="login-form-box">
					<view class="login-button">
						<u-button type="primary" shape="circle" @click="popupShow = true" text="微信用户一键登录"></u-button>
					</view>
				</view>
			</view>
		</view>
		<view class="login-type">
			<u-divider text="其他登录方式" textSize="16"></u-divider>
			<view class="type-box">
				<view class="login-item" @click="toPhoneLogin()">
					<view class="login-item-icon">
						<tui-icon name="voipphone" :size="28" color="#577ee3"></tui-icon>
					</view>
					<view class="login-item-text">
						<text>手机验证登录</text>
					</view>
				</view>
				<view class="login-item" @click="toWxLogin()">
					<view class="login-item-icon">
						<tui-icon name="wechat" :size="32" color="#577ee3"></tui-icon>
					</view>
					<view class="login-item-text">
						<text>微信快捷登录</text>
					</view>
				</view>
				<view class="login-item" @click="toAccountPwdLogin()">
					<view class="login-item-icon">
						<tui-icon name="pwd" :size="26" color="#577ee3"></tui-icon>
					</view>
					<view class="login-item-text">
						<text>账号密码登录</text>
					</view>
				</view>
			</view>
		</view>
		<view>
			<tui-bottom-popup backgroundColor="#fff" zIndex="9999" :height="700" :show="popupShow">
				<view class="u-popup-box">
					<view class="u-popup-header">
						<view class="header-logo">
							<image src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/logo/logo.png"
								style="width: 100%;height: 100%; border-radius: 50%;"></image>
						</view>
						<view class="header-name">
							<text>E优汇商城</text>
						</view>
					</view>
					<view class="u-popup-content">
						<view class="u-popup-content-title">申请获取以下权限</view>
						<view class="u-popup-content-desc">获取您的公开信息（昵称、头像、性别等）</view>
					</view>
					<view class="u-popup-view">
						<checkbox-group @change="checkboxChange">
							<checkbox value="agree" style="transform:scale(0.7)" />我已阅读并同意<text @click="toAgreement()"
								class="text-agreement">《用户服务协议》</text>以及<text class="text-agreement">《隐私政策》</text>
						</checkbox-group>
					</view>
					<view class="u-popup-buttom">
						<tui-button type="white" width="220rpx" height="80rpx" @click="refuse()">拒绝</tui-button>
						<tui-button type="primary" width="220rpx" height="80rpx" @click="allow()">允许</tui-button>
					</view>
				</view>
			</tui-bottom-popup>
		</view>
		<view class="">
			<tui-modal :show="bindPhoneModel" :maskClosable="false" custom>
				<view class="tui-modal-custom">
					<view class="">绑定手机号码</view>
					<view class="phone-modal">
						<view class="">
							<u--input placeholder="请输入手机号码" placeholderStyle="font-size: 11px;" maxlength="11"
								v-model="phone"></u--input>
						</view>
						<view class="" style="margin-left: 10rpx;">
							<tui-button type="primary" size="20" width="150rpx" height="70rpx" @click="getPhoneCode">
								获取验证码</tui-button>
						</view>
					</view>
					<view class="code">
						<u--input style="height: 20px;" placeholder="请输入验证码" placeholderStyle="font-size: 11px;"
							maxlength="11" v-model="phoneCode"></u--input>
					</view>
					<view class="phone-modal-button" style="margin-top: 70rpx;">
						<tui-button height="70rpx" width="230rpx" :size="28" type="gray" plain
							@click="bindPhoneModel = false">取消</tui-button>
						<tui-button height="70rpx" width="230rpx" :size="28" type="danger" plain
							@click="handleBindPhoneClick">确定</tui-button>
					</view>
				</view>
			</tui-modal>
		</view>
	</view>
</template>

<script>
	import {
		wxQuickLogin,
		getWxBindPhoneCode,
		wxBindPhone
	} from '@/api/user/user.js'
	import {
		mapState,
		mapActions
	} from 'vuex'
	export default {
		computed: {
			...mapState(['user'])
		},
		data() {
			return {
				popupShow: false,
				isAgreeUserInfo: false,
				isAgreementApply: false,
				loginToUrl: '',
				bindPhoneModel: false,
				phone: '',
				phoneCode: '',
				wxLoginResData: {}
			};
		},
		onReady() {
			this.loginToUrl = this.$Route.query.loginTo
		},
		methods: {
			...mapActions(['saveUserDetail']),
			checkboxChange(e) {
				if (e.detail.value.length == 0) {
					this.isAgreementApply = false
				} else if (e.detail.value.length == 1) {
					this.isAgreementApply = true
				}
			},
			toPhoneLogin() {
				uni.redirectTo({
					url: '/subPages/personal-pages/login/phone-login/phone-login?loginTo=' + this.loginToUrl
				})
			},
			toWxLogin() {
				uni.redirectTo({
					url: '/subPages/personal-pages/login/wx-login/wx-login?loginTo=' + this.loginToUrl
				})
			},
			toAccountPwdLogin() {
				uni.redirectTo({
					url: '/subPages/personal-pages/login/account-pwd-login/account-pwd-login?loginTo=' + this
						.loginToUrl
				})
			},
			// 绑定嘛
			async handleBindPhoneClick() {
				let _this = this
				const {
					data: res
				} = await wxBindPhone(_this.phone, _this.phoneCode, _this.wxLoginResData.userId)
				if (res.code == 0) {
					_this.bindPhoneModel = false
					_this.wxLoginResData.phone = _this.phone
					const userDetail = {
						"isLogin": true,
						"userInfo": _this.wxLoginResData,
						"token": _this.wxLoginResData.accessToken
					}
					_this.saveUserDetail(userDetail)
					if (_this.loginToUrl == '/pages/personal/personal'  || _this.loginToUrl == '/pages/cart/cart' || _this.loginToUrl == '/pages/notice/notice') {
						uni.switchTab({
							url: _this.loginToUrl
						})
					} else {
						// 跳转目标页面
						uni.redirectTo({
							url: _this.loginToUrl
						})
					}
				}
			},
			async getPhoneCode() {
				if (this.phone.length == 11) {
					const {
						data: res
					} = await getWxBindPhoneCode(this.phone)
					if (res.code == 0) {
						uni.showToast({
							title: res.msg,
							icon: 'success'
						})
					} else {
						uni.showToast({
							title: res.msg,
							icon: 'error'
						})
					}
				} else {
					uni.showToast({
						title: '输入格式错误',
						icon: 'error'
					})
					this.phone = ''
				}
			},
			wxLogin() {
				let _this = this
				uni.getProvider({
					service: 'oauth',
					success: function(res) {
						uni.login({
							provider: res.provider[0],
							success: function(loginRes) {
								// 获取用户信息
								uni.getUserInfo({
									provider: res.provider[0],
									success: async function(infoRes) {
										const sendDataInfo = {
											code: loginRes.code,
											rawData: infoRes.rawData,
											signature: infoRes.signature,
										}
										const {
											data: response
										} = await wxQuickLogin(sendDataInfo)
										if (response.code == 0) {
											// 校验绑定的验证码，判断是否已经绑定电话号码
											if (response.data.isBindPhone == 0) {
												// 没有绑定过
												_this.bindPhoneModel = true
												_this.wxLoginResData = response.data
											}else{
												const userDetail = {
													"isLogin": true,
													"userInfo": response.data,
													"token": response['data']['accessToken']
												}
												_this.saveUserDetail(userDetail)
												if (_this.loginToUrl == '/pages/personal/personal'  || _this.loginToUrl == '/pages/cart/cart') {
													uni.switchTab({
														url: _this.loginToUrl
													})
												} else {
													// 跳转目标页面
													uni.redirectTo({
														url: _this.loginToUrl
													})
												}
											}
											
										} else {
											uni.showToast({
												title: response.msg,
												icon: 'error'
											})
										}
									}
								})
							}
						})
					}
				})
			},
			refuse() {
				uni.showToast({
					title: '您取消了授权',
					icon: 'error'
				})
				this.popupShow = false
			},
			allow() {
				if (this.isAgreementApply) {
					this.wxLogin()
					this.popupShow = false
				} else {
					uni.showToast({
						title: '请同意服务协议',
						icon: 'error'
					})
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
	.container {
		width: 100%;
		height: 100vh;
		display: flex;
		flex-direction: column;
		background-color: #fff;

		.back-box {
			width: 100%;
			height: 25%;
			background-color: #577ee3;
		}

		.title-box {
			position: absolute;
			top: 55rpx;
			width: 100%;

			.title-main {
				margin-left: 40rpx;
				font-size: 25px;
				font-weight: bold;
				color: #fff;
			}

			.title-desc {
				margin-top: 10rpx;
				margin-left: 40rpx;
				font-size: 14px;
				color: #fff;
			}
		}

		.top-box {
			position: absolute;
			top: 20%;
			width: 100%;
			z-index: 99;
			border-radius: 80rpx 80rpx 0 0;
			background-color: #fff;

			.login-form {
				display: flex;
				flex-direction: column;
				width: 80%;
				margin: 0 auto;
				margin-top: 120rpx;

				.login-form-top {
					font-size: 14px;
					font-weight: 700;
					color: #577ee3;
				}

				.login-form-box {
					width: 100%;
					margin-top: 30rpx;

					.form-item-one {
						width: 100%;
					}

					.form-item-two {
						display: flex;
						flex-direction: row;
						justify-content: space-around;
						width: 100%;
						margin-top: 30rpx;

						.form-item-input {
							width: 70%;
						}

						.form-item-button {
							width: 30%;
						}
					}

					.login-button {
						width: 95%;
						margin: 0 auto;
						margin-top: 70rpx;
					}
				}
			}
		}

		.login-type {
			position: fixed;
			bottom: 20px;
			width: 100%;
			z-index: 999;

			.type-box {
				display: flex;
				flex-direction: row;
				justify-content: space-around;
				width: 90%;
				margin: 0 auto;

				.login-item {
					width: 80px;
					height: 60px;
					display: flex;
					flex-direction: column;
					align-items: center;

					.login-item-text {
						margin-top: 10rpx;
						font-size: 12px;
						text-align: center;
					}
				}
			}
		}

		.phone-modal {
			display: flex;
			flex-direction: row;
			margin-top: 30rpx;
		}

		.code {
			margin-top: 20rpx;
			height: 20px;
		}

		.phone-modal-button {
			display: flex;
			flex-direction: row;
			justify-content: space-around;
		}

		.u-popup-box {
			width: 90%;
			margin: 0 auto;
			padding: 50rpx;
			display: flex;
			flex-direction: column;
			background-color: #fff;

			.u-popup-header {
				display: flex;
				flex-direction: row;

				.header-logo {
					width: 30px;
					height: 30px;
				}

				.header-name {
					margin-left: 30rpx;
					height: 30px;
					line-height: 30px;
					font-size: 14px;
					font-weight: bold;
				}
			}

			.u-popup-content {
				width: 100%;
				margin-top: 80rpx;
				display: flex;
				flex-direction: column;

				.u-popup-content-title {
					font-size: 15px;
					font-weight: bold;
				}

				.u-popup-content-desc {
					font-size: 13px;
					margin-top: 40rpx;
				}
			}

			.u-popup-view {
				width: 95%;
				margin-top: 60rpx;
				font-size: 12px;

				.text-agreement {
					color: #0877ff;
				}
			}

			.u-popup-buttom {
				width: 100%;
				margin-top: 80rpx;
				display: flex;
				flex-direction: row;
				justify-content: space-around;
			}
		}
	}
</style>
