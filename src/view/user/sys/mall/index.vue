<template>
    <t-card :bordered="false" size="small">
        <t-row>
            <t-col :span="6">
                <div class="flex flex-row">
                    <t-space>
                        <a-button type="primary" @click="showAddUserModal">
                            <template #icon>
                                <DownloadOutlined />
                            </template>
                            新增商家管理员
                        </a-button>
                    </t-space>
                </div>
            </t-col>
            <t-col :span="6">
                <div>
                    <a-form layout="inline" :model="formState" @finish="handleFinish" @finishFailed="handleFinishFailed">
                        <a-form-item>
                            <a-input v-model:value="formState.user" placeholder="账号">
                                <template #prefix>
                                    <UserOutlined style="color: rgba(0, 0, 0, 0.25)" />
                                </template>
                            </a-input>
                        </a-form-item>
                        <a-form-item>
                            <a-select ref="select" v-model:value="value1" placeholder="请选择状态">
                                <a-select-option value="正常">正常</a-select-option>
                                <a-select-option value="已锁定">已锁定</a-select-option>
                            </a-select>
                        </a-form-item>
                        <a-form-item>
                            <a-input v-model:value="formState.password" placeholder="关联角色">
                                <template #prefix>
                                    <LockOutlined style="color: rgba(0, 0, 0, 0.25)" />
                                </template>
                            </a-input>
                        </a-form-item>
                        <a-form-item>
                            <a-button type="primary" html-type="submit"
                                :disabled="formState.user === '' || formState.password === ''">
                                查询
                            </a-button>
                        </a-form-item>
                    </a-form>
                </div>
            </t-col>
        </t-row>
        <t-row>
            <t-col :span="12">
                <div class="mt-16">
                    <a-table :columns="columns" :data-source="adminList" :loading="tableLoading">
                        <template #bodyCell="{ column }">
                            <template v-if="column.key === 'status'">
                                <a-tag color="cyan">正常</a-tag>
                            </template>
                            <template v-else-if="column.key === 'action'">
                                <a-button type="link" size="small">详情</a-button>
                                <a-button type="link" size="small">修改</a-button>
                                <a-popconfirm title="您确定要删除吗?" ok-text="确定" cancel-text="取消">
                                    <template #icon><question-circle-outlined style="color: red" /></template>
                                    <a-button type="link" size="small">删除</a-button>
                                </a-popconfirm>
                                <a-dropdown>
                                    <a-button type="link" size="small">更多</a-button>
                                    <template #overlay>
                                        <a-menu>
                                            <a-menu-item>
                                                <a-button type="link" size="small">锁定</a-button>
                                            </a-menu-item>
                                            <a-menu-item>
                                                <a-button type="link" size="small">解锁</a-button>
                                            </a-menu-item>
                                            <a-menu-item>
                                                <a-button type="link" size="small">重置密码</a-button>
                                            </a-menu-item>
                                            <a-menu-item>
                                                <a-button type="link" size="small">修改密码</a-button>
                                            </a-menu-item>
                                        </a-menu>
                                    </template>
                                </a-dropdown>
                            </template>
                        </template>
                    </a-table>
                </div>
            </t-col>
        </t-row>
        <div>
            <a-modal v-model:visible="addUserVisible" :footer="null" title="新增超级管理员" @ok="AddUserHandleOk">
                <div>
                    <a-form :model="form" :label-col="labelCol" :wrapper-col="wrapperCol">
                        <a-form-item label="Activity name">
                            <a-input v-model:value="form.name" />
                        </a-form-item>
                        <a-form-item label="Instant delivery">
                            <a-switch v-model:checked="form.delivery" />
                        </a-form-item>
                        <a-form-item label="Activity type">
                            <a-checkbox-group v-model:value="form.type">
                                <a-checkbox value="1" name="type">Online</a-checkbox>
                                <a-checkbox value="2" name="type">Promotion</a-checkbox>
                                <a-checkbox value="3" name="type">Offline</a-checkbox>
                            </a-checkbox-group>
                        </a-form-item>
                        <a-form-item label="Resources">
                            <a-radio-group v-model:value="form.resource">
                                <a-radio value="1">Sponsor</a-radio>
                                <a-radio value="2">Venue</a-radio>
                            </a-radio-group>
                        </a-form-item>
                        <a-form-item label="Activity form">
                            <a-input v-model:value="form.desc" type="textarea" />
                        </a-form-item>
                        <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
                            <a-button type="primary" @click="onSubmit">Create</a-button>
                            <a-button style="margin-left: 10px">Cancel</a-button>
                        </a-form-item>
                    </a-form>
                </div>
            </a-modal>
        </div>
    </t-card>
</template>

<script lang="ts" setup>
import { UserOutlined, LockOutlined, QuestionCircleOutlined } from '@ant-design/icons-vue'
import { ref, toRaw } from 'vue'
import type { FormProps } from 'ant-design-vue'
import { getAllAdminList } from '@/api/admin/index'

interface FormState {
    user: string
    password: string
}

interface AddForm {
    name: string
    delivery: boolean
    type: string[]
    resource: string
    desc: string
}

const formState: any = ref<FormState>({
    user: '',
    password: '',
})

const form: any = ref<AddForm>({
    name: '',
    delivery: false,
    type: [],
    resource: '',
    desc: '',
})

const labelCol = ref({ style: { width: '150px' } })
const wrapperCol = ref({ span: 14 })
const addUserVisible = ref<boolean>(false)
const adminList = ref<any>(null)
const value1 = ref<string>('')
// 控制表格加载动画开关
const loading = ref<boolean>(true)
// 表格加载动画自定义设置
const tableLoading = ref<any>({
    size: 'large',
    delay: 100,
    spinning: loading
})

// 获取所有管理员列表信息
getAllAdminList(1).then((res: any) => {
    if (res?.code === 0) {
        adminList.value = res?.data
        loading.value = false
    }
})

const onSubmit = () => {
    console.log('submit!', toRaw(form))
}

const columns = [
    {
        title: '账号',
        dataIndex: 'account',
        key: 'account',
        align: 'center'
    }, {
        title: '关联角色',
        dataIndex: 'roleName',
        key: 'roleName',
        align: 'center'
    }, {
        title: '角色描述',
        dataIndex: 'roleDesc',
        key: 'roleDesc',
        align: 'center'
    }, {
        title: '状态',
        dataIndex: 'status',
        key: 'status',
        align: 'center'
    }, {
        title: '操作',
        dataIndex: 'action',
        key: 'action',
        align: 'center'
    }
]

const showAddUserModal = () => {
    addUserVisible.value = true
}

const AddUserHandleOk = (e: MouseEvent) => {
    console.log(e)
    addUserVisible.value = false
}

const handleFinish: FormProps['onFinish'] = values => {
    console.log(values, formState)
}

const handleFinishFailed: FormProps['onFinishFailed'] = errors => {
    console.log(errors)
}

</script>

<style scoped></style>