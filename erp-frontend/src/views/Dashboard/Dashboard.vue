<template>
  <el-container style="height:100vh;">
    <el-aside width="200px">
      <el-menu 
        class="el-menu-vertical-demo" 
        @select="handleSelect"
        :default-active="activeMenu"
        :router="false"
      >
        <el-menu-item v-if="hasPermission('inventory')" index="/inventory">
          <el-icon><Box /></el-icon>
          <span>库存管理</span>
        </el-menu-item>
        <el-menu-item v-if="hasPermission('purchase')" index="/purchase">
          <el-icon><ShoppingCart /></el-icon>
          <span>采购管理</span>
        </el-menu-item>
        <el-menu-item v-if="hasPermission('sales')" index="/sales">
          <el-icon><Goods /></el-icon>
          <span>商品管理</span>
        </el-menu-item>
        <el-menu-item v-if="hasPermission('salesorder')" index="/salesorder">
          <el-icon><Document /></el-icon>
          <span>订单查看</span>
        </el-menu-item>
        <el-menu-item v-if="hasPermission('product')" index="/product">
          <el-icon><Present /></el-icon>
          <span>商品购买</span>
        </el-menu-item>
        <el-menu-item v-if="hasPermission('auth')" index="/auth">
          <el-icon><User /></el-icon>
          <span>用户管理</span>
        </el-menu-item>
        <el-menu-item v-if="hasPermission('checking')" index="/checking">
          <el-icon><Check /></el-icon>
          <span>采购审核</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header style="text-align:right; padding: 0 20px; display: flex; justify-content: space-between; align-items: center;">
        <div>
          <span>欢迎，{{ userInfo.username || '用户' }}</span>
          <el-tag type="info" style="margin-left: 10px;">{{ getRoleDisplayText(userInfo.role) }}</el-tag>
        </div>
        <el-button type="danger" @click="logout">退出登录</el-button>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import {
  Box,
  ShoppingCart,
  Goods,
  Document,
  Present,
  User,
  Check
} from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()
const activeMenu = ref('')

const userInfo = ref({
  username: '',
  role: '',
  id: null,
  originalRole: ''
})

const rolePermissions = {
  'ADMIN': ['inventory', 'purchase', 'sales', 'salesorder', 'product', 'auth', 'checking'],
  'PURCHASER': ['purchase'],
  'SALESPERSON': ['sales', 'salesorder', 'inventory'],
  'CUSTOMER': ['product'],
  'WAREHOUSE_MANAGER': ['inventory']
}

const roleDisplayMap = {
  'ADMIN': '系统管理员',
  'PURCHASER': '采购员',
  'SALESPERSON': '销售员',
  'CUSTOMER': '客户',
  'WAREHOUSE_MANAGER': '仓库管理员'
}

watch(() => route.path, (newPath) => {
  activeMenu.value = newPath
}, { immediate: true })

onMounted(async () => {
  await loadUserFromStorage()
  if (!userInfo.value.username) {
    await getUserInfoFromBackend()
  }
  await redirectToDefaultPage()
})

async function loadUserFromStorage() {
  try {
    const token = localStorage.getItem('token')
    const storedUserInfo = localStorage.getItem('userInfo')

    if (!token) {
      router.push('/login')
      return
    }

    if (storedUserInfo) {
      const parsedUserInfo = JSON.parse(storedUserInfo)
      userInfo.value = parsedUserInfo
    }
  } catch (error) {
    localStorage.removeItem('userInfo')
  }
}

async function getUserInfoFromBackend() {
  try {
    const token = localStorage.getItem('token')
    if (!token) {
      router.push('/login')
      return
    }

    // 请在此补充调用后端接口代码
    // 示例:
    // const response = await fetch('/api/user/info', { headers: { Authorization: token } })

    // 如果使用 mock：
    const response = { ok: true, json: async () => ({ code: 200, data: { username: '张三', role: 'ADMIN' } }) }

    if (response.ok) {
      const result = await response.json()
      if (result.code === 200) {
        userInfo.value = result.data
        localStorage.setItem('userInfo', JSON.stringify(result.data))
      } else {
        ElMessage.error('获取用户信息失败')
        logout()
      }
    } else {
      ElMessage.error('获取用户信息失败')
      logout()
    }
  } catch (error) {
    ElMessage.error('网络错误，请重试')
    if (!userInfo.value.username) {
      logout()
    }
  }
}

async function redirectToDefaultPage() {
  if (route.path === '/dashboard') {
    const userRole = userInfo.value.role
    let defaultPath = '/product'
    switch (userRole) {
      case 'ADMIN':
        defaultPath = '/auth'
        break
      case 'PURCHASER':
        defaultPath = '/purchase'
        break
      case 'SALESPERSON':
        defaultPath = '/sales'
        break
      case 'CUSTOMER':
        defaultPath = '/product'
        break
      case 'WAREHOUSE_MANAGER':
        defaultPath = '/inventory'
        break
    }

    const permission = defaultPath.substring(1)
    if (hasPermission(permission)) {
      await router.replace(defaultPath)
      activeMenu.value = defaultPath
    } else {
      const permissions = rolePermissions[userRole] || []
      if (permissions.length > 0) {
        const firstPermission = permissions[0]
        const firstPath = `/${firstPermission}`
        await router.replace(firstPath)
        activeMenu.value = firstPath
      }
    }
  }
}

function hasPermission(permission) {
  const userRole = userInfo.value.role
  if (!userRole) return false
  const permissions = rolePermissions[userRole] || []
  return permissions.includes(permission)
}

function getRoleDisplayText(role) {
  if (userInfo.value.originalRole) {
    return userInfo.value.originalRole
  }
  return roleDisplayMap[role] || role
}

// 🔁 整页刷新实现
function handleSelect(index) {
  const permission = index.substring(1)
  if (hasPermission(permission)) {
    window.location.href = index  // 整页刷新
  } else {
    ElMessage.warning('您没有权限访问该功能')
  }
}

function logout() {
  localStorage.removeItem('token')
  localStorage.removeItem('userInfo')
  router.push('/login')
  ElMessage.success('已退出登录')
}
</script>

<style scoped>
.el-header {
  background-color: #f5f5f5;
  border-bottom: 1px solid #e6e6e6;
}
.el-aside {
  background-color: #f8f9fa;
  border-right: 1px solid #e6e6e6;
}
.el-menu {
  border-right: none;
}
.el-menu-item {
  display: flex;
  align-items: center;
}
.el-menu-item .el-icon {
  margin-right: 8px;
}
</style>
