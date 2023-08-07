import { defineStore } from 'pinia'

export const userStore = defineStore('user', {
    state: () => {
        return {
            // 是否登录
            isLogin: false,
            // token
            token: '',
            // 用户信息
            userInfo: {},
            // 用户菜单
            userMenu: [],
        }
    },
    // 开启数据缓存
    persist: {
        enabled: true,
        strategies: [
            {
                storage: localStorage,
                paths: ['isLogin', 'token', 'userInfo', 'userMenu']
            }
        ]
    },
    getters: {
        // 获取登录状态
        getIsLogin (): boolean {
            return this.isLogin
        },
        // 获取用户信息
        getUserInfo (): any {
            return this.userInfo
        },
        // 获取用户token
        getToken (): string {
            return this.token
        },
        // 获取用户菜单按钮
        getUserMenu (): any {
            return this.userMenu
        }
    },
    actions: {
        // 设置登录状态
        setIsLogin (login: boolean): void {
            this.isLogin = login
        },
        // 设置用户信息
        setUserInfo (user: any): void {
            this.userInfo = user
        },
        // 设置token
        setToken (token: string): void {
            this.token = token
        },
        // 设置用户菜单
        setUserMenu (userMenu: any): void {
            this.userMenu = userMenu
        }
    }
})
