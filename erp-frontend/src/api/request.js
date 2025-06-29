import axios from 'axios'

const service = axios.create({
  baseURL: '/api',
  timeout: 5000
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    console.log('请求拦截器 - URL:', config.url)
    console.log('请求拦截器 - token:', token ? token.substring(0, 20) + '...' : '无token')
    
    if (token) {
      config.headers['Authorization'] = token
    }
    return config
  },
  error => {
    console.error('请求拦截器错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    console.log('响应拦截器 - URL:', response.config.url)
    console.log('响应拦截器 - 状态码:', response.status)
    return response
  },
  error => {
    console.error('响应拦截器错误:', error)
    console.error('错误URL:', error.config?.url)
    console.error('错误状态码:', error.response?.status)
    console.error('错误信息:', error.response?.data)
    
    // 只有在非登录页面且确实是认证失败时才自动登出
    if (error.response && error.response.status === 401) {
      // 检查当前页面是否为登录页，避免无限重定向
      if (!window.location.pathname.includes('/login')) {
        console.log('Token无效，清除本地存储并跳转到登录页')
        localStorage.removeItem('token')
        // 添加延迟，避免与路由冲突
        setTimeout(() => {
          window.location.href = '/login'
        }, 100)
      }
    }
    
    return Promise.reject(error)
  }
)

export default service