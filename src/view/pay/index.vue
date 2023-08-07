<template>
    <div class="">
        <a-card title="支付方式">
            <div>
                <a-table bordered :loading="loading" :dataSource="payWayList" :columns="columns" />
            </div>
        </a-card>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { getAllPayWayList } from '@/api/pay/index'
import { message } from 'ant-design-vue'

const loading = ref<boolean>(false)
const payWayList = ref<any>()
const selectAllPayWayList = async () => {
    loading.value = true
    const res: any = await getAllPayWayList()

    if (res?.code === 0) {
        payWayList.value = res?.data
        message.success("数据获取成功")
        loading.value = false
    } else {
        message.error("数据获取失败，尝试刷新重试")
    }
}

selectAllPayWayList()

const columns = [
    {
        title: 'ID',
        dataIndex: 'id',
        key: 'id',
        align: 'center'
    },
    {
        title: '支付ID',
        dataIndex: 'payWayId',
        key: 'payWayId',
        align: 'center'
    },
    {
        title: '支付名称',
        dataIndex: 'payWayName',
        key: 'payWayName',
        align: 'center'
    },
    {
        title: '支付描述',
        dataIndex: 'payWayDesc',
        key: 'payWayDesc',
        align: 'center'
    }
]

</script>

<style scoped></style>