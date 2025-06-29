<template>
  <div>
    <!-- 添加加载状态显示 -->
    <div v-if="loading" style="text-align: center; padding: 20px;">
      <el-icon class="is-loading"><Loading /></el-icon>
      <span style="margin-left: 10px;">加载中...</span>
    </div>
    
    <!-- 添加错误状态显示 -->
    <div v-else-if="error" style="text-align: center; padding: 20px;">
      <el-alert
        title="加载失败"
        :description="error"
        type="error"
        show-icon
        :closable="false"
      />
      <el-button type="primary" @click="getList" style="margin-top: 10px;">
        重新加载
      </el-button>
    </div>
    
    <!-- 数据为空时的显示 -->
    <div v-else-if="tableData.length === 0" style="text-align: center; padding: 20px;">
      <el-empty description="暂无商品数据" />
      <el-button type="primary" @click="getList" style="margin-top: 10px;">
        刷新
      </el-button>
    </div>
    
    <!-- 正常数据表格 -->
    <el-table v-else :data="tableData" style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="商品名称" min-width="150" />
      <el-table-column prop="category" label="分类" width="120" />
      <el-table-column prop="unit" label="单位" width="80" />
      <el-table-column prop="price" label="价格" width="100">
        <template #default="scope">
          ¥{{ parseFloat(scope.row.price).toFixed(2) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120" fixed="right">
        <template #default="scope">
          <el-button size="small" type="success" @click="purchase(scope.row)">
            购买
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 购买订单弹窗 -->
    <el-dialog title="新建订单" v-model="purchaseDialogVisible" width="500px">
      <el-form :model="purchaseForm" label-width="120px">
        <el-form-item label="商品名称">
          <el-input v-model="purchaseDisplay.productName" disabled />
        </el-form-item>
        <el-form-item label="单价">
          <el-input v-model="purchaseDisplay.unitPrice" disabled />
        </el-form-item>
        <el-form-item label="购买数量" required>
          <el-input-number 
            v-model="purchaseDisplay.quantity" 
            :min="1" 
            @change="calculateTotal"
          />
        </el-form-item>
        <el-form-item label="客户ID" required>
          <el-input v-model="purchaseForm.customerId" placeholder="请输入客户ID" />
        </el-form-item>
        <el-form-item label="订单日期">
          <el-input v-model="purchaseForm.orderDate" disabled />
        </el-form-item>
        <el-form-item label="总金额">
          <el-input v-model="purchaseForm.totalAmount" disabled />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="purchaseDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="savePurchaseOrder()" :loading="submitting">
          {{ submitting ? '提交中...' : '提交订单' }}
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import request from '../../api/request.js'
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Loading } from '@element-plus/icons-vue'

const tableData = ref([])
const loading = ref(false)
const error = ref('')
const submitting = ref(false)

// 购买相关的响应式数据
const purchaseDialogVisible = ref(false)
const purchaseForm = reactive({
  id: "",
  productName: "",
  customerId: "",
  orderDate: "",
  totalAmount: ""
})

// 用于显示和计算的临时数据
const purchaseDisplay = reactive({
  productName: "",
  unitPrice: "",
  quantity: 1
})

// 获取产品列表
async function getList() {
  loading.value = true
  error.value = ''
  
  try {
    console.log('开始请求产品列表...')
    
    const response = await request.get('/product/product/list')
    
    console.log('=== 产品列表调试信息开始 ===')
    console.log('完整响应对象:', response)
    console.log('响应状态码:', response.status)
    console.log('响应数据:', response.data)
    console.log('响应数据类型:', typeof response.data)
    console.log('=== 产品列表调试信息结束 ===')
    
    // 检查响应数据
    if (!response.data) {
      throw new Error('服务器返回数据为空')
    }
    
    // 根据你的后端数据结构进行适配
    let productList = []
    
    if (response.data.success === true && response.data.data) {
      // 如果后端返回格式是 {success: true, data: [...], message: ''}
      productList = response.data.data
    } else if (response.data.success === false) {
      // 如果后端返回错误
      throw new Error(response.data.message || '获取产品列表失败')
    } else if (Array.isArray(response.data)) {
      // 如果后端直接返回数组
      productList = response.data
    } else if (response.data.data && Array.isArray(response.data.data)) {
      // 如果数据在 data 字段中
      productList = response.data.data
    } else {
      console.error('未知的响应数据格式:', response.data)
      throw new Error('服务器返回数据格式不正确')
    }
    
    // 验证数据格式
    if (!Array.isArray(productList)) {
      console.error('产品列表不是数组格式:', productList)
      throw new Error('产品列表数据格式不正确')
    }
    
    console.log('解析后的产品列表:', productList)
    console.log('产品数量:', productList.length)
    
    // 确保每个产品对象都有必要的字段
    const validatedProducts = productList.map(product => ({
      id: product.id || '',
      name: product.name || '未知商品',
      category: product.category || '未分类',
      unit: product.unit || '个',
      price: product.price || 0
    }))
    
    tableData.value = validatedProducts
    
    if (validatedProducts.length === 0) {
      console.log('产品列表为空')
    } else {
      console.log('成功加载', validatedProducts.length, '个产品')
    }
    
  } catch (err) {
    console.error('获取产品列表失败:', err)
    
    let errorMessage = '获取产品列表失败'
    
    if (err.response) {
      // 服务器返回的错误
      const status = err.response.status
      const data = err.response.data
      
      switch (status) {
        case 401:
          errorMessage = '未授权，请重新登录'
          break
        case 403:
          errorMessage = '没有权限访问产品列表'
          break
        case 404:
          errorMessage = '产品列表接口不存在'
          break
        case 500:
          errorMessage = '服务器内部错误'
          break
        default:
          errorMessage = data?.message || `请求失败 (${status})`
      }
    } else if (err.request) {
      // 网络错误
      errorMessage = '网络连接失败，请检查网络设置'
    } else {
      // 其他错误
      errorMessage = err.message || '未知错误'
    }
    
    error.value = errorMessage
    ElMessage.error(errorMessage)
  } finally {
    loading.value = false
  }
}

// 购买相关函数
function purchase(row) {
  console.log('购买商品:', row)
  
  // 重置购买表单
  for (let key in purchaseForm) {
    purchaseForm[key] = ""
  }
  
  // 设置产品信息到purchaseForm中
  purchaseForm.productName = row.name
  
  // 重置显示数据
  purchaseDisplay.productName = row.name
  purchaseDisplay.unitPrice = row.price
  purchaseDisplay.quantity = 1
  
  // 设置默认值
  purchaseForm.orderDate = new Date().toISOString().split('T')[0]
  purchaseForm.totalAmount = row.price
  
  purchaseDialogVisible.value = true
}

function calculateTotal() {
  if (purchaseDisplay.quantity && purchaseDisplay.unitPrice) {
    purchaseForm.totalAmount = (parseFloat(purchaseDisplay.unitPrice) * purchaseDisplay.quantity).toFixed(2)
  }
}

async function savePurchaseOrder() {
  // 基本验证
  if (!purchaseDisplay.quantity || purchaseDisplay.quantity <= 0) {
    ElMessage.warning('请输入有效的购买数量')
    return
  }
  
  if (!purchaseForm.customerId) {
    ElMessage.warning('请输入客户ID')
    return
  }

  submitting.value = true

  try {
    // 构建发送给后端的订单数据
    const orderData = {
      ...purchaseForm,
      quantity: purchaseDisplay.quantity
    }

    console.log('提交订单数据:', orderData)

    if (purchaseForm.id) {
      // 更新订单
      await request.put('/sales/salesOrder/update', orderData)
      ElMessage.success('订单更新成功')
    } else {
      // 创建新订单
      await request.post('/sales/salesOrder/create', orderData)
      ElMessage.success('订单创建成功')
    }
    
    purchaseDialogVisible.value = false
  } catch (err) {
    console.error('提交订单失败:', err)
    
    let errorMessage = '提交订单失败'
    if (err.response?.data?.message) {
      errorMessage = err.response.data.message
    } else if (err.message) {
      errorMessage = err.message
    }
    
    ElMessage.error(errorMessage)
  } finally {
    submitting.value = false
  }
}

// 组件挂载时获取数据
onMounted(() => {
  getList()
})
</script>

<style scoped>
.is-loading {
  animation: rotating 2s linear infinite;
}

@keyframes rotating {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>