<template>
    <t-aside class="m-1">
        <t-menu v-model:expanded="expanded" :width="t_menu_width" theme="light" default-value="0" :collapsed="collapsed">
            <!-- <t-menu-item value="item?.value" to="/tree">
                <span>组织树</span>
            </t-menu-item> -->

            <MenuItem :menuDataList="menuDataList" />

            <template #operations>
                <t-button class="t-demo-collapse-btn" variant="text" shape="square" @click="changeCollapsed">
                    <template #icon><t-icon name="view-list" /></template>
                </t-button>
            </template>
        </t-menu>
    </t-aside>
</template>


<script lang="ts" setup>
import { ref } from 'vue'
import { userStore } from '@/store/modules/user'
import MenuItem from '@/layout/aside/menu/index.vue'
import { formatRouterTree } from '@/utils/router'

const collapsed = ref(false)
const t_menu_width = ref(['230px', '80px'])

const menuData = JSON.parse(JSON.stringify(userStore().getUserMenu))

// 左侧导航栏菜单数据集合
const menuDataList = formatRouterTree(menuData)

const changeCollapsed = () => {
    collapsed.value = !collapsed.value
}

const expanded = ref(['0'])

</script>

<style>
.t-layout__sider {
    position: relative;
    transition: all 0.2s;
    background: var(--td-bg-color-container);
    width: var(--td-bg-width-container);
}
</style>