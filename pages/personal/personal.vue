<template>
	<view class="content">
		<view class="top">
			<view class="top-left" v-if="!user.isLogin">
				<view class="avatar" @click="toUserinfo()">
					<u-avatar src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/avatar/avatar.png" class="image" size="90"></u-avatar>
				</view>
				<view class="user">
					<view class="name-login" @click="toUserLogin()">立即登录</view>
				</view>
			</view>
			<view class="top-left" v-else>
				<view class="avatar" @click="toUserinfo()">
					<u-avatar :src="user.userInfo.avatar" class="image" size="90"></u-avatar>
				</view>
				<view class="user">
					<view class="name" @click="toUserinfo()">{{user.userInfo.realName ? user.userInfo.realName : '.u-微信用户'}}</view>
					<view class="member" @click="toVip()">
						<tui-tag v-if="user.userInfo.type == 0" type="light-blue" hover="true" shape="circle" padding="12rpx" size="18rpx">暂未开通
						</tui-tag>
						<tui-tag v-else type="light-brownish" hover="true" shape="circle" padding="12rpx" size="18rpx">终身会员
						</tui-tag>
					</view>
				</view>
			</view>
			<view class="top-right">
				<!-- <tui-icon name="explore-fill" :color="icon.iconColor" :size="24" style="margin-right: 10rpx;" @click="toMap()"></tui-icon> -->
				<tui-icon name="sweep" @click="saoyisao" :color="icon.iconColor" :size="24" style="margin-right: 10rpx;"></tui-icon>
				<!-- <tui-icon name="feedback"  :color="icon.iconColor" :size="24" style="margin-right: 10rpx;"></tui-icon> -->
				<tui-icon name="signin" @click="selectcCalendar" :color="icon.iconColor" :size="24"></tui-icon>
			</view>
		</view>
		<view class="container">
			<view class="container-item" @click="toBalance()">
				<view class="item-box">
					<view class="num" v-if="!user.isLogin">-</view>
					<view class="num" v-else>{{availableBalance}}</view>
					<view class="tag">余额</view>
				</view>
			</view>
			<view class="container-item" @click="toIntegral()">
				<view class="item-box">
					<view class="num" v-if="!user.isLogin">-</view>
					<view class="num" v-else>{{availableIntegral}}</view>
					<view class="tag">积分</view>
				</view>
			</view>
			<view class="container-item" @click="toCoupon()">
				<view class="item-box">
					<view class="num" v-if="!user.isLogin">-</view>
					<view class="num" v-else>{{userCouponCount}}</view>
					<view class="tag">优惠券</view>
				</view>
			</view>
			<view class="container-item" @click="toCollection()">
				<view class="item-box">
					<view class="num" v-if="!user.isLogin">-</view>
					<view class="num" v-else>{{userCollectCount}}</view>
					<view class="tag">收藏</view>
				</view>
			</view>
		</view>
		<view class="vip" @click="toVip()">
			<view class="vip-box">
				<view class="vip-item-left">
					<view class="vip-item-left-image">
						<view class="vip-image">
							<image :src="vipSrc" style="width: 100%; height: 100%;"></image>
						</view>
						<view class="vip-title"><text>专享</text></view>
					</view>
					<view class="vip-desc"><text>立享5种专属特权，最低1元/周</text></view>
				</view>
				<view class="vip-item-right">
					<view class="activate-now">立即开通</view>
				</view>
			</view>
		</view>
		<view class="order">
			<view class="order-top">
				<view class="my-order">
					<text>我的订单</text>
				</view>
				<view class="whole-order" @click="toOrder(0)">
					<text>全部订单</text>
					<tui-icon name="arrowright" :size="16"></tui-icon>
				</view>
			</view>
			<view class="order-grid">
				<tui-grid unlined>
					<block v-for="(item,index) in icon.personalIconList.orderIconList" :key="index">
						<tui-grid-item :cell="5" @click="toOrder(index + 1)" :border="false" :bottom-line="false">
							<view class="grid-item-icon">
								<tui-icon :name="item.name" :size="icon.personalIconSize" :color="icon.iconColor">
								</tui-icon>
							</view>
							<text class="grid-title">{{item.title}}</text>
						</tui-grid-item>
					</block>
				</tui-grid>
			</view>
		</view>
		<view class="market">
			<view class="market-item" @click="toNationalPromotion()">
				<view class="market-item-left">
					<view class="left-item">
						<text>全民促销</text>
					</view>
					<view class="left-item-sub">
						<text>申请全民促销活动</text>
					</view>
				</view>
				<view class="market-item-left">
					<tui-icon name="moments" :color="icon.iconColor"></tui-icon>
				</view>
			</view>
			<u-line direction="col" length="50%" margin="35rpx" :color="icon.iconColor"></u-line>
			<view class="market-item" @click="toMerchantSettlement()">
				<view class="market-item-left">
					<view class="left-item">
						<text>商家入驻</text>
					</view>
					<view class="left-item-sub">
						<text>入驻商城申请</text>
					</view>
				</view>
				<view class="market-item-left">
					<tui-icon name="add-fill" :color="icon.iconColor"></tui-icon>
				</view>
			</view>
		</view>
		<view class="utils">
			<view class="utils-title">
				<text>服务与工具</text>
			</view>
			<view class="utils-grid">
				<tui-grid unlined>
					<block v-for="(item,index) in icon.personalIconList.serviceUtilIconList" :key="index">
						<tui-grid-item :cell="4" :border="false" :bottom-line="false" @click="toServiceUtil(item)">
							<view class="utils-grid-icon">
								<tui-icon :name="item.name" :size="icon.personalIconSize" :color="icon.iconColor">
								</tui-icon>
							</view>
							<text class="utils-grid-title">{{item.title}}</text>
						</tui-grid-item>
					</block>
				</tui-grid>
			</view>
		</view>
		<view class="recommend">
			<view class="recommend-divider">
				<tui-icon name="like-fill" :size="10" color="#ff0000"></tui-icon>
				<text style="margin-left: 10rpx;margin-right: 10rpx;">为您推荐</text>
				<tui-icon name="like-fill" :size="10" color="#ff0000"></tui-icon>
			</view>
			<view class="recommend-list">
				<view>
					<WaterfallFlow :commodityList="indexShowCommodityData" />
				</view>
			</view>
		</view>
		<view class="">
			<u-modal :show="saoyisaoModel" @confirm="saoyisaoModel = false" >
				<view style="display: flex;flex-direction: column;word-break: break-all;">
					<view style="font-size: 11px;">条码类型：{{saoyisaoModelDataRawData}}</view>
					<view  style="font-size: 11px;">条码内容：{{saoyisaoModelDataResult}}</view>
				</view>
			</u-modal>
		</view>
		<view class="">
				<tui-calendar ref="calendar" lunar isFixed></tui-calendar>
		</view>
		<view>
			<tui-scroll-top :scrollTop="scrollTop" :topIcon="topIcon" :bottom="bottom"></tui-scroll-top>
		</view>
		<view class="">
			<tui-fab :right="20" @click="tuiFabClick" :bgColor="icon.iconColor" :bottom="30" :btnList="btnList"></tui-fab>
		</view>
	</view>
</template>

<script>
	import WaterfallFlow from '@/components/waterfallFlow/waterfallFlow.vue'
	import {getUserCollectCount,getUserCouponCount} from '@/api/user/user.js'
	import { getUserBalanceNumber,getUserIntegralNumber } from '@/api/pay/pay.js'
	import { getIndexShowCommodity } from '@/api/commodity/commodity.js'
	import {getPromotionByUserId} from '@/api/merkete/merkete.js'
	import {
		mapState,
		mapGetters
	} from 'vuex'

	export default {
		components: {
			WaterfallFlow
		},
		computed: {
			...mapState(['user', 'icon']),
		},
		data() {
			return {
				indexShowCommodityData: [],
				currentPage: 2,
				pageSize: 10,
				totalPage: 1,
				 btnList:[{
					bgColor: "#16C2C2",
					//名称
					text: "购物车",
					imgUrl: '/static/icon/cart.png',
					imgHeight: 50,
					imgWidth : 50,
					//字体大小
					fontSize: 28,
					//字体颜色
					color: "#fff"
				}, {
					bgColor: "#64B532",
					//名称
					text: "首页",
					imgUrl: '/static/icon/index.png',
					imgHeight: 50,
					imgWidth : 50,
					//字体大小
					fontSize: 28,
					//字体颜色
					color: "#fff"
				}],
				saoyisaoModel:false,
				saoyisaoModelDataRawData: '',
				saoyisaoModelDataResult: '',
				vipSrc: "/static/icon/vip.png",
				bottom: 100,
				scrollTop: 0,
				topIcon: "/static/icon/topIcon.png",
				userCollectCount: 0,
				userCouponCount: 0,
				availableIntegral: 0,
				availableBalance: 0,
				userId: '',
			};
		},
		onLoad() {
			this.userId = this.getUserInfo().userId
			this.initUserPersonal()
		},
		created() {
			this.getCommodityIndexShow()
		},
		onPullDownRefresh() {
			let _this = this
			_this.initUserPersonal()
			_this.currentPage = Math.floor(Math.random() * (_this.totalPage -1)) + 1
			_this.indexShowCommodityData = []
			_this.getCommodityIndexShow()
			uni.stopPullDownRefresh()
		},
		onPageScroll(e) {
			this.scrollTop = e.scrollTop
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
			...mapGetters(['getIsLogin','getUserInfo']),
			// 初始化个人中心页面
			async initUserPersonal(){
					if(this.getIsLogin()){
						// 已登录
						let userId = this.getUserInfo().userId
						// 获取用户收藏
						const {data: res1 } = await getUserCollectCount(userId)
						this.userCollectCount = res1.data
						// 获取用户领取的优惠券
						const {data: res2 } = await getUserCouponCount(userId)
						this.userCouponCount = res2.data
						// 获取用户可用积分
						const { data:res3 } = await getUserIntegralNumber(userId)
						this.availableIntegral = res3.data
						// 获取用户可用余额
						const { data:res4 } = await getUserBalanceNumber(userId)
						this.availableBalance = res4.data
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
					this.totalPage = res.data.pages
				}
			},
			tuiFabClick(e){
				if(e.index == 0){
					//购物车
					uni.switchTab({
						url:'/pages/cart/cart'
					})
				}else if(e.index == 1){
					//首页
					uni.switchTab({
						url:'/pages/index/index'
					})
				}
			},
			saoyisao(){
				let _this = this
				// 允许从相机和相册扫码
				uni.scanCode({
					success: function (res) {
						_this.saoyisaoModelDataRawData = res.scanType
						_this.saoyisaoModelDataResult = res.result
						_this.saoyisaoModel = true
					}
				})
			},
			selectcCalendar(){
				this.$refs.calendar.show();
			},
			toMap(){
				uni.navigateTo({
					url:'/subPages/personal-pages/map/map'
				})
			},
			toUserLogin() {
				uni.redirectTo({
					url:'/subPages/personal-pages/login/wx-login/wx-login?loginTo=' + '/pages/personal/personal'
				})
			},
			// 跳转到个人信息页面
			toUserinfo() {
				uni.navigateTo({
					url: 'subPages/personal-pages/userinfo/userinfo'
				})
			},
			// 跳转到会员中心页面
			toVip() {
				uni.navigateTo({
					url: 'subPages/personal-pages/vip/vip'
				})

			},
			// 跳转到余额页面
			toBalance() {
				uni.navigateTo({
					url: 'subPages/personal-pages/balance/balance'
				})
			},
			// 跳转到积分页面
			toIntegral() {
				uni.navigateTo({
					url: 'subPages/personal-pages/integral/integral'
				})
			},
			// 跳转到优惠券页面
			toCoupon() {
				uni.navigateTo({
					url: 'subPages/personal-pages/coupon/coupon?id=1'
				})
			},
			// 跳转到收藏中心页面
			toCollection() {
				uni.navigateTo({
					url: 'subPages/personal-pages/collection/collection'
				})
			},
			// 跳转到订单页面
			toOrder(e) {
				uni.navigateTo({
					url: 'subPages/personal-pages/order/order?currentTabIndex=' + e
				})
			},
			// 跳转到全民促销页面
			async toNationalPromotion() {
				const {data:res} = await getPromotionByUserId(this.userId)
				if(res.data != null){
					if(res.data.verify == 0){
						uni.showToast({
							title: '您提交的申请正在审核中，请耐心等待',
							duration: 2000,
							icon: 'none'
						})
					}else if(res.data.verify == 2){
						uni.showToast({
							title: '您提交的申请审核不通过，请重新提交',
							duration: 2000,
							icon: 'none'
						})
						setTimeout(() => {
							uni.navigateTo({
								url: 'subPages/personal-pages/national-promotion/national-promotion'
							})
						},2000)
					}else{
						//审核通过
						uni.navigateTo({
							url: 'subPages/personal-pages/user-promotion/user-promotion'
						})
					}
				}else{
					uni.navigateTo({
						url: 'subPages/personal-pages/national-promotion/national-promotion'
					})
				}
			},
			// 跳转到商家入驻页面
			toMerchantSettlement() {
				uni.navigateTo({
					url: 'subPages/personal-pages/merchant-settlement/merchant-settlement'
				})
			},
			// 跳转到商家入驻页面
			toServiceUtil(item) {
				if (item.index === 0) {
					// 会员中心
					uni.navigateTo({
						url: 'subPages/personal-pages/vip/vip'
					})
				} else if (item.index === 1) {
					// 领券中心
					uni.navigateTo({
						url: 'subPages/personal-pages/getCoupon/getCoupon'
					})
				} else if (item.index === 2) {
					// 积分中心
					uni.navigateTo({
						url: 'subPages/personal-pages/integral/integral'
					})
				} else if (item.index === 3) {
					// 地址管理
					uni.navigateTo({
						url: 'subPages/personal-pages/address/address?id=1'
					})
				} else if (item.index === 4) {
					// 收藏中心
					uni.navigateTo({
						url: 'subPages/personal-pages/collection/collection'
					})
				} else if (item.index === 5) {
					// 官方客服
					// uni.navigateTo({
					// 	url: 'subPages/personal-pages/customer-service/customer-service'
					// })
					uni.makePhoneCall({
						phoneNumber: '15136754892' //仅为示例
					})
				} else if (item.index === 6) {
					// 图标设置
					uni.navigateTo({
						url: 'subPages/personal-pages/icon/icon'
					})
				} else if (item.index === 7) {
					// 系统设置
					uni.navigateTo({
						url: 'subPages/personal-pages/system-settings/system-settings'
					})
				}
			},
		}
	}
</script>

<style lang="scss" >
	page{
		background-color: #f6f6f6;
	}
	.content {
		display: flex;
		flex-direction: column;
		width: 100%;
		// height: 100vh;
		background-color: #f6f6f6;

		.top {
			display: flex;
			flex-direction: row;
			margin-top: 30rpx;
			margin-left: 30rpx;

			.top-left {
				width: 68%;
				display: flex;
				flex-direction: row;

				.avatar {
					width: 95rpx;
					height: 95rpx;
				}

				.user {
					margin-left: 20rpx;
					margin-top: 6rpx;

					.name {
						font-size: 15px;
					}

					.name-login {
						margin-top: 20rpx;
					}

					.member {
						font-size: 12px;
						margin-top: 6rpx;
					}
				}
			}

			.top-right {
				margin-top: 10rpx;
				width: 25%;
				display: flex;
				flex-direction: row;
				margin-left: 40rpx;
			}
		}

		.container {
			display: flex;
			flex-direction: row;
			margin: 30rpx auto;

			.container-item {
				display: flex;
				flex-direction: column;
				text-align: center;
				width: 120rpx;
				height: 120rpx;
				margin: 20rpx;

				.item-box {
					margin-top: 10%;

					.num {
						font-size: 12px;
					}

					.tag {
						font-size: 11px;
					}
				}
			}
		}

		.vip {
			display: flex;
			margin-top: -60rpx;

			.vip-box {
				display: flex;
				flex-direction: row;
				width: 90%;
				height: 130rpx;
				background-color: #535353;
				margin: 0 auto;
				border-radius: 10rpx 10rpx;

				.vip-item-left {
					display: flex;
					flex-direction: column;
					width: 70%;
					margin-top: 20rpx;
					margin-left: 10rpx;

					.vip-item-left-image {
						display: flex;
						flex-direction: row;
						width: 150rpx;

						.vip-image {
							width: 60rpx;
							height: 40rpx;
						}

						.vip-title {
							color: #f0ce96;
							opacity: 0.9;
							font-size: 13px;
							font-style: initial;
							font-weight: bold;
						}
					}

					.vip-desc {
						margin-top: 15rpx;
						margin-left: 10rpx;
						font-size: 11px;
						color: #f0ce96;
						opacity: 0.9;
					}
				}

				.vip-item-right {
					display: flex;
					width: 30%;
					margin: 0 auto;
					align-items: center;

					.activate-now {
						color: #535353;
						font-size: 11px;
						background-color: #f0ce96;
						width: 150rpx;
						height: 55rpx;
						margin: 0 auto;
						text-align: center;
						line-height: 55rpx;
						border-radius: 50rpx;
					}
				}
			}
		}

		.order {
			display: flex;
			flex-direction: column;
			width: 90%;
			height: 180rpx;
			background-color: #fff;
			margin: 30rpx auto;

			.order-top {
				display: flex;
				flex-direction: row;
				font-size: 12px;
				padding: 10rpx;
				margin-top: 10rpx;
				justify-content: space-between;

				.my-order {
					font-weight: 600;
					font-size: 12px;
					margin-left: 10rpx;
				}

				.whole-order {
					display: flex;
					flex-direction: row;
				}
			}

			.order-grid {
				text-align: center;

				.grid-title {
					font-size: 11px;
				}
			}
		}

		.market {
			display: flex;
			flex-direction: row;
			justify-content: center;
			width: 90%;
			height: 130rpx;
			background-color: #fff;
			border-radius: 10rpx;
			margin: 0 auto;

			.market-item {
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				width: 48%;

				.market-item-left {
					display: flex;
					flex-direction: column;
					margin: auto;
					margin-left: 20rpx;

					.left-item text {
						font-size: 15px;
						font-weight: bold;
					}

					.left-item-sub {
						font-size: 11px;
						margin-top: 10rpx;
						color: #797979;
					}
				}

				.separate-line {
					width: 4%;
					height: 10rpx;

					.separate-line text {
						line-height: 130rpx;
						color: #797979;
						font-weight: 900;
					}
				}
			}
		}

		.utils {
			display: flex;
			flex-direction: column;
			width: 90%;
			height: 350rpx;
			background-color: #fff;
			margin: 20rpx auto;
			margin-bottom: 50rpx;

			.utils-title {
				margin-top: 20rpx;
				margin-left: 20rpx;
				font-size: 15px;
				font-weight: bold;
			}

			.utils-grid {
				text-align: center;
				margin-top: 10rpx;

				.utils-grid-title {
					font-size: 12px;
				}
			}
		}

		.recommend {
			width: 90%;
			display: flex;
			flex-direction: column;
			margin: 0 auto;

			.recommend-divider {
				display: flex;
				flex-direction: row;
				margin: 10rpx auto;

				.recommend-divider text {
					font-size: 13px;
					font-weight: bold;
				}
			}

			.recommend-list {
				height: 500rpx;
				display: flex;
				flex-direction: row;
				justify-content: center;
				justify-items: center;
				flex-wrap: wrap;

				.recommend-list-item {
					width: 47%;
					height: 400rpx;
					margin: 10rpx;
					border-radius: 10rpx;
					background-color: #fff;
				}
			}
		}
	}
</style>
