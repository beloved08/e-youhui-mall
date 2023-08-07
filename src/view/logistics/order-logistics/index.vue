<template>
    <a-card title="搜索区域">
        <div class="pl-8 pr-8">
            <a-row :gutter="16">
                <a-col :span="6">
                    <div class="gutter-box">
                        <a-form-item label="订单编号">
                            <a-input v-model:value="expressOrderPage.orderNumber">
                            </a-input>
                        </a-form-item>
                    </div>
                </a-col>
                <a-col :span="6">
                    <div class="gutter-box">
                        <a-form-item label="发货者姓名">
                            <a-input v-model:value="expressOrderPage.senderName">
                            </a-input>
                        </a-form-item>
                    </div>
                </a-col>
                <a-col :span="6">
                    <div class="gutter-box">
                        <a-form-item label="发货者号码">
                            <a-input v-model:value="expressOrderPage.senderPhone">
                            </a-input>
                        </a-form-item>
                    </div>
                </a-col>
                <a-col :span="6">
                    <div class="gutter-box">
                        <a-form-item label="发货地址">
                            <a-input v-model:value="expressOrderPage.senderAddress">
                            </a-input>
                        </a-form-item>
                    </div>
                </a-col>
            </a-row>
            <a-row :gutter="16">
                <a-col :span="6">
                    <div class="gutter-box">
                        <a-form-item label="收件人姓名">
                            <a-input v-model:value="expressOrderPage.receiverName">
                            </a-input>
                        </a-form-item>
                    </div>
                </a-col>
                <a-col :span="6">
                    <div class="gutter-box">
                        <a-form-item label="收件人号码">
                            <a-input v-model:value="expressOrderPage.receiverPhone">
                            </a-input>
                        </a-form-item>
                    </div>
                </a-col>
                <a-col :span="6">
                    <div class="gutter-box">
                        <a-form-item class="mt-2" label="收件地址">
                            <a-input v-model:value="expressOrderPage.receiverAddress">
                            </a-input>
                        </a-form-item>
                    </div>
                </a-col>
                <a-col :span="6">
                    <div class="gutter-box">
                        <a-form-item>
                            <a-space>
                                <a-button type="primary" @click="selectSearchMallUser">
                                    查询
                                </a-button>
                                <a-button type="primary" @click="resetSelectSearchMallUser">
                                    重置
                                </a-button>
                            </a-space>
                        </a-form-item>
                    </div>
                </a-col>
            </a-row>
        </div>
    </a-card>
    <a-card title="快递订单物流信息" :bordered="false">
        <div>
            <a-table :columns="columns" :data-source="expressOrderDataList" :indentSize="50" :pagination="false"
                :loading="tableLoading">
                <template #bodyCell="{ column, record }">
                    <template v-if="column.key === 'action'">
                        <a-button type="link" size="small" @click="openDetail(record)">详情</a-button>
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
            <a-pagination size="small" v-model:current="expressOrderPage.currentPage" :page-size-options="pageSizeOptions"
                :hideOnSinglePage="false" :show-total="(pageTotal: number) => `共 ${ pageTotal } 条 `" show-size-changer
                @showSizeChange="onShowSizeChange" @change="onShowSizeChange" v-model:page-size="expressOrderPage.pageSize"
                :total="pageTotal">
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
    <!-- 用户详情 -->
    <div>
        <a-modal v-model:visible="logisticsDetailVisible" :footer="false" :destroyOnClose="true" :width="1200">
            <div>
                <a-descriptions title="快递物流订单" bordered>
                    <a-descriptions-item label="订单号">{{ logisticsDetailData.expressOrder.orderNumber
                    }}</a-descriptions-item>
                    <a-descriptions-item label="创建时间">{{ logisticsDetailData.expressOrder.createTime
                    }}</a-descriptions-item>
                    <a-descriptions-item label="物流公司">{{ logisticsDetailData.expressOrder.logisticsCompanyName
                    }}</a-descriptions-item>
                    <a-descriptions-item label="发货者姓名">{{ logisticsDetailData.expressOrder.senderName
                    }}</a-descriptions-item>
                    <a-descriptions-item label="发货者号码">{{ logisticsDetailData.expressOrder.senderPhone
                    }}</a-descriptions-item>
                    <a-descriptions-item label="发货地">{{ logisticsDetailData.expressOrder.senderAddress
                    }}</a-descriptions-item>
                    <a-descriptions-item label="收件人姓名">{{ logisticsDetailData.expressOrder.receiverName
                    }}</a-descriptions-item>
                    <a-descriptions-item label="收件人号码">{{ logisticsDetailData.expressOrder.receiverPhone
                    }}</a-descriptions-item>
                    <a-descriptions-item label="收件地址">{{ logisticsDetailData.expressOrder.receiverAddress
                    }}</a-descriptions-item>
                </a-descriptions>
            </div>
            <div class="mt-2">
                <a-card title="物流轨迹" :bordered="false">
                    <div class="mt-2 flex justify-center">
                        <div>
                            <a-steps size="small" direction="vertical"
                                :current="logisticsDetailData.logisticsTrack.length - 1">
                                <a-step v-for="(item, index) in logisticsDetailData.logisticsTrack" :key="index">
                                    <template #title>
                                        <span class="text-red-500 font-bold">{{ item.logisticsStatus.split('-')[0]
                                        }}</span>
                                    </template>
                                    <template #description>
                                        <div class="flex flex-col text-xs">
                                            <span>{{ item.updateTime }}</span>
                                            <span>{{ item.logisticsDesc }}</span>
                                        </div>
                                    </template>
                                </a-step>
                            </a-steps>
                        </div>
                    </div>
                </a-card>
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
import { getExpressOrderPage, getLogisticsTrackDetail } from '@/api/logistics/index'
import { message } from 'ant-design-vue'

// 控制表格加载动画开关
const loading = ref<boolean>(true)
// 表格加载动画自定义设置
const tableLoading = ref<any>({
    size: 'large',
    delay: 100,
    spinning: loading
})

const expressOrderPage = ref<any>({
    "currentPage": 1,
    "pageSize": 10,
    "orderNumber": '',
    "senderName": '',
    "senderPhone": '',
    "receiverName": '',
    "receiverPhone": '',
    "receiverAddress": '',
    "senderAddress": '',
})

const pageTotal = ref<number>()
const pageSizeOptions = ref<string[]>(['5', '10', '20', '50', '100'])
const expressOrderDataList = ref<any>()

const selectExpressOrderPage = async () => {
    const res: any = await getExpressOrderPage(expressOrderPage.value)
    if (res?.code === 0) {
        expressOrderDataList.value = res?.data.expressOrderDtoList
        pageTotal.value = res?.data?.total
        message.success('信息获取成功')
        loading.value = false
    } else {
        message.error("数据加载错误，请刷新重新加载")
    }
}

selectExpressOrderPage()

// 条件搜索
const selectSearchMallUser = () => {
    selectExpressOrderPage()
}

// 重置
const resetSelectSearchMallUser = () => {
    expressOrderPage.value.orderNumber = ''
    expressOrderPage.value.senderName = ''
    expressOrderPage.value.senderPhone = ''
    expressOrderPage.value.senderAddress = ''
    expressOrderPage.value.receiverName = ''
    expressOrderPage.value.receiverPhone = ''
    expressOrderPage.value.receiverAddress = ''
    selectExpressOrderPage()
}

// 分页
const onShowSizeChange = (current: number, pageSize: number) => {
    expressOrderPage.value.currentPage = current
    expressOrderPage.value.pageSize = pageSize
    selectExpressOrderPage()
}

const columns = [
    {
        title: '订单号',
        dataIndex: 'orderNumber',
        key: 'orderNumber',
        align: 'center'
    }, {
        title: '创建时间',
        dataIndex: 'createTime',
        key: 'createTime',
        align: 'center'
    },
    {
        title: '物流公司',
        dataIndex: 'logisticsCompanyName',
        key: 'logisticsCompanyName',
        align: 'center'
    },
    {
        title: '发货者姓名',
        dataIndex: 'senderName',
        key: 'senderName',
        align: 'center'
    },
    {
        title: '发货者号码',
        dataIndex: 'senderPhone',
        key: 'senderPhone',
        align: 'center'
    },
    {
        title: '发货地',
        dataIndex: 'senderAddress',
        key: 'senderAddress',
        align: 'center'
    },
    {
        title: '收件人姓名',
        dataIndex: 'receiverName',
        key: 'receiverName',
        align: 'center'
    },
    {
        title: '收件人号码',
        dataIndex: 'receiverPhone',
        key: 'receiverPhone',
        align: 'center'
    },
    {
        title: '收件地址',
        dataIndex: 'receiverAddress',
        key: 'receiverAddress',
        align: 'center'
    },
    {
        title: '操作',
        dataIndex: 'action',
        key: 'action',
        width: 120,
        align: 'center'
    }
]

// 详情
const logisticsDetailVisible = ref<boolean>(false)
const vertical = ref<boolean>(false)
// 详情数据
const logisticsDetailData = ref<any>({
    "expressOrder": {},
    "logisticsTrack": []
})
// 打开详情弹窗
const openDetail = async (e: any) => {
    logisticsDetailData.value.expressOrder = e
    const res: any = await getLogisticsTrackDetail(e.expressOrderId)
    if (res?.code === 0) {
        logisticsDetailData.value.logisticsTrack = res?.data
    }
    console.log(logisticsDetailData)
    logisticsDetailVisible.value = true
}

const deleteVisible = ref<boolean>(false)
const deleteUserTitle = ref<string>('')
// 删除
const deleteUser = (e: any) => {
    message.warning("不允许删除物流订单详情")

    deleteUserTitle.value = '不允许删除物流订单详情'
    deleteVisible.value = true
}

</script>

<style scoped></style>