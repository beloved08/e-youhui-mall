# Vite-TS-Pinia

## Pinia

Pinia 是 Vue 的存储库，它允许您跨组件/页面共享状态

Pinia优点：

* **dev-tools 支持**

  跟踪动作、突变的时间线

  Store 出现在使用它们的组件中

  time travel 和 更容易的调试

* **热模块更换**

  在不重新加载页面的情况下修改您的 Store

  在开发时保持任何现有状态

* **插件**

  ​	使用插件扩展 Pinia 功能

* 为 JS 用户提供适当的 TypeScript 支持或 **autocompletion**

* 服务器端渲染支持

## 项目环境搭建

### 创建vue3、vite、ts项目

~~~javascript
npm create vite@latest vite-TS-Pinia --template vue-ts
~~~

### 安装 Pinia

~~~javascript
npm install pinia
~~~

创建一个 pinia（根存储）并将其传递给应用程序

~~~typescript
import { createPinia } from 'pinia'

app.use(createPinia())
~~~

## Pinia

/src/store/user.ts

### 创建Store

~~~typescript
import { defineStore } from 'pinia'

// useStore 可以是 useUser、useCart 之类的任何东西
// 第一个参数是应用程序中 store 的唯一 id
export const useStore = defineStore('main', {
  // other options...
})
~~~

### 使用Store

~~~typescript
import { useUserStore } from './store/user.js';

const userStore = useUserStore();

console.log(userStore)
~~~

![65941709443](C:\Users\29787\AppData\Local\Temp\1659417094435.png)

### 添加state

~~~typescript
import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
    state: () => {
        return {
            name: 'lp',
            age: 25
        }
    }
})
~~~

### 操作state

#### App.vue中读取数据

~~~typescript
import { useUserStore } from './store/user.js';
import { ref } from 'vue';

const userStore = useUserStore();

const name = ref<String>(userStore.name)
const age = ref<number>(userStore.age)

// 解构
const { name, age } = userStore;
~~~

#### 修改store数据

~~~typescript
import { storeToRefs } from 'pinia';

// storeToRefs()它将为任何响应式属性创建 refs
const { name, age } = storeToRefs(userStore);

const modify = () => {
    userStore.name = 'zs'
    console.log(userStore)
}
~~~

#### 重置state

~~~typescript
// 重置state
const reset = () => {
    userStore.$reset()
}
~~~

#### 批量修改 state 数据

~~~typescript
// 批量修改state数据
const patchStore = () => {
    userStore.$patch({
        name: 'ts',
        age: 22
    })

    console.log(userStore)
}
~~~

### getters

Getter 完全等同于 Store 状态的 [计算值](https://v3.vuejs.org/guide/reactivity-computed-watchers.html#computed-values)。 它们可以用 `defineStore()` 中的 `getters` 属性定义。 他们接收“状态”作为第一个参数**以鼓励**箭头函数的使用：

~~~typescript
getters: {
        getAddAge: (state) => {
            return state.age + 100
        },
         getAddAge2: (state) => {
            return (num: number) => state.age + num
        },
        // 调用其他gettter
        getNameAndAge(): string {
            return this.name + this.getAddAge
        }
    }
~~~

~~~vue
<p>新年龄： {{ userStore.getAddAge }}</p>
~~~

~~~vue
 <p>姓名 + 年龄 {{ userStore.getNameAndAge }}</p>
~~~

~~~vue
<p>新年龄： {{ userStore.getAddAge2(200) }}</p>
~~~

### actions属性

Actions 相当于组件中的 [methods](https://v3.vuejs.org/guide/data-methods.html#methods)。 它们可以使用 `defineStore()` 中的 `actions` 属性定义，并且**它们非常适合定义业务逻辑**：

~~~typescript
actions: {
        saveName(name: string){
            this.name = name
        }
    }
~~~

~~~typescript
const save = () => {
  userStore.saveName('lala')
  console.log(userStore)
}
~~~







