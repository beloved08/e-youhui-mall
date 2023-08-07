<template>
	<view class="container">
		<view class="top-image">
			<u-swiper :list="swiperList" indicator height="650" indicatorMode="line" circular></u-swiper>
		</view>
		<view class="commodity">
			<view class="price">
				<text style="font-size: 11px;">￥</text>
				<text
					v-if="commodityDetailInfo.commodity.salesModel == 0">{{commodityDetailInfo.commodity.retailPrice}}</text>
				<text v-else>{{commodityDetailInfo.commodity.wholesalePrice}}</text>
				<text style="font-size: 11px;margin-left: 5rpx;">元</text>
			</view>
			<view class="name">
				<text>{{commodityDetailInfo.commodity.commodityName}}</text>
			</view>
			<view class="desc">
				<text>{{commodityDetailInfo.commodity.commodityDescribe}}</text>
			</view>
		</view>
		<view class="commodity-detail">
			<view class="specifications">
				<text style="margin-right: 10rpx; margin-top: 5rpx;">配送</text>
				<view class="radio-item">
					<view class="item1">
						<radio style="transform:scale(0.4)" checked="true" />快递配送
					</view>
					<view class="item2">
						<radio style="transform:scale(0.4)" checked="true" />上门自提
					</view>
				</view>
			</view>
		</view>
		<view class="commodity-detail">
			<view class="specifications">
				<text style="margin-right: 10rpx;">规格</text>
				<text>{{commodityDetailInfo.commodity.commodityWeight}}kg/{{commodityDetailInfo.commodity.meterCompany}}</text>
			</view>
		</view>
		<view class="appraise">
			<view class="title">评价</view>
			<view class="appraise-item" v-if="commodityCommentList.length != 0" v-for="(item,index) in commodityCommentList" :key="index">
				<view class="avatar-name">
					<view class="avatar">
						<image style="width: 100%;height: 100%;border-radius: 50%;"
							:src="commodityDetailInfo.commodity.commodityImageUrl" mode="">
						</image>
					</view>
					<view class="name">匿名买家</view>
					<view class="rate">
						<!-- <u-rate :value="3.7" size="22" active-color="#FA3534" inactive-color="#b2b2b2"></u-rate> -->
					</view>
					<view style="font-size: 9px;margin-left: 15rpx;">
						{{item.commentTime}}
					</view>
				</view>
				<view class="appraise-desc">
					{{item.commentContent}}
				</view>
			</view>
			<view v-else  style="margin-bottom: 20rpx;">
				<u-empty
				mode="car"
				        text="暂无商品评论"
				        icon="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/empty/comment-empty.png"
				>
				</u-empty>
			</view>
		</view>
		<tui-divider>宝贝详情</tui-divider>
		<view class="detail">
			<view class="detail-item" v-for="(item,index) in 4" :key="index">
				<image style="width: 100%;height: 100%;" :src="commodityDetailInfo.commodity.commodityImageUrl"></image>
			</view>
		</view>
		<view class="business-view">
			<view class="business">
				<view class="business-item" style="margin-left: 50rpx;" @click="toBusinessCenter">
					<tui-icon size="22" color="#19be6b" name="home-fill"></tui-icon>
					<text class="tui-grid-label">店铺</text>
				</view>
				<view class="business-item" @click="userCollect">
					<tui-icon size="22" color="#19be6b" name="shop-fill"></tui-icon>
					<text class="tui-grid-label">收藏</text>
				</view>
				<view class="business-item" @click="toCart">
					<!-- <tui-badge absolute :scaleRatio="0.8" translateX="-1650%" top="-3rpx" type="danger">
						{{cart.cartCommodityInfo.length}}
					</tui-badge> -->
					<tui-icon size="22" color="#19be6b" name="cart-fill"></tui-icon>
					<text class="tui-grid-label">购物车</text>
				</view>
			</view>
			<view class="button-list">
				<tui-button @click="popupShow = true" type="warning" shape="circle" size="28" width="200rpx"
					height="75rpx">加入购物车</tui-button>
				<tui-button @click="popupShow = true" type="danger" shape="circle" size="28" style="margin-left: 20rpx;"
					width="180rpx" height="75rpx">立即购买</tui-button>
			</view>
		</view>
		<view class="">
			<u-popup :show="popupShow" mode="bottom" closeable :round="20" @close="popupShow = false">
				<view class="popup-bottom">
					<view class="popup-top">
						<view class="popup-image">
							<image style="width: 100%;height: 100%;"
								:src="commodityDetailInfo.commodity.commodityImageUrl" mode="">
							</image>
						</view>
						<view class="popup-info">
							<text v-if="commodityDetailInfo.commodity.salesModel == 0"
								style="color: red;font-size: 14px; font-weight: bold;">￥{{commodityDetailInfo.commodity.retailPrice}}</text>
							<text v-else
								style="color: red;font-size: 14px; font-weight: bold;">￥{{commodityDetailInfo.commodity.wholesalePrice}}</text>
							<text style="margin-top: 10rpx;">{{commodityDetailInfo.commodity.commodityName}}</text>
							<text style="margin-top: 10rpx;">已选 {{selectNumber}} 件</text>
							<text style="margin-top: 10rpx;">库存 {{ commodityDetailInfo.commodity.commodityStock }}
								件</text>
						</view>
					</view>
					<tui-divider height="10"></tui-divider>
					<view class="popup-number">
						<view class="popup-number-left">数量</view>
						<view class="popup-number-right">
							<u-number-box bgColor="#2979ff" color="#fff" iconStyle="color: #fff" integer
								button-size="50" :min="1" :max="100" v-model="selectNumber"></u-number-box>
						</view>
					</view>
					<view class="popup-button">
						<u-button type="warning" shape="circle" size="normal" @click="addCart(commodityDetailInfo)"
							text="加入购物车"></u-button>
						<u-button style="margin-left: 20rpx;" @click="buyNow(commodityDetailInfo)" type="error" shape="circle" size="normal" text="立即购买">
						</u-button>
					</view>
				</view>
			</u-popup>
		</view>
	</view>
</template>

<script>
	import {
		mapState,
		mapGetters,
		mapActions
	} from 'vuex'
	import {
		getCommodityDetail
	} from '@/api/commodity/commodity.js'
	import { getPromotionActivityCommodityById } from '@/api/merkete/merkete.js'
	import {addUserCollect} from '@/api/user/user.js'
	export default {
		computed: {
			...mapState(['cart']),
			...mapState(['user']),
		},
		data() {
			return {
				popupShow: false,
				commodityDetailInfo: {},
				swiperList: [],
				selectNumber: 1,
				currentBusinessId: '',
				commodityCommentList: []
			};
		},
		async onLoad(o) {
				getCommodityDetail(o.commodityId).then(res => {
					if(res.data.code == 0){
						this.currentBusinessId = res.data.data.business.businessId
						this.commodityDetailInfo = res.data.data
						this.commodityCommentList = res.data.data.commodityCommentList
						this.swiperList.push(
							this.commodityDetailInfo.commodity.commodityImageUrl
						)
						this.swiperList.push(
							this.commodityDetailInfo.commodity.commodityImageUrl
						)
						this.swiperList.push(
							this.commodityDetailInfo.commodity.commodityImageUrl
						)
					}
				})
			
		},
		methods: {
			...mapActions(['saveCartCommodityInfo','saveCommodityList']),
			// 前往商家店铺中心
			toBusinessCenter(){
				uni.navigateTo({
					url:'subIndexPages/business-center/business-center?businessId=' + this.currentBusinessId
				})
			},
			toCart() {
				uni.switchTab({
					url: 'pages/cart/cart'
				})
			},
			async buyNow(){
				let commodityId = this.commodityDetailInfo.commodity.commodityId
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
								// uni.navigateTo({
								// 	url: '/subPages/personal-pages/login/wx-login/wx-login?loginTo=' +
								// 		'subIndexPages/commodityDetail/commodityDetail',
								// 		options: {
								// 		    commodityId: commodityId
								// 	}
								// })
							} else {
								uni.navigateTo({
									url: url
								})
							}
						}
					})
				}else{
					let buyCommodity = {
						"business": this.commodityDetailInfo.business,
						"commodity": this.commodityDetailInfo.commodity,
						"price": 0,
						"quantity": this.selectNumber,
						"selected": true,
						"totalFinalPrice": 0
					}
					let c = []
					c.push(buyCommodity)

					this.saveCommodityList(c)
					uni.navigateTo({
						url: 'subCartPages/settle-account/settle-account'
					})
				}
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
						"commodityId": this.commodityDetailInfo.commodity.commodityId,
						"businessId": '',
						"isType": 1
					}
					const {data: res} = await addUserCollect(userCollect)
					if(res.code == 0){
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
			addCart(e) {
				console.log(e)
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
				} else {
					if (e.commodity.commodityStock == 0) {
						//库存为0
						uni.showToast({
							title: "暂时无货",
							icon: 'error'
						})
					} else if (e.commodity.commodityStock < this.selectNumber) {
						uni.showToast({
							title: "库存不足",
							icon: 'error'
						})
					} else {
						const cartData = {
							"commodity": e.commodity,
							"business": e.business,
							"quantity": this.selectNumber,
							"selected": true
						}
						this.saveCartCommodityInfo(cartData)
						this.popupShow = false

						uni.showToast({
							title: "已添加至购物车",
							icon: 'success'
						})
					}
				}
			}
		}

	}
</script>

<style lang="scss" scoped>
	.container {
		width: 100%;
		display: flex;
		flex-direction: column;
		background-color: #f6f6f6;

		.top-image {
			width: 100%;
			// height: 320px;
		}

		.commodity {
			width: 95%;
			margin: 0 auto;
			// height: 100px;
			margin-top: 20rpx;

			.price {
				color: #ff0000;
				font-size: 18px;
				font-weight: bold;
				margin-top: 10rpx;
				margin-left: 10rpx;
			}

			.name {
				margin-top: 20rpx;
				margin-left: 10rpx;
				margin-bottom: 20rpx;
				font-size: 13px;
				font-weight: bold;
				word-break: break-all;
			}

			.desc {
				margin-left: 10rpx;
				font-size: 12px;
				margin-bottom: 20rpx;
				word-break: break-all;
			}
		}

		.commodity-detail {
			width: 100%;
			margin: 0 auto;
			// height: 100px;
			border-radius: 1rpx;
			background-color: #fff;
			margin-top: 20rpx;

			.specifications {
				font-size: 12px;
				margin-top: 20rpx;
				margin-left: 10rpx;
				margin-bottom: 20rpx;
				display: flex;
				flex-direction: row;

				.radio-item {
					display: flex;
					flex-direction: row;
					margin-top: -5rpx;

					.item1 {
						margin-right: 10rpx;
						margin-left: 10rpx;
					}

					.item2 {
						margin-left: 10rpx;
					}
				}

			}

		}

		.appraise {
			margin-top: 20rpx;
			margin-bottom: 10rpx;
			width: 100%;
			background-color: #fff;
			display: flex;
			flex-direction: column;

			.title {
				margin-top: 20rpx;
				margin-left: 20rpx;
				font-size: 12px;
			}

			.appraise-item {
				width: 90%;
				margin: 0 auto;
				height: 150rpx;
				margin-bottom: 20rpx;
				display: flex;
				flex-direction: column;

				.avatar-name {
					display: flex;
					flex-direction: row;
					margin-top: 20rpx;

					.avatar {
						width: 20px;
						height: 20px;
					}

					.name {
						font-size: 12px;
						margin-left: 10rpx;
					}

					.rate {
						margin-top: 5rpx;
						margin-left: 10rpx;
					}
				}

				.appraise-desc {
					margin-top: 20rpx;
					margin-left: 10rpx;
					font-size: 11px;
				}
			}
		}

		.detail {
			width: 100%;
			margin-top: 0rpx;

			.detail-item {
				width: 100%;
				height: 400px;
				margin-bottom: 10rpx;
			}
		}

		.business-view {
			position: fixed;
			bottom: 0;
			width: 100%;
			line-height: var(--footer-height);
			color: #fff;
			// height: 50px;
			border-top: 2rpx solid #eee;
			background-color: #f6f6f6;
			display: flex;
			flex-direction: row;

			.business {
				display: flex;
				flex-direction: row;
				margin-top: 10rpx;

				.business-item {
					margin: 10rpx;
					margin-left: 10rpx;
					margin-right: 20rpx;
					display: flex;
					flex-direction: column;

					.tui-grid-label {
						color: #000;
						font-size: 12px;
					}
				}
			}

			.button-list {
				display: flex;
				flex-direction: row;
				justify-content: end;
				margin-top: 15rpx;
				margin-left: 20rpx;
			}
		}

		.popup-bottom {
			width: 100%;
			height: 280px;
			padding: 20rpx;
			display: flex;
			flex-direction: column;

			.popup-top {
				display: flex;
				flex-direction: row;
				margin-top: 20rpx;
				margin-bottom: 20rpx;

				.popup-image {
					width: 80px;
					height: 80px;
				}

				.popup-info {
					display: flex;
					flex-direction: column;
					margin-left: 30rpx;
					margin-top: 0rpx;
					font-size: 12px;
				}
			}

			.popup-number {
				margin-top: 20rpx;
				display: flex;
				flex-direction: row;
				justify-content: space-between;

				.popup-number-left {
					font-size: 14px;
				}

				.popup-number-right {
					margin-right: 40px;
				}
			}

			.popup-button {
				width: 90%;
				position: fixed;
				bottom: 10px;
				display: flex;
				flex-direction: row;
				justify-content: space-between;
			}
		}
	}
</style>
