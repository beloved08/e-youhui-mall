<template>
	<view class="container">
		<view class="address-box" v-if="userAddressList.length > 0">
			<view class="address-item" v-for="(item,index) in userAddressList" :key="index" @click="selectAddress(item)">
				<view class="address-top">
					<view class="address-left">
						<view class="address-left-top">
							<view class="address-name">
								<text>{{item.consignee}}</text>
							</view>
							<view class="address-phone">
								<text>{{item.phone}}</text>
							</view>
						</view>
						<view class="address-left-bottom">
							<text>{{item.detailedAddress}}</text>
						</view>
					</view>
					<view class="address-right">
						<tui-icon name="evaluate" :size="24" color="#616161" @click="toEditAddress(item)"></tui-icon>
					</view>
				</view>
				<view class="address-bottom">
					<view class="address-bottom-desc">
						<radio style="transform:scale(0.7)" v-if="item.isDefault == 0" :checked="item.isDefault == 0">
							默认地址</radio>
						<radio style="transform:scale(0.7)" v-if="item.isDefault == 1" :checked="item.isDefault == 1">
							普通地址</radio>
					</view>
				</view>
			</view>
		</view>
		<view v-else class="no-address-box">
			<view class="no-address-desc">
				<image style="width: 100px;height: 100px;"
					src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/address/no-address.png" mode=""></image>
				<text class="no-address-text">暂无地址</text>
			</view>
		</view>
		<view class="add-address">
			<u-button text="新增收货地址" @click="toAddAddress()"
				color="linear-gradient(to right, rgb(66, 83, 216), rgb(213, 51, 186))"></u-button>
		</view>
	</view>
</template>

<script>
	import {
		getUserAddress
	} from '@/api/user/user.js'
	import {
		mapState,
		mapActions,
		mapGetters
	} from 'vuex'
	export default {
		computed: {
			...mapState(['user']),
			...mapState(['order']),
		},
		data() {
			return {
				userId: '',
				userAddressList: [],
				isNow: false
			}
		},
		onLoad(o) {
			if(o.id == 0){
				this.isNow = true
			}else if(o.id == 1){
				this.isNow = false
			}
			this.userId = this.getUserInfo().userId
			this.getUserAddressList()
			uni.$on('refreshUserAddressData', () => {
				this.getUserAddressList();
			})
		},
		async onPullDownRefresh() {
			const {
				data: res
			} = await getUserAddress(this.userId)
			if (res.code == 0) {
				this.userAddressList = res.data
				uni.stopPullDownRefresh()
			}
		},
		methods: {
			...mapGetters(['getUserInfo']),
			...mapActions(['selectOrderAddress']),
			selectAddress(e){
				if(this.isNow){
					this.selectOrderAddress(e)
					uni.navigateBack()
				}
			},
			async getUserAddressList() {
				const {
					data: res
				} = await getUserAddress(this.userId)

				if (res.code == 0) {
					this.userAddressList = res.data
				}
			},
			toAddAddress() {
				uni.navigateTo({
					url: '/subPages/personal-pages/address/addAddress/addAddress'
				})
			},
			toEditAddress(item) {
				uni.navigateTo({
					url: '/subPages/personal-pages/address/editAddress/editAddress?addressId=' + item.addressId,
				})
			}
		}
	}
</script>

<style lang="scss">
	page{
		background-color: #f6f6f6;
	}
	.container {
		width: 100%;
		background-color: #f6f6f6;
		display: flex;
		flex-direction: column;

		.address-box {
			width: 95%;
			margin: 0 auto;
			margin-top: 10rpx;
			margin-bottom: 80rpx;

			.address-item {
				padding: 20rpx;
				width: 90%;
				background-color: #fff;
				margin: 0 auto;
				margin-top: 10rpx;
				margin-bottom: 20rpx;
				display: flex;
				flex-direction: column;

				.address-top {
					width: 100%;
					display: flex;
					flex-direction: row;
					justify-content: space-between;

					.address-left {
						display: flex;
						flex-direction: column;

						.address-left-top {
							display: flex;
							flex-direction: row;

							.address-name {
								font-size: 14px;
								font-weight: bold;
							}

							.address-phone {
								font-size: 12px;
								margin-left: 20rpx;
							}
						}

						.address-left-bottom {
							margin-top: 15rpx;
							margin-left: 10rpx;
							font-size: 12px;
							color: #888;
						}
					}
				}

				.address-bottom {
					margin-top: 20rpx;
					border-top: 1px dashed #888;

					.address-bottom-desc {
						margin-top: 15rpx;
						font-size: 16px;
					}
				}
			}
		}

		.no-address-box {
			width: 100%;
			height: 100vh;
			background-color: #f6f6f6;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;

			.no-address-desc {
				display: flex;
				flex-direction: column;

				.no-address-text {
					margin-top: 30rpx;
					font-size: 14px;
					text-align: center;
				}
			}
		}

		.add-address {
			position: fixed;
			bottom: 0;
			width: 100%;
			line-height: var(--footer-height);
			color: #fff;
		}
	}
</style>
