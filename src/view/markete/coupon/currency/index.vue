<template>
    <div class="">
        <a-button type="primary" @click="addCouponVisible = true">新增通用优惠券</a-button>
    </div>
    <!-- 新增通用优惠券弹窗 -->
    <div>
        <a-modal v-model:visible="addCouponVisible" :destroyOnClose="true" :width="630" :footer="false" title="新增通用优惠券">
            <div>
                <a-form :model="addCouponForm" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }" autocomplete="off">
                    <a-form-item label="优惠券名称" name="universalCouponName"
                        :rules="[{ required: true, message: '请输入优惠券名称' }]">
                        <a-input v-model:value="addCouponForm.universalCouponName" />
                    </a-form-item>

                    <a-form-item label="总计数量" name="totalQuantity;">
                        <a-input-number id="inputNumber" v-model:value="addCouponForm.totalQuantity" :min="1" :max="100" />
                    </a-form-item>

                    <a-form-item label="折扣金额/元" name="discountAmount" :rules="[{ required: true, message: '请输入折扣金额' }]">
                        <a-input v-model:value="addCouponForm.discountAmount" />
                    </a-form-item>

                    <a-form-item label="满多少可用" name="fullAvailable" :rules="[{ required: true, message: '请输入满多少可用' }]">
                        <a-input v-model:value="addCouponForm.fullAvailable" />
                    </a-form-item>

                    <a-form-item label="时间范围" name="dateTime" :rules="[{ required: true, message: '请选择时间范围' }]">
                        <t-date-range-picker v-model="dateTime" :presets="presets" enable-time-picker />
                    </a-form-item>

                    <a-form-item :wrapper-col="{ offset: 6, span: 16 }">
                        <a-space>
                            <a-button type="primary" @click="submitAddCoupon">添加优惠券</a-button>
                            <a-button @click="resetAddForm">重置</a-button>
                        </a-space>
                    </a-form-item>
                </a-form>
            </div>
        </a-modal>
    </div>
    <!-- 条件搜索区域 -->
    <div class="mt-4">
        <a-card title="条件筛选区域">
            <div>
                <a-form layout="inline">
                    <a-form-item label="优惠券名称">
                        <a-input placeholder="优惠券名称" v-model:value="searchCouponForm.universalCouponName">
                        </a-input>
                    </a-form-item>
                    <a-form-item label="优惠券状态" name="oneClassificationName">
                        <a-select v-model:value="searchCouponFormStatus" style="width: 150px" show-search :allowClear="true"
                            placeholder="优惠券状态">
                            <!-- 0：使用中，1：未开始，2：已结束，3：所有 -->
                            <a-select-option value="0">正在进行中</a-select-option>
                            <a-select-option value="1">未开始</a-select-option>
                            <a-select-option value="2">已结束</a-select-option>
                        </a-select>
                    </a-form-item>
                    <a-form-item>
                        <a-space>
                            <a-button type="primary" @click="selectSearchCoupon">
                                查询
                            </a-button>
                            <a-button type="primary" @click="resetSelectSearchCoupon">
                                重置
                            </a-button>
                        </a-space>
                    </a-form-item>
                </a-form>
            </div>
        </a-card>
    </div>
    <!-- 优惠券列表 -->
    <div>
        <a-card title="全场通用优惠券列表数据" :bordered="false">
            <div>
                <a-table :columns="columns" :data-source="universalCouponList" bordered :indentSize="50" :pagination="false"
                    :loading="tableLoading">
                    <template #bodyCell="{ column, record }">

                        <template v-if="column.key === 'totalQuantity'">
                            <a-tag color="cyan">{{ record.totalQuantity }}</a-tag>
                        </template>
                        <template v-if="column.key === 'status'">
                            <div v-if="record.status === 0">
                                <a-tag color="#2db7f5">正在进行中</a-tag>
                            </div>
                            <div v-else-if="record.status === 1">
                                <a-tag color="#87d068">未开始</a-tag>
                            </div>
                            <div v-else>
                                <a-tag color="#f50">已结束</a-tag>
                            </div>
                        </template>

                        <template v-if="column.key === 'action'">
                            <a-button type="link" size="small" @click="openUniversalCouponDetail(record)">详情</a-button>
                            <a-popconfirm title="您确定要删除该优惠券吗?" ok-text="确定" @confirm="deleteUniversalCouponById(record)"
                                cancel-text="取消">
                                <template #icon><question-circle-outlined style="color: red" /></template>
                                <a-button type="link" danger size="small">删除</a-button>
                            </a-popconfirm>
                        </template>
                    </template>
                </a-table>
            </div>
            <!-- 分页 -->
            <div class="mt-8 mb-4 flex justify-end">
                <a-pagination size="small" v-model:current="searchCouponForm.currentPage"
                    :page-size-options="pageSizeOptions" :hideOnSinglePage="false"
                    :show-total="(pageTotal: number) => `共 ${ pageTotal } 条 `" show-size-changer
                    @showSizeChange="onShowSizeChange" @change="onShowSizeChange"
                    v-model:page-size="searchCouponForm.pageSize" :total="pageTotal">
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
    <!-- 优惠券详情弹窗 -->
    <div>
        <a-modal v-model:visible="universalCouponDetailVisible" title="通用优惠券详情" :centered="true" :destroyOnClose="true"
            :footer="false" width="950px">
            <div>
                <a-descriptions bordered>
                    <a-descriptions-item label="优惠券名称">{{ universalCouponDetailData.universalCouponName
                    }}</a-descriptions-item>
                    <a-descriptions-item label="总计数量/张">
                        <a-tag color="cyan">{{ universalCouponDetailData.totalQuantity
                        }}</a-tag>
                    </a-descriptions-item>
                    <a-descriptions-item label="折扣金额/元">{{ universalCouponDetailData.discountAmount
                    }}元</a-descriptions-item>
                    <a-descriptions-item label="满多少可用/元">{{ universalCouponDetailData.fullAvailable
                    }}元</a-descriptions-item>
                    <a-descriptions-item label="开始时间">{{ universalCouponDetailData.startTime }}</a-descriptions-item>
                    <a-descriptions-item label="结束时间">{{ universalCouponDetailData.endTime }}</a-descriptions-item>
                    <a-descriptions-item label="优惠券状态">
                        <div v-if="universalCouponDetailData.status === 0">
                            <a-tag color="#2db7f5">正在进行中</a-tag>
                        </div>
                        <div v-else-if="universalCouponDetailData.status === 1">
                            <a-tag color="#87d068">未开始</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="#f50">已结束</a-tag>
                        </div>
                    </a-descriptions-item>
                </a-descriptions>
            </div>
        </a-modal>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import dayjs from 'dayjs'
import { QuestionCircleOutlined } from '@ant-design/icons-vue'
import { addUniversalCoupon, getAllUniversalCouponPage, deleteUniversalCoupon } from '@/api/marketing/index'
import { message } from 'ant-design-vue'

// 新增通用优惠券弹窗控件
const addCouponVisible = ref<boolean>(false)

const dateTime = ref<any>([])
const presets = ref({
    最近7天: [dayjs().subtract(6, 'day'), dayjs()],
    最近3天: [dayjs().subtract(2, 'day'), dayjs()],
    今天: [dayjs(), dayjs()],
})

// 搜索条件
const searchCouponFormStatus = ref<any>()
const searchCouponForm = ref<any>({
    "currentPage": 1,
    "pageSize": 5,
    "universalCouponName": '',
    "status": 3
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
const universalCouponList = ref<any>()
// 获取所有优惠券
const getAllUniversalCouponPageList = async () => {
    // searchCouponForm.value.status = Number(2)
    const res: any = await getAllUniversalCouponPage(searchCouponForm.value)
    if (res?.code === 0) {
        universalCouponList.value = res?.data?.universalCouponDtoList
        pageTotal.value = res?.data?.total
        message.success('信息获取成功')
        loading.value = false
    } else {
        message.error("数据加载错误，请刷新重新加载")
    }
}
getAllUniversalCouponPageList()

// 分页
const onShowSizeChange = (current: number, pageSize: number) => {
    searchCouponForm.value.currentPage = current
    searchCouponForm.value.pageSize = pageSize
    getAllUniversalCouponPageList()
}

const columns = [
    {
        title: '优惠券名称',
        dataIndex: 'universalCouponName',
        key: 'universalCouponName',
        align: 'center'
    },
    {
        title: '折扣金额/元',
        dataIndex: 'discountAmount',
        key: 'discountAmount',
        align: 'center'
    },
    {
        title: '满多少可用/元',
        dataIndex: 'fullAvailable',
        key: 'fullAvailable',
        align: 'center'
    },
    {
        title: '总计数量/张',
        dataIndex: 'totalQuantity',
        key: 'totalQuantity',
        align: 'center'
    },
    {
        title: '优惠券状态',
        dataIndex: 'status',
        key: 'status',
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
        title: '操作',
        dataIndex: 'action',
        key: 'action',
        align: 'center'
    }
]
// 条件搜索
const selectSearchCoupon = () => {
    if (searchCouponFormStatus.value) {
        searchCouponForm.value.status = Number(searchCouponFormStatus.value)
    }
    getAllUniversalCouponPageList()
}
// 重置
const resetSelectSearchCoupon = () => {
    searchCouponForm.value.universalCouponName = ''
    searchCouponFormStatus.value = ''
    searchCouponForm.value.status = Number(3)
    getAllUniversalCouponPageList()
}

// 新增通用优惠券表单
const addCouponForm = ref<any>({
    "universalCouponName": '通用优惠券',
    "discountAmount": '',
    "fullAvailable": '',
    "totalQuantity": 0,
    "startTime": '',
    "endTime": '',
})
// 重置表单
const resetAddForm = () => {
    addCouponForm.value.discountAmount = ''
    addCouponForm.value.fullAvailable = ''
    addCouponForm.value.startTime = ''
    addCouponForm.value.endTime = ''
    dateTime.value = ''
}
// 新增通用优惠券提交事件
const submitAddCoupon = async () => {
    addCouponForm.value.startTime = dateTime.value[0]
    addCouponForm.value.endTime = dateTime.value[1]

    const res: any = await addUniversalCoupon(addCouponForm.value)

    if (res?.code === 0) {
        message.success(res?.data)
        resetAddForm()
        addCouponVisible.value = false
        getAllUniversalCouponPageList()
    } else {
        message.error(res?.msg)
    }
}
// 详情数据
const universalCouponDetailData = ref<any>()
const universalCouponDetailVisible = ref<boolean>(false)
// 详情
const openUniversalCouponDetail = (e: any) => {
    universalCouponDetailVisible.value = true
    universalCouponDetailData.value = e
    console.log(e)
}
// 删除
const deleteUniversalCouponById = async (e: any) => {

    const deleteRes: any = await deleteUniversalCoupon(e.universalCouponId)
    if (deleteRes?.code === 0) {
        message.success(deleteRes?.data)
        getAllUniversalCouponPageList()
    } else {
        message.error(deleteRes?.msg)
    }

}

</script>

<style scoped></style>