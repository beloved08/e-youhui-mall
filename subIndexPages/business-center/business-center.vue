<template>
	<view class="business-box">
		<view class="business-top">
			<view class="logo">
				<image :src="businessData.businessLogo" style="width: 100%;height: 100%;" mode=""></image>
			</view>
			<view class="name">{{businessData.calloutContent}}</view>
			<view class="rate">
				<u-rate size="24" count="5"  :value="4.4"></u-rate>
				<text style="color: #ff0000;font-size: 11px;margin-left: 10rpx;">4.4</text>
			</view>
			
			<view class="desc">
				<u-button v-if="!isCollect" shape="circle" text="收藏" @click="userCollect" size="small" color="linear-gradient(to right, rgb(66, 83, 216), rgb(213, 51, 186))"></u-button>
				<u-button v-else shape="circle" text="已收藏" :disabled="true" size="small" type="primary" :plain="true"></u-button>
			</view>
			
			<view class="detail">
				<view class="detail-item">
					<view style="margin-right: 30rpx;">物流履约</view>
					<view style="margin-right: 15rpx;">4.3</view>
					<view style="width: 50%;margin-top: 8rpx;">
						<u-line-progress :percentage="80" :showText="false" height="15" inactiveColor="#eee" activeColor="#ff0000"></u-line-progress>
					</view>
					<view style="margin-left: 10rpx;font-size: 9px; color: red;">高</view>
				</view>
				<view class="detail-item">
					<view style="margin-right: 30rpx;">用户评价</view>
					<view style="margin-right: 15rpx;">4.2</view>
					<view style="width: 50%;margin-top: 8rpx;">
						<u-line-progress :percentage="70" :showText="false" height="15" inactiveColor="#eee" activeColor="#ff0000"></u-line-progress>
					</view>
					<view style="margin-left: 10rpx;font-size: 9px; color: red;">高</view>
				</view>
				<view class="detail-item">
					<view style="margin-right: 30rpx;">售后服务</view>
					<view style="margin-right: 15rpx;">3.6</view>
					<view style="width: 50%;margin-top: 8rpx;">
						<u-line-progress :percentage="50" :showText="false" height="15" inactiveColor="#eee" activeColor="#ff0000"></u-line-progress>
					</view>
					<view style="margin-left: 10rpx;font-size: 9px; color: red;">中</view>
				</view>
				<view class="detail-item">
					<view style="margin-right: 30rpx;">客服咨询</view>
					<view style="margin-right: 15rpx;">3.7</view>
					<view style="width: 50%;margin-top: 8rpx;">
						<u-line-progress :percentage="60" :showText="false" height="15" inactiveColor="#eee" activeColor="#ff0000"></u-line-progress>
					</view>
					<view style="margin-left: 10rpx;font-size: 9px; color: red;">中</view>
				</view>
			</view>
			
			<view class="detail">
				<view class="detail-item" style="margin-bottom: 40rpx;">
					<text style="margin-right: 30rpx;">所在城市</text>
					<text>{{businessData.businessCity}}</text>
				</view>
				<view class="detail-item" style="margin-bottom: 40rpx;">
					<text style="margin-right: 30rpx;">详细地址</text>
					<text>{{businessData.businessDetailAddress}}</text>
				</view>
				<view class="detail-item" style="margin-bottom: 30rpx;">
					<text style="margin-right: 30rpx;">店铺简介</text>
					<text>{{businessData.businessDescribe}}</text>
				</view>
			</view>
			
			<view class="all" @click="selectBusinessAllCommodity">
				<text class="all-commodity-text">查看全部商品</text>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		mapState
	} from 'vuex'
		import {addUserCollect, checkUserIsCollectBusiness} from '@/api/user/user.js'
	import { getBusinessById } from '@/api/merchantStores/merchantStores.js'
	export default {
		computed: {
			...mapState(['user']),
		},
		data() {
			return {
				businessData: {},
				// 当前商家该用户是否已收藏
				isCollect: false
			}
		},
		async onLoad(o) {
			const { data:res } = await getBusinessById(o.businessId)
			this.businessData = res.data
			uni.setNavigationBarTitle({
				title: this.businessData.shopName
			})
			const { data:checkRes } = await checkUserIsCollectBusiness(this.user.userInfo.userId,this.businessData.businessId)
			this.isCollect = checkRes.data
		},
		methods:{
			selectBusinessAllCommodity(){
				uni.navigateTo({
					url: 'subIndexPages/business-all-commodity/business-all-commodity?businessId=' + this.businessData.businessId
				})
			},
			async userCollect(){
				if (!this.user.isLogin) {
					uni.showModal({
						title: '警告',
						content: '您还未登录，请先登录',
						cancelText: "暂不登录",
						confirmText: "前往登录",
						confirmColor: '#ff0000',
						cancelColor: '#000000',
						success: function(res) {
							if (res.confirm) {
								uni.navigateTo({
									url: '/subPages/personal-pages/login/wx-login/wx-login?loginTo=' +
										'/pages/personal/personal'
								})
							} else {
								uni.navigateTo({
									url: url
								})
							}
						}
					})
				}else{
					//登录了
					const userCollect = {
						"userId": this.user.userInfo.userId,
						"commodityId": '',
						"businessId": this.businessData.businessId,
						"isType": 0
					}
					const {data: res} = await addUserCollect(userCollect)
					if(res.code == 0){
						this.isCollect = true
						uni.showToast({
							title: "收藏成功",
							icon: 'success'
						})
					}else{
						uni.showToast({
							title: "收藏失败",
							icon: 'error'
						})
					}
				}
			},
		}
	}
</script>

<style lang="scss">
page{
	background-color: #f6f6f6;
}

.business-box{
	width: 100%;
	display: flex;
	flex-direction: column;
	
	.business-top{
		width: 100%;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		
		.logo{
			margin-top: 60rpx;
			width: 100rpx;
			height: 100rpx;
		}
		
		.name{
			margin-top: 20rpx;
			font-size: 12px;
		}
		.rate{
			margin-top: 20rpx;
			display: flex;
			flex-direction: row;
		}
		.desc{
			margin-top: 30rpx;
			font-size: 12px;
		}
		.detail{
			width: 90%;
			background-color: #fff;
			margin-top: 20rpx;
			border-radius: 10rpx;
			padding: 20rpx;
			
			.detail-item{
				display: flex;
				flex-direction: row;
				font-size: 11px;
				margin-bottom: 15rpx;
			}
		}
		
		.all{
			margin-top: 20rpx;
			width: 95%;
			height: 90rpx;
			background-color: #fff;
			display: flex;
			justify-content: center;
			
			.all-commodity-text{
				font-size: 11px;
				line-height: 90rpx;
				color: #ff0000;
			}
		}
	}
}
</style>
