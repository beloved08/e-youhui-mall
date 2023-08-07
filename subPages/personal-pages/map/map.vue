<template>
	<view class="content">
		<view class="map">
			<map
				style="width: 100%; height: 100%;" 
				:layer-style="layerStyle"
				:enable-3D="enable3D" 
				:subkey="subkey"
				:enable-zoom="enableZoom"
				:enable-scroll="enableScroll"
				:enable-rotate="enableRotate"
				:enable-overlooking="enableOverlooking"
				:enable-satellite="enableSatellite"
				:show-compass="showCompass" 
				:enable-poi="enablePoi" 
				:longitude="longitude" 
				:latitude="latitude" 
				:scale="scale" 
				:enable-traffic="enableTraffic"
				:enable-building="enableBuilding"
				:enable-indoorMap="enableIndoorMap"
				:markers="markers"
				:include-points="includePoints"
				@markertap="markertap"
				@tap="selectPoint"
				@callouttap='callouttap'>
			</map>
		</view>
		<view class="item-box">
			<tui-button plain size="20" height="50rpx" width="100rpx" @click="showCompass = !showCompass">指南针</tui-button>
			<tui-button plain size="20" height="50rpx" width="100rpx" @click="enable3D = !enable3D">3D楼块</tui-button>
			<tui-button plain size="20" height="50rpx" width="100rpx" @click="enableOverlooking = !enableOverlooking">俯视</tui-button>
			<tui-button plain size="20" height="50rpx" width="120rpx" @click="enableSatellite = !enableSatellite">卫星图</tui-button>
			<tui-button plain size="20" height="50rpx" width="130rpx" @click="enableTraffic = !enableTraffic">实时路况</tui-button>
			<tui-button plain size="20" height="50rpx" width="100rpx" @click="enablePoi = !enablePoi">POI 点</tui-button>
			<tui-button plain size="20" height="50rpx" width="110rpx" @click="enableBuilding = !enableBuilding">建筑物</tui-button>
			<tui-button plain size="20" height="50rpx" width="130rpx" @click="enableIndoorMap = !enableIndoorMap">室内地图</tui-button>

			<tui-button plain size="20" height="50rpx" width="130rpx" @click="layerStyle = 1">白浅样式</tui-button>
			<tui-button plain size="20" height="50rpx" width="130rpx" @click="layerStyle = 2">墨渊样式</tui-button>
			<tui-button plain size="20" height="50rpx" width="130rpx" @click="layerStyle = 3">经典样式</tui-button>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			MapData: Array
		},
		data() {
			return {
				// 中心经度
				latitude: 24.9749, 
				// 中心纬度
				longitude: 102.7998,
				// 缩放级别 3-20
				scale: 12,
				// 个性化地图
				layerStyle: 3,
				// key
				subkey: 'YJXBZ-M5ECJ-CWZFI-FITCL-WGG4S-WFBRL',
				// 是否显示指南针
				showCompass: false,
				// 是否显示3D楼块
				enable3D: false,
				// 是否支持缩放，默认true
				enableZoom: true,
				// 是否支持拖动，默认true
				enableScroll: true,
				// 是否支持旋转，默认false
				enableRotate: false,
				// 是否开启俯视，默认false
				enableOverlooking: false,
				// 是否开启卫星图，默认false
				enableSatellite: false,
				// 是否开启实时路况，默认false
				enableTraffic: false,
				// 是否展示 POI 点，默认false
				enablePoi: true,
				// 是否展示建筑物，默认false
				enableBuilding: false,
				// 是否展示室内地图，默认false
				enableIndoorMap: false,
				// 缩放视野以包含所有给定的坐标点
				includePoints: [],
				// 标记点
				markers: []
			}
		},
		created() {
			this.latitude = this.MapData[0].businessDetailAddressLatitude
			this.longitude =  this.MapData[0].businessDetailAddressLongitude
			
			console.log(this.MapData)
			this.MapData.map(mp => {
				// this.includePoints.push({
				// 	latitude: mp.businessDetailAddressLatitude,
				// 	longitude: mp.businessDetailAddressLongitude,
				// 	iconPath: mp.businessLogo
				// })
				// this.includePoints.push({
				// 	latitude: mp.businessCityLatitude,
				// 	longitude: mp.businessCityLongitude,
				// 	iconPath: mp.businessLogo
				// })
				this.markers.push({
					id: mp.markerId,
					latitude: mp.businessDetailAddressLatitude,
					longitude: mp.businessDetailAddressLongitude,
					iconPath: mp.businessLogo,
					width: 30,
					height: 30,
					callout: {
						content: mp.calloutContent,
						color: '#fff',
						fontSize: 10,
						bgColor: '#577ee3',
						padding: 3,
						textAlign: 'center',
						borderWidth: 1,
						borderColor: '#fff',
						borderRadius: 3,
						display: 'ALWAYS'
					},
					label: {
						content: mp.businessDetailAddress,
						color: '#fff',
						fontSize: 10,
						bgColor: '#577ee3',
						padding: 3,
						textAlign: 'center',
						borderWidth: 1,
						borderColor: '#fff',
						borderRadius: 3,
					}
				})
			})
		},
		methods: {
			// 点击地图时触发; App-nvue、微信小程序2.9支持返回经纬度
			selectPoint(e){
				console.log(e)
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
	.content {
		width: 100%;
		height: 1000rpx;
		display: flex;
		flex-direction: column;
		
		.map{
			width: 100%;
			height: 80%;
		}
		
		.item-box{
			width: 100%;
			margin: 0 auto;
			margin-top: 20rpx;
			display: flex;
			flex-wrap: wrap;
			
			tui-button{
				margin: 5rpx;
			}
		}
	}
</style>
