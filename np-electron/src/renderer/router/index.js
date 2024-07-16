import Router from 'vue-router'
// 引入路由表
import asyncRouterMap from './constantRouterMap'

export const constantRouterMap = [{
  path: '/login',
  component: () => import('@/views/login'),
  hidden: true
},]
export const asyncRoutes = asyncRouterMap

const createRouter = () => new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export function resetRouter () {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher
}
const router = createRouter()

export default router
