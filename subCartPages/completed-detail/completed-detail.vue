<template>
	<view class="order-box">
		<view class="address-box">
			<view class="address-icon">
				<tui-icon color="#ff2e63" name="position"></tui-icon>
			</view>
			<view class="address-desc">
				<view class="detailedAddress">{{orderDetailData.userAddress.detailedAddress}}</view>
				<view class="consignee-phone">
					<text style="margin-right: 20rpx;">{{orderDetailData.userAddress.consignee}}</text>
					<text>{{orderDetailData.userAddress.phone}}</text>
				</view>
			</view>
		</view>
		<view style="">
			<view class="commodity-box" v-for="(item,index) in orderDetailData.receiveOrderCommodityList" :key="index">
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
						<text>{{item.order.orderRemarks}}</text>
					</view>
				</view>
			</view>
		</view>
		<view class="order-status">
			<view style="margin-bottom: 15rpx;">订单信息</view>
			<view class="order-detail">
				<view class="order-detail-item">
					<text>订单编号</text>
					<text>{{orderDetailData.receiveOrderCommodityList[0].order.orderNumber}}</text>
				</view>
				<view class="order-detail-item">
					<text>订单状态</text>
					<text>已完成</text>
				</view>
				<view class="order-detail-item">
					<text>快递运费</text>
					<text>￥{{orderDetailData.receiveOrderCommodityList[0].order.transportationExpenses}}.00 元</text>
				</view>
				<view class="order-detail-item">
					<text>创建时间</text>
					<text>{{orderDetailData.receiveOrderCommodityList[0].order.createTime}}</text>
				</view>
				<view class="order-detail-item">
					<text>付款时间</text>
					<text>{{orderDetailData.receiveOrderCommodityList[0].order.strikeBargainTime}}</text>
				</view>
			</view>
		</view>
		<view class="logistics-task">
			<div style="font-size: 12px;margin-bottom: 20rpx;" >
				<text>物流轨迹详细信息</text>
			</div>
			<div>
				<tui-steps direction="column" titleSize="18" descSize="16" type="1" :items="logisticsTaskItems" spacing="100rpx" :activeSteps="logisticsTaskItems.length - 1"></tui-steps>
			</div>
		</view>
		<view class="price-detail">
			<view class="price-detail-desc">
				<view class="price-detail-item">
					<text style="font-size: 10px;margin-left: 10rpx;">合计：</text>
					<text style="font-size: 9px;color: #ff0000;margin-right: 5rpx;">￥</text>
					<text style="font-size: 14px; font-weight: bold; color: #ff0000;">{{orderDetailData.receiveOrderCommodityList[0].order.totalPrice}}</text>
					<text style="font-size: 10px;margin-left: 6rpx;">元</text>
				</view><view class="price-detail-button">
					<u-button size="small" type="primary" shape="circle" @click="fillComments" text="填写商品评论"></u-button>
				</view>
			</view>
		</view>
		<view class="commodity-list">
			<tui-divider height="30" gradual dividerColor="red">为您推荐</tui-divider>
			<view class="list">
				<WaterfallFlow :commodityList="indexShowCommodityData" />
			</view>
		</view>
		<view style="width: 100%;height: 100%;">
			<u-popup round="20" :show="commentShow" mode="bottom"  @close="commentShow = false">
			        <view class="commentView">
			            <view class="commodity-comment-list">
							<view style="font-size: 13px;margin-bottom: 30rpx;">
								填写商品评论
							</view>
							<view class="commodity-comment-item" v-for="(item,index) in orderDetailData.receiveOrderCommodityList" :key="index">
								<view class="commodity-comment-item-image">
									<image :src="item.commodity.commodityImageUrl" style="width: 100%;height: 100%;" mode=""></image>
								</view>
								<view class="commodity-comment-item-title">
									<view class="title-item">{{item.commodity.commodityName}}</view>
									<view class="button-item">
										<u-button type="warning" shape="circle" @click="commodityComment(item)" size="small" text="评论"></u-button>
									</view>
								</view>
							</view>
						</view>
			        </view>
				</u-popup>
		</view>
		<view class="">
			<tui-modal :show="commentModal" custom zIndex="10000000">
				<view class="tui-modal-custom">
					<view style="font-size: 10px;margin-bottom: 50rpx;text-align: center;">商品评论</view>
					<view class="tui-modal-custom-text" style="margin-bottom: 30rpx;">
						<u--textarea v-model="commodityCommentText"  count  height="150" maxlength="200" placeholder="请输入商品描述内容内容" ></u--textarea>
					</view>
					<tui-button height="65rpx" :size="24" type="danger" :plain="true" shape="circle" @click="handleCommodityCommentClick">确定</tui-button>
				</view>
			</tui-modal>
		</view>
	</view>
</template>

<script>
	import { getExpressOrderDetail,getLogisticsTrackDetail } from '@/api/logistics/logistics.js'
	import { getOrderDetailByNumber,userConfirmReceipt } from '@/api/pay/pay.js'
	import { getIndexShowCommodity,addCommodityComment } from '@/api/commodity/commodity.js'
	import WaterfallFlow from '@/components/waterfallFlow/waterfallFlow.vue'
	import {
		mapState,
		mapActions,
		mapGetters
	} from 'vuex'
	export default {
		computed: {
			...mapState(['user'])
		},
		components: {
			WaterfallFlow
		},
		data() {
			return {
				orderDetailData: [],
				indexShowCommodityData: [],
				currentPage: Math.floor(Math.random() * 6) + 1,
				pageSize: 10,
				totalPage: 1,
				logisticsTaskItems: [],
				expressOrderId: '',
				orderNumber: '',
				commentShow: false,
				commentModal: false,
				commodityCommentText: '',
				commodityCommentId: ''
			}
		},
		async onLoad(o) {
			this.orderNumber = o.orderNumber
			const { data: orderDetailRes } = await getOrderDetailByNumber(this.orderNumber )
			const { data: res } = await getExpressOrderDetail(this.orderNumber )
			this.orderDetailData = orderDetailRes.data
			if(res.code == 0){
				this.expressOrderId = res.data.expressOrderId
				const { data: taskRes } = await getLogisticsTrackDetail(res.data.expressOrderId)
				taskRes.data.map(m => {
					let item = {
						title: m.logisticsStatus.split('-')[0],
						desc: m.updateTime + " - " + m.logisticsDesc
					}
					this.logisticsTaskItems.push(item)
				})
			}
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
			...mapGetters(['getUserInfo']),
			async handleCommodityCommentClick(){
				console.log(this.commodityCommentText)
				let commentData = {
					"userId": this.getUserInfo().userId,
					"commodityId": this.commodityCommentId,
					"commentContent": this.commodityCommentText
				}
				const {data:res } = await addCommodityComment(commentData)
				if(res.code == 0){
					uni.showToast({
						title: '商品评论成功',
						duration: 2000,
						icon: 'success'
					})
					this.commentModal = false
					this.commodityCommentText = ''
				}else{
					uni.showToast({
						title: '商品评论失败',
						duration: 2000,
						icon: 'error'
					})
					this.commentModal = false
					this.commodityCommentText = ''
				}
			},
			commodityComment(item){
				this.commodityCommentId = item.commodity.commodityId
				this.commentModal = true
			},
			async fillComments(){
				// console.log(this.orderNumber)
				this.commentShow = true
			},
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

		.order-status{
			width: 90%;
			margin: 0 auto;
			margin-top: 20rpx;
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
	
	.logistics-task{
		width: 90%;
		margin: 0 auto;
		margin-top: 20rpx;
		margin-bottom: 30rpx;
		background-color: #fff;
		border-radius: 20rpx;
		padding: 20rpx;
		display: flex;
		flex-direction: column;
		
		.step-desc{
			display: flex;
			flex-direction: column;
		}
	}
		.price-detail {
			position: fixed;
			bottom: 0;
			z-index: 999;
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

.commentView{
	width: 100%;
	padding: 25rpx;
	
	.commodity-comment-list{
		width: 95%;
		display: flex;
		flex-direction: column;
		justify-content: center;
		
		.commodity-comment-item{
			width: 90%;
			margin: 0 auto;
			display: flex;
			flex-direction: row;
			margin-bottom: 20rpx;
			
			.commodity-comment-item-image{
				width:30%;
				height: 80px;
				padding: 10rpx;
			}
			
			.commodity-comment-item-title{
				padding: 10rpx;
				display: flex;
				flex-direction: column;
				width: 70%;
				
				.title-item{
					font-size: 12px;
				}
				
				.button-item{
					margin-top: 30rpx;
					display: flex;
					justify-content: end;
					width: 50px;
					align-items: flex-end;
				}
			}
		}
	}
}
	}
</style>
