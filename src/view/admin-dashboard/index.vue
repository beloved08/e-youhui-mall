<template>
    <div class="" style="background-color: rgb(245 247 249);">
        <!-- <div class="mt-2 ml-8 text-base font-bold">基本信息</div> -->
        <a-row justify="space-around" class="mt-6" v-if=" orderTotal ">
            <a-col class="h-24 cursor-pointer bg-white  hover:-translate-y-1" :span=" 5 ">
                <div class="flex flex-row justify-around">
                    <div class="w-16 h-16 mt-4 ">
                        <img src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/manage/sys-index/todal-order.png" alt="">
                    </div>
                    <div class="mt-4 mr-8 flex flex-col">
                        <div><a-statistic title="总订单数" :value=" orderTotal.totalOrderCount " /></div>
                    </div>
                </div>
            </a-col>
            <a-col class="h-24 cursor-pointer bg-white hover:-translate-y-1" :span=" 5 ">
                <div class=" flex flex-row justify-around">
                    <div class="w-16 h-16 mt-4">
                        <img src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/manage/sys-index/sales-volume.png" alt="">
                    </div>
                    <div class="mt-4 mr-8 flex flex-col">
                        <div><a-statistic title="总销售额" :value=" orderTotal.totalOrderPrice.toFixed(2) " /></div>
                    </div>
                </div>
            </a-col>
            <a-col class="h-24 cursor-pointer bg-white hover:-translate-y-1" :span=" 5 ">
                <div class=" flex flex-row justify-around">
                    <div class="w-16 h-16 mt-4">
                        <img src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/manage/sys-index/total-user.png" alt="">
                    </div>
                    <div class="mt-4 mr-8 flex flex-col">
                        <div><a-statistic title="总用户数" :value=" userTotal " /></div>
                    </div>
                </div>
            </a-col>
            <a-col class="h-24 cursor-pointer bg-white hover:-translate-y-1" :span=" 5 ">
                <div class=" flex flex-row justify-around">
                    <div class="w-16 h-16 mt-4">
                        <img src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/manage/sys-index/settle.png" alt="">
                    </div>
                    <div class="mt-4 mr-8 flex flex-col">
                        <div><a-statistic title="商家入驻总数" :value=" businessTotal " /></div>
                    </div>
                </div>
            </a-col>
        </a-row>
        <!-- <div class="mt-4 ml-8 text-base font-bold">快速导航</div> -->
        <a-row justify="space-around" class="mt-8">
            <a-col class="h-24 cursor-pointer" :span=" 5 ">
                <div class="flex flex-row justify-around  align-middle">
                    <router-link to="/ordinary">
                        <div class="w-40 h-24 bg-white flex mr-2 flex-col justify-center align-middle hover:-translate-y-1">
                            <div class="flex justify-center ">
                                <img class="w-8 h-8"
                                    src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/manage/sys-index/user.png" alt="">
                            </div>
                            <div class="flex justify-center text-black mt-2">用户管理</div>
                        </div>
                    </router-link>
                    <router-link to="/shop">
                        <div class="w-40 h-24 bg-white flex flex-col justify-center align-middle hover:-translate-y-1">
                            <div class="flex justify-center ">
                                <img class="w-10 h-10"
                                    src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/manage/sys-index/business.png" alt="">
                            </div>
                            <div class="flex justify-center text-black mt-2">商家店铺</div>
                        </div>
                    </router-link>
                </div>
            </a-col>
            <a-col class="h-24 cursor-pointer" :span=" 5 ">
                <div class="flex flex-row justify-around  align-middle">
                    <router-link to="/commodity-list">
                        <div
                            class="w-40 h-24 bg-white flex  mr-2 flex-col justify-center align-middle hover:-translate-y-1">
                            <div class="flex justify-center ">
                                <img class="w-10 h-10"
                                    src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/manage/sys-index/commodity.png"
                                    alt="">
                            </div>
                            <div class="flex justify-center text-black mt-2">商品管理</div>
                        </div>
                    </router-link>
                    <router-link to="/order-mall">
                        <div class="w-40 h-24 bg-white flex flex-col justify-center align-middle hover:-translate-y-1">
                            <div class="flex justify-center ">
                                <img class="w-10 h-10"
                                    src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/manage/sys-index/order.png" alt="">
                            </div>
                            <div class="flex justify-center text-black mt-2">订单管理</div>
                        </div>
                    </router-link>
                </div>
            </a-col>
            <a-col class="h-24 cursor-pointer" :span=" 5 ">
                <div class="flex flex-row justify-around  align-middle">
                    <router-link to="/order-logistics">
                        <div
                            class="w-40 h-24 bg-white flex  mr-2 flex-col justify-center align-middle hover:-translate-y-1">
                            <div class="flex justify-center ">
                                <img class="w-10 h-10"
                                    src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/manage/sys-index/logistics.png"
                                    alt="">
                            </div>
                            <div class="flex justify-center  text-black mt-2">订单物流</div>
                        </div>
                    </router-link>
                    <router-link to="/promotion-activity">
                        <div class="w-40 h-24 bg-white flex flex-col justify-center align-middle hover:-translate-y-1">
                            <div class="flex justify-center ">
                                <img class="w-10 h-10"
                                    src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/manage/sys-index/sale.png" alt="">
                            </div>
                            <div class="flex justify-center text-black mt-2">全民促销</div>
                        </div>
                    </router-link>
                </div>
            </a-col>
            <a-col class="h-24 cursor-pointer" :span=" 5 ">
                <div class="flex flex-row justify-around  align-middle">
                    <router-link to="/coupon-currency">
                        <div
                            class="w-40 h-24 bg-white flex  mr-2 flex-col justify-center align-middle hover:-translate-y-1">
                            <div class="flex justify-center ">
                                <img class="w-10 h-10"
                                    src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/manage/sys-index/coupon.png" alt="">
                            </div>
                            <div class="flex justify-center text-black mt-2">优惠券</div>
                        </div>
                    </router-link>
                    <router-link to="/data-chart">
                        <div class="w-40 h-24 bg-white flex flex-col justify-center align-middle hover:-translate-y-1">
                            <div class="flex justify-center ">
                                <img class="w-10 h-10"
                                    src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/manage/sys-index/datav.png" alt="">
                            </div>
                            <div class="flex justify-center text-black mt-2">数据可视化</div>
                        </div>
                    </router-link>
                </div>
            </a-col>
        </a-row>
        <div style="width: 99%;" class="mt-8 h-96 m-2 p-2 bg-white hover:-translate-y-1">
            <div style="height: 100%;width: 100%;" id="main"></div>
        </div>
        <div style="width: 100%;" class="mt-4 p-2 h-96  flex flex-row justify-around">
            <div style="width: 66%; height: 100%;" class="p-2 bg-white hover:-translate-y-1" id="echarts3"></div>
            <div style="width: 33%; height: 100%;" class="p-2 bg-white hover:-translate-y-1" id="echarts1"></div>
        </div>
    </div>
</template>

<script setup lang="ts">
import * as echarts from 'echarts'
import { ref } from 'vue'
import { getOrderCount, getOrderTrendsPastMonth, getOrderStatusSituation } from '@/api/pay/index'
import { getTotalUser } from '@/api/user/index'
import { getTotalBusiness } from '@/api/merchantStore/index'
import { getClassificationSituation } from '@/api/commodity/index'

const orderTotal = ref<any>()
const selectOrderCount = async () => {
    const res: any = await getOrderCount()
    if (res?.code === 0) {
        orderTotal.value = res?.data
    }
}
selectOrderCount()

const businessTotal = ref<any>()
const selectBusinessCount = async () => {
    const res: any = await getTotalBusiness()
    if (res?.code === 0) {
        businessTotal.value = res?.data
    }
}
selectBusinessCount()

const userTotal = ref<any>()
const selectUserCount = async () => {
    const res: any = await getTotalUser()
    if (res?.code === 0) {
        userTotal.value = res?.data
    }
}
selectUserCount()

const orderNumberX = ref<any>([])
const orderNumberY = ref<any>([])

const selectOrderTrendsPastMonth = async () => {
    const res: any = await getOrderTrendsPastMonth()
    if (res?.code === 0) {
        res?.data.map((r: any) => {
            orderNumberX.value.push(r.date)
            orderNumberY.value.push(r.count)
        })
        initEcharts(orderNumberX.value, orderNumberY.value)
    }
}
selectOrderTrendsPastMonth()

const classificationX = ref<any>([])
const classificationY = ref<any>([])

const selectClassificationSituation = async () => {
    const res: any = await getClassificationSituation()
    if (res?.code === 0) {
        res?.data.map((c: any) => {
            classificationX.value.push(c.name)
            classificationY.value.push(c.count)
        })
    }
    initEcharts3(classificationX.value, classificationY.value)
}

selectClassificationSituation()

const selectOrderStatusSituation = async () => {
    const res: any = await getOrderStatusSituation()
    if (res?.code === 0) {
        initEcharts2(res?.data)
    }
}

selectOrderStatusSituation()

const initEcharts = (x: any, y: any) => {

    var chartDom: any = document.getElementById('main')
    var myChart = echarts.init(chartDom)
    var option: any

    option = {
        title: {
            text: '近30天订单趋势'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['订单数']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: x
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: '该天订单数',
                type: 'line',
                stack: 'Total',
                smooth: true,
                areaStyle: {},
                data: y
            }
        ]
    }
    let currentIndex = -1

    setInterval(function () {
        var dataLen = option.series[0].data.length
        // 取消之前高亮的图形
        myChart.dispatchAction({
            type: 'downplay',
            seriesIndex: 0,
            dataIndex: currentIndex
        })
        currentIndex = (currentIndex + 1) % dataLen
        // 高亮当前图形
        myChart.dispatchAction({
            type: 'highlight',
            seriesIndex: 0,
            dataIndex: currentIndex
        })
        // 显示 tooltip
        myChart.dispatchAction({
            type: 'showTip',
            seriesIndex: 0,
            dataIndex: currentIndex
        })
    }, 2000)

    option && myChart.setOption(option)

    window.addEventListener('resize', () => { //监听浏览器大小去改变图表尺寸
        myChart.resize()
    })
}

const initEcharts2 = (orderData: any) => {
    var chartDom: any = document.getElementById('echarts1')!
    var myChart = echarts.init(chartDom)
    var option: any

    option = {
        title: {
            text: '订单状态数',
            left: 'center',
            top: 20,
            textStyle: {
                color: '#000'
            }
        },
        legend: {
            top: 'bottom'
        },
        tooltip: {
            trigger: 'item'
        },
        toolbox: {
            show: true,
            feature: {
                mark: { show: true },
                dataView: { show: true, readOnly: false },
                restore: { show: true },
                saveAsImage: { show: true }
            }
        },
        series: [
            {
                name: '订单状态',
                type: 'pie',
                radius: [20, 90],
                center: ['50%', '50%'],
                roseType: 'area',
                itemStyle: {
                    borderRadius: 8
                },
                data: orderData
            }
        ]
    }
    let currentIndex = -1

    setInterval(function () {
        var dataLen = option.series[0].data.length
        // 取消之前高亮的图形
        myChart.dispatchAction({
            type: 'downplay',
            seriesIndex: 0,
            dataIndex: currentIndex
        })
        currentIndex = (currentIndex + 1) % dataLen
        // 高亮当前图形
        myChart.dispatchAction({
            type: 'highlight',
            seriesIndex: 0,
            dataIndex: currentIndex
        })
        // 显示 tooltip
        myChart.dispatchAction({
            type: 'showTip',
            seriesIndex: 0,
            dataIndex: currentIndex
        })
    }, 2000)
    option && myChart.setOption(option)

    window.addEventListener('resize', () => { //监听浏览器大小去改变图表尺寸
        myChart.resize()
    })
}

const initEcharts3 = (x: any, y: any) => {
    var chartDom: any = document.getElementById('echarts3')!
    var myChart = echarts.init(chartDom)
    var option: any

    // prettier-ignore
    let dataAxis = x
    // prettier-ignore
    let data = y
    let yMax = 500
    let dataShadow = []

    for (let i = 0; i < data.length; i++) {
        dataShadow.push(yMax)
    }

    option = {
        tooltip: {
            trigger: 'axis',
        },
        title: {
            text: '商品分类情况'
        },
        xAxis: {
            data: dataAxis,
            axisLabel: {
                inside: true,
                color: '#fff'
            },
            axisTick: {
                show: false
            },
            axisLine: {
                show: false
            },
            z: 10
        },
        yAxis: {
            axisLine: {
                show: false
            },
            axisTick: {
                show: false
            },
            axisLabel: {
                color: '#999'
            }
        },
        dataZoom: [
            {
                type: 'inside'
            }
        ],
        series: [
            {
                name: '下级分类数',
                type: 'bar',
                showBackground: true,
                itemStyle: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                        { offset: 0, color: '#83bff6' },
                        { offset: 0.5, color: '#188df0' },
                        { offset: 1, color: '#188df0' }
                    ])
                },
                emphasis: {
                    itemStyle: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                            { offset: 0, color: '#2378f7' },
                            { offset: 0.7, color: '#2378f7' },
                            { offset: 1, color: '#83bff6' }
                        ])
                    }
                },
                data: data
            }
        ]
    }

    // Enable data zoom when user click bar.
    const zoomSize = 6
    myChart.on('click', function (params) {
        console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)])
        myChart.dispatchAction({
            type: 'dataZoom',
            startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
            endValue:
                dataAxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
        })
    })
    let currentIndex = -1

    setInterval(function () {
        var dataLen = option.series[0].data.length
        // 取消之前高亮的图形
        myChart.dispatchAction({
            type: 'downplay',
            seriesIndex: 0,
            dataIndex: currentIndex
        })
        currentIndex = (currentIndex + 1) % dataLen
        // 高亮当前图形
        myChart.dispatchAction({
            type: 'highlight',
            seriesIndex: 0,
            dataIndex: currentIndex
        })
        // 显示 tooltip
        myChart.dispatchAction({
            type: 'showTip',
            seriesIndex: 0,
            dataIndex: currentIndex
        })
    }, 2000)

    option && myChart.setOption(option)

    window.addEventListener('resize', () => { //监听浏览器大小去改变图表尺寸
        myChart.resize()
    })
}

</script>

<style scoped></style>