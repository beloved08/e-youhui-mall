<template>
	<view class="container">
		<view class="list-view-box">
			<tui-list-view>
				<tui-list-cell arrow>
					<view class="list-view-item">
						<view class="item-left">头像</view>
						<view class="item-right">
							<u-avatar :src="user.userInfo.avatar" class="image" size="100"></u-avatar>
						</view>
					</view>
				</tui-list-cell>
				<tui-list-cell arrow>
					<view class="list-view-item">
						<view class="item-left-text">名字</view>
						<view class="item-right-text">
							{{user.userInfo.realName}}
						</view>
					</view>
				</tui-list-cell>
				<tui-list-cell arrow>
					<view class="list-view-item">
						<view class="item-left-text">电话号码</view>
						<view class="item-right-text">
							<view>
								<text>{{user.userInfo.phone}}</text>
							</view>
						</view>
					</view>
				</tui-list-cell>
				<tui-list-cell arrow>
					<view class="list-view-item">
						<view class="item-left-text">邮箱</view>
						<view class="item-right-text">
							<view v-if="user.userInfo.email == null">
								<u--text type="primary" text="前往绑定" size="26"></u--text>
							</view>
							<view v-else>
								<text>{{user.userInfo.email}}</text>
							</view>
						</view>
					</view>
				</tui-list-cell>
				<tui-list-cell arrow>
					<view class="list-view-item">
						<view class="item-left-text">性别</view>
						<view class="item-right-text">
							<text v-if="user.userInfo.sex == 0">男</text>
							<text v-else>女</text>
						</view>
					</view>
				</tui-list-cell>
				<tui-list-cell arrow>
					<view class="list-view-item">
						<view class="item-left-text">地区</view>
						<view class="item-right-text">
							<view v-if="user.userInfo.address == null">
								<u--text type="primary" text="选择地区" size="26"></u--text>
							</view>
							<view v-else>
								<text>{{user.userInfo.address}}</text>
							</view>
						</view>
					</view>
				</tui-list-cell>
				<tui-list-cell arrow>
					<view class="list-view-item">
						<view class="item-left-text">账号状态</view>
						<view class="item-right-text">
							<text v-if="user.userInfo.status == 0">正常</text>
							<text v-else>异常</text>
						</view>
					</view>
				</tui-list-cell>
				<tui-list-cell arrow>
					<view class="list-view-item">
						<view class="item-left-text">用户类型</view>
						<view class="item-right-text">
							<text v-if="user.userInfo.type == 0">普通用户</text>
							<text v-else>会员用户</text>
						</view>
					</view>
				</tui-list-cell>
			</tui-list-view>
		</view>
	
	</view>
</template>

<script>
	import {
		mapState,
		mapMutations,
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
		onLoad() {
			// const user = uni.getStorageSync('LOGIN_USER_DETAIL')
		},
		methods: {
			...mapMutations(['setIsLogin']),
			...mapActions(['login']),
		}
	}
</script>

<style lang="scss">
.container{
	width: 100%;
	height: 100vh;
	background-color: #f6f6f6;
	display: flex;
	flex-direction: column;
	
	.list-view-box{
		width: 97%;
		margin: 0 auto;
		margin-top: 20rpx;
		background-color: #fff;
		border-radius: 10rpx;
		
		.list-view-item{
			width: 100%;
			display: flex;
			flex-direction: row;
			justify-content: space-between;
			
			.item-left{
				line-height: 100rpx;
				font-size: 13px;
			}
			
			.item-left-text{
				font-size: 13px;
			}
			
			.item-right-text{
				font-size: 13px;
				margin-right: 50rpx;
			}
			
			.item-right{
				height: 100rpx;
				width: 100rpx;
				margin-right: 50rpx;
				
				.image{
					width: 100%;
					height: 100%;
					border-radius: 50%;
				}
			}
		}
	}

}
</style>
