<template>
	<view class="container">
		<view class="top">
			<view class="notice-bar">
				<tui-sticky :scrollTop="scrollTop" stickyHeight="80rpx">
					<template v-slot:header>
						<u-notice-bar :text="noticeBarDataList" direction="column" fontSize="20"
							 bgColor="#f6003c" duration="2000" color="#fff"></u-notice-bar>
					</template>
				</tui-sticky>
			</view>
			<view class="search">
				<tui-dropdown-list :show="dropdownShow" :top="94" :height="400">
					<template v-slot:selectionbox>
						<u-search v-model="searchValue" @change="searchChange" :showAction="false" bgColor="#fff" shape="round" :clearabled="true"
							height="60"></u-search>
					</template>
					<template v-slot:dropdownbox >
						<view class="dropdown-box">
							<view class="search-result-item" v-for="(item,index) in searchResultData" :key="index">
								<text style="font-size: 10px;padding: 10rpx;height: 40rpx;line-height: 40rpx;" @click="searchToDetail(item)">{{item.commodityName.slice(0, 16)}}</text>
							</view>
						</view>
					</template>
				</tui-dropdown-list>
			</view>
			<view class="swiper">
				<u-swiper :list="swperCommodityData" @click="swperCommodityDataClick" height="280"
					keyName="commodityImageUrl" showTitle :autoplay="true" circular>
				</u-swiper>
			</view>
		</view>
		<!-- <view class="utils">
			<u-grid :border="false" col="4">
				<u-grid-item v-for="(item,index) in icon.indexIconList" :key="index" @click="clickGridItem(item,index)">
					<u-icon :name="item.name" :size="icon.indexIconSize" :color="icon.iconColor"></u-icon>
					<text class="grid-text">{{item.title}}</text>
				</u-grid-item>
			</u-grid>
			<u-toast ref="uToast" />
		</view> -->
		<view class="promotion">
			<view class="back-item back-item-left">
				<view class="back-item-top seckill-box">
					<view class="seckill" @click="toTimeKill">
						<view class="seckill-top">
							<view class="seckill-top-title"><text>秒杀专场</text></view>
							<view class="seckill-top-icon">
								<tui-icon name="arrowright" :size="20" :bold="true"></tui-icon>
							</view>
						</view>
						<view class="seckill-bottom"><text>好货捡漏，限量秒杀</text></view>
					</view>
					<view class="seckill-icon">
						<image :src="seckillSrc" style="width: 100%;height: 100%;"></image>
					</view>
				</view>
				<view class="seckill-buttom">
					<view class="back-item-top bg-white seckill-one" v-for="(item,index) in limitedTimeFlashKillCommodityData" :key="index" @click="toTimeKillDetail(item)">
						<view class="seckill-one-left">
							<image
								:src="item.commodityImageUrl"
								style="width: 100%;height: 100%;"></image>
						</view>
						<view class="seckill-one-right">
							<view class="seckill-goods-title">
								<text>{{ item.commodityName }}</text>
							</view>
							<view class="seckill-goods-price">
								<view class="seckill-goods-price-new">
									<text class="seckill-goods-price-new-icon">￥</text>
									<text class="seckill-goods-price-new-price">{{item.retailPrice}}</text>
								</view>
								<view class="seckill-goods-price-old">
									<text>￥{{item.retailPrice}}</text>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
			<view class="back-item">
				<view class="back-item-top-right">
					<view class="back-right-top">
						<text class="back-right-top-max">热卖商品</text>
						<text class="back-right-top-min">惊喜来袭</text>
					</view>
					<view class="back-right-buttom">
						<view class="back-right-buttom-item back-right-buttom-left">
							<view class="back-right-goods" @click="openBestSellersCommodity(bestSellersCommodityData[1])">
								<image
									:src="bestSellersCommodityData[0].commodityImageUrl"
									style="width: 100%;height: 100%;"></image>
							</view>
							<view class="back-right-goods-price">
								<text class="price-icon">￥</text>
								<text class="price-num"
									v-if="bestSellersCommodityData[0].salesModel == 0">{{bestSellersCommodityData[0].retailPrice}}</text>
								<text class="price-num"
									v-if="bestSellersCommodityData[0].salesModel == 1">{{bestSellersCommodityData[0].wholesalePrice}}</text>
							</view>
						</view>
						<view class="back-right-buttom-item ">
							<view class="back-right-goods" @click="openBestSellersCommodity(bestSellersCommodityData[1])">
								<image
									:src="bestSellersCommodityData[1].commodityImageUrl"
									style="width: 100%;height: 100%;"></image>
							</view>
							<view class="back-right-goods-price">
								<text class="price-icon">￥</text>
								<text class="price-num"
									v-if="bestSellersCommodityData[1].salesModel == 0">{{bestSellersCommodityData[1].retailPrice}}</text>
								<text class="price-num"
									v-if="bestSellersCommodityData[1].salesModel == 1">{{bestSellersCommodityData[1].wholesalePrice}}</text>
							</view>
						</view>
					</view>
				</view>
				<view class="back-item-top-right back-item-top-right-two">
					<view class="back-right-top">
						<text class="back-right-top-max">新品推荐</text>
						<text class="back-right-top-min">最近上新</text>
					</view>
					<view class="back-right-buttom">
						<view class="back-right-buttom-item back-right-buttom-left">
							<view class="back-right-goods" @click="openRecommendCommodity(recommendCommodityData[0])">
								<image :src="recommendCommodityData[0].commodityImageUrl"
									style="width: 100%;height: 100%;"></image>
							</view>
							<view class="back-right-goods-price">
								<text class="price-icon">￥</text>
								<text class="price-num"
									v-if="recommendCommodityData[0].salesModel == 0">{{recommendCommodityData[0].retailPrice}}</text>
								<text class="price-num"
									v-if="recommendCommodityData[0].salesModel == 1">{{recommendCommodityData[0].wholesalePrice}}</text>
							</view>
						</view>
						<view class="back-right-buttom-item ">
							<view class="back-right-goods" @click="openRecommendCommodity(recommendCommodityData[1])">
								<image :src="recommendCommodityData[1].commodityImageUrl"
									style="width: 100%;height: 100%;"></image>
							</view>
							<view class="back-right-goods-price">
								<text class="price-icon">￥</text>
								<text class="price-num"
									v-if="recommendCommodityData[1].salesModel == 0">{{recommendCommodityData[1].retailPrice}}</text>
								<text class="price-num"
									v-if="recommendCommodityData[1].salesModel == 1">{{recommendCommodityData[1].wholesalePrice}}</text>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<!-- <view class="quick">
			<view class="quick-item">

			</view>
			<view class="quick-item-right">
				<view class="quick-item-top">

				</view>
				<view class="quick-item-buttom">

				</view>
			</view>
		</view> -->
		<!-- <tui-divider></tui-divider> -->
		<view class="flow">
			<WaterfallFlow :commodityList="indexShowCommodityData" />
		</view>
		<view>
			<tui-scroll-top :scrollTop="toScrollTop" :topIcon="topIcon" :bottom="bottom"></tui-scroll-top>
		</view>
	</view>
</template>

<script>
	import {
		getCommodityRecommend,
		getRotationChartCommodity,
		getIndexShowCommodity,
		searchCommodity,
		getBestSellersCommodity
	} from '@/api/commodity/commodity.js'
	import {getLimitedTimeFlashKillCommodity} from '@/api/merkete/merkete.js'
	import WaterfallFlow from '@/components/waterfallFlow/waterfallFlow.vue'
	import {
		mapState
	} from 'vuex'

	export default {
		computed: {
			...mapState(['icon']),
			...mapState(['user']),
			...mapState(['notice']),
		},
		components: {
			WaterfallFlow
		},
		data() {
			return {
				dropdownShow: false,
				searchResultData: [],
				recommendCommodityData: [],
				recommendCommodityData: [],
				bestSellersCommodityData: [],
				limitedTimeFlashKillCommodityData: [],
				swperCommodityData: [],
				indexShowCommodityData: [],
				bottom: 100,
				toScrollTop: 0,
				topIcon: "/static/icon/topIcon.png",
				seckillSrc: 'https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/time-kill.png',
				scrollTop: 0,
				// 搜索
				searchValue: '',
				currentPage: 1,
				pageSize: 10,
				totalPage: 1,
				// 滚动通知数据
				noticeBarDataList: [],
				noticeBarSize: 6
			}
		},
		onLoad() {
			this.getRecommendCommodity()
			this.selectBestSellersCommodity()
			this.selectLimitedTimeFlashKillCommodity()
			this.getCommodityRotationChart()
			this.getCommodityIndexShow()
			this.removeNoticeBarData()
			this.initNoticeBarData()
		},
		onPageScroll(e) {
			this.scrollTop = e.scrollTop
			this.toScrollTop = e.scrollTop
		},
		onPullDownRefresh() {
			let _this = this
			_this.currentPage = Math.floor(Math.random() * (_this.totalPage -1)) + 1
			_this.indexShowCommodityData = []
			_this.getCommodityIndexShow()
			this.initNoticeBarData()
			this.getRecommendCommodity()
			this.selectBestSellersCommodity()
			this.selectLimitedTimeFlashKillCommodity()
			this.getCommodityRotationChart()
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
		methods: {
			toTimeKill(){
				uni.navigateTo({
					url: 'subIndexPages/time-kill/time-kill'
				})
			},
			initNoticeBarData(){
				// 用户已登录，获取信息通知
				if(this.user.isLogin){
					let balanceData = this.notice.balanceData
					let integralData = this.notice.integralData
					let purchaseVipData = this.notice.purchaseVipData
					if(balanceData.length  > 0){
						let index = balanceData.length - 1
						let balanceText = ''
						if(balanceData[index].changeType == 0){
							 balanceText = "您于" + balanceData[index].changeTime + "充值" + balanceData[index].changeQuota + "元余额"
						}else{
							balanceText = "您于" + balanceData[index].changeTime + "扣减" + balanceData[index].changeQuota + "元余额"
						}
						this.noticeBarDataList.push(balanceText)
					}
					if(integralData.length > 0){
						let index = integralData.length - 1
						let balanceText = ''
						if(integralData[index].changeType == 0){
							 balanceText = "您于" + integralData[index].changeTime + "充值" + integralData[index].changeQuota + "点积分"
						}else{
							balanceText = "您于" + integralData[index].changeTime + "扣减" + integralData[index].changeQuota + "点积分"
						}
						this.noticeBarDataList.push(balanceText)
					}
					if(purchaseVipData.length > 0){
						let index = purchaseVipData.length - 1
						let balanceText = "恭喜您于" + purchaseVipData[index].currentTime + "成功开通付费会员"
						this.noticeBarDataList.push(balanceText)
					}
				}
			},
			// 获取新品推荐商品
			async getRecommendCommodity() {
				const {
					data: res
				} = await getCommodityRecommend()
				if (res.code == 0) {
					this.recommendCommodityData = res.data	
				}
				
				let balanceText = this.recommendCommodityData[1].commodityName + "于" +  this.recommendCommodityData[1].putShelfTime + "上架啦"
				this.noticeBarDataList.push(balanceText)
				this.removeNoticeBarData()
			},
			// 获取限时秒杀商品
			async selectLimitedTimeFlashKillCommodity(){
				const {data:res} = await getLimitedTimeFlashKillCommodity()
				if(res.code == 0){
					this.limitedTimeFlashKillCommodityData = res.data
				}
			},
			// 获取热卖商品
			async selectBestSellersCommodity(){
				const { data:res } = await getBestSellersCommodity()
				if (res.code == 0) {
					this.bestSellersCommodityData = res.data
				}
			},
			// 获取首页展示的商品
			async getCommodityIndexShow() {
				const {
					data: res
				} = await getIndexShowCommodity(this.currentPage, this.pageSize)
				if (res.code == 0) {
					res.data.records.map(d => {
						this.indexShowCommodityData.push(d)
					})
					this.totalPage = res.data.total
					let index = Math.floor(Math.random() * (this.pageSize -1)) - 1
					if(index < 0){
						index = 0
					}
					let balanceText = this.indexShowCommodityData[index].commodityName + "于" +  this.indexShowCommodityData[index].putShelfTime + "上架啦"
					this.noticeBarDataList.push(balanceText)
					this.removeNoticeBarData()
				}
			},
			// 清除滚动通知条数
			removeNoticeBarData(){
				if(this.noticeBarDataList.length > this.noticeBarSize){
					let removeNoticeBarData = this.noticeBarDataList[0]
					this.noticeBarDataList.splice(removeNoticeBarData,1)
				}
			},
			// 获取轮播图商品
			async getCommodityRotationChart() {
				const {
					data: res
				} = await getRotationChartCommodity()
				if (res.code == 0) {
					this.swperCommodityData = res.data
					this.swperCommodityData.map(c => {
						c.title = c.commodityName
					})
				}
			},
			// 点击轮播图跳转详情页
			swperCommodityDataClick(e) {
				uni.navigateTo({
					url: 'subIndexPages/commodityDetail/commodityDetail?commodityId=' + this.swperCommodityData[e]
						.commodityId
				})
			},
			// 点击秒杀商品跳转详情页
			toTimeKillDetail(e) {
				uni.navigateTo({
					url: 'subIndexPages/commodityDetail/commodityDetail?commodityId=' +  e.commodityId
				})
			},
			// 新品推荐商品点击跳转详情页
			openRecommendCommodity(e) {
				uni.navigateTo({
					url: 'subIndexPages/commodityDetail/commodityDetail?commodityId=' + e.commodityId
				})
			},
			// 热卖商品点击跳转详情页
			openBestSellersCommodity(e) {
				uni.navigateTo({
					url: 'subIndexPages/commodityDetail/commodityDetail?commodityId=' + e.commodityId
				})
			},
			clickGridItem(item, index) {
				console.log(item, index)
			},
			click(name) {
				this.$refs.uToast.success(`点击了第${name}个`)
			},
			async searchChange(e){
				if(e != ''){
					const {data:res} = await searchCommodity(e)
					this.searchResultData = res.data
					this.dropdownShow = true
				}
			},
			searchToDetail(item){
				uni.navigateTo({
					url:'subIndexPages/commodityDetail/commodityDetail?commodityId=' + item.commodityId
				})
				setTimeout(() =>{
					this.dropdownShow = false
					this.searchValue = ''
				},2000)
			}
		}
	}
</script>

<style scoped lang="scss">
	.container {
		display: flex;
		flex-direction: column;
		width: 100%;
		// height: 100vh;
		background-color: #f6f6f6;

		.top {
			display: flex;
			flex-direction: column;
			background-color: #f6003c;
			height: 450rpx;

			.notice-bar {
				height: 30rpx;
			}

			.search {
				width: 95%;
				height: 70rpx;
				margin: 0 auto;
				margin-top: 45rpx;
				
				.dropdown-box{
					width: 100%;
					background-color: #fff;
					z-index: 999;
					padding: 20rpx;
					border-radius: 10rpx;
					
					.search-result-item{
						width: 90%;
						margin: 0 auto;
						display: flex;
						
					}
					
					.search-result-item:hover{
						background-color: #f6f6f6;
					}
				}
			}

			.swiper {
				width: 95%;
				height: 300rpx;
				margin: 0 auto;
				margin-top: 30rpx;
				border-radius: 10rpx;
			}

		}

		.utils {
			width: 95%;
			height: 240rpx;
			margin: 0 auto;
			margin-top: 20rpx;
			border-radius: 10rpx;
			background-color: #fff;

			.grid-text {
				font-size: 14px;
				color: #626468;
				padding: 10rpx 0 20rpx 0rpx;
			}
		}

		.promotion {
			width: 95%;
			display: flex;
			flex-direction: row;
			margin: 0 auto;
			margin-top: 20rpx;

			.back-item-left {
				margin-right: 20rpx;
				background: -webkit-linear-gradient(to right, rgb(250, 233, 219), rgb(255, 255, 254));
				background: linear-gradient(to right, rgb(250, 233, 219), rgb(255, 255, 254));

			}

			.back-item {
				width: 48%;
				display: flex;
				flex-direction: column;

				.seckill-box {
					display: flex;
					flex-direction: row;
				}

				.back-item-top {
					width: 100%;
					height: 130rpx;

					.seckill {
						width: 70%;
						display: flex;
						flex-direction: column;

						.seckill-top {
							display: flex;
							flex-direction: row;
							margin-top: 13rpx;
							margin-left: 13rpx;

							.seckill-top-title {
								font-size: 14px;
								font-weight: bold;
							}
						}

						.seckill-buttom {
							display: flex;
							flex-direction: column;
							border-radius: 10rpx;
							background-color: #fff;
						}

						.seckill-bottom {
							font-size: 11px;
							margin-top: 15rpx;
							margin-left: 13rpx;
						}
					}

					.seckill-icon {
						animation: ping 2s cubic-bezier(0, 0, 0.2, 1) infinite;
						
						@keyframes ping {
						  50%, 100% {
						    transform: scale(1);
						    opacity: 0.1;
						  }
						}
						width: 20%;
						height: 60rpx;
						margin-top: 30rpx;
						margin-left: 10rpx;
					}
				}

				.bg-white {
					background-color: #fff;
				}

				.seckill-one-top {
					border-radius: 20rpx 0 0 0;
				}

				.seckill-one {
					display: flex;
					flex-direction: row;

					.seckill-one-left {
						width: 30%;
						height: 100rpx;
						margin-top: 20rpx;
						margin-left: 20rpx;
						border-radius: 5rpx;
						background-color: #f9f9f9;
					}

					.seckill-one-right {
						width: 50%;
						height: 100rpx;
						margin-top: 10rpx;
						margin-left: 15rpx;

						.seckill-goods-title {
							font-size: 11px;
							font-weight: bold;
							margin-top: 15rpx;
							margin-bottom: 25rpx;
							word-break: break-all;
							overflow: hidden;
							text-overflow: ellipsis;
							white-space: nowrap;
						}

						.seckill-goods-price {
							font-size: 10px;
							display: flex;
							flex-direction: row;

							.seckill-goods-price-new {
								display: flex;
								flex-direction: row;
								color: red;

								.seckill-goods-price-new-icon {
									font-size: 9px;
								}

								.seckill-goods-price-new-price {
									font-weight: bold;

								}
							}

							.seckill-goods-price-old {
								margin-left: 30rpx;
								text-decoration: line-through;
								font-size: 9px;
								color: #d0d0d0;
							}

						}
					}
				}

				.back-item-top-right {
					width: 100%;
					height: 255rpx;
					background: -webkit-linear-gradient(to right, rgb(255, 245, 242), rgb(255, 255, 254));
					background: linear-gradient(to right, rgb(255, 245, 242), rgb(255, 255, 254));
					display: flex;
					flex-direction: column;

					.back-right-top {
						width: 90%;
						height: 50rpx;

						.back-right-top-max {
							font-size: 14px;
							font-weight: bold;
							margin-top: 15rpx;
							margin-left: 10rpx;
						}

						.back-right-top-min {
							font-size: 11px;
							color: red;
							margin-left: 20rpx;
						}
					}

					.back-right-buttom {
						width: 95%;
						height: 173rpx;
						display: flex;
						flex-direction: row;
						margin: 0 auto;
						margin-top: 15rpx;
						background-color: #fff;
						border-radius: 10rpx;

						.back-right-buttom-item {
							width: 50%;
							height: 150rpx;
							display: flex;
							flex-direction: column;
							margin-top: 10rpx;

							.back-right-goods {
								width: 85%;
								height: 110rpx;
								margin: 0 auto;
								border-radius: 10rpx;
							}

							.back-right-goods-price {
								width: 85%;
								height: 40rpx;
								margin: 0 auto;
								color: red;
								text-align: center;

								.price-icon {
									font-size: 10px;
								}

								.price-num {
									font-size: 12px;
									font-weight: bold;
								}
							}
						}

						.back-right-buttom-left {
							margin-right: 10rpx;
						}
					}
				}

				.back-item-top-right-two {
					margin-top: 20rpx;
				}
			}
		}

		.quick {
			width: 95%;
			height: 500rpx;
			margin: 0 auto;
			margin-top: 20rpx;
			display: flex;
			flex-direction: row;

			.quick-item {
				width: 50%;
				margin-right: 20rpx;
				background-color: #fff;
			}

			.quick-item-right {
				width: 50%;
				display: flex;
				flex-direction: column;

				.quick-item-top {
					height: 48%;
					background-color: #fff;
				}

				.quick-item-buttom {
					height: 48%;
					background-color: #fff;
					margin-top: 20rpx;
				}
			}
		}

		.flow {
			margin: 0 auto;
			// margin-top: 10rpx;
		}
	}
</style>
