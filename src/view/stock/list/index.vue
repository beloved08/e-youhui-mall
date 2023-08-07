<template>
    <a-card title="搜索区域">
        <div>
            <a-form layout="inline" :model="commodityStockPage">
                <a-form-item class="w-48" label="订单号">
                    <a-input v-model:value="commodityStockPage.orderNumber">
                    </a-input>
                </a-form-item>
                <a-form-item class="w-48" label="变动类型">
                    <a-select v-model:value="commodityStockPage.type">
                        <a-select-option value="-1">全部</a-select-option>
                        <a-select-option value="0">入库</a-select-option>
                        <a-select-option value="1">出库</a-select-option>
                    </a-select>
                </a-form-item>
                <a-form-item label="时间范围" name="timeFrame" style="margin-bottom:20px;">
                    <t-date-range-picker v-model="timeFrame" :presets="presets" enable-time-picker />
                </a-form-item>
                <a-form-item>
                    <a-space>
                        <a-button type="primary" @click="selectSearchCommodityStock">
                            查询
                        </a-button>
                        <a-button type="primary" @click="resetSelectSearchCommodityStock">
                            重置
                        </a-button>
                    </a-space>
                </a-form-item>
            </a-form>
        </div>
    </a-card>
    <a-card title="商品库存变动信息" :bordered="false">
        <div>
            <a-table :bordered="true" :columns="columns" :data-source="commodityStockDataList" :indentSize="50"
                :pagination="false" :loading="tableLoading">
                <template #bodyCell="{ column, record }">
                    <template v-if="column.key === 'type'">
                        <div v-if="record.type === 0">
                            <a-tag color="#87d068">入库</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="#f50">出库</a-tag>
                        </div>
                    </template>
                    <template v-if="column.key === 'action'">
                        <a-button type="link" size="small" @click="openCommodityStockDetail(record)">详情</a-button>
                        <a-popconfirm title="您确定要删除吗?" ok-text="确定" @confirm="deleteUser(record)" cancel-text="取消">
                            <template #icon><question-circle-outlined style="color: red" /></template>
                            <a-button type="link" danger size="small">删除</a-button>
                        </a-popconfirm>
                    </template>
                </template>
            </a-table>
        </div>
        <!-- 分页 -->
        <div class="mt-8 mb-4 flex justify-end">
            <a-pagination size="small" v-model:current="commodityStockPage.currentPage" :page-size-options="pageSizeOptions"
                :hideOnSinglePage="false" :show-total="(pageTotal: number) => `共 ${ pageTotal } 条 `" show-size-changer
                @showSizeChange="onShowSizeChange" @change="onShowSizeChange"
                v-model:page-size="commodityStockPage.pageSize" :total="pageTotal">
                <template #itemRender="{ type, originalElement }">
                    <a class="mr-3 ml-3" v-if="type === 'prev'">上一页</a>
                    <a class="ml-3 mr-3" v-else-if="type === 'next'">下一页</a>
                    <component :is="originalElement" v-else></component>
                </template>
                <template #buildOptionText="props">
                    <span class="text-xs">{{ props.value }}条/页</span>
                </template>
            </a-pagination>
        </div>
    </a-card>
    <!-- 出库详情 -->
    <div>
        <a-modal v-model:visible="commodityStockDetailVisible" title="出库记录详情" :footer="false" :width="1400"
            :destroyOnClose="true">
            <div>
                <div>
                    <a-descriptions :column="5" title="订单详情" bordered>
                        <a-descriptions-item label="订单号">{{ commodityStockDetailData.order.orderNumber
                        }}</a-descriptions-item>
                        <a-descriptions-item label="创建时间">{{ commodityStockDetailData.order.createTime
                        }}</a-descriptions-item>
                        <a-descriptions-item label="付款时间">{{ commodityStockDetailData.order.strikeBargainTime
                        }}</a-descriptions-item>
                        <a-descriptions-item label="订单状态">
                            <a-tag v-if="commodityStockDetailData.order.orderStatus === 0" color="#f50">待付款</a-tag>
                            <a-tag v-if="commodityStockDetailData.order.orderStatus === 1" color="#2db7f5">待发货</a-tag>
                            <a-tag v-if="commodityStockDetailData.order.orderStatus === 2" color="#87d068">待收货</a-tag>
                            <a-tag v-if="commodityStockDetailData.order.orderStatus === 3" color="#108ee9">已完成</a-tag>
                            <a-tag v-if="commodityStockDetailData.order.orderStatus === 4" color="#3fc1c9">售后</a-tag>
                        </a-descriptions-item>
                        <a-descriptions-item label="会员折扣">{{ commodityStockDetailData.order.memberDiscount
                        }} 折</a-descriptions-item>
                        <a-descriptions-item label="采购量">{{ commodityStockDetailData.order.purchaseQuantity
                        }}</a-descriptions-item>
                        <a-descriptions-item label="总价">{{ commodityStockDetailData.order.totalPrice
                        }} 元</a-descriptions-item>
                        <a-descriptions-item label="运费">{{ commodityStockDetailData.order.transportationExpenses
                        }} 元</a-descriptions-item>
                        <a-descriptions-item label="订单备注">{{ commodityStockDetailData.order.orderRemarks
                        }}</a-descriptions-item>
                    </a-descriptions>
                </div>
                <div class="mt-6">
                    <a-descriptions :column="5" size="small" bordered title="商品详情">
                        <a-descriptions-item label="商品名称">
                            {{ commodityStockDetailData.commodity.commodityName }}
                        </a-descriptions-item>
                        <a-descriptions-item label="商品状态">
                            <div v-if="commodityStockDetailData.commodity.commodityStatus === 0">
                                <a-tag color="pink">销售中</a-tag>
                            </div>
                            <div v-else>
                                <a-tag color="cyan">仓库中</a-tag>
                            </div>
                        </a-descriptions-item>
                        <a-descriptions-item label="商品库存">
                            <div v-if="Number(commodityStockDetailData.commodity.commodityStock) <= 10">
                                <a-tooltip placement="top" color="red">
                                    <template #title>
                                        <span>库存警告，即将无货，请即刻进货</span>
                                    </template>
                                    <a-tag color="#f50">
                                        {{ commodityStockDetailData.commodity.commodityStock }}
                                    </a-tag>
                                </a-tooltip>
                            </div>
                            <div
                                v-else-if="Number(commodityStockDetailData.commodity.commodityStock) >= 10 && Number(commodityStockDetailData.commodity.commodityStock) <= 100">
                                <a-tooltip placement="top" color="purple">
                                    <template #title>
                                        <span>库存预警，请注意进货</span>
                                    </template>
                                    <a-tag color="orange">
                                        {{ commodityStockDetailData.commodity.commodityStock }}
                                    </a-tag>
                                </a-tooltip>
                            </div>
                            <div v-else>
                                <a-tooltip placement="top" color="blue">
                                    <template #title>
                                        <span>库存非常充裕，无需进货</span>
                                    </template>
                                    <a-tag color="success">
                                        {{ commodityStockDetailData.commodity.commodityStock }}
                                    </a-tag>
                                </a-tooltip>
                            </div>
                        </a-descriptions-item>
                        <a-descriptions-item label="商品类型">
                            <div v-if="commodityStockDetailData.commodity.commodityStatus === 0">
                                <a-tag color="green">虚拟商品</a-tag>
                            </div>
                            <div v-else>
                                <a-tag color="purple">实物商品</a-tag>
                            </div>
                        </a-descriptions-item>
                        <a-descriptions-item label="销售模式">
                            <div v-if="commodityStockDetailData.commodity.salesModel === 0">
                                <a-tag color="orange">零售</a-tag>
                            </div>
                            <div v-else>
                                <a-tag color="blue">批发</a-tag>
                            </div>
                        </a-descriptions-item>
                        <a-descriptions-item label="零售价/元">
                            <a-tag color="#2db7f5">{{ commodityStockDetailData.commodity.retailPrice }}</a-tag>
                        </a-descriptions-item>
                        <a-descriptions-item label="批发价/元">
                            <a-tag color="#87d068">{{ commodityStockDetailData.commodity.wholesalePrice }}</a-tag>
                        </a-descriptions-item>
                        <a-descriptions-item label="商品重量/kg">
                            <a-tag color="#2db7f5">{{ commodityStockDetailData.commodity.commodityWeight }}</a-tag>
                        </a-descriptions-item>
                        <a-descriptions-item label="计量单位">
                            <a-tag color="#87d068">{{ commodityStockDetailData.commodity.meterCompany }}</a-tag>
                        </a-descriptions-item>
                        <a-descriptions-item label="是否推荐为新品">
                            <div v-if="commodityStockDetailData.commodity.commodityRecommend === 0">
                                <a-tag color="#87d068">推荐</a-tag>
                            </div>
                            <div v-else>
                                <a-tag color="#108ee9">不推荐</a-tag>
                            </div>
                        </a-descriptions-item>
                        <a-descriptions-item label="添加时间">
                            <a-tag color="blue">{{ commodityStockDetailData.commodity.createTime }}</a-tag>
                        </a-descriptions-item>
                        <a-descriptions-item label="上架时间">
                            <a-tag color="blue">{{ commodityStockDetailData.commodity.putShelfTime }}</a-tag>
                        </a-descriptions-item>
                        <a-descriptions-item label="下架时间">
                            <a-tag color="blue">{{ commodityStockDetailData.commodity.offShelfTime }}</a-tag>
                        </a-descriptions-item>
                        <a-descriptions-item label="下架原因">{{ commodityStockDetailData.commodity.offShelfReason
                        }}</a-descriptions-item>
                        <a-descriptions-item label="商品图片">
                            <div v-if="commodityStockDetailData.commodity.commodityImageUrl">
                                <a-image style="height: 50px; width: 70px;"
                                    :src="commodityStockDetailData.commodity.commodityImageUrl" />
                            </div>
                            <div v-else>
                                暂无
                            </div>
                        </a-descriptions-item>
                        <a-descriptions-item label="商品描述">{{ commodityStockDetailData.commodity.commodityDescribe
                        }}</a-descriptions-item>
                    </a-descriptions>
                </div>
            </div>
        </a-modal>
    </div>
    <!-- 入库详情 -->
    <div>
        <a-modal v-model:visible="commodityStockDetailVisibleIn" title="入库记录详情" :footer="false" :width="1150"
            :destroyOnClose="true">
            <div>
                <a-descriptions :column="3" size="small" bordered title="商品详情">
                    <a-descriptions-item label="商品名称">
                        {{ commodityStockInDetailData.commodity.commodityName }}
                    </a-descriptions-item>
                    <a-descriptions-item label="所属商家">
                        {{ commodityStockInDetailData.business.calloutContent }}
                    </a-descriptions-item>
                    <a-descriptions-item label="商品状态">
                        <div v-if="commodityStockInDetailData.commodity.commodityStatus === 0">
                            <a-tag color="pink">销售中</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="cyan">仓库中</a-tag>
                        </div>
                    </a-descriptions-item>
                    <a-descriptions-item label="商品库存">
                        <div v-if="Number(commodityStockInDetailData.commodity.commodityStock) <= 10">
                            <a-tooltip placement="top" color="red">
                                <template #title>
                                    <span>库存警告，即将无货，请即刻进货</span>
                                </template>
                                <a-tag color="#f50">
                                    {{ commodityStockInDetailData.commodity.commodityStock }}
                                </a-tag>
                            </a-tooltip>
                        </div>
                        <div
                            v-else-if="Number(commodityStockInDetailData.commodity.commodityStock) >= 10 && Number(commodityStockInDetailData.commodity.commodityStock) <= 100">
                            <a-tooltip placement="top" color="purple">
                                <template #title>
                                    <span>库存预警，请注意进货</span>
                                </template>
                                <a-tag color="orange">
                                    {{ commodityStockInDetailData.commodity.commodityStock }}
                                </a-tag>
                            </a-tooltip>
                        </div>
                        <div v-else>
                            <a-tooltip placement="top" color="blue">
                                <template #title>
                                    <span>库存非常充裕，无需进货</span>
                                </template>
                                <a-tag color="success">
                                    {{ commodityStockInDetailData.commodity.commodityStock }}
                                </a-tag>
                            </a-tooltip>
                        </div>
                    </a-descriptions-item>
                    <a-descriptions-item label="商品类型">
                        <div v-if="commodityStockInDetailData.commodity.commodityStatus === 0">
                            <a-tag color="green">虚拟商品</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="purple">实物商品</a-tag>
                        </div>
                    </a-descriptions-item>
                    <a-descriptions-item label="销售模式">
                        <div v-if="commodityStockInDetailData.commodity.salesModel === 0">
                            <a-tag color="orange">零售</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="blue">批发</a-tag>
                        </div>
                    </a-descriptions-item>
                    <a-descriptions-item label="零售价/元">
                        <a-tag color="#2db7f5">{{ commodityStockInDetailData.commodity.retailPrice }}</a-tag>
                    </a-descriptions-item>
                    <a-descriptions-item label="批发价/元">
                        <a-tag color="#87d068">{{ commodityStockInDetailData.commodity.wholesalePrice }}</a-tag>
                    </a-descriptions-item>
                    <a-descriptions-item label="商品重量/kg">
                        <a-tag color="#2db7f5">{{ commodityStockInDetailData.commodity.commodityWeight }}</a-tag>
                    </a-descriptions-item>
                    <a-descriptions-item label="计量单位">
                        <a-tag color="#87d068">{{ commodityStockInDetailData.commodity.meterCompany }}</a-tag>
                    </a-descriptions-item>
                    <a-descriptions-item label="是否推荐为新品">
                        <div v-if="commodityStockInDetailData.commodity.commodityRecommend === 0">
                            <a-tag color="#87d068">推荐</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="#108ee9">不推荐</a-tag>
                        </div>
                    </a-descriptions-item>
                    <a-descriptions-item label="添加时间">
                        <a-tag color="blue">{{ commodityStockInDetailData.commodity.createTime }}</a-tag>
                    </a-descriptions-item>
                    <a-descriptions-item label="上架时间">
                        <a-tag color="blue">{{ commodityStockInDetailData.commodity.putShelfTime }}</a-tag>
                    </a-descriptions-item>
                    <a-descriptions-item label="下架时间">
                        <a-tag color="blue">{{ commodityStockInDetailData.commodity.offShelfTime }}</a-tag>
                    </a-descriptions-item>
                    <a-descriptions-item label="下架原因">{{ commodityStockInDetailData.commodity.offShelfReason
                    }}</a-descriptions-item>
                    <a-descriptions-item label="商品图片">
                        <div v-if="commodityStockInDetailData.commodity.commodityImageUrl">
                            <a-image style="height: 50px; width: 70px;"
                                :src="commodityStockInDetailData.commodity.commodityImageUrl" />
                        </div>
                        <div v-else>
                            暂无
                        </div>
                    </a-descriptions-item>
                    <a-descriptions-item label="商品描述">{{ commodityStockInDetailData.commodity.commodityDescribe
                    }}</a-descriptions-item>
                </a-descriptions>
            </div>
        </a-modal>
    </div>
    <!-- 删除用户 -->
    <div>
        <a-modal v-model:visible="deleteVisible" :centered="true" :closable="false" :maskClosable="false" :keyboard="false"
            :footer="false">
            <div>
                <a-result status="500" title="错误" :sub-title="deleteUserTitle">
                    <template #extra>
                        <a-button type="primary" @click="deleteVisible = false">关闭</a-button>
                    </template>
                </a-result>
            </div>
        </a-modal>
    </div>
</template>

<script lang="ts" setup>
import { QuestionCircleOutlined } from '@ant-design/icons-vue'
import { ref } from 'vue'
import { getCommodityStockPageList, getCommodityStockDetail, getCommodityDetail } from '@/api/commodity/index'
import { message } from 'ant-design-vue'
import dayjs from 'dayjs'

const presets = ref({
    最近7天: [dayjs().subtract(6, 'day'), dayjs()],
    最近3天: [dayjs().subtract(2, 'day'), dayjs()],
    今天: [dayjs(), dayjs()],
})

// 控制表格加载动画开关
const loading = ref<boolean>(true)
// 表格加载动画自定义设置
const tableLoading = ref<any>({
    size: 'large',
    delay: 100,
    spinning: loading
})

const timeFrame = ref<any>([])
const commodityStockPage = ref<any>({
    "currentPage": 1,
    "pageSize": 5,
    "orderNumber": '',
    "startTime": '',
    "endTime": '',
    "type": -1
})

const pageTotal = ref<number>()
const pageSizeOptions = ref<string[]>(['5', '10', '20', '50', '100'])

const commodityStockDataList = ref<any>()

const selectCommodityStockList = async () => {
    const res: any = await getCommodityStockPageList(commodityStockPage.value)

    if (res?.code === 0) {
        commodityStockDataList.value = res?.data?.commodityStockDtoList
        pageTotal.value = res?.data?.total
        message.success('信息获取成功')
        loading.value = false
    } else {
        message.error("数据加载错误，请刷新重新加载")
    }
}

selectCommodityStockList()

// 条件搜索
const selectSearchCommodityStock = () => {
    if (timeFrame.value.length > 0) {
        commodityStockPage.value.startTime = timeFrame.value[0]
        commodityStockPage.value.endTime = timeFrame.value[1]
    }

    selectCommodityStockList()
}

// 重置
const resetSelectSearchCommodityStock = () => {
    commodityStockPage.value.orderNumber = ''
    commodityStockPage.value.type = -1
    commodityStockPage.value.startTime = ''
    commodityStockPage.value.endTime = ''
    timeFrame.value = []
    selectCommodityStockList()
}

// 分页
const onShowSizeChange = (current: number, pageSize: number) => {
    commodityStockPage.value.currentPage = current
    commodityStockPage.value.pageSize = pageSize
    selectCommodityStockList()
}

const columns = [
    {
        title: '商品名称',
        dataIndex: 'commodityName',
        key: 'commodityName',
        align: 'center'
    }, {
        title: '订单号',
        dataIndex: 'orderNumber',
        key: 'orderNumber',
        align: 'center'
    },
    {
        title: '变动类型',
        dataIndex: 'type',
        key: 'type',
        align: 'center'
    },
    {
        title: '变动数量',
        dataIndex: 'quota',
        key: 'quota',
        align: 'center'
    },
    {
        title: '变动时间',
        dataIndex: 'changeTime',
        key: 'changeTime',
        align: 'center'
    },
    {
        title: '操作',
        dataIndex: 'action',
        key: 'action',
        align: 'center'
    }
]

// 详情
const commodityStockDetailVisible = ref<boolean>(false)
const commodityStockDetailVisibleIn = ref<boolean>(false)
// 详情数据
const commodityStockDetailData = ref<any>()
const commodityStockInDetailData = ref<any>()
// 打开详情弹窗
const openCommodityStockDetail = async (e: any) => {
    if (e.type === 1) {
        const res: any = await getCommodityStockDetail(e.orderNumber)
        if (res?.code === 0) {
            message.success('信息获取成功')
            res?.data.map((d: any) => {
                if (d.commodity.commodityId === e.commodityId) {
                    commodityStockDetailData.value = d
                }
            })
            commodityStockDetailVisible.value = true
        } else {
            message.error(res?.msg)
        }
    } else {
        const res: any = await getCommodityDetail(e.commodityId)
        if (res?.code === 0) {
            message.success('信息获取成功')
            commodityStockInDetailData.value = res?.data
            commodityStockDetailVisibleIn.value = true
        } else {
            message.error(res?.msg)
        }
    }
}

const deleteVisible = ref<boolean>(false)
const deleteUserTitle = ref<string>('')
// 删除
const deleteUser = (e: any) => {
    deleteUserTitle.value = '不允许删除该商品库存变动明细'
    deleteVisible.value = true
    message.error("不允许删除该商品库存变动明细")
}

</script>

<style scoped></style>