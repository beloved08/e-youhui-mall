<template>
    <!-- 商品评论弹窗 -->
    <div v-if="commodityCommentData.length > 0" v-for="(item, index) in commodityCommentData" :key="index">
        <a-comment>
            <template #author><a>{{ item.userName }}</a></template>
            <template #avatar>
                <a-avatar :src="item.avatarUrl" alt="avatar" />
            </template>
            <template #content>
                <p>
                    {{ item.commentContent }}
                </p>
            </template>
            <template #datetime>
                <a-tooltip :title="dayjs().format('YYYY年MM月DD HH:mm:ss')">
                    <span>{{ item.time }}</span>
                </a-tooltip>
            </template>
        </a-comment>
    </div>
    <div v-else>
        <div class="flex justify-center">
            <a-empty
                image="https://gw.alipayobjects.com/mdn/miniapp_social/afts/img/A*pevERLJC9v0AAAAAAAAAAABjAQAAAQ/original"
                :image-style="{
                    height: '150px',
                }">
                <template #description>
                    <span>
                        暂无商品评论
                    </span>
                </template>
            </a-empty>
        </div>
    </div>
</template>

<script lang="ts">
export default {
    name: 'CommodityComment',
}
</script>

<script setup lang="ts">
import { LikeFilled, LikeOutlined, DislikeFilled, DislikeOutlined } from '@ant-design/icons-vue'
import { ref, toRefs } from 'vue'
import relativeTime from 'dayjs/plugin/relativeTime'
import dayjs from 'dayjs'

dayjs.extend(relativeTime)

const props = defineProps({
    commodityCommentData: []
})

const commodityCommentData: any = toRefs(props).commodityCommentData

console.log(commodityCommentData.value)

const likes = ref<number>(0)
const dislikes = ref<number>(0)
const action = ref<string>()

const like = () => {
    likes.value = 1
    dislikes.value = 0
    action.value = 'liked'
}

const dislike = () => {
    likes.value = 0
    dislikes.value = 1
    action.value = 'disliked'
}
</script>

<style scoped></style>