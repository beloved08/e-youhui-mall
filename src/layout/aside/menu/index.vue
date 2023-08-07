<template>
    <template v-for="(item, index) in menuDataList" :key="index">
        <!-- 如果当前菜单有下级,循环下级菜单 -->
        <t-submenu :value="item?.value" v-if="item?.children && item?.children.length > 0">
            <template #icon>
                <t-icon :name="item?.icon" />
            </template>
            <template #title>
                <span>{{ item?.label }}</span>
            </template>

            <!-- 递归自身 -->
            <MenuItem :menuDataList="item?.children" :key="item?.id" />
        </t-submenu>

        <!-- 没有下级菜单 -->
        <t-menu-item v-else-if="item?.value === '10' || item?.value === '11' || item?.value === '12'" :index="item?.id"
            :value="item?.value" @click="toAddress(item?.path)">
            <template #icon>
                <t-icon :name="item?.icon" />
            </template>
            {{ item?.label }}
        </t-menu-item>
        <t-menu-item v-else :index="item?.id" :value="item?.value" :to="item?.path">
            <template #icon>
                <t-icon :name="item?.icon" />
            </template>
            {{ item?.label }}
        </t-menu-item>
    </template>
</template>

<script lang="ts">
export default {
    name: 'MenuItem',
}
</script>

<script lang="ts" setup>
import { defineProps, toRefs } from 'vue'

const props = defineProps({
    menuDataList: {}
})

const menuDataList: any = toRefs(props).menuDataList

const toAddress = (url: string) => {
    window.open(url)
}
</script>

<style scoped></style>