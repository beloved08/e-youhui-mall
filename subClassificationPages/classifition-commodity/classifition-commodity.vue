<template>
	<view class="">
		<view class="empty" v-if="commodityList.length == 0">
			<u-empty mode="car" text="暂无该分类商品" textSize="24" iconSize="150" height="250" icon="http://cdn.uviewui.com/uview/empty/car.png">
			</u-empty>
		</view>
		<view class="" v-else>
			<WaterfallFlow :commodityList="commodityList" />
		</view>
	</view>
</template>

<script>
	import WaterfallFlow from '@/components/waterfallFlow/waterfallFlow.vue'
	import {
		getCommodityListByClassification
	} from '@/api/commodity/commodity.js'
	export default {
		components: {
			WaterfallFlow
		},
		data() {
			return {
				commodityList: []
			}
		},
		onLoad(options) {
			getCommodityListByClassification(options.classificationId).then(res => {
				if (res.data.code == 0) {
					this.commodityList = res.data.data
				}
			})
		}

	}
</script>

<style scoped>
	.empty{
		margin-top: 100px;
	}
</style>
