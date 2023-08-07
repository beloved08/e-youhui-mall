<template>
	<view class="container">
		<view class="recharge-top">
			在线充值
		</view>
		<view class="recharge-top-button">
			<view class="recharge">
				<view class="recharge-num">
					{{showRechargeData.number}}
				</view>
				<view class="recharge-unit">
					元
				</view>
			</view>
			<view class="recharge-detail">
				充值优惠：额外赠送{{showRechargeData.integral}}积分
			</view>
		</view>

		<view class="recharge-num-list">
			<view class="recharge-num-list-item" v-for="(item,index) in rechargeList" :key="index"
				:class="'select-' + index + selectIndex" @click="numberStyleChange(item,index)">
				<view class="recharge-list-item-num">
					<text>{{item.number}}</text>
					<text>元</text>
				</view>
			</view>
		</view>

		<view class="recharge-way">
			<view class="recharge-way-name">
				<u-icon name="chat-fill" color="#0074e4" size="50"></u-icon>
				<view class="recharge-way-title">
					微信支付
				</view>
			</view>
			<view class="recharge-way-radio">
				<radio value="r1" checked="true" color="#0074e4" />
			</view>
		</view>

		<view class="recharge-button">
			<u-button text="充值" @click="recharge" color="linear-gradient(to right, rgb(0, 116, 228), rgb(0, 116, 228))">
			</u-button>
		</view>
	</view>
</template>

<script>
	import {
		mapGetters
	} from 'vuex'
	import {
		userBalanceRecharge
	} from '@/api/pay/pay.js'
	export default {
		data() {
			return {
				userId: '',
				selectIndex: 0,
				showRechargeData: {
					number: 1,
					integral: 100
				},
				rechargeList: [{
					number: 10,
					integral: 100
				}, {
					number: 50,
					integral: 500
				}, {
					number: 100,
					integral: 1000
				}, {
					number: 200,
					integral: 2000
				}, {
					number: 300,
					integral: 3000
				}, {
					number: 500,
					integral: 50000
				}]
			};
		},
		onLoad() {
			this.userId = this.getUserInfo().userId
		},
		methods: {
			...mapGetters(['getUserInfo']),
			numberStyleChange(item, index) {
				this.showRechargeData = item
				// 改变样式
				this.selectIndex = index
			},
			async recharge() {
				const rechargeData = {
					"userId": this.userId,
					"availableBalance": this.showRechargeData.number
				}
				const {
					data: res
				} = await userBalanceRecharge(rechargeData)

				if (res.code == 0) {
					uni.showToast({
						title: res.data,
						icon: 'success',
						duration: 2000
					})
					setTimeout(()=> {
						// 返回上一级
						uni.navigateBack()
					},2500)
				} else {
					uni.showToast({
				 	title: res.msg,
						icon: 'error',
						duration: 2000
					})
				}
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

		.recharge-top {
			font-size: 20px;
			font-size: bold;
			margin-top: 20rpx;
			margin-left: 20rpx;
		}

		.recharge-top-button {
			width: 90%;
			margin: 0 auto;
			margin-top: 30rpx;
			border-radius: 0 50rpx 0 0;
			background: linear-gradient(to right, rgb(255, 49, 11), rgb(238, 81, 73));

			.recharge {
				display: flex;
				flex-direction: row;

				.recharge-num {
					font-size: 30px;
					font-weight: bold;
					color: #fff;
					margin-top: 50rpx;
					margin-left: 30rpx;
				}

				.recharge-unit {
					font-size: 15px;
					color: #fff;
					margin-top: 80rpx;
				}
			}

			.recharge-detail {
				margin-top: 10rpx;
				margin-left: 30rpx;
				font-size: 12px;
				color: #fff;
				margin-bottom: 30rpx;
			}
		}

		.recharge-num-list {
			width: 95%;
			margin: 0 auto;
			margin-top: 20rpx;
			background-color: #fff;
			display: flex;
			flex-wrap: wrap;

			.recharge-num-list-item {
				width: 25%;
				height: 110rpx;
				border: 1rpx solid #dfdfdf;
				margin: 20rpx auto;
				display: flex;
				align-items: center;

				.recharge-list-item-num {
					margin: 0 auto;
				}
			}

			.select-00 {
				color: #fff;
				border: 1rpx solid #0074e4;
				background-color: #0074e4;

				.recharge-list-item-num {
					margin: 0 auto;
				}
			}

			.select-11 {
				color: #fff;
				border: 1rpx solid #0074e4;
				background-color: #0074e4;

				.recharge-list-item-num {
					margin: 0 auto;
				}
			}

			.select-22 {
				color: #fff;
				border: 1rpx solid #0074e4;
				background-color: #0074e4;

				.recharge-list-item-num {
					margin: 0 auto;
				}
			}

			.select-33 {
				color: #fff;
				border: 1rpx solid #0074e4;
				background-color: #0074e4;

				.recharge-list-item-num {
					margin: 0 auto;
				}
			}

			.select-44 {
				color: #fff;
				border: 1rpx solid #0074e4;
				background-color: #0074e4;

				.recharge-list-item-num {
					margin: 0 auto;
				}
			}

			.select-55 {
				color: #fff;
				border: 1rpx solid #0074e4;
				background-color: #0074e4;

				.recharge-list-item-num {
					margin: 0 auto;
				}
			}


		}

		.recharge-way {
			width: 90%;
			margin: 0 auto;
			margin-top: 20rpx;
			display: flex;
			flex-direction: row;
			justify-content: space-between;

			.recharge-way-name {
				display: flex;
				flex-direction: row;

				.recharge-way-title {
					margin-left: 10rpx;
					font-size: 16px;
				}
			}
		}

		.recharge-button {
			width: 90%;
			margin: 0 auto;
			margin-top: 50rpx;
		}
	}
</style>
