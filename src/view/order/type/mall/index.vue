<template>
    <a-card title="条件筛选区域">
        <div>
            <a-form layout="inline">
                <a-form-item label="订单号" name="orderNumber" style="margin-bottom:20px;">
                    <a-input v-model:value="orderPage.orderNumber"></a-input>
                </a-form-item>
                <a-form-item label="用户名称" name="userName">
                    <!-- <a-input v-model:value="commoditySearchForm.classificationName"></a-input> -->
                    <a-select v-model:value="orderPage.userName" style="width: 150px" show-search :allowClear="true"
                        placeholder="用户名称">
                        <a-select-option v-if="userNameList" v-for="(item, index) in userNameList" :value="item">{{
                            item }}</a-select-option>
                    </a-select>
                </a-form-item>
                <a-form-item label="订单状态" name="orderStatus">
                    <!-- <a-input v-model:value="commoditySearchForm.classificationName"></a-input> -->
                    <a-select v-model:value="orderPage.orderStatus" style="width: 150px" show-search :allowClear="true"
                        placeholder="订单状态">
                        <!-- (0：待付款，1：待发货，2：待收货，3：已完成，4：售后) -->
                        <a-select-option value="-1">全部</a-select-option>
                        <a-select-option value="0">待付款</a-select-option>
                        <a-select-option value="1">待发货</a-select-option>
                        <a-select-option value="2">待收货</a-select-option>
                        <a-select-option value="3">已完成</a-select-option>
                        <a-select-option value="4">售后</a-select-option>
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
    <a-card title="用户订单列表数据" :bordered="false">
        <div>
            <a-table :columns="columns" :data-source="userCollectList" bordered :indentSize="50" :pagination="false"
                :loading="tableLoading">
                <template #bodyCell="{ column, record }">
                    <template v-if="column.key === 'orderStatus'">
                        <a-tag v-if="record.orderStatus === 0" color="#f50">待付款</a-tag>
                        <a-tag v-if="record.orderStatus === 1" color="#2db7f5">待发货</a-tag>
                        <a-tag v-if="record.orderStatus === 2" color="#87d068">待收货</a-tag>
                        <a-tag v-if="record.orderStatus === 3" color="#108ee9">已完成</a-tag>
                        <a-tag v-if="record.orderStatus === 4" color="#3fc1c9">售后</a-tag>
                    </template>
                    <template v-if="column.key === 'memberDiscount'">
                        <a-tag color="blue">{{ record.memberDiscount }}</a-tag>
                    </template>
                    <template v-if="column.key === 'action'">
                        <div class="flex flex-row">
                            <a-tooltip color="cyan">
                                <template #title>订单详情</template>
                                <a-button type="link" size="medium" @click="orderDetail(record)">
                                    <template #icon>
                                        <t-icon name="error-circle" />
                                    </template>
                                </a-button>
                            </a-tooltip>
                            <a-dropdown placement="topRight">
                                <a-button type="link" size="medium">
                                    <template #icon>
                                        <t-icon name="edit" />
                                    </template>
                                </a-button>
                                <template #overlay>
                                    <a-menu>
                                        <a-menu-item>
                                            <a-button :disabled="record.orderStatus != 1" type="link" danger
                                                @click="shipImmediately(record)">立即发货</a-button>
                                        </a-menu-item>
                                    </a-menu>
                                </template>
                            </a-dropdown>
                        </div>
                    </template>
                </template>
            </a-table>
        </div>
        <!-- 分页 -->
        <div class="mt-8 mb-4 flex justify-end">
            <a-pagination size="small" v-model:current="orderPage.currentPage" :page-size-options="pageSizeOptions"
                :hideOnSinglePage="false" :show-total="(pageTotal: number) => `共 ${ pageTotal } 条 `" show-size-changer
                @showSizeChange="onShowSizeChange" @change="onShowSizeChange" v-model:page-size="orderPage.pageSize"
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
    <!-- 订单详情 -->
    <div>
        <a-modal v-model:visible="orderDetailVisible" title="订单详情" :footer="false" :width="1450">
            <div>
                <a-collapse v-model:activeKey="orderDetailActiveKey" accordion>
                    <a-collapse-panel key="1" header="订单用户">
                        <div>
                            <a-descriptions bordered>
                                <a-descriptions-item label="用户名">{{ orderDetailUser.realName }}</a-descriptions-item>
                                <a-descriptions-item label="昵称">{{ orderDetailUser.nickName }}</a-descriptions-item>
                                <a-descriptions-item label="电话号码">{{ orderDetailUser.phone }}</a-descriptions-item>
                                <a-descriptions-item label="性别">
                                    <div v-if="orderDetailUser.sex === 0">
                                        <a-tag color="#2db7f5">男</a-tag>
                                    </div>
                                    <div v-else>
                                        <a-tag color="#87d068">女</a-tag>
                                    </div>
                                </a-descriptions-item>
                                <a-descriptions-item label="类型">
                                    <a-tag color="pink" v-if="orderDetailUser.type === 0">普通用户</a-tag>
                                    <a-tag color="pink" v-else>会员用户</a-tag>
                                </a-descriptions-item>
                                <a-descriptions-item label="Email">{{ orderDetailUser.email }}</a-descriptions-item>
                                <a-descriptions-item label="状态">
                                    <a-tag color="orange" v-if="orderDetailUser.status === 0">正常</a-tag>
                                    <a-tag color="orange" v-else>异常</a-tag>
                                </a-descriptions-item>
                                <a-descriptions-item label="开通会员时间">{{ orderDetailUser.purchaseVipTime
                                }}</a-descriptions-item>
                                <a-descriptions-item label="地址">{{ orderDetailUser.address }}</a-descriptions-item>
                                <a-descriptions-item label="头像">
                                    <a-image style="height: 50px; width: 70px;" :src="orderDetailUser.avatar" />
                                </a-descriptions-item>
                            </a-descriptions>
                        </div>
                    </a-collapse-panel>
                    <a-collapse-panel key="2" header="订单商品">
                        <div>
                            <div v-if="orderDetailCommodity" v-for="(item, index) in orderDetailCommodity" :key="index"
                                class="mb-4">
                                <a-descriptions :column="5" size="small" bordered :title="item.commodity.commodityName">
                                    <a-descriptions-item label="购买商品数量">
                                        <span>{{ item.purchaseQuantity }}</span>
                                        <span>{{ item.commodity.meterCompany }}</span>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="所属商家">{{ item.business.calloutContent
                                    }}</a-descriptions-item>
                                    <a-descriptions-item label="商品状态">
                                        <div v-if="item.commodity.commodityStatus === 0">
                                            <a-tag color="pink">销售中</a-tag>
                                        </div>
                                        <div v-else>
                                            <a-tag color="cyan">仓库中</a-tag>
                                        </div>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="商品库存">
                                        <div v-if="Number(item.commodity.commodityStock) <= 10">
                                            <a-tooltip placement="top" color="red">
                                                <template #title>
                                                    <span>库存警告，即将无货，请即刻进货</span>
                                                </template>
                                                <a-tag color="#f50">
                                                    {{ item.commodity.commodityStock }}
                                                </a-tag>
                                            </a-tooltip>
                                        </div>
                                        <div
                                            v-else-if="Number(item.commodity.commodityStock) >= 10 && Number(item.commodity.commodityStock) <= 100">
                                            <a-tooltip placement="top" color="purple">
                                                <template #title>
                                                    <span>库存预警，请注意进货</span>
                                                </template>
                                                <a-tag color="orange">
                                                    {{ item.commodity.commodityStock }}
                                                </a-tag>
                                            </a-tooltip>
                                        </div>
                                        <div v-else>
                                            <a-tooltip placement="top" color="blue">
                                                <template #title>
                                                    <span>库存非常充裕，无需进货</span>
                                                </template>
                                                <a-tag color="success">
                                                    {{ item.commodity.commodityStock }}
                                                </a-tag>
                                            </a-tooltip>
                                        </div>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="商品类型">
                                        <div v-if="item.commodity.commodityStatus === 0">
                                            <a-tag color="green">虚拟商品</a-tag>
                                        </div>
                                        <div v-else>
                                            <a-tag color="purple">实物商品</a-tag>
                                        </div>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="销售模式">
                                        <div v-if="item.commodity.salesModel === 0">
                                            <a-tag color="orange">零售</a-tag>
                                        </div>
                                        <div v-else>
                                            <a-tag color="blue">批发</a-tag>
                                        </div>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="零售价/元">
                                        <a-tag color="#2db7f5">{{ item.commodity.retailPrice }}</a-tag>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="批发价/元">
                                        <a-tag color="#87d068">{{ item.commodity.wholesalePrice }}</a-tag>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="商品重量/kg">
                                        <a-tag color="#2db7f5">{{ item.commodity.commodityWeight }}</a-tag>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="计量单位">
                                        <a-tag color="#87d068">{{ item.commodity.meterCompany }}</a-tag>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="是否推荐为新品">
                                        <div v-if="item.commodity.commodityRecommend === 0">
                                            <a-tag color="#87d068">推荐</a-tag>
                                        </div>
                                        <div v-else>
                                            <a-tag color="#108ee9">不推荐</a-tag>
                                        </div>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="添加时间">
                                        <a-tag color="blue">{{ item.commodity.createTime }}</a-tag>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="上架时间">
                                        <a-tag color="blue">{{ item.commodity.putShelfTime }}</a-tag>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="下架时间">
                                        <a-tag color="blue">{{ item.commodity.offShelfTime }}</a-tag>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="下架原因">{{ item.commodity.offShelfReason
                                    }}</a-descriptions-item>
                                    <a-descriptions-item label="商品图片">
                                        <div v-if="item.commodity.commodityImageUrl">
                                            <a-image style="height: 50px; width: 70px;"
                                                :src="item.commodity.commodityImageUrl" />
                                        </div>
                                        <div v-else>
                                            暂无
                                        </div>
                                    </a-descriptions-item>
                                    <a-descriptions-item v-if="item.merchantCoupon != null" label="商家优惠券名称">{{
                                        item.merchantCoupon.merchantCouponName }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.merchantCoupon != null" label="商家优惠券扣减额度">{{
                                        item.merchantCoupon.discountAmount
                                    }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.merchantCoupon != null" label="商家优惠券满多少可用">{{
                                        item.merchantCoupon.fullAvailable
                                    }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.merchantCoupon != null" label="商家优惠券数量">{{
                                        item.merchantCoupon.totalQuantity
                                    }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.merchantCoupon != null" label="商家优惠券开始时间">{{
                                        item.merchantCoupon.startTime
                                    }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.merchantCoupon != null" label="商家优惠券结束时间">{{
                                        item.merchantCoupon.endTime
                                    }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.universalCoupon != null" label="通用优惠券名称">{{
                                        item.universalCoupon.merchantCouponName
                                    }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.universalCoupon != null" label="通用优惠券扣减额度">{{
                                        item.universalCoupon.discountAmount
                                    }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.universalCoupon != null" label="通用优惠券满多少可用">{{
                                        item.universalCoupon.fullAvailable
                                    }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.universalCoupon != null" label="通用优惠券数量">{{
                                        item.universalCoupon.totalQuantity
                                    }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.universalCoupon != null" label="通用优惠券开始时间">{{
                                        item.universalCoupon.startTime
                                    }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.universalCoupon != null" label="通用优惠券结束时间">{{
                                        item.universalCoupon.endTime
                                    }}</a-descriptions-item>
                                    <a-descriptions-item label="商品描述">{{ item.commodity.commodityDescribe
                                    }}</a-descriptions-item>
                                </a-descriptions>
                            </div>
                        </div>
                    </a-collapse-panel>
                    <a-collapse-panel key="3" header="订单收货地址">
                        <div>
                            <a-descriptions bordered>
                                <a-descriptions-item label="收件人">{{ orderDetailUserAddress.consignee
                                }}</a-descriptions-item>
                                <a-descriptions-item label="电话号码">{{ orderDetailUserAddress.phone }}</a-descriptions-item>
                                <a-descriptions-item label="地区">{{ orderDetailUserAddress.location }}</a-descriptions-item>
                                <a-descriptions-item label="经度">{{ orderDetailUserAddress.longitude }}</a-descriptions-item>
                                <a-descriptions-item label="纬度">{{ orderDetailUserAddress.latitude }}</a-descriptions-item>
                                <a-descriptions-item label="详细地址">{{ orderDetailUserAddress.detailedAddress
                                }}</a-descriptions-item>
                            </a-descriptions>
                        </div>
                    </a-collapse-panel>
                </a-collapse>
            </div>
        </a-modal>
    </div>
    <!-- 发货抽屉处理 -->
    <div>
        <a-drawer v-model:visible="shipDrawerVisible" class="custom-class" :width="1500" style="color: red" title="发货审核处理"
            placement="left">
            <div>
                <a-descriptions :column="5" title="订单用户信息" bordered>
                    <a-descriptions-item label="用户名">{{ orderDetailUser.realName
                    }}</a-descriptions-item>
                    <a-descriptions-item label="昵称">{{ orderDetailUser.nickName }}</a-descriptions-item>
                    <a-descriptions-item label="电话号码">{{ orderDetailUser.phone }}</a-descriptions-item>
                    <a-descriptions-item label="性别">
                        <div v-if="orderDetailUser.sex === 0">
                            <a-tag color="#2db7f5">男</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="#87d068">女</a-tag>
                        </div>
                    </a-descriptions-item>
                    <a-descriptions-item label="类型">
                        <a-tag color="pink" v-if="orderDetailUser.type === 0">普通用户</a-tag>
                        <a-tag color="pink" v-else>会员用户</a-tag>
                    </a-descriptions-item>
                    <a-descriptions-item label="Email">{{ orderDetailUser.email }}</a-descriptions-item>
                    <a-descriptions-item label="状态">
                        <a-tag color="orange" v-if="orderDetailUser.status === 0">正常</a-tag>
                        <a-tag color="orange" v-else>异常</a-tag>
                    </a-descriptions-item>
                    <a-descriptions-item label="开通会员时间">{{ orderDetailUser.purchaseVipTime
                    }}</a-descriptions-item>
                    <a-descriptions-item label="地址">{{ orderDetailUser.address }}</a-descriptions-item>
                    <a-descriptions-item label="头像">
                        <a-image style="height: 50px; width: 70px;" :src="orderDetailUser.avatar" />
                    </a-descriptions-item>
                </a-descriptions>
            </div>
            <div>
                <a-descriptions :column="10" bordered title="收货地址">
                    <a-descriptions-item label="收件人">{{ orderDetailUserAddress.consignee
                    }}</a-descriptions-item>
                    <a-descriptions-item label="电话号码">{{ orderDetailUserAddress.phone }}</a-descriptions-item>
                    <a-descriptions-item label="地区">{{ orderDetailUserAddress.location }}</a-descriptions-item>
                    <a-descriptions-item label="经度">{{ orderDetailUserAddress.longitude }}</a-descriptions-item>
                    <a-descriptions-item label="纬度">{{ orderDetailUserAddress.latitude }}</a-descriptions-item>
                    <a-descriptions-item label="详细地址">{{ orderDetailUserAddress.detailedAddress
                    }}</a-descriptions-item>
                </a-descriptions>
            </div>
            <div class="mt-8">
                <a-collapse v-model:activeKey="orderDetailActiveKey" accordion>
                    <a-collapse-panel key="1" header="订单商品信息">
                        <div>
                            <div v-if="orderDetailCommodity" v-for="(item, index) in orderDetailCommodity" :key="index"
                                class="mb-4">
                                <a-descriptions :column="6" size="small" bordered :title="item.commodity.commodityName">
                                    <a-descriptions-item label="购买商品数量">
                                        <span>{{ item.purchaseQuantity }}</span>
                                        <span>{{ item.commodity.meterCompany }}</span>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="所属商家">{{ item.business.calloutContent
                                    }}</a-descriptions-item>
                                    <a-descriptions-item label="商品状态">
                                        <div v-if="item.commodity.commodityStatus === 0">
                                            <a-tag color="pink">销售中</a-tag>
                                        </div>
                                        <div v-else>
                                            <a-tag color="cyan">仓库中</a-tag>
                                        </div>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="商品库存">
                                        <div v-if="Number(item.commodity.commodityStock) <= 10">
                                            <a-tooltip placement="top" color="red">
                                                <template #title>
                                                    <span>库存警告，即将无货，请即刻进货</span>
                                                </template>
                                                <a-tag color="#f50">
                                                    {{ item.commodity.commodityStock }}
                                                </a-tag>
                                            </a-tooltip>
                                        </div>
                                        <div
                                            v-else-if="Number(item.commodity.commodityStock) >= 10 && Number(item.commodity.commodityStock) <= 100">
                                            <a-tooltip placement="top" color="purple">
                                                <template #title>
                                                    <span>库存预警，请注意进货</span>
                                                </template>
                                                <a-tag color="orange">
                                                    {{ item.commodity.commodityStock }}
                                                </a-tag>
                                            </a-tooltip>
                                        </div>
                                        <div v-else>
                                            <a-tooltip placement="top" color="blue">
                                                <template #title>
                                                    <span>库存非常充裕，无需进货</span>
                                                </template>
                                                <a-tag color="success">
                                                    {{ item.commodity.commodityStock }}
                                                </a-tag>
                                            </a-tooltip>
                                        </div>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="商品类型">
                                        <div v-if="item.commodity.commodityStatus === 0">
                                            <a-tag color="green">虚拟商品</a-tag>
                                        </div>
                                        <div v-else>
                                            <a-tag color="purple">实物商品</a-tag>
                                        </div>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="销售模式">
                                        <div v-if="item.commodity.salesModel === 0">
                                            <a-tag color="orange">零售</a-tag>
                                        </div>
                                        <div v-else>
                                            <a-tag color="blue">批发</a-tag>
                                        </div>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="零售价/元">
                                        <a-tag color="#2db7f5">{{ item.commodity.retailPrice }}</a-tag>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="批发价/元">
                                        <a-tag color="#87d068">{{ item.commodity.wholesalePrice }}</a-tag>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="商品重量/kg">
                                        <a-tag color="#2db7f5">{{ item.commodity.commodityWeight }}</a-tag>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="计量单位">
                                        <a-tag color="#87d068">{{ item.commodity.meterCompany }}</a-tag>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="是否推荐为新品">
                                        <div v-if="item.commodity.commodityRecommend === 0">
                                            <a-tag color="#87d068">推荐</a-tag>
                                        </div>
                                        <div v-else>
                                            <a-tag color="#108ee9">不推荐</a-tag>
                                        </div>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="添加时间">
                                        <a-tag color="blue">{{ item.commodity.createTime }}</a-tag>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="上架时间">
                                        <a-tag color="blue">{{ item.commodity.putShelfTime }}</a-tag>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="下架时间">
                                        <a-tag color="blue">{{ item.commodity.offShelfTime }}</a-tag>
                                    </a-descriptions-item>
                                    <a-descriptions-item label="下架原因">{{ item.commodity.offShelfReason
                                    }}</a-descriptions-item>
                                    <a-descriptions-item label="商品图片">
                                        <div v-if="item.commodity.commodityImageUrl">
                                            <a-image style="height: 50px; width: 70px;"
                                                :src="item.commodity.commodityImageUrl" />
                                        </div>
                                        <div v-else>
                                            暂无
                                        </div>
                                    </a-descriptions-item>
                                    <a-descriptions-item v-if="item.merchantCoupon != null" label="商家优惠券名称">{{
                                        item.merchantCoupon.merchantCouponName }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.merchantCoupon != null" label="商家优惠券扣减额度">{{
                                        item.merchantCoupon.discountAmount
                                    }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.merchantCoupon != null" label="商家优惠券满多少可用">{{
                                        item.merchantCoupon.fullAvailable
                                    }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.merchantCoupon != null" label="商家优惠券数量">{{
                                        item.merchantCoupon.totalQuantity
                                    }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.merchantCoupon != null" label="商家优惠券开始时间">{{
                                        item.merchantCoupon.startTime
                                    }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.merchantCoupon != null" label="商家优惠券结束时间">{{
                                        item.merchantCoupon.endTime
                                    }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.universalCoupon != null" label="通用优惠券名称">{{
                                        item.universalCoupon.merchantCouponName
                                    }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.universalCoupon != null" label="通用优惠券扣减额度">{{
                                        item.universalCoupon.discountAmount
                                    }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.universalCoupon != null" label="通用优惠券满多少可用">{{
                                        item.universalCoupon.fullAvailable
                                    }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.universalCoupon != null" label="通用优惠券数量">{{
                                        item.universalCoupon.totalQuantity
                                    }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.universalCoupon != null" label="通用优惠券开始时间">{{
                                        item.universalCoupon.startTime
                                    }}</a-descriptions-item>
                                    <a-descriptions-item v-if="item.universalCoupon != null" label="通用优惠券结束时间">{{
                                        item.universalCoupon.endTime
                                    }}</a-descriptions-item>
                                    <a-descriptions-item label="商品描述">{{ item.commodity.commodityDescribe
                                    }}</a-descriptions-item>
                                </a-descriptions>
                            </div>
                        </div>
                    </a-collapse-panel>
                </a-collapse>
            </div>
            <div class="mt-10">
                <a-card title="物流信息" style="width: 100%">
                    <div>
                        <a-form :model="logisticsForm" name="basic" autocomplete="off">
                            <a-form-item label="物流公司" name="logisticsCompany"
                                :rules="[{ required: true, message: '请选择物流公司' }]">
                                <a-select v-model:value="logisticsForm.logisticsCompany" style="width: 300px">
                                    <a-select-option v-if="logisticsCompanyNameList != null"
                                        v-for="(item, index) in logisticsCompanyNameList" :key="index" :value="item">{{ item
                                        }}</a-select-option>
                                </a-select>
                            </a-form-item>
                            <a-form-item label="发送者姓名" name="logisticsCompany"
                                :rules="[{ required: true, message: '请填写发送者姓名' }]">
                                <a-input v-model:value="logisticsForm.senderName" style="width: 300px"></a-input>
                            </a-form-item>
                            <a-form-item label="发送者地址" name="logisticsCompany"
                                :rules="[{ required: true, message: '请填写发送地' }]">
                                <a-input v-model:value="logisticsForm.senderAddress" style="width: 300px"></a-input>
                            </a-form-item>
                            <a-form-item label="发送者电话号码" name="logisticsCompany"
                                :rules="[{ required: true, message: '请填写发送者电话号码' }]">
                                <a-input v-model:value="logisticsForm.senderPhone" style="width: 300px"></a-input>
                            </a-form-item>
                        </a-form>
                    </div>
                    <div>
                        <a-button @click="shipImmediatelyNow" type="primary">立即发货</a-button>
                    </div>
                </a-card>
            </div>
        </a-drawer>
    </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { message } from 'ant-design-vue'
import { getAllMallUserName } from '@/api/user/index'
import { getAllOrderListPage, getOrderDetail, shipImmediatelyByNow } from '@/api/pay/index'
import { getLogisticsCompanyName } from '@/api/logistics/index'

const userNameList = ref<any>()
const selectAllMallUserName = async () => {
    const res: any = await getAllMallUserName()
    if (res?.code === 0) {
        userNameList.value = res?.data
    }
}
selectAllMallUserName()

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
const userCollectList = ref<any>()

const orderPage = ref<any>({
    "currentPage": 1,
    "pageSize": 10,
    "orderStatus": -1,
    "userName": '',
    "orderNumber": ''
})

const getOrderList = async () => {
    const res: any = await getAllOrderListPage(orderPage.value)

    if (res?.code === 0) {
        userCollectList.value = res?.data?.userOrderPageList
        pageTotal.value = res?.data?.total
        message.success('信息获取成功')
        loading.value = false
    } else {
        message.error("数据加载错误，请刷新重新加载")
    }
}

getOrderList()

// 分页
const onShowSizeChange = (current: number, pageSize: number) => {
    orderPage.value.currentPage = current
    orderPage.value.pageSize = pageSize
    getOrderList()
}

// 条件搜索
const selectSearchCart = () => {
    console.log(orderPage.value)
    getOrderList()
}

// 重置
const resetSelectSearchMallUser = () => {
    orderPage.value.userName = ''
    orderPage.value.orderNumber = ''
    orderPage.value.orderStatus = -1
    // orderStatus.value = '-1'
    getOrderList()
}

const columns = [
    {
        title: '用户名',
        dataIndex: 'userName',
        key: 'userName',
        align: 'center'
    }, {
        title: '订单号',
        dataIndex: 'orderNumber',
        key: 'orderNumber',
        align: 'center'
    },
    {
        title: '订单状态',
        dataIndex: 'orderStatus',
        key: 'orderStatus',
        align: 'center'
    },
    {
        title: '总价/元',
        dataIndex: 'totalPrice',
        key: 'totalPrice',
        align: 'center'
    },
    {
        title: '运费/元',
        dataIndex: 'transportationExpenses',
        key: 'transportationExpenses',
        align: 'center'
    },
    {
        title: '会员折扣',
        dataIndex: 'memberDiscount',
        key: 'memberDiscount',
        align: 'center'
    },
    {
        title: '创建时间',
        dataIndex: 'createTime',
        key: 'createTime',
        align: 'center'
    },
    {
        title: '付款时间',
        dataIndex: 'strikeBargainTime',
        key: 'strikeBargainTime',
        align: 'center'
    },
    {
        title: '收货地址',
        dataIndex: 'addressDetail',
        key: 'addressDetail',
        align: 'center',
    },
    {
        title: '订单备注',
        dataIndex: 'orderRemarks',
        key: 'orderRemarks',
        align: 'center'
    },
    {
        title: '操作',
        dataIndex: 'action',
        key: 'action',
        align: 'center'
    }
]

// 订单详情
const orderDetailVisible = ref<boolean>(false)
const orderDetailActiveKey = ref([])
const orderDetailUser = ref<any>()
const orderDetailUserAddress = ref<any>()
const orderDetailCommodity = ref<any>()

const orderDetail = async (e: any) => {
    const res: any = await getOrderDetail(e.orderId)
    if (res?.code === 0) {
        orderDetailVisible.value = true
        orderDetailUser.value = e.mallUser
        orderDetailUserAddress.value = e.userAddress
        orderDetailCommodity.value = res?.data
    } else {
        message.error("订单详情获取失败")
    }
}

const shipDrawerVisible = ref<boolean>(false)
const logisticsForm = ref<any>({
    'placeOrderUserId': '',
    'addresseeId': '',
    'orderNumber': '',
    "logisticsCompany": '',
    "senderName": '',
    "senderPhone": '',
    "senderAddress": '',
    "orderId": ''
})
const logisticsCompanyNameList = ref<any>()
const shipImmediatelyData = ref<any>()
// 发货
const shipImmediately = async (e: any) => {
    shipImmediatelyData.value = e
    const logisticsCompanyNameRes: any = await getLogisticsCompanyName()
    if (logisticsCompanyNameRes?.code === 0) {
        logisticsCompanyNameList.value = logisticsCompanyNameRes?.data
    }

    const res: any = await getOrderDetail(e.orderId)
    if (res?.code === 0) {
        orderDetailUserAddress.value = e.userAddress
        shipDrawerVisible.value = true
        orderDetailUser.value = e.mallUser
        orderDetailCommodity.value = res?.data

    } else {
        message.error("订单详情获取失败")
    }
}

// 发货请求
const shipImmediatelyNow = async () => {
    logisticsForm.value.orderNumber = shipImmediatelyData.value.orderNumber
    logisticsForm.value.orderId = shipImmediatelyData.value.orderId
    logisticsForm.value.placeOrderUserId = shipImmediatelyData.value.userId
    logisticsForm.value.addresseeId = shipImmediatelyData.value.userAddress.addressId
    const res: any = await shipImmediatelyByNow(logisticsForm.value)

    if (res?.code === 0) {
        shipDrawerVisible.value = false
        message.success("发货成功")
        getOrderList()
        logisticsForm.value.placeOrderUserId = ''
        logisticsForm.value.addresseeId = ''
        logisticsForm.value.orderNumber = ''
        logisticsForm.value.logisticsCompany = ''
        logisticsForm.value.senderName = ''
        logisticsForm.value.senderPhone = ''
        logisticsForm.value.senderAddress = ''
        logisticsForm.value.orderId = ''
    } else {
        message.error("发货失败")
    }
}

</script>

<style scoped></style>