<template>
    <div class="w-80 flex flex-col overflow-y-auto">
        <!-- 商家入驻 -->
        <router-link to="/shop" v-if="merchantSettlementChannelData.length > 0">
            <div class="flex flex-row w-full h-20 hover:bg-gray-100 p-4 -mt-2 mb-4"
                v-for="(item, index) in merchantSettlementChannelData" :key="index">
                <div class="flex flex-col">
                    <div class="w-10 h-10">
                        <img style="width: 100%;height:100%;border-radius: 50%;" :src="item.shopAvatar" alt="">
                    </div>
                </div>
                <div class="flex flex-col w-4/5 ml-4">
                    <div class="-mt-1 font-bold text-blue-400">商家入驻申请</div>
                    <div class="text-xs mt-1  text-pink-400">{{ item.businessName }}-{{
                        item.shopName }}提交了入驻申请，请尽快审核处理</div>
                </div>
            </div>
        </router-link>
        <!-- 待发货订单 -->
        <router-link to="/order-mall" v-if="shippedOrderData.length > 0">
            <div class="flex flex-row w-full h-20 hover:bg-gray-100 p-4 -mt-2 mb-4"
                v-for="(item, index) in shippedOrderData" :key="index">
                <div class="flex flex-col">
                    <div class="w-10 h-10">
                        <img style="width: 100%;height:100%;border-radius: 50%;"
                            src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/manage/notice/shipped-1.png" alt="">
                    </div>
                </div>
                <div class="flex flex-col w-4/5 ml-4">
                    <div class="-mt-1 font-bold text-red-400">待发货订单</div>
                    <div class="text-xs mt-1 text-green-400">您有新的订单，请尽快处理哦！订单号：{{ item.orderNumber }}</div>
                </div>
            </div>
        </router-link>
        <router-link to="/promotion-person" v-if="nationalPromotionData.length > 0">
            <div class="flex flex-row w-full h-20 hover:bg-gray-100 p-4 -mt-2 mb-4"
                v-for="(item, index) in nationalPromotionData" :key="index">
                <div class="flex flex-col">
                    <div class="w-10 h-10">
                        <img style="width: 100%;height:100%;border-radius: 50%;"
                            src="https://eyouhui.oss-cn-hangzhou.aliyuncs.com/manage/notice/personnel-application.png"
                            alt="">
                    </div>
                </div>
                <div class="flex flex-col w-4/5 ml-4">
                    <div class="-mt-1 font-bold text-red-400">促销人员申请</div>
                    <div class="text-xs mt-1 text-green-400">您有新的全民促销人员申请，请尽快处理哦！，电话号码：{{ item.phone }}</div>
                </div>
            </div>
        </router-link>
        <div v-else>
            <a-empty>
                <template #description>
                    <span class="text-blue-400">暂无通知消息</span>
                </template>
            </a-empty>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { noticeStore } from '@/store/modules/notice'

const state = noticeStore()
const merchantSettlementChannelData = ref<any>(state.getMerchantSettlementData)
const shippedOrderData = ref<any>(state.shippedOrderData)
const nationalPromotionData = ref<any>(state.nationalPromotionData)

const str: any = setInterval(() => {
    noticeStore().$reset()
    clearInterval(str)
}, 1 * 60 * 60 * 1000)

</script>

<style scoped></style>