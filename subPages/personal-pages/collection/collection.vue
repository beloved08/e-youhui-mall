<template>
	<view class="container">
		<tui-sticky :scrollTop="scrollTop" stickyHeight="80rpx">
			<template v-slot:header>
				<view class="subsection-box">
					<u-subsection :list="subsectionList" fontSize="16" mode="button" activeColor="#000" bgColor="#9fc6ff" :current="currentSubsection" @change="sectionChange"></u-subsection>
				</view>
			</template>
		</tui-sticky>
		<template v-if="currentSubsection == 0 ? true : false">
			<view class="collect-content" v-if="commodityCollectList.length > 0">
				<view class="collect-content-item" v-for="(item,index) in commodityCollectList" :key="index">
					<view class="collect-image" @click="toCommodityDetail(item)">
						<image
							:src="item.commodity.commodityImageUrl"
							style="width: 100%;height: 100%; border-radius: 10rpx;"></image>
					</view>
					<view class="collect-desc">
						<view class="collect-top">
							<view class="collect-title">
								<text>{{item.commodity.commodityName}}</text>
							</view>
						</view>
						<view class="collect-action">
							<view class="collect-price">
								<text class="collect-price-unit">￥</text>
								<text class="collect-price-num" v-if="item.commodity.salesModel == 0">{{item.commodity.retailPrice}}</text>
								<text class="collect-price-num" v-else>{{item.commodity.wholesalePrice}}</text>
							</view>
							<view class="collect-cancel" @click="cancelCollection(item)">
								<text>取消收藏</text>
							</view>
						</view>
					</view>
				</view>
			</view>
			<view class="collect-content" v-else>
				<view class="empty" style="width: 200rpx;height: 200rpx;">
					<u-empty
					text="暂无商品收藏"
					        mode="car"
					        icon="http://cdn.uviewui.com/uview/empty/car.png"
					>
					</u-empty>
				</view>
			</view>
		</template>
		<template  v-else>
			<view class="collect-content" v-if="businessCollectList.length > 0">
				<view class="collect-content-item" v-for="(item,index) in businessCollectList" :key="index">
					<view class="collect-image" @click="toBusinessDetail(item)">
						<image
							:src="item.business.businessLogo"
							style="width: 100%;height: 100%; border-radius: 10rpx;"></image>
					</view>
					<view class="collect-desc">
						<view class="collect-top">
							<view class="collect-title">
								<text>{{item.business.calloutContent}}</text>
							</view>
						</view>
						<view class="collect-action">
							<view class="collect-price">
								<text class="collect-price-num">{{item.collectTime.split(" ")[0]}}</text>
							</view>
							<view class="collect-cancel" @click="cancelCollection(item)">
								<text>取消收藏</text>
							</view>
						</view>
					</view>
				</view>
			</view>
			<view class="collect-content " v-else>
				<view class="empty" style="width: 200rpx;height: 200rpx;">
					<u-empty
					text="暂无商家店铺收藏"
					        mode="car"
					        icon="http://cdn.uviewui.com/uview/empty/car.png"
					>
					</u-empty>
				</view>
			</view>
		</template>
	</view>
</template>

<script>
	import { getUserCollectList,cancelCollect } from '@/api/user/user.js'
	import {
		mapState,
		mapActions,
		mapGetters
	} from 'vuex'
	export default {
		computed: {
			...mapState(['user']),
		},
		data() {
			return {
				userId: '',
				scrollTop: 0,
				subsectionList: ['商品收藏', '店铺收藏'],
				currentSubsection: 0,
				commodityCollectList: [],
				businessCollectList: [],
			};
		},
		onPageScroll(e) {
			this.scrollTop = e.scrollTop
		},
		 onLoad() {
			this.userId = this.getUserInfo().userId
			this.selectUserCollect()
		},
		onPullDownRefresh() {
			this.selectUserCollect()
			uni.stopPullDownRefresh()
		},
		methods: {
			...mapGetters(['getUserInfo']),
			// 获取用户收藏
			async selectUserCollect(){
				const { data:res } = await getUserCollectList(this.userId)
				this.commodityCollectList = []
				this.businessCollectList = []
				res.data.userCollectDtoList.map(c => {
					//判断当前收藏类型
					if(c.type == 1){
						// 商品收藏
						this.commodityCollectList.push(c)
					}else{
						this.businessCollectList.push(c)
					}
				})
			},
			toCommodityDetail(e){
				uni.navigateTo({
					url:'subIndexPages/commodityDetail/commodityDetail?commodityId=' + e.commodity.commodityId
				})
			},
			toBusinessDetail(e){
				uni.navigateTo({
					url:'subIndexPages/business-center/business-center?businessId=' + e.business.businessId
				})
			},
			sectionChange(index) {
				this.currentSubsection = index
			},
			// 取消收藏
			async cancelCollection(item){
				const { data:res } = await cancelCollect(item.collectId)
				if(res.code == 0){
					this.commodityCollectList = []
					this.businessCollectList = []
					this.selectUserCollect()
					uni.showToast({
						title: res.data,
						icon:'success',
						duration: 2000
					})
				}else{
					uni.showToast({
						title:res.msg,
						icon:'error',
						duration: 2000
					})
				}
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
		
		.subsection-box{
			width: 98%;
			margin: 0 auto;
		}
		
		.collect-content{
			width: 95%;
			margin: 0 auto;
			margin-top: 15rpx;
			margin-bottom: 20rpx;
			border-radius: 10rpx;
			display: flex;
			flex-wrap: wrap;
			
			.empty{
				height: 100vh;
				background-color: #f6f6f6;
				margin: 0 auto;
				display: flex;
				justify-content: center;
				align-items: center;
			}
			
			.collect-content-item{
				width: 40%;
				height: 380rpx;
				margin: 0 auto;
				padding: 15rpx;
				margin-top: 20rpx;
				background-color: #fff;
				border-radius: 10rpx;
				
				.collect-image{
					width: 90%;
					height: 73%;
					margin: 0 auto;
				}
				
				.collect-desc{
					display: flex;
					flex-direction: column;
					width: 93%;
					margin: 0 auto;
					margin-top: 20rpx;
					
					.collect-top{
						display: flex;
						flex-direction: row;
						width: 100%;
						
						.collect-title{
							width: 95%;
							margin: 0 auto;
							margin-left: 10rpx;
							font-size: 10px;
							overflow: hidden;
							text-overflow: ellipsis;
							white-space: nowrap;
						}
						
					}
					
					.collect-action{
						width: 90%;
						margin: 0 auto;
						margin-top: 10rpx;
						margin-bottom: 20rpx;
						display: flex;
						flex-direction: row;
						
						.collect-price{
							width: 60%;
							margin: 0 auto;
							color: red;
							
							.collect-price-unit{
								font-size: 11px;
							}
							
							.collect-price-num{
								font-size: 12px;
								font-weight: bold;
							}
						}
						
						.collect-cancel{
							width: 40%;
							margin: 0 auto;
							margin-top: 12rpx;
							font-size: 10px;
							color: red;
						}
					}
				}
			}
		}
	}
</style>
