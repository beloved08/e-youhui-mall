import { createRouter, createWebHashHistory } from 'vue-router'
import { generateRouter } from '@/utils/router'
import { userStore } from '@/store/modules/user'
import { MessagePlugin } from 'tdesign-vue-next'
import { Modal } from 'ant-design-vue'
import { createVNode } from 'vue'
import { CloseCircleFilled } from '@ant-design/icons-vue'

const publicRoutes: any[] = [
    {
        path: '/',
        name: 'Login',
        meta: { isLogin: false },
        component: () => import('@/view/login/index.vue')
    },
    // {
    //     path: '/map',
    //     name: 'Map',
    //     meta: { isLogin: false },
    //     component: () => import('@/components/map.vue')
    // },
    // {
    //     path: '/datav',
    //     name: 'Datav',
    //     meta: { isLogin: false },
    //     component: () => import('@/view/test-dataV/index.vue')
    // },
    {
        path: '/dashboard',
        name: 'Dashboard',
        meta: { isLogin: true },
        component: () => import('@/layout/index.vue'),
        children: [
            {
                path: '/dashboard',
                name: 'Home',
                meta: { isLogin: true },
                component: () => import('@/view/admin-dashboard/index.vue')
            }
        ]
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes: publicRoutes
})

/**
 * 全局前置路由守卫，每一次路由跳转前都进入这个 beforeEach 函数
 */
router.beforeEach((to, from, next) => {
    if (to.path == '/') {
        // 登录或者注册才可以往下进行
        next()
    } else {
        const dashboardRouter = publicRoutes.filter((r: any) => r.path === '/dashboard')[0]
        // 判断是否已经动态添加路由
        if (dashboardRouter.children.length >= 3) {
            // 已经加载路由
            // 判断进入该路由是否需要登录
            if (to.meta.isLogin) {
                // 需要登录
                // 判断是否登录
                if (userStore().getToken === null || userStore().getToken === '' || !userStore().getIsLogin) {
                    // 用户未登录
                    MessagePlugin.error('授权信息失效，请重新登录授权')
                    Modal.confirm({
                        title: '授权信息失效，请重新登录授权！！！',
                        icon: createVNode(CloseCircleFilled),
                        okText: '立即登录授权',
                        cancelText: '暂不登录',
                        centered: true,
                        onOk () {
                            next('/')
                        },
                        onCancel () {
                            MessagePlugin.warning('您选择了暂不登录 ovo')
                        },
                    })
                } else {
                    next()
                }
            } else {
                next()
            }
        } else {
            // 重新加载路由，并跳转到对应页面，解决刷新页面空白问题
            const menuData = JSON.parse(JSON.stringify(userStore().getUserMenu))
            const routerData = generateRouter(publicRoutes, dashboardRouter, menuData)
            const notFoundRouter = {
                path: '/:error*',
                name: '404',
                meta: { isLogin: false },
                component: () => import('@/components/404.vue')
            }

            router.addRoute(notFoundRouter)
            router.addRoute(routerData)

            next({ ...to })
        }
    }
})

export default router

