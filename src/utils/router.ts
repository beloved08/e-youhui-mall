export const formatRouterTree = ((data: any[]) => {

    const translateDataToTree = () => {

        let parents = data.filter((p: { pid: number }) => p.pid === 0)
        let children = data.filter((c: { pid: number }) => c.pid !== 0)

        let translator = (parents: any[], children: { pid: any }[]) => {
            parents.map((p: { mid: any; children: any[] }) => {
                children.map((c: { pid: any }, index: any) => {
                    if (c.pid === p.mid) {
                        let _c = JSON.parse(JSON.stringify(children))
                        _c.splice(index, 1)
                        translator([c], _c)
                        if (p.children) {
                            p.children.push(c)
                        } else {
                            p.children = [c]
                        }
                    }
                })
            })
        }

        // 调用转换方法
        translator(parents, children)
        //返回
        return parents
    }
    return translateDataToTree()
})

export const generateRouter = (publicRoutes: any, dashboardRouter: any, res: any): any => {

    res.map((ur: any) => {
        if (ur?.cid === '0') {
            const urlPath = ur?.component.split('/')
            if (urlPath.length === 1) {
                dashboardRouter.children.push({
                    path: ur?.path,
                    name: ur?.name,
                    meta: { isLogin: ur?.isLogin },
                    component: () => import(`@/view/${ urlPath[0] }/index.vue`)
                })

            } else if (urlPath.length === 2) {
                dashboardRouter.children.push({
                    path: ur?.path,
                    name: ur?.name,
                    meta: { isLogin: ur?.isLogin },
                    component: () => import(`@/view/${ urlPath[0] }/${ urlPath[1] }/index.vue`)
                })

            } else if (urlPath.length === 3) {
                dashboardRouter.children.push({
                    path: ur?.path,
                    name: ur?.name,
                    meta: { isLogin: ur?.isLogin },
                    component: () => import(`@/view/${ urlPath[0] }/${ urlPath[1] }/${ urlPath[2] }/index.vue`)
                })
            }

        }
    })

    return dashboardRouter
}
