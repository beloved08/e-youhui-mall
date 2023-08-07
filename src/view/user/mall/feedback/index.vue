<template>
    <a-card title="条件筛选区域">
        <div>
            <a-form layout="inline">
                <a-form-item label="用户名称" name="oneClassificationName">
                    <!-- <a-input v-model:value="commoditySearchForm.classificationName"></a-input> -->
                    <a-select v-model:value="userAddressSearchPage.userName" style="width: 150px" show-search
                        :allowClear="true" placeholder="用户名称">
                        <a-select-option v-if="userNameList" v-for="(item, index) in userNameList" :value="item">{{
                            item }}</a-select-option>
                    </a-select>
                </a-form-item>
                <a-form-item label="收件人">
                    <a-input v-model:value="userAddressSearchPage.consignee">
                    </a-input>
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
    <a-card title="用户收货地址列表数据" :bordered="false">
        <div>
            <a-table :columns="columns" :data-source="userAddressList" bordered :indentSize="50" :pagination="false"
                :loading="tableLoading">
                <template #bodyCell="{ column, record }">

                    <template v-if="column.key === 'isDefault'">
                        <div v-if="record.isDefault === 0">
                            <a-tag color="#2db7f5">默认地址</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="#87d068">普通地址</a-tag>
                        </div>
                    </template>

                    <template v-if="column.key === 'action'">
                        <a-button type="link" size="small" @click="openUserAddressDetail(record)">详情</a-button>
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
            <a-pagination size="small" v-model:current="userAddressSearchPage.currentPage"
                :page-size-options="pageSizeOptions" :hideOnSinglePage="false"
                :show-total="(pageTotal: number) => `共 ${ pageTotal } 条 `" show-size-changer
                @showSizeChange="onShowSizeChange" @change="onShowSizeChange"
                v-model:page-size="userAddressSearchPage.pageSize" :total="pageTotal">
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
    <!-- 收货地址详情 -->
    <div>
        <a-modal v-model:visible="userAddressDetailVisible" title="" :centered="true" :destroyOnClose="true" :footer="false"
            width="1300px">
            <div>
                <a-descriptions :title="userAddressTitle" bordered>
                    <a-descriptions-item label="用户名">{{ userAddressDetailData.userName }}</a-descriptions-item>
                    <a-descriptions-item label="收件人">{{ userAddressDetailData.consignee }}</a-descriptions-item>
                    <a-descriptions-item label="收件人电话号码">{{ userAddressDetailData.phone }}</a-descriptions-item>
                    <a-descriptions-item label="地址所在经度">{{ userAddressDetailData.longitude }}</a-descriptions-item>
                    <a-descriptions-item label="地址所在纬度">{{ userAddressDetailData.latitude }}</a-descriptions-item>
                    <a-descriptions-item label="地址类型">
                        <div v-if="userAddressDetailData.isDefault === 0">
                            <a-tag color="green">默认地址</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="pink">普通地址</a-tag>
                        </div>
                    </a-descriptions-item>
                    <a-descriptions-item label="所在地区">{{ userAddressDetailData.location }}</a-descriptions-item>
                    <a-descriptions-item label="详情地址">
                        {{ userAddressDetailData.detailedAddress }}
                    </a-descriptions-item>
                </a-descriptions>
            </div>
            <div class="mt-2" style="width: 100%;">
                <Map :mapDetailData="mapDetailData" />
            </div>
        </a-modal>
    </div>
    <!-- 删除用户收货地址 -->
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
import Map from '@/components/map.vue'
import { message } from 'ant-design-vue'
import { getAllMallUserName, getAllUserAddressPage } from '@/api/user/index'

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
const userAddressList = ref<any>()

const userAddressSearchPage = ref<any>({
    "currentPage": 1,
    "pageSize": 5,
    "userName": '',
    "consignee": ''
})

// 查询所有
const selectAllUserAddress = async () => {
    const res: any = await getAllUserAddressPage(userAddressSearchPage.value)
    if (res?.code === 0) {
        userAddressList.value = res?.data?.userAddressDetailDto
        pageTotal.value = res?.data?.total
        message.success('信息获取成功')
        loading.value = false
    } else {
        message.error("数据加载错误，请刷新重新加载")
    }
}

selectAllUserAddress()

// 分页
const onShowSizeChange = (current: number, pageSize: number) => {
    userAddressSearchPage.value.currentPage = current
    userAddressSearchPage.value.pageSize = pageSize
    selectAllUserAddress()
}

// 条件搜索
const selectSearchCart = () => {
    selectAllUserAddress()
}

// 重置
const resetSelectSearchMallUser = () => {
    userAddressSearchPage.value.consignee = ''
    userAddressSearchPage.value.userName = ''
    selectAllUserAddress()
}

const columns = [
    {
        title: '用户名',
        dataIndex: 'userName',
        key: 'userName',
        align: 'center'
    }, {
        title: '收件人',
        dataIndex: 'consignee',
        key: 'consignee',
        align: 'center'
    },
    {
        title: '收件人电话号码',
        dataIndex: 'phone',
        key: 'phone',
        align: 'center'
    },
    {
        title: '地址类型',
        dataIndex: 'isDefault',
        key: 'isDefault',
        align: 'center'
    },
    {
        title: '所在地区',
        dataIndex: 'location',
        key: 'location',
        align: 'center'
    },
    {
        title: '详情地址',
        dataIndex: 'detailedAddress',
        key: 'detailedAddress',
        align: 'center'
    },
    {
        title: '操作',
        dataIndex: 'action',
        key: 'action',
        align: 'center'
    }
]

// 地图组件数据
const mapDetailData = ref<any>({
    businessData: {},
    businessUserData: {},
    use: 1,
    mapData: {
        longitude: 0,
        latitude: 0,
        iconPath: 'https://eyouhui.oss-cn-hangzhou.aliyuncs.com/manage/address/user-address.png',
        calloutContent: '',
    }
})

// 收货地址数据详情控件
const userAddressDetailVisible = ref<boolean>(false)
// 收货地址详情数据
const userAddressDetailData = ref<any>()
const userAddressTitle = ref<string>('')
// 打开收货地址详情弹窗
const openUserAddressDetail = (e: any) => {
    mapDetailData.value.mapData.longitude = e.longitude
    mapDetailData.value.mapData.latitude = e.latitude
    mapDetailData.value.mapData.calloutContent = e.detailedAddress
    userAddressDetailData.value = e
    userAddressTitle.value = e.userName + '的' + e.consignee + '收货地址详情'
    userAddressDetailVisible.value = true
}

const deleteVisible = ref<boolean>(false)
const deleteUserTitle = ref<string>('')
// 删除用户收货地址
const deleteCart = (e: any) => {
    deleteUserTitle.value = '不允许删除-' + e.userName + '-' + e.consignee + '的收货地址记录'
    deleteVisible.value = true
}

</script>

<style scoped></style>