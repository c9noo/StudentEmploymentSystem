export const constRoutes = [
    // 登录路由
    {
        // 路由的路径
        path: '/login',
        // 路由的name
        name: 'login',
        // 路由懒加载
        component: () => import('@/view/login/index.vue'),
        // 路由的原信息
        meta: {
            title: '登录',
            hidden: true
        }
    },
    // 任意路由
    {
        path: '/:pathMatch(.*)*',
        redirect: '/404',
        name: 'Any',
        meta: {
            title: '任意路由',
            hidden: true
        }
    },
    {
        path: '/404',
        component: () => import('@/view/404/index.vue'),
        name: '404',//命名路由 主要是权限的控制
        meta: {
            title: '404',
            hidden: true
        }
    },
    // 首页路由
    {
        // 路由的路径
        path: '/',
        // 路由的name
        name: 'layout',
        // 路由懒加载
        component: () => import('@/layout/index.vue'),
        meta:{
            title:'',
            hidden:false
        },
        // 重定向到home首页
        redirect: '/home',
        // 子级路由
        children: [
            {
                path: '/home',
                component: () => import('@/view/home/index.vue'),
                name: 'home',
                meta: {
                    title: '首页',//菜单的名称
                    hidden: false,//控制路由的显示与隐藏
                    icon:'HomeFilled'
                }
            }
        ]
    },
    // 数据大屏
    {
        path: '/dataDashboard',
        component: () => import('@/view/dataDashboard/index.vue'),
        name: 'dataDashboard',//命名路由 主要是权限的控制
        meta: {
            title: '数据大屏',
            hidden: false,
            icon:'Monitor'
        }
    },
    // 企业管理
    {
        path: '/companyManagement',
        component: () => import('@/layout/index.vue'),
        name: 'companyManagement',//命名路由 主要是权限的控制
        redirect: '/companyManagement/companyInfo',
        meta: {
            title: '企业招聘',
            hidden: false,
            icon:'OfficeBuilding'
        },
        // 子级路由
        children: [
            {
                path: '/companyManagement/companyInfo',
                component: () => import('@/view/companyManagement/companyInfo/index.vue'),
                name: 'companyInfo',
                meta: {
                    title: '企业管理',//菜单的名称
                    hidden: false,//控制路由的显示与隐藏
                    icon:'Tickets'
                }
            },
            {
                path: '/companyManagement/jobPostings',
                component: () => import('@/view/companyManagement/jobPostings/index.vue'),
                name: 'jobPostings',
                meta: {
                    title: '招聘管理',//菜单的名称
                    hidden: false,//控制路由的显示与隐藏
                    icon:'Memo'
                }
            },
            {
                path: '/companyManagement/resumeView',
                component: () => import('@/view/companyManagement/resumeView/index.vue'),
                name: 'resumeView',
                meta: {
                    title: '简历管理',//菜单的名称
                    hidden: false,//控制路由的显示与隐藏
                    icon:'Document'
                }
            },

        ]
    },
    // 班级管理
    {
        path: '/schoolManagement',
        component: () => import('@/layout/index.vue'),
        name: 'schoolManagement',//命名路由 主要是权限的控制
        redirect: '/schoolManagement/teacherManagement',
        meta: {
            title: '学校管理',
            hidden: false,
            icon:'School'
        },
        // 子级路由
        children: [
            {
                path: '/schoolManagement/teacherManagement',
                component: () => import('@/view/schoolManagement/teacherManagement/index.vue'),
                name: 'teacherManagement',
                meta: {
                    title: '教师管理',//菜单的名称
                    hidden: false,//控制路由的显示与隐藏
                    icon:'Postcard'
                }
            },
            {
                path: '/schoolManagement/alumniNetwork',
                component: () => import('@/view/schoolManagement/alumniNetwork/index.vue'),
                name: 'alumniNetwork',
                meta: {
                    title: '校友网络',//菜单的名称
                    hidden: false,//控制路由的显示与隐藏
                    icon:'Opportunity'
                }
            },
            {
                path: '/schoolManagement/studentManagement',
                component: () => import('@/view/schoolManagement/studentManagement/index.vue'),
                name: 'studentManagement',
                meta: {
                    title: '学生管理',//菜单的名称
                    hidden: false,//控制路由的显示与隐藏
                    icon:'Memo'
                }
            },
        ]
    },
    // 权限管理
    {
        path:'/other',
        component:() => import('@/layout/index.vue'),
        name:'other',
        meta:{
            title:'其他',//菜单的名称
            hidden:false,//控制路由的显示与隐藏
            icon:'MoreFilled',//路由的图标
        },
        redirect:'/other/recruitActivity',
        children:[
            {
                path:'/other/recruitActivity',
                component:() => import('@/view/other/recruitActivity/index.vue'),
                name:'recruitActivity',
                meta:{
                    title:'招聘活动',//菜单的名称
                    hidden:false,//控制路由的显示与隐藏
                    icon:'MagicStick',//路由的图标
                },
            },
            {
                path:'/other/resumeReview',
                component:() => import('@/view/other/resumeReview/index.vue'),
                name:'resumeReview',
                meta:{
                    title:'简历审核',//菜单的名称
                    hidden:false,//控制路由的显示与隐藏
                    icon:'Tickets',//路由的图标
                },
            },
            {
                path:'/other/aclControl',
                component:() => import('@/view/other/aclControl/index.vue'),
                name:'aclControl',
                meta:{
                    title:'权限控制',//菜单的名称
                    hidden:false,//控制路由的显示与隐藏
                    icon:'Pointer',//路由的图标
                },
            },
        ]

    },
]