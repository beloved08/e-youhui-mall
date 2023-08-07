<template>
    <t-card title="物流公司信息" :bordered="false">
        <a-table :columns="columns" :data-source="LogisticsCompanyList" bordered :loading="tableLoading">
            <template #bodyCell="{ column, record }">
                <template v-if="column.key === 'logo'">
                    <a-image style="height: 50px; width: 80px;" :src="record.logo" />
                </template>
                <template v-if="column.key === 'isForbidden'">
                    <a-tag v-if="record.isForbidden === 1" color="#f50">已禁用</a-tag>
                    <a-tag v-else color="#2db7f5">正常</a-tag>
                </template>
                <template v-if="column.key === 'website'">
                    <t-link theme="primary" :href="record.website" target="_blank">
                        <link-icon slot="prefix-icon"></link-icon>
                        {{ record.website }}
                    </t-link>
                </template>
                <template v-if="column.key === 'action'">
                    <a-popconfirm title="此操作将恢复该物流公司的使用，您确定继续吗？" ok-text="确定" cancel-text="取消"
                        @confirm="reForbiddenisLogisticsCompanyItem">
                        <a-button :disabled="record.isForbidden === 0" type="link"
                            @click="forbidden(record)">解除禁用</a-button>
                    </a-popconfirm>
                    <a-popconfirm title="此操作将禁用该物流公司的使用，您确定继续吗？" ok-text="确定" cancel-text="取消"
                        @confirm="forbiddenisLogisticsCompanyItem">
                        <a-button :disabled="record.isForbidden === 1" type="link" danger
                            @click="forbidden(record)">禁用</a-button>
                    </a-popconfirm>
                </template>
            </template>
        </a-table>
    </t-card>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { getLogisticsCompany, forbiddenisLogisticsCompany } from '@/api/logistics/index'
import { message } from 'ant-design-vue'
import { LinkIcon } from 'tdesign-icons-vue-next'

const LogisticsCompanyList = ref<any>()
// 控制表格加载动画开关
const loading = ref<boolean>(false)
// 表格加载动画自定义设置
const tableLoading = ref<any>({
    size: 'large',
    delay: 100,
    spinning: loading
})


const getLogisticsCompanyList = async () => {
    loading.value = true
    const res: any = await getLogisticsCompany()
    if (res?.code === 0) {
        LogisticsCompanyList.value = res?.data
        loading.value = false
        message.success("物流公司信息获取成功")
    }
}

getLogisticsCompanyList()


const columns = [
    {
        title: '物流公司名称',
        dataIndex: 'logisticsCompanyName',
        key: 'logisticsCompanyName',
        align: 'center'
    }, {
        title: '物流公司logo',
        dataIndex: 'logo',
        key: 'logo',
        align: 'center'
    }, {
        title: '物流公司客服电话',
        dataIndex: 'phone',
        key: 'phone',
        align: 'center'
    }, {
        title: '是否被禁用',
        dataIndex: 'isForbidden',
        key: 'isForbidden',
        align: 'center'
    }, {
        title: '物流公司官网',
        dataIndex: 'website',
        key: 'website',
        align: 'center'
    }, {
        title: '操作',
        dataIndex: 'action',
        key: 'action',
        align: "center",
        width: 200
    }
]

const forbiddenId = ref<any>()
const forbidden = (e: any) => {
    forbiddenId.value = e
}

// 禁用
const forbiddenisLogisticsCompanyItem = async () => {
    const res: any = await forbiddenisLogisticsCompany(forbiddenId.value.logisticsCompanyId, Number(1))

    console.log(res)
    if (res?.code === 0) {
        message.success(forbiddenId.value.logisticsCompanyName + "禁用成功")
        getLogisticsCompanyList()
    } else {
        message.error(forbiddenId.value.logisticsCompanyName + "禁用失败")
    }
}

// 解除禁用
const reForbiddenisLogisticsCompanyItem = async () => {
    const res: any = await forbiddenisLogisticsCompany(forbiddenId.value.logisticsCompanyId, Number(0))

    console.log(res)
    if (res?.code === 0) {
        message.success(forbiddenId.value.logisticsCompanyName + "禁用解除成功")
        getLogisticsCompanyList()
    } else {
        message.error(forbiddenId.value.logisticsCompanyName + "禁用解除失败")
    }
}


</script>

<style scoped></style>