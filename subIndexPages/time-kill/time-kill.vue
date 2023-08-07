<template>
	<view class="container">
		<view class="box">
			<view class="countdown">
				<CountDown :startDate="startTime" :endDate="endTime" />
			</view>
			<view class="commodity-box" v-for="(item,index) in timeKillCommodityData" :key="index">
				<view class="image">
					<image :src="item.commodityImageUrl" style="width: 100px;height: 100px" mode=""></image>
				</view>
				<view class="content">
					<view class="title">
						{{item.commodityName}}
					</view>
					<view class="button">
						<view class="price">
							<text style="font-size: 9px;">￥</text>
							<text>{{item.retailPrice}}</text>
						</view>
						<view class="buy-button" >
							<tui-button type="danger" @click="toCommodityDetail(item)" width="80rpx" :size="24" height="60rpx">抢购</tui-button>
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import { getTimeKillCommodityPage,getProgressPromotionActivity } from '@/api/merkete/merkete.js'
	import CountDown from '@/subIndexPages/time-kill/countDown/countDown.vue'
	export default {
		components: {
			CountDown
		},
		data() {
			return {
				startTime: '2023-04-17 17:55:00',
				endTime: '2023-04-17 19:54:00',
				currentPage: 1,
				pageSize: 10,
				totalPage: 1,
				timeKillCommodityData: []
			}
		},
		onLoad() {
			this.selectProgressPromotionActivity()
			this.selectTimeKillCommodityPage()
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
			this.selectTimeKillCommodityPage()
		},
		methods: {
			toCommodityDetail(item){
				console.log('-----------------')
				console.log(item)
				uni.navigateTo({
					url:'subIndexPages/commodityDetail/commodityDetail?commodityId=' + item.commodityId
				})
			},
			async selectTimeKillCommodityPage(){
				const { data:res } = await getTimeKillCommodityPage(this.currentPage, this.pageSize)
				if (res.code == 0) {
					res.data.map(d => {
						this.timeKillCommodityData.push(d)
					})
					this.totalPage = res.data.total
				}
			},
			async selectProgressPromotionActivity(){
				const {data:res} = await getProgressPromotionActivity()
				if(res.code == 0){
					this.startTime = res.data.startTime
					this.endTime = res.data.endTime
				}
			}
		}
	}
</script>

<style lang="scss">
	page{
		background-color: #ff0000;
	}
.container{
	width: 100%;
	display: flex;
	flex-direction: column;
	
	.box{
		width: 100%;
		background-color: #f6f6f6;
		// height: 300rpx;
		border-radius: 20px 20rpx 0 0;
		margin-top: 20rpx;
		
		.countdown{
			width: 90%;
			// height: 50px;
			margin: 0 auto;
			margin-top: 30rpx;
		}
		
		.commodity-box{
			width: 90%;
			background-color: #fff;
			height: 120px;
			padding: 30rpx;
			margin: 0 auto;
			margin-top: 30rpx;
			margin-bottom: 20rpx;
			border-radius: 15rpx;
			display: flex;
			flex-direction: row;
			justify-content: space-around;
			align-items: center;
			
			.image{
				width: 35%;
				height: 95%;
				border-radius: 10rpx;
				display: flex;
				align-items: center;
				justify-content: center;
			}
			
			.content{
				width: 60%;
				height: 95%;
				display: flex;
				flex-direction: column;
				
				.title{
					font-size: 13px;
					margin-top: 10rpx;
					margin-left: 5rpx;
					font-weight: bold;
					// word-break: break-all;
					// overflow: hidden;
					// text-overflow: ellipsis;
					// white-space: nowrap;
				}
				
				.button{
					margin-top: 40rpx;
					display: flex;
					flex-direction: row;
					justify-content: space-between;
					align-items: center;
					
					.price{
						font-size: 15px;
						color: #ff0000;
						font-weight: bold;
					}
				}
			}
		}
	}
}
</style>
