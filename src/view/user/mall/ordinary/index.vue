<template>
    <a-card title="搜索区域">
        <div>
            <a-form layout="inline" :model="mallUserPage">
                <a-form-item class="w-48" label="账户名">
                    <a-input v-model:value="mallUserPage.userName">
                    </a-input>
                </a-form-item>
                <a-form-item class="w-48" label="电话号码">
                    <a-input v-model:value="mallUserPage.phone">
                    </a-input>
                </a-form-item>
                <a-form-item class="w-48" label="用户类型">
                    <a-select v-model:value="mallUserPage.type">
                        <a-select-option value="0">普通用户</a-select-option>
                        <a-select-option value="1">会员用户</a-select-option>
                    </a-select>
                </a-form-item>
                <a-form-item class="w-48" label="用户状态">
                    <a-select v-model:value="mallUserPage.status">
                        <a-select-option value="0">正常</a-select-option>
                        <a-select-option value="1">异常</a-select-option>
                    </a-select>
                </a-form-item>
                <a-form-item class="w-48" label="性别">
                    <a-select v-model:value="mallUserPage.sex">
                        <a-select-option value="0">男</a-select-option>
                        <a-select-option value="1">女</a-select-option>
                    </a-select>
                </a-form-item>
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
            </a-form>
        </div>
    </a-card>
    <a-card title="用户列表信息" :bordered="false">
        <div>
            <a-table :columns="columns" :data-source="mallUserDataList" :indentSize="50" :pagination="false"
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
                        <a-button type="link" size="small" @click="openUserDetail(record)">详情</a-button>
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
            <a-pagination size="small" v-model:current="mallUserPage.currentPage" :page-size-options="pageSizeOptions"
                :hideOnSinglePage="false" :show-total="(pageTotal: number) => `共 ${ pageTotal } 条 `" show-size-changer
                @showSizeChange="onShowSizeChange" @change="onShowSizeChange" v-model:page-size="mallUserPage.pageSize"
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
        <a-modal v-model:visible="userDetailVisible" :footer="false" :width="1000">
            <div>
                <a-descriptions :title="userDetailData.realName" bordered>
                    <a-descriptions-item label="账户名">{{ userDetailData.realName }}</a-descriptions-item>
                    <a-descriptions-item label="账户类型">{{ userDetailData.nickName }}</a-descriptions-item>
                    <a-descriptions-item label="电话号码">{{ userDetailData.phone }}</a-descriptions-item>
                    <a-descriptions-item label="邮箱">{{ userDetailData.email }}</a-descriptions-item>
                    <a-descriptions-item label="性别">
                        <div v-if="userDetailData.sex === 0">
                            <a-tag color="#2db7f5">男</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="#87d068">女</a-tag>
                        </div>
                    </a-descriptions-item>
                    <a-descriptions-item label="类型">
                        <div v-if="userDetailData.type === 0">
                            <a-tag color="green">普通用户</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="pink">会员用户</a-tag>
                        </div>
                    </a-descriptions-item>
                    <a-descriptions-item label="状态">
                        <div v-if="userDetailData.status === 0">
                            <a-tag color="cyan">正常</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="red">异常</a-tag>
                        </div>
                    </a-descriptions-item>
                    <a-descriptions-item label="地址">{{ userDetailData.address }}</a-descriptions-item>
                    <a-descriptions-item label="头像">
                        <a-image style="height: 50px; width: 70px;" :src="userDetailData.avatar" />
                    </a-descriptions-item>
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
import { getMallUserPage } from '@/api/user/index'
import { message } from 'ant-design-vue'

// 控制表格加载动画开关
const loading = ref<boolean>(true)
// 表格加载动画自定义设置
const tableLoading = ref<any>({
    size: 'large',
    delay: 100,
    spinning: loading
})

const mallUserPage = ref<any>({
    "currentPage": 1,
    "pageSize": 5,
    "userName": '',
    "phone": '',
    "sex": '',
    "status": '',
    "type": '',
})

const pageTotal = ref<number>()
const pageSizeOptions = ref<string[]>(['5', '10', '20', '50', '100'])

const mallUserDataList = ref<any>()

// 获取所有普通用户列表信息
const selectOrdinaryMallUserList = async () => {
    const res: any = await getMallUserPage(mallUserPage.value)
    if (res?.code === 0) {
        mallUserDataList.value = res?.data?.records
        pageTotal.value = res?.data?.total
        message.success('用户信息获取成功')
        loading.value = false
    } else {
        message.error("数据加载错误，请刷新重新加载")
    }
}

selectOrdinaryMallUserList()

// 条件搜索
const selectSearchMallUser = () => {
    selectOrdinaryMallUserList()
}

// 重置
const resetSelectSearchMallUser = () => {
    mallUserPage.value.userName = ''
    mallUserPage.value.phone = ''
    mallUserPage.value.sex = ''
    mallUserPage.value.status = ''
    mallUserPage.value.type = ''
    selectOrdinaryMallUserList()
}

// 分页
const onShowSizeChange = (current: number, pageSize: number) => {
    mallUserPage.value.currentPage = current
    mallUserPage.value.pageSize = pageSize
    selectOrdinaryMallUserList()
}

const columns = [
    {
        title: '账户名',
        dataIndex: 'realName',
        key: 'realName',
        align: 'center'
    }, {
        title: '账户类型',
        dataIndex: 'nickName',
        key: 'nickName',
        align: 'center'
    },
    {
        title: '电话号码',
        dataIndex: 'phone',
        key: 'phone',
        align: 'center'
    },
    {
        title: '邮箱',
        dataIndex: 'email',
        key: 'email',
        align: 'center'
    },
    {
        title: '性别',
        dataIndex: 'sex',
        key: 'sex',
        align: 'center'
    },
    {
        title: '头像',
        dataIndex: 'avatar',
        key: 'avatar',
        align: 'center'
    },
    {
        title: '地址',
        dataIndex: 'address',
        key: 'address',
        align: 'center'
    },
    {
        title: '类型',
        dataIndex: 'type',
        key: 'type',
        align: 'center'
    },
    {
        title: '状态',
        dataIndex: 'status',
        key: 'status',
        align: 'center'
    },
    {
        title: '操作',
        dataIndex: 'action',
        key: 'action',
        align: 'center'
    }
]

// 用户详情
const userDetailVisible = ref<boolean>(false)
// 用户详情数据
const userDetailData = ref<any>()
// 打开用户详情弹窗
const openUserDetail = (e: any) => {
    userDetailData.value = e
    userDetailVisible.value = true
}

const deleteVisible = ref<boolean>(false)
const deleteUserTitle = ref<string>('')
// 删除用户
const deleteUser = (e: any) => {
    deleteUserTitle.value = e.nickName + '-' + e.realName + '，该用户不允许删除'
    deleteVisible.value = true
}

</script>

<style scoped></style>