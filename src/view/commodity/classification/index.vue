<template>
  <!-- 顶部区域 -->
  <div class="flex flex-row justify-between m-4">
    <div class="flex flex-row">
      <a-button type="primary" danger @click="addClassificationVisible = true">
        <template #icon><t-icon name="add" /></template>
        添加商品类别
      </a-button>
    </div>
    <div class="components-input-demo-size">
      <a-input v-model:value="pageQuery.classificationName" placeholder="请输入分类名称" />
      <a-button @click="sectClassificationList" type="primary"><t-icon name="search" />
      </a-button>
    </div>
  </div>
  <!-- 添加商品类别弹框 -->
  <div>
    <a-modal v-model:visible="addClassificationVisible" :footer="null">
      <a-tabs v-model:activeKey="addClassificationActiveKey" centered type="card">
        <a-tab-pane key="1">
          <template #tab>
            <span class="flex flex-row">
              <div class="-mt-1"><apartment-outlined /></div>
              一级商品类别
            </span>
          </template>
          <a-collapse v-model:activeKey="batchOneActiveKey" :bordered="false">
            <a-collapse-panel key="1" header="手动添加" :style="customStyle">
              <div class="mt-4 ml-8">
                <a-form :model="oneLevelClassification">
                  <a-form-item label="分类名称">
                    <a-input v-model:value="oneLevelClassification.classificationName" />
                  </a-form-item>
                  <a-form-item label="分类描述">
                    <a-input v-model:value="oneLevelClassification.classificationDescribe" type="textarea" />
                  </a-form-item>
                  <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
                    <a-button type="primary" @click="onSubmitOneLevel">提交</a-button>
                    <a-button style="margin-left: 10px">取消</a-button>
                  </a-form-item>
                </a-form>
              </div>
            </a-collapse-panel>
          </a-collapse>
          <a-collapse v-model:activeKey="batchOneActiveKey" :bordered="false">
            <a-collapse-panel key="2" header="批量导入" :style="customStyle">
              <div class="text-xs text-red-600 mb-4">批量导入时，必须先导入一级商品分类</div>
              <div class="flex flex-row justify-between">
                <div class="ml-4">
                  <a-upload v-model:file-list="batchFileList" :maxCount="1" name="classificationBatchLevel"
                    :progress="progress" :before-upload="batchBeforeUpload" @change="handleUploadBatchChange">
                    <a-button type="primary">
                      <upload-outlined></upload-outlined>
                      批量导入一级商品分类数据
                    </a-button>
                  </a-upload>
                </div>
                <a-button type="primary" class="ml-4" v-if="isBatchUpload"
                  @click="uploadBatchOneLevel('0')">开始导入</a-button>
              </div>
              <div>
                <a-modal v-model:visible="batchOneLevelVisible" :width="130" :centered="true" :closable="false"
                  :maskClosable="false" :keyboard="false" :footer="false">
                  <div class="flex flex-col justify-items-center">
                    <a-progress type="circle" :percent="oneLevelDefaultPercent" :width="82" />
                    <span class="mt-2 text-sm text-center">{{ batchUploadText }}</span>
                  </div>
                </a-modal>
              </div>
            </a-collapse-panel>
          </a-collapse>
        </a-tab-pane>
        <a-tab-pane key="2">
          <template #tab>
            <span class="flex flex-row">
              <div class="-mt-1"><apartment-outlined /></div>
              二级商品类别
            </span>
          </template>
          <a-collapse v-model:activeKey="batchOneActiveKey" :bordered="false">
            <a-collapse-panel key="1" header="手动添加" :style="customStyle">
              <div class="mt-4">
                <a-form :model="oneLevelClassification">
                  <a-form-item label="分类名称">
                    <a-input v-model:value="twoLevelClassification.classificationName" />
                  </a-form-item>
                  <a-form-item label="一级分类">
                    <a-select ref="select" v-model:value="parentNameSearch" show-search @focus="selectParentNameList"
                      @change="handleChange">
                      <a-select-option v-if="oneLevelClassificationNameList"
                        v-for="(item, index) in oneLevelClassificationNameList" :key="index" :value="item">{{ item
                        }}</a-select-option>
                    </a-select>
                  </a-form-item>
                  <a-form-item label="分类图标">
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
                  <a-form-item label="分类描述">
                    <a-input v-model:value="twoLevelClassification.classificationDescribe" type="textarea" />
                  </a-form-item>
                  <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
                    <a-button type="primary" @click="onSubmitTwoLevel">提交</a-button>
                    <a-button style="margin-left: 10px">取消</a-button>
                  </a-form-item>
                </a-form>
              </div>
            </a-collapse-panel>
          </a-collapse>
          <a-collapse :bordered="false">
            <a-collapse-panel key="2" header="批量导入" :style="customStyle">
              <div class="text-xs text-red-600 mb-4">批量导入时，必须先导入一级商品分类</div>
              <div class="flex flex-row justify-between">
                <div class="ml-4">
                  <a-upload v-model:file-list="batchFileList" :maxCount="1" name="classificationBatchLevel"
                    :progress="progress" :before-upload="batchBeforeUpload" @change="handleUploadBatchChange">
                    <a-button type="primary">
                      <upload-outlined></upload-outlined>
                      批量导入二级商品分类数据
                    </a-button>
                  </a-upload>
                </div>
                <a-button type="primary" class="ml-4" v-if="isBatchUpload"
                  @click="uploadBatchOneLevel('1')">开始导入</a-button>
              </div>
              <div>
                <a-modal v-model:visible="batchOneLevelVisible" :width="130" :centered="true" :closable="false"
                  :maskClosable="false" :keyboard="false" :footer="false">
                  <div class="flex flex-col justify-items-center">
                    <a-progress type="circle" :percent="oneLevelDefaultPercent" :width="82" />
                    <span class="mt-2 text-sm text-center">{{ batchUploadText }}</span>
                  </div>
                </a-modal>
              </div>
            </a-collapse-panel>
          </a-collapse>
        </a-tab-pane>
      </a-tabs>
    </a-modal>
  </div>
  <!-- 商品分类表格 -->
  <div class="mt-10 ml-4 mr-4">
    <a-table :columns="columns" :loading="tableLoading" size="small" :pagination="false" :indentSize="50"
      :data-source="tableData" tableLayout="fixed">
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'classificationIcon'">
          <div v-if="record.classificationIcon">
            <img style="height: 50px; width: 60px;" :src="record.classificationIcon" />
          </div>
          <div v-else>
            暂无图标
          </div>
        </template>
        <template v-if="column.key === 'classificationGrade'">
          <div v-if="record.classificationGrade === 0">
            <span>
              <a-tag color="#2db7f5">一级分类</a-tag>
            </span>
          </div>
          <div v-else>
            <span><a-tag color="#87d068">二级分类</a-tag></span>
          </div>
        </template>
        <template v-if="column.key === 'action'">
          <a-button type="link" size="medium" @click="classificationDetail(record)">
            <template #icon>
              <t-icon name="error-circle" />
            </template>
          </a-button>
          <a-popconfirm title="此操作将删除该分类及其下级分类，您确定要删除吗?" placement="topRight" @confirm="deleteClassificationRequest"
            ok-text="确定" cancel-text="取消">
            <template #icon><question-circle-outlined style="color: red" /></template>
            <a-button type="link" size="medium" @click="deleteClassification(record)">
              <template #icon><t-icon name="delete" /></template>
            </a-button>
          </a-popconfirm>
        </template>
      </template>
    </a-table>
    <div class="mt-8 mb-4 flex justify-end">
      <a-pagination size="small" v-model:current="pageQuery.currentPage" :page-size-options="pageSizeOptions"
        :hideOnSinglePage="false" :show-total="(pageTotal: number) => `共 ${ pageTotal } 条 `" show-size-changer
        @showSizeChange="onShowSizeChange" @change="onShowSizeChange" v-model:page-size="pageQuery.pageSize"
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
  </div>
  <!-- 商品分类详情弹框 -->
  <div>
    <a-modal width="700px" v-model:visible="classificationDetailVisible" :footer="null" v-if="classificationDetailData"
      :title="classificationDetailData.classificationName">
      <div>
        <a-descriptions bordered>
          <a-descriptions-item label="分类名称">{{ classificationDetailData.classificationName }}</a-descriptions-item>
          <a-descriptions-item label="分类等级">
            <div v-if="classificationDetailData.classificationGrade === 0">
              <span>
                <a-tag color="#2db7f5">一级分类</a-tag>
              </span>
            </div>
            <div v-else>
              <span><a-tag color="#87d068">二级分类</a-tag></span>
            </div>
          </a-descriptions-item>
          <a-descriptions-item label="分类描述">{{ classificationDetailData.classificationDescribe }}</a-descriptions-item>
          <a-descriptions-item label="分类图标">
            <div v-if="classificationDetailData.classificationIcon">
              <img style="height: 80px; width: 80px;" :src="classificationDetailData.classificationIcon" />
            </div>
            <div v-else>
              暂无图标
            </div>
          </a-descriptions-item>
        </a-descriptions>
      </div>
    </a-modal>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ApartmentOutlined, UploadOutlined, PlusOutlined, LoadingOutlined, QuestionCircleOutlined } from '@ant-design/icons-vue'
import { message } from 'ant-design-vue'
import type { UploadProps } from 'ant-design-vue'
import type { UploadChangeParam } from 'ant-design-vue'

import {
  uploadClassificationIcon,
  addCommodityClassification,
  getParentNameList,
  getParentId,
  getClassificationListPage,
  deleteClassificationById,
  uploadBatchLeade
} from '@/api/commodity/index'

const columns = [
  {
    title: '分类名称',
    dataIndex: 'classificationName',
    key: 'classificationName',
  },
  {
    title: '分类等级',
    dataIndex: 'classificationGrade',
    key: 'classificationGrade',
  },
  {
    title: '分类图标',
    dataIndex: 'classificationIcon',
    key: 'classificationIcon',
  },
  {
    title: '分类描述',
    dataIndex: 'classificationDescribe',
    key: 'classificationDescribe',
  },
  {
    title: '操作',
    dataIndex: 'action',
    key: 'action',
    align: "center"
  },
]

const tableData = ref<any>([
  {
    key: 0,
    classificationGrade: 0,
    classificationName: '',
    classificationDescribe: '',
    classificationIcon: '',
    action: '',
    children: [
      {
        key: 0,
        classificationGrade: 1,
        classificationName: '',
        classificationDescribe: '',
        action: '',
        classificationIcon: '',
      },
    ],
  }
])

const tableLoading = ref<boolean>(false)

const pageTotal = ref<number>()
const pageSizeOptions = ref<string[]>(['5', '10', '20', '50', '100'])

const pageQuery = ref<any>({
  currentPage: 1,
  pageSize: 6,
  classificationName: ''
})

// 获取所有商品分类数据
const sectClassificationList = async () => {
  tableLoading.value = true
  const classificationList: any = await getClassificationListPage(pageQuery.value)

  if (classificationList?.code == 0) {
    tableData.value = classificationList?.data?.classificationResordsList
    pageTotal.value = classificationList?.data?.total

    tableData.value.map((item: any, index: number) => {
      item.key = index
      item?.children.map((child: any, index: number) => {
        child.key = index
      })
    })
    tableLoading.value = false
    message.success("商品分类数据获取成功")
  } else {
    message.error("数据加载错误，请刷新重新加载")
  }
}
sectClassificationList()

const onShowSizeChange = (current: number, pageSize: number) => {
  pageQuery.value.currentPage = current
  pageQuery.value.pageSize = pageSize
  sectClassificationList()
}

const batchOneActiveKey = ref(['1'])
const batchUploadText = ref<string>('开始导入')
const oneLevelDefaultPercent = ref<number>(0)
const batchOneLevelVisible = ref<boolean>(false)
const customStyle = 'background: #f7f7f7;border-radius: 4px;margin-bottom: 24px;border: 0;overflow: hidden'
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

// 导入excel
const uploadBatchOneLevel = async (e: string) => {
  const batchUploadText = ref<string>('开始导入')
  oneLevelDefaultPercent.value = 0
  batchOneLevelVisible.value = true
  const formData = new FormData()
  formData.append('classificationBatchLevel', batchFileList.value[0].originFileObj)

  // 发送请求导入数据
  const res = await uploadBatchLeade(formData, e)

  oneLevelTimer.value = setInterval(() => {
    batchUploadText.value = '正在导入...'
    oneLevelDefaultPercent.value += 9
    if (oneLevelDefaultPercent.value === 90) {
      // 销毁定时器
      clearInterval(oneLevelTimer.value)

      if (res?.code === 0) {
        oneLevelDefaultPercent.value = 100
        batchUploadText.value = '导入完成'
        batchOneLevelVisible.value = false
        addClassificationVisible.value = false

        sectClassificationList()
      }
    }
  }, 100)
}

// 封装删除分类的参数
const deleteParam = ref<any>({
  classificationGrade: 0,
  classificationId: ''
})

// 删除商品分类
const deleteClassification = (item: any) => {
  deleteParam.value.classificationId = item.classificationId
  if (item.classificationGrade === 0) {
    // 删除一级分类
    deleteParam.value.classificationGrade = 0
  } else {
    //删除二级分类
    deleteParam.value.classificationGrade = 1
  }
}
// 分类详情空间
const classificationDetailVisible = ref<boolean>(false)
// 分类详情数据
const classificationDetailData = ref<any>()
// 商品分类详情
const classificationDetail = (item: any) => {
  classificationDetailData.value = item
  classificationDetailVisible.value = true
}

// 确认删除商品分类
const deleteClassificationRequest = async () => {
  const res: any = await deleteClassificationById(deleteParam.value)

  if (res?.code === 0) {
    message.success(res?.data)
    sectClassificationList()
  } else {
    message.error(res?.msg)
  }
}

// 添加商品弹框空间
const addClassificationVisible = ref<Boolean>(false)
const addClassificationActiveKey = ref<string>('1')

const oneLevelClassification = ref<any>({
  classificationName: '',
  classificationDescribe: '',
  classificationGrade: 0,
  classificationIcon: '',
  parentId: ''
})

const oneLevelClassificationNameList = ref<any>()

const parentNameSearch = ref<any>("")
// 获取所有一级分类名称
const selectParentNameList = async () => {

  const nameList = await getParentNameList(parentNameSearch.value)

  if (nameList?.code === 0) {
    oneLevelClassificationNameList.value = nameList?.data
  } else {
    message.warning(nameList?.msg)
  }
}

const twoLevelClassification = ref<any>({
  classificationName: '',
  classificationDescribe: '',
  classificationGrade: 1,
  classificationIcon: '',
  parentId: '',
})

// 获取父级分分类ID
const handleChange = async (value: string) => {
  const res = await getParentId(value)
  twoLevelClassification.value.parentId = res?.data
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

// 清空一级分类表单内容
const resetOneLevel = () => {
  oneLevelClassification.value.classificationDescribe = ''
  oneLevelClassification.value.classificationName = ''
  oneLevelClassification.value.classificationGrade = 0
  oneLevelClassification.value.classificationIcon = ''
  oneLevelClassification.value.parentId = ''
}

// 添加一级商品分类按钮事件
const onSubmitOneLevel = async () => {
  //上传表单
  const res = await addCommodityClassification(oneLevelClassification.value)

  if (res?.code === 0) {
    addClassificationVisible.value = false
    message.success('一级商品类别添加成功')
    resetOneLevel()
    sectClassificationList()
  } else {
    message.error(res?.msg)
    resetOneLevel()
  }
}

// 清空二级分类表单内容
const resetTwoLevel = () => {
  parentNameSearch.value = ''
  twoLevelClassification.value.classificationName = ''
  twoLevelClassification.value.classificationDescribe = ''
  twoLevelClassification.value.classificationGrade = 1
  twoLevelClassification.value.parentId = ''
  twoLevelClassification.value.classificationIcon = ''
  imageUrl.value = ''
}

// 添加二级商品分类按钮事件
const onSubmitTwoLevel = async () => {
  // 上传图片
  const formData = new FormData()
  formData.append('classificationIcon', fileList.value[0].originFileObj)
  uploading.value = true

  const res: any = await uploadClassificationIcon(formData, twoLevelClassification.value.classificationName)
  if (res?.code === 0) {
    uploading.value = false
    twoLevelClassification.value.classificationIcon = res?.data
    //上传表单
    const response = await addCommodityClassification(twoLevelClassification.value)

    if (response?.code === 0) {
      addClassificationVisible.value = false
      message.success('二级商品类别添加成功')
      resetTwoLevel()
      sectClassificationList()
    } else {
      message.error(response?.msg)
      resetTwoLevel()
    }
  } else {
    message.error(res?.msg)
  }
}
</script>

<style scoped>
.components-input-demo-size .ant-input {
  width: 200px;
  margin: 0 8px 8px 0;
}
</style>