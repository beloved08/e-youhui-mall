<template>
    <div class="">
        <t-tree :data="menuDataList" activable checkable expandParent line hover transition :onChange="changeTree" />
    </div>
</template>

<script setup lang="ts">
import { userStore } from '@/store/modules/user'
import { formatRouterTree } from '@/utils/router'

const menuData = JSON.parse(JSON.stringify(userStore().getUserMenu))

// 左侧导航栏菜单数据集合
const menuDataList = formatRouterTree(menuData)

// console.log(menuDataList)
const items = [
    {
        label: '第一段',
        children: [
            {
                label: '第一段',
            },
            {
                label: '第二段',
            },
        ],
    },
    {
        label: '第二段',
        children: [
            {
                label: '第一段',
            },
            {
                label: '第二段',
            },
        ],
    },
    {
        label: '第三段',
        children: [
            {
                label: '第一段',
            },
            {
                label: '第二段',
            },
        ],
    },
    {
        label: '第四段',
        children: [
            {
                label: '第一段',
            },
            {
                label: '第二段',
            },
        ],
    },
]

const changeTree = (e: any) => {
    console.log(e)
}
</script>

<style scoped>

</style>