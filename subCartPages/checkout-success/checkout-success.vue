<template>
	<view class="con-box">
		<view class="top">
			<view class="top-right" @click="backCart">
				<text>返回购物车</text>
				<tui-icon name="arrowright" :size="20" ></tui-icon>
			</view>
		</view>
		<view class="top-two">
			<view class="image">
				<image style="width: 100%;height: 100%;" src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/notice/checkout-success.png" mode=""></image>
			</view>
			<view class="text">
				<text>恭喜你，下单成功！</text>
				<text style="margin-left: 10rpx;" @click="toOrderDetail">查看详情</text>
			</view>
		</view>
		<view class="commodity-list">
			<tui-divider height="30" gradual dividerColor="red">为您推荐</tui-divider>
			<view class="list">
				<WaterfallFlow :commodityList="indexShowCommodityData" />
			</view>
		</view>
	</view>
</template>

<script>
	import { getIndexShowCommodity } from '@/api/commodity/commodity.js'
	import WaterfallFlow from '@/components/waterfallFlow/waterfallFlow.vue'
	import {
		mapState,
		mapActions,
		mapGetters
	} from 'vuex'
	export default {
		computed: {
			...mapState(['user'])
		},
		components: {
			WaterfallFlow
		},
		data() {
			return {
				orderId: '',
				indexShowCommodityData: [],
				currentPage: Math.floor(Math.random() * 12) + 1,
				pageSize: 10,
				totalPage: 1,
			}
		},
		onPullDownRefresh() {
			let _this = this
			_this.currentPage = Math.floor(Math.random() * (_this.totalPage -1)) + 1
			_this.indexShowCommodityData = []
			_this.getCommodityIndexShow()
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
			this.getCommodityIndexShow()
		},
		onLoad(o) {
			this.orderId = o.orderId
			this.getCommodityIndexShow()
		},
		methods: {
			...mapGetters(['getUserInfo']),
			// 获取展示的商品
			async getCommodityIndexShow() {
				const {
					data: res
				} = await getIndexShowCommodity(this.currentPage, this.pageSize)
				if (res.code == 0) {
					res.data.records.map(d => {
						this.indexShowCommodityData.push(d)
					})
					this.totalPage = res.data.pages
				}
			},
			// 返回购物车
			backCart(){
				uni.switchTab({
					url: 'pages/cart/cart'
				})
			},
			toOrderDetail(){
				uni.redirectTo({
					url: 'subCartPages/order-detail/order-detail?orderId=' + this.orderId
				})
			}
		}
	}
</script>

<style lang="scss">
page{
	background-color: #f6f6f6;
}
.con-box{
	width: 100%;
	display: flex;
	flex-direction: column;
	padding: 10rpx;
	
	.top{
		width: 100%;
		display: flex;
		flex-direction: row;
		justify-content: flex-end;
		
		.top-right{
			font-size: 12px;
			margin-right: 30rpx;
			display: flex;
			align-items: center;
			justify-content: center;
		}
	}
	
	.top-two{
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		
		.image{
			width: 50px;
			height: 50px;
		}
		
		.text{
			margin-top: 20rpx;
			font-size: 13px;
		}
	}
	
	.commodity-list{
		width: 95%;
		margin: 0 auto;
		margin-top: 40rpx;
		
		.list{
			margin-top: 20rpx;
		}
	}
}
</style>
