<template>
	<view class="container">
		<view class="order-box">
			<tui-sticky :scrollTop="scrollTop" stickyHeight="80rpx">
				<template v-slot:header>
					<view class="order-class">
						<tui-tabs :tabs="tabs" :currentTab="currentTab" @change="orderClassChange"></tui-tabs>
					</view>
				</template>
			</tui-sticky>
			<view class="order-content-box">
				<view class="order-all" v-show="currentTab == 0 ? true : false">
					<orderAll :allOrder="allOrder" />
				</view>
				<view class="order-paid" v-show="currentTab == 1 ? true : false">
					<orderPaid :pendingPaymentOrder="pendingPaymentOrder" />
				</view>
				<view class="order-shipped" v-show="currentTab == 2 ? true : false">
					<orderShipped :shippedOrder="shippedOrder" />
				</view>
				<view class="order-received" v-show="currentTab == 3 ? true : false">
					<orderReceived :receivedOrder="receivedOrder" />
				</view>
				<view class="order-complete" v-show="currentTab == 4 ? true : false">
					<orderComplete :completedOrder="completedOrder" />
				</view>
				<view class="order-after-sales" v-show="currentTab == 5 ? true : false">
					<afterSales :afterSalesOrder="afterSalesOrder" />
				</view>
			</view>
			<view class="commodity-list">
				<tui-divider height="30" gradual dividerColor="red">为您推荐</tui-divider>
				<view class="list">
					<WaterfallFlow :commodityList="indexShowCommodityData" />
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import orderAll from '@/subPages/personal-pages/order/orderAll/orderAll.vue'
	import orderPaid from '@/subPages/personal-pages/order/orderPaid/orderPaid.vue'
	import orderShipped from '@/subPages/personal-pages/order/orderShipped/orderShipped.vue'
	import orderReceived from '@/subPages/personal-pages/order/orderReceived/orderReceived.vue'
	import orderComplete from '@/subPages/personal-pages/order/orderComplete/orderComplete.vue'
	import afterSales from '@/subPages/personal-pages/order/afterSales/afterSales.vue'
	import { getUserOrderList } from '@/api/pay/pay.js'
	import { getIndexShowCommodity } from '@/api/commodity/commodity.js'
	import WaterfallFlow from '@/components/waterfallFlow/waterfallFlow.vue'
	import {
		mapState,
		mapActions,
		mapGetters
	} from 'vuex'
	export default {
		components: {
			orderAll,
			orderPaid,
			orderShipped,
			orderReceived,
			orderComplete,
			afterSales,
			WaterfallFlow
		},
		computed: {
			...mapState(['user'])
		},
		data() {
			return {
				indexShowCommodityData: [],
				currentPage: Math.floor(Math.random() * 7) + 1,
				pageSize: 10,
				totalPage: 1,
				scrollTop: 0,
				currentTab: 0,
				userId: '',
				allOrder: [],
				pendingPaymentOrder: [],
				shippedOrder: [],
				receivedOrder: [],
				completedOrder: [],
				afterSalesOrder: [],
				tabs: [{
					name: "全部"
				}, {
					name: "待付款"
				}, {
					name: "待发货"
				}, {
					name: "待收货"
				}, {
					name: "已完成"
				}, {
					name: "售后"
				}]
			};
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
		onPageScroll(e) {
			this.scrollTop = e.scrollTop
		},
		async onLoad(option) {
			this.getCommodityIndexShow()
			this.userId = this.getUserInfo().userId
			// 获取当前标签页
			this.currentTab = decodeURIComponent(option.currentTabIndex)
			const { data:res } = await getUserOrderList(this.userId )
			if(res.code == 0){
				this.allOrder = []
				res.data.map(o => {
					this.allOrder.push(o)
					if(o.orderStatus == 0){
						this.pendingPaymentOrder.push(o)
					}
					if(o.orderStatus == 1){
						this.shippedOrder.push(o)
					}
					if(o.orderStatus == 2){
						this.receivedOrder.push(o)
					}
					if(o.orderStatus == 3){
						this.completedOrder.push(o)
					}
					if(o.orderStatus == 4){
						this.afterSalesOrder.push(o)
					}
				})
			}
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
			orderClassChange(e) {
				this.currentTab = e.index
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

		.order-content-box {
			width: 100%;
			display: flex;
			margin: 0 auto;
			margin-bottom: 60rpx;
			margin-top: 10rpx;
			border-radius: 8rpx;
			background-color: #f6f6f6;

			.order-all {
				width: 100%;
			}

			.order-paid {
				width: 100%;
			}

			.order-shipped {
				width: 100%;
			}

			.order-received {
				width: 100%;
			}

			.order-complete {
				width: 100%;
			}
			
			.order-after-sales{
				width: 100%;
			}
		}
		
		.commodity-list{
			width: 95%;
			height: 100%;
			margin: 0 auto;
			margin-top: 40rpx;
			
			.list{
				width: 100%;
				height: 100%;
				margin-top: 20rpx;
			}
		}
	}
</style>
