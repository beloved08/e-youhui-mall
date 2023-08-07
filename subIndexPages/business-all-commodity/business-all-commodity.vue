<template>
	<view>
		<WaterfallFlow :commodityList="commodityListData" />
	</view>
</template>

<script>
	import { getBusinessById } from '@/api/merchantStores/merchantStores.js'
	import { getCommodityByBusinessIdPage } from '@/api/commodity/commodity.js'
		import WaterfallFlow from '@/components/waterfallFlow/waterfallFlow.vue'
	export default {
		components: {
			WaterfallFlow
		},
		data() {
			return {
				businessData: {},
				currentPage: 1,
				pageSize: 10,
				totalPage: 1,
				commodityListData: []
			}
		},
		async onLoad(o) {
			const { data:res } = await getBusinessById(o.businessId)
			this.businessData = res.data
			uni.setNavigationBarTitle({
				title: this.businessData.shopName
			})
			this.initBusinessCommodityList()
		},
		onPullDownRefresh() {
			let _this = this
			_this.currentPage = Math.floor(Math.random() * (_this.totalPage -1)) + 1
			_this.commodityListData = []
			_this.initBusinessCommodityList()
			uni.stopPullDownRefresh()
		},
		onReachBottom() {
			if (this.totalPage <= this.currentPage) {
				uni.showToast({
					title: '哥也是有底的',
					duration: 2000,
					icon: 'none'
				});
			}
			this.currentPage += 1
			this.initBusinessCommodityList()
		},
		methods: {
			async initBusinessCommodityList(){
				const { data:res } = await getCommodityByBusinessIdPage(this.businessData.businessId, this.currentPage,this.pageSize)
				if(res.code == 0){
					res.data.records.map(d => {
						this.commodityListData.push(d)
					})
					this.totalPage = res.data.pages
				}
			}
		}
	}
</script>

<style lang="scss">

</style>
