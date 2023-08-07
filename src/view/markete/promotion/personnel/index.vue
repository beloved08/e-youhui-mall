<template>
    <a-card title="条件筛选区域">
        <div>
            <a-form layout="inline">
                <a-form-item label="申请号码" name="promotionPeoplePhone">
                    <a-input v-model:value="promotionPeopleSearchPage.promotionPeoplePhone"></a-input>
                </a-form-item>
                <a-form-item label="状态" name="verify">
                    <a-select v-model:value="promotionPeopleSearchPage.verify" style="width: 150px" show-search
                        :allowClear="true" placeholder="用户名称">
                        <a-select-option value="-1">全部</a-select-option>
                        <a-select-option value="0">待审核</a-select-option>
                        <a-select-option value="1">审核通过</a-select-option>
                        <a-select-option value="2">审核不通过</a-select-option>
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
    <a-card title="促销人员列表数据" :bordered="false">
        <div>
            <a-table :columns="columns" :data-source="promotionPeopleList" bordered :indentSize="50" :pagination="false"
                :loading="tableLoading">
                <template #bodyCell="{ column, record }">

                    <template v-if="column.key === 'verify'">
                        <div v-if="record.verify === 0">
                            <a-tag color="#2db7f5">待审核</a-tag>
                        </div>
                        <div v-if="record.verify === 1">
                            <a-tag color="#87d068">审核通过</a-tag>
                        </div>
                        <div v-if="record.verify === 2">
                            <a-tag color="#f50">审核不通过</a-tag>
                        </div>
                    </template>
                    <template v-if="column.key === 'action'">
                        <a-button type="link" size="small" @click="detail(record)">详情</a-button>
                        <a-popconfirm title="您确定审核通过吗?" ok-text="确定" @confirm="approved(record, 1)" cancel-text="取消">
                            <template #icon><question-circle-outlined style="color: red" /></template>
                            <a-button type="link" size="small">通过审核</a-button>
                        </a-popconfirm>
                        <a-popconfirm title="您确定审核不通过吗?" ok-text="确定" @confirm="approved(record, 2)" cancel-text="取消">
                            <template #icon><question-circle-outlined style="color: red" /></template>
                            <a-button type="link" danger size="small">不通过审核</a-button>
                        </a-popconfirm>
                        <a-popconfirm title="您确定删除该申请记录?" ok-text="确定" @confirm="deletePromotionPeople(record)"
                            cancel-text="取消">
                            <template #icon><question-circle-outlined style="color: red" /></template>
                            <a-button type="text" danger size="small">删除</a-button>
                        </a-popconfirm>
                    </template>
                </template>
            </a-table>
        </div>
        <!-- 分页 -->
        <div class="mt-8 mb-4 flex justify-end">
            <a-pagination size="small" v-model:current="promotionPeopleSearchPage.currentPage"
                :page-size-options="pageSizeOptions" :hideOnSinglePage="false"
                :show-total="(pageTotal: number) => `共 ${ pageTotal } 条 `" show-size-changer
                @showSizeChange="onShowSizeChange" @change="onShowSizeChange"
                v-model:page-size="promotionPeopleSearchPage.pageSize" :total="pageTotal">
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
    <div>
        <a-modal v-model:visible="detailVisible" title="申请用户信息" :width="1000" :footer="false">
            <div v-if="userData">
                <a-descriptions bordered>
                    <a-descriptions-item label="账户名">{{ userData.realName
                    }}</a-descriptions-item>
                    <a-descriptions-item label="账户类型">{{ userData.nickName
                    }}</a-descriptions-item>
                    <a-descriptions-item label="电话号码">{{ userData.phone
                    }}</a-descriptions-item>
                    <a-descriptions-item label="邮箱">{{ userData.email }}</a-descriptions-item>
                    <a-descriptions-item label="性别">
                        <div v-if="userData.sex === 0">
                            <a-tag color="#2db7f5">男</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="#87d068">女</a-tag>
                        </div>
                    </a-descriptions-item>
                    <a-descriptions-item label="类型">
                        <div v-if="userData.type === 0">
                            <a-tag color="green">普通用户</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="pink">会员用户</a-tag>
                        </div>
                    </a-descriptions-item>
                    <a-descriptions-item label="状态">
                        <div v-if="userData.status === 0">
                            <a-tag color="cyan">正常</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="red">异常</a-tag>
                        </div>
                    </a-descriptions-item>
                    <a-descriptions-item label="地址">{{ userData.address
                    }}</a-descriptions-item>
                    <a-descriptions-item label="头像">
                        <a-image style="height: 50px; width: 70px;" :src="userData.avatar" />
                    </a-descriptions-item>
                </a-descriptions>
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
import { message } from 'ant-design-vue'
import { getPromotionPeoplePage, approvedPromotionPeople } from '@/api/marketing/index'
import { getMallUserByUserId } from '@/api/user/index'

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
const promotionPeopleList = ref<any>()

const promotionPeopleSearchPage = ref<any>({
    "currentPage": 1,
    "pageSize": 5,
    "promotionPeoplePhone": '',
    "verify": -1
})

// 获取所有用户的收藏信息
const selectPromotionPeoplePageList = async () => {
    const res: any = await getPromotionPeoplePage(promotionPeopleSearchPage.value)
    if (res?.code === 0) {
        promotionPeopleList.value = res?.data?.records
        pageTotal.value = res?.data?.total
        message.success('信息获取成功')
        loading.value = false
    } else {
        message.error("数据加载错误，请刷新重新加载")
    }
}

selectPromotionPeoplePageList()

// 分页
const onShowSizeChange = (current: number, pageSize: number) => {
    promotionPeopleSearchPage.value.currentPage = current
    promotionPeopleSearchPage.value.pageSize = pageSize
    selectPromotionPeoplePageList()
}

// 条件搜索
const selectSearchCart = () => {
    selectPromotionPeoplePageList()
}

// 重置
const resetSelectSearchMallUser = () => {
    promotionPeopleSearchPage.value.promotionPeoplePhone = ''
    promotionPeopleSearchPage.value.verify = -1
    selectPromotionPeoplePageList()
}

const columns = [
    {
        title: '用户ID',
        dataIndex: 'userId',
        key: 'userId',
        align: 'center'
    }, {
        title: '申请号码',
        dataIndex: 'promotionPeoplePhone',
        key: 'promotionPeoplePhone',
        align: 'center'
    },
    {
        title: '申请时间',
        dataIndex: 'applyTime',
        key: 'applyTime',
        align: 'center'
    },
    {
        title: '验证码',
        dataIndex: 'code',
        key: 'code',
        align: 'center'
    },
    {
        title: '状态',
        dataIndex: 'verify',
        key: 'verify',
        align: 'center'
    },
    {
        title: '操作',
        dataIndex: 'action',
        key: 'action',
        align: 'center'
    }
]

const detailVisible = ref<boolean>(false)
const userData = ref<any>()
const detail = async (e: any) => {
    const res: any = await getMallUserByUserId(e.userId)

    userData.value = res
    detailVisible.value = true

}

const approved = async (e: any, status: number) => {
    const res: any = await approvedPromotionPeople(e.promotionPeopleId, status)

    if (res?.code === 0) {
        message.success(res?.msg)
        selectPromotionPeoplePageList()
    } else {
        message.error(res?.msg)
    }
}

const deleteVisible = ref<boolean>(false)
const deleteUserTitle = ref<string>('')
// 删除用户收货地址
const deletePromotionPeople = (e: any) => {
    deleteUserTitle.value = '不允许删除-' + e.promotionPeoplePhone + '-的申请记录'
    deleteVisible.value = true
}

</script>

<style scoped></style>