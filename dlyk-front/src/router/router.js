import {createRouter, createWebHistory} from "vue-router";

// var let 和 const 定义变量的区别
const router = createRouter({
    // 路由地址
    history: createWebHistory(),
    // 配置路由
    routes: [
        {
            path: '/',
            component: () => import('../view/LoginView.vue')
        },
        {
            path: '/dashboard', component: () => import('../view/Dashboard.vue'),

            // 子路由
            children: [
                {
                    // 路由路径
                    path: 'user',
                    // 路由页面
                    component: () => import('../view/UserView.vue'),
                }
            ]
        }
    ]
})

export default router;