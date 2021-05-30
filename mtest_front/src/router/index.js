import {createRouter, createWebHistory} from "vue-router";
import Home from "../views/Home.vue";

const routes = [
    {
        path: '/',
        redirect: '/readme'
    }, {
        path: "/",
        name: "Home",
        component: Home,
        children: [
        {
                path: "/readme",
                name: "readme",
                meta: {
                    title: '首页'
                },
                component: () => import (
                /* webpackChunkName: "dashboard" */
                "../views/Readme.vue")
            }, 
            {
                path: "/dashboard",
                name: "dashboard",
                meta: {
                    title: '首页2'
                },
                component: () => import (
                /* webpackChunkName: "dashboard" */
                "../views/Export.vue")
            }, {
                path: "/systems",
                name: "systems",
                meta: {
                    title: '系统'
                },
                component: () => import (
                /* webpackChunkName: "table" */
                "../views/Systems.vue")
            },{
                path: "/pageModules",
                name: "pageModules",
                meta: {
                    title: '页面模块'
                },
                component: () => import (
                /* webpackChunkName: "table" */
                "../views/PageModules.vue")
            },{
                path: "/functions",
                name: "functions",
                meta: {
                    title: '功能'
                },
                component: () => import (
                /* webpackChunkName: "table" */
                "../views/Functions.vue")
            },{
                path: "/testPoint",
                name: "testPoint",
                meta: {
                    title: '测试点'
                },
                component: () => import (
                /* webpackChunkName: "table" */
                "../views/TestPoint.vue")
            },{
                path: "/export",
                name: "export",
                meta: {
                    title: '导入'
                },
                component: () => import (
                /* webpackChunkName: "table" */
                "../views/Export.vue")
            },{
                path: "/demand",
                name: "demand",
                meta: {
                    title: '需求'
                },
                component: () => import (
                /* webpackChunkName: "table" */
                "../views/Demand.vue")
            },{
                path: "/demandDetail",
                name: "demandDetail",
                meta: {
                    title: '用例详情'
                },
                component: () => import (
                /* webpackChunkName: "table" */
                "../views/DemandDetail.vue")
            },{
                path: "/demandMindDetail",
                name: "demandMindDetail",
                meta: {
                    title: '用例详情'
                },
                component: () => import (
                /* webpackChunkName: "table" */
                "../views/DemandMindDetail.vue")
            },{
                path: "/maxMind",
                name: "maxMind",
                meta: {
                    title: '用例详情'
                },
                component: () => import (
                /* webpackChunkName: "table" */
                "../views/MaxMind.vue")
            },{
                path: "/table",
                name: "basetable",
                meta: {
                    title: '表格'
                },
                component: () => import (
                /* webpackChunkName: "table" */
                "../views/BaseTable.vue")
            }, {
                path: "/charts",
                name: "basecharts",
                meta: {
                    title: '图表'
                },
                component: () => import (
                /* webpackChunkName: "charts" */
                "../views/BaseCharts.vue")
            }, {
                path: "/form",
                name: "baseform",
                meta: {
                    title: '表单'
                },
                component: () => import (
                /* webpackChunkName: "form" */
                "../views/BaseForm.vue")
            }, {
                path: "/tabs",
                name: "tabs",
                meta: {
                    title: 'tab标签'
                },
                component: () => import (
                /* webpackChunkName: "tabs" */
                "../views/Tabs.vue")
            }, {
                path: "/donate",
                name: "donate",
                meta: {
                    title: '鼓励作者'
                },
                component: () => import (
                /* webpackChunkName: "donate" */
                "../views/Donate.vue")
            }, {
                path: "/permission",
                name: "permission",
                meta: {
                    title: '权限管理',
                    permission: true
                },
                component: () => import (
                /* webpackChunkName: "permission" */
                "../views/Permission.vue")
            }, {
                path: "/i18n",
                name: "i18n",
                meta: {
                    title: '国际化语言'
                },
                component: () => import (
                /* webpackChunkName: "i18n" */
                "../views/I18n.vue")
            }, {
                path: "/upload",
                name: "upload",
                meta: {
                    title: '上传插件'
                },
                component: () => import (
                /* webpackChunkName: "upload" */
                "../views/Upload.vue")
            }, {
                path: "/icon",
                name: "icon",
                meta: {
                    title: '自定义图标'
                },
                component: () => import (
                /* webpackChunkName: "icon" */
                "../views/Icon.vue")
            }, {
                path: '/404',
                name: '404',
                meta: {
                    title: '找不到页面'
                },
                component: () => import (/* webpackChunkName: "404" */
                '../views/404.vue')
            }, {
                path: '/403',
                name: '403',
                meta: {
                    title: '没有权限'
                },
                component: () => import (/* webpackChunkName: "403" */
                '../views/403.vue')
            }
        ]
    }, {
        path: "/login",
        name: "Login",
        meta: {
            title: '登录'
        },
        component: () => import (
        /* webpackChunkName: "login" */
        "../views/Login.vue")
    }
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    base: "/mtest/",
    routes
});


router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title} | MTest`;
    const role = localStorage.getItem('ms_username');
    if (!role && to.path !== '/login') {
        next('/login');
    } else if (to.meta.permission) {
        // 如果是管理员权限则可进入，这里只是简单的模拟管理员权限而已
        role === 'admin'
            ? next()
            : next('/403');
    } else {
        next();
    }
});

export default router;