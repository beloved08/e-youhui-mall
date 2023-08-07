<template>
  <div class="flex flex-row justify-between">
    <a-button type="primary" @click="showModal">
      <template #icon><t-icon name="add" /></template>
      添加员工
    </a-button>
    <div class="components-input-demo-size">
    <a-input v-model:value="value" placeholder="请输入关键字" />
    <a-button type="primary"><t-icon name="search" />
    </a-button>
    </div>
  </div>
  
    <a-modal v-model:visible="visible" title="添加员工" @ok="handleOk">
<a-form>
      
    <a-form-item label="姓名" name="name">
      <a-input v-model="formData.name" placeholder="请输入内容" ></a-input>
    </a-form-item>

    <a-form-item label="联系方式" name="number">
      <a-input v-model="formData.number" placeholder="请输入内容" ></a-input>
    </a-form-item>
    <a-form-item label="性别" name="sex">
      <t-radio-group v-model="formData.sex">
        <t-radio value="1">男</t-radio>
        <t-radio value="2">女</t-radio>
      </t-radio-group>
      </a-form-item>
      <a-form-item label="职位" name="option">
      <a-select
    v-model:value="value"
    label-in-value
    style="width: 120px"
    :options="options"
   
  ></a-select>
</a-form-item>

      </a-form>

    </a-modal>
 
  <div class="mt-10">
    <a-table :columns="columns" :data-source="dataSource">
      <template #bodyCell="{ column }">
        <template v-if="column.key === 'action'">
          <a-button type="link" size="medium" @click="showReviseModal">
            <template #icon><t-icon name="edit-1" /></template>
            </a-button
          >
          <a-popconfirm
            title="您确定要删除吗?"
            ok-text="确定"
            cancel-text="取消"
          >
            <template #icon
              ><question-circle-outlined style="color: red"
            /></template>
            <a-button type="link" size="medium">
              <template #icon><t-icon name="delete" /></template>
              </a-button>
          </a-popconfirm>
        </template>
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { defineComponent,reactive, ref } from "vue";
import type { UnwrapRef } from "vue";
import type { FormProps } from "ant-design-vue";
import type { SelectProps } from 'ant-design-vue';

const icon_size = ref<string>("large");
const visible = ref<boolean>(false);

const options = ref<SelectProps['options']>([
      {
        value: '店长',
        label: '店长',
      },
      {
        value: '员工',
        label: '员工',
      },
    ]);
    

const showModal = () => {
  visible.value = true;
};

const handleOk = (e: MouseEvent) => {
  console.log(e);
  visible.value = false;
};

const formData = reactive({
  name: '',
  number:'',
  sex:'',
});

const dataSource = ref<any>([
  {
    key: "1",
    name: "卷毛呆呆",
    sex:"女",
    idnumber:"530324200011042124",
    age: 32,
    position:"店长",
    number: "13678744147",
  },
  {
    key: "2",
    name: "小板凳",
    sex:"女",
    idnumber:"530323200406062124",
    age: 42,
    position:"客服",
    number: "18313602527",
  },
]);
const columns = ref<any>([
  {
    title: "姓名",
    dataIndex: "name",
    key: "name",
    align: "center",
  },
  {
    title: "性别",
    dataIndex: "sex",
    key: "sex",
    align: "center",
  },
  {
    title: "职位",
    dataIndex: "position",
    key: "position",
    align: "center",
  },
  {
    title: "身份证号",
    dataIndex: "idnumber",
    key: "idnumber",
    align: "center",
  },
  {
    title: "联系方式",
    dataIndex: "number",
    key: "number",
    align: "center",
  },
  {
    title: "操作",
    dataIndex: "action",
    key: "action",
    align: "center",
  },
]);
</script>

<style scoped>


.components-input-demo-size .ant-input {
  width: 200px;
  margin: 0 8px 8px 0;
}

</style>