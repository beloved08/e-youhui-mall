<template>
	<view>
		<view class="promotion">
			全民促销
		</view>
		<view class="apply-button" @click="toApplyPromotion">
			<view class="apply">申请成为促销人员</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		methods: {
			toApplyPromotion(){
				uni.navigateTo({
					url: '/subPages/personal-pages/national-promotion/apply-promotion/apply-promotion'
				})
			}
		}
	}
</script>

<style lang="scss">
	.promotion {
		padding: 30rpx;
	}

	.apply-button {
		position: fixed;
		bottom: 0;
		height: 50px;
		width: 100%;
		line-height: var(--footer-height);

		.apply {
			line-height: 50px;
			background-color: #ff0000;
			color: #fff;
			text-align: center;
			font-size: 14px;
		}
	}
</style>
