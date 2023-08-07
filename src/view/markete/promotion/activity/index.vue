<template>
    <div class="p-4">
        <a-button type="primary" @click="createActivityVisible = true">创建促销活动</a-button>
    </div>
    <div>
        <a-modal v-model:visible="createActivityVisible" width="50%" :keyboard="false" :destroyOnClose="true"
            :maskClosable="false" title="创建促销活动" :footer="false">
            <div style="width: 100%;" class="flex justify-center">
                <a-form :model="activityForm" class="flex flex-col justify-center">
                    <a-form-item label="活动名称">
                        <a-input v-model:value="activityForm.promotionActivityName" />
                    </a-form-item>
                    <a-form-item label="活动类型">
                        <a-checkbox-group v-model:value="activityCategory" style="width: 100%">
                            <a-checkbox :checked="true" value="限时秒杀">限时秒杀</a-checkbox>
                        </a-checkbox-group>
                    </a-form-item>
                    <a-form-item label="添加时间" name="activityTime">
                        <t-date-range-picker v-model="activityTime" :presets="presets" enable-time-picker />
                    </a-form-item>
                    <a-form-item label="活动描述">
                        <a-textarea v-model:value="activityForm.activityDescribe" />
                    </a-form-item>
                    <!-- <a-form-item label="活动商品" :wrapper-col="{ span: 14, offset: 1 }">
                        <a-upload v-model:file-list="batchFileList" :maxCount="1" name="promotionActivityCommodityBatchData"
                            :progress="progress" :before-upload="batchBeforeUpload" @change="handleUploadBatchChange">
                            <a-button danger>导入活动商品</a-button>
                        </a-upload>
                    </a-form-item> -->
                    <a-form-item :wrapper-col="{ span: 24, offset: 2 }">
                        <a-button type="primary" @click="onSubmit">立即创建</a-button>
                        <a-button @click="createActivityVisible = false" style="margin-left: 15px">取消</a-button>
                    </a-form-item>
                </a-form>
            </div>
            <div>
                <a-modal v-model:visible="batchOneLevelVisible" :width="130" :centered="true" :closable="false"
                    :maskClosable="false" :keyboard="false" :footer="false">
                    <div class="flex flex-col justify-items-center">
                        <a-progress type="circle" :percent="oneLevelDefaultPercent" :width="82" />
                        <span class="mt-2 text-sm text-center">{{ batchUploadText }}</span>
                    </div>
                </a-modal>
            </div>
        </a-modal>
    </div>
    <div class="mt-4">
        <a-card title="条件筛选区域">
            <div>
                <a-form layout="inline">
                    <a-form-item label="活动名称" name="oneClassificationName">
                        <a-input v-model:value="promotionActivityPage.promotionActivityName"></a-input>
                    </a-form-item>
                    <a-form-item class="w-48" label="活动状态">
                        <a-select v-model:value="promotionActivityPage.promotionActivityStatus">
                            <a-select-option value="0">未开始</a-select-option>
                            <a-select-option value="1">进行中</a-select-option>
                            <a-select-option value="2">已结束</a-select-option>
                        </a-select>
                    </a-form-item>
                    <a-form-item>
                        <a-space>
                            <a-button type="primary" @click="selectSearchCart">
                                搜索
                            </a-button>
                            <a-button type="primary" @click="resetSelectSearchMallUser">
                                重置
                            </a-button>
                        </a-space>
                    </a-form-item>
                </a-form>
            </div>
        </a-card>
    </div>
    <div>
        <a-card title="促销活动列表数据" :bordered="false">
            <div>
                <a-table :columns="columns" :data-source="promotionActivityPageList" bordered :indentSize="50"
                    :pagination="false" :loading="tableLoading">
                    <template #bodyCell="{ column, record }">

                        <template v-if="column.key === 'status'">
                            <div v-if="record.status === 0">
                                <a-tag color="#2db7f5">未开始</a-tag>
                            </div>
                            <div v-if="record.status === 1">
                                <a-tag color="#87d068">进行中</a-tag>
                            </div>
                            <div v-if="record.status === 2">
                                <a-tag color="#f50">已结束</a-tag>
                            </div>
                        </template>

                        <template v-if="column.key === 'isCurrent'">
                            <div v-if="record.isCurrent === 0">
                                <a-tag color="#ff2e63">是</a-tag>
                            </div>
                            <div v-if="record.isCurrent === 1">
                                <a-tag color="#e3fdfd">否</a-tag>
                            </div>
                        </template>

                        <template v-if="column.key === 'action'">
                            <a-button type="link" size="small"
                                @click="selectPromotionActivityCommodity(record)">商品列表</a-button>
                            <a-popconfirm title="确定将该活动设置为当前进行的活动?" ok-text="确定" @confirm="setCurrentActivity(record)"
                                cancel-text="取消">
                                <template #icon><question-circle-outlined style="color: red" /></template>
                                <a-button type="link" danger size="small">设置</a-button>
                            </a-popconfirm>
                        </template>
                    </template>
                </a-table>
            </div>
            <!-- 分页 -->
            <div class="mt-8 mb-4 flex justify-end">
                <a-pagination size="small" v-model:current="promotionActivityPage.currentPage"
                    :page-size-options="pageSizeOptions" :hideOnSinglePage="false"
                    :show-total="(pageTotal: number) => `共 ${ pageTotal } 条 `" show-size-changer
                    @showSizeChange="onShowSizeChange" @change="onShowSizeChange"
                    v-model:page-size="promotionActivityPage.pageSize" :total="pageTotal">
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
    </div>
    <div>
        <a-modal v-model:visible="promotionActivityCommodityVisible" :destroyOnClose="true" :footer="false" width="1500px"
            :title="promotionActivityCommodityTitle">
            <!-- <div>
                <a-card title="条件筛选区域">
                <a-form layout="inline">
                    <a-form-item label="商品名称" name="oneClassificationName">
                        <a-input v-model:value="promotionActivityCommodityPage.commodityName"></a-input>
                    </a-form-item>
                    <a-form-item>
                        <a-space>
                            <a-button type="primary" @click="selectSearchPromotionActivityCommodityPage">
                                搜索
                            </a-button>
                            <a-button type="primary" @click="resetSelectSearchPromotionActivityCommodityPage">
                                重置
                            </a-button>
                        </a-space>
                    </a-form-item>
                </a-form>
                </a-card>
            </div> -->
            <div class="mt-6">
                <a-card title="促销活动商品列表数据" :bordered="true">
                    <div>
                        <a-table :columns="columns1" :data-source="promotionActivityCommodityList" bordered :indentSize="50"
                            :pagination="false" :loading="tableLoading1">
                            <template #bodyCell="{ column, record }">

                                <template v-if="column.key === 'commodityType'">
                                    <div v-if="record.commodityType === 0">
                                        <a-tag color="#2db7f5">虚拟商品</a-tag>
                                    </div>
                                    <div v-else>
                                        <a-tag color="#87d068">实物商品</a-tag>
                                    </div>
                                </template>
                                <template v-if="column.key === 'commodityStatus'">
                                    <div v-if="record.commodityStatus === 0">
                                        <a-tag color="#2db7f5">销售中</a-tag>
                                    </div>
                                    <div v-else>
                                        <a-tag color="#87d068">仓库中</a-tag>
                                    </div>
                                </template>
                                <template v-if="column.key === 'retailPrice'">
                                    <a-tag color="cyan">{{ record.retailPrice }}</a-tag>
                                </template>
                                <template v-if="column.key === 'commodityStock'">
                                    <a-tag color="green">{{ record.commodityStock }}</a-tag>
                                </template>
                                <template v-if="column.key === 'meterCompany'">
                                    <a-tag color="#3f72af">{{ record.meterCompany }}</a-tag>
                                </template>
                                <template v-if="column.key === 'commodityWeight'">
                                    <a-tag color="purple">{{ record.commodityWeight }}</a-tag>
                                </template>

                                <template v-if="column.key === 'commodityImageUrl'">
                                    <img :src="record.commodityImageUrl" style="height: 50px; width: 60px;" alt="">
                                </template>

                                <!-- <template v-if="column.key === 'action'">
                                    <a-button type="link" size="small"
                                        @click="selectPromotionActivityCommodity(record)">商品列表</a-button>
                                </template> -->
                            </template>
                        </a-table>
                    </div>
                    <!-- 分页 -->
                    <div class="mt-8 mb-4 flex justify-center">
                        <a-pagination size="small" v-model:current="promotionActivityCommodityPage.currentPage"
                            :page-size-options="pageSizeOptions1" :hideOnSinglePage="false"
                            :show-total="(pageTotal1: number) => `共 ${ pageTotal1 } 条 `" show-size-changer
                            @showSizeChange="onShowSizeChange1" @change="onShowSizeChange1"
                            v-model:page-size="promotionActivityCommodityPage.pageSize" :total="pageTotal1">
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
            </div>
        </a-modal>
    </div>
</template>

<script setup lang="ts">
import { QuestionCircleOutlined } from '@ant-design/icons-vue'
import { ref } from 'vue'
import dayjs from 'dayjs'
import { message } from 'ant-design-vue'
import type { UploadProps } from 'ant-design-vue'
import type { UploadChangeParam } from 'ant-design-vue'
import { createPromotionActivity, uploadPromotionActivityCommodity, getPromotionActivityPage, getPromotionActivityCommodityPage, setCurrentPromotionActivity } from '@/api/marketing/index'

const presets = ref({
    最近7天: [dayjs().subtract(6, 'day'), dayjs()],
    最近3天: [dayjs().subtract(2, 'day'), dayjs()],
    今天: [dayjs(), dayjs()],
})

const promotionActivityPage = ref<any>({
    currentPage: 1,
    pageSize: 6,
    promotionActivityName: '',
    promotionActivityStatus: '-1'
})

// 控制表格加载动画开关
const loading = ref<boolean>(true)
// 表格加载动画自定义设置
const tableLoading = ref<any>({
    size: 'large',
    delay: 100,
    spinning: loading
})

const pageTotal = ref<number>()
const pageSizeOptions = ref<string[]>(['5', '10', '20', '50', '100'])
const promotionActivityPageList = ref<any>()
// 获取所有信息
const selectPromotionActivityPageList = async () => {
    const res: any = await getPromotionActivityPage(promotionActivityPage.value)
    if (res?.code === 0) {
        promotionActivityPageList.value = res?.data?.promotionActivityDtoList
        pageTotal.value = res?.data?.total
        message.success('信息获取成功')
        loading.value = false
    } else {
        message.error("数据加载错误，请刷新重新加载")
    }
}
selectPromotionActivityPageList()

// 分页
const onShowSizeChange = (current: number, pageSize: number) => {
    promotionActivityPage.value.currentPage = current
    promotionActivityPage.value.pageSize = pageSize
    selectPromotionActivityPageList()
}

// 条件搜索
const selectSearchCart = () => {
    selectPromotionActivityPageList()
}

// 重置
const resetSelectSearchMallUser = () => {
    promotionActivityPage.value.promotionActivityName = ''
    promotionActivityPage.value.promotionActivityStatus = '-1'
    selectPromotionActivityPageList()
}

const columns = [
    {
        title: '活动名称',
        dataIndex: 'promotionActivityName',
        key: 'promotionActivityName',
        align: 'center'
    }, {
        title: '活动类型',
        dataIndex: 'promotionActivityCategory',
        key: 'promotionActivityCategory',
        align: 'center'
    },
    {
        title: '创建时间',
        dataIndex: 'createTime',
        key: 'createTime',
        align: 'center'
    },
    {
        title: '开始时间',
        dataIndex: 'startTime',
        key: 'startTime',
        align: 'center'
    },
    {
        title: '结束时间',
        dataIndex: 'endTime',
        key: 'endTime',
        align: 'center'
    },
    {
        title: '活动状态',
        dataIndex: 'status',
        key: 'status',
        align: 'center'
    },
    {
        title: '是否为当前活动',
        dataIndex: 'isCurrent',
        key: 'isCurrent',
        align: 'center'
    },
    {
        title: '活动描述',
        dataIndex: 'activityDescribe',
        key: 'activityDescribe',
        align: 'center'
    },
    {
        title: '操作',
        dataIndex: 'action',
        key: 'action',
        align: 'center'
    }
]

const promotionActivityCommodityPage = ref<any>({
    currentPage: 1,
    pageSize: 5,
    promotionActivityId: '',
    commodityName: ''
})

const promotionActivityCommodityVisible = ref<any>(false)
const pageTotal1 = ref<number>()
const pageSizeOptions1 = ref<string[]>(['5', '10', '20', '30', '50'])
const promotionActivityCommodityList = ref<any>()
const promotionActivityCommodityTitle = ref<any>()

// 控制表格加载动画开关
const loading1 = ref<boolean>(true)
// 表格加载动画自定义设置
const tableLoading1 = ref<any>({
    size: 'large',
    delay: 100,
    spinning: loading
})

const selectPromotionActivityCommodityList = async () => {
    const res: any = await getPromotionActivityCommodityPage(promotionActivityCommodityPage.value)
    if (res?.code === 0) {
        promotionActivityCommodityList.value = res?.data?.commodityList
        pageTotal1.value = res?.data?.total
        message.success('信息获取成功')
        loading1.value = false
    } else {
        message.error("数据加载错误，请刷新重新加载")
    }
}

const columns1 = [
    {
        title: '名称',
        dataIndex: 'commodityName',
        key: 'commodityName',
        align: 'center'
    }, {
        title: '价格/元',
        dataIndex: 'retailPrice',
        key: 'retailPrice',
        align: 'center'
    },
    {
        title: '状态',
        dataIndex: 'commodityStatus',
        key: 'commodityStatus',
        align: 'center'
    },
    {
        title: '库存',
        dataIndex: 'commodityStock',
        key: 'commodityStock',
        align: 'center'
    },
    {
        title: '类型',
        dataIndex: 'commodityType',
        key: 'commodityType',
        align: 'center'
    },
    {
        title: '重量',
        dataIndex: 'commodityWeight',
        key: 'commodityWeight',
        align: 'center'
    },
    {
        title: '计量单位',
        dataIndex: 'meterCompany',
        key: 'meterCompany',
        align: 'center'
    },
    {
        title: '图片',
        dataIndex: 'commodityImageUrl',
        key: 'commodityImageUrl',
        align: 'center'
    },
    {
        title: '描述',
        dataIndex: 'commodityDescribe',
        key: 'commodityDescribe',
        align: 'center'
    },
    // {
    //     title: '操作',
    //     dataIndex: 'action',
    //     key: 'action',
    //     align: 'center'
    // }
]

const selectPromotionActivityCommodity = async (e: any) => {
    promotionActivityCommodityPage.value.promotionActivityId = e.promotionActivityId
    selectPromotionActivityCommodityList()

    promotionActivityCommodityVisible.value = true
}

// 分页
const onShowSizeChange1 = (current: number, pageSize: number) => {
    promotionActivityCommodityPage.value.currentPage = current
    promotionActivityCommodityPage.value.pageSize = pageSize
    selectPromotionActivityCommodityList()
}

const createActivityVisible = ref<boolean>(false)
const activityTime = ref<any>()
const activityCategory = ref<any>()

const activityForm = ref<any>({
    "promotionActivityName": '',
    "promotionActivityCategory": '',
    "startTime": '',
    "endTime": '',
    "activityDescribe": ''
})

const onSubmit = async () => {
    activityForm.value.startTime = activityTime.value[0]
    activityForm.value.endTime = activityTime.value[1]
    activityForm.value.promotionActivityCategory = activityCategory.value[0]

    const res: any = await createPromotionActivity(activityForm.value)
    if (res?.code === 0) {
        message.success("促销活动创建成功")
        // uploadPromotionActivityCommodityData(res?.data)
        createActivityVisible.value = false
        selectPromotionActivityPageList()
    }
}

const batchFileList = ref<any>()
// 进度条定时器
const oneLevelTimer = ref()
const isBatchUpload = ref<boolean>(false)
const batchUploadText = ref<string>('开始导入')
const oneLevelDefaultPercent = ref<number>(0)
const batchOneLevelVisible = ref<boolean>(false)

// 进度条样式
const progress: UploadProps['progress'] = {
    strokeColor: {
        '0%': '#108ee9',
        '100%': '#87d068',
    },
    strokeWidth: 3,
    format: (percent: any) => `${ parseFloat(percent.toFixed(2)) }%`,
    class: 'test',
}

const batchBeforeUpload: UploadProps['beforeUpload'] = (file: any) => {
    batchFileList.value = [...batchFileList.value, file]
    return false
}
const handleUploadBatchChange = (info: UploadChangeParam) => {
    if (info.file.status !== 'uploading') {
        isBatchUpload.value = true
    }
    if (info.file.status === 'done') {
        message.success(`${ info.file.name } file uploaded successfully`)
    } else if (info.file.status === 'error') {
        message.error(`${ info.file.name } file upload failed.`)
    }
}
// 导入excel
const uploadPromotionActivityCommodityData = async (e: string) => {
    const formData = new FormData()
    formData.append('promotionActivityCommodityBatchData', batchFileList.value[0].originFileObj)

    const batchUploadText = ref<string>('开始导入')
    oneLevelDefaultPercent.value = 0
    batchOneLevelVisible.value = true

    // 发送请求导入数据
    const res = await uploadPromotionActivityCommodity(formData, e)
    oneLevelTimer.value = setInterval(() => {
        batchUploadText.value = '正在导入...'
        oneLevelDefaultPercent.value += 9
        if (oneLevelDefaultPercent.value === 90) {
            // 销毁定时器
            clearInterval(oneLevelTimer.value)
            if (res?.code === 0) {
                message.success("促销活动商品导入成功")

                oneLevelDefaultPercent.value = 100
                batchUploadText.value = '导入完成'

                setTimeout(() => {
                    batchOneLevelVisible.value = false
                    createActivityVisible.value = false
                }, 1000)
            }
        }
    }, 100)
}

// 设置为当前活动
const setCurrentActivity = async (e: any) => {
    if (e.status === 2) {
        message.error("当前活动已结束，此操作无法继续！")
    } else {
        const res: any = await setCurrentPromotionActivity(e.promotionActivityId)
        if (res?.code === 0) {
            message.success(res?.msg)
            selectPromotionActivityPageList()
        } else {
            message.error(res?.msg)
        }
    }
}

</script>

<style scoped></style>