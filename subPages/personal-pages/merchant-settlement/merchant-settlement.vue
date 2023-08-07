<template>
	<view>
		<view class="box" v-if="showItem ==0">
			<view class="title">
				开店啦
			</view>
			<view class="button">
				<tui-button type="primary" width="420rpx" height="80rpx" @click="startApply()">开始申请</tui-button>
			</view>
			<view class="agreement-box">
				<checkbox-group @change="checkboxChange">
					<checkbox value="agree" style="transform:scale(0.7)" />我已阅读并同意<text @click="toAgreement()"
						class="text-agreement">《E优汇商家入驻申请开店服务协议》</text>
				</checkbox-group>
			</view>
			<tui-tips ref="toast" position="center" backgroundColor="#f73859" color="#fff" :size="22"></tui-tips>
		</view>
	</view>
</template>

<script>
	import { getMerchantStoresList } from '@/api/merchantStores/merchantStores.js'
	import {
		mapState,
		mapActions,
		mapGetters
	} from 'vuex'
	
	export default {
		data() {
			return {
				merchantStoresDataList: [],
				// 用户是否同意商家入驻协议
				isAgreementApply: true,
				// 申请开店页面与申请表单切换控件
				isApply: false,
			}
		},
		mounted() {
			this.merchantStoresList()
		},
		methods: {
			// 获取当前登录用户的商家列表
			async merchantStoresList(){
				const userId = uni.getStorageSync('LOGIN_USER_DETAIL') != null ? uni.getStorageSync('LOGIN_USER_DETAIL').userInfo.userId : ''
				const { data:res } = await getMerchantStoresList(userId)
				if(res.code == 0){
					this.merchantStoresDataList = res.data
					if(this.merchantStoresDataList != null){
						uni.redirectTo({
							url: '/subPages/personal-pages/merchant-settlement/merchant-stores/merchant-stores'
						})
					}
				}
				if(res.data == null){
					uni.redirectTo({
						url:"/subPages/personal-pages/merchant-settlement/apply-form/apply-form"
					})
				}
			},
			// 是否同意商家入驻协议
			checkboxChange(e) {
				if (e.detail.value.length == 0) {
					this.isAgreementApply = false
				} else if (e.detail.value.length == 1) {
					this.isAgreementApply = true
				}
			},
			// 开始申请按钮事件
			startApply() {
				if (!this.isAgreementApply) {
					let options = {
						msg: '请勾选同意下方的E优汇商家入驻申请开店服务协议',
						duration: 2000
					};
					this.$refs.toast.showTips(options)
				}
				this.showItem = this.isAgreementApply ? 1 : 0
			},
			change(data) {
				this.addressValue = data.data.join('')
			},
			agreement() {
				this.isAgreement = false
			},
			noAgreement() {
				uni.navigateBack()
			},
			toAgreement() {
				uni.navigateTo({
					url: "/subPages/personal-pages/merchant-settlement/agreement/agreement"
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.box {
		display: flex;
		flex-direction: column;
		width: 100%;
		height: 100vh;
		background-color: #f6f6f6;
		align-items: center;

		.title {
			height: 60vh;
			font-size: 30px;
			font-weight: bold;
			margin-top: 200rpx;
		}

		.agreement-box {
			width: 85%;
			margin-top: 30rpx;
			font-size: 11px;

			.text-agreement {
				color: #0877ff;
			}
		}
	}

	.shop-box {
		width: 100%;
		display: flex;
		flex-direction: column;
		background-color: #f6f6f6;
		align-items: center;

		.apply-shop {
			width: 90%;
			background-color: #fff;
			padding: 15rpx;
			border-radius: 10rpx;
			margin-bottom: 30rpx;

			.apply-title {
				font-size: 15px;
				font-weight: bold;
			}

			.apply-from {
				width: 100%;
				margin: 0 auto;
				margin-top: 10rpx;

				.apply-from-item {
					display: flex;
					flex-direction: row;

					.address {
						margin-top: 10rpx;
					}

					.apply-from-textarea {
						margin-left: 10rpx;
					}
				}

				.apply-from-item text {
					font-size: 13px;
					margin-top: 25rpx;
					margin-right: 10rpx;
				}
			}
		}

		.button-box {
			width: 85%;
			background-color: #fff;
			margin: 30rpx;
		}
	}
	
	.ms-box{
		width: 100%;
		
	}
</style>
