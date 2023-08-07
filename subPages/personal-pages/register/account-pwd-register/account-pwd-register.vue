<template>
	<view class="container">
		<view class="back-box"></view>
		<view class="title-box">
			<view class="title-main">
				<text>注册</text>
			</view>
			<view class="title-desc">
				<text>欢迎来到E优汇商城</text>
			</view>
		</view>
		<view class="top-box">
			<view class="login-form">
				<view class="login-form-top">
					<text>账号密码注册</text>
				</view>
				<view class="login-form-box">
					<u--form labelPosition="left" :model="registerForm" ref="regForm">
						<u-form-item prop="phone">
							<view class="form-item-one">
								<u--input placeholder="请输入手机号" maxlength="11" prefixIcon="phone"
									prefixIconStyle="font-size: 20px;color: #d6d6d6" border="bottom"
									v-model="registerForm.phone"></u--input>
							</view>
						</u-form-item>
						<u-form-item prop="password">
							<view class="form-item-pwd">
								<u--input placeholder="请输入密码" type="password" prefixIcon="lock"
									prefixIconStyle="font-size: 20px;color: #d6d6d6" border="bottom"
									v-model="registerForm.password"></u--input>
							</view>
						</u-form-item>
						<u-form-item prop="rePassword">
							<view class="form-item-pwd2">
								<u--input placeholder="请再次输入密码" type="password" prefixIcon="lock-open"
									prefixIconStyle="font-size: 20px;color: #d6d6d6" border="bottom"
									v-model="registerForm.rePassword"></u--input>
							</view>
						</u-form-item>
						<u-form-item prop="code">
							<view class="form-item-two">
								<view class="form-item-input">
									<u--input placeholder="请输入验证码" maxlength="6" border="bottom" prefixIcon="clock"
										prefixIconStyle="font-size: 20px;color: #d6d6d6" v-model="registerForm.code">
									</u--input>
								</view>
								<view class="form-item-button">
									<u-button type="primary" size="small" shape="circle" :plain="true" text="获取验证码"
										@click="getCode()"></u-button>
								</view>
							</view>
						</u-form-item>
						<u-form-item prop="userInfo.name" ref="item1">
							<view class="login-button">
								<u-button type="primary" shape="circle" text="立即注册" @click="nowRegister()"></u-button>
							</view>
						</u-form-item>
					</u--form>
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
	</view>
</template>

<script>
	import {
		wxAccountPwdRegisterSendCode,
		checkWxRegisterCode,
		wxAccountPwdRegister
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
				registerForm: {
					phone: '',
					password: '',
					rePassword: '',
					code: ''
				},
				rules: {
					'phone': [{
							type: 'string',
							required: true,
							len: 11,
							message: '请填写11位手机号码',
							trigger: ['change', 'blur']
						},
						{
							pattern: /^[0-9]*$/g,
							transform(value) {
								return String(value);
							},
							message: '只能为数字',
							trigger: ['change', 'blur']
						}
					],
					'password': [{
						type: 'string',
						required: true,
						message: '请填写密码',
						trigger: ['change', 'blur']
					}, {
						max: 20,
						min: 5,
						message: '密码为5-20位',
						trigger: ['change', 'blur']
					}],
					'rePassword': [{
						type: 'string',
						max: 20,
						min: 5,
						required: true,
						message: '请再次填写密码',
						trigger: ['blur', 'change']
					}, {
						max: 20,
						min: 5,
						message: '密码为5-20位',
						trigger: ['change', 'blur']
					}, {
						asyncValidator: (rule, value, callback) => {
							if (this.registerForm.password == value) {
								callback();
							} else {
								callback(new Error("两次密码输入不一致"));
							}
						},
					}],
					'code': [{
						type: 'number',
						required: true,
						message: '请填写验证码',
						trigger: ['blur', 'change']
					}, {
						max: 6,
						min: 6,
						min: 6,
						message: '验证码为6位',
						trigger: ['change', 'blur']
					}],
				},
				loginToUrl: ''
			};
		},
		onReady() {
			this.loginToUrl = this.$Route.query.loginTo
			this.$refs.regForm.setRules(this.rules)
		},
		methods: {
			...mapActions(['saveUserDetail']),
			toPhoneLogin() {
				uni.navigateTo({
					url: '/subPages/personal-pages/login/phone-login/phone-login'
				})
			},
			toWxLogin() {
				uni.navigateTo({
					url: '/subPages/personal-pages/login/wx-login/wx-login'
				})
			},
			toAccountPwdLogin() {
				uni.navigateTo({
					url: '/subPages/personal-pages/login/account-pwd-login/account-pwd-login'
				})
			},
			// 获取验证码
			async getCode() {
				const {
					data: res
				} = await wxAccountPwdRegisterSendCode(this.registerForm.phone)
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
			},
			// 立即注册按钮事件
			nowRegister() {
				let _this = this
				this.$refs.regForm.validate().then(async res => {
					// 校验验证码是否正确
					const {
						data: r
					} = await checkWxRegisterCode({
						"phone": _this.registerForm.phone,
						"code": _this.registerForm.code,
					})
					if (r.code == 0) {
						const {
							data: response
						} = await wxAccountPwdRegister(_this.registerForm)
						if (response.code == 0) {
							uni.showToast({
								title: '注册成功',
								icon: 'success'
							})
							const userDetail = {
								"isLogin": true,
								"userInfo": response['data'],
								"token": response['data']['accessToken']
							}
							_this.saveUserDetail(userDetail)
							if (_this.loginToUrl == '/pages/personal/personal') {
								uni.switchTab({
									url: _this.loginToUrl
								})
							} else {
								// 跳转目标页面
								uni.redirectTo({
									url: _this.loginToUrl
								})
							}
						} else {
							uni.$u.toast(response.msg)
						}
					} else {
						uni.$u.toast(r.msg)
					}
				}).catch(errors => {
					uni.$u.toast(errors[0].message)
				})
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
				margin-top: 50rpx;

				.login-form-top {
					font-size: 14px;
					font-weight: 700;
					color: #577ee3;
				}

				.login-form-box {
					width: 100%;
					margin-top: 10rpx;

					.form-item-one {
						width: 100%;
					}

					.form-item-pwd {
						width: 100%;
						// margin-top: 30rpx;
					}

					.form-item-pwd2 {
						width: 100%;
						// margin-top: 30rpx;
					}

					.form-item-two {
						display: flex;
						flex-direction: row;
						justify-content: space-around;
						width: 100%;
						// margin-top: 30rpx;

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
						// margin-top: 70rpx;
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
	}
</style>
