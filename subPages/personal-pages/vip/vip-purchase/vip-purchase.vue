<template>
	<view class="con-box">
		<view class="top">
			<!-- <image style="width: 100%;height: 100%;" src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/svg-red.png" mode=""></image> -->
		</view>
		<view class="info">
				<view class="user-image">
					<image :src="user.userInfo.avatar" style="width: 100%;height: 100%;border-radius: 50%;"></image>
				</view>
				<view class="name">
					<text style="font-size: 14px;margin-bottom: 10rpx;">{{user.userInfo.realName}}</text>
					<text v-if="user.userInfo.type == 0">您还不是付费会员</text>
					<text v-else>恭喜你，您已是付费会员</text>
				</view>
		</view>
		<view class="vip">
			<view class="card">
				<view class="top-right" v-if="user.userInfo.type == 0">未购买</view>
				<view class="top-right" v-else>已购买</view>
				<view class="vip-name">终身卡</view>
				<view class="vip-price">
					<text style="font-size: 10px;">￥</text>
					<text style="font-size: 24px;font-weight: bold;">399</text>
				</view>
			</view>
		</view>
		<view class="vip-desc">
			<view class="vip-name-1">VIP权限</view>
			<view class="vip-list">
				<view class="list">
					<view class="vip-list-image">
						<image style="width: 100%;height: 100%;"  src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/integral.png" mode=""></image>
					</view>
					<view class="vip-item-name">送积分3000</view>
				</view>
				<view class="list">
					<view class="vip-list-image">
						<image style="width: 100%;height: 100%;"  src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/discount.png" mode=""></image>
					</view>
					<view class="vip-item-name">商品9.5折</view>
				</view>
			</view>
		</view>
		<view class="purchase">
			<view class="purchase-price">
				<view style="font-size: 10px;">总价：</view>
				<view class="purchase-price-desc">
					<text style="font-size: 10px;">￥</text>
					<text style="font-size: 20px;font-weight: bold;">399</text>
				</view>
			</view>
			<view :class="user.userInfo.type == 0 ? 'purchase-button' : 'purchase-button2'" @click="purchaseNow">立即购买</view>
		</view>
	</view>
</template>

<script>
	import { userPurchaseVip } from '@/api/user/user.js'
	import {
		mapState,
		mapActions
	} from 'vuex'
	export default {
		computed: {
			...mapState(['user'])
		},
		data() {
			return {
				
			}
		},
		methods: {
			...mapActions(['saveUserDetail','removeUserDetail']),
			// 立即购买
			async purchaseNow(){
				let _this = this
				if(_this.user.userInfo.type == 0){
					const { data:res } = await userPurchaseVip(_this.user.userInfo.userId)
					if(res.code == 0){
						uni.showToast({
							title: res.msg,
							icon: 'none',
							duration: 2000
						})
						//  更新用户信息
						let token = uni.getStorageSync('LOGIN_USER_DETAIL').token
						//移除原先的缓存数据
						_this.removeUserDetail(userDetail)
						// 获取用户信息
						const userDetail = {
							"isLogin": true,
							"userInfo": res.data,
							"token": token
						}
						_this.saveUserDetail(userDetail)
						setTimeout(()=>{
							uni.navigateBack()
						},2000)
					}else{
						uni.showToast({
							title: res.msg,
							icon: 'none',
							duration: 2000
						})
					}
				}else{
					uni.showToast({
						title: '您已购买会员，不能重复购买',
						icon: 'none',
						duration: 2000
					})
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
.con-box{
	width: 100%;
	height: 100vh;
	background-color: #fff;
	display: flex;
	flex-direction: column;
	
	.top{
		width: 100%;
		height: 15%;
		background-color: #ff0000;
	}
	
	.info{
		width: 100%;
		height: 50px;
		position: absolute;
		top: 30rpx;
		display: flex;
		flex-direction: row;
		
		.user-image{
			margin-left: 40rpx;
			margin-top: 10rpx;
			width: 60px;
			height: 60px;
			border-radius: 50%;
		}
		
		.name{
			display: flex;
			flex-direction: column;
			color: #fff;
			font-size: 12px;
			margin-left: 20rpx;
			margin-top: 20rpx;
		}
	}
	.vip{
		background-color: #ff0000;
		width: 100%;
		height: 250rpx;
		display: flex;
		justify-content: center;
		
		.card{
			width: 80%;
			height: 92%;
			padding: 10rpx;
			border-radius: 10rpx 10rpx 0 0;
			background: #f0ce96;
			background: linear-gradient(to right, rgb(240, 206, 150), rgb(240, 206, 150));
			display: flex;
			flex-direction: column;
			
			.top-right{
				background-color: #fff;
				color: #5e503a;
				width: 80rpx;
				text-align: center;
				font-size: 10px;
				// padding: 5rpx;
				position: relative;
				left: 85%;
			}
			
			.vip-name{
				font-size: 20px;
				margin-top: 10rpx;
				margin-left: 30rpx;
				color: #fff;
				font-weight: bold;
			}
			.vip-price{
				margin-top: 10rpx;
				margin-left: 30rpx;
				color: #fff;
			}
		}
	}
	.vip-desc{
		width: 100%;
		margin-top: -20rpx;
		height: 100px;
		border-radius: 10rpx 10rpx 0 0;
		background-color: #fff;
		display: flex;
		flex-direction: column;
		
		.vip-name-1{
			margin: 20rpx;
			font-size: 14px;
			font-weight: bold;
		}
		.vip-list{
			width: 100%;
			height: 100px;
			margin-top: 20rpx;
			display: flex;
			flex-direction: row;
			
			.list{
				display: flex;
				flex-direction: column;
				
				.vip-list-image{
					width: 55px;
					height: 55px;
					margin-top: 10rpx;
					margin-left: 50rpx;
				}
				
				.vip-item-name{
					font-size: 13px;
					margin-top: 15rpx;
					margin-left: 50rpx;
					font-weight: bold;
				}
			}
		}
	}
	.purchase{
		position: fixed;
		bottom: 0;
		width: 100%;
		height: 50px;
		background-color: #fdfdfd;
		border-top: 2px solid #eee;
		line-height: var(--footer-height);
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		
		.purchase-price{
			display: flex;
			flex-direction: row;
			font-size: 14px;
			margin-left: 50rpx;
			line-height: 50px;
			
			.purchase-price-desc{
				color: #ff0000;
			}
		}
		
		.purchase-button{
			width: 200rpx;
			height: 100%;
			background-color: #ff0000;
			color: #fff;
			text-align: center;
			align-items: center;
			font-size: 16px;
			line-height: 50px;
			font-weight: bold;
		}
		
		.purchase-button2{
			width: 200rpx;
			height: 100%;
			background-color: #8b8b8b;
			color: #fff;
			text-align: center;
			align-items: center;
			font-size: 16px;
			line-height: 50px;
			font-weight: bold;
		}
	}
}
</style>
