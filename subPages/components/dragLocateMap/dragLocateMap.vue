<template>
	<view class="content">
		<view class="">
			<map style="width: 100%; height: 700rpx;" enable-3D show-compass enable-poi :show-location='true' :longitude="longitude"
				:latitude="latitude" :scale="scale" enable-traffic :markers="markers" @markertap="markertap()" @callouttap='callouttap()'>
			</map>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				latitude: 24.9749, //纬度
				longitude: 102.7998, //经度
				scale: 16, //缩放级别
				markers: [{
					id:0,
					latitude: 24.9749,
					longitude: 102.7998,
					iconPath: '/static/map/location.png',
					width: 30,
					height: 30
				}]
			}
		},
		computed: {

		},
		mounted() {
			uni.getLocation({
				success: function (res) {
					console.log('位置名称：' + res.name);
					console.log('详细地址：' + res.address);
					console.log('纬度：' + res.latitude);
					console.log('经度：' + res.longitude);
				},
				fail(r){
					console.log(r)
				}
			});
		},
		methods: {
			openMap(){
				console.log('------')
				uni.chooseLocation({
					success: function (res) {
						console.log('位置名称：' + res.name);
						console.log('详细地址：' + res.address);
						console.log('纬度：' + res.latitude);
						console.log('经度：' + res.longitude);
					}
				});
			},
			//地图点击事件
			markertap(e) {
				console.log("===你点击了标记点===", e)
			},
			//地图点击事件
			callouttap(e) {
				console.log('地图点击事件', e)
			}
		}
	}
</script>

<style lang="scss" scoped>
	.content{
		width: 100%;
		height: 100vh;
		display: flex;
		flex-direction: column;
	}
</style>
