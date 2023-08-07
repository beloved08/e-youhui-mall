<template>
	<view class="container">
		<view class="apply-box">
			 <u--input
			    placeholder="请输入手机号码"
			    border="surround"
			    v-model="phone"
			  ></u--input>
		</view>
		<view class="apply-button">
			<u-button type="primary" @click="submitApply" text="提交申请"></u-button>
		</view>
		<view class="">
			<tui-modal :show="modal" custom>
				<view class="tui-modal-custom">
					<view class="">请输入验证码</view>
					<view class="tui-modal-custom-text">
						<u-code-input :focus="true" :fontSize="20" :maxlength="6" :size="60" v-model="value"></u-code-input>
					</view>
					<tui-button height="70rpx" width="400rpx" :size="28" type="danger" shape="circle" @click="handleClick">确定</tui-button>
				</view>
			</tui-modal>
		</view>
	</view>
</template>

<script>
	import { sendNationalPromotionPeopleCode,verifyNationalPromotion } from '@/api/merkete/merkete.js'
	import {
		mapState,
		mapActions,
		mapGetters
	} from 'vuex'
	export default {
		data() {
			return {
				phone: '',
				modal: false,
				value: '',
				userId: '',
			}
		},
		onLoad() {
			this.userId = this.getUserInfo().userId
		},
		methods: {
			...mapGetters(['getUserInfo']),
			async submitApply(){
				//发送验证码
				const { data:res } = await sendNationalPromotionPeopleCode(this.phone)
				if(res.code == 0){
					this.modal = true
					uni.showToast({
						title: res.msg,
						duration: 2000,
						icon:'none'
					})
				}
			},
			async handleClick(){
				//校验验证码
				// 存入数据库
				const { data:res } = await verifyNationalPromotion(this.phone,this.value,this.userId)
				if(res.code == 0){
					this.modal = false
					uni.showToast({
						title: res.msg,
						duration: 2000,
						icon:'none'
					})
					uni.switchTab({
						url:'/pages/personal/personal'
					})
				}else{
					uni.showToast({
						title: res.msg,
						duration: 2000,
						icon:'none'
					})
				}
			}
		}
	}
</script>

<style lang="scss">
.container{
	width: 100%;
	display: flex;
	flex-direction: column;
	
	.apply-box{
		width: 90%;
		margin: 0 auto;
		margin-top: 50rpx;
	}
	
	.apply-button{
		width: 90%;
		margin: 0 auto;
		margin-top: 30rpx;
	}
	
	.tui-modal-custom{
		height: 300rpx;
		display: flex;
		flex-direction: column;
		align-items: center;
		
		.tui-modal-custom-text{
			height: 100rpx;
			margin-top: 40rpx;
		}
	}
}
</style>
