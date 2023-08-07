<template>
    <a-card title="条件筛选区域">
        <div>
            <a-form layout="inline">
                <a-form-item label="用户名称" name="oneClassificationName">
                    <!-- <a-input v-model:value="commoditySearchForm.classificationName"></a-input> -->
                    <a-select v-model:value="userCollectSearchPage.userName" style="width: 150px" show-search
                        :allowClear="true" placeholder="用户名称">
                        <a-select-option v-if="userNameList" v-for="(item, index) in userNameList" :value="item">{{
                            item }}</a-select-option>
                    </a-select>
                </a-form-item>
                <a-form-item>
                    <a-space>
                        <a-button type="primary" @click="selectSearchCart">
                            查询
                        </a-button>
                        <a-button type="primary" @click="resetSelectSearchMallUser">
                            重置
                        </a-button>
                    </a-space>
                </a-form-item>
            </a-form>
        </div>
    </a-card>
    <a-card title="用户余额/积分列表数据" :bordered="false">
        <div>
            <a-table :columns="columns" :data-source="userCollectList" bordered :indentSize="50" :pagination="false"
                :loading="tableLoading">
                <template #bodyCell="{ column, record }">
                    <template v-if="column.key === 'action'">
                        <a-button type="link" size="small" @click="userBalanceChangeRecharge(record.userBalanceChangeRechargeList
                        )">余额充值明细</a-button>
                        <a-button type="link" danger size="small" @click="userBalanceChangeDeduction(record.userBalanceChangeDeductionList
                        )">余额扣减明细</a-button>
                        <a-button type="link" size="small"
                            @click="userIntegralChangeRecharge(record.userIntegralChangeRechargeList)">积分充值明细</a-button>
                        <a-button type="link" danger size="small" @click="userIntegralChangeDeduction(record.userIntegralChangeDeductionList
                        )">积分扣减明细</a-button>
                    </template>
                </template>
            </a-table>
        </div>
        <!-- 分页 -->
        <div class="mt-8 mb-4 flex justify-end">
            <a-pagination size="small" v-model:current="userCollectSearchPage.currentPage"
                :page-size-options="pageSizeOptions" :hideOnSinglePage="false"
                :show-total="(pageTotal: number) => `共 ${ pageTotal } 条 `" show-size-changer
                @showSizeChange="onShowSizeChange" @change="onShowSizeChange"
                v-model:page-size="userCollectSearchPage.pageSize" :total="pageTotal">
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
    <!-- 详情 -->
    <div>
        <a-modal v-model:visible="userBalanceChangeRechargeVisible" title="余额充值明细（最近20条）" :destroyOnClose="true"
            :footer="false" width="1100px">
            <div v-if="userBalanceChangeRechargeData.length > 0">
                <a-descriptions :column="4" bordered v-for="(item, index) in userBalanceChangeRechargeData" :key="index">
                    <a-descriptions-item label="订单号">{{ item.orderNumber }}</a-descriptions-item>
                    <a-descriptions-item label="变动类型">余额充值</a-descriptions-item>
                    <a-descriptions-item label="充值时间">{{ item.changeTime
                    }}</a-descriptions-item>
                    <a-descriptions-item label="充值金额">
                        <a-tag color="#2db7f5">{{ item.changeQuota }} 元</a-tag>
                    </a-descriptions-item>
                </a-descriptions>
            </div>
            <div v-else>
                <a-empty description="暂无余额充值明细" />
            </div>
        </a-modal>
        <a-modal v-model:visible="userBalanceChangeDeductionVisible" title="余额扣减明细（最近20条）" :destroyOnClose="true"
            :footer="false" width="1100px">
            <div v-if="userBalanceChangeDeductionData.length > 0">
                <a-descriptions :column="4" bordered v-for="(item, index) in userBalanceChangeDeductionData" :key="index">
                    <a-descriptions-item label="订单号">{{ item.orderNumber }}</a-descriptions-item>
                    <a-descriptions-item label="变动类型">余额扣减</a-descriptions-item>
                    <a-descriptions-item label="扣减时间">{{ item.changeTime
                    }}</a-descriptions-item>
                    <a-descriptions-item label="扣减金额">
                        <a-tag color="#f50">{{ item.changeQuota }} 元</a-tag>
                    </a-descriptions-item>
                </a-descriptions>
            </div>
            <div v-else>
                <a-empty description="暂无余额扣减明细" />
            </div>
        </a-modal>
        <a-modal v-model:visible="userIntegralChangeRechargeVisible" title="积分充值明细（最近20条）" :destroyOnClose="true"
            :footer="false" width="1100px">
            <div v-if="userIntegralChangeRechargeData.length > 0">
                <a-descriptions :column="4" bordered v-for="(item, index) in userIntegralChangeRechargeData" :key="index">
                    <a-descriptions-item label="订单号">{{ item.orderNumber }}</a-descriptions-item>
                    <a-descriptions-item label="变动类型">积分充值</a-descriptions-item>
                    <a-descriptions-item label="充值时间">{{ item.changeTime
                    }}</a-descriptions-item>
                    <a-descriptions-item label="充值点数">
                        <a-tag color="#108ee9">{{ item.changeQuota }} 点</a-tag>
                    </a-descriptions-item>
                </a-descriptions>
            </div>
            <div v-else>
                <a-empty description="暂无积分充值明细" />
            </div>
        </a-modal>
        <a-modal v-model:visible="userIntegralChangeDeductionVisible" title="积分扣减明细（最近20条）" :destroyOnClose="true"
            :footer="false" width="1100px">
            <div v-if="userIntegralChangeDeductionData.length > 0">
                <a-descriptions :column="4" bordered v-for="(item, index) in userIntegralChangeDeductionData" :key="index">
                    <a-descriptions-item label="订单号">{{ item.orderNumber }}</a-descriptions-item>
                    <a-descriptions-item label="变动类型">积分扣减</a-descriptions-item>
                    <a-descriptions-item label="扣减时间">{{ item.changeTime
                    }}</a-descriptions-item>
                    <a-descriptions-item label="扣减点数">
                        <a-tag color="#f50">{{ item.changeQuota }} 点</a-tag>
                    </a-descriptions-item>
                </a-descriptions>
            </div>
            <div v-else>
                <a-empty description="暂无积分扣减明细" />
            </div>
        </a-modal>
    </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { message } from 'ant-design-vue'
import { getAllMallUserName } from '@/api/user/index'
import { getAllUserBalancePage } from '@/api/pay/index'

const userNameList = ref<any>()

const selectAllMallUserName = async () => {
    const res: any = await getAllMallUserName()
    if (res?.code === 0) {
        userNameList.value = res?.data
    }
}
selectAllMallUserName()

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
const userCollectList = ref<any>()

const userCollectSearchPage = ref<any>({
    "currentPage": 1,
    "pageSize": 5,
    "userName": ''
})

// 获取所有用户的收藏信息
const selectAllUserCollectList = async () => {
    const res: any = await getAllUserBalancePage(userCollectSearchPage.value)
    if (res?.code === 0) {
        userCollectList.value = res?.data?.userBalanceDtoList
        pageTotal.value = res?.data?.total
        message.success('信息获取成功')
        loading.value = false
    } else {
        message.error("数据加载错误，请刷新重新加载")
    }
}
selectAllUserCollectList()

// 分页
const onShowSizeChange = (current: number, pageSize: number) => {
    userCollectSearchPage.value.currentPage = current
    userCollectSearchPage.value.pageSize = pageSize
    selectAllUserCollectList()
}

// 条件搜索
const selectSearchCart = () => {
    selectAllUserCollectList()
}

// 重置
const resetSelectSearchMallUser = () => {
    userCollectSearchPage.value.userName = ''
    selectAllUserCollectList()
}

const columns = [
    {
        title: '用户名',
        dataIndex: 'userName',
        key: 'userName',
        align: 'center'
    }, {
        title: '可用余额/元',
        dataIndex: 'availableBalance',
        key: 'availableBalance',
        align: 'center'
    },
    {
        title: '可用积分/点',
        dataIndex: 'availableIntegral',
        key: 'availableIntegral',
        align: 'center'
    },
    {
        title: '操作',
        dataIndex: 'action',
        key: 'action',
        align: 'center'
    }
]

// 详情控件
const userBalanceChangeRechargeVisible = ref<boolean>(false)
const userBalanceChangeDeductionVisible = ref<boolean>(false)
const userIntegralChangeRechargeVisible = ref<boolean>(false)
const userIntegralChangeDeductionVisible = ref<boolean>(false)
// 详情数据
const userBalanceChangeRechargeData = ref<any>()
const userBalanceChangeDeductionData = ref<any>()
const userIntegralChangeRechargeData = ref<any>()
const userIntegralChangeDeductionData = ref<any>()

// 打开详情弹窗
const userBalanceChangeRecharge = (e: any) => {
    userBalanceChangeRechargeData.value = e
    userBalanceChangeRechargeVisible.value = true
}
const userBalanceChangeDeduction = (e: any) => {
    userBalanceChangeDeductionData.value = e
    userBalanceChangeDeductionVisible.value = true
}
const userIntegralChangeRecharge = (e: any) => {
    userIntegralChangeRechargeData.value = e
    userIntegralChangeRechargeVisible.value = true
}
const userIntegralChangeDeduction = (e: any) => {
    userIntegralChangeDeductionData.value = e
    userIntegralChangeDeductionVisible.value = true
}

</script>

<style scoped></style>