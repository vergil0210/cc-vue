import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/register',
    component: () => import('@/views/register/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: 'dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: 'Dashboard', icon: 'dashboard', affix: true }
    }]
  },
  //
  // {
  //   path: '/example',
  //   component: Layout,
  //   redirect: '/example/table',
  //   name: 'Example',
  //   meta: { title: 'Example', icon: 'example' },
  //   children: [
  //     {
  //       path: 'table',
  //       name: 'Table',
  //       component: () => import('@/views/table/index'),
  //       meta: { title: 'Table', icon: 'table' }
  //     },
  //     {
  //       path: 'tree',
  //       name: 'Tree',
  //       component: () => import('@/views/tree/index'),
  //       meta: { title: 'Tree', icon: 'tree' }
  //     }
  //   ]
  // },
  // {
  //   path: '/form',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'index',
  //       name: 'Form',
  //       component: () => import('@/views/form/index'),
  //       meta: { title: 'form', icon: 'form' }
  //     }
  //   ]
  // },
  {
    path: '/course',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'CourseSelect',
        component: () => import('@/views/course/index'),
        meta: { title: '课程选择', icon: 'list' }
      }
    ]
  },
  {
    path: '/elective',
    component: Layout,
    children: [{
      path: 'index',
      name: 'Elective',
      component: () => import('@/views/elective/index'),
      meta: { title: '已选课程', icon: 'elective' }
    }]
  },
  {
    path: '/grade',
    component: Layout,
    children: [{
      path: 'index',
      name: 'Grade',
      component: () => import('@/views/grade/index'),
      meta: { title: '成绩查询', icon: 'grade' }
    }]
  },
  {
    path: '/manage',
    component: Layout,
    redirect: '/manage/teacher',
    name: 'Manage',
    meta: {
      title: '管理',
      icon: 'manager'
    },
    children: [
      {
        path: 'teacher',
        name: 'Teacher',
        component: () => import('@/views/manage/teachers'),
        meta: { title: 'Teachers Manage', icon: 'teacher' }
      },
      {
        path: 'student',
        name: 'Student',
        component: () => import('@/views/manage/students'),
        meta: { title: 'Students Manage', icon: 'student' }
      },
      {
        path: 'course',
        name: 'Course',
        component: () => import('@/views/manage/course'),
        meta: { title: 'Course Manage', icon: 'college' }
      },
      {
        path: 'college',
        name: 'College',
        component: () => import('@/views/manage/students'),
        meta: { title: 'College Manage', icon: 'college' }
      },
      {
        path: 'class',
        name: 'Class',
        component: () => import('@/views/manage/students'),
        meta: { title: 'Class Manage', icon: 'class' }
      },
      {
        path: 'test',
        name: 'Test',
        component: () => import('@/views/manage/test'),
        meta: { title: 'TEST', icon: 'class' }
      }
    ]
  },
  {
    path: '/teacher',
    component: Layout,
    children: [{
      name: 'Teacher',
      path: 'index',
      component: () => import('@/views/manage/teachers/index'),
      meta: { title: '教师管理', icon: 'teacher' }
    }]
  },
  {
    path: '/plan',
    component: Layout,
    children: [{
      name: 'Plan',
      path: 'index',
      component: () => import('@/views/plan/index'),
      meta: { title: '培养计划', icon: 'plan' }
    }]
  },
  {
    path: '/manage/course',
    component: Layout,
    children: [{
      name: 'courseManage',
      path: 'index',
      component: () => import('@/views/manage/course/index'),
      meta: { title: '课程管理', icon: 'college' }
    }]
  },
  {
    path: '/createChoice',
    component: Layout,
    meta: { title: '选课管理', icon: 'college' },
    children: [
      {
        path: 'index',
        name: 'CreateChoice',
        component: () => import('@/views/createChoice/index'),
        meta: { title: '创建选课', icon: 'form' }
      },
      {
        path: 'list',
        name: 'ListVue',
        component: () => import('@/views/createChoice/List'),
        meta: { title: '选课列表', icon: 'plan' }
      }
    ]
  },
  {
    path: '/statistics',
    component: Layout,
    children: [{
      name: 'courseManage',
      path: 'index',
      component: () => import('@/views/statistics/index'),
      meta: { title: '课程统计', icon: 'course' }
    }]
  },
  {
    path: '/student',
    component: Layout,
    children: [{
      name: 'Student',
      path: 'index',
      component: () => import('@/views/manage/students/index'),
      meta: { title: '学生管理', icon: 'student' }
    }]
  },
  {
    path: '/profile',
    component: Layout,
    children: [{
      name: 'Profile',
      path: 'index',
      component: () => import('@/views/profile/index'),
      meta: { title: '个人信息', icon: 'user' }
    }]
  },
  {
    path: 'cqut',
    component: Layout,
    children: [
      {
        // path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
        path: 'https://www.cqut.edu.cn/',
        meta: { title: 'CQUT', icon: 'link' }
      }
    ]
  },
  {
    path: 'icqut',
    component: Layout,
    children: [
      {
        // path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
        path: 'http://i.cqut.edu.cn/zfca/login',
        meta: { title: '数字化校园', icon: 'link' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
