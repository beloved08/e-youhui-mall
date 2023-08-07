<template>
	<view class="container">
		<view class="button">
			<tui-sticky :scrollTop="scrollTop" >
				<template v-slot:header >
					<view class="top-button">
						<tui-button width="250rpx" @click="open()" style="margin-right: 20rpx;">图标颜色选择器</tui-button>
						<tui-button type="gray" width="250rpx" @click="restoreDefaultIconColorModal = true">恢复默认
						</tui-button>
					</view>
				</template>
			</tui-sticky>
			<tui-modal :show="restoreDefaultIconColorModal" custom>
				<view class="tui-modal-custom">
					<view class="tui-modal-custom-text">确定恢复默认颜色？</view>
					<view class="tui-modal-custom-button">
						<tui-button width="230rpx" height="70rpx" plain @click="restoreDefaultIconColorModal = false"
							style="margin-right: 20rpx;">取消</tui-button>
						<tui-button width="230rpx" height="70rpx" @click="determineRestoreDefault()">确定</tui-button>
					</view>
				</view>
			</tui-modal>
		</view>
		<view class="icon_preview">
			<view class="icon_preview_item">
				<view class="icon_preview_title">
					<text>首页图标预览</text>
				</view>
				<view class="icon_preview_list">
					<u-grid :border="false" col="4">
						<u-grid-item v-for="(item,index) in icon.indexIconList" :key="index">
							<u-icon :name="item.name" :size="icon.indexIconSize" :color="icon.iconColor"></u-icon>
							<text class="grid-text">{{item.title}}</text>
						</u-grid-item>
					</u-grid>
				</view>
			</view>
			<view class="icon_preview_item">
				<view class="icon_preview_title">
					<text>订单状态图标预览</text>
				</view>
				<view class="icon_preview_list order-grid">
					<tui-grid unlined>
						<block v-for="(item,index) in icon.personalIconList.orderIconList" :key="index">
							<tui-grid-item :cell="5" :border="false" :bottom-line="false">
								<view class="grid-item-icon">
									<tui-icon :name="item.name" :size="icon.personalIconSize" :color="icon.iconColor">
									</tui-icon>
								</view>
								<text class="grid-title">{{item.title}}</text>
							</tui-grid-item>
						</block>
					</tui-grid>
				</view>
			</view>
			<view class="icon_preview_item">
				<view class="icon_preview_title">
					<text>服务与工具图标预览</text>
				</view>
				<view class="icon_preview_list utils-grid">
					<tui-grid unlined>
						<block v-for="(item,index) in icon.personalIconList.serviceUtilIconList" :key="index">
							<tui-grid-item :cell="4" :border="false" :bottom-line="false" @click="toServiceUtil(item)">
								<view class="utils-grid-icon">
									<tui-icon :name="item.name" :size="icon.personalIconSize" :color="icon.iconColor">
									</tui-icon>
								</view>
								<text class="utils-grid-title">{{item.title}}</text>
							</tui-grid-item>
						</block>
					</tui-grid>
				</view>
			</view>
		</view>
		<t-color-picker ref="colorPicker" :color="color" @confirm="confirm()"></t-color-picker>

		<view class="flow">
			<WaterfallFlow></WaterfallFlow>
		</view>
	</view>
</template>

<script>
	import tColorPicker from '@/subPages/components/t-color-picker/t-color-picker.vue'
	import WaterfallFlow from '@/components/waterfallFlow/waterfallFlow.vue'
	import {
		mapState,
		mapActions
	} from 'vuex'

	export default {
		components: {
			tColorPicker,
			WaterfallFlow
		},
		computed: {
			...mapState(['icon'])
		},
		data() {
			return {
				restoreDefaultIconColorModal: false,
				restoreDefaultButton: [{
						text: "取消",
						type: "primary",
						plain: true
					},
					{
						text: "确定",
						type: "primary",
						plain: false
					}
				],
				scrollTop: 0,
				color: {
					r: 255,
					g: 0,
					b: 0,
					a: 0.6
				}
			};
		},
		onPageScroll(e) {
			this.scrollTop = e.scrollTop
		},
		methods: {
			...mapActions(['setIconColorAsync', 'restoreDefaultIconColor']),
			open(item) {
				// 打开颜色选择器
				this.$refs.colorPicker.open();
			},
			confirm(e) {
				this.color = e.hex
				this.setIconColorAsync(e.hex)
			},
			// 确定恢复默认颜色
			determineRestoreDefault() {
				this.restoreDefaultIconColorModal = false
				this.restoreDefaultIconColor()
			}
		}
	}
</script>

<style lang="scss">
	.container {
		width: 100%;
		height: 100vh;
		display: flex;
		flex-direction: column;
		background-color: #f6f6f6;

		.button {
			margin: 0 auto;
			margin-top: 10rpx;
			display: flex;
			flex-direction: row;
			
			.top-button{
				width: 520rpx;
				margin: 0 auto;
				display: flex;
				flex-direction: row;
			}

			.tui-modal-custom {
				display: flex;
				flex-direction: column;

				.tui-modal-custom-text {
					margin: 0 auto;
					margin-top: 20rpx;
					margin-bottom: 50rpx;
				}

				.tui-modal-custom-button {
					display: flex;
					flex-direction: row;
					margin: 0 auto;
				}
			}
		}

		.icon_preview {
			width: 95%;
			margin: 0 auto;
			margin-top: 20rpx;
			border-radius: 10rpx;
			background-color: #fff;

			.icon_preview_item {
				width: 100%;
				display: flex;
				flex-direction: column;

				.icon_preview_title {
					padding: 20rpx;
					font-size: 15px;
					font-weight: bold;
				}

				.icon_preview_list {
					width: 95%;

					.grid-text {
						font-size: 12px;
						color: #626468;
						padding: 10rpx 0 20rpx 0rpx;
					}

				}

				.order-grid {
					text-align: center;

					.grid-title {
						font-size: 11px;
					}
				}

				.utils-grid {
					text-align: center;

					.utils-grid-title {
						font-size: 12px;
					}
				}
			}
		}

		.flow {
			width: 100%;
			margin: 0 auto;
			margin-top: 40rpx;
		}
	}
</style>
