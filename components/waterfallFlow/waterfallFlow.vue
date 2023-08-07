<template>
    <view class="Index">
        <!-- 瀑布流布局列表 -->
        <view class="pubuBox">
            <view class="pubuItem">
                <view class="item-masonry" v-for="(item, index) in commodityList" :key="index" @click="toCommodityDetail(item)">
                    <image :src="item.commodityImageUrl" mode="widthFix"></image>
                    <view class="listtitle">
                        <view class="listtitle1">{{ item.commodityName }}</view>
                        <view class="listtitle2">
                            <text class="listtitle2son">￥</text>
                            <text v-if="item.salesModel == 0">{{ item.retailPrice }}</text>
                            <text v-else>{{ item.wholesalePrice }}</text>
                        </view>
                    </view>
                </view>
            </view>
        </view>
    </view>
</template>
 
<script>
	import {
		mapState,
		mapGetters,
		mapActions
	} from 'vuex'
    export default {
		props: ['commodityList'],
        data() {
            return {}
        },
        onShow() {},
        onLoad() {
		},
        methods: {
			...mapActions(['saveCommodityInfoDetail']),
			toCommodityDetail(e){
				this.saveCommodityInfoDetail(e)
				uni.navigateTo({
					url:'subIndexPages/commodityDetail/commodityDetail?commodityId=' + e.commodityId
				})
			}
		},
    };
</script>
 
<style scoped lang="scss">
    //瀑布流
    page {
        background-color: #eee;
        height: 100%;
    }
 
    .pubuBox {
        padding: 22rpx;
		width: 95%;
    }
 
    .pubuItem {
        column-count: 2;
        column-gap: 20rpx;
    }
 
    .item-masonry {
        box-sizing: border-box;
        border-radius: 10rpx;
        overflow: hidden;
        background-color: #fff;
        break-inside: avoid;
        /*避免在元素内部插入分页符*/
        box-sizing: border-box;
        margin-bottom: 20rpx;
        box-shadow: 0px 0px 28rpx 1rpx rgba(78, 101, 153, 0.14);
    }
 
    .item-masonry image {
        width: 100%;
		height: 80%;
    }
 
    .listtitle {
        padding-left: 22rpx;
        font-size: 24rpx;
        padding-bottom: 22rpx;
 
        .listtitle1 {
            line-height: 39rpx;
            text-overflow: -o-ellipsis-lastline;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            line-clamp: 2;
            -webkit-box-orient: vertical;
            min-height: 39rpx;
            max-height: 78rpx;
        }
 
        .listtitle2 {
            color: #ff0000;
            font-size: 32rpx;
            line-height: 32rpx;
            font-weight: bold;
            padding-top: 22rpx;
 
            .listtitle2son {
                font-size: 32rpx;
            }
        }
 
        .listtitle3 {
            font-size: 28rpx;
            color: #909399;
            line-height: 32rpx;
            padding-top: 22rpx;
        }
    }
 
    .Index {
        width: 100%;
        height: 100%;
    }
</style>