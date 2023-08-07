<template>
	<view>
		<view class="shop-box">
			<view class="apply-shop">
				<view class="apply-title">
					<text>收货地址信息</text>
				</view>
				<view class="apply-from">
					<view class="apply-from-item">
						<text>收件人</text>
						<u--input placeholder="请输入2-10字以内的收件人" v-model="userAddressForm.consignee" clearable
							fontSize="13px" border="bottom"></u--input>
					</view>
					<view class="apply-from-item">
						<text>手机号码</text>
						<u--input placeholder="请输入11字的手机号码" v-model="userAddressForm.phone" clearable fontSize="13px"
							border="bottom"></u--input>
					</view>
					<view class="apply-from-item">
						<text>所在地区</text>
						<u--input placeholder="请选择所在地区" v-model="userAddressForm.location" clearable fontSize="13px"
							border="bottom"></u--input>
						<view class="address">
							<pickerAddress @change="pickerAddressChange">
								<tui-icon name="gps" :size="25" color="#be1004"></tui-icon>
							</pickerAddress>
						</view>
					</view>
					<view class="apply-from-item apply-from-item-map">
						<view class="apply-from-item-map-one">
							<text>详细地址</text>
							<u--input placeholder="请输入50字以内的详细地址" v-model="userAddressForm.detailedAddress" clearable
								fontSize="13px" border="bottom"></u--input>
							<view class="address" @click="selectLocation">
								<tui-icon name="gps" :size="25" color="#be1004"></tui-icon>
							</view>
						</view>
					</view>
					<view class="apply-from-item">
						<text>地址类型</text>
						<view class="" style="margin-top: 10rpx;">
							<radio-group @change="addressChange">
								<radio value="0" :checked="userAddressForm.isDefault == 0"
									style="transform:scale(0.7)" /><text style="font-size: 13px;">默认地址</text>
								<radio value="1" :checked="userAddressForm.isDefault == 1"
									style="transform:scale(0.7)" /><text style="font-size: 13px;">普通地址</text>
							</radio-group>
						</view>
					</view>
				</view>
			</view>
			<view class="button-box">
				<view class="button-box-one">
					<u-button type="primary" @click="editAddress" text="保存收货地址"
						color="linear-gradient(to right, rgb(76, 99, 252), rgb(255, 60, 226))"></u-button>
				</view>
				<view class="button-box-two">
					<u-button type="error" @click="deleteAddress" text="删除收货地址" :plain="true"></u-button>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		mapState,
		mapActions,
		mapGetters
	} from 'vuex'
	import {
		getAddressById,
		editUserAddress,
		deleteUserAddress
	} from '@/api/user/user.js'
	const chooseLocation = requirePlugin('chooseLocation')
	import pickerAddress from '@/subPages/components/pickerAddress/pickerAddress.vue'
	export default {
		computed: {
			...mapState(['user'])
		},
		components: {
			pickerAddress
		},
		data() {
			return {
				addressData: {
					longitude: '',
					latitude: ''
				},
				userAddressForm: {
					userId: '',
					location: '',
					detailedAddress: '',
					phone: '',
					consignee: '',
					longitude: '',
					latitude: '',
					isDefault: 1
				}
			}
		},
		onShow() {
			const location = chooseLocation.getLocation();
			if (location != null) {
				this.userAddressForm.detailedAddress = location.address
				this.userAddressForm.longitude = location.longitude
				this.userAddressForm.latitude = location.latitude
			}
		},
		async onLoad(option) {
			this.userAddressForm.userId = this.getUserInfo().userId
			const {
				data: res
			} = await getAddressById(option.addressId)
			if (res.code == 0) {
				this.userAddressForm = res.data
			}
		},
		methods: {
			...mapGetters(['getUserInfo']),
			addressChange(e) {
				this.userAddressForm.isDefault = Number(e.detail.value)
			},
			async editAddress() {
				if (this.userAddressForm.consignee == '') {
					uni.showToast({
						title: '请填写收件人名称',
						icon: 'none'
					})
				} else if (this.userAddressForm.phone == '') {
					uni.showToast({
						title: '请填写收件人电话号码',
						icon: 'none'
					})
				} else if (this.userAddressForm.phone.length != 11) {
					uni.showToast({
						title: '电话号码错误',
						icon: 'none'
					})
				} else if (this.userAddressForm.location == '') {
					uni.showToast({
						title: '请填写收件人所在地区',
						icon: 'none'
					})
				} else if (this.userAddressForm.detailedAddress == '') {
					uni.showToast({
						title: '请填写收件人详细地址',
						icon: 'none'
					})
				} else {
					const {
						data: res
					} = await editUserAddress(this.userAddressForm)
					if (res.code == 0) {
						uni.showToast({
							title: res.data,
							icon: 'none'
						})
						setTimeout(() => {
							uni.$emit('refreshUserAddressData')
							uni.navigateBack()
						}, 200)
					} else {
						uni.showToast({
							title: res.msg,
							icon: 'none'
						})
					}
				}
			},
			// 删除收货地址
			async deleteAddress() {
				const {
					data: res
				} = await deleteUserAddress(this.userAddressForm.addressId, this.userAddressForm.userId)

				if (res.code == 0) {
					uni.showToast({
						title: res.data,
						icon: 'none'
					})
					setTimeout(() => {
						uni.$emit('refreshUserAddressData')
						uni.navigateBack()
					}, 200)
				} else {
					uni.showToast({
						title: res.msg,
						icon: 'none'
					})
				}
			},
			async pickerAddressChange(e) {
				this.userAddressForm.location = e.data[0] + e.data[1] + e.data[2]
				const {
					data: res
				} = await wx.serviceMarket.invokeService({
					service: 'wxc1c68623b7bdea7b',
					api: 'geoc',
					data: {
						"address": this.userAddressForm.location
					},
				})
				this.addressData.latitude = res.result.location.lat
				this.addressData.longitude = res.result.location.lng
			},
			// 选择详细地址
			selectLocation() {
				const key = 'YJXBZ-M5ECJ-CWZFI-FITCL-WGG4S-WFBRL'; //使用在腾讯位置服务申请的key
				const referer = 'EYouHui'; //调用插件的app的名称
				const location = JSON.stringify({
					latitude: this.addressData.latitude,
					longitude: this.addressData.longitude
				});

				const category = '生活服务,娱乐休闲';

				wx.navigateTo({
					url: `plugin://chooseLocation/index?key=${key}&referer=${referer}&location=${location}&category=${category}`
				});
			},
		}
	}
</script>

<style lang="scss" scoped>
	.box {
		display: flex;
		flex-direction: column;
		width: 100%;
		height: 100vh;
		background-color: #f6f6f6;
		align-items: center;

		.title {
			height: 60vh;
			font-size: 30px;
			font-weight: bold;
			margin-top: 200rpx;
		}

		.agreement-box {
			width: 85%;
			margin-top: 30rpx;
			font-size: 11px;

			.text-agreement {
				color: #0877ff;
			}
		}
	}

	.shop-box {
		width: 100%;
		display: flex;
		flex-direction: column;
		background-color: #f6f6f6;
		align-items: center;

		.apply-shop {
			width: 90%;
			background-color: #fff;
			padding: 15rpx;
			border-radius: 10rpx;
			margin-bottom: 30rpx;

			.apply-title {
				font-size: 15px;
				font-weight: bold;
			}

			.apply-from {
				width: 100%;
				margin: 0 auto;
				margin-top: 20rpx;

				.apply-from-item {
					display: flex;
					flex-direction: row;
					margin-bottom: 20rpx;

					.address {
						margin-top: 10rpx;
					}

					.apply-from-textarea {
						margin-left: 10rpx;
					}
				}

				.apply-from-item-map {
					display: flex;
					flex-direction: column;

					.apply-from-item-map-one {
						display: flex;
						flex-direction: row;
						margin-bottom: 10rpx;
					}

					.map {
						height: 700rpx;
						width: 100%;
					}
				}

				.apply-from-item text {
					font-size: 13px;
					margin-top: 20rpx;
					margin-right: 20rpx;
				}
			}
		}

		.button-box {
			width: 85%;
			background-color: #fff;
			margin: 30rpx;

			.button-box-one {
				margin-bottom: 20rpx;
			}
		}
	}
</style>
