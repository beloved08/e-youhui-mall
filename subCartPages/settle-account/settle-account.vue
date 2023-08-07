<template>
	<view class="order-box">
		<view class="address-box" @click="selectAddress">
			<view class="address-icon">
				<tui-icon color="#ff2e63" name="position"></tui-icon>
			</view>
			<view class="address-desc">
				<view class="detailedAddress">{{order.orderAddress.detailedAddress}}</view>
				<view class="consignee-phone">
					<text style="margin-right: 20rpx;">{{order.orderAddress.consignee}}</text>
					<text>{{order.orderAddress.phone}}</text>
				</view>
			</view>
			<view class="address-select">
				<tui-icon color="#939393" name="arrowright"></tui-icon>
			</view>
		</view>
		<view >
			<view class="commodity-box" v-for="(item,index) in order.commodityList" :key="index">
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
						<view style="margin-top: 10rpx;">{{item.quantity}} {{item.commodity.meterCompany}}</view>
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
						<view @click="selectCommonCoupon(item)">
							<text>通用优惠券</text>
							<tui-icon color="#939393" :size="16" name="arrowright"></tui-icon>
						</view>
						<view @click="selectBusinessCoupon(item)">
							<text>商家优惠券</text>
							<tui-icon color="#939393" :size="16" name="arrowright"></tui-icon>
						</view>
					</view>
				</view>
				<view class="use-coupon">
					<view class="common" v-if="item.selectCommonCoupon">
						<text style="margin-right: 10rpx;">已选{{item.selectCommonCoupon.couponName}}</text>
						<text>满{{item.selectCommonCoupon.fullAvailable}} 减 {{item.selectCommonCoupon.discountAmount}}</text>
					</view>
					<view class="business" v-if="item.selectBusinessCoupon">
						<text style="margin-right: 10rpx;">已选{{item.selectBusinessCoupon.couponName}}</text>
						<text>满{{item.selectBusinessCoupon.fullAvailable}} 减 {{item.selectBusinessCoupon.discountAmount}}</text>
					</view>
				</view>
				<view class="order-remarks">
					<view class="order-remarks-left">订单备注</view>
					<view class="order-remarks-right" @click="openOrderRemarksShow(item)">
						<text v-if="item.orderRemarks == null">填写备注</text>
						<text v-else>{{item.orderRemarks}}</text>
						<tui-icon color="#939393" :size="16" name="arrowright"></tui-icon>
					</view>
				</view>
				<view class="">
					<u-popup :show="orderRemarksShow" :round="30" @close="orderRemarksShow = false" :closeable="true">
						<view class="order-remarks-popup">
							<view class="order-remarks-popup-title">订单备注</view>
							<view class="order-remarks-popup-textarea">
								<u--textarea v-model="orderRemarks" border="none" count :maxlength="200" :height="200"
									placeholder="选填,填写订单备注"></u--textarea>
							</view>
							<view class="order-remarks-popup-button">
								<u-button type="primary" text="确定" @click="okOrderRemarksShow"></u-button>
							</view>
						</view>
					</u-popup>
				</view>
			</view>
		</view>
		<view class="pay">
			<view style="font-size: 12px; font-weight: 500;">平台余额</view>
			<view>
				<u-checkbox-group>
					<u-checkbox  shape="circle" size="30" iconSize="20" activeColor="red" :checked="true"></u-checkbox>
				</u-checkbox-group>
			</view>
		</view>
		<view class="price-detail">
			<view class="price-detail-desc">
				<view class="price-detail-item">
					<text style="font-size: 9px;color: #84837f;">共{{order.commodityList.length}}件</text>
					<text style="font-size: 10px;margin-left: 10rpx;">合计：</text>
					<text style="font-size: 9px;color: #ff0000;margin-right: 5rpx;">￥</text>
					<text style="font-size: 14px; font-weight: bold; color: #ff0000;">{{finalPrice}}</text>
				</view>
				<view class="price-detail-button">
					<u-button color="red" size="small" shape="circle" @click="placeOrder" text="提交订单"></u-button>
				</view>
			</view>
		</view>
		<view class="">
			<tui-modal :show="orderModal" custom>
				<view class="tui-modal-custom">
					<view class="tui-modal-custom-text">
						<text style="font-size: 12px;color: #000;margin-right: 5rpx;">￥</text>
						<text style="font-size: 24px; font-weight: bold; color: #000;">{{finalPrice}}</text>
					</view>
					<view class="pay">
						<view style="font-size: 12px; font-weight: 500;">平台余额</view>
						<view>
							<u-checkbox-group>
								<u-checkbox  shape="circle" size="30" iconSize="20" activeColor="red" :checked="true"></u-checkbox>
							</u-checkbox-group>
						</view>
					</view>
					<view class="pay-button">
						<tui-button height="60rpx"  width="200rpx" :size="18" type="white"  @click="createOrder(0)">取消付款</tui-button>
						<tui-button height="60rpx"  width="200rpx" :size="18" type="primary"  @click="createOrder(1)">确认付款</tui-button>
					</view>
				</view>
			</tui-modal>
		</view>
	</view>
</template>

<script>
	import {
		getUserAddress
	} from '@/api/user/user.js'
	import { createOrder,getUserBalanceNumber } from '@/api/pay/pay.js'
	import {
		mapState,
		mapActions,
		mapGetters
	} from 'vuex'
	export default {
		computed: {
			...mapState(['user']),
			...mapState(['order']),
			finalPrice() {
				let price = 0
				let discount = 1
				// 会员优惠
				if (this.getUserInfo().type == 1) {
					discount = 0.95
				}
				// 优惠券
				let commodityList = this.getCommodityList()
				commodityList.map(c => {
					let common = 0
					let business = 0
					// 通用优惠券
					if (c.selectCommonCoupon != null) {
						common = c.selectCommonCoupon.discountAmount
					}
					if (c.selectBusinessCoupon != null) {
						business = c.selectBusinessCoupon.discountAmount
					}
					if (c.commodity.salesModel == 0) {
						// 零售型
						c.totalFinalPrice = ((discount * c.commodity.retailPrice) * c.quantity) - common - business
					} else {
						// 批发型
						c.totalFinalPrice = ((discount * c.commodity.wholesalePrice) * c.quantity ) - common -
							business
					}
				})
				commodityList.map(c => {
					c.price = price
					price += c.totalFinalPrice
				})
				return price.toFixed(2)
			}
		},
		data() {
			return {
				userId: '',
				orderModal: false,
				orderRemarks: '',
				orderRemarksShow: false,
				orderRemarksShowCommodity: {}
			}
		},
		onLoad() {
			this.userId = this.getUserInfo().userId
			this.getUserAddressList()
		},
		methods: {
			...mapGetters(['getUserInfo', 'getCommodityList']),
			...mapActions(['selectOrderAddress', 'saveCommodityList']),
			async createOrder(i){
				let orderList = []
				this.order.commodityList.map(o => {
					let order = {
						"userId": this.userId,
						"businessId": o.business.businessId,
						"commodityId": o.commodity.commodityId,
						"purchaseQuantity": o.quantity,
						"orderRemarks": "",
						"merchantCoupon": "",
						"universalCoupon": "",
						"memberDiscount": "",
						"transportationExpenses": "0",
						"orderStatus": 0,
						"totalPrice": this.finalPrice,
						"addressId": this.order.orderAddress.addressId,
					}
					order.orderRemarks = o.orderRemarks != null ? o.orderRemarks : "无备注"
					order.merchantCoupon = o.selectBusinessCoupon != null ? o.selectBusinessCoupon.couponId : ""
					order.universalCoupon = o.selectCommonCoupon != null ? o.selectCommonCoupon.couponId : ""
					order.memberDiscount = this.getUserInfo().type == 1 ? "9.5" : ""
					orderList.push(order)
				})
				
				if(i == 0){
					this.orderModal = false
					//订单状态为待付款
					orderList.map(o =>{
						o.orderStatus = 0
					})
					const { data:res } = await createOrder(orderList)
					if(res.code == 0){
						uni.redirectTo({
							url: 'subCartPages/pending-payment/pending-payment?orderId=' + res.data.orderId
						})
					}
				}else{
					//付款
					orderList.map(o =>{
						o.orderStatus = -1
					})
					// 获取用户可用余额
					const { data:balanceRes } = await getUserBalanceNumber(this.userId)
					if(this.finalPrice > balanceRes.data){
						uni.showToast({
							title: "您的可用余额不足，请先充值",
							icon:'none',
							duration: 2000
						})
					}else{
						// 扣款
						const { data:res } = await createOrder(orderList)
						if(res.code == 0){
							uni.showToast({
								title: "下单成功",
								icon: 'success',
								duration: 2000
							})
							setTimeout(()=>{
								uni.redirectTo({
									url: 'subCartPages/checkout-success/checkout-success?orderId=' + res.data.orderId
								})
							},2000)
						}
					}
				}
			},
			async placeOrder(){
				this.orderModal = true
			},
			// 打开填写订单备注按钮
			openOrderRemarksShow(e) {
				this.orderRemarksShowCommodity = e
				this.orderRemarksShow = true
			},
			// 确认填写的备注
			okOrderRemarksShow() {
				let commodityList = this.getCommodityList()
				commodityList.map(c => {
					if (c.commodity.commodityId == this.orderRemarksShowCommodity.commodity.commodityId) {
						c.orderRemarks = this.orderRemarks
					}
				})
				this.saveCommodityList(commodityList)
				this.orderRemarksShow = false
				this.orderRemarks = ''
			},
			// 选择可使用的商家优惠券
			selectBusinessCoupon(e) {
				let price = 0
				if (e.commodity.salesModel == 0) {
					price = e.commodity.retailPrice * e.quantity
				} else {
					price = e.commodity.wholesalePrice * e.quantity
				}
				uni.navigateTo({
					url: 'subPages/personal-pages/coupon/coupon?id=0&businessId=' +
						e.business.businessId + "&price=" + price + "&commodityId=" + e.commodity.commodityId
				})
			},
			// 选择可使用的通用优惠券
			selectCommonCoupon(e) {
				let price = 0
				if (e.commodity.salesModel == 0) {
					price = e.commodity.retailPrice * e.quantity
				} else {
					price = e.commodity.wholesalePrice * e.quantity
				}
				uni.navigateTo({
					url: 'subPages/personal-pages/coupon/coupon?id=2' + "&price=" + price + "&commodityId=" + e
						.commodity.commodityId
				})
			},
			async getUserAddressList() {
				const {
					data: res
				} = await getUserAddress(this.userId)
				res.data.map(address => {
					if (address.isDefault == 0) {
						this.selectOrderAddress(address)
					}
				})
			},
			selectAddress() {
				uni.navigateTo({
					url: 'subPages/personal-pages/address/address?id=0'
				})
			}
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

		.order-remarks-popup {
			height: 500rpx;
			display: flex;
			flex-direction: column;
			padding: 30rpx;

			.order-remarks-popup-title {
				font-size: 14px;
				font-weight: bold;
				display: flex;
				justify-content: center;
				text-align: center;
			}

			.order-remarks-popup-textarea {
				margin-top: 30rpx;
				background-color: #f6f6f6;
			}

			.order-remarks-popup-button {
				margin-top: 100rpx;
			}
		}

		.price-detail {
			position: fixed;
			bottom: 0;
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
					width: 70%;
					// margin-left: 30%;
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
		
		.tui-modal-custom{
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			
			.tui-modal-custom-text{
				
			}
			.pay{
				width: 90%;
				margin: 0 auto;
				margin-top: 40rpx;
				background-color: #fff;
				border-radius: 20rpx;
				padding: 10rpx;
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				margin-bottom: 80rpx;
			}
			
			.pay-button{
				width: 90%;
				display: flex;
				flex-direction: row;
				justify-content: space-between;
			}
		}
	}
</style>
