<template>
	<view class="container">
		<view  v-if="cartCommodityList.length != 0">
			<view class="order-content-for"  v-for="(item,index) in cartCommodityList">
			<tui-swipe-action :operateWidth="140">
				<template v-slot:content>
					<view class="order-all-box">
						<view class="order-top-shop" @click="toBusiness(item)">
							<view class="shop-logo">
								<image
									:src="item.business.businessLogo"
									style="width: 100%;height: 100%; border-radius: 50%;"></image>
							</view>
							<view class="order-top-title">
								<text>{{item.business.shopName}}</text>
							</view>
							<view class="shop-more">
								<tui-icon name="arrowright" color="#000" :size="20"></tui-icon>
							</view>
						</view>
						<view class="tui-divider">
							<tui-divider gradual height="30" width="90%"></tui-divider>
						</view>
						<view class="order-content">
							<view class="select">
								<checkbox-group @change="checkboxChange(item,index)">
									<checkbox :value="item.commodity.id" :checked="cartListSelected[index].cartItemSelect"  style="transform:scale(0.8)"  color="#2979ff"/>
								</checkbox-group>
							</view>
							<view class="order-content-image" @click="toCommodityDetail(item)">
								<image
									:src="item.commodity.commodityImageUrl"
									style="width: 100%;height: 100%;border-radius: 6rpx;"></image>
							</view>
							<view class="order-content-desc">
								<view class="desc-title">
									<text>{{item.commodity.commodityName}}</text>
								</view>
								<view class="desc-class">
									<text>{{item.commodity.commodityDescribe}}</text>
								</view>
							</view>
							<view class="order-price-num">
								<view class="order-price">
									<text class="order-price-unit">￥</text>
									<text class="order-price-number" v-if="item.commodity.salesModel == 0">{{item.commodity.retailPrice * item.quantity}}</text>
									<text class="order-price-number" v-else>{{item.commodity.wholesalePrice * item.quantity}}</text>
								</view>
								<view class="order-num">
									<text class="order-num-unit">x</text>
									<text class="order-number">{{item.quantity}}</text>
								</view>
							</view>
						</view>
						<view class="order-bottom">
							<u-number-box v-model="item.quantity" :min="1" :max="item.commodity.commodityStock" :step="1" button-size="36" color="#ffffff"
								bgColor="#2979ff" iconStyle="color: #fff" inputWidth="50" buttonSize="50" integer ></u-number-box>
						</view>
					</view>
				</template>
				<template v-slot:button>
					<view class="tui-custom-btn_box">
						<tui-button type="gray-primary" width="140rpx" height="100%" @click="collectOrder(item)">收藏
						</tui-button>
						<tui-button type="gray-danger" width="140rpx" height="100%" @click="deleteOrder(item,index)">删除
						</tui-button>
					</view>
				</template>
			</tui-swipe-action>
		</view>
		<view class="bottom">
			<view class="bottom-select">
				<checkbox-group @change="selectAll">
					<checkbox  value="selectAllBox" :checked="selectAllBox" style="transform:scale(0.7)"  color="#2979ff"/>全选
				</checkbox-group>
				
				<u-checkbox-gr<!-- oup @change="selectAll">
					<u-checkbox v-model="selectAllBox" labelSize="26" activeColor="#ff0000" size="30" :checked="selectAllBox" shape="circle" label="全选"></u-checkbox>
				</u-checkbox-group> -->
				</view>
			<view class="bottom-price">
				<text>总计：</text>
				<text style="color: red;">￥</text>
				<text style="color: red; font-size: 16px;">{{allTotalPriceComputed}}</text>
				<text style="color: red; font-size: 12px;margin-left: 5rpx;">元</text>
			</view>
			<view class="bottom-buttom">
				<u-button color="red" text="结算" @click="settleAccount"  size="small" shape="circle"></u-button>
			</view>
		</view>
		</view>
		<view v-else class="noLogin">
				<!-- <image style="width: 85%; height: 100%;" src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/card/cart-empty.png" mode=""></image>
				<view style="margin-left: 20rpx;">
					<text>您的购物车为空</text>
				</view> -->
				<!-- <view style="margin-top: 40rpx;">
					<u-button type="primary" @click="toLogin" size="small" text="前往登录"></u-button>
				</view> -->
				<u-empty
				textSize="24"
				width="240"
				height="240"
				        mode="car"
				        icon="http://cdn.uviewui.com/uview/empty/car.png"
				>
				</u-empty>
		</view>
	</view>
</template>

<script>
	import { getUserCart } from '@/api/commodity/commodity.js'
	import {addUserCollect} from '@/api/user/user.js'
	import {
		mapState,
		mapGetters,
		mapMutations,
		mapActions
	} from 'vuex'
	export default {
		...mapState(['order']),
		data() {
			return {
				// 购物车列表数据
				cartCommodityList: [],
				// 购物车列表数据是否选中
				cartListSelected: [],
				// 全选按钮控制
				selectAllBox: false,
				// 选中的商品列表，即结算按钮结算的商品
				selectCommodityList: [],
				// 价格总计
				allTotalPrice: 0
			};
		},
		onPullDownRefresh() {
			let _this = this
			_this.initCartData()
			uni.stopPullDownRefresh()
		},
		async onLoad() {
			if(!this.user.isLogin){
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
								url:'/subPages/personal-pages/login/wx-login/wx-login?loginTo=' + '/pages/cart/cart'
							})
						} else {
							uni.navigateTo({
								url:'/pages/cart/cart'
							})
						}
					}
				})
			}else{
				this.initCartData()
			}
		},
		computed:{
			...mapState(['user']),
			...mapState(['cart']),
			allTotalPriceComputed(){
				let price = 0
				this.selectCommodityList.map(c=>{
					if(c.commodity.salesModel == 0){
						price += c.quantity * c.commodity.retailPrice
					}else{
						price += c.quantity * c.commodity.wholesalePrice
					}
				})
				return price
			}
		},
		methods: {
			...mapGetters(['getCartCommodityInfo']),
			...mapMutations(['setCartCommodityInfo']),
			...mapActions(['resetSaveCartCommodityInfo','saveCommodityList']),
			// 结算按钮事件
			settleAccount(){
				if(this.selectCommodityList.length == 0){
					uni.showToast({
						title: '请先选择购买的商品',
						icon: 'none',
						duration:2000
					})
				}
				this.saveCommodityList(this.selectCommodityList)
				uni.navigateTo({
					url: 'subCartPages/settle-account/settle-account'
				})
			},
			toBusiness(e){
				uni.navigateTo({
					url:'subIndexPages/business-center/business-center?businessId=' + e.business.businessId
				})
			},
			async initCartData(){
				const {data:res} = await getUserCart(this.user.userInfo.userId)
				if(res.data.length != 0){
					res.data.map(cart => {
						this.setCartCommodityInfo(cart)
					})
				}
				this.cartCommodityList = res.data
				this.selectCommodityList = res.data
				this.selectAllBox = true
				this.cartCommodityList.map((c,i) => {
					this.cartListSelected.push({
						'cartItemSelect': true
					})
				})
			},
			checkboxChange(item,index){
				this.cartListSelected[index].cartItemSelect = !this.cartListSelected[index].cartItemSelect
				this.cartCommodityList[index].selected = this.cartListSelected[index].cartItemSelect
				this.selectCommodityList = []
				this.cartCommodityList.map(c =>{
					if(c.selected){
						this.selectCommodityList.push(c)
					}
				})
			},
			selectAll(e){
				this.selectAllBox = !this.selectAllBox
				this.cartCommodityList.map(c => {
					c.selected = this.selectAllBox
				})
				if(this.selectAllBox){
					// true,全选
					this.cartListSelected.map(c => {
						c.cartItemSelect = true
					})
				}else{
					this.cartListSelected.map(c => {
						c.cartItemSelect = false
					})
				}
				this.selectCommodityList = []
				this.cartCommodityList.map(c =>{
					if(c.selected){
						this.selectCommodityList.push(c)
					}
				})
			},
			toCommodityDetail(e){
				uni.navigateTo({
					url:'subIndexPages/commodityDetail/commodityDetail?commodityId=' + e.commodity.commodityId
				})
			},
			async collectOrder(e) {
				const userCollect = {
					"userId": this.user.userInfo.userId,
					"commodityId": e.commodity.commodityId,
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
			},
			deleteOrder(e,i) {
				this.cartCommodityList.splice(e,1)
				const r = {
					"deleteItem": e,
					"commodityList": this.cartCommodityList
				}
				this.resetSaveCartCommodityInfo(r)
				
				uni.showToast({
					title: "删除->" + e.commodity.commodityName,
					icon: 'none'
				})
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
		// height: 100%;
		// overflow-y: auto;
		display: flex;
		flex-direction: column;
		margin-bottom: 120rpx;
		background-color: #f6f6f6;

.bottom{
				position: fixed;
				width: 100%;
				height: 100rpx;
				background-color: #fff;
				bottom: 0;
				display: flex;
				flex-direction: row;
				border-top: 1rpx solid #eee;
				justify-content: space-between;
				line-height: 100rpx;
				
				.bottom-select{
					font-size: 12px;
					margin-left: 30rpx;
				}
				
				.bottom-price{
					font-size: 12px;
				}
				
				.bottom-buttom{
					margin-right: 40rpx;
					margin-top: 20rpx;
				}
			}
			
		.order-content-for {
			width: 97%;
			margin: 0 auto;
			margin-top: 20rpx;
			margin-bottom: 10rpx;
			
			.tui-custom-btn_box {
				display: flex;
				flex-direction: row;
				height: 100%;
			}

			.order-all-box {
				display: flex;
				flex-direction: column;
				width: 100%;
				background-color: #fff;
				padding: 20rpx;
				border-radius: 20rpx;

				.order-top-shop {
					display: flex;
					flex-direction: row;

					.shop-logo {
						width: 40rpx;
						height: 40rpx;
						border-radius: 50%;
					}

					.order-top-title {
						font-size: 14px;
						font-weight: bold;
						margin-left: 10rpx;
					}
				}

				.tui-divider {
					margin-left: -20rpx;
				}

				.order-top {
					display: flex;
					flex-direction: row;
					justify-content: space-between;

					.order-top-left {
						display: flex;
						flex-direction: row;

						.order-num {
							font-size: 12px;
							margin-left: 10rpx;
						}
					}

					.order-top-right {
						margin-right: 20rpx;

						.order-status {
							font-size: 13px;
							color: red;
							margin-right: 30rpx;
						}
					}
				}

				.order-content {
					display: flex;
					flex-direction: row;
					margin-top: 20rpx;
					margin-left: 20rpx;
					// justify-content: space-between;
					justify-content: space-around;
					
					.select{
						display: flex;
						flex-direction: column;
						align-items: center;
						margin-top: 50rpx;
						margin-left: -20rpx;
					}

					.order-content-image {
						width: 150rpx;
						height: 150rpx;
						margin-left: -30rpx;
					}

					.order-content-desc {
						width: 45%;
						margin-left: -10rpx;
						margin-top: 10rpx;
						display: flex;
						flex-direction: column;

						.desc-title {
							width: 100%;
							font-size: 13px;
							color: #000;
							word-break: break-all;
							overflow: hidden;
							text-overflow: ellipsis;
							white-space: nowrap;
						}

						.desc-class {
							width: 100%;
							margin-top: 10rpx;
							font-size: 11px;
							color: #c1c1c1;
							word-break: break-all;
							overflow: hidden;
							text-overflow: ellipsis;
							white-space: nowrap;
						}
					}

					.order-price-num {
						display: flex;
						flex-direction: column;

						.order-price {
							color: red;
							margin-right: 20rpx;

							.order-price-unit {
								font-size: 12px;
							}

							.order-price-number {
								font-size: 13px;
								font-weight: bold;
							}
						}

						.order-num {
							color: #828282;
							margin-left: 30rpx;

							.order-num-unit {
								font-size: 11px;
							}

							.order-number {
								font-size: 12px;
								margin-left: 6rpx;
							}
						}

					}
				}

				.order-bottom {
					width: 90%;
					margin: 0 auto;
					margin-top: 10rpx;
					display: flex;
					flex-direction: row;
					justify-content: flex-end;
					z-index: 999;
				}
			}

		}
		
		.noLogin{
			width: 50%;
			height: 280rpx;
			margin: 0 auto;
			margin-top: 250rpx;
			color: #828282;
			font-size: 13px;
		}
	}
</style>
