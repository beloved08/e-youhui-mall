import Axios from "@/utils/request"
import { URL } from "@/config"

/** 导入接口返回类型 */
import { resType } from "@/api/index"
import { MessagePlugin } from 'tdesign-vue-next'

/**
 * 测试前后端连通
 * @returns 
 */
export const testGoeasy = async () => {
    return await Axios<resType>({
        url: '/search/goeasy',
        method: 'get'
    }).catch((err: any) => {
        MessagePlugin.warning(err)
    })
}