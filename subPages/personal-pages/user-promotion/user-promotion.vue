<template>
	<view class="container">
		<view class="activity-box">
			<tui-list-view>
				<tui-list-cell :hover="true" :arrow="false" v-for="(item,index) in allPromotionActivityList"
					:key="index">
					<view class="activity-item">
						<view class="activity-img">
							<image style="width: 70rpx;height: 70rpx;"
								src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/time.png" mode=""></image>
						</view>
						<view class="activity-desc">
							<view class="top">
								<view class="name">{{item.promotionActivityName}}</view>
								<view class="time">{{item.createTime}}</view>
							</view>
							<view class="bottom">
								<text style="font-size: 9px;margin-right: 5rpx;">{{item.startTime}}</text>
								<text>~</text>
								<text style="font-size: 9px;margin-left: 5rpx;">{{item.endTime}}</text>
							</view>
						</view>
						<view class="create-url">
							<image @click="createShare(item)" style="width: 60rpx;height: 60rpx;"
								src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/share.png" mode=""></image>
						</view>
					</view>
				</tui-list-cell>
			</tui-list-view>
		</view>
		<view class="">
			<tui-modal :show="shareModal" :maskClosable="true" custom>
				<view class="tui-modal-custom">
					<view class="qr">
						<image style="width: 150px;height: 150px;" :src="qrCodeImageData" mode=""></image>
					</view>
					<view class="download">
						<view class="" style="margin-right: 15rpx;">
							<u-button @click="shareModal = false" :plain="true" size="small" text="取消"></u-button>
						</view>
						<view class="">
							<u-button type="primary" @click="saveAlbum" :plain="false" size="small" text="下载二维码"></u-button>
						</view>
					</view>
				</view>
			</tui-modal>
		</view>
	</view>
</template>

<script>
	import {
		getAllPromotionActivity,
		getPromotionByUserId,
		getPromotionActivityShareQr
	} from '@/api/merkete/merkete.js'
	import {
		mapGetters
	} from 'vuex'
	export default {
		components: {},
		data() {
			return {
				allPromotionActivityList: [],
				shareModal: false,
				qrCodeImageData: '',
			}
		},
		async onLoad() {
			const {
				data: res
			} = await getAllPromotionActivity()
			if (res.code == 0) {
				this.allPromotionActivityList = res.data
			}
		},
		methods: {
			...mapGetters(['getUserInfo']),
			async createShare(e) {
				let userId = this.getUserInfo().userId
				const {
					data: res
				} = await getPromotionActivityShareQr(userId, e.promotionActivityId)
				if (res.code == 0) {
					this.qrCodeImageData = res.data
					this.shareModal = true
				}
			},
			//保存相册
			saveAlbum() {
				uni.getSetting({ //获取用户的当前设置
					success: (res) => {
						if (res.authSetting['scope.writePhotosAlbum']) { //验证用户是否授权可以访问相册
							this.saveImageToPhotosAlbum();
						} else {
							uni.authorize({ //如果没有授权，向用户发起请求
								scope: 'scope.writePhotosAlbum',
								success: () => {
									this.saveImageToPhotosAlbum();
								}
							})
						}
					}
				})
			},
			saveImageToPhotosAlbum() {
				let _this = this
				let base64 = _this.qrCodeImageData.replace(/^data:image\/\w+;base64,/, ""); //去掉data:image/png;base64,
				let filePath = wx.env.USER_DATA_PATH + '/eyouhui_qrcode.png';
				uni.getFileSystemManager().writeFile({
					filePath: filePath, //创建一个临时文件名
					data: base64, //写入的文本或二进制数据
					encoding: 'base64', //写入当前文件的字符编码
					success: res => {
						uni.saveImageToPhotosAlbum({
							filePath: filePath,
							success: function(res2) {
								_this.shareModal = false
								// 	title: '保存成功',
								uni.showToast({
									title: '保存成功',
									icon: 'success',
									icon: 'none'
								})
							},
							fail: function(err) {
								this.shareModal = false
								// console.log(err.errMsg);
								uni.showToast({
									title: '保存失败',
									icon: 'error',
									icon: 'none'
								})
							}
						})
					},
					fail: err => {
						//console.log(err)
					}
				})
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

		.qr {
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
		}

		.download {
			margin-top: 30rpx;
			display: flex;
			flex-direction: row;
			align-items: center;
			justify-content: center;
		}

		.activity-box {
			width: 100%;
			// height: 400px;
			margin-top: 20rpx;
			background-color: #fff;

			.activity-item {
				width: 100%;
				display: flex;
				flex-direction: row;

				.activity-img {
					width: 15%;
				}

				.activity-desc {
					width: 65%;
					display: flex;
					flex-direction: row;
					flex-direction: column;

					.top {
						width: 100%;
						display: flex;
						flex-direction: row;
						justify-content: space-between;

						.name {
							width: 40%;
							font-size: 12px;
						}

						.time {
							width: 60%;
							font-size: 10px;
							color: #5a5a5a;
							display: flex;
							justify-content: right;
						}
					}

					.bottom {
						display: flex;
						flex-direction: row;
						margin-top: 20rpx;
					}
				}

				.create-url {
					width: 20%;
					display: flex;
					justify-content: center;
					align-items: center;
				}
			}
		}
	}
</style>
