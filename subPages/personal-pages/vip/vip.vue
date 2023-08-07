<template>
	<view class="container">
		<view class="user">
			<view class="top">
				<view class="user-image">
					<image :src="user.userInfo.avatar" class="image"></image>
				</view>
				<view class="user-name">
					<view class="user-name-top">
						<text class="user-name-top-text">{{user.userInfo.realName}}</text>
						<tui-tag type="light-blue" hover="true" shape="circle" padding="11rpx" size="14rpx" v-if="user.userInfo.type == 0">普通用户
						</tui-tag>
						<tui-tag type="light-blue" hover="true" shape="circle" padding="11rpx" size="14rpx" v-else>会员用户
						</tui-tag>
					</view>
					<view class="user-desc">
						<text class="user-desc-contect" v-if="user.userInfo.type == 0">您还不是付费会员~</text>
						<text class="user-desc-contect" v-else>您已是付费会员~</text>
					</view>
				</view>
				<view class="buy-vip" @click="purchase">
					<view class="activate-now">购买会员</view>
				</view>
			</view>
		</view>
		<view class="detail" v-if="user.userInfo.type == 0">
			<u-empty width="300" height="300" textSize="18" text="暂无会员明细" mode="list"
				icon="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/list.png">
			</u-empty>
		</view>
		<view class="detail-1" v-else>
			恭喜您，于 {{user.userInfo.purchaseVipTime}} 成功开通付费会员
		</view>
	</view>
</template>

<script>
	import {
		mapState
	} from 'vuex'
	export default {
		computed: {
			...mapState(['user'])
		},
		data() {
			return {
			}
		},
		methods: {
			purchase(){
				uni.navigateTo({
					url: 'subPages/personal-pages/vip/vip-purchase/vip-purchase'
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

		.user {
			width: 100%;
			// height: 300rpx;
			margin: 0 auto;
			background: #ef3b36;
			background: -webkit-linear-gradient(to bottom, rgb(239, 59, 54), rgb(246, 246, 246));
			background: linear-gradient(to bottom, rgb(239, 59, 54), rgb(246, 246, 246));

			.top {
				width: 90%;
				display: flex;
				flex-direction: row;
				margin: 0 auto;
				margin-top: 50rpx;
				margin-bottom: 40rpx;

				.user-image {
					width: 17%;
					height: 110rpx;
					border-radius: 50%;
					border: 2px solid #fff;

					.image {
						width: 100%;
						height: 100%;
						border-radius: 50%;
					}
				}

				.user-name {
					width: 50%;
					display: flex;
					flex-direction: column;
					margin-top: 15rpx;
					margin-left: 10rpx;

					.user-name-top {
						display: flex;
						flex-direction: row;

						.user-name-top-text {
							font-size: 14px;
							font-weight: bold;
							color: #fff;
							margin-right: 10rpx;
						}
					}

					.user-desc {
						color: #fff;

						.user-desc-contect {
							font-size: 11px;
						}
					}
				}

				.buy-vip {
					width: 40%;
					height: 100rpx;
					margin-top: 20rpx;

					.activate-now {
						color: #535353;
						font-size: 11px;
						background-color: #f0ce96;
						width: 150rpx;
						height: 55rpx;
						margin: 0 auto;
						text-align: center;
						line-height: 55rpx;
						border-radius: 50rpx;
					}
				}
			}

		}

		.detail {
			width: 90%;
			margin: 0 auto;
			margin-top: 20rpx;
			margin-bottom: 20rpx;
			padding-bottom: 20rpx;
			border-radius: 10rpx;
			background-color: #fff;
		}
		
		.detail-1{
			width: 90%;
			margin: 0 auto;
			border-radius: 10rpx;
			background-color: #fff;
			padding: 20rpx;
			font-size: 12px;		
			}
	}
</style>
