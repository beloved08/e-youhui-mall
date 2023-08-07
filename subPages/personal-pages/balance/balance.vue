<template>
	<view class="container">
		<view class="balance-top">
			<view class="balance">
				<view class="balance-text">
					<text>可用余额(元)</text>
				</view>
				<view class="balance-icon">
					<tui-icon name="eye" :size="20" v-show="eyeShow" @click="eyeShow = false"></tui-icon>
					<tui-icon name="unseen" :size="20" v-show="!eyeShow" @click="eyeShow = true"></tui-icon>
				</view>
			</view>
			<view class="balance-num">
				<view v-show="eyeShow">
					<text>{{availableBalance}}</text>
				</view>
				<view v-show="!eyeShow">
					<text>****</text>
				</view>
			</view>
			<view class="balance-button">
				<tui-button @click="toRecharge">充值</tui-button>
			</view>
		</view>
		<view class="balance-back"></view>

		<view class="balance-detail">
			<view class="balance-detail-title">
				<text>余额变动明细</text>
				<view class="balance-detail-all" @click="toAllBalanceDetail">
					<view class="balance-detail-text">
						<text>全部</text>
					</view>
					<tui-icon name="arrowright" :size="20"></tui-icon>
				</view>
			</view>
			<tui-divider width="100%" height="40" gradual></tui-divider>
			<view class="balance-detail-list">
				<view class="balance-detail-list-item" v-for="(item, index) in userBalanceChangeDetailList"
					:key="index">
					<view class="item-left">
						<view class="item-left-title" v-if="item.changeType == 0">
							余额充值
						</view>
						<view class="item-left-title" v-else>
							余额扣减
						</view>
						<view class="item-left-time">
							{{item.changeTime}}
						</view>
					</view>
					<view class="item-right">
						<view class="item-right-num" v-if="item.changeType == 0">
							+{{item.changeQuota}}
						</view>
						<view class="item-right-num" v-else>
							-{{item.changeQuota}}
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		mapGetters
	} from 'vuex'
	import {
		getUserBalanceNumber,
		getUserBalanceChangeDetail
	} from '@/api/pay/pay.js'
	export default {
		data() {
			return {
				eyeShow: true,
				userId: '',
				// 可用余额
				availableBalance: 0,
				userBalanceChangeDetailList: []
			};
		},
		onLoad() {
			this.userId = this.getUserInfo().userId
			this.selectUserBalanceNumber()
			this.selectUserBalanceChangeDetail()
		},
		onPullDownRefresh() {
			this.selectUserBalanceNumber()
			this.selectUserBalanceChangeDetail()
			uni.stopPullDownRefresh()
		},
		methods: {
			...mapGetters(['getUserInfo']),
			toAllBalanceDetail() {
				uni.navigateTo({
					url: '/subPages/personal-pages/balance/balance-detail/balance-detail'
				})
			},
			// 获取变动明细
			async selectUserBalanceChangeDetail() {
				const {
					data: res
				} = await getUserBalanceChangeDetail(this.userId, 0)
				this.userBalanceChangeDetailList = res.data
			},
			// 获取可用余额
			async selectUserBalanceNumber() {
				const {
					data: res
				} = await getUserBalanceNumber(this.userId)
				this.availableBalance = res.data
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
			background-color: #0074e4;
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
