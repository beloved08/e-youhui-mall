<template>
	<view class="merchant-stores-box">
		<view v-for="(item,index) in merchantStoresDataList" :key="index">
			<view class="ms-box">
				<view class="logo-wrap">
					<view class="logo">
						<image :src="item.businessLogo" style="width: 100%;height: 100%;border-radius: 50%;"></image>
					</view>
				</view>
				<view class="ms-title">
					<text>{{item.businessName}} - {{item.shopName}}</text>
				</view>
				<view class="status">
					<view class="shop-status">
						<view class="shop-status-title">
							<text>商家店铺状态</text>
						</view>
						<view>
							<u-tag text="正常" v-if="item.businessStatus == 0"></u-tag>
							<u-tag text="已注销" type="error" v-if="item.businessStatus == 1"></u-tag>
							<u-tag text="待审核" type="warning" size="mini" v-if="item.businessStatus == 2"></u-tag>
							<u-tag text="已审核" type="success" v-if="item.businessStatus == 3"></u-tag>
							<u-tag text="信息有误" type="error" v-if="item.businessStatus == 4"></u-tag>
						</view>
					</view>
					<view class="root-status">
						<view class="root-status-title">
							<text>管理员审核状态</text>
						</view>
						<view>
							<u-tag text="审核通过" size="mini"  v-if="item.verifyStatus == 0"></u-tag>
							<u-tag text="审核不通过" type="error" size="mini" v-if="item.verifyStatus == 1"></u-tag>
							<u-tag text="审核信息有误" type="warning" size="mini" v-if="item.verifyStatus == 2"></u-tag>
							<u-tag text="待审核" type="success" size="mini" v-if="item.verifyStatus == 3"></u-tag>
						</view>
					</view>
				</view>
				<view class="shop-desc">
					<view class="title">商家店铺信息</view>
					<view class="shop-item">
						<view class="left">
							<text class="left-name">店铺电话</text>
							<text class="left-content">{{item.businessPhone}}</text>
						</view>
						<view class="right">
							<text class="right-name">所在城市</text>
							<text class="right-content">{{item.businessCity}}</text>
						</view>
					</view>
					<view class="shop-item">
						<view class="left-desc">
							<text class="left-name">详细地址</text>
							<text class="left-content">{{item.businessDetailAddress}}</text>
						</view>
					</view>
					<view class="title">管理员信息</view>
					<view class="shop-item">
						<view class="left">
							<text class="left-name">姓名</text>
							<text class="left-content">{{item.businessUserName}}</text>
						</view>
						<view class="right">
							<text class="right-name">年龄</text>
							<text class="right-content">{{item.businessUserAge}}</text>
						</view>
					</view>
					<view class="shop-item">
						<view class="left-sex">
							<text class="left-name">性别</text>
							<text class="left-content">{{item.businessUserSex}}</text>
						</view>
						<view class="right-idcard">
							<text class="right-name">身份证号码</text>
							<text class="right-content">{{item.businessUserIdCard}}</text>
						</view>
					</view>
					<view class="shop-item">
						<view class="left-lxfs">
							<text class="left-name">联系方式</text>
							<text class="left-content">{{item.businessUserPhone}}</text>
						</view>
						<view class="right-lxfs">
							<text class="right-name">联系邮箱</text>
							<text class="right-content">{{item.businessUserEmail}}</text>
						</view>
					</view>
				</view>
				<view class="map">
					<Map style="width: 100%;height: 100%;" :MapData="merchantStoresDataList" />
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import Map from '@/subPages/personal-pages/map/map.vue'
	import { getMerchantStoresList } from '@/api/merchantStores/merchantStores.js'
	export default {
		components: {
			Map
		},
		created() {
			this.merchantStoresList()
		},
		data() {
			return {
				merchantStoresDataList: []
			}
		},
		methods:{
			// 获取当前登录用户的商家列表
			async merchantStoresList(){
				const userId = uni.getStorageSync('LOGIN_USER_DETAIL') != null ? uni.getStorageSync('LOGIN_USER_DETAIL').userInfo.userId : ''
				const { data:res } = await getMerchantStoresList(userId)
				if(res.code == 0){
					this.merchantStoresDataList = res.data
					
					// console.log(this.merchantStoresDataList)
				}
				if(res.data == null){
					uni.redirectTo({
						url:"/subPages/personal-pages/merchant-settlement/apply-form/apply-form"
					})
				}
			},
		}
	}
</script>

<style lang="scss" scoped>
	.merchant-stores-box {
		width: 100%;
		// height: 100%;
		display: flex;
		flex-direction: column;
		background-color: #f6f6f6;

		.ms-box {
			width: 90%;
			// height: 100%;
			margin: 0 auto;
			padding: 20rpx;
			margin: 10rpx;
			margin-top: 20rpx;
			margin-bottom: 20rpx;
			border-radius: 10rpx;
			display: flex;
			flex-direction: column;
			background-color: #fff;

			.logo-wrap {
				width: 100%;
				display: flex;
				flex-direction: row;

				.logo {
					margin: 0 auto;
					width: 180rpx;
					height: 160rpx;
				}
			}

			.ms-title {
				width: 100%;
				margin-top: 40rpx;
				margin-bottom: 20rpx;
				font-size: 16px;
				font-weight: bold;
				text-align: center;
			}
			
			.status{
				width: 100%;
				margin: 0 auto;
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				
				.shop-status{
					display: flex;
					flex-direction: row;
					
					.shop-status-title{
						font-size: 13px;
						color: #577ee3;
						font-weight: 600;
						margin-right: 20rpx;
					}
				}
				
				.root-status{
					display: flex;
					flex-direction: row;
					
					.root-status-title{
						font-size: 13px;
						color: #577ee3;
						font-weight: 600;
						margin-right: 20rpx;
					}
				}
			}

			.shop-desc {
				width: 100%;
				margin-top: 20rpx;

				.title {
					margin-top: 10rpx;
					margin-bottom: 10rpx;
					font-size: 14px;
					font-weight: bold;
					color: #577ee3;
				}

				.shop-item {
					width: 100%;
					margin: 0 auto;
					padding: 10rpx;
					margin-bottom: 20rpx;
					display: flex;
					flex-direction: row;
					justify-content: space-between;

					.left-desc {
						width: 100%;
						display: flex;
						flex-direction: row;

						.left-name {
							width: 18%;
							font-size: 13px;
							font-weight: 600;
							margin-right: 10rpx;
						}

						.left-content {
							width: 80%;
							font-size: 12px;
						}
					}

					.left-sex {
						width: 20%;
						display: flex;
						flex-direction: row;

						.left-name {
							font-size: 13px;
							font-weight: 600;
							margin-right: 10rpx;
						}

						.left-content {
							font-size: 12px;
						}
					}

					.right-idcard {
						width: 60%;
						display: flex;
						flex-direction: row;

						.right-name {
							font-size: 13px;
							margin-right: 10rpx;
						}

						.right-content {
							font-size: 12px;
						}
					}
					
					.left-lxfs{
						width: 43%;
						display: flex;
						flex-direction: row;
						
						.left-name {
							font-size: 13px;
							font-weight: 600;
							margin-right: 10rpx;
						}
						
						.left-content {
							font-size: 12px;
						}
					}
					
					.right-lxfs{
						width: 55%;
						
						.right-name {
							font-size: 13px;
							margin-right: 10rpx;
						}
						
						.right-content {
							font-size: 12px;
						}
					}

					.left {
						width: 45%;
						display: flex;
						flex-direction: row;

						.left-name {
							font-size: 13px;
							font-weight: 600;
							margin-right: 10rpx;
						}

						.left-content {
							font-size: 12px;
						}
					}

					.right {
						width: 45%;

						.right-name {
							font-size: 13px;
							margin-right: 10rpx;
						}

						.right-content {
							font-size: 12px;
						}
					}
				}
			}
			
			.map{
				width: 90%;
				margin: 0 auto;
				display: flex;
				align-items: center;
			}
		}
	}
</style>
