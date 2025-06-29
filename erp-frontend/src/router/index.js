
import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: () => import('../views/Login/Login.vue') },
  {
  path: '/redirect-temp',
  name: 'RedirectTemp',
  component: {
    template: '<div></div>' // 直接空组件
  }
},
  { path: '/dashboard', component: () => import('../views/Dashboard/Dashboard.vue'),
    children: [
      { path: '/inventory', component: () => import('../views/Inventory/InventoryManagement.vue') },
      { path: '/purchase', component: () => import('../views/PurchaseOrder/PurchaseOrderList.vue') },
      { path: '/sales', component: () => import('../views/SalesManagement/SalesManagement.vue') },
      { path: '/salesorder', component: () => import('../views/SalesOrder/SalesOrder.vue') },
      { path: '/product', component: () => import('../views/Product/ProductList.vue') },
      { path: '/auth', component: () => import('../views/User/UserList.vue') },
      { path: '/checking', component: () => import('../views/Checking/CheckingList.vue') }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
