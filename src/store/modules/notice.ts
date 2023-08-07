import { defineStore } from 'pinia'

const MAX_ITEMS = 5

export const noticeStore = defineStore('notice', {
    state: (): any => {
        return {
            // 每一种类型的消息缓存的最大消息数
            noticeStorageNumber: 3,
            // 商家入驻消息
            merchantSettlementData: [],
            // 待发货订单
            shippedOrderData: [],
            // 小程序用户申请成为促销人员
            nationalPromotionData: []
        }
    },

    // 开启数据缓存
    persist: {
        enabled: true,
        strategies: [
            {
                storage: localStorage,
                paths: ['merchantSettlementData', 'shippedOrderData','nationalPromotionData']
            }
        ]
    },
    getters: {
        // 获取最大数量
        getNoticeStorageNumber (): any {
            return this.noticeStorageNumber
        },
        // 获取商家入驻消息
        getMerchantSettlementData (): any {
            return this.merchantSettlementData
        },
        getShippedOrderData (): any {
            return this.shippedOrderData
        },
        getNationalPromotionData():any{
            return this.nationalPromotionData
        }
    },
    actions: {
        saveMerchantSettlementData (data: any): any {
            this.merchantSettlementData.push(data)
        },
        saveShippedOrderData (data: any): any {
            this.shippedOrderData.push(data)
        },
        saveNationalPromotionData(data:any):any {
            this.nationalPromotionData.push(data)
        }
    }
})
