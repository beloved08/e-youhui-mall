<template>
  <!-- 加载动画 -->
  <a-spin tip="正在加载中..." :spinning="spinning">
    <!-- <a-card :bordered="false"> -->
    <div class="flex flex-row m-6">
      <div class="mr-6 text-lg ">搜索条件</div>
      <div class="components-input-demo-size flex flex-row">
        <div class="w-32 mr-3">
          <a-input style="width: 100%;" v-model:value="merchantStore.businessName" placeholder="商家名称"
            @change="getMerchantStoreList" />
        </div>
        <div class="w-32 mr-3">
          <a-input style="width: 100%;" v-model:value="merchantStore.shopName" placeholder="店铺名称"
            @change="getMerchantStoreList" />
        </div>
        <div class="w-32 mr-3">
          <a-select style="width: 100%;" v-model:value="businessStatusValue" label-in-value placeholder="商家状态"
            @change="businessStatusValueChange">
            <a-select-option value="0">正常</a-select-option>
            <a-select-option value="1">已注销</a-select-option>
            <a-select-option value="2">未审核</a-select-option>
            <a-select-option value="3">已审核</a-select-option>
            <a-select-option value="4">信息有误</a-select-option>
          </a-select>
        </div>
        <div class="w-32">
          <a-select style="width: 100%;" v-model:value="verifyStatusValue" label-in-value placeholder="审核状态"
            @change="verifyStatusValueChange">
            <a-select-option value="0">审核通过</a-select-option>
            <a-select-option value="1">审核不通过</a-select-option>
            <a-select-option value="2">审核信息有误</a-select-option>
            <a-select-option value="3">待审核</a-select-option>
          </a-select>
        </div>
      </div>
    </div>
    <!-- </a-card> -->
    <!-- 商家店铺表格数据 -->
    <div class="mt-2">
      <a-card title="商家店铺" :bordered="true">
        <a-table :columns="columns" :data-source="dataSource" :pagination="false" :row-selection="rowSelection">
          <template #bodyCell="{ column, record }">
            <template v-if="column.key === 'businessStatus'">
              <a-tag v-if="record.businessStatus == 0" color="blue">正常</a-tag>
              <a-tag v-if="record.businessStatus == 1" color="red">已注销</a-tag>
              <a-tag v-if="record.businessStatus == 2" color="pink">未审核</a-tag>
              <a-tag v-if="record.businessStatus == 3" color="purple">已审核</a-tag>
              <a-tag v-if="record.businessStatus == 4" color="cyan">信息有误</a-tag>
            </template>
            <template v-if="column.key === 'verifyStatus'">
              <a-tag v-if="record.verifyStatus == 0" color="#2db7f5">审核通过</a-tag>
              <a-tag v-if="record.verifyStatus == 1" color="#f50">审核不通过</a-tag>
              <a-tag v-if="record.verifyStatus == 2" color="#87d068">信息有误</a-tag>
              <a-tag v-if="record.verifyStatus == 3" color="#108ee9">待审核</a-tag>
            </template>
            <template v-if="column.key === 'businessLogo'">
              <!-- <img class="h-12 w-12" :src="record.businessLogo" /> -->
              <a-image :width="60" @click="imgVisible = true" :preview="{
                imgVisible, onVisibleChange: () => imgVisible = true,
              }" :src="record.businessLogo" />
            </template>
            <template v-if="column.key === 'action'">
              <a-button type="link" size="medium" @click="showDetailModal(record)"><template #icon><t-icon
                    name="error-circle" /></template>
              </a-button>
              <a-button type="link" size="medium" @click="openNoPassExamineVerify(record)"><template #icon><t-icon
                    name="close" /></template></a-button>
              <a-popconfirm title="您确定要审核通过吗？" ok-text="通过" cancel-text="取消" @confirm="passExamineVerify(record)">
                <template #icon><question-circle-outlined style="color: red" /></template>
                <a-button type="link" size="medium"><template #icon><t-icon name="check" /></template></a-button>
              </a-popconfirm>
              <a-dropdown>
                <a-button type="link" size="medium"><template #icon><t-icon name="ellipsis" /></template>
                </a-button>
                <template #overlay>
                  <a-menu>
                    <a-menu-item>
                      <a-button type="link" size="small" @click="logOffBusiness(record)">注销商家</a-button>
                    </a-menu-item>
                    <a-menu-item>
                      <a-button type="link" size="small" @click="openCityMap(record)">城市地址</a-button>
                    </a-menu-item>
                    <a-menu-item>
                      <a-button type="link" size="small" @click="openDetailAddressMap(record)">详细地址</a-button>
                    </a-menu-item>
                  </a-menu>
                </template>
              </a-dropdown>
            </template>
          </template>
        </a-table>
        <div class="mt-8 mb-4 flex justify-end">
          <a-pagination size="small" v-model:current="merchantStore.currentPage" :page-size-options="pageSizeOptions"
            :hideOnSinglePage="false" :show-total="(pageTotal: number) => `共 ${ pageTotal } 条 `" show-size-changer
            @showSizeChange="onShowSizeChange" @change="onShowSizeChange" v-model:page-size="merchantStore.pageSize"
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
    </div>
  </a-spin>
  <!-- 审核不通过按钮区域 -->
  <div>
    <a-modal v-model:visible="noPassVisible" title="商家店铺审核不通过的原因" :closable="false" :footer="null">
      <div class="flex flex-col">
        <div>
          <a-select style="width: 100%;" allowClear label-in-value placeholder="审核状态"
            @change="setVerifyStatusValueChange">
            <a-select-option value="1">审核不通过</a-select-option>
            <a-select-option value="2">信息有误</a-select-option>
          </a-select>
        </div>
        <div class="mt-2 mb-6">
          <a-input v-model:value="verifyData.verifyDesc" placeholder="请您填写不通过的原因" />
        </div>
      </div>
      <div class="mt-2 flex flex-row justify-around">
        <t-button theme="warning" class="mr-2" @click="noPassVisible = false">
          取消
        </t-button>
        <t-button theme="primary" @click="noPassExamineVerify">
          提交
        </t-button>
      </div>
    </a-modal>
  </div>
  <div>
    <a-modal v-model:visible="cityMapShowModal" :destroyOnClose="true" width="1300px" title="店铺所在详细地址" :footer="null">
      <Map :mapDetailData="mapDetailData" />
    </a-modal>
  </div>
  <!-- 新增商家店铺弹窗 -->
  <div>
    <a-modal v-model:visible="addShowModal" title="新增商家店铺" :footer="null" @ok="handleOk">
      <a-form :model="formState1" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-form-item label="Activity name">
          <a-input v-model:value="formState1.name" />
        </a-form-item>
        <a-form-item label="Instant delivery">
          <a-switch v-model:checked="formState1.delivery" />
        </a-form-item>
        <a-form-item label="Activity type">
          <a-checkbox-group v-model:value="formState1.type">
            <a-checkbox value="1" name="type">Online</a-checkbox>
            <a-checkbox value="2" name="type">Promotion</a-checkbox>
            <a-checkbox value="3" name="type">Offline</a-checkbox>
          </a-checkbox-group>
        </a-form-item>
        <a-form-item label="Resources">
          <a-radio-group v-model:value="formState1.resource">
            <a-radio value="1">Sponsor</a-radio>
            <a-radio value="2">Venue</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="Activity form">
          <a-input v-model:value="formState1.desc" type="textarea" />
        </a-form-item>
        <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
          <a-button type="primary" @click="onSubmit">Create</a-button>
          <a-button style="margin-left: 10px">Cancel</a-button>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
  <!-- 详情 -->
  <div>
    <a-modal v-model:visible="detailVisible" :footer="null" centered width="900px">
      <a-descriptions title="店铺信息">
        <a-descriptions-item label="商家名称">{{ showDetailModalData.businessData.businessName }}</a-descriptions-item>
        <a-descriptions-item label="店铺名称">{{ showDetailModalData.businessData.businessName }}</a-descriptions-item>
        <a-descriptions-item label="商家电话">{{ showDetailModalData.businessData.businessPhone }}</a-descriptions-item>
        <a-descriptions-item label="商家状态">
          <a-tag v-if="showDetailModalData.businessData.businessStatus == 0" color="blue">正常</a-tag>
          <a-tag v-if="showDetailModalData.businessData.businessStatus == 1" color="red">已注销</a-tag>
          <a-tag v-if="showDetailModalData.businessData.businessStatus == 2" color="pink">未审核</a-tag>
          <a-tag v-if="showDetailModalData.businessData.businessStatus == 3" color="purple">已审核</a-tag>
          <a-tag v-if="showDetailModalData.businessData.businessStatus == 4" color="cyan">信息有误</a-tag>
        </a-descriptions-item>
        <a-descriptions-item label="审核状态">
          <a-tag v-if="showDetailModalData.businessData.verifyStatus == 0" color="#2db7f5">审核通过</a-tag>
          <a-tag v-if="showDetailModalData.businessData.verifyStatus == 1" color="#f50">审核不通过</a-tag>
          <a-tag v-if="showDetailModalData.businessData.verifyStatus == 2" color="#87d068">信息有误</a-tag>
          <a-tag v-if="showDetailModalData.businessData.verifyStatus == 3" color="#108ee9">待审核</a-tag>
        </a-descriptions-item>
        <a-descriptions-item label="所在城市">{{ showDetailModalData.businessData.businessCity }}</a-descriptions-item>
        <a-descriptions-item label="所在城市经度">{{ showDetailModalData.businessData.businessCityLatitude
        }}</a-descriptions-item>
        <a-descriptions-item label="所在城市纬度">{{ showDetailModalData.businessData.businessCityLongitude
        }}</a-descriptions-item>
        <a-descriptions-item label="详细地址经度">{{ showDetailModalData.businessData.businessDetailAddressLatitude
        }}</a-descriptions-item>
        <a-descriptions-item label="详细地址纬度">{{ showDetailModalData.businessData.businessDetailAddressLongitude
        }}</a-descriptions-item>
        <a-descriptions-item label="详细地址">{{ showDetailModalData.businessData.businessDetailAddress
        }}</a-descriptions-item>
        <a-descriptions-item label="商家店铺描述">{{ showDetailModalData.businessData.businessDescribe }}</a-descriptions-item>
      </a-descriptions>
      <a-descriptions title="店铺管理员">
        <a-descriptions-item label="申请人">{{ showDetailModalData.businessUserData.businessUserName }}</a-descriptions-item>
        <a-descriptions-item label="性别">{{ showDetailModalData.businessUserData.businessUserSex }}</a-descriptions-item>
        <a-descriptions-item label="年龄">{{ showDetailModalData.businessUserData.businessUserAge }}</a-descriptions-item>
        <a-descriptions-item label="电话号码">{{ showDetailModalData.businessUserData.businessUserPhone
        }}</a-descriptions-item>
        <a-descriptions-item label="身份证号码">{{ showDetailModalData.businessUserData.businessUserIdCard
        }}</a-descriptions-item>
        <a-descriptions-item label="电子邮箱">{{ showDetailModalData.businessUserData.businessUserEmail
        }}</a-descriptions-item>
      </a-descriptions>
    </a-modal>
  </div>
  <!-- 注销商家弹框 -->
  <div>
    <a-modal v-model:visible="logOffVisible" title="商家注销" width="650px">
      <template #footer>
        <a-button key="back" @click="logOffVisible = false">取消</a-button>
        <a-button key="submit" type="primary" @click="handleLogOff">确认注销</a-button>
      </template>
      <div>
        <a-descriptions>
          <a-descriptions-item label="商家名称">{{ logOffData.businessName }}</a-descriptions-item>
          <a-descriptions-item label="店铺名称">{{ logOffData.shopName }}</a-descriptions-item>
          <a-descriptions-item label="所在城市">{{ logOffData.businessCity }}</a-descriptions-item>
          <a-descriptions-item label="商家电话">{{ logOffData.businessPhone }}</a-descriptions-item>
          <a-descriptions-item label="详细地址">{{ logOffData.businessDetailAddress }}</a-descriptions-item>
        </a-descriptions>
      </div>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { message } from 'ant-design-vue'
import { ref } from "vue"
import { QuestionCircleOutlined } from '@ant-design/icons-vue'
import { getMerchantStorePage, getBusinessUser, residentAudit, logOffBusinessById } from '@/api/merchantStore/index'
import Map from '@/components/map.vue'

const spinning = ref<boolean>(false)

const dataSource = ref<any>()
// 商家店铺列信息
const columns = ref<any>([
  {
    title: "商家名称",
    dataIndex: "businessName",
    key: "businessName",
    align: "center",
    // width: 90,
    fixed: 'left'
  },
  {
    title: "店铺名称",
    dataIndex: "shopName",
    key: "shopName",
    align: "center",
    // width: 90,
  },
  {
    title: "店铺Logo",
    dataIndex: "businessLogo",
    key: "businessLogo",
    align: "center",
    // width: 100,
  },
  {
    title: "商家电话",
    dataIndex: "businessPhone",
    key: "businessPhone",
    align: "center",
    // width: 130,
  },
  {
    title: "商家状态",
    dataIndex: "businessStatus",
    key: "businessStatus",
    align: "center",
    // width: 90,
  },
  {
    title: "审核状态",
    dataIndex: "verifyStatus",
    key: "verifyStatus",
    align: "center",
    // width: 90,
  },
  {
    title: "所在城市",
    dataIndex: "businessCity",
    key: "businessCity",
    align: "center",
    // width: 90,
  },
  {
    title: "详细地址",
    dataIndex: "businessDetailAddress",
    key: "businessDetailAddress",
    align: "center",
    // width: 250,
  },
  {
    title: "描述",
    dataIndex: "businessDescribe",
    key: "businessDescribe",
    align: "center",
    // width: 200,
  },
  {
    title: "操作",
    dataIndex: "action",
    key: "action",
    align: "center",
    width: 160,
    fixed: 'right'
  }
])

const rowSelection = ref({
  checkStrictly: false,
  onChange: (selectedRowKeys: (string | number)[]) => {
    console.log(`selectedRowKeys: ${ selectedRowKeys }`)
  },
  onSelect: (selected: boolean) => {
    console.log(selected)
  },
  onSelectAll: (selected: boolean) => {
    console.log(selected)
  },
})

const imgVisible = ref<boolean>(false)
const detailVisible = ref<boolean>(false)

// 新增商家店铺弹窗控件
const addShowModal = ref<boolean>(false)
const verifyStatusValue = ref<any>(false)

interface merchantStorePage {
  pageSize: number,
  currentPage: number,
  businessName: string,
  shopName: string,
  businessStatus: number,
  verifyStatus: number
}

const merchantStore = ref<merchantStorePage>({
  pageSize: 5,
  currentPage: 1,
  businessName: '',
  shopName: '',
  businessStatus: 2,
  verifyStatus: 3
})

const pageTotal = ref<number>()
const pageSizeOptions = ref<string[]>(['5', '10', '20', '50', '100'])
const businessStatusValue = ref<any>()

// 获取所有商家店铺数据
const getMerchantStoreList = async () => {
  spinning.value = true
  const res: any = await getMerchantStorePage(merchantStore.value)

  if (res?.code == 0) {
    message.success('店铺数据获取成功')
    spinning.value = false
    dataSource.value = res?.data?.records
    pageTotal.value = res?.data?.total
  } else {
    message.error('店铺数据获取失败')
    spinning.value = false
  }
}

getMerchantStoreList()

const onShowSizeChange = (current: number, pageSize: number) => {
  merchantStore.value.currentPage = current
  merchantStore.value.pageSize = pageSize
  getMerchantStoreList()
}

const businessStatusValueChange = (e: any) => {
  merchantStore.value.businessStatus = Number(e.value)
  getMerchantStoreList()
}

const verifyStatusValueChange = (e: any) => {
  merchantStore.value.verifyStatus = Number(e.value)
  getMerchantStoreList()
}

const logOffVisible = ref<boolean>(false)
const logOffData = ref<any>()

// 注销商家
const logOffBusiness = (e: any) => {
  logOffVisible.value = true
  logOffData.value = e

}
// 发送商家注销请求
const handleLogOff = async () => {
  spinning.value = true

  const res: any = await logOffBusinessById(logOffData.value.businessId)

  if (res?.code == 0) {
    spinning.value = false
    logOffVisible.value = false
    message.success(res?.data)
    getMerchantStoreList()
  }

}

const showDetailModalData = ref<any>({ businessData: {}, businessUserData: {} })

const showDetailModal = async (item: any) => {
  detailVisible.value = true
  const res = await getBusinessUser(item.businessId)
  if (res?.code == 0) {
    showDetailModalData.value.businessData = item
    showDetailModalData.value.businessUserData = res?.data
  }
}

const noPassVisible = ref<boolean>(false)

const verifyData = ref<any>({
  verifyDesc: '',
  businessId: '',
  businessUserPhone: '',
  businessStatus: 0,
  verifyStatus: 0
})

const setVerifyStatusValueChange = (e: any) => {
  verifyData.value.verifyStatus = Number(e.value)
}

const businessResidentAudit = async (data: any) => {
  spinning.value = true
  const res: any = await residentAudit(data)

  if (res?.code == 0) {
    message.success(res?.data)
    spinning.value = false
    getMerchantStoreList()
  }
}

const noPassExamineVerifyUserData = ref<any>()

// 打开审核不通过弹框
const openNoPassExamineVerify = async (item: any) => {
  spinning.value = true

  noPassVisible.value = true
  verifyData.value.businessId = item.businessId

  const res: any = await getBusinessUser(item.businessId)

  if (res?.code == 0) {
    spinning.value = false
    noPassExamineVerifyUserData.value = res?.data
  } else {
    message.error("数据获取失败")
  }
}

// 不通过审核
const noPassExamineVerify = async () => {

  verifyData.value.businessStatus = Number(4)
  verifyData.value.businessUserPhone = noPassExamineVerifyUserData.value.businessUserPhone

  businessResidentAudit(verifyData.value)
  noPassVisible.value = false
}

// 通过审核
const passExamineVerify = async (item: any) => {
  spinning.value = true
  const res: any = await getBusinessUser(item.businessId)

  verifyData.value.businessStatus = 0
  verifyData.value.verifyStatus = 0
  if (res?.code == 0) {
    spinning.value = false
    verifyData.value.businessUserPhone = res?.data?.businessUserPhone
  }

  verifyData.value.businessId = item.businessId
  businessResidentAudit(verifyData.value)
}

// 地图组件数据
const mapDetailData = ref<any>({
  businessData: {},
  businessUserData: {},
  use: 0,
  mapData: {
    longitude: 0,
    latitude: 0,
    iconPath: '',
    calloutContent: '',
    address: ''
  }
})
const cityMapShowModal = ref<boolean>(false)

// 打开定位商家店铺所在城市信息地图
const openCityMap = async (item: any) => {
  spinning.value = true

  const res = await getBusinessUser(item.businessId)
  if (res?.code == 0) {
    spinning.value = false
    mapDetailData.value.businessUserData = res?.data

    mapDetailData.value.businessData = item
    mapDetailData.value.mapData.longitude = item.businessCityLongitude
    mapDetailData.value.mapData.latitude = item.businessCityLatitude
    mapDetailData.value.mapData.iconPath = item.iconPath
    mapDetailData.value.mapData.calloutContent = item.calloutContent
    mapDetailData.value.mapData.address = item.businessDetailAddress

    cityMapShowModal.value = true

    message.success('地图渲染成功')
  } else {
    message.error('数据获取失败')
    spinning.value = false
  }
}

// 打开定位商家店铺详细地址信息地图
const openDetailAddressMap = async (item: any) => {
  spinning.value = true
  const res = await getBusinessUser(item.businessId)
  if (res?.code == 0) {
    spinning.value = false
    mapDetailData.value.businessUserData = res?.data

    mapDetailData.value.businessData = item
    mapDetailData.value.mapData.longitude = item.businessDetailAddressLongitude
    mapDetailData.value.mapData.latitude = item.businessDetailAddressLatitude
    mapDetailData.value.mapData.iconPath = item.businessLogo
    mapDetailData.value.mapData.calloutContent = item.businessDetailAddress
    mapDetailData.value.mapData.address = item.businessDetailAddress

    cityMapShowModal.value = true

    message.success('地图渲染成功')
  } else {
    message.error('数据获取失败')
    spinning.value = false
  }
}
</script>

<style scoped>
.components-input-demo-size .ant-input {
  width: 200px;
  margin: 0 8px 8px 0;
}
</style>