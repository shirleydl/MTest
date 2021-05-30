<template>
    <div class="sidebar">
        <el-menu class="sidebar-el-menu" :default-active="onRoutes" :collapse="collapse" background-color="#324157" text-color="#bfcbd9" active-text-color="#20a0ff" unique-opened router>
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template #title>
                            <i :class="item.icon"></i>
                            <span>{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <el-submenu v-if="subItem.subs" :index="subItem.index" :key="subItem.index">
                                <template #title>{{ subItem.title }}</template>
                                <el-menu-item v-for="(threeItem, i) in subItem.subs" :key="i" :index="threeItem.index">{{ threeItem.title }}</el-menu-item>
                            </el-submenu>
                            <el-menu-item v-else :index="subItem.index" :key="subItem.index">{{ subItem.title }}</el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        <template #title>{{ item.title }}</template>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>
<script>
// import bus from "../common/bus";
export default {
    data() {
        return {
            items: [{
                    icon: "el-icon-lx-home",
                    index: "readme",
                    title: "首页"
                },
                {
                    icon: "el-icon-lx-right",
                    index: "demand",
                    title: "需求"
                },
                {
                    icon: "el-icon-lx-right",
                    index: "2",
                    title: "用例库",
                    subs: [{
                            icon: "el-icon-lx-right",
                            index: "systems",
                            title: "系统"
                        },
                        {
                            icon: "el-icon-lx-right",
                            index: "pageModules",
                            title: "页面模块"
                        },
                        {
                            icon: "el-icon-lx-right",
                            index: "functions",
                            title: "功能"
                        },
                        {
                            icon: "el-icon-lx-right",
                            index: "testPoint",
                            title: "测试点"
                        },
                        {
                            icon: "el-icon-lx-right",
                            index: "export",
                            title: "导入"
                        }
                    ]
                }

            ]
        };
    },
    computed: {
        onRoutes() {
            return this.$route.path.replace("/", "");
        },
        collapse() {
            return this.$store.state.collapse
        }
    }
};
</script>
<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 60px;
    bottom: 0;
    overflow-y: scroll;
}

.sidebar::-webkit-scrollbar {
    width: 0;
}

.sidebar-el-menu:not(.el-menu--collapse) {
    width: 210px;
}

.sidebar>ul {
    height: 100%;
}
</style>