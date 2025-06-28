
<template>
  <el-container style="height:100vh;">
    <el-aside width="200px">
      <el-menu class="el-menu-vertical-demo" @select="handleSelect">
        <el-menu-item index="/inventory">库存管理</el-menu-item>
        <el-menu-item index="/purchase">采购管理</el-menu-item>
        <el-menu-item index="/sales">销售管理</el-menu-item>
        <el-menu-item index="/product">商品管理</el-menu-item>
        <el-menu-item index="/auth">用户管理</el-menu-item>
        <el-menu-item index="/checking">采购审核</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="text-align:right">
        <el-button type="danger" @click="logout">退出登录</el-button>
      </el-header>
      <el-main><router-view></router-view></el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { useRouter } from 'vue-router'
const router = useRouter()

function handleSelect(index) { router.push(index) }
function logout() { localStorage.removeItem('token'); router.push('/login') }
</script>
