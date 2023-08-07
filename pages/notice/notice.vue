<template>
	<view class="container">
		<view class="notice-box" v-if="hasNotice">
			<tui-list-view v-if="notice.purchaseVipData.length > 0">
						<tui-list-cell :arrow="false" v-for="(item,index) in notice.purchaseVipData" :key="index">
							<view class="notice-item">
								<view class="notice-item-img">
									<image style="width: 100%; height: 100%; border-radius: 50%;"
										src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/notice/vip.png" mode=""></image>
								</view>
								<view class="notice-item-content">
									<view class="notice-item-content-top">
										<view class="title">开通付费会员</view>
										<view class="time">{{item.currentTime}}</view>
									</view>
									<view class="desc">
										恭喜您-{{item.userName}}，成功开通付费会员
									</view>
								</view>
							</view>
						</tui-list-cell>
					</tui-list-view>
			<tui-list-view v-if="notice.businessData.length > 0">
						<tui-list-cell :arrow="false" v-for="(item,index) in notice.businessData" :key="index">
							<view class="notice-item">
								<view class="notice-item-img">
									<image style="width: 100%; height: 100%; border-radius: 50%;"
										:src="item.shopAvatar" mode=""></image>
								</view>
								<view class="notice-item-content">
									<view class="notice-item-content-top">
										<view class="title">{{item.title}}</view>
										<view class="time">{{item.currentTime}}</view>
									</view>
									<view class="desc">
										您的{{item.businessName}}-{{item.shopName}}{{item.msg}}
									</view>
								</view>
							</view>
						</tui-list-cell>
					</tui-list-view>
			<tui-list-view v-if="notice.balanceData.length > 0">
				<tui-list-cell :arrow="false" v-for="(item,index) in notice.balanceData" :key="index">
					<view class="notice-item">
						<view class="notice-item-img" v-if="item.changeType == 0">
							<image style="width: 100%; height: 100%; border-radius: 50%;"
								src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/notice/balance-add.png" mode=""></image>
						</view>
						<view class="notice-item-img" v-else>
							<image style="width: 100%; height: 100%; border-radius: 50%;"
								src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/notice/balance-reduce.png" mode=""></image>
						</view>
						<view class="notice-item-content">
							<view class="notice-item-content-top">
								<view class="title" v-if="item.changeType == 0">余额充值</view>
								<view class="title" v-else>余额扣减</view>
								<view class="time">{{item.changeTime}}</view>
							</view>
							<view class="desc" v-if="item.changeType == 0">您充值了 {{item.changeQuota}} 元余额</view>
							<view class="desc" v-else>您扣减了 {{item.changeQuota}} 元余额</view>
						</view>
					</view>
				</tui-list-cell>
			</tui-list-view>
			<tui-list-view v-if="notice.promotionPeopleData.length > 0">
				<tui-list-cell :arrow="false" v-for="(item,index) in notice.promotionPeopleData" :key="index">
					<view class="notice-item">
						<!-- 审核通过 -->
						<view class="notice-item-img" v-if="item.verify == 1">
							<image style="width: 100%; height: 100%; border-radius: 50%;"
								src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/notice/approved.png" mode=""></image>
						</view>
						<view class="notice-item-img" v-else>
							<image style="width: 100%; height: 100%; border-radius: 50%;"
								src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/notice/no-approved.png" mode=""></image>
						</view>
						<view class="notice-item-content">
							<view class="notice-item-content-top">
								<view class="title" v-if="item.verify == 1">促销人员申请审核通过</view>
								<view class="title" v-else>促销人员申请审核不通过</view>
								<view class="time">{{item.currentTime}}</view>
							</view>
							<view class="desc" v-if="item.verify == 1">恭喜{{item.phone}}，您的促销人员申请审核已被管理员批准通过</view>
							<view class="desc" v-else>很遗憾-{{item.phone}}，您的促销人员申请审核不通过，请重新提交申请</view>
						</view>
					</view>
				</tui-list-cell>
			</tui-list-view>
			<tui-list-view v-if="notice.integralData.length > 0">
				<tui-list-cell :arrow="false" v-for="(item,index) in notice.integralData" :key="index">
					<view class="notice-item">
						<view class="notice-item-img" v-if="item.changeType == 0">
							<image style="width: 100%; height: 100%; border-radius: 50%;"
								src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/notice/integral-add.png" mode=""></image>
						</view>
						<view class="notice-item-img" v-else>
							<image style="width: 100%; height: 100%; border-radius: 50%;"
								src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/notice/integral-reduce.png" mode=""></image>
						</view>
						<view class="notice-item-content">
							<view class="notice-item-content-top">
								<view class="title" v-if="item.changeType == 0">积分充值</view>
								<view class="title" v-else>积分扣减</view>
								<view class="time">{{item.changeTime}}</view>
							</view>
							<view class="desc" v-if="item.changeType == 0">您的可用积分增加了 {{item.changeQuota}} 点</view>
							<view class="desc" v-else>您的可用积分扣减了 {{item.changeQuota}} 点</view>
						</view>
					</view>
				</tui-list-cell>
			</tui-list-view>
			<tui-list-view v-if="notice.createOrderData.length > 0">
				<tui-list-cell :arrow="false" v-for="(item,index) in notice.createOrderData" :key="index">
					<view class="notice-item">
						<view class="notice-item-img" v-if="item.orderStatus == 0">
							<image style="width: 100%; height: 100%; border-radius: 50%;"
								src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/notice/payment.png" mode=""></image>
						</view>
						<view class="notice-item-img" v-if="item.orderStatus ==1">
							<image style="width: 100%; height: 100%; border-radius: 50%;"
								src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/notice/shipped.png" mode=""></image>
						</view>
						<view class="notice-item-img" v-if="item.orderStatus ==2">
							<image style="width: 100%; height: 100%; border-radius: 50%;"
								src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/notice/goods-received.png" mode=""></image>
						</view>
						<view class="notice-item-img" v-if="item.orderStatus ==3">
							<image style="width: 100%; height: 100%; border-radius: 50%;"
								src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/notice/signed.png" mode=""></image>
						</view>
						<view class="notice-item-content">
							<view class="notice-item-content-top">
								<view class="title" v-if="item.orderStatus == 0">待付款</view>
								<view class="title" v-if="item.orderStatus == 1">待发货</view>
								<view class="title" v-if="item.orderStatus == 2">待收货</view>
								<view class="title" v-if="item.orderStatus == 3">已签收</view>
								<view class="time" v-if="item.orderStatus == 2">{{item.deliveryTime}}</view>
								<view class="time" v-else>{{item.createTime}}</view>
							</view>
							<view class="desc" v-if="item.orderStatus == 0">您有新的订单已创建，请及时完成付款</view>
							<view class="desc" v-if="item.orderStatus == 1">恭喜你，下单成功，我们将尽快为您发货</view>
							<view class="desc" v-if="item.orderStatus == 2">您的订单已发货，请注意查收</view>
							<view class="desc" v-if="item.orderStatus == 3">您的订单商品已签收成功</view>
						</view>
					</view>
				</tui-list-cell>
			</tui-list-view>
		</view>
		<view class="no-notice" v-if="!hasNotice">
			<view class="empty">
				<u-empty
				text="暂无消息"
				textSize="24"
				iconSize="120"
				        mode="car"
				        icon="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/empty/message-empty.png"
				>
				</u-empty>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		mapState
	} from 'vuex'
	export default {
		computed: {
			...mapState(['user']),
			...mapState(['notice'])
		},
		data() {
			return {
				hasNotice: false
			}
		},
		onLoad() {
			if (!this.user.isLogin) {
				uni.showModal({
					title: '警告',
					content: '您还未登录，请先登录',
					cancelText: "暂不登录",
					confirmText: "前往登录",
					confirmColor: '#ff0000',
					cancelColor: '#000000',
					success: function(res) {
						if (res.confirm) {
							uni.navigateTo({
								url: '/subPages/personal-pages/login/wx-login/wx-login?loginTo=' +
									'/pages/notice/notice'
							})
						} else {
							uni.navigateTo({
								url: '/pages/notice/notice'
							})
						}
					}
				})
			}else{
				if(this.notice.businessData.length > 0 || this.notice.balanceData.length > 0 
				|| this.notice.integralData.length > 0 || this.notice.purchaseVipData.length > 0
				|| this.notice.createOrderData.length > 0 || this.notice.promotionPeopleData.length > 0){
					this.hasNotice = true
				}
			}
		},
		created() {
			// setTimeout(()=>{
			// 	uni.hideTabBarRedDot({
			// 		index: 2
			// 	})
			// },3000)
		}
	}
</script>

<style lang="scss" scoped>
	.container {
		width: 100%;
		height: 100vh;
		display: flex;
		flex-direction: column;
		background-color: #f6f6f6;

		.notice-box {
			width: 100%;
			background-color: #fff;
			border-radius: 40rpx 40rpx 0 0;
			margin: 0 auto;
			margin-top: 10rpx;

			.notice-item {
				width: 100%;
				display: flex;
				flex-direction: row;

				.notice-item-img {
					width: 15%;
					height: 100rpx;
				}

				.notice-item-content {
					line-height: 50rpx;
					width: 80%;
					margin-left: 20rpx;
					display: flex;
					flex-direction: column;

					.notice-item-content-top {
						display: flex;
						flex-direction: row;
						justify-content: space-between;

						.title {
							font-size: 12px;
							font-weight: 600;
							color: #000;
						}

						.time {
							font-size: 9px;
							color: #aaa;
							margin-right: 10rpx;
						}
					}

					.desc {
						font-size: 11px;
						color: #7e7e7e;
					}
				}
			}
		}

		.no-notice {
			width: 100%;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			
			.empty{
				margin-top: 100rpx;
			}
		}
	}
</style>
