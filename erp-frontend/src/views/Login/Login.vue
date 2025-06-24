
<template>
  <div style="width:300px; margin:150px auto;">
    <el-form :model="form" label-width="80px">
      <el-form-item label="用户名"><el-input v-model="form.username" /></el-form-item>
      <el-form-item label="密码"><el-input type="password" v-model="form.password" /></el-form-item>
      <el-form-item><el-button type="primary" @click="login">登录</el-button></el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import request from '../../api/request.js'
import { reactive } from 'vue'
import { useRouter } from 'vue-router'

const form = reactive({ username: '', password: '' })
const router = useRouter()

function login() {
  const params = new URLSearchParams();
  params.append("username", form.username);
  params.append("password", form.password);

  request.post('/auth/login', params, {
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  }).then(res => {
    localStorage.setItem('token', res.data)
    router.push('/dashboard')
  }).catch(err => { alert(err) })
}

</script>
