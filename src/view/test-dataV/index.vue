<template>
    <dv-full-screen-container>
        <div class="bg-black w-full h-full">
            <div class="flex w-full h-full justify-center align-middle" v-if=" isLoading ">
                <div small-bg>
                    <dv-loading>
                        <div class="text-white">
                            Loading...
                        </div>
                    </dv-loading>
                </div>
            </div>
            <div class="w-full h-full bg-black flex justify-center" v-else>
                <div class="w-full flex">
                    <dv-border-box11 title="E优汇数据可视化平台" :title-width=" 300 ">
                        <div class="flex m-20">
                            <div class="flex w-full justify-between h-full">
                                <div style="width: 25%;" class="left p-2 flex bg-slate-500 justify-center">
                                    <div>
                                        <div>
                                            <div class="flex flex-row ">
                                                <dv-decoration6 style="width:120px;height:40px;" />
                                                <dv-decoration7 style="width:160px;height:30px;margin-top: 5px;">
                                                    <div color-white class="text-white" font-300>
                                                        &nbsp;&nbsp;&nbsp;热销商品列表&nbsp;&nbsp;&nbsp;
                                                    </div>
                                                </dv-decoration7>
                                                <dv-decoration6 style="width:120px;height:40px;" />
                                            </div>
                                            <div flex="~ col" class="mt-2" justify-center items-center bg-dark>
                                                <div>
                                                    <dv-scroll-board :config=" config6 " style="width:400px;height:900px" />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div style="width: 50%;">
                                    <div class="flex w-full justify-center align-middle">
                                        <div class="flex flex-col text-white">
                                            <div class="h-32 flex align-middle">
                                                <div class="w-full h-28 flex flex-row align-middle justify-around">
                                                    <div style="width: 30%;height: 100%;background-color: rgb(55, 121, 253);"
                                                        class="flex justify-center items-center">
                                                        <div class="w-4/5 h-4/5 flex flex-col">
                                                            <div
                                                                style="display: flex;align-items: center;color: rgb(255, 255, 255);font-weight: normal;font-family: 'Microsoft Yahei', Arial, 'sans-serif';font-size: 16px;">
                                                                今日销售额
                                                            </div>
                                                            <div class="mt-6">
                                                                <span v-if=" todayOrder.todayOrderPrice != null "
                                                                    style="font-family: DIN;font-size: 36px;font-weight: bold;">{{
                                                                        todayOrder.todayOrderPrice }}</span>
                                                                <span
                                                                    style=" font-family: 'Microsoft Yahei', Arial, 'sans-serif';font-size: 18px;margin-left: 12px;">元</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div style="width: 30%;height: 100%;"
                                                        class="flex justify-center items-center bg-yellow-400">
                                                        <div class="w-4/5 h-4/5 flex flex-col">
                                                            <div
                                                                style="display: flex;align-items: center;color: rgb(255, 255, 255);font-weight: normal;font-family: 'Microsoft Yahei', Arial, 'sans-serif';font-size: 16px;">
                                                                今日订单数
                                                            </div>
                                                            <div class="mt-6">
                                                                <span v-if=" todayOrder.todayOrderCount != null "
                                                                    style="font-family: DIN;font-size: 36px;font-weight: bold;">{{
                                                                        todayOrder.todayOrderCount }}</span>
                                                                <span
                                                                    style=" font-family: 'Microsoft Yahei', Arial, 'sans-serif';font-size: 18px;margin-left: 12px;">单</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div style="width: 30%;height: 100%;"
                                                        class="flex justify-center items-center bg-red-500">
                                                        <div class="w-4/5 h-4/5 flex flex-col">
                                                            <div
                                                                style="display: flex;align-items: center;color: rgb(255, 255, 255);font-weight: normal;font-family: 'Microsoft Yahei', Arial, 'sans-serif';font-size: 16px;">
                                                                今日会员数
                                                            </div>
                                                            <div class="mt-6">
                                                                <span v-if=" todayAddMember != null "
                                                                    style="font-family: DIN;font-size: 36px;font-weight: bold;">{{
                                                                        todayAddMember }}</span>
                                                                <span
                                                                    style=" font-family: 'Microsoft Yahei', Arial, 'sans-serif';font-size: 18px;margin-left: 12px;">个</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="h-32 flex align-middle">
                                                <div class="w-full h-28 flex flex-row align-middle justify-around">
                                                    <div style="width: 30%;height: 100%;background: rgb(36, 38, 45);"
                                                        class="flex justify-center items-center">
                                                        <div class="w-4/5 h-4/5 flex flex-col">
                                                            <div
                                                                style="display: flex;align-items: center;color: rgb(255, 255, 255);font-weight: normal;font-family: 'Microsoft Yahei', Arial, 'sans-serif';font-size: 16px;">
                                                                总销售额
                                                            </div>
                                                            <div class="mt-6">
                                                                <span v-if=" orderTotal != null "
                                                                    style="font-family: DIN;font-size: 36px;font-weight: bold;color: rgb(55, 121, 253);">{{
                                                                        orderTotal.totalOrderPrice.toFixed(2) }}</span>
                                                                <span
                                                                    style=" font-family: 'Microsoft Yahei', Arial, 'sans-serif';font-size: 18px;margin-left: 12px;">元</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div style="width: 30%;height: 100%;background: rgb(36, 38, 45);"
                                                        class="flex justify-center items-center">
                                                        <div class="w-4/5 h-4/5 flex flex-col">
                                                            <div
                                                                style="display: flex;align-items: center;color: rgb(255, 255, 255);font-weight: normal;font-family: 'Microsoft Yahei', Arial, 'sans-serif';font-size: 16px;">
                                                                总订单数
                                                            </div>
                                                            <div class="mt-6">
                                                                <span class="text-yellow-400" v-if=" orderTotal != null "
                                                                    style="font-family: DIN;font-size: 36px;font-weight: bold;">{{
                                                                        orderTotal.totalOrderCount }}</span>
                                                                <span
                                                                    style=" font-family: 'Microsoft Yahei', Arial, 'sans-serif';font-size: 18px;margin-left: 12px;">单</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div style="width: 30%;height: 100%;background: rgb(36, 38, 45);"
                                                        class="flex justify-center items-center">
                                                        <div class="w-4/5 h-4/5 flex flex-col">
                                                            <div
                                                                style="display: flex;align-items: center;color: rgb(255, 255, 255);font-weight: normal;font-family: 'Microsoft Yahei', Arial, 'sans-serif';font-size: 16px;">
                                                                总会员数
                                                            </div>
                                                            <div class="mt-6">
                                                                <span v-if=" totalMember != null "
                                                                    style="font-family: DIN;font-size: 36px;font-weight: bold;color: red;">{{
                                                                        totalMember }}</span>
                                                                <span
                                                                    style=" font-family: 'Microsoft Yahei', Arial, 'sans-serif';font-size: 18px;margin-left: 12px;">个</span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="m-auto flex flex-col w-full justify-center">
                                                <!-- <div class="text-white">商家分布情况</div> -->
                                                <div style="width: 850px;height:550px;" v-if=" points != null ">
                                                    <baidu-map style="width: 850px;height:550px;"
                                                        :center=" { lng: 116.404, lat: 39.915 } " :zoom=" 5 "
                                                        :mapStyle=" mapStyle " :scroll-wheel-zoom=" true ">
                                                        <bm-marker
                                                            v-for="(                                       item, index                                       ) in                                        points                                       "
                                                            :key=" index " @click=" infoWindowOpen(item) "
                                                            :position=" { lng: item.businessDetailAddressLongitude, lat: item.businessDetailAddressLatitude } "
                                                            :icon=" { url: item.businessLogo, size: { width: 30, height: 30 } } ">
                                                            <bm-label v-if=" item != null " :content=" item.calloutContent "
                                                                :labelStyle=" labelStyle "
                                                                :offset=" { width: 5, height: 30 } " />
                                                            <bm-boundary :name=" item.businessCity " :strokeWeight=" 1 "
                                                                fillColor="#00adb5" strokeColor="#00adb5"></bm-boundary>
                                                        </bm-marker>
                                                    </baidu-map>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div style="width: 25%;" class="right">
                                    <div>
                                        <div v-if=" config1.data.length > 0 ">
                                            <div class="flex flex-row justify-center">
                                                <dv-decoration1 style="width:120px;height:40px;" />
                                                <dv-decoration7 style="width:150px;height:30px;margin-top: 5px;">
                                                    <div class="text-white">
                                                        &nbsp;&nbsp;&nbsp;商家总收入&nbsp;&nbsp;&nbsp;
                                                    </div>
                                                </dv-decoration7>
                                                <dv-decoration1 style="width:120px;height:40px;" />
                                            </div>
                                            <div justify-center items-center bg-dark class="flex flex-row justify-center">
                                                <div class="flex flex-row justify-center">
                                                    <dv-capsule-chart :config=" config1 "
                                                        style="width: 400px;height:15rem" />
                                                </div>
                                            </div>
                                        </div>
                                        <div>
                                            <div class="flex justify-center flex-row mt-4">
                                                <dv-decoration1 style="width:120px;height:40px;" />
                                                <dv-decoration7 style="width:150px;height:30px;">
                                                    <div color-white class="text-white" font-300>
                                                        &nbsp;&nbsp;地区销量排行&nbsp;&nbsp;
                                                    </div>
                                                </dv-decoration7>
                                                <dv-decoration1 style="width:120px;height:40px;" />
                                            </div>
                                            <div class="mt-4 ml-4 flex justify-center" justify-center items-center bg-dark>
                                                <div>
                                                    <dv-scroll-ranking-board :config=" config5 "
                                                        style="width:400px;height:230px;" />
                                                </div>
                                            </div>
                                        </div>
                                        <div>
                                            <div class="flex justify-center flex-row mt-4">
                                                <dv-decoration1 style="width:120px;height:40px;" />
                                                <dv-decoration7 style="width:150px;height:30px;">
                                                    <div color-white class="text-white" font-300>
                                                        &nbsp;&nbsp;&nbsp;商品分类占比&nbsp;&nbsp;&nbsp;
                                                    </div>
                                                </dv-decoration7>
                                                <dv-decoration1 style="width:120px;height:40px;" />
                                            </div>
                                            <div class="flex justify-center">
                                                <div bg-dark>
                                                    <dv-active-ring-chart :config=" conf "
                                                        style="width:400px;height:300px;margin-top: -50px;" />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </dv-border-box11>
                </div>
            </div>
        </div>
    </dv-full-screen-container>
</template>

<script setup lang="ts">
import { BorderBox11 as DvBorderBox11 } from '@kjgl77/datav-vue3'
import { ref } from 'vue'
import mapImg from '@/assets/img/map.jpg'
import mapPoint from '@/assets/img/mapPoint.png'
import mapCenterPoint from '@/assets/img/mapCenterPoint.png'
import { mapStyle } from '@/utils/mapStyle'

import { getAllBusinessMap } from '@/api/merchantStore/index'
import { getOrderCount, getToadyOrderCount, getBusinessTotalIncome, getRegionalSalesRanking } from '@/api/pay/index'
import { getTotalMember, getTodayAddMember } from '@/api/user/index'
import { getCommodityDataVList, getClassificationCommodityProportion } from '@/api/commodity/index'

const labelStyle = ref<any>({ color: '#fff', fontSize: '10px', backgroundColor: '#108ee9', border: '#108ee9', padding: '3px', borderRadius: '2px' })

const orderTotal = ref<any>()
const selectOrderCount = async () => {
    const res: any = await getOrderCount()
    if (res?.code === 0) {
        orderTotal.value = res?.data
    }
}
selectOrderCount()

const todayOrder = ref<any>()
const selectTodayOrderCount = async () => {
    const res: any = await getToadyOrderCount()

    if (res?.code === 0) {
        todayOrder.value = res?.data
    }
}
selectTodayOrderCount()

const totalMember = ref<any>()
const selectTotalMember = async () => {
    const res: any = await getTotalMember()
    if (res?.code === 0) {
        totalMember.value = res?.data
    }
}
selectTotalMember()

const todayAddMember = ref<any>()
const selectTodayAddMember = async () => {
    const res: any = await getTodayAddMember()
    if (res?.code === 0) {
        todayAddMember.value = res?.data
    }
}
selectTodayAddMember()

const isLoading = ref<boolean>(true)

const points = ref<any>([])
const addPoints = async () => {
    const res: any = await getAllBusinessMap()
    if (res?.code === 0) {
        points.value = res?.data
    }
}

addPoints()

const show = ref(true)

const infoWindowOpen = (e: any) => {
    show.value = true
    console.log(e)
}

const config1 = ref<any>({
    data: [],
    colors: ['#e062ae', '#fb7293', '#e690d1', '#32c5e9', '#96bfff'],
    showValue: true,
    unit: '元'
})
const selectBusinessTotalIncome = async () => {
    const res: any = await getBusinessTotalIncome()
    if (res?.code === 0) {
        config1.value.data = res?.data
    }
}
selectBusinessTotalIncome()

const config5 = ref<any>({
    data: [],
    unit: '件',
})

const selectRegionalSalesRanking = async () => {
    const res: any = await getRegionalSalesRanking()
    if (res?.code === 0) {
        config5.value.data = res?.data
    }
}

selectRegionalSalesRanking()

const config6 = ref<any>({
    header: ['商品', '类别', '库存', '价格/元', '商家'],
    data: [],
    carousel: 'single',
    rowNum: 20,
    index: true,
    headerBGC: 'rgb(0 0 0 0.1)',
    oddRowBGC: 'rgb(0 0 0 0.1)',
    evenRowBGC: 'rgb(0 0 0 0.1)',
    columnWidth: [50],
    align: ['center'],
})

const selectCommodityDataVList = async () => {
    const res: any = await getCommodityDataVList()
    if (res?.code === 0) {
        config6.value.data = res?.data
        isLoading.value = false
    }
}

selectCommodityDataVList()

const conf = ref({
    lineWidth: 24,
    digitalFlopStyle: {
        fill: 'pink',
    },
    data: [],
})

const selectClassificationCommodityProportion = async () => {
    const res: any = await getClassificationCommodityProportion()
    if (res?.code === 0) {
        conf.value.data = res?.data
    }
}

selectClassificationCommodityProportion()

</script>

<style scoped>
.content {
    font-size: 28px;
    text-shadow: 0 0 3px #7acaec;
}

.main {
    background-image: url('@/assets/img/earth1.gif');
    background-position: center;
    background-size: 80%;
    background-repeat: no-repeat;
    background-size: cover;
}

.top {
    background-image: url('@/assets/img/bg.png');
    background-size: cover;
}

.left {
    background-image: url('@/assets/img/bg.png');
    background-position: center top;
    background-size: cover;
}

.right {
    background-image: url('@/assets/img/bg.png');
    background-position: center top;
    background-size: cover;
}
</style>