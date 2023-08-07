<template>
	<view class="container">
		<view v-if="afterSalesOrder.length > 0">
			<view v-for="(item,index) in afterSalesOrder" :key="index" class="order-content-for">
				<tui-swipe-action :operateWidth="140">
					<template v-slot:content>
						<view class="order-all-box">
							<view class="order-top-shop">
								<view class="shop-logo">
									<image :src="item.business.businessLogo"
										style="width: 100%;height: 100%; border-radius: 50%;"></image>
								</view>
								<view class="order-top-title">
									<text>{{item.business.calloutContent}}</text>
								</view>
								<view class="shop-more">
									<tui-icon name="arrowright" color="#000" :size="20"></tui-icon>
								</view>
							</view>
							<view class="tui-divider">
								<tui-divider gradual height="30" width="90%"></tui-divider>
							</view>
							<view class="order-top">
								<view class="order-top-left">
									<text class="order-num">订单号</text>
									<text class="order-num">{{item.orderNumber}}</text>
								</view>
								<view class="order-top-right">
									<text class="order-status" v-if="item.orderStatus == 0">待付款</text>
									<text class="order-status" v-if="item.orderStatus == 1">待发货</text>
									<text class="order-status" v-if="item.orderStatus == 2">待收货</text>
									<text class="order-status" v-if="item.orderStatus == 3">已完成</text>
									<text class="order-status" v-if="item.orderStatus == 4">售后</text>
								</view>
							</view>
							<view class="order-content">
								<view class="order-content-image">
									<image :src="item.commodity.commodityImageUrl"
										style="width: 100%;height: 100%;border-radius: 6rpx;"></image>
								</view>
								<view class="order-content-desc">
									<view class="desc-title">
										<text>{{item.commodity.commodityName}}</text>
									</view>
									<view class="desc-class">{{item.commodity.commodityDescribe}}</view>
								</view>
								<view class="order-price-num">
									<view class="order-price">
										<text class="order-price-unit">￥</text>
										<text class="order-price-number"
											v-if="item.commodity.salesModel == 0">{{item.commodity.retailPrice}}</text>
										<text class="order-price-number" v-else>{{item.commodity.wholesalePrice}}</text>
									</view>
									<view class="order-num">
										<text class="order-num-unit">x</text>
										<text class="order-number">{{item.purchaseQuantity}}</text>
									</view>
								</view>
							</view>
							<view class="order-bottom">
								<tui-button @click="seeOrderDetail(item)" plain size="25" shape="circle" width="160rpx"
									height="50rpx" type="green">申请售后</tui-button>
							</view>
						</view>
					</template>
					<template v-slot:button>
						<view class="tui-custom-btn_box">
							<tui-button type="gray-primary" width="140rpx" height="100%" @click="collectOrder(item)">收藏
							</tui-button>
							<tui-button type="gray-danger" width="140rpx" height="100%" @click="deleteOrder(item)">删除
							</tui-button>
						</view>
					</template>
				</tui-swipe-action>
			</view>
		</view>
		<view v-else class="empty">
			<view class="empty-image">
				<image style="width: 100%;height: 100%;"
					src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/order/6.png" mode=""></image>
			</view>
			<view class="empty-title">
				<text>暂无售后订单</text>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			afterSalesOrder: {
				type: Array,
				default: () => []
			}
		},
		data() {
			return {

			}
		},
		methods: {
			seeOrderDetail(e) {
				// console.log(e)
				uni.makePhoneCall({
					phoneNumber: '15136754892' //仅为示例
				})
			},
			collectOrder(e) {
				uni.showToast({
					title: "点击收藏->" + e,
					icon: 'none'
				})
			},
			deleteOrder(e) {
				uni.showToast({
					title: "点击删除->" + e,
					icon: 'none'
				})
			}
		}
	}
</script>

<style lang="scss">
	.container {
		width: 100%;
		height: 100%;
		display: flex;
		flex-direction: column;
		background-color: #f6f6f6;

		.order-content-for {
			width: 95%;
			margin: 0 auto;
			margin-top: 20rpx;

			.tui-custom-btn_box {
				display: flex;
				flex-direction: row;
				height: 100%;
			}

			.order-all-box {
				display: flex;
				flex-direction: column;
				width: 100%;
				background-color: #fff;
				padding: 20rpx;
				border-radius: 20rpx;

				.order-top-shop {
					display: flex;
					flex-direction: row;

					.shop-logo {
						width: 40rpx;
						height: 40rpx;
						border-radius: 50%;
					}

					.order-top-title {
						font-size: 14px;
						font-weight: bold;
						margin-left: 10rpx;
					}
				}

				.tui-divider {
					margin-left: -20rpx;
				}

				.order-top {
					display: flex;
					flex-direction: row;
					justify-content: space-between;

					.order-top-left {
						display: flex;
						flex-direction: row;

						.order-num {
							font-size: 12px;
							margin-left: 10rpx;
						}
					}

					.order-top-right {
						margin-right: 20rpx;

						.order-status {
							font-size: 13px;
							color: red;
							margin-right: 30rpx;
						}
					}
				}

				.order-content {
					display: flex;
					flex-direction: row;
					margin-top: 20rpx;
					margin-left: 20rpx;
					// justify-content: space-between;
					justify-content: space-around;

					.order-content-image {
						width: 150rpx;
						height: 150rpx;
						margin-left: -30rpx;
					}

					.order-content-desc {
						width: 45%;
						margin-left: -40rpx;
						margin-top: 10rpx;
						display: flex;
						flex-direction: column;

						.desc-title {
							width: 100%;
							font-size: 13px;
							color: #000;
							word-break: break-all;
							overflow: hidden;
							text-overflow: ellipsis;
							white-space: nowrap;
						}

						.desc-class {
							width: 100%;
							font-size: 11px;
							color: #c1c1c1;
							word-break: break-all;
							overflow: hidden;
							text-overflow: ellipsis;
							white-space: nowrap;
						}
					}

					.order-price-num {
						display: flex;
						flex-direction: column;

						.order-price {
							color: red;
							margin-right: 20rpx;

							.order-price-unit {
								font-size: 12px;
							}

							.order-price-number {
								font-size: 13px;
								font-weight: bold;
							}
						}

						.order-num {
							color: #828282;
							margin-left: 30rpx;

							.order-num-unit {
								font-size: 11px;
							}

							.order-number {
								font-size: 12px;
								margin-left: 6rpx;
							}
						}

					}
				}

				.order-bottom {
					width: 90%;
					margin: 0 auto;
					margin-top: 10rpx;
					display: flex;
					flex-direction: row;
					justify-content: flex-end;
					z-index: 999;
				}
			}

		}

		.empty {
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			margin-top: 80rpx;

			.empty-image {
				width: 60px;
				height: 60px;
			}

			.empty-title {
				font-size: 14px;
			}
		}

	}
</style>
