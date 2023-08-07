<template>
	<view class="order-box">
		<view class="address-box">
			<view class="address-icon">
				<tui-icon color="#ff2e63" name="position"></tui-icon>
			</view>
			<view class="address-desc">
				<view class="detailedAddress">{{pendingPaymentOrder[0].userAddress.detailedAddress}}</view>
				<view class="consignee-phone">
					<text style="margin-right: 20rpx;">{{pendingPaymentOrder[0].userAddress.consignee}}</text>
					<text>{{pendingPaymentOrder[0].userAddress.phone}}</text>
				</view>
			</view>
		</view>
		<view style="">
			<view class="commodity-box" v-for="(item,index) in pendingPaymentOrder" :key="index">
				<view class="shop-box">
					<view class="logo">
						<image style="width: 100%;height: 100%;" :src="item.business.businessLogo" mode=""></image>
					</view>
					<view class="business-name">{{item.business.calloutContent}}</view>
				</view>
				<view class="commodity">
					<view class="commodity-image">
						<image style="width: 100%;height: 100%;" :src="item.commodity.commodityImageUrl" mode=""></image>
					</view>
					<view class="commodity-desc">{{item.commodity.commodityDescribe}}</view>
					<view class="commodity-price" v-if="item.commodity.salesModel == 0">
						<view class="">
							<text style="font-size: 9px;color: #ff0000;">￥</text>
							<text
								style="font-size: 12px;color: #ff0000;margin-left: 5rpx;">{{item.commodity.retailPrice}}</text>
						</view>
						<view style="margin-top: 10rpx;">{{item.purchaseQuantity}} {{item.commodity.meterCompany}}</view>
					</view>
					<view class="commodity-price" v-else>
						<view class="">
							<text style="font-size: 9px; color: #ff0000;">￥</text>
							<text
								style="font-size: 12px;color: #ff0000;margin-left: 5rpx;">{{item.commodity.wholesalePrice}}</text>
						</view>
						<view style="margin-top: 20rpx;">{{item.quantity}} {{item.commodity.meterCompany}}</view>
					</view>
				</view>
				<view class="delivery-service">
					<view class="delivery-service-left">配送服务</view>
					<view class="delivery-service-right">
						<text>快递 免邮</text>
						<text>现货，付款后48小时内发货</text>
					</view>
				</view>
				<view class="member-preferential" v-if="user.userInfo.type == 1">
					<view class="member-preferential-left">会员优惠</view>
					<view class="member-preferential-right">
						<text>全场商品9.5折</text>
					</view>
				</view>
				<view class="member-preferential">
					<view class="member-preferential-left">优惠券</view>
					<view class="member-preferential-right">
						<view>
							<text>- {{item.discountLimit}} 元</text>
						</view>
					</view>
				</view>
				<view class="order-remarks">
					<view class="order-remarks-left">订单备注</view>
					<view class="order-remarks-right">
						<text>{{item.orderRemarks}}</text>
					</view>
				</view>
			</view>
		</view>
		<view class="order-status">
			<view style="margin-bottom: 15rpx;">订单信息</view>
			<view class="order-detail">
				<view class="order-detail-item">
					<text>订单编号</text>
					<text>{{pendingPaymentOrder[0].orderNumber}}</text>
				</view>
				<view class="order-detail-item">
					<text>订单状态</text>
					<text>待发货</text>
				</view>
				<view class="order-detail-item">
					<text>快递运费</text>
					<text>￥{{pendingPaymentOrder[0].transportationExpenses}}.00 元</text>
				</view>
				<view class="order-detail-item">
					<text>创建时间</text>
					<text>{{pendingPaymentOrder[0].createTime}}</text>
				</view>
				<view class="order-detail-item">
					<text>付款时间</text>
					<text>{{pendingPaymentOrder[0].strikeBargainTime}}</text>
				</view>
			</view>
		</view>
		<view class="price-detail">
			<view class="price-detail-desc">
				<view class="price-detail-item">
					<text style="font-size: 9px;color: #84837f;">共{{pendingPaymentOrder.length}}件</text>
					<text style="font-size: 10px;margin-left: 10rpx;">合计：</text>
					<text style="font-size: 9px;color: #ff0000;margin-right: 5rpx;">￥</text>
					<text style="font-size: 14px; font-weight: bold; color: #ff0000;">{{pendingPaymentOrder[0].totalPrice}}</text>
					<text style="font-size: 10px;margin-left: 6rpx;">元</text>
				</view><view class="price-detail-button">
					<!-- <u-button size="small" :plain="true" shape="circle" @click="continuePayment" text="催发货"></u-button> -->
				</view>
			</view>
		</view>
		<view class="commodity-list">
			<tui-divider height="30" gradual dividerColor="red">为您推荐</tui-divider>
			<view class="list">
				<WaterfallFlow :commodityList="indexShowCommodityData" />
			</view>
		</view>
	</view>
</template>

<script>
	import { getIndexShowCommodity } from '@/api/commodity/commodity.js'
	import WaterfallFlow from '@/components/waterfallFlow/waterfallFlow.vue'
	import { getPaymentCompletedOrderById } from '@/api/pay/pay.js'
	export default {
		components: {
			WaterfallFlow
		},
		data() {
			return {
				orderId: '',
				pendingPaymentOrder: [],
				indexShowCommodityData: [],
				currentPage: Math.floor(Math.random() * 16) + 1,
				pageSize: 10,
				totalPage: 1,
			}
		},
		async onLoad(o) {
			this.orderId = o.orderId
			const {data:res } = await getPaymentCompletedOrderById(this.orderId)
			if(res.code ==0 ){
				this.pendingPaymentOrder = res.data
			}
		},
		onPullDownRefresh() {
			let _this = this
			_this.currentPage = Math.floor(Math.random() * (_this.totalPage -1)) + 1
			_this.indexShowCommodityData = []
			_this.getCommodityIndexShow()
			uni.stopPullDownRefresh()
		},
		onReachBottom() {
			if (this.totalPage <= this.currentPage) {
				uni.showToast({
					title: '哥也是有底的',
					duration: 2000,
					icon: 'none'
				});
			}
			this.currentPage += 1
			this.getCommodityIndexShow()
		},
		methods: {
			// 获取展示的商品
			async getCommodityIndexShow() {
				const {
					data: res
				} = await getIndexShowCommodity(this.currentPage, this.pageSize)
				if (res.code == 0) {
					res.data.records.map(d => {
						this.indexShowCommodityData.push(d)
					})
					this.totalPage = res.data.pages
				}
			},
		}
	}
</script>

<style lang="scss">
	page {
		background-color: #f6f6f6;
	}

	.order-box {
		width: 100%;
		display: flex;
		flex-direction: column;

		.address-box {
			width: 90%;
			margin: 0 auto;
			margin-top: 20rpx;
			background-color: #fff;
			// height: 200rpx;
			border-radius: 20rpx;
			padding: 20rpx;
			display: flex;
			flex-direction: row;

			.address-icon {
				width: 10%;
				display: flex;
				align-items: center;
				justify-content: center;
			}

			.address-desc {
				width: 80%;
				display: flex;
				flex-direction: column;
				padding: 10rpx;

				.detailedAddress {
					font-size: 13px;
					font-weight: bold;
				}

				.consignee-phone {
					font-size: 11px;
					margin-top: 10rpx;
					color: #939393;
				}
			}

			.address-select {
				width: 10%;
				display: flex;
				align-items: center;
				justify-content: center;
			}
		}

		.commodity-box {
			width: 90%;
			margin: 0 auto;
			margin-top: 20rpx;
			background-color: #fff;
			border-radius: 20rpx;
			padding: 20rpx;
			display: flex;
			flex-direction: column;

			.shop-box {
				display: flex;
				flex-direction: row;

				.logo {
					width: 35rpx;
					height: 35rpx;
					background-color: red;
					margin-right: 20rpx;
				}

				.business-name {
					font-size: 13px;
					font-weight: bold;
				}
			}

			.commodity {
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				margin-top: 20rpx;

				.commodity-image {
					width: 20%;
					height: 130rpx;
				}

				.commodity-desc {
					width: 55%;
					color: #717171;
					font-size: 12px;
					margin-left: 10rpx;
					margin-right: 10rpx;
				}

				.commodity-price {
					width: 15%;
					font-size: 10px;
					display: flex;
					flex-direction: column;
				}
			}

			.delivery-service {
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				margin-top: 30rpx;
				align-items: center;

				.delivery-service-left {
					font-size: 10px;
					color: #717171;
				}

				.delivery-service-right {
					font-size: 10px;
					display: flex;
					flex-direction: column;
					justify-content: end;
				}
			}

			.member-preferential {
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				margin-top: 20rpx;
				align-items: center;

				.member-preferential-left {
					font-size: 10px;
					color: #717171;
				}

				.member-preferential-right {
					font-size: 10px;
					display: flex;
					flex-direction: column;
					justify-content: end;
					color: #939393;
				}
			}

			.use-coupon {
				display: flex;
				flex-direction: column;
				margin-top: 20rpx;
				align-items: center;

				.common {
					font-size: 10px;
					color: #939393;
				}

				.business {
					font-size: 10px;
					color: #939393;
				}

			}

			.order-remarks {
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				margin-top: 20rpx;
				align-items: center;

				.order-remarks-left {
					font-size: 10px;
					color: #717171;
				}

				.order-remarks-right {
					font-size: 10px;
					color: #939393;
					display: flex;
					flex-direction: row;
					justify-content: end;
				}
			}
		}
		
		.pay{
			width: 90%;
			margin: 0 auto;
			margin-top: 20rpx;
			background-color: #fff;
			border-radius: 20rpx;
			padding: 20rpx;
			display: flex;
			flex-direction: row;
			justify-content: space-between;
			margin-bottom: 150rpx;
		}

		.order-status{
			width: 90%;
			margin: 0 auto;
			margin-top: 20rpx;
			margin-bottom: 30rpx;
			background-color: #fff;
			border-radius: 20rpx;
			padding: 20rpx;
			display: flex;
			flex-direction: column;
			font-size: 13px;
			
			.order-detail{
				width: 100%;
				display: flex;
				flex-direction: column;
				
				.order-detail-item{
					font-size: 10px;
					margin-bottom: 10rpx;
					color: #5b5b5b;
					margin-top: 10rpx;
					display: flex;
					flex-direction: row;
					justify-content: space-between;
				}
			}
		}

.commodity-list{
		width: 95%;
		margin: 0 auto;
		margin-top: 40rpx;
		
		.list{
			margin-top: 20rpx;
		}
	}
		.price-detail {
			position: fixed;
			bottom: 0;
			z-index: 99999;
			margin-top: 50rpx;
			width: 100%;
			height: 100rpx;
			border-top: 2rpx solid #eee;
			background-color: #fff;
			line-height: var(--footer-height);
			
			.price-detail-desc {
				display: flex;
				flex-direction: row;
				line-height: 100rpx;
				
				.price-detail-item{
					width: 100%;
					align-items: center;
					display: flex;
					flex-direction: row;
					justify-content: center;
					text-align: center;
				}
				
				.price-detail-button{
					width: 30%;
					display: flex;
					justify-content: center;
					align-items: center;
					margin-right: 30rpx;
				}

			}
		}

	}
</style>
