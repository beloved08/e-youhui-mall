<template>
    <baidu-map class="map w-full overflow-hidden"
        :center="{ lng: mapDataRef.mapData.longitude, lat: mapDataRef.mapData.latitude }" :scroll-wheel-zoom="true"
        :zoom="15">
        <!-- 在左下角加入比例尺控件 -->
        <bm-scale anchor="BMAP_ANCHOR_BOTTOM_LEFT"></bm-scale>
        <!-- 在右上角加入缩放控件 -->
        <!-- <bm-navigation anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-navigation> -->
        <!-- 在地图右上角加入地图类型控件 -->
        <bm-map-type :map-types="['BMAP_NORMAL_MAP', 'BMAP_HYBRID_MAP']" anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-map-type>
        <!-- 在地图右下角加入缩略图控件 -->
        <bm-overview-map anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :isOpen="true"></bm-overview-map>
        <!-- 在地图右下角加入定位控件 -->
        <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true" :autoLocation="true"></bm-geolocation>
        <!-- 在地图左上角加入城市切换控件 -->
        <bm-city-list anchor="BMAP_ANCHOR_TOP_LEFT"></bm-city-list>
        <bm-marker :position="{ lng: mapDataRef.mapData.longitude, lat: mapDataRef.mapData.latitude }"
            @click="infoWindowOpen" :icon="{ url: mapDataRef.mapData.iconPath, size: { width: 60, height: 60 } }"
            animation="BMAP_ANIMATION_BOUNCE">
            <bm-label :content="mapDataRef.mapData.calloutContent"
                :labelStyle="{ color: '#fff', fontSize: '13px', backgroundColor: '#108ee9', border: '#108ee9', padding: '5px', borderRadius: '3px' }"
                :offset="{ width: 5, height: 60 }" />
            <bm-info-window :show="show" @close="infoWindowClose" @open="infoWindowOpen">
                <div style="width: 700px;">
                    <a-descriptions>
                        <a-descriptions-item label="商家名称">{{ mapDataRef.businessData.businessName }}</a-descriptions-item>
                        <a-descriptions-item label="店铺名称">{{ mapDataRef.businessData.shopName }}</a-descriptions-item>
                        <a-descriptions-item label="商家电话">{{ mapDataRef.businessData.businessPhone }}</a-descriptions-item>
                        <a-descriptions-item label="所在城市">{{ mapDataRef.businessData.businessCity }}</a-descriptions-item>
                        <a-descriptions-item label="申请人">{{ mapDataRef.businessUserData.businessUserName
                        }}</a-descriptions-item>
                        <a-descriptions-item label="性别">{{ mapDataRef.businessUserData.businessUserSex
                        }}</a-descriptions-item>
                        <a-descriptions-item label="年龄">{{ mapDataRef.businessUserData.businessUserAge
                        }}</a-descriptions-item>
                        <a-descriptions-item label="电话号码">{{ mapDataRef.businessUserData.businessUserPhone
                        }}</a-descriptions-item>
                        <a-descriptions-item label="身份证号码">{{ mapDataRef.businessUserData.businessUserIdCard
                        }}</a-descriptions-item>
                        <a-descriptions-item label="邮箱">{{ mapDataRef.businessUserData.businessUserEmail
                        }}</a-descriptions-item>
                        <a-descriptions-item label="地址">{{ mapDataRef.businessData.businessDetailAddress
                        }}</a-descriptions-item>
                        <a-descriptions-item label="描述">{{ mapDataRef.businessData.businessDescribe
                        }}</a-descriptions-item>
                    </a-descriptions>
                </div>
            </bm-info-window>
            <!-- <bm-overlay pane="labelPane" :class="{ sample: true, active }" @draw="draw" @mouseover="active = true"
                @mouseleave="active = false">
                <div>{{ mapDataRef.mapData.calloutContent }}</div>
            </bm-overlay> -->
        </bm-marker>
    </baidu-map>
</template>

<script lang="ts">
export default {
    name: 'Map',
}
</script>

<script setup lang="ts">
import { defineProps, toRefs, ref } from 'vue'

const props = defineProps({
    mapDetailData: {}
})

const mapDataRef: any = toRefs(props).mapDetailData

// console.log(mapDataRef.value)

const show = ref(false)


const draw = ({ el, BMap, map }: any) => {
    const pixel = map.pointToOverlayPixel(new BMap.Point(mapDataRef.value.mapData.longitude, mapDataRef.value.mapData.latitude))
    el.style.left = pixel.x - 50 + 'px'
    el.style.top = pixel.y + 20 + 'px'
}

const infoWindowClose = () => {
    show.value = false
}
const infoWindowOpen = () => {
    if (mapDataRef.value.use === 1) {
        show.value = false
    } else if (mapDataRef.value.use === 0) {
        show.value = true
    }
}

</script>

<style scoped>
.map {
    height: 550px;
}

.sample {
    position: absolute;
    width: 200px;
    height: 40px;
    /* line-height: 40px; */
    background: rgba(0, 0, 0, 0.5);
    /* overflow: hidden; */
    box-shadow: 0 0 5px #000;
    color: #fff;
    text-align: center;
    padding: 10px;
}

.sample.active {
    background: rgba(0, 0, 0, 0.75);
    color: #fff;
}
</style>