<template>
	<view>
		<view class="shop-box">
			<view class="apply-shop">
				<view class="apply-title">
					<text>商家店铺信息</text>
				</view>
				<view class="apply-from">
					<u--form labelPosition="left" :model="businessFormData" ref="regForm">
						<u-form-item prop="businessName">
							<view class="apply-from-item">
								<text>商家名称</text>
								<u--input placeholder="请输入2-10字以内的商家名称" v-model="businessFormData.businessName" clearable fontSize="13px" border="bottom">
								</u--input>
							</view>
						</u-form-item>
						<u-form-item prop="shopName">
							<view class="apply-from-item">
								<text>店铺名称</text>
								<u--input placeholder="请输入2-20字以内的店铺名称" v-model="businessFormData.shopName" clearable fontSize="13px" border="bottom">
								</u--input>
							</view>
						</u-form-item>
						<u-form-item prop="businessPhone">
							<view class="apply-from-item">
								<text>店铺电话</text>
								<u--input placeholder="请输入11位店铺电话" v-model="businessFormData.businessPhone" clearable fontSize="13px" border="bottom"></u--input>
							</view>
						</u-form-item>
						<u-form-item prop="businessCity">
							<view class="apply-from-item">
								<text>所在城市</text>
								<u--input placeholder="请选择所在城市" v-model="businessFormData.businessCity" clearable fontSize="13px"
									border="bottom"></u--input>
								<view class="address" @click="selectCity">
									<tui-icon name="gps" :size="25" color="#577ee3"></tui-icon>
								</view>
							</view>
						</u-form-item>
						<u-form-item prop="businessDetailAddress">
							<view class="apply-from-item">
								<text>详细地址</text>
								<u--input placeholder="请输入50字以内的详细地址" v-model="businessFormData.businessDetailAddress" clearable
									fontSize="13px" border="bottom">
								</u--input>
								<view class="address" @click="selectLocation">
									<tui-icon name="gps" :size="25" color="#577ee3"></tui-icon>
								</view>
							</view>
						</u-form-item>
						<u-form-item prop="businessDescribe">
							<view class="apply-from-item">
								<text>店铺简介</text>
								<u--textarea maxlength="100" v-model="businessFormData.businessDescribe" placeholder="请输入100字以内的店铺简介"
									placeholderStyle="fontSize: 11px;" border="bottom" autoHeight fontSize="11px" count>
								</u--textarea>
							</view>
						</u-form-item>
						<u-form-item prop="phone">
							<view class="apply-from-item">
								<text>店铺LOGO</text>
								<view class="logo-upload">
									<u-upload :fileList="fileList" @afterRead="afterRead" @delete="deletePic"
										name="shopLogo" :maxCount="1" width="200" height="200">
									</u-upload>
								</view>
							</view>
						</u-form-item>
						<view class="apply-title admin-user">
							<text>商家管理员身份信息</text>
						</view>
						<u-form-item prop="businessUserName">
							<view class="apply-from-item">
								<text>管理员姓名</text>
								<u--input placeholder="请输入管理员姓名" v-model="businessFormData.businessUserName" clearable fontSize="13px" border="bottom">
								</u--input>
							</view>
						</u-form-item>
						<u-form-item prop="businessUserAge">
							<view class="apply-from-item">
								<text>管理员年龄</text>
								<u--input placeholder="请输入管理员年龄" v-model="businessFormData.businessUserAge" clearable fontSize="13px" border="bottom">
								</u--input>
							</view>
						</u-form-item>
						<u-form-item prop="businessUserSex">
							<view class="apply-from-item">
								<text>管理员性别</text>
								<u--input placeholder="请选择管理员性别" v-model="businessFormData.businessUserSex" @focus="pickerShow = true" clearable fontSize="13px"
									border="bottom">
								</u--input>
							</view>
							<view>
								<u-picker :show="pickerShow" :columns="columns" @cancel="pickerShow = false"
									@confirm="selectSex"></u-picker>
							</view>
						</u-form-item>
						<u-form-item prop="businessUserIdCard">
							<view class="apply-from-item">
								<text>身份证号码</text>
								<u--input placeholder="请输入18位身份证号码" v-model="businessFormData.businessUserIdCard" clearable fontSize="13px" border="bottom">
								</u--input>
							</view>
						</u-form-item>
						<u-form-item prop="businessUserPhone">
							<view class="apply-from-item">
								<text>联系方式</text>
								<u--input placeholder="请输入常用联系人11位手机号码" v-model="businessFormData.businessUserPhone" clearable fontSize="13px" border="bottom">
								</u--input>
							</view>
						</u-form-item>
						<u-form-item prop="businessUserEmail">
							<view class="apply-from-item">
								<text>联系邮箱</text>
								<u--input placeholder="请输入常用联系邮箱" v-model="businessFormData.businessUserEmail" clearable fontSize="13px" border="bottom"></u--input>
							</view>
						</u-form-item>
					</u--form>
				</view>
				<view class="button-box">
					<u-button type="primary" text="提交申请" @click="businessSubmit()"
						color="linear-gradient(to right, rgb(76, 99, 252), rgb(255, 60, 226))"></u-button>
				</view>
			</view>
			<tui-loading v-if="businessLoading" text="正在入驻,请稍后..."></tui-loading>
			<tui-toast ref="toast"></tui-toast>
		</view>
	</view>
</template>

<script>
	const citySelector = requirePlugin('citySelector')
	const chooseLocation = requirePlugin('chooseLocation')
	import { addBusiness } from '@/api/merchantStores/merchantStores.js'
	import {
		mapState,
		mapActions,
		mapGetters
	} from 'vuex'
	export default {
		computed: {
			...mapState(['user']),
			...mapGetters(['getToken'])
		},
		data() {
			return {
				// 入驻加载效果
				businessLoading: false,
				// 商家店铺logo
				fileList: [],
				addressValue: "",
				userSex: '',
				// 性别选择器
				pickerShow: false,
				columns: [
					['男', '女']
				],
				businessFormData: {
					userId: uni.getStorageSync('LOGIN_USER_DETAIL') != null ? uni.getStorageSync('LOGIN_USER_DETAIL').userInfo.userId : '',
					businessName: "",
					shopName: "",
					businessPhone: "",
					businessCity: "",
					businessCityLatitude: "",
					businessCityLongitude: "",
					businessDetailAddress: "",
					businessDetailAddressLatitude: "",
					businessDetailAddressLongitude: "",
					businessDescribe: "",
					businessLogo: "",
					businessUserName: "",
					businessUserIdCard: "",
					businessUserPhone: "",
					businessUserEmail: "",
					businessUserSex: "",
					businessUserAge: "",
					businessLogo: ""
				},
				rules: {
					'businessName': {
						type: 'string',
						required: true,
						message: '请填写商家名称',
						trigger: ['blur', 'change']
					},
					'shopName': {
						type: 'string',
						required: true,
						message: '请填写店铺名称',
						trigger: ['blur', 'change']
					},
					'businessPhone': [{
							type: 'string',
							required: true,
							len: 11,
							message: '请填写11位店铺电话号码',
							trigger: ['change', 'blur']
						},
						{
							pattern: /^[0-9]*$/g,
							transform(value) {
								return String(value);
							},
							message: '只能为数字',
							trigger: ['change', 'blur']
						}
					],
					'businessCity': {
						type: 'string',
						required: true,
						message: '请选择所在城市',
						trigger: ['blur', 'change']
					},
					'businessDetailAddress': {
						type: 'string',
						required: true,
						message: '请选择所在详细地址',
						trigger: ['blur', 'change']
					},
					'businessDescribe': {
						type: 'string',
						required: true,
						message: '请填写店铺简介',
						trigger: ['blur', 'change']
					},
					'businessUserName': {
						type: 'string',
						required: true,
						message: '请填写管理员姓名',
						trigger: ['blur', 'change']
					},
					'businessUserPhone': [{
							type: 'string',
							required: true,
							len: 11,
							message: '请填写11位管理员手机号码',
							trigger: ['change', 'blur']
						},
						{
							pattern: /^[0-9]*$/g,
							transform(value) {
								return String(value);
							},
							message: '只能为数字',
							trigger: ['change', 'blur']
						}
					],
					'businessUserIdCard': {
						type: 'string',
						required: true,
						len: 18,
						message: '请填写18位管理员身份证号码',
						trigger: ['blur', 'change']
					},
					'businessUserEmail': {
						type: 'email',
						required: true,
						message: '请填写管理员联系邮箱',
						trigger: ['blur', 'change']
					},
					'businessUserSex': {
						type: 'string',
						required: true,
						message: '请选择管理员性别',
						trigger: ['blur', 'change']
					},
					'businessUserAge': {
						type: 'number',
						required: true,
						message: '请填写管理员年龄',
						trigger: ['blur', 'change']
					}
				},
							
			}
		},
		onReady() {
			this.$refs.regForm.setRules(this.rules)
		},
		onShow() {
			const selectedCity = citySelector.getCity();
			const location = chooseLocation.getLocation();
			if (selectedCity != null) {
				this.businessFormData.businessCity = selectedCity.fullname
				this.businessFormData.businessCityLatitude = selectedCity.location.latitude
				this.businessFormData.businessCityLongitude = selectedCity.location.longitude
			}
			if (location != null) {
				this.businessFormData.businessDetailAddress = location.address
				this.businessFormData.businessDetailAddressLatitude = location.latitude
				this.businessFormData.businessDetailAddressLongitude = location.longitude
			}
		},
		methods: {
			showToast(title,url,icon,content) {
				let params = {
					title: title,
					content : content ,
					imgUrl: url,
					icon: icon
				}
				this.$refs.toast.show(params);
			},
			// 立即申请按钮事件
			businessSubmit(){
				let _this = this
				_this.businessLoading = true
				_this.$refs.regForm.validate().then(async res => {
					//  上传logo
					const {data: result} = await _this.uploadFilePromise(_this.fileList[0].url)
					const resLogo = JSON.parse(result)
					if(resLogo.code == 0){
						_this.businessFormData.businessLogo = resLogo.data
						// 入驻
						const {data: addRes} = await addBusiness(_this.businessFormData)
						if(addRes.code == 0){
							_this.businessLoading = false
							_this.showToast(addRes.msg,"https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/toast_icon/merchantsettlement.png",true,"")
							setTimeout(()=>{
								uni.redirectTo({
									url: '/subPages/personal-pages/merchant-settlement/merchant-stores/merchant-stores'
								})
							},2000)
						}else{
							this.showToast(addRes.msg,"https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/toast_icon/fail.png",true,"")
						}
					}else{
						this.showToast(addRes.msg,"https://eyouhui.oss-cn-hangzhou.aliyuncs.com/wx/toast_icon/img_fail.png",true,"")
					}
				}).catch(errors => {
					uni.$u.toast(errors[0].message)
				})
			},
			// 选择性别
			selectSex(e) {
				this.businessFormData.businessUserSex = e.value[0]
				this.userSex = e.value[0]
				this.pickerShow = false
			},
			// 选择所在城市
			selectCity() {
				const key = 'YJXBZ-M5ECJ-CWZFI-FITCL-WGG4S-WFBRL'; //使用在腾讯位置服务申请的key
				const referer = 'EYouHui'; //调用插件的app的名称
			
				const hotCitys = '昆明,北京,上海,深圳,重庆,成都,广州,香港'; // 用户自定义的的热门城市
			
				wx.navigateTo({
					url: `plugin://citySelector/index?key=${key}&referer=${referer}&hotCitys=${hotCitys}`,
				})
			},
			// 选择详细地址
			selectLocation() {
				const key = 'YJXBZ-M5ECJ-CWZFI-FITCL-WGG4S-WFBRL'; //使用在腾讯位置服务申请的key
				const referer = 'EYouHui'; //调用插件的app的名称
				const location = JSON.stringify({
					latitude: 24.9749,
					longitude: 102.7998
				});
			
				const category = '生活服务,娱乐休闲';
			
				wx.navigateTo({
					url: `plugin://chooseLocation/index?key=${key}&referer=${referer}&location=${location}&category=${category}`
				});
			},
			// 删除图片
			deletePic(event) {
				this.fileList = []
			},
			// 新增图片
			async afterRead(event) {
				// this.fileList.push({
				// 	...event.file,
				// 	status: 'uploading',
				// 	message: '上传中'
				// })
				this.fileList.push({
					...event.file
				})
				// let result = await this.uploadFilePromise(event.file.url)
			
				// this.fileList.splice(0, 1, Object.assign(event.file, {
				// 		status: 'success',
				// 		message: '上传成功',
				// 		url: result
				// 	})
				// )
			},
			// 上传logo
			uploadFilePromise(url) {
				let _this = this
				return new Promise((resolve, reject) => {
					// uploadLogo()
					let a = uni.uploadFile({
						url: 'https://eyh-shop.com/merchantStores/wx/uploadLogo/' + _this.businessFormData.businessName + _this.businessFormData.shopName, // 仅为示例，非真实的接口地址
						filePath: url,
						name: 'shopLogo',
						header: {
							'authorization': _this.getToken,
							"content-type":"multipart/form-data"
						},
						success: (res) => {
							resolve(res)
							// setTimeout(() => {
							// 	resolve(res.data.data)
							// }, 1000)
						},
						fail: (err) => {
							reject(err)
						}
					})
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.shop-box {
		width: 100%;
		display: flex;
		flex-direction: column;
		background-color: #f6f6f6;
		align-items: center;

		.apply-shop {
			width: 90%;
			background-color: #fff;
			padding: 15rpx;
			border-radius: 10rpx;
			margin-bottom: 30rpx;

			.apply-title {
				font-size: 15px;
				font-weight: bold;
			}

			.apply-from {
				width: 100%;
				margin: 0 auto;
				margin-top: 10rpx;

				.apply-from-item {
					display: flex;
					flex-direction: row;

					.address {
						margin-top: 10rpx;
					}

					.apply-from-textarea {
						margin-left: 10rpx;
					}
				}

				.apply-from-item text {
					font-size: 13px;
					margin-top: 25rpx;
					margin-right: 10rpx;
				}
			}
		}

		.button-box {
			width: 85%;
			background-color: #fff;
			margin: 30rpx;
		}
	}
</style>
