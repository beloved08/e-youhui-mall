<template>
	<view class="container">
		<view class="change-list">
			<tui-list-view color="#777">
				<tui-list-cell hover :arrow="false" v-for="(item,index) in allUserBalanceChangeDetailList" :key="index">
					<view class="list-item">
						<view class="list-item-left">
							<view class="title">订单号：{{item.orderNumber}}</view>
							<view class="time">交易时间：{{item.changeTime}}</view>
						</view>
						<view class="list-item-right">
							<view class="title" v-if="item.changeType == 0">余额充值</view>
							<view class="title" v-else>余额扣减</view>
							<view class="time" v-if="item.changeType == 0">+{{item.changeQuota}}</view>
							<view class="time" v-else>+{{item.changeQuota}}</view>
						</view>
					</view>
				</tui-list-cell>
			</tui-list-view>
		</view>
	</view>
</template>

<script>
	import {
		mapGetters
	} from 'vuex'
	import {
		getUserBalanceChangeDetail
	} from '@/api/pay/pay.js'
	export default {
		data() {
			return {
				userId: '',
				allUserBalanceChangeDetailList: []
			};
		},
		onLoad() {
			this.userId = this.getUserInfo().userId
			this.selectAllUserBalanceChangeDetail()
		},
		onPullDownRefresh() {
			this.selectAllUserBalanceChangeDetail()
			uni.stopPullDownRefresh()
		},
		methods: {
			...mapGetters(['getUserInfo']),
			// 获取变动明细
			async selectAllUserBalanceChangeDetail() {
				const {
					data: res
				} = await getUserBalanceChangeDetail(this.userId, 1)
				this.allUserBalanceChangeDetailList = res.data
			}
		}
	}
</script>

<style lang="scss">
	page {
		background-color: #f6f6f6;
	}

	.container {
		width: 100%;
		display: flex;
		flex-direction: column;

		.change-list {
			padding: 10rpx;

			.list-item {
				display: flex;
				flex-direction: row;
				justify-content: space-between;

				.list-item-left {
					width: 70%;
					display: flex;
					flex-direction: column;
					font-size: 12px;

					.time {
						margin-top: 20rpx;
					}
				}

				.list-item-right {
					width: 15%;
					display: flex;
					flex-direction: column;
					font-size: 12px;

					.time {
						margin-top: 20rpx;
					}
				}
			}
		}
	}
</style>
