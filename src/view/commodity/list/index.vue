<template>
    <!-- 顶部添加商品区域 -->
    <div class="m-2 mb-4 flex flex-row">
        <div>
            <a-button type="primary" @click="addCommodityVisible = true">
                <template #icon><t-icon name="add" /></template>
                添加商品
            </a-button>
        </div>
        <div class="flex flex-row">
            <a-button type="primary" class="ml-4" @click="uploadVisible = true">
                <template #icon><t-icon name="backtop-rectangle" /></template>
                批量导入
            </a-button>
            <a-modal v-model:visible="uploadVisible" title="批量导入商品数据" :centered="true" :closable="false"
                :maskClosable="false" :keyboard="false" :footer="false">
                <div class="mt-6 mb-6 flex flex-row justify-between">
                    <a-upload v-model:file-list="batchFileList" :maxCount="1" name="classificationBatchLevel"
                        :progress="progress" :before-upload="batchBeforeUpload" @change="handleUploadBatchChange">
                        <a-button type="primary">
                            <template #icon><t-icon name="backtop-rectangle" /></template>
                            选取文件
                        </a-button>
                    </a-upload>
                    <a-button type="primary" v-if="isBatchUpload" @click="uploadBatchOneLevel()">开始导入</a-button>
                </div>
            </a-modal>
            <a-modal v-model:visible="uploadCommodityVisible" :width="130" :centered="true" :closable="false"
                :maskClosable="false" :keyboard="false" :footer="false">
                <div class="flex flex-col justify-items-center">
                    <a-progress type="circle" :percent="uploadCommodityPercent" :width="82" />
                    <span class="mt-2 text-sm text-center">{{ uploadCommodityText }}</span>
                </div>
            </a-modal>
        </div>
        <div>
            <a-modal v-model:visible="addCommodityVisible" title="添加商品" :footer="false" :width="650">
                <div style="width: 80%; margin-left: 30px;">
                    <a-form :model="commodityAddForm" @finish="handleFinish" @finishFailed="handleFinishFailed"
                        ref="addFormRef" :rules="addRules" name="commodityAddForm" autocomplete="off">
                        <a-form-item label="商品名称" name="commodityName" style="margin-bottom:10px;">
                            <a-input v-model:value="commodityAddForm.commodityName"></a-input>
                        </a-form-item>

                        <a-form-item label="所属商家" name="businessName" style="margin-bottom:10px;">
                            <a-select v-model:value="commodityAddForm.businessName" show-search placeholder="所属商家名称"
                                @focus="getAllBusinessNameList">
                                <a-select-option v-if="businessNameList" v-for="(item, index) in businessNameList"
                                    :value="item">{{ item }}</a-select-option>
                            </a-select>
                        </a-form-item>

                        <a-form-item label="一级分类" name="oneClassificationName" style="margin-bottom:10px;">
                            <a-select v-model:value="commodityAddForm.oneClassificationName" show-search :allowClear="true"
                                placeholder="一级分类" @focus="selectOneLevelNameList">
                                <a-select-option v-if="oneLevelNameList" v-for="(item, index) in oneLevelNameList"
                                    :value="item">{{ item }}</a-select-option>
                            </a-select>
                        </a-form-item>

                        <a-form-item label="二级分类" name="twoClassificationName" style="margin-bottom:10px;">
                            <a-select v-model:value="commodityAddForm.twoClassificationName" show-search
                                :disabled="commodityAddForm.oneClassificationName != '' ? false : true" :allowClear="true"
                                placeholder="二级分类" @focus="selectTwoLevelNameList(commodityAddForm.oneClassificationName)">
                                <a-select-option v-if="twoLevelNameList" v-for="(item, index) in twoLevelNameList"
                                    :value="item">{{ item }}</a-select-option>
                            </a-select>
                        </a-form-item>

                        <a-form-item label="计量单位" name="meterCompany" style="margin-bottom:10px;">
                            <a-input v-model:value="commodityAddForm.meterCompany"></a-input>
                        </a-form-item>

                        <a-form-item label="商品重量(/kg)" name="commodityWeight" style="margin-bottom:10px;">
                            <a-input v-model:value="commodityAddForm.commodityWeight"></a-input>
                        </a-form-item>

                        <a-form-item label="商品数量" name="commodityStock" style="margin-bottom:10px;">
                            <a-input v-model:value="commodityAddForm.commodityStock"></a-input>
                        </a-form-item>

                        <a-form-item label="商品推荐" name="commodityRecommend" style="margin-bottom:10px;">
                            <a-radio-group v-model:value="commodityAddForm.commodityRecommend" name="radioGroup">
                                <a-radio value="0">推荐</a-radio>
                                <a-radio value="1">不推荐</a-radio>
                            </a-radio-group>
                        </a-form-item>

                        <a-form-item label="商品状态" name="commodityStatus" style="margin-bottom:10px;">
                            <!-- <a-input v-model:value="commoditySearchForm.commodityStatus"></a-input> -->
                            <a-radio-group v-model:value="commodityAddForm.commodityStatus" name="radioGroup">
                                <a-radio value="0">上架</a-radio>
                                <a-radio value="1">下架(放入仓库)</a-radio>
                            </a-radio-group>
                        </a-form-item>

                        <a-form-item label="商品类型" name="commodityType" style="margin-bottom:10px;">
                            <a-radio-group v-model:value="commodityAddForm.commodityType" name="radioGroup">
                                <a-radio value="0">虚拟商品</a-radio>
                                <a-radio value="1">实物商品</a-radio>
                            </a-radio-group>
                        </a-form-item>

                        <a-form-item label="销售模式" name="salesModel" style="margin-bottom:10px;">
                            <a-radio-group v-model:value="commodityAddForm.salesModel" name="radioGroup">
                                <a-radio value="0">零售型</a-radio>
                                <a-radio value="1">批发型</a-radio>
                            </a-radio-group>
                        </a-form-item>
                        <a-form-item label="零售价格" name="retailPrice" v-if="commodityAddForm.salesModel === '0'"
                            style="margin-bottom:10px;">
                            <a-input v-model:value="commodityAddForm.retailPrice"></a-input>
                        </a-form-item>
                        <a-form-item label="批发价格" name="wholesalePrice" v-if="commodityAddForm.salesModel === '1'"
                            style="margin-bottom:10px;">
                            <a-input v-model:value="commodityAddForm.wholesalePrice"></a-input>
                        </a-form-item>

                        <a-form-item label="商品图片" style="margin-bottom:10px;">
                            <a-upload :maxCount="1" :customRequest="onSubmitTwoLevel" list-type="picture-card"
                                :show-upload-list="false" v-model:file-list="fileList" name="classificationIcon"
                                :before-upload="beforeUpload" @remove="handleRemove">
                                <img v-if="imageUrl" style="width:100px;height:100px;" :src="imageUrl" alt="avatar" />
                                <div v-else>
                                    <loading-outlined v-if="uploading"></loading-outlined>
                                    <plus-outlined v-else></plus-outlined>
                                    <div class="ant-upload-text">Upload</div>
                                </div>
                            </a-upload>
                        </a-form-item>

                        <a-form-item label="商品描述" name="commodityDescribe" style="margin-bottom:10px;">
                            <a-input v-model:value="commodityAddForm.commodityDescribe"></a-input>
                        </a-form-item>

                        <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
                            <a-button type="primary" html-type="submit">添加</a-button>
                            <a-button style="margin-left: 10px" @click="resetAddForm">重置</a-button>
                        </a-form-item>
                    </a-form>
                </div>
            </a-modal>
        </div>
    </div>
    <!-- 顶部搜索区域 -->
    <div style="background: #ececec; padding: 15px">
        <a-card :bordered="false">
            <div class="flex flex-col">
                <div class="mb-4">
                    <a-form :model="commoditySearchForm" ref="formRef" name="commoditySearchForm" layout="inline"
                        autocomplete="off">
                        <a-form-item label="商品名称" name="commodityName" style="margin-bottom:20px;">
                            <a-input v-model:value="commoditySearchForm.commodityName"></a-input>
                        </a-form-item>

                        <a-form-item label="所属商家" name="businessName" style="margin-bottom:20px;">
                            <a-select v-model:value="commoditySearchForm.businessName" show-search placeholder="所属商家名称"
                                style="width: 180px" @focus="getAllBusinessNameList">
                                <a-select-option v-if="businessNameList" v-for="(item, index) in businessNameList"
                                    :value="item">{{ item }}</a-select-option>
                            </a-select>
                        </a-form-item>

                        <a-form-item label="一级分类" name="oneClassificationName" style="margin-bottom:20px;">
                            <!-- <a-input v-model:value="commoditySearchForm.classificationName"></a-input> -->
                            <a-select v-model:value="commoditySearchForm.oneClassificationName" show-search
                                :allowClear="true" placeholder="一级分类" style="width: 180px" @focus="selectOneLevelNameList">
                                <a-select-option v-if="oneLevelNameList" v-for="(item, index) in oneLevelNameList"
                                    :value="item">{{ item }}</a-select-option>
                            </a-select>
                        </a-form-item>

                        <a-form-item label="二级分类" name="twoClassificationName" style="margin-bottom:20px;">
                            <!-- <a-input v-model:value="commoditySearchForm.classificationName"></a-input> -->
                            <a-select v-model:value="commoditySearchForm.twoClassificationName" show-search
                                :disabled="commoditySearchForm.oneClassificationName != '' ? false : true"
                                :allowClear="true" placeholder="二级分类" style="width: 180px"
                                @focus="selectTwoLevelNameList(commoditySearchForm.oneClassificationName)">
                                <a-select-option v-if="twoLevelNameList" v-for="(item, index) in twoLevelNameList"
                                    :value="item">{{ item }}</a-select-option>
                            </a-select>
                        </a-form-item>

                        <a-form-item label="商品状态" name="commodityStatus" style="margin-bottom:20px;">
                            <!-- <a-input v-model:value="commoditySearchForm.commodityStatus"></a-input> -->
                            <a-select v-model:value="commoditySearchForm.commodityStatus" show-search placeholder="商品状态"
                                style="width: 180px">
                                <a-select-option value="0">上架</a-select-option>
                                <a-select-option value="1">仓库中</a-select-option>
                            </a-select>
                        </a-form-item>

                        <a-form-item label="商品类型" name="commodityType" style="margin-bottom:20px;">
                            <!-- <a-input v-model:value="commoditySearchForm.commodityType"></a-input> -->
                            <a-select v-model:value="commoditySearchForm.commodityType" show-search placeholder="商品类型"
                                style="width: 180px">
                                <a-select-option value="0">虚拟商品</a-select-option>
                                <a-select-option value="1">实物商品</a-select-option>
                            </a-select>
                        </a-form-item>

                        <a-form-item label="销售模式" name="salesModel" style="margin-bottom:20px;">
                            <!-- <a-input v-model:value="commoditySearchForm.salesModel"></a-input> -->
                            <a-select v-model:value="commoditySearchForm.salesModel" show-search placeholder="销售模式"
                                style="width: 180px">
                                <a-select-option value="0">零售型</a-select-option>
                                <a-select-option value="1">批发型</a-select-option>
                            </a-select>
                        </a-form-item>

                        <a-form-item label="商品推荐" name="commodityRecommend" style="margin-bottom:20px;">
                            <!-- <a-input v-model:value="commoditySearchForm.commodityRecommend"></a-input> -->
                            <a-select v-model:value="commoditySearchForm.commodityRecommend" show-search placeholder="商品推荐"
                                style="width: 180px">
                                <a-select-option value="0">推荐</a-select-option>
                                <a-select-option value="1">不推荐</a-select-option>
                            </a-select>
                        </a-form-item>

                        <a-form-item label="添加时间" name="createTime" style="margin-bottom:20px;">
                            <t-date-range-picker v-model="commoditySearchForm.createTime" :presets="presets"
                                enable-time-picker />
                        </a-form-item>

                        <a-form-item label="上架时间" name="putShelfTime" style="margin-bottom:20px;">
                            <t-date-range-picker v-model="commoditySearchForm.putShelfTime" :presets="presets"
                                enable-time-picker />
                        </a-form-item>

                    </a-form>
                </div>
                <div class="flex flex-row -mt-2">
                    <a-button type="primary" class="mr-4" @click="selectSearchCommodity">
                        筛选
                    </a-button>
                    <a-button @click="resetForm">
                        重置
                    </a-button>
                </div>
            </div>
        </a-card>
    </div>
    <!-- 商品列表区域 -->
    <div>
        <a-card :bordered="false" title="商品列表数据">
            <div class="-mt-2">
                <a-space :size="8">
                    <a-popconfirm title="此操作将会当前选中的商品进行批量上架，您确定吗？" placement="topLeft" ok-text="确定" cancel-text="取消"
                        @confirm="batchLaunch">
                        <template #icon><question-circle-outlined style="color: red" /></template>
                        <a-button size="small" :disabled="batchIsUse" type="primary">批量上架</a-button>
                    </a-popconfirm>
                    <a-popconfirm title="此操作将会当前选中的商品进行批量下架，您确定吗？" placement="topLeft" ok-text="确定" cancel-text="取消"
                        @confirm="batchOffShelf">
                        <template #icon><question-circle-outlined style="color: red" /></template>
                        <a-button size="small" :disabled="batchIsUse" type="primary" danger>批量下架</a-button>
                    </a-popconfirm>
                    <a-popconfirm title="此操作将会当前选中的商品进行批量推荐为新品，您确定吗？" placement="topLeft" ok-text="确定" cancel-text="取消"
                        @confirm="batchRecommendation">
                        <template #icon><question-circle-outlined style="color: red" /></template>
                        <a-button size="small" :disabled="batchIsUse" type="primary">批量推荐为新品</a-button>
                    </a-popconfirm>
                    <a-popconfirm title="此操作将会当前选中的商品设置为不推荐为新品，您确定吗？" placement="topLeft" ok-text="确定" cancel-text="取消"
                        @confirm="batchNoRecommendation">
                        <template #icon><question-circle-outlined style="color: red" /></template>
                        <a-button size="small" :disabled="batchIsUse" type="primary">批量不推荐为新品</a-button>
                    </a-popconfirm>
                    <a-popconfirm title="此操作将会当前选中的商品进行批量添加库存数量，您确定吗？" placement="top" ok-text="确定" cancel-text="取消"
                        @confirm="bulkPurchase">
                        <template #icon><question-circle-outlined style="color: red" /></template>
                        <a-button size="small" :disabled="batchIsUse" type="primary">批量添加库存</a-button>
                    </a-popconfirm>
                    <a-modal v-model:visible="bulkPurchaseVisible" :closable="false" :keyboard="false" :maskClosable="false"
                        title="请填写批量进货的数量" :width="400" :centered="true">
                        <template #footer>
                            <a-button key="back" @click="bulkPurchaseVisible = false">取消</a-button>
                            <a-button key="submit" type="primary" @click="bulkPurchaseRequest">确定</a-button>
                        </template>
                        <a-input-number v-model:value="bulkPurchaseNumberValue" style="width: 100%">
                            <template #addonBefore>
                                <taobao-outlined />
                            </template>
                        </a-input-number>
                        <div class="mt-3 text-xs">您将为当前选中的所有商品全部添加<span class="text-red-600 text-sm font-bold ml-1 mr-1">{{
                            bulkPurchaseNumberValue }}</span>的库存数量</div>
                    </a-modal>
                    <a-popconfirm title="此操作会将当前选中的所有商品全部放入回收站，您确定吗？" placement="top" ok-text="确定" cancel-text="取消"
                        @confirm="batchDelete">
                        <template #icon><question-circle-outlined style="color: red" /></template>
                        <a-button size="small" :disabled="batchIsUse" type="primary" danger>批量放入回收站</a-button>
                    </a-popconfirm>
                </a-space>
            </div>
            <div class="mt-4">
                <a-table :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
                    :dataSource="commodityAllList" :indentSize="50" :pagination="false" :columns="commodityAllListColumns"
                    :loading="commodityAllListLoading">
                    <template #bodyCell="{ column, record }">
                        <!-- 图片 -->
                        <template v-if="column.key === 'commodityImageUrl'">
                            <div v-if="record.commodityImageUrl">
                                <!-- <a-image style="height: 50px; width: 60px;" :src="record.commodityImageUrl" /> -->
                                <img style="height: 50px; width: 60px;" :src="record.commodityImageUrl" />
                            </div>
                            <div v-else>
                                暂无
                            </div>
                        </template>
                        <!-- 状态 -->
                        <template v-if="column.key === 'commodityStatus'">
                            <div v-if="record.commodityStatus === 0">
                                <a-tag color="pink">销售中</a-tag>
                            </div>
                            <div v-else>
                                <a-tag color="cyan">仓库中</a-tag>
                            </div>
                        </template>
                        <!-- 库存 -->
                        <template v-if="column.key === 'commodityStock'">
                            <div v-if="Number(record.commodityStock) <= 10">
                                <a-tooltip placement="top" color="red">
                                    <template #title>
                                        <span>库存警告，即将无货，请即刻进货</span>
                                    </template>
                                    <a-tag color="#f50">
                                        {{ record.commodityStock }}
                                    </a-tag>
                                </a-tooltip>
                            </div>
                            <div v-else-if="Number(record.commodityStock) >= 10 && Number(record.commodityStock) <= 100">
                                <a-tooltip placement="top" color="purple">
                                    <template #title>
                                        <span>库存预警，请注意进货</span>
                                    </template>
                                    <a-tag color="orange">
                                        {{ record.commodityStock }}
                                    </a-tag>
                                </a-tooltip>
                            </div>
                            <div v-else>
                                <a-tooltip placement="top" color="blue">
                                    <template #title>
                                        <span>库存非常充裕，无需进货</span>
                                    </template>
                                    <a-tag color="success">
                                        {{ record.commodityStock }}
                                    </a-tag>
                                </a-tooltip>
                            </div>
                        </template>
                        <!-- 类型 -->
                        <template v-if="column.key === 'commodityType'">
                            <div v-if="record.commodityStatus === 0">
                                <a-tag color="green">虚拟商品</a-tag>
                            </div>
                            <div v-else>
                                <a-tag color="purple">实物商品</a-tag>
                            </div>
                        </template>
                        <!-- 计量单位 -->
                        <template v-if="column.key === 'meterCompany'">
                            <a-tag color="#16f5e9">{{ record.meterCompany }}</a-tag>
                        </template>
                        <!-- 销售模式 -->
                        <template v-if="column.key === 'salesModel'">
                            <div v-if="record.salesModel === 0">
                                <a-tag color="orange">零售</a-tag>
                            </div>
                            <div v-else>
                                <a-tag color="blue">批发</a-tag>
                            </div>
                        </template>
                        <!-- 推荐 -->
                        <template v-if="column.key === 'commodityRecommend'">
                            <div v-if="record.commodityRecommend === 0">
                                <a-tag color="#87d068">推荐</a-tag>
                            </div>
                            <div v-else>
                                <a-tag color="#108ee9">不推荐</a-tag>
                            </div>
                        </template>
                        <!-- 操作 -->
                        <template v-if="column.key === 'action'">
                            <a-tooltip color="cyan">
                                <template #title>商品详情</template>
                                <a-button type="link" size="medium" @click="openCommodityDetailModel(record)">
                                    <template #icon>
                                        <t-icon name="error-circle" />
                                    </template>
                                </a-button>
                            </a-tooltip>
                            <a-tooltip color="cyan">
                                <template #title>商品评论</template>
                                <a-button type="link" size="medium" @click="toCommodityCommentModel(record)">
                                    <template #icon>
                                        <t-icon name="view-module" />
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
                                            <a-button type="link" @click="grounding(record)">上架该商品</a-button>
                                        </a-menu-item>
                                        <a-menu-item>
                                            <a-button type="link" danger @click="undercarriage(record)">下架该商品</a-button>
                                        </a-menu-item>
                                        <a-menu-item>
                                            <a-button type="link" @click="addStock(record)">添加库存数</a-button>
                                        </a-menu-item>
                                        <a-modal v-model:visible="addStockVisible" :closable="false" :keyboard="false"
                                            :maskClosable="false" title="请填写该商品进货的数量" :width="400" :centered="true">
                                            <template #footer>
                                                <a-button key="back" @click="addStockVisible = false">取消</a-button>
                                                <a-button key="submit" type="primary" @click="addStockRequest">确定</a-button>
                                            </template>
                                            <a-input-number v-model:value="addStockNumberValue" style="width: 100%">
                                                <template #addonBefore>
                                                    <taobao-outlined />
                                                </template>
                                            </a-input-number>
                                            <div class="mt-3 text-xs">您将为当前选中的商品添加<span
                                                    class="text-red-600 text-sm font-bold ml-1 mr-1">{{
                                                        addStockNumberValue }}</span>的库存数量</div>
                                        </a-modal>
                                        <a-menu-item>
                                            <a-button type="link" @click="recommend(record)">推荐为新品</a-button>
                                        </a-menu-item>
                                        <a-menu-item>
                                            <a-button type="link" danger @click="noRecommended(record)">不推荐新品</a-button>
                                        </a-menu-item>
                                    </a-menu>
                                </template>
                            </a-dropdown>
                            <a-popconfirm title="此操作会将该商品放入回收站，您确定要继续吗?" placement="topRight" @confirm="recycleBin(record)"
                                ok-text="确定" cancel-text="取消">
                                <template #icon><question-circle-outlined style="color: red" /></template>
                                <a-button type="link" size="medium">
                                    <template #icon><t-icon name="delete" /></template>
                                </a-button>
                            </a-popconfirm>
                        </template>
                    </template>
                    <template #expandedRowRender="{ record }">
                        <div class="flex flex-row ml-10">
                            <div>
                                <span>添加时间：</span>
                                <a-tag color="blue"><span>{{ record.createTime }}</span></a-tag>
                            </div>
                            <div class="ml-2">
                                <span>上架时间：</span>
                                <a-tag color="blue"><span>{{ record.putShelfTime }}</span></a-tag>
                            </div>
                            <div class="ml-2">
                                <span>下架时间：</span>
                                <a-tag color="blue"><span>{{ record.offShelfTime }}</span></a-tag>
                            </div>
                            <div class="ml-2">
                                <span>下架原因：</span>
                                <span>{{ record.offShelfReason }}</span>
                            </div>
                            <div class="ml-2">
                                <span>商品描述：</span>
                                <span>{{ record.commodityDescribe }}</span>
                            </div>
                        </div>
                    </template>
                </a-table>
            </div>
            <!-- 分页 -->
            <div class="mt-8 mb-4 flex justify-end">
                <a-pagination size="small" v-model:current="commoditySearchForm.currentPage"
                    :page-size-options="pageSizeOptions" :hideOnSinglePage="false"
                    :show-total="(pageTotal: number) => `共 ${ pageTotal } 条 `" show-size-changer
                    @showSizeChange="onShowSizeChange" @change="onShowSizeChange"
                    v-model:page-size="commoditySearchForm.pageSize" :total="pageTotal">
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
    </div>
    <!-- 商品详情弹框 -->
    <div>
        <a-modal v-model:visible="commodityDetailVisible" :footer="false" :width="1000">
            <div>
                <a-descriptions bordered :title="commodityDetailData.commodityName">
                    <a-descriptions-item label="商品名称">{{ commodityDetailData.commodityName }}</a-descriptions-item>
                    <a-descriptions-item label="所属商家">{{ commodityDetailData.businessName }}</a-descriptions-item>
                    <a-descriptions-item label="一级分类">{{ commodityDetailData.oneClassificationName }}</a-descriptions-item>
                    <a-descriptions-item label="二级分类">{{ commodityDetailData.twoClassificationName }}</a-descriptions-item>
                    <a-descriptions-item label="商品状态">
                        <div v-if="commodityDetailData.commodityStatus === 0">
                            <a-tag color="pink">销售中</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="cyan">仓库中</a-tag>
                        </div>
                    </a-descriptions-item>
                    <a-descriptions-item label="商品库存">
                        <div v-if="Number(commodityDetailData.commodityStock) <= 10">
                            <a-tooltip placement="top" color="red">
                                <template #title>
                                    <span>库存警告，即将无货，请即刻进货</span>
                                </template>
                                <a-tag color="#f50">
                                    {{ commodityDetailData.commodityStock }}
                                </a-tag>
                            </a-tooltip>
                        </div>
                        <div
                            v-else-if="Number(commodityDetailData.commodityStock) >= 10 && Number(commodityDetailData.commodityStock) <= 100">
                            <a-tooltip placement="top" color="purple">
                                <template #title>
                                    <span>库存预警，请注意进货</span>
                                </template>
                                <a-tag color="orange">
                                    {{ commodityDetailData.commodityStock }}
                                </a-tag>
                            </a-tooltip>
                        </div>
                        <div v-else>
                            <a-tooltip placement="top" color="blue">
                                <template #title>
                                    <span>库存非常充裕，无需进货</span>
                                </template>
                                <a-tag color="success">
                                    {{ commodityDetailData.commodityStock }}
                                </a-tag>
                            </a-tooltip>
                        </div>
                    </a-descriptions-item>
                    <a-descriptions-item label="商品类型">
                        <div v-if="commodityDetailData.commodityStatus === 0">
                            <a-tag color="green">虚拟商品</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="purple">实物商品</a-tag>
                        </div>
                    </a-descriptions-item>
                    <a-descriptions-item label="销售模式">
                        <div v-if="commodityDetailData.salesModel === 0">
                            <a-tag color="orange">零售</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="blue">批发</a-tag>
                        </div>
                    </a-descriptions-item>
                    <a-descriptions-item label="零售价/元">
                        <a-tag color="#2db7f5">{{ commodityDetailData.retailPrice }}</a-tag>
                    </a-descriptions-item>
                    <a-descriptions-item label="批发价/元">
                        <a-tag color="#87d068">{{ commodityDetailData.wholesalePrice }}</a-tag>
                    </a-descriptions-item>
                    <a-descriptions-item label="商品重量/kg">
                        <a-tag color="#2db7f5">{{ commodityDetailData.commodityWeight }}</a-tag>
                    </a-descriptions-item>
                    <a-descriptions-item label="计量单位">
                        <a-tag color="#87d068">{{ commodityDetailData.meterCompany }}</a-tag>
                    </a-descriptions-item>
                    <a-descriptions-item label="是否推荐为新品">
                        <div v-if="commodityDetailData.commodityRecommend === 0">
                            <a-tag color="#87d068">推荐</a-tag>
                        </div>
                        <div v-else>
                            <a-tag color="#108ee9">不推荐</a-tag>
                        </div>
                    </a-descriptions-item>
                    <a-descriptions-item label="添加时间">
                        <a-tag color="blue">{{ commodityDetailData.createTime }}</a-tag>
                    </a-descriptions-item>
                    <a-descriptions-item label="上架时间">
                        <a-tag color="blue">{{ commodityDetailData.putShelfTime }}</a-tag>
                    </a-descriptions-item>
                    <a-descriptions-item label="下架时间">
                        <a-tag color="blue">{{ commodityDetailData.offShelfTime }}</a-tag>
                    </a-descriptions-item>
                    <a-descriptions-item label="下架原因">{{ commodityDetailData.offShelfReason }}</a-descriptions-item>
                    <a-descriptions-item label="商品图片">
                        <div v-if="commodityDetailData.commodityImageUrl">
                            <a-image style="height: 50px; width: 70px;" :src="commodityDetailData.commodityImageUrl" />
                        </div>
                        <div v-else>
                            暂无
                        </div>
                    </a-descriptions-item>
                    <a-descriptions-item label="商品描述">{{ commodityDetailData.commodityDescribe }}</a-descriptions-item>
                </a-descriptions>
            </div>
        </a-modal>
    </div>
    <div>
        <a-modal v-model:visible="commodityCommentVisible" width="60%" :keyboard="false" :maskClosable="false" title="评论列表"
            :footer="false">
            <CommodityComment :commodityCommentData="commodityCommentData" />
        </a-modal>
    </div>
</template>

<script setup lang="ts">
import { ref } from "vue"
import { QuestionCircleOutlined, TaobaoOutlined } from '@ant-design/icons-vue'
import { getAllBusinessName } from '@/api/merchantStore/index'
import {
    getParentNameList,
    getTwoLevelNameList,
    uploadCommodityImage,
    addCommodity,
    getCommodityByPage,
    batchLaunchCommodity,
    batchOffShelfCommodity,
    batchRecommendationCommodity,
    batchNoRecommendationCommodity,
    bulkPurchaseCommodity,
    putInRecycleBinCommodity,
    uploadBatchCommodity,
    getCommodityCommentById
} from '@/api/commodity/index'
import { message } from 'ant-design-vue'
import dayjs from 'dayjs'
import type { FormInstance } from 'ant-design-vue'
import type { Rule } from 'ant-design-vue/es/form'
import { PlusOutlined, LoadingOutlined, } from '@ant-design/icons-vue'
import type { UploadProps } from 'ant-design-vue'
import type { UploadChangeParam } from 'ant-design-vue'
import CommodityComment from '@/view/commodity/list/comment/index.vue'

const presets = ref({
    最近7天: [dayjs().subtract(6, 'day'), dayjs()],
    最近3天: [dayjs().subtract(2, 'day'), dayjs()],
    今天: [dayjs(), dayjs()],
})

const formRef = ref<FormInstance>()
const addFormRef = ref<FormInstance>()

// 添加商品弹窗控件
const addCommodityVisible = ref<boolean>(false)

// 商品搜索表单数据
const commoditySearchForm = ref<any>({
    currentPage: 1,
    pageSize: 10,
    commodityName: '',
    commodityStatus: '',
    twoClassificationName: '',
    oneClassificationName: '',
    commodityType: '',
    salesModel: '',
    commodityRecommend: '',
    businessName: '',
    createStartTime: '',
    createEndTime: '',
    putShelfEndTime: '',
    putShelfStartTime: '',
    createTime: [],
    putShelfTime: []
})
const pageTotal = ref<number>()
const pageSizeOptions = ref<string[]>(['10', '20', '50', '80', '100'])

const commodityAllListLoading = ref<boolean>(false)
// 全部商品数据
const commodityAllList = ref<any>()
const commodityAllListColumns = [
    {
        title: '商品名称',
        dataIndex: 'commodityName',
        key: 'commodityName',
        onFilter: (value: string, record: any) => record.commodityName.indexOf(value) === 0,
        sorter: (a: any, b: any) => a.commodityName.length - b.commodityName.length,
        sortDirections: ['descend'],
    },
    {
        title: '商家',
        dataIndex: 'businessName',
        key: 'businessName',
    },
    {
        title: '一级分类',
        dataIndex: 'oneClassificationName',
        key: 'oneClassificationName',
    },
    {
        title: '二级分类',
        dataIndex: 'twoClassificationName',
        key: 'twoClassificationName',
    },
    {
        title: '图片',
        dataIndex: 'commodityImageUrl',
        key: 'commodityImageUrl',
    }, {
        title: '状态',
        dataIndex: 'commodityStatus',
        key: 'commodityStatus',
    },
    {
        title: '库存',
        dataIndex: 'commodityStock',
        key: 'commodityStock',
        defaultSortOrder: 'descend',
        sorter: (a: any, b: any) => a.commodityStock - b.commodityStock
    },
    {
        title: '类型',
        dataIndex: 'commodityType',
        key: 'commodityType',
    },
    {
        title: '销售模式',
        dataIndex: 'salesModel',
        key: 'salesModel',
    },
    {
        title: '零售价/元',
        dataIndex: 'retailPrice',
        key: 'retailPrice',
        defaultSortOrder: 'descend',
        sorter: (a: any, b: any) => a.retailPrice - b.retailPrice,
    },
    {
        title: '批发价/元',
        dataIndex: 'wholesalePrice',
        key: 'wholesalePrice',
        defaultSortOrder: 'descend',
        sorter: (a: any, b: any) => a.wholesalePrice - b.wholesalePrice,
    },
    {
        title: '计量单位',
        dataIndex: 'meterCompany',
        key: 'meterCompany',
    },
    {
        title: '重量/kg',
        dataIndex: 'commodityWeight',
        key: 'commodityWeight',
        defaultSortOrder: 'descend',
        sorter: (a: any, b: any) => a.commodityWeight - b.commodityWeight,
    },
    {
        title: '推荐新品',
        dataIndex: 'commodityRecommend',
        key: 'commodityRecommend',
    },
    {
        title: '操作',
        dataIndex: 'action',
        key: 'action',
        align: "center",
        width: 160
    },
    // {
    //     title: '添加时间',
    //     dataIndex: 'createTime',
    //     key: 'createTime',
    //     ellipsis: true,
    // },
    // {
    //     title: '上架时间',
    //     dataIndex: 'putShelfTime',
    //     key: 'putShelfTime',
    //     ellipsis: true,
    // },
    // {
    //     title: '下架时间',
    //     dataIndex: 'offShelfTime',
    //     key: 'offShelfTime',
    //     ellipsis: true,
    // },
    // {
    //     title: '下架原因',
    //     dataIndex: 'offShelfReason',
    //     key: 'offShelfReason',
    //     ellipsis: true,
    // },
    // {
    //     title: '描述',
    //     dataIndex: 'commodityDescribe',
    //     key: 'commodityDescribe',
    //     ellipsis: true,
    // },
]

// 获取所有商品数据
const selectCommodityListByPage = async () => {
    commodityAllListLoading.value = true

    const res: any = await getCommodityByPage(commoditySearchForm.value)

    if (res?.code === 0) {
        commodityAllList.value = res?.data?.commodityResordsList

        commodityAllList.value.map((item: any, index: number) => {
            item.key = index
        })

        pageTotal.value = res?.data?.total
        // commodityAllList.value = res?.data?.commodityResordsList
        message.success('商品数据获取成功')
        commodityAllListLoading.value = false
    } else {
        message.error("数据加载错误，请刷新重新加载")
    }
}

selectCommodityListByPage()

const onShowSizeChange = (current: number, pageSize: number) => {
    commoditySearchForm.value.currentPage = current
    commoditySearchForm.value.pageSize = pageSize
    selectCommodityListByPage()
}

const batchIsUse = ref<boolean>(true)
const selectedRowBatchData = ref<any>()
const selectedRowKeys = ref<any>()

const uploadCommodityVisible = ref<boolean>(false)
const uploadVisible = ref<boolean>(false)
const uploadCommodityText = ref<string>('开始导入')
const uploadCommodityPercent = ref<number>(0)
const batchFileList = ref<any>()
const isBatchUpload = ref<boolean>(false)

// 进度条定时器
const oneLevelTimer = ref()
// 进度条样式
const progress: UploadProps['progress'] = {
    strokeColor: {
        '0%': '#108ee9',
        '100%': '#87d068',
    },
    strokeWidth: 3,
    format: (percent: any) => `${ parseFloat(percent.toFixed(2)) }%`,
    class: 'test',
}

const batchBeforeUpload: UploadProps['beforeUpload'] = (file: any) => {
    batchFileList.value = [...batchFileList.value, file]
    return false
}

const handleUploadBatchChange = (info: UploadChangeParam) => {

    if (info.file.status !== 'uploading') {
        // console.log(info.file, info.fileList)
        isBatchUpload.value = true
    }
    if (info.file.status === 'done') {
        message.success(`${ info.file.name } file uploaded successfully`)
    } else if (info.file.status === 'error') {
        message.error(`${ info.file.name } file upload failed.`)
    }
}

const commodityCommentVisible = ref<boolean>(false)
const commodityCommentData = ref<any>()
// 商品评论
const toCommodityCommentModel = async (item: any) => {
    const res: any = await getCommodityCommentById(item.commodityId)
    commodityCommentData.value = res?.data
    commodityCommentVisible.value = true
}

// 导入excel
const uploadBatchOneLevel = async () => {
    const batchUploadText = ref<string>('开始导入')
    uploadCommodityPercent.value = 0
    uploadCommodityVisible.value = true
    const formData = new FormData()
    formData.append('commodityBatchData', batchFileList.value[0].originFileObj)

    // 发送请求导入数据
    const res = await uploadBatchCommodity(formData)

    oneLevelTimer.value = setInterval(() => {
        batchUploadText.value = '正在导入...'
        uploadCommodityPercent.value += 9
        if (uploadCommodityPercent.value === 90) {
            // 销毁定时器
            clearInterval(oneLevelTimer.value)

            if (res?.code === 0) {
                uploadCommodityPercent.value = 100
                batchUploadText.value = '导入完成'
                uploadCommodityVisible.value = false
                uploadVisible.value = false
                selectCommodityListByPage()
            }
        }
    }, 100)
}

// 多选监听
const onSelectChange = (changableRowKeys: string[], selectedRows: any) => {
    selectedRowBatchData.value = selectedRows
    if (selectedRowBatchData.value.length == 0) {
        batchIsUse.value = true
    } else {
        batchIsUse.value = false
    }
}

// 批量上架
const batchLaunch = async () => {
    // 当前选中的所有商品的商品ID
    const batchCommodityIds: any = {
        batchCommodityId: []
    }
    selectedRowBatchData.value.map((m: any) => {
        batchCommodityIds.batchCommodityId.push(m.commodityId)
    })
    const res: any = await batchLaunchCommodity(batchCommodityIds)
    selectCommodityListByPage()
    res?.code === 0 ? message.success(res?.data) : message.error(res?.msg)
}

// 单个上架
const grounding = async (item: any) => {
    const batchCommodityIds: any = {
        batchCommodityId: []
    }
    batchCommodityIds.batchCommodityId.push(item.commodityId)
    const res: any = await batchLaunchCommodity(batchCommodityIds)
    selectCommodityListByPage()
    res?.code === 0 ? message.success("该商品上架成功") : message.error("该商品上架失败")
}

// 批量下架
const batchOffShelf = async () => {
    // 当前选中的所有商品的商品ID
    const batchCommodityIds: any = {
        batchCommodityId: []
    }
    selectedRowBatchData.value.map((m: any) => {
        batchCommodityIds.batchCommodityId.push(m.commodityId)
    })
    const res: any = await batchOffShelfCommodity(batchCommodityIds)
    selectCommodityListByPage()
    res?.code === 0 ? message.success(res?.data) : message.error(res?.msg)
}
// 单个下架
const undercarriage = async (item: any) => {
    const batchCommodityIds: any = {
        batchCommodityId: []
    }
    batchCommodityIds.batchCommodityId.push(item.commodityId)
    const res: any = await batchOffShelfCommodity(batchCommodityIds)
    selectCommodityListByPage()
    res?.code === 0 ? message.success("该商品下架成功") : message.error("该商品下架失败")
}

// 批量推荐
const batchRecommendation = async () => {
    // 当前选中的所有商品的商品ID
    const batchCommodityIds: any = {
        batchCommodityId: []
    }
    selectedRowBatchData.value.map((m: any) => {
        batchCommodityIds.batchCommodityId.push(m.commodityId)
    })
    const res: any = await batchRecommendationCommodity(batchCommodityIds)
    selectCommodityListByPage()

    res?.code === 0 ? message.success(res?.data) : message.error(res?.msg)
}

// 单个推荐
const recommend = async (item: any) => {
    // 当前选中的所有商品的商品ID
    const batchCommodityIds: any = {
        batchCommodityId: []
    }
    batchCommodityIds.batchCommodityId.push(item.commodityId)
    const res: any = await batchRecommendationCommodity(batchCommodityIds)
    selectCommodityListByPage()

    res?.code === 0 ? message.success("该商品成功推荐为新品") : message.error("该商品推荐为新品失败")
}

// 批量不推荐
const batchNoRecommendation = async () => {
    // 当前选中的所有商品的商品ID
    const batchCommodityIds: any = {
        batchCommodityId: []
    }
    selectedRowBatchData.value.map((m: any) => {
        batchCommodityIds.batchCommodityId.push(m.commodityId)
    })
    const res: any = await batchNoRecommendationCommodity(batchCommodityIds)
    selectCommodityListByPage()

    res?.code === 0 ? message.success(res?.data) : message.error(res?.msg)
}

// 单个不推荐
const noRecommended = async (item: any) => {
    // 当前选中的所有商品的商品ID
    const batchCommodityIds: any = {
        batchCommodityId: []
    }
    batchCommodityIds.batchCommodityId.push(item.commodityId)
    const res: any = await batchNoRecommendationCommodity(batchCommodityIds)
    selectCommodityListByPage()

    res?.code === 0 ? message.success("该商品不推荐为新品成功") : message.error("该商品不推荐为新品失败")
}

const bulkPurchaseVisible = ref<boolean>(false)
const addStockVisible = ref<boolean>(false)
const bulkPurchaseNumberValue = ref<number>(0)
const addStockNumberValue = ref<number>(0)

// 批量进货
const bulkPurchase = () => {
    bulkPurchaseVisible.value = true
}
const bulkPurchaseRequest = async () => {

    // 当前选中的所有商品的商品ID
    const batchCommodityIds: any = {
        batchCommodityId: [],
        commodityNumber: 0
    }
    selectedRowBatchData.value.map((m: any) => {
        batchCommodityIds.batchCommodityId.push(m.commodityId)
    })

    if (Number(bulkPurchaseNumberValue.value) > 0) {
        batchCommodityIds.commodityNumber = Number(bulkPurchaseNumberValue.value)
        const res: any = await bulkPurchaseCommodity(batchCommodityIds)
        selectCommodityListByPage()
        res?.code === 0 ? message.success(res?.data) : message.error(res?.msg)
        bulkPurchaseVisible.value = false
        bulkPurchaseNumberValue.value = 0
    } else {
        message.error("进货数量不能小于1")
    }
}

// 当前选中的所有商品的商品ID
const addStockCommodityIds: any = {
    batchCommodityId: [],
    commodityNumber: 0
}

// 单个进货弹框
const addStock = async (item: any) => {
    addStockVisible.value = true
    addStockCommodityIds.batchCommodityId.push(item.commodityId)
}

// 单个进货请求
const addStockRequest = async () => {
    if (Number(addStockNumberValue.value) > 0) {
        addStockCommodityIds.commodityNumber = Number(addStockNumberValue.value)

        const res: any = await bulkPurchaseCommodity(addStockCommodityIds)
        selectCommodityListByPage()
        res?.code === 0 ? message.success("该商品成功进货") : message.error("该商品进货失败")
        addStockVisible.value = false
        addStockNumberValue.value = 0
    } else {
        message.error("进货数量不能小于1")
    }
}

// 批量放入回收站
const batchDelete = async () => {
    // 当前选中的所有商品的商品ID
    const batchCommodityIds: any = {
        batchCommodityId: []
    }
    selectedRowBatchData.value.map((m: any) => {
        batchCommodityIds.batchCommodityId.push(m.commodityId)
    })
    const res: any = await putInRecycleBinCommodity(batchCommodityIds)
    selectCommodityListByPage()

    res?.code === 0 ? message.success(res?.data) : message.error(res?.msg)
}

// 单个放入回收站
const recycleBin = async (item: any) => {
    // 当前选中的所有商品的商品ID
    const batchCommodityIds: any = {
        batchCommodityId: []
    }
    batchCommodityIds.batchCommodityId.push(item.commodityId)
    const res: any = await putInRecycleBinCommodity(batchCommodityIds)
    selectCommodityListByPage()

    res?.code === 0 ? message.success("该商品成功放入回收站") : message.error("该商品放入回收站失败")
}

const commodityAddForm = ref<any>({
    commodityName: '',
    retailPrice: '',
    wholesalePrice: '',
    commodityImageUrl: '',
    commodityDescribe: '',
    commodityStatus: '',
    commodityType: '',
    salesModel: '',
    meterCompany: '',
    commodityWeight: '',
    commodityRecommend: '',
    commodityStock: '',
    businessName: '',
    oneClassificationName: '',
    twoClassificationName: ''
})

const addRules: Record<string, Rule[]> = {
    commodityName: [{ required: true, message: '请填写商品名称' }],
    commodityStatus: [{ required: true, message: '请选择商品状态' }],
    oneClassificationName: [{ required: true, message: '请选择一级分类' }],
    twoClassificationName: [{ required: true, message: '请选择二级分类' }],
    retailPrice: [{ required: true, message: '请填写零售价格' }],
    wholesalePrice: [{ required: true, message: '请填写批发价格' }],
    salesModel: [{ required: true, message: '请选择销售模式' }],
    commodityType: [{ required: true, message: '请选择商品类型' }],
    businessName: [{ required: true, message: '请选择所属商家' }],
    commodityRecommend: [{ required: true, message: '请选择商品推荐' }],
    commodityWeight: [{ required: true, message: '请填写商品重量' }],
    meterCompany: [{ required: true, message: '请填写计量单位' }],
    commodityStock: [{ required: true, message: '请填写商品数量' }],
}

// 商品详情弹窗
const commodityDetailVisible = ref<boolean>(false)
const commodityDetailData = ref<any>()
const openCommodityDetailModel = (item: any) => {
    commodityDetailVisible.value = true
    commodityDetailData.value = item
}

// 提交按钮事件
const handleFinish = (values: any) => {
    onSubmitTwoLevel()
}
const handleFinishFailed = (errors: any) => {
    console.log(errors)
}
const resetAddForm = () => {
    addFormRef.value?.resetFields()
}

const selectSearchCommodity = () => {
    if (commoditySearchForm.value.createTime.length != 0) {
        commoditySearchForm.value.createStartTime = commoditySearchForm.value.createTime[0]
        commoditySearchForm.value.createEndTime = commoditySearchForm.value.createTime[1]
    }
    if (commoditySearchForm.value.putShelfTime.length != 0) {
        commoditySearchForm.value.putShelfStartTime = commoditySearchForm.value.putShelfTime[0]
        commoditySearchForm.value.putShelfEndTime = commoditySearchForm.value.putShelfTime[1]
    }
    selectCommodityListByPage()
}

const resetForm = () => {
    commoditySearchForm.value.createStartTime = ''
    commoditySearchForm.value.createEndTime = ''
    commoditySearchForm.value.putShelfStartTime = ''
    commoditySearchForm.value.putShelfEndTime = ''
    commoditySearchForm.value.offShelfStartTime = ''
    commoditySearchForm.value.offShelfEndTime = ''
    formRef.value?.resetFields()
}

const parentName = ref<string>('')

const businessNameList = ref<any>()
const getAllBusinessNameList = async () => {
    const res = await getAllBusinessName(commoditySearchForm.value.businessName)
    if (res?.code === 0) {
        businessNameList.value = res?.data
    } else {
        message.error(res?.msg)
    }
}
// 所有一级分类
const oneLevelNameList = ref<any>()

const selectOneLevelNameList = async () => {
    const res = await getParentNameList(parentName.value)

    if (res?.code === 0) {
        oneLevelNameList.value = res?.data
    } else {
        message.error(res?.msg)
    }

    console.log(res)
}

// 所有二级分类
const twoLevelNameList = ref<any>()
const selectTwoLevelNameList = async (e: any) => {
    const res = await getTwoLevelNameList(e)

    if (res?.code === 0) {
        twoLevelNameList.value = res?.data
    } else {
        twoLevelNameList.value = ''
        message.error(res?.msg)
    }

    console.log(res)
}

const fileList = ref<any>([])
const uploading = ref<boolean>(false)
const imageUrl = ref<string>('')

function getBase64 (img: Blob, callback: (base64Url: string) => void) {
    const reader = new FileReader()
    reader.addEventListener('load', () => callback(reader.result as string))
    reader.readAsDataURL(img)
}

const handleRemove: UploadProps['onRemove'] = file => {
    const index = fileList.value.indexOf(file)
    const newFileList = fileList.value.slice()
    newFileList.splice(index, 1)
    fileList.value = newFileList
}

const beforeUpload: UploadProps['beforeUpload'] = file => {
    fileList.value = [...fileList.value, file]
    getBase64(fileList.value[0], (base64Url: string) => {
        imageUrl.value = base64Url
        uploading.value = false
    })

    return false
}

// 添加二级商品分类按钮事件
const onSubmitTwoLevel = async () => {
    // 上传图片
    const formData = new FormData()
    formData.append('commodityServiceImage', fileList.value[0].originFileObj)
    uploading.value = true

    const res: any = await uploadCommodityImage(formData, commodityAddForm.value.businessName, commodityAddForm.value.oneClassificationName, commodityAddForm.value.twoClassificationName, commodityAddForm.value.commodityName)
    if (res?.code === 0) {
        commodityAddForm.value.commodityImageUrl = res?.data
        const response = await addCommodity(commodityAddForm.value)

        addCommodityVisible.value = false

        if (response?.code === 0) {
            addCommodityVisible.value = false
            message.success('商品添加成功')
            resetAddForm()
            selectCommodityListByPage()
        } else {
            message.error(response?.msg)
            resetAddForm()
        }

    } else {
        message.error(res?.msg)
    }

}

</script>

<style scoped></style>
