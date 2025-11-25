import {createApp} from 'vue'
// import './style.css'
// 导入组件
import ElementPlus from 'element-plus'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'


// 导入css样式文件
import 'element-plus/dist/index.css'
import router from "./router/router.js";
import App from "./App.vue"; // 根组件 其余页面根据路由地址显示

let app = createApp(App);

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.use(ElementPlus).use(router).mount('#app')
