<template>
	<view class="container">
		<view class="balance-top">
			<view class="balance">
				<view class="balance-text">
					<text>可用积分</text>
				</view>
				<view class="balance-icon">
					<tui-icon name="eye" :size="20" v-show="eyeShow" @click="eyeShow = false"></tui-icon>
					<tui-icon name="unseen" :size="20" v-show="!eyeShow" @click="eyeShow = true"></tui-icon>
				</view>
			</view>
			<view class="balance-num">
				<view v-show="eyeShow">
					<text>{{availableIntegral}}</text>
				</view>
				<view v-show="!eyeShow">
					<text>****</text>
				</view>
			</view>
			<view class="balance-button">
				<tui-button plain type="green" @click="toRecharge">获取积分</tui-button>
			</view>
		</view>
		<view class="balance-back"></view>
		<view class="balance-detail">
			<view class="balance-detail-title">
				<text>积分变动明细</text>
				<view class="balance-detail-all" @click="toAllIntegralDetail">
					<view class="balance-detail-text">
						<text>全部</text>
					</view>
						<tui-icon name="arrowright" :size="20" ></tui-icon>
				</view>
			</view>
			<tui-divider width="100%" height="40" gradual></tui-divider>
			<view class="balance-detail-list">
				<view class="balance-detail-list-item" v-for="(item, index) in userIntegralChangeDetailList" :key="index">
					<view class="item-left">
						<view class="item-left-title" v-if="item.changeType == 0">
							积分充值
						</view>
						<view class="item-left-title" v-else>
							积分扣减
						</view>
						<view class="item-left-time">
							{{item.changeTime}}
						</view>
					</view>
					<view class="item-right">
						<view class="item-right-num"  v-if="item.changeType == 0">
							+{{item.changeQuota}}
						</view>
						<view class="item-right-num"  v-else >
							-{{item.changeQuota}}
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import { getUserIntegralNumber,getUserIntegralChangeDetail } from '@/api/pay/pay.js'
	export default {
		data() {
			return {
				userId: uni.getStorageSync('LOGIN_USER_DETAIL') != null ? uni.getStorageSync('LOGIN_USER_DETAIL')
					.userInfo.userId : '',
				eyeShow: true,
				// 可用积分
				availableIntegral: 0,
				userIntegralChangeDetailList: []
			};
		},
		onLoad() {
			this.selectUserIntegralNumber()
			this.selectUserIntegralChangeDetail()
		},
		onPullDownRefresh() {
			this.selectUserIntegralNumber()
			this.selectUserIntegralChangeDetail()
			uni.stopPullDownRefresh()
		},
		methods: {
			async selectUserIntegralChangeDetail(){
				const { data:res } = await getUserIntegralChangeDetail(this.userId,0)
				this.userIntegralChangeDetailList = res.data
			},
			async selectUserIntegralNumber(){
				const { data:res } = await getUserIntegralNumber(this.userId)
				this.availableIntegral = res.data
			},
			toAllIntegralDetail(){
				uni.navigateTo({
					url: '/subPages/personal-pages/integral/integral-detail/integral-detail'
				})
			},
			toRecharge() {
				uni.navigateTo({
					url: '/subPages/personal-pages/balance/recharge/recharge'
				})
			}
		}
	}
</script>

<style lang="scss">
	.container {
		width: 100%;
		height: 100vh;
		background-color: #f6f6f6;
		display: flex;
		flex-direction: column;

		.balance-back {
			position: absolute;
			width: 100%;
			height: 20%;
			background-color: #17b978;
			border-radius: 0 0 5% 5%;
		}

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

				.balance-text {
					font-size: 13px;
				}

				.balance-icon {
					margin-left: 10rpx;
				}
			}

			.balance-num {
				margin: 0 auto;
				margin-top: 30rpx;
				margin-bottom: 25rpx;
				font-size: 40px;
				font-weight: bold;
			}

			.balance-button {
				width: 80%;
				margin: 0 auto;
				margin-top: 80rpx;
				margin-bottom: 30rpx;
			}
		}

		.balance-detail {
			width: 95%;
			margin: 0 auto;
			margin-top: 20rpx;
			border-radius: 10rpx;
			background-color: #fff;

			.balance-detail-title {
				font-size: 13px;
				font-weight: bold;
				margin-top: 20rpx;
				margin-left: 20rpx;
				display: flex;
				flex-direction: row;
				justify-content: space-between;

				.balance-detail-all {
					margin-right: 30rpx;
					display: flex;
					flex-direction: row;

					.balance-detail-text {
						font-weight: normal;
						font-size: 12px;
						margin-top: 5rpx;
					}

				}
			}

			.balance-detail-list {
				display: flex;
				flex-direction: column;

				.balance-detail-list-item {
					width: 95%;
					margin: 0 auto;
					margin-bottom: 20rpx;
					margin-top: 10rpx;
					display: flex;
					flex-direction: row;
					justify-content: space-between;

					.item-left {
						display: flex;
						flex-direction: column;

						.item-left-title {
							font-size: 12px;

						}

						.item-left-time {
							font-size: 11px;
							margin-top: 10rpx;
						}
					}

					.item-right {
						display: flex;
						margin-right: 20rpx;

						.item-right-num {
							font-size: 15px;
							font-weight: bold;
							margin-top: 20rpx;
						}
					}
				}
			}
		}
	}
</style>
