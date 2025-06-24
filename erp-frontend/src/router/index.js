
import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: () => import('../views/Login/Login.vue') },
  { path: '/dashboard', component: () => import('../views/Dashboard/Dashboard.vue'),
    children: [
      { path: '/inventory', component: () => import('../views/Inventory/InventoryList.vue') },
      { path: '/purchase', component: () => import('../views/PurchaseOrder/PurchaseOrderList.vue') },
      { path: '/sales', component: () => import('../views/SalesOrder/SalesOrderList.vue') },
      { path: '/product', component: () => import('../views/Product/ProductList.vue') },
      { path: '/auth', component: () => import('../views/User/UserList.vue') }
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
