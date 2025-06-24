
import axios from 'axios'

const service = axios.create({
  baseURL: '/api',
  timeout: 5000
})

service.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers['Authorization'] = token
  }
  return config
})

service.interceptors.response.use(response => {
  const res = response.data
  if (!res.success) {
    return Promise.reject(res.message || 'Error')
  }
  return res
}, error => {
  return Promise.reject(error)
})

export default service
