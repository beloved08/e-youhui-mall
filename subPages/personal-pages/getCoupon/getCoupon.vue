<template>
	<view class="container">
		<!-- <view class="balance-top">
			<view class="balance">
				
			</view>
		</view> -->
		<view class="coupon-box">
			<view class="coupon-tab">
				<tui-tabs :tabs="tabs" :currentTab="currentTab" bottom="10" sliderBgColor="#fff" color="#fff" size="24"
					selectedColor="#ff0000" sliderHeight="60" sliderWidth="180" backgroundColor="#ff0000"
					itemWidth="50%" @change="couponTabChange"></tui-tabs>
			</view>
			<view class="coupon-list" v-if="currentTab == 0 ? true : false">
				<view class="coupon-list-item" v-for="(item,index) in universalCouponList" :key="index">
					<view class="coupon-left">
						<view class="left-title">
							<text>{{item.universalCouponName}}</text>
						</view>
						<view class="commodity-list">
							<view class="commodity-list-item" v-for="(it,i) in item.commodityList" :key="i">
								<view class="commodity-image">
									<image
										:src="it.commodityImageUrl"
										style="width: 100%;height: 100%; border-radius: 10rpx;"></image>
								</view>
								<view class="commodity-price">
									<text>￥</text>
									<text v-if="it.salesModel == 0">{{it.retailPrice}}</text>
									<text v-else>{{it.wholesalePrice}}</text>
								</view>
							</view>
						</view>
					</view>
					<view class="coupon-right">
						<view class="coupon-top">
							<view class="title">
								<text>无门槛券</text>
							</view>
						</view>
						<view class="coupon-right-desc">
							<view class="desc-one">
								满{{item.fullAvailable}}
							</view>
							<view class="desc-two">
								减{{item.discountAmount}}
							</view>
						</view>
						<view class="coupon-bottom">
							<u-button text="立即领取" size="mini" @click="drawUserCoupon(item,0)"
								color="linear-gradient(to right, rgb(255, 0, 0), rgb(255, 0, 0))"></u-button>
						</view>
					</view>
				</view>
			</view>
			<view class="coupon-list" v-else>
				<view class="coupon-list-item" v-for="(item,index) in merchantCouponList" :key="index">
					<view class="coupon-left">
						<view class="left-title">
							<text>{{item.businessName}}{{item.merchantCouponName}}</text>
						</view>
						<view class="commodity-list">
							<view class="commodity-list-item" v-for="(it,i) in item.commodityList" :key="i">
								<view class="commodity-image">
									<image
										:src="it.commodityImageUrl"
										style="width: 100%;height: 100%; border-radius: 10rpx;"></image>
								</view>
								<view class="commodity-price">
									<text>￥</text>
									<text v-if="it.salesModel == 0">{{it.retailPrice}}</text>
									<text v-else>{{it.wholesalePrice}}</text>
								</view>
							</view>
						</view>
					</view>
					<view class="coupon-right">
						<view class="coupon-top">
							<view class="title">
								<text>无门槛券</text>
							</view>
						</view>
						<view class="coupon-right-desc">
							<view class="desc-one">
								满{{item.fullAvailable}}
							</view>
							<view class="desc-two">
								减{{item.discountAmount}}
							</view>
						</view>
						<view class="coupon-bottom">
							<u-button text="立即领取" size="mini" @click="drawUserCoupon(item,1)"
								color="linear-gradient(to right, rgb(255, 0, 0), rgb(255, 0, 0))"></u-button>
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import { selectAllMerchantCoupon,selectUniversalCoupon } from '@/api/merkete/merkete.js'
	import { drawCoupon } from '@/api/user/user.js'
	
	export default {
		data() {
			return {
				// 商家优惠券列表
				merchantCouponList: [],
				// 通用优惠券列表
				universalCouponList: [],
				currentTab: 0,
				tabs: [{
					name: "通用优惠券"
				}, {
					name: "商家优惠券"
				}]
			}
		},
		async onLoad() {
			const { data:resMerchantCoupon } = await selectAllMerchantCoupon()
			if(resMerchantCoupon.code == 0){
				this.merchantCouponList = resMerchantCoupon.data
			}
			const { data:resUniversalCoupon } = await selectUniversalCoupon()
			if(resUniversalCoupon.code == 0){
				this.universalCouponList = resUniversalCoupon.data
			}
		},
		methods: {
			couponTabChange(e) {
				this.currentTab = e.index
			},
			async drawUserCoupon(item,type){
				const coupon = {
					"type": type,
					"userId": uni.getStorageSync('LOGIN_USER_DETAIL') != null ? uni.getStorageSync('LOGIN_USER_DETAIL').userInfo.userId : '',
					"couponId": ''
				}
				if(type == 0){
					coupon.couponId = item.universalCouponId
				}else{
					coupon.couponId = item.merchantCouponId
				}
				
				const { data: res } = await drawCoupon(coupon)
				if(res.code == 0){
					uni.showToast({
						title: res.data,
						icon:'none'
					})
				}else{
					uni.showToast({
						title: res.msg,
						icon:'none'
					})
				}
			}
		}
	}
</script>

<style lang="scss">
	page{
		background-color: #ff0000;
	}
	.container {
		width: 100%;
		background-color: #f6f6f6;
		display: flex;
		flex-direction: column;

		.balance-top {
			display: flex;
			flex-direction: column;
			width: 95%;
			z-index: 999;
			background-color: #fff;
			border-radius: 10rpx;
			margin: 0 auto;
			margin-top: 30rpx;

			.balance {
				display: flex;
				flex-direction: row;
				margin: 0 auto;
				margin-top: 40rpx;

			}

		}

		.coupon-box {
			width: 100%;
			margin: 0 auto;
			background-color: #ff0000;
			display: flex;
			flex-direction: column;

			.coupon-tab {
				width: 100%;
			}

			.coupon-list {
				width: 90%;
				margin: 0 auto;
				margin-top: 20rpx;
				display: flex;
				flex-direction: column;

				.coupon-list-item {
					width: 100%;
					margin: 0 auto;
					display: flex;
					flex-direction: row;
					margin-bottom: 20rpx;

					.coupon-left {
						width: 70%;
						background-color: #fff;
						border-radius: 10rpx;
						display: flex;
						flex-direction: column;

						.left-title {
							font-size: 13px;
							font-weight: bold;
							margin-top: 10rpx;
							margin-left: 20rpx;
						}

						.commodity-list {
							display: flex;
							flex-direction: row;
							margin-top: 10rpx;
							margin-left: 20rpx;

							.commodity-list-item {
								width: 35%;
								height: 80px;
								display: flex;
								flex-direction: column;
								margin-right: 10rpx;

								.commodity-image {
									width: 100%;
									height: 70%;
									background-color: #f6f6f6;
									border-radius: 10rpx;
								}

								.commodity-price {
									margin: 0 auto;
									margin-top: 10rpx;
									font-size: 12px;
									font-weight: 800;
									color: #ff0000;
								}
							}
						}
					}

					.coupon-right {
						width: 30%;
						background-color: #fff;
						border-radius: 10rpx;
						border-left: 1rpx solid #f6f6f6;
						display: flex;
						flex-direction: column;

						.coupon-top {
							width: 80%;
							margin: 0 auto;
							border-radius: 0 0 15rpx 15rpx;
							background-color: #feca4f;

							.title {
								font-size: 11px;
								color: #836627;
								text-align: center;
								margin-top: 5rpx;
								margin-bottom: 10rpx;
							}
						}

						.coupon-right-desc {
							margin: 0 auto;
							margin-top: 20rpx;
							display: flex;
							flex-direction: column;

							.desc-one {
								font-size: 11px;
								color: #000;
							}

							.desc-two {
								font-size: 13px;
								color: #ff0000;
							}
						}

						.coupon-bottom {
							width: 80%;
							margin: 0 auto;
							margin-top: 20rpx;
							margin-bottom: 10rpx;
						}
					}
				}
			}
		}
	}
</style>
