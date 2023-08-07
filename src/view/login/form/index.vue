<template>
    <div class="h-full flex flex-row justify-center">
        <div class="flex flex-wrap content-center">
            <div class="flex-grow-0">
                <t-card :title="title" :hoverShadow="hoverShadow" :bordered="false"
                    :style="{ width: '400px', height: '330px' }">
                    <div>
                        <t-form ref="form" :rules="rules" :data="formData" :colon="true" :label-width="0"
                            @reset="onReset" @submit="onSubmit">
                            <t-form-item name="account">
                                <t-input v-model="formData.account" clearable :value="formData.account"
                                    placeholder="请输入账户名">
                                    <template #prefix-icon>
                                        <UserCircleIcon />
                                    </template>
                                </t-input>
                            </t-form-item>

                            <t-form-item name="password" class="mt-4">
                                <t-input v-model="formData.password" type="password" :value="formData.password"
                                    clearable placeholder="请输入密码">
                                    <template #prefix-icon>
                                        <lock-on-icon />
                                    </template>
                                </t-input>
                            </t-form-item>

                            <t-form-item name="code" class="mt-4">
                                <div class="flex flex-row justify-between">
                                    <div class="w-56">
                                        <t-input v-model="formData.code" clearable placeholder="请输入验证码">
                                            <template #prefix-icon>
                                                <ViewListIcon />
                                            </template>
                                        </t-input>
                                    </div>
                                    <div class="ml-2 -mt-0.5">
                                        <div @click="refreshCode">
                                            <SIdentify :identifyCode="identifyCode" />
                                        </div>
                                    </div>
                                </div>
                            </t-form-item>

                            <t-form-item class="mt-4">
                                <t-button theme="primary" type="submit" shape="round" block>登&nbsp;录</t-button>
                            </t-form-item>
                        </t-form>
                    </div>
                </t-card>
            </div>
        </div>
    </div>
</template>
<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { MessagePlugin, LoadingPlugin } from 'tdesign-vue-next'
import { UserCircleIcon, LockOnIcon, ViewListIcon } from 'tdesign-icons-vue-next'
import router from '@/router/index'
import SIdentify from '@/components/SIdentify.vue'
import { adminLogin, adminMenu } from '@/api/admin/index'
import { userStore } from '@/store/modules/user'

const state = userStore()

const title = '账号登录'
const hoverShadow = ref<boolean>(true)

const formData = ref({
    account: 'admin',
    password: 'E-YouHui@2023',
    // account: 'merchant_admin_1'
    // password: 'merchant-1@2023',
    code: '',
    rememberMe: false
})
// 图形验证码
// let identifyCodes = '1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'
let identifyCodes = '1234567890'
let identifyCode = ref('')
const iCode = ref('')

const randomNum = (min: number, max: number) => {
    return Math.floor(Math.random() * (max - min) + min)
}

const makeCode = (o: string | any[], l: number) => {
    for (let i = 0; i < l; i++) {
        identifyCode.value += o[
            randomNum(0, o.length)
        ]
    }
    iCode.value = identifyCode.value
}

// 刷新验证码
const refreshCode = () => {
    identifyCode.value = ""
    makeCode(identifyCodes, 4)
}

onMounted(() => {
    identifyCode.value = ""
    makeCode(identifyCodes, 4)
})

// 密码校验器
const passwordValidator = (val: string | any[]) => {
    if (val.length > 0 && val.length <= 2) {
        return { result: false, message: '太简单了！再开动一下你的小脑筋吧！', type: 'error' }
    }
    if (val.length > 2 && val.length < 4) {
        return { result: false, message: '还差一点点，就是一个完美的密码了！', type: 'warning' }
    }
    return { result: true, message: '太强了，你确定自己记得住吗！', type: 'success' }
}

// 验证码校验器
const codeValidator = (val: string | any[]) => {
    if (val === iCode.value) {
        return { result: true, message: '验证码输入正确', type: 'success' }
    } else {
        return { result: false, message: '验证码输入错误', type: 'error' }
    }
}

const rules = {
    account: [
        { required: true, message: '账号必填', type: 'error' },
        {
            min: 2,
            message: '至少需要两个字',
            type: 'error',
            trigger: 'blur',
        },
    ],
    password: [{ required: true, message: '密码必填', type: 'error' }, { validator: passwordValidator }],
    code: [{ required: true, message: '验证码必填', type: 'error' }, { validator: codeValidator }]
}

// 登录按钮事件函数
const onSubmit = async ({ validateResult, firstError, e }: any) => {
    e.preventDefault()
    if (validateResult === true) {

        await adminLogin(formData.value).then(async (res: any) => {
            if (res?.code === 0) {
                // 设置 isLogin 登录状态为true
                state.setIsLogin(true)
                // 设置 userInfo 信息
                state.setUserInfo(JSON.parse(JSON.stringify(res?.data)))
                // 设置 token
                state.setToken(res?.msg)

                MessagePlugin.success('登录成功')
                MessagePlugin.success('正在初始化系统，请稍后...')
                // 函数式：全屏加载，防止滚动穿透
                LoadingPlugin(true)

                // 获取用户菜单
                await adminMenu().then(r => {
                    if (r?.code === 0) {

                        // 设置 userMenu
                        state.setUserMenu(r?.data)

                        router.push({ path: "/dashboard" })
                        LoadingPlugin(false)

                    }
                })
            }
        })
    } else {
        MessagePlugin.warning(firstError)
    }
}

const onReset = () => {
    MessagePlugin.success('重置成功')
}

</script>

<style scoped>

</style>
