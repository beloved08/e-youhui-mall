<template>
	<view class="container">
		<view class="list">
			<tui-list-view color="#777">
				<tui-list-cell hover arrow @click="totTermsService">
					服务条款
				</tui-list-cell>
				<tui-list-cell hover arrow @click="toPrivacyPolicy">
					隐私政策
				</tui-list-cell>
			</tui-list-view>
		</view>
		<view class="logout">
			<view class="button">
				<u-button text="退出登录" @click="logout" color="linear-gradient(to right, rgb(255, 0, 0), rgb(255, 0, 0))"></u-button>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		mapActions
	} from 'vuex'
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			...mapActions(['removeUserDetail']),
			totTermsService(){
				uni.navigateTo({
					url:'/subPages/personal-pages/system-settings/terms-service/terms-service'
				})
			},
			toPrivacyPolicy(){
				uni.navigateTo({
					url:'/subPages/personal-pages/system-settings/privacy-policy/privacy-policy'
				})
			},
			logout(){
				let _this = this
				uni.showToast({
					title:'正在登出',
					icon:'loading',
					duration: 2000
				})
				// 清除vuex
				_this.removeUserDetail()
				setTimeout(() => {
					uni.showToast({
						title:'登出成功',
						icon:'success'
					})
				},1000)
				setTimeout(()=>{
					uni.switchTab({
						url:'/pages/personal/personal'
					},1000)
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
.container{
	width: 100%;
	height: 100vh;
	display: flex;
	flex-direction: column;
	background-color: #f6f6f6;
	
	.list{
		width: 100%;
		margin-top: 30rpx;
	}
	
	.logout{
		position: fixed;
		bottom: 80rpx;
		width: 100%;
		line-height: var(--footer-height);
		
		.button{
			width: 80%;
			margin: 0 auto;
			display: flex;
			justify-content: center;
			align-items: center;
			color: #fff;
		}
	}
}
</style>
