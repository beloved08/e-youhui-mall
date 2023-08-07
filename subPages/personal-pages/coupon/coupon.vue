<template>
	<view class="container">
		<view class="coupon-top">
			<tui-tabs :tabs="tabs" :currentTab="currentTab" itemWidth="50%" @change="couponClassChange"></tui-tabs>
		</view>
		<view class="coupon-content-box">
			<view v-show="currentTab == 0 ? true : false">
				<view class="notuse-box" v-if="noUseCouponList.length > 0">
					<view class="notuse-item" v-for="(item,index) in noUseCouponList" :key="index"
						@click="selectCoupon(item)">
						<view class="notuse-image">
							<view class="coupon">
								<view class="">
									<text>￥</text>
									<text style="font-size: 20px;">{{item.discountAmount}}</text>
								</view>
								<view class="">满{{item.fullAvailable}}元可用</view>
							</view>
						</view>
						<view class="notuse-desc">
							<view class="notuse-title">
								<text>{{item.couponName}}</text>
							</view>
							<view class="notuse-time">
								<text>{{item.startTime.split(' ')[0]}}~{{item.endTime.split(' ')[0]}}</text>
							</view>
						</view>
						<view class="notuse-icon">
							<image src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/notuse.png"
								style="width: 100%;height: 100%; border-radius: 10rpx;"></image>
						</view>
					</view>
				</view>
				<view class="" v-else>
					<u-empty mode="car" height="200" textSize="18" text="暂无未使用的优惠券"
						icon="http://cdn.uviewui.com/uview/empty/car.png">
					</u-empty>
				</view>

			</view>
			<view v-show="currentTab == 1 ? true : false">
				<view class="notuse-box" v-if="usedCouponList.length > 0">
					<view class="notuse-item" v-for="(item,index) in usedCouponList" :key="index">
						<view class="notuse-image">
							<view class="coupon">
								<view class="">
									<text>￥</text>
									<text style="font-size: 20px;">{{item.discountAmount}}</text>
								</view>
								<view class="">满{{item.fullAvailable}}元可用</view>
							</view>
						</view>
						<view class="notuse-desc">
							<view class="notuse-title">
								<text>{{item.couponName}}</text>
							</view>
							<view class="notuse-time">
								<text>{{item.startTime.split(' ')[0]}}~{{item.endTime.split(' ')[0]}}</text>
							</view>
						</view>
						<view class="notuse-icon">
							<image src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/used.png"
								style="width: 100%;height: 100%; border-radius: 10rpx;"></image>
						</view>
					</view>
				</view>
				<view v-else class="">
					<u-empty mode="car" height="200" textSize="18" text="暂无已使用的优惠券"
						icon="http://cdn.uviewui.com/uview/empty/car.png">
					</u-empty>
				</view>
			</view>
			<view v-show="currentTab == 2 ? true : false">
				<view class="notuse-box" v-if="expireCouponList.length > 0">
					<view class="notuse-item" v-for="(item,index) in expireCouponList" :key="index">
						<view class="notuse-image">
							<view class="coupon">
								<view class="">
									<text>￥</text>
									<text style="font-size: 20px;">{{item.discountAmount}}</text>
								</view>
								<view class="">满{{item.fullAvailable}}元可用</view>
							</view>
						</view>
						<view class="notuse-desc">
							<view class="notuse-title">
								<text>{{item.couponName}}</text>
							</view>
							<view class="notuse-time">
								<text>{{item.startTime.split(' ')[0]}}~{{item.endTime.split(' ')[0]}}</text>
							</view>
						</view>
						<view class="notuse-icon">
							<image src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/expired.png"
								style="width: 100%;height: 100%; border-radius: 10rpx;"></image>
						</view>
					</view>
				</view>
				<view v-else class="">
					<u-empty mode="car" height="200" textSize="18" text="暂无已过期的优惠券"
						icon="http://cdn.uviewui.com/uview/empty/car.png">
					</u-empty>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		getUserCouponList
	} from '@/api/user/user.js'
	import {
		mapState,
		mapActions,
		mapGetters
	} from 'vuex'
	export default {
		data() {
			return {
				userId: uni.getStorageSync('LOGIN_USER_DETAIL') != null ? uni.getStorageSync('LOGIN_USER_DETAIL')
					.userInfo.userId : '',
				// 使用中
				noUseCouponList: [],
				// 已使用
				usedCouponList: [],
				// 已过期
				expireCouponList: [],
				currentTab: 0,
				currentId: 0,
				currentOrder: {},
				tabs: [{
					name: "未使用"
				}, {
					name: "已使用"
				}, {
					name: "已过期"
				}]
			};
		},
		async onLoad(o) {
			this.currentOrder = o
			this.currentId = o.id
			const {
				data: res
			} = await getUserCouponList(this.userId)
			if (o.id == 0) {
				//商家优惠券
				res.data.map(c => {
					if (c.isUse == 0 && c.isExpire == 0 && c.businessId == o.businessId && c.fullAvailable <= o
						.price) {
						//使用中
						this.noUseCouponList.push(c)
					}
					if (c.isExpire == 2 && c.businessId == o.businessId) {
						// 已过期
						this.expireCouponList.push(c)
					}
					if (c.isUse == 1 && c.businessId == o.businessId) {
						// 已使用
						this.usedCouponList.push(c)
					}
				})
			} else if (o.id == 1) {
				res.data.map(c => {
					if (c.isUse == 0 && c.isExpire == 0) {
						//使用中
						this.noUseCouponList.push(c)
					}
					if (c.isExpire == 2) {
						// 已过期
						this.expireCouponList.push(c)
					}
					if (c.isUse == 1) {
						// 已使用
						this.usedCouponList.push(c)
					}
				})
			} else {
				res.data.map(c => {
					if (c.isUse == 0 && c.isExpire == 0 && c.businessId == null && c.fullAvailable <= o.price) {
						//使用中
						this.noUseCouponList.push(c)
					}
					if (c.isExpire == 2 && c.businessId == null) {
						// 已过期
						this.expireCouponList.push(c)
					}
					if (c.isUse == 1 && c.businessId == null) {
						// 已使用
						this.usedCouponList.push(c)
					}
				})
			}
		},
		methods: {
			...mapGetters(['getCommodityList']),
			...mapActions(['selectOrderCoupon', 'saveCommodityList']),
			selectCoupon(e) {
				let commodityList = this.getCommodityList()
				
				if(this.currentId == 2){
					let isSelect = false
					//判断该优惠券是否已选择
					commodityList.map(c => {
						if(c.selectCommonCoupon != null){
							if(c.selectCommonCoupon.couponId  == e.couponId){
								isSelect = true
							}
						}
					})
					if(isSelect){
						//已经选择了
						uni.showToast({
							title: "您已选择使用该优惠券，不能重复使用",
							icon: 'none',
							duration: 2000
						})
					}else{
						//设置通用优惠券
						commodityList.map(c => {
							if (c.commodity.commodityId == this.currentOrder.commodityId) {
								c.selectCommonCoupon = e
							}
						})
						this.saveCommodityList(commodityList)
						uni.navigateBack()
					}
				}else if ( this.currentId == 0) {
					let isSelect = false
					//判断该优惠券是否已选择
					commodityList.map(c => {
						if(c.selectBusinessCoupon != null){
							if(c.selectBusinessCoupon.couponId  == e.couponId){
								isSelect = true
							}
						}
					})
					if(isSelect){
						//已经选择了
						uni.showToast({
							title: "您已选择使用该优惠券，不能重复使用",
							icon: 'none',
							duration: 2000
						})
					}else{
						//商家优惠券
						commodityList.map(c => {
							if (c.commodity.commodityId == this.currentOrder.commodityId) {
								c.selectBusinessCoupon = e
							}
						})
						this.saveCommodityList(commodityList)
						uni.navigateBack()
					}
				} else {
					//所有
				}
			},
			couponClassChange(e) {
				this.currentTab = e.index
			}
		}
	}
</script>

<style lang="scss" scoped>
	.container {
		width: 100%;
		height: 100vh;
		background-color: #f6f6f6;
		display: flex;
		flex-direction: column;

		.coupon-top {
			width: 100%;
		}

		.coupon-content-box {
			width: 100%;
			height: 100%;
			margin: 0 auto;
			margin-top: 20rpx;
			background-color: #fff;

			.notuse-box {
				width: 95%;
				margin: 0 auto;
				margin-top: 10rpx;
				margin-bottom: 50rpx;
				height: 150rpx;
				display: flex;
				flex-direction: column;

				.notuse-item {
					width: 95%;
					height: 100%;
					padding: 20rpx;
					margin-bottom: 20rpx;
					background-color: #f6f6f6;
					display: flex;
					flex-direction: row;
					justify-content: space-between;

					.notuse-image {
						width: 30%;
						height: 100%;

						.coupon {
							width: 100%;
							height: 100%;
							background-color: #ff0000;
							display: flex;
							flex-direction: column;
							align-items: center;
							justify-content: center;
							color: #fff;
							font-size: 12px;
						}
					}

					.notuse-desc {
						width: 40%;
						height: 100%;
						margin-top: 20rpx;
						margin-left: 30rpx;
						display: flex;
						flex-direction: column;

						.notuse-title {
							font-size: 13px;
						}

						.notuse-time {
							margin-top: 40rpx;
							font-size: 10px;
						}
					}

					.notuse-icon {
						width: 12%;
						height: 60%;
						margin-top: 20rpx;
						margin-right: 20rpx;
					}
				}
			}
		}
	}
</style>
