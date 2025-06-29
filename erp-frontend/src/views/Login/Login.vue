<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-header">
        <h2>用户登录</h2>
        <p>请输入您的账户信息</p>
      </div>
      
      <el-form 
        :model="loginForm" 
        :rules="rules" 
        ref="loginFormRef"
        class="login-form"
        @keyup.enter="handleLogin"
      >
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="请输入用户名"
            prefix-icon="User"
            size="large"
            clearable
          />
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            size="large"
            show-password
            clearable
          />
        </el-form-item>
        
        <el-form-item>
          <el-button
            type="primary"
            size="large"
            class="login-btn"
            :loading="loading"
            @click="handleLogin"
          >
            {{ loading ? '登录中...' : '登录' }}
          </el-button>
        </el-form-item>
      </el-form>
      
      <div class="login-footer">
        <p>还没有账户？<a href="#" @click.prevent="goToRegister">立即注册</a></p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElLoading } from 'element-plus'
import request from '../../api/request.js'

// 路由实例
const router = useRouter()

// 表单引用
const loginFormRef = ref()

// 加载状态
const loading = ref(false)

// 表单数据
const loginForm = reactive({
  username: '',
  password: ''
})

// 表单验证规则
const rules = reactive({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 50, message: '密码长度在 6 到 50 个字符', trigger: 'blur' }
  ]
})

// 中文角色到英文角色的映射
const roleMapping = {
  '管理员': 'ADMIN',
  '采购员': 'PURCHASER', 
  '销售员': 'SALESPERSON',
  '客户': 'CUSTOMER',
  '仓库管理员': 'WAREHOUSE_MANAGER'
}

// 登录处理函数
const handleLogin = async () => {
  // 表单验证
  if (!loginFormRef.value) return
  
  try {
    const valid = await loginFormRef.value.validate()
    if (!valid) return
  } catch (error) {
    console.log('表单验证失败:', error)
    return
  }

  // 开始登录
  loading.value = true
  
  try {
    // 准备请求参数
    const params = new URLSearchParams()
    params.append('username', loginForm.username.trim())
    params.append('password', loginForm.password)
    
    console.log('发送登录请求...') // 调试日志
    
    // 发送登录请求
    const response = await request.post('/auth/login', params, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
    
    console.log('=== 调试信息开始 ===')
    console.log('完整响应对象:', response)
    console.log('响应状态码:', response.status)
    console.log('响应数据:', response.data)
    console.log('响应数据类型:', typeof response.data)
    console.log('=== 调试信息结束 ===')
    
    // 检查响应数据
    if (!response.data) {
      throw new Error('服务器返回数据为空')
    }
    
    // 检查success状态
    if (response.data.success === false) {
      throw new Error(response.data.message || '登录失败')
    }
    
    // 获取token - 根据你的后端格式，token在message字段中
    let token = null
    if (response.data.success && response.data.message) {
      token = response.data.message
      console.log('从response.data.message获取token:', token)
    }
    
    // 确保token存在
    if (!token) {
      console.error('无法从响应中提取token')
      console.error('响应结构:', response.data)
      throw new Error('服务器未返回有效的token')
    }
    
    // 确保token是字符串
    if (typeof token !== 'string') {
      console.error('Token不是字符串类型!')
      console.error('Token类型:', typeof token)
      console.error('Token值:', token)
      throw new Error(`Token格式不正确，期望字符串，实际${typeof token}`)
    }
    
    // 验证JWT格式
    console.log('Token内容:', token)
    console.log('Token长度:', token.length)
    
    if (!token.includes('.') || token.split('.').length !== 3) {
      console.error('Token格式不是有效的JWT:', token)
      throw new Error('服务器返回的token格式不是有效的JWT')
    }
    
    // 解析用户信息
    let userInfo = null
    if (response.data.data) {
      try {
        // 解析JSON字符串格式的用户信息
        const userData = JSON.parse(response.data.data)
        console.log('解析后的用户数据:', userData)
        
        // 转换角色名称为英文
        const englishRole = roleMapping[userData.role] || userData.role
        
        userInfo = {
          username: loginForm.username, // 使用登录时输入的用户名
          role: englishRole, // 转换后的英文角色
          id: userData.id,
          originalRole: userData.role // 保留原始中文角色名
        }
        
        console.log('处理后的用户信息:', userInfo)
      } catch (parseError) {
        console.error('解析用户信息失败:', parseError)
        throw new Error('服务器返回的用户信息格式错误')
      }
    }
    
    // 保存token和用户信息
    localStorage.setItem('token', token)
    if (userInfo) {
      localStorage.setItem('userInfo', JSON.stringify(userInfo))
    }
    
    // 安全地显示token信息
    console.log('Token已保存:', token.substring(0, 20) + '...') // 调试日志
    console.log('用户信息已保存:', userInfo)
    
    // 显示成功消息
    ElMessage.success('登录成功！')
    
    // 跳转到首页或指定页面
    const redirect = router.currentRoute.value.query.redirect || '/dashboard'
    await router.push(redirect)
    
  } catch (error) {
    console.error('登录失败:', error)
    
    // 处理不同类型的错误
    let errorMessage = '登录失败'
    
    if (error.response) {
      // 服务器返回的错误
      const status = error.response.status
      const data = error.response.data
      
      switch (status) {
        case 401:
          errorMessage = '用户名或密码错误'
          break
        case 403:
          errorMessage = '账户被禁用，请联系管理员'
          break
        case 500:
          errorMessage = '服务器内部错误，请稍后重试'
          break
        default:
          errorMessage = data?.message || `请求失败 (${status})`
      }
    } else if (error.request) {
      // 网络错误
      errorMessage = '网络连接失败，请检查网络设置'
    } else {
      // 其他错误
      errorMessage = error.message || '未知错误'
    }
    
    ElMessage.error(errorMessage)
    
  } finally {
    loading.value = false
  }
}

// 跳转到注册页面
const goToRegister = () => {
  ElMessage.info('注册功能暂未开放')
  // router.push('/register')
}

// 组件挂载时检查是否已登录
import { onMounted } from 'vue'

onMounted(() => {
  const token = localStorage.getItem('token')
  if (token) {
    console.log('用户已登录，跳转到首页')
    router.push('/dashboard')
  }
})
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.login-box {
  width: 100%;
  max-width: 400px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.login-header {
  text-align: center;
  padding: 40px 30px 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.login-header h2 {
  margin: 0 0 10px;
  font-size: 28px;
  font-weight: 600;
}

.login-header p {
  margin: 0;
  opacity: 0.9;
  font-size: 14px;
}

.login-form {
  padding: 30px;
}

.login-form :deep(.el-form-item) {
  margin-bottom: 24px;
}

.login-form :deep(.el-input__wrapper) {
  border-radius: 8px;
  box-shadow: 0 0 0 1px #dcdfe6 inset;
  transition: all 0.3s;
}

.login-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 0 0 1px #c0c4cc inset;
}

.login-form :deep(.el-input.is-focus .el-input__wrapper) {
  box-shadow: 0 0 0 1px #409eff inset;
}

.login-btn {
  width: 100%;
  height: 44px;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 500;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  transition: all 0.3s;
}

.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(102, 126, 234, 0.4);
}

.login-footer {
  text-align: center;
  padding: 0 30px 30px;
  color: #666;
  font-size: 14px;
}

.login-footer a {
  color: #667eea;
  text-decoration: none;
  font-weight: 500;
}

.login-footer a:hover {
  text-decoration: underline;
}

/* 响应式设计 */
@media (max-width: 480px) {
  .login-container {
    padding: 10px;
  }
  
  .login-box {
    margin: 10px;
  }
  
  .login-header {
    padding: 30px 20px 15px;
  }
  
  .login-header h2 {
    font-size: 24px;
  }
  
  .login-form {
    padding: 20px;
  }
}

/* 加载动画 */
.login-btn:loading {
  pointer-events: none;
}
</style>