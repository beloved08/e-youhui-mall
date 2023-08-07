<template>
    <a-card title="条件筛选区域">
        <div>
            <a-form layout="inline">
                <a-form-item label="用户名称" name="oneClassificationName">
                    <!-- <a-input v-model:value="commoditySearchForm.classificationName"></a-input> -->
                    <a-select v-model:value="cartSearchPage.userName" style="width: 150px" show-search :allowClear="true"
                        placeholder="用户名称">
                        <a-select-option v-if="userNameList" v-for="(item, index) in userNameList" :value="item">{{
                            item }}</a-select-option>
                    </a-select>
                </a-form-item>
                <a-form-item label="加入购物车时间范围">
                    <t-date-range-picker v-model="searchTime" :presets="presets" enable-time-picker />
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
    <a-card title="用户购物车列表数据" :bordered="false">
        <div>
            <a-table :columns="columns" :data-source="cartList" bordered :indentSize="50" :pagination="false"
                :loading="tableLoading">
                <template #bodyCell="{ column, record }">
                    <template v-if="column.key === 'status'">
                        <div v-if="record.status === 0">
                            <a-tag color="cyan">正常</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="red">异常</a-tag>
                        </div>
                    </template>
                    <template v-if="column.key === 'type'">
                        <div v-if="record.type === 0">
                            <a-tag color="green">普通用户</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="pink">会员用户</a-tag>
                        </div>
                    </template>
                    <template v-if="column.key === 'sex'">
                        <div v-if="record.sex === 0">
                            <a-tag color="#2db7f5">男</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="#87d068">女</a-tag>
                        </div>
                    </template>
                    <template v-if="column.key === 'avatar'">
                        <a-image style="height: 50px; width: 60px;" :src="record.avatar" />
                    </template>
                    <template v-if="column.key === 'action'">
                        <a-button type="link" size="small" @click="openCartDetail(record)">详情</a-button>
                        <a-popconfirm title="您确定要删除吗?" ok-text="确定" @confirm="deleteCart(record)" cancel-text="取消">
                            <template #icon><question-circle-outlined style="color: red" /></template>
                            <a-button type="link" danger size="small">删除</a-button>
                        </a-popconfirm>
                    </template>
                </template>
            </a-table>
        </div>
        <!-- 分页 -->
        <div class="mt-8 mb-4 flex justify-end">
            <a-pagination size="small" v-model:current="cartSearchPage.currentPage" :page-size-options="pageSizeOptions"
                :hideOnSinglePage="false" :show-total="(pageTotal: number) => `共 ${ pageTotal } 条 `" show-size-changer
                @showSizeChange="onShowSizeChange" @change="onShowSizeChange" v-model:page-size="cartSearchPage.pageSize"
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
    <!-- 购物车详情 -->
    <div>
        <a-modal v-model:visible="cartDetailVisible" title="购物车数据详情" :footer="false" :width="1000">
            <div>
                <a-collapse v-model:activeKey="cartActiveKey" accordion>
                    <a-collapse-panel key="1" header="用户详情">
                        <a-descriptions :title="cartDetailData.mallUser.realName" bordered>
                            <a-descriptions-item label="账户名">{{ cartDetailData.mallUser.realName }}</a-descriptions-item>
                            <a-descriptions-item label="账户类型">{{ cartDetailData.mallUser.nickName }}</a-descriptions-item>
                            <a-descriptions-item label="电话号码">{{ cartDetailData.mallUser.phone }}</a-descriptions-item>
                            <a-descriptions-item label="邮箱">{{ cartDetailData.mallUser.email }}</a-descriptions-item>
                            <a-descriptions-item label="性别">
                                <div v-if="cartDetailData.mallUser.sex === 0">
                                    <a-tag color="#2db7f5">男</a-tag>
                                </div>
                                <div v-else>
                                    <a-tag color="#87d068">女</a-tag>
                                </div>
                            </a-descriptions-item>
                            <a-descriptions-item label="类型">
                                <div v-if="cartDetailData.mallUser.type === 0">
                                    <a-tag color="green">普通用户</a-tag>
                                </div>
                                <div v-else>
                                    <a-tag color="pink">会员用户</a-tag>
                                </div>
                            </a-descriptions-item>
                            <a-descriptions-item label="状态">
                                <div v-if="cartDetailData.mallUser.status === 0">
                                    <a-tag color="cyan">正常</a-tag>
                                </div>
                                <div v-else>
                                    <a-tag color="red">异常</a-tag>
                                </div>
                            </a-descriptions-item>
                            <a-descriptions-item label="地址">{{ cartDetailData.mallUser.address }}</a-descriptions-item>
                            <a-descriptions-item label="头像">
                                <a-image style="height: 50px; width: 70px;" :src="cartDetailData.mallUser.avatar" />
                            </a-descriptions-item>
                        </a-descriptions>
                    </a-collapse-panel>
                    <a-collapse-panel key="2" header="商家详情">
                        <a-descriptions :title="cartDetailData.business.businessName" bordered>
                            <a-descriptions-item label="商家名称">{{ cartDetailData.business.businessName
                            }}</a-descriptions-item>
                            <a-descriptions-item label="店铺名称">{{ cartDetailData.business.shopName
                            }}</a-descriptions-item>
                            <a-descriptions-item label="商家电话">{{ cartDetailData.business.businessPhone
                            }}</a-descriptions-item>
                            <a-descriptions-item label="商家状态">
                                <a-tag v-if="cartDetailData.business.businessStatus == 0" color="blue">正常</a-tag>
                                <a-tag v-if="cartDetailData.business.businessStatus == 1" color="red">已注销</a-tag>
                                <a-tag v-if="cartDetailData.business.businessStatus == 2" color="pink">未审核</a-tag>
                                <a-tag v-if="cartDetailData.business.businessStatus == 3" color="purple">已审核</a-tag>
                                <a-tag v-if="cartDetailData.business.businessStatus == 4" color="cyan">信息有误</a-tag>
                            </a-descriptions-item>
                            <a-descriptions-item label="审核状态">
                                <a-tag v-if="cartDetailData.business.verifyStatus == 0" color="#2db7f5">审核通过</a-tag>
                                <a-tag v-if="cartDetailData.business.verifyStatus == 1" color="#f50">审核不通过</a-tag>
                                <a-tag v-if="cartDetailData.business.verifyStatus == 2" color="#87d068">信息有误</a-tag>
                                <a-tag v-if="cartDetailData.business.verifyStatus == 3" color="#108ee9">待审核</a-tag>
                            </a-descriptions-item>
                            <a-descriptions-item label="所在城市">{{ cartDetailData.business.businessCity
                            }}</a-descriptions-item>
                            <a-descriptions-item label="所在城市经度">{{ cartDetailData.business.businessCityLatitude
                            }}</a-descriptions-item>
                            <a-descriptions-item label="所在城市纬度">{{ cartDetailData.business.businessCityLongitude
                            }}</a-descriptions-item>
                            <a-descriptions-item label="详细地址经度">{{
                                cartDetailData.business.businessDetailAddressLatitude
                            }}</a-descriptions-item>
                            <a-descriptions-item label="详细地址纬度">{{
                                cartDetailData.business.businessDetailAddressLongitude
                            }}</a-descriptions-item>
                            <a-descriptions-item label="详细地址">{{ cartDetailData.business.businessDetailAddress
                            }}</a-descriptions-item>
                            <a-descriptions-item label="商家店铺描述">{{ cartDetailData.business.businessDescribe
                            }}</a-descriptions-item>
                        </a-descriptions>
                    </a-collapse-panel>
                    <a-collapse-panel key="3" header="商品详情">
                        <a-descriptions bordered :title="cartDetailData.commodity.commodityName">
                            <a-descriptions-item label="商品名称">{{ cartDetailData.commodity.commodityName
                            }}</a-descriptions-item>
                            <a-descriptions-item label="商品状态">
                                <div v-if="cartDetailData.commodity.commodityStatus === 0">
                                    <a-tag color="pink">销售中</a-tag>
                                </div>
                                <div v-else>
                                    <a-tag color="cyan">仓库中</a-tag>
                                </div>
                            </a-descriptions-item>
                            <a-descriptions-item label="商品库存">
                                <div v-if="Number(cartDetailData.commodity.commodityStock) <= 10">
                                    <a-tooltip placement="top" color="red">
                                        <template #title>
                                            <span>库存警告，即将无货，请即刻进货</span>
                                        </template>
                                        <a-tag color="#f50">
                                            {{ cartDetailData.commodity.commodityStock }}
                                        </a-tag>
                                    </a-tooltip>
                                </div>
                                <div
                                    v-else-if="Number(cartDetailData.commodity.commodityStock) >= 10 && Number(cartDetailData.commodity.commodityStock) <= 100">
                                    <a-tooltip placement="top" color="purple">
                                        <template #title>
                                            <span>库存预警，请注意进货</span>
                                        </template>
                                        <a-tag color="orange">
                                            {{ cartDetailData.commodity.commodityStock }}
                                        </a-tag>
                                    </a-tooltip>
                                </div>
                                <div v-else>
                                    <a-tooltip placement="top" color="blue">
                                        <template #title>
                                            <span>库存非常充裕，无需进货</span>
                                        </template>
                                        <a-tag color="success">
                                            {{ cartDetailData.commodity.commodityStock }}
                                        </a-tag>
                                    </a-tooltip>
                                </div>
                            </a-descriptions-item>
                            <a-descriptions-item label="商品类型">
                                <div v-if="cartDetailData.commodity.commodityStatus === 0">
                                    <a-tag color="green">虚拟商品</a-tag>
                                </div>
                                <div v-else>
                                    <a-tag color="purple">实物商品</a-tag>
                                </div>
                            </a-descriptions-item>
                            <a-descriptions-item label="销售模式">
                                <div v-if="cartDetailData.commodity.salesModel === 0">
                                    <a-tag color="orange">零售</a-tag>
                                </div>
                                <div v-else>
                                    <a-tag color="blue">批发</a-tag>
                                </div>
                            </a-descriptions-item>
                            <a-descriptions-item label="零售价/元">
                                <a-tag color="#2db7f5">{{ cartDetailData.commodity.retailPrice }}</a-tag>
                            </a-descriptions-item>
                            <a-descriptions-item label="批发价/元">
                                <a-tag color="#87d068">{{ cartDetailData.commodity.wholesalePrice }}</a-tag>
                            </a-descriptions-item>
                            <a-descriptions-item label="商品重量/kg">
                                <a-tag color="#2db7f5">{{ cartDetailData.commodity.commodityWeight }}</a-tag>
                            </a-descriptions-item>
                            <a-descriptions-item label="计量单位">
                                <a-tag color="#87d068">{{ cartDetailData.commodity.meterCompany }}</a-tag>
                            </a-descriptions-item>
                            <a-descriptions-item label="是否推荐为新品">
                                <div v-if="cartDetailData.commodity.commodityRecommend === 0">
                                    <a-tag color="#87d068">推荐</a-tag>
                                </div>
                                <div v-else>
                                    <a-tag color="#108ee9">不推荐</a-tag>
                                </div>
                            </a-descriptions-item>
                            <a-descriptions-item label="添加时间">
                                <a-tag color="blue">{{ cartDetailData.commodity.createTime }}</a-tag>
                            </a-descriptions-item>
                            <a-descriptions-item label="上架时间">
                                <a-tag color="blue">{{ cartDetailData.commodity.putShelfTime }}</a-tag>
                            </a-descriptions-item>
                            <a-descriptions-item label="下架时间">
                                <a-tag color="blue">{{ cartDetailData.commodity.offShelfTime }}</a-tag>
                            </a-descriptions-item>
                            <a-descriptions-item label="下架原因">{{ cartDetailData.commodity.offShelfReason
                            }}</a-descriptions-item>
                            <a-descriptions-item label="商品图片">
                                <div v-if="cartDetailData.commodity.commodityImageUrl">
                                    <a-image style="height: 50px; width: 70px;"
                                        :src="cartDetailData.commodity.commodityImageUrl" />
                                </div>
                                <div v-else>
                                    暂无
                                </div>
                            </a-descriptions-item>
                            <a-descriptions-item label="商品描述">{{ cartDetailData.commodity.commodityDescribe
                            }}</a-descriptions-item>
                        </a-descriptions>
                    </a-collapse-panel>
                </a-collapse>
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
import { message } from 'ant-design-vue'
import { getAllCart } from '@/api/commodity/index'
import { getAllMallUserName } from '@/api/user/index'
import dayjs from 'dayjs'

const userNameList = ref<any>()

const selectAllMallUserName = async () => {
    const res: any = await getAllMallUserName()
    if (res?.code === 0) {
        userNameList.value = res?.data
    }
}
selectAllMallUserName()

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

const pageTotal = ref<number>()
const pageSizeOptions = ref<string[]>(['5', '10', '20', '50', '100'])
const cartList = ref<any>()

const searchTime = ref<any>()
const cartSearchPage = ref<any>({
    "currentPage": 1,
    "pageSize": 5,
    "userName": '',
    "startTime": '',
    "endTime": ''
})

// 查询所有
const selectAllCart = async () => {
    const res: any = await getAllCart(cartSearchPage.value)

    if (res?.code === 0) {
        cartList.value = res?.data?.cartDetailDtoList

        pageTotal.value = res?.data?.total
        message.success('信息获取成功')
        loading.value = false
    } else {
        message.error("数据加载错误，请刷新重新加载")
    }

}

selectAllCart()

// 分页
const onShowSizeChange = (current: number, pageSize: number) => {
    cartSearchPage.value.currentPage = current
    cartSearchPage.value.pageSize = pageSize
    selectAllCart()
}

// 条件搜索
const selectSearchCart = () => {
    if (searchTime.value, length > 0) {
        cartSearchPage.value.startTime = searchTime.value[0]
        cartSearchPage.value.endTime = searchTime.value[1]
    }
    selectAllCart()
}

// 重置
const resetSelectSearchMallUser = () => {
    cartSearchPage.value.startTime = ''
    cartSearchPage.value.endTime = ''
    cartSearchPage.value.userName = ''
    selectAllCart()
}

const columns = [
    {
        title: '用户名',
        dataIndex: 'userName',
        key: 'userName',
        align: 'center'
    }, {
        title: '商品名称',
        dataIndex: 'commodityName',
        key: 'commodityName',
        align: 'center'
    },
    {
        title: '所属商家',
        dataIndex: 'businessName',
        key: 'businessName',
        align: 'center'
    },
    {
        title: '加入购物车时间',
        dataIndex: 'addTime',
        key: 'addTime',
        align: 'center'
    },
    {
        title: '操作',
        dataIndex: 'action',
        key: 'action',
        align: 'center'
    }
]

const cartActiveKey = ref([])
// 购物车数据详情控件
const cartDetailVisible = ref<boolean>(false)
// 购物车详情数据
const cartDetailData = ref<any>()
// 打开购物车详情弹窗
const openCartDetail = (e: any) => {
    cartDetailData.value = e
    cartDetailVisible.value = true
}

const deleteVisible = ref<boolean>(false)
const deleteUserTitle = ref<string>('')
// 删除用户
const deleteCart = (e: any) => {
    deleteUserTitle.value = '不允许删除-' + e.userName + '-' + e.commodityName + '该购物车数据'
    deleteVisible.value = true
}

</script>

<style scoped></style>