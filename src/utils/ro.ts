const rp = [
    {
        "mid": "1",
        "pid": "0",
        "cid": true,
        "title": "系统首页",
        "icon": "dashboard",
        "value": "0",
        "path": "/dashboard"
    }, {
        "mid": "2",
        "pid": "0",
        "cid": true,
        "title": "用户管理",
        "icon": "user-avatar",
        "value": "1"
    }, {
        "mid": "3",
        "pid": "2",
        "cid": true,
        "title": "系统用户",
        "icon": "user-add",
        "value": "1-1"
    }, {
        "mid": "4",
        "pid": "3",
        "cid": false,
        "title": "超级管理员",
        "value": "1-1-1",
        "path": "/root",
        "name": "Root",
        "isLogin": true,
        "component": "user/sys/root"
    }, {
        "mid": "5",
        "pid": "3",
        "cid": false,
        "title": "商家管理员",
        "value": "1-1-2",
        "path": "/mall",
        "name": "Mall",
        "isLogin": true,
        "component": "user/sys/mall"
    }, {
        "mid": "6",
        "pid": "2",
        "cid": true,
        "title": "商城用户",
        "value": "1-2",
        "icon": "user-talk"
    }, {
        "mid": "7",
        "pid": "6",
        "cid": false,
        "title": "会员用户",
        "value": "1-2-1",
        "path": "/member",
        "name": "Member",
        "isLogin": true,
        "component": "user/mall/member"
    }, {
        "mid": "8",
        "pid": "6",
        "cid": false,
        "title": "普通用户",
        "value": "1-2-2",
        "path": "/ordinary",
        "name": "Ordinary",
        "isLogin": true,
        "component": "user/mall/ordinary"
    }, {
        "mid": "9",
        "pid": "6",
        "cid": false,
        "title": "搜索历史",
        "value": "1-2-3",
        "path": "/history",
        "name": "History",
        "isLogin": true,
        "component": "user/mall/history"
    }, {
        "mid": "10",
        "pid": "6",
        "cid": false,
        "title": "用户收藏",
        "value": "1-2-4",
        "path": "/collection",
        "name": "Collection",
        "isLogin": true,
        "component": "user/mall/collection"
    }, {
        "mid": "11",
        "pid": "6",
        "cid": false,
        "title": "意见反馈",
        "value": "1-2-5",
        "path": "/feedback",
        "name": "Feedback",
        "isLogin": true,
        "component": "user/mall/feedback"
    }, {
        "mid": "12",
        "pid": "0",
        "cid": true,
        "title": "商家管理",
        "icon": "root-list",
        "value": "2"
    }, {
        "mid": "13",
        "pid": "12",
        "cid": false,
        "title": "商家店铺",
        "value": "2-1",
        "path": "/shop",
        "name": "Shop",
        "isLogin": true,
        "component": "business/shop"
    }, {
        "mid": "14",
        "pid": "12",
        "cid": false,
        "title": "店铺员工",
        "value": "2-2",
        "path": "/staff",
        "name": "Staff",
        "isLogin": true,
        "component": "business/staff"
    }, {
        "mid": "15",
        "pid": "0",
        "cid": true,
        "title": "商品管理",
        "icon": "minus-rectangle",
        "value": "3"
    }, {
        "mid": "16",
        "pid": "15",
        "cid": false,
        "title": "商品分类",
        "value": "3-1",
        "path": "/commodity-classification",
        "name": "CommodityClassification",
        "isLogin": true,
        "component": "commodity/classification"
    }, {
        "mid": "17",
        "pid": "15",
        "cid": false,
        "title": "商品列表",
        "value": "3-2",
        "path": "/commodity-list",
        "name": "CommodityList",
        "isLogin": true,
        "component": "commodity/list"
    }, {
        "mid": "18",
        "pid": "15",
        "cid": false,
        "title": "商品上架",
        "value": "3-3",
        "path": "/commodity-put-shelf",
        "name": "CommodityPutShelf",
        "isLogin": true,
        "component": "commodity/put-shelf"
    }, {
        "mid": "19",
        "pid": "15",
        "cid": false,
        "title": "商品评论",
        "value": "3-4",
        "path": "/commodity-comment",
        "name": "CommodityComment",
        "isLogin": true,
        "component": "commodity/comment"
    }, {
        "mid": "20",
        "pid": "0",
        "cid": true,
        "title": "营销管理",
        "icon": "gift",
        "value": "4"
    }, {
        "mid": "21",
        "pid": "20",
        "cid": true,
        "title": "全民促销",
        "icon": "layers",
        "value": "4-1"
    }, {
        "mid": "22",
        "pid": "21",
        "cid": false,
        "title": "促销活动",
        "path": "/promotion-activity",
        "value": "4-1-1",
        "name": "PromotionActivity",
        "isLogin": true,
        "component": "markete/promotion/activity"
    }, {
        "mid": "23",
        "pid": "21",
        "cid": false,
        "title": "促销人员",
        "path": "/promotion-person",
        "value": "4-1-2",
        "name": "PromotionPerson",
        "isLogin": true,
        "component": "markete/promotion/personnel"
    }, {
        "mid": "24",
        "pid": "20",
        "cid": true,
        "title": "优惠券",
        "icon": "laptop",
        "value": "4-2"
    }, {
        "mid": "25",
        "pid": "24",
        "cid": false,
        "title": "通用优惠券",
        "path": "/coupon-currency",
        "value": "4-2-1",
        "name": "CouponCurrency",
        "isLogin": true,
        "component": "markete/coupon/currency"
    }, {
        "mid": "26",
        "pid": "24",
        "cid": false,
        "title": "商家优惠券",
        "path": "/coupon-business",
        "value": "4-2-2",
        "name": "CouponBusiness",
        "isLogin": true,
        "component": "markete/coupon/business"
    }, {
        "mid": "27",
        "pid": "20",
        "cid": false,
        "title": "限时秒杀",
        "path": "/seckill",
        "value": "4-3",
        "name": "Seckill",
        "isLogin": true,
        "component": "markete/seckill"
    }, {
        "mid": "28",
        "pid": "0",
        "cid": true,
        "title": "库存管理",
        "icon": "folder-open",
        "value": "5"
    }, {
        "mid": "29",
        "pid": "28",
        "cid": false,
        "title": "库存列表",
        "value": "5-1",
        "path": "/stock-list",
        "name": "StockList",
        "isLogin": true,
        "component": "stock/list"
    }, {
        "mid": "30",
        "pid": "28",
        "cid": false,
        "title": "商品入库",
        "value": "5-2",
        "path": "/stock-warehouse",
        "name": "StockWarehouse",
        "isLogin": true,
        "component": "stock/warehouse"
    }, {
        "mid": "31",
        "pid": "28",
        "cid": false,
        "title": "商品出库",
        "value": "5-3",
        "path": "/stock-outbound",
        "name": "StockOutbound",
        "isLogin": true,
        "component": "stock/outbound"
    }, {
        "mid": "32",
        "pid": "0",
        "cid": true,
        "title": "订单管理",
        "icon": "server",
        "value": "6"
    }, {
        "mid": "33",
        "pid": "32",
        "cid": true,
        "title": "订单类型",
        "icon": "secured",
        "value": "6-1"
    }, {
        "mid": "34",
        "pid": "33",
        "cid": false,
        "title": "商城订单",
        "value": "6-1-1",
        "path": "/order-mall",
        "name": "OrderMall",
        "isLogin": true,
        "component": "order/type/mall"
    }, {
        "mid": "35",
        "pid": "33",
        "cid": false,
        "title": "会员卡订单",
        "value": "6-1-2",
        "path": "/order-card",
        "name": "OrderCard",
        "isLogin": true,
        "component": "order/type/card"
    }, {
        "mid": "36",
        "pid": "32",
        "cid": true,
        "title": "订单处理",
        "icon": "wallet",
        "value": "6-2"
    }, {
        "mid": "37",
        "pid": "36",
        "cid": false,
        "title": "自提",
        "value": "6-2-1",
        "path": "/order-delivery",
        "name": "OrderDelivery",
        "isLogin": true,
        "component": "order/handle/delivery"
    }, {
        "mid": "38",
        "pid": "36",
        "cid": false,
        "title": "发货",
        "value": "6-2-2",
        "path": "/logistics-deliver",
        "name": "LogisticsDeliver",
        "isLogin": true,
        "component": "order/handle/logistics"
    }, {
        "mid": "39",
        "pid": "0",
        "cid": true,
        "title": "物流管理",
        "icon": "location",
        "value": "7"
    }, {
        "mid": "40",
        "pid": "39",
        "cid": false,
        "title": "第三方",
        "value": "7-1",
        "path": "/third-party",
        "name": "ThirdParty",
        "isLogin": true,
        "component": "logistics/third-party"
    }, {
        "mid": "41",
        "pid": "39",
        "cid": false,
        "title": "订单物流",
        "value": "7-2",
        "path": "/order-logistics",
        "name": "OrderLogistics",
        "isLogin": true,
        "component": "logistics/order-logistics"
    }, {
        "mid": "42",
        "pid": "0",
        "cid": false,
        "title": "支付管理",
        "value": "8",
        "icon": "chart-bubble",
        "path": "/pay",
        "name": "Pay",
        "isLogin": true,
        "component": "pay"
    }, {
        "mid": "43",
        "pid": "0",
        "cid": false,
        "title": "数据可视化",
        "icon": "chart",
        "value": "9",
        "path": "/data-chart",
        "name": "DataChart",
        "isLogin": true,
        "component": "data-visualization"
    }, {
        "mid": "44",
        "pid": "0",
        "cid": true,
        "title": "Nacos服务",
        "icon": "link",
        "value": "10",
        "path": "http://120.77.28.117:8848/nacos"
    }, {
        "mid": "45",
        "pid": "0",
        "cid": true,
        "title": "ElasticSearch服务",
        "icon": "star",
        "value": "11",
        "path": "https://www.baidu.com/"
    }, {
        "mid": "46",
        "pid": "0",
        "cid": true,
        "title": "RabbitMQ服务",
        "icon": "logo-android",
        "value": "12",
        "path": "https://www.baidu.com/"
    }

]

export default rp


const t = [
    {
        path: '/tree',
        name: 'OrganizationTree',
        meta: { isLogin: true },
        component: () => import('@/view/tree/index.vue')
    }, {
        path: '/dashboard',
        name: 'Home',
        meta: { isLogin: true },
        component: () => import('@/components/HelloWorld.vue')
    }, {
        path: '/root',
        name: 'Root',
        meta: { isLogin: true },
        // component: () => import('@/view/user/sys/root/index.vue')
        component: () => import('../view/user/sys/root/index.vue')
    }, {
        path: '/mall',
        name: 'Mall',
        meta: { isLogin: true },
        component: () => import('@/view/user/sys/mall/index.vue')
    },
    {
        path: '/member',
        name: 'Member',
        meta: { isLogin: true },
        component: () => import('@/view/user/mall/member/index.vue')
    },
    {
        path: '/collection',
        name: 'Collection',
        meta: { isLogin: true },
        component: () => import('@/view/user/mall/collection/index.vue')
    },
    {
        path: '/history',
        name: 'History',
        meta: { isLogin: true },
        component: () => import('@/view/user/mall/history/index.vue')
    },
    {
        path: '/ordinary',
        name: 'Ordinary',
        meta: { isLogin: true },
        component: () => import('@/view/user/mall/ordinary/index.vue')
    },
    {
        path: '/feedback',
        name: 'Feedback',
        meta: { isLogin: true },
        component: () => import('@/view/user/mall/feedback/index.vue')
    },

    {
        path: '/shop',
        name: 'Shop',
        meta: { isLogin: true },
        component: () => import('@/view/business/shop/index.vue')
    },
    {
        path: '/staff',
        name: 'Staff',
        meta: { isLogin: true },
        component: () => import('@/view/business/staff/index.vue')
    },
    {
        path: '/commodity-list',
        name: 'CommodityList',
        meta: { isLogin: true },
        component: () => import('@/view/commodity/list/index.vue')
    },
    {
        path: '/commodity-comment',
        name: 'CommodityComment',
        meta: { isLogin: true },
        component: () => import('@/view/commodity/comment/index.vue')
    },
    {
        path: '/commodity-put-shelf',
        name: 'CommodityPutShelf',
        meta: { isLogin: true },
        component: () => import('@/view/commodity/put-shelf/index.vue')
    },
    {
        path: '/commodity-classification',
        name: 'CommodityClassification',
        meta: { isLogin: true },
        component: () => import('@/view/commodity/classification/index.vue')
    },
    {
        path: '/seckill',
        name: 'Seckill',
        meta: { isLogin: true },
        component: () => import('@/view/markete/seckill/index.vue')
    },
    {
        path: '/promotion-activity',
        name: 'PromotionActivity',
        meta: { isLogin: true },
        component: () => import('@/view/markete/promotion/activity/index.vue')
    },
    {
        path: '/promotion-person',
        name: 'PromotionPerson',
        meta: { isLogin: true },
        component: () => import('@/view/markete/promotion/personnel/index.vue')
    },
    {
        path: '/coupon-currency',
        name: 'CouponCurrency',
        meta: { isLogin: true },
        component: () => import('@/view/markete/coupon/currency/index.vue')
    },
    {
        path: '/coupon-business',
        name: 'CouponBusiness',
        meta: { isLogin: true },
        component: () => import('@/view/markete/coupon/business/index.vue')
    },
    {
        path: '/stock-list',
        name: 'StockList',
        meta: { isLogin: true },
        component: () => import('@/view/stock/list/index.vue')
    },
    {
        path: '/stock-outbound',
        name: 'StockOutbound',
        meta: { isLogin: true },
        component: () => import('@/view/stock/outbound/index.vue')
    },
    {
        path: '/stock-warehouse',
        name: 'StockWarehouse',
        meta: { isLogin: true },
        component: () => import('@/view/stock/warehouse/index.vue')
    },
    {
        path: '/order-mall',
        name: 'OrderMall',
        meta: { isLogin: true },
        component: () => import('@/view/order/type/mall/index.vue')
    },
    {
        path: '/order-card',
        name: 'OrderCard',
        meta: { isLogin: true },
        component: () => import('@/view/order/type/card/index.vue')
    },
    {
        path: '/order-delivery',
        name: 'OrderDelivery',
        meta: { isLogin: true },
        component: () => import('@/view/order/handle/delivery/index.vue')
    },
    {
        path: '/logistics-deliver',
        name: 'LogisticsDeliver',
        meta: { isLogin: true },
        component: () => import('@/view/order/handle/logistics/index.vue')
    },
    {
        path: '/third-party',
        name: 'ThirdParty',
        meta: { isLogin: true },
        component: () => import('@/view/logistics/third-party/index.vue')
    },
    {
        path: '/order-logistics',
        name: 'OrderLogistics',
        meta: { isLogin: true },
        component: () => import('@/view/logistics/order-logistics/index.vue')
    },
    {
        path: '/pay',
        name: 'Pay',
        meta: { isLogin: true },
        component: () => import('@/view/pay/index.vue')
    },
    {
        path: '/data-chart',
        name: 'DataChart',
        meta: { isLogin: true },
        component: () => import('@/view/data-visualization/index.vue')
    }

]
