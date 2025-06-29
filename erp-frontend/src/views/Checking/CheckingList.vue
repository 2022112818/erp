<template>
  <div>
    <h2>采购订单审核</h2>
    
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
      <el-button type="primary" @click="getPendingOrders" style="margin-top: 10px;">
        重新加载
      </el-button>
    </div>
    
    <!-- 数据为空时的显示 -->
    <div v-else-if="pendingOrders.length === 0" style="text-align: center; padding: 20px;">
      <el-empty description="暂无待审核订单" />
      <el-button type="primary" @click="getPendingOrders" style="margin-top: 10px;">
        刷新
      </el-button>
    </div>
    
    <!-- 正常数据表格 -->
    <el-table v-else :data="pendingOrders" style="width: 100%; margin-top: 20px;">
      <el-table-column prop="id" label="订单ID" width="80" />
      <el-table-column prop="supplierName" label="供应商名称" width="150" />
      <el-table-column prop="productName" label="货品名称" width="150" />
      <el-table-column prop="quantity" label="货品数量" width="100" />
      <el-table-column prop="orderDate" label="订单日期" width="120" />
      <el-table-column prop="totalAmount" label="总金额" width="120">
        <template #default="scope">
          ¥{{ parseFloat(scope.row.totalAmount || 0).toFixed(2) }}
        </template>
      </el-table-column>
      <el-table-column prop="orderStatus" label="状态" width="100">
        <template #default="scope">
          <el-tag type="warning" effect="plain">
            待审核
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120" fixed="right">
        <template #default="scope">
          <el-button 
            size="small" 
            type="primary" 
            @click="openAuditDialog(scope.row)">
            审核
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 审核对话框 -->
    <el-dialog 
      title="订单审核" 
      v-model="auditDialogVisible" 
      width="500px"
      :close-on-click-modal="false">
      
      <div v-if="currentOrder">
        <!-- 订单信息展示 -->
        <el-card class="order-info" shadow="never">
          <h4>订单信息</h4>
          <el-descriptions :column="2" size="small">
            <el-descriptions-item label="订单ID">{{ currentOrder.id }}</el-descriptions-item>
            <el-descriptions-item label="供应商">{{ currentOrder.supplierName || '未知供应商' }}</el-descriptions-item>
            <el-descriptions-item label="货品名称">{{ currentOrder.productName || '未知商品' }}</el-descriptions-item>
            <el-descriptions-item label="货品数量">{{ currentOrder.quantity || 0 }}</el-descriptions-item>
            <el-descriptions-item label="订单日期">{{ currentOrder.orderDate || '未知日期' }}</el-descriptions-item>
            <el-descriptions-item label="总金额">¥{{ parseFloat(currentOrder.totalAmount || 0).toFixed(2) }}</el-descriptions-item>
          </el-descriptions>
        </el-card>

        <!-- 审核选项 -->
        <div style="margin: 20px 0;">
          <h4>审核决定</h4>
          <el-radio-group v-model="auditDecision" @change="onAuditDecisionChange">
            <el-radio value="approve" size="large">
              <span style="color: #67c23a;">审核通过</span>
            </el-radio>
            <el-radio value="reject" size="large">
              <span style="color: #f56c6c;">审核不通过</span>
            </el-radio>
          </el-radio-group>
        </div>

        <!-- 拒绝原因输入框 -->
        <div v-if="auditDecision === 'reject'" style="margin: 20px 0;">
          <h4>拒绝原因</h4>
          <el-input
            v-model="rejectionReason"
            type="textarea"
            :rows="4"
            placeholder="请输入审核不通过的理由..."
            maxlength="500"
            show-word-limit
          />
        </div>
      </div>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="closeAuditDialog">取消</el-button>
          <el-button 
            type="primary" 
            @click="submitAudit"
            :disabled="!canSubmit"
            :loading="submitting">
            {{ submitting ? '提交中...' : '确认提交' }}
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import request from '../../api/request.js'
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Loading } from '@element-plus/icons-vue'

// 数据
const pendingOrders = ref([])
const auditDialogVisible = ref(false)
const currentOrder = ref(null)
const auditDecision = ref('')
const rejectionReason = ref('')
const submitting = ref(false)
const loading = ref(false)
const error = ref('')

// 计算属性
const canSubmit = computed(() => {
  if (auditDecision.value === 'approve') {
    return true
  }
  if (auditDecision.value === 'reject') {
    return rejectionReason.value.trim().length > 0
  }
  return false
})

// 获取待审核订单列表
async function getPendingOrders() {
  loading.value = true
  error.value = ''
  
  try {
    console.log('开始请求采购订单列表...')
    
    const response = await request.get('/purchase/purchaseOrder/list')
    
    console.log('=== 采购订单列表调试信息开始 ===')
    console.log('完整响应对象:', response)
    console.log('响应状态码:', response.status)
    console.log('响应数据:', response.data)
    console.log('响应数据类型:', typeof response.data)
    console.log('=== 采购订单列表调试信息结束 ===')
    
    // 检查响应数据
    if (!response.data) {
      throw new Error('服务器返回数据为空')
    }
    
    // 根据后端数据结构进行适配
    let orderList = []
    
    if (response.data.success === true && response.data.data) {
      // 如果后端返回格式是 {success: true, data: [...], message: ''}
      orderList = response.data.data
    } else if (response.data.success === false) {
      // 如果后端返回错误
      throw new Error(response.data.message || '获取采购订单列表失败')
    } else if (Array.isArray(response.data)) {
      // 如果后端直接返回数组
      orderList = response.data
    } else if (response.data.data && Array.isArray(response.data.data)) {
      // 如果数据在 data 字段中
      orderList = response.data.data
    } else {
      console.error('未知的响应数据格式:', response.data)
      throw new Error('服务器返回数据格式不正确')
    }
    
    // 验证数据格式
    if (!Array.isArray(orderList)) {
      console.error('订单列表不是数组格式:', orderList)
      throw new Error('订单列表数据格式不正确')
    }
    
    console.log('解析后的订单列表:', orderList)
    console.log('订单总数:', orderList.length)
    
    // 确保每个订单对象都有必要的字段并只保留待审核的订单
    const validatedOrders = orderList
      .map(order => ({
        id: order.id || '',
        supplierName: order.supplierName || '未知供应商',
        productName: order.productName || '未知商品',
        quantity: order.quantity || 0,
        orderDate: order.orderDate || '',
        totalAmount: order.totalAmount || 0,
        orderStatus: order.orderStatus || 'UNKNOWN'
      }))
      .filter(order => order.orderStatus === 'PENDING')
    
    console.log('待审核订单:', validatedOrders)
    console.log('待审核订单数量:', validatedOrders.length)
    
    pendingOrders.value = validatedOrders
    
    if (validatedOrders.length === 0) {
      console.log('没有待审核的订单')
    } else {
      console.log('成功加载', validatedOrders.length, '个待审核订单')
    }
    
  } catch (err) {
    console.error('获取采购订单列表失败:', err)
    
    let errorMessage = '获取采购订单列表失败'
    
    if (err.response) {
      // 服务器返回的错误
      const status = err.response.status
      const data = err.response.data
      
      switch (status) {
        case 401:
          errorMessage = '未授权，请重新登录'
          break
        case 403:
          errorMessage = '没有权限访问采购订单列表'
          break
        case 404:
          errorMessage = '采购订单列表接口不存在'
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

// 打开审核对话框
function openAuditDialog(order) {
  console.log('审核订单:', order)
  currentOrder.value = order
  auditDecision.value = ''
  rejectionReason.value = ''
  auditDialogVisible.value = true
}

// 关闭审核对话框
function closeAuditDialog() {
  auditDialogVisible.value = false
  currentOrder.value = null
  auditDecision.value = ''
  rejectionReason.value = ''
}

// 审核决定改变时的处理
function onAuditDecisionChange(value) {
  if (value === 'approve') {
    rejectionReason.value = ''
  }
}

// 提交审核结果
async function submitAudit() {
  if (!canSubmit.value) {
    ElMessage.warning('请完善审核信息')
    return
  }

  const orderId = currentOrder.value.id
  
  try {
    await ElMessageBox.confirm(
      `确定要${auditDecision.value === 'approve' ? '通过' : '拒绝'}这个订单吗？`,
      '确认审核',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
    )
    
    submitting.value = true
    
    try {
      if (auditDecision.value === 'approve') {
        // 审核通过
        console.log(`审核通过订单 ${orderId}`)
        await request.put(`/purchase/purchaseOrder/approve/${orderId}`)
        ElMessage.success('审核通过成功')
      } else {
        // 审核拒绝
        console.log(`审核拒绝订单 ${orderId}，原因:`, rejectionReason.value)
        await request.put(`/purchase/purchaseOrder/reject/${orderId}`, null, {
          params: {
            reason: rejectionReason.value
          }
        })
        ElMessage.success('审核拒绝成功')
      }
      
      closeAuditDialog()
      await getPendingOrders() // 刷新列表
      
    } catch (err) {
      console.error('审核操作失败:', err)
      
      let errorMessage = '审核操作失败'
      if (err.response?.data?.message) {
        errorMessage = err.response.data.message
      } else if (err.message) {
        errorMessage = err.message
      }
      
      ElMessage.error(errorMessage)
    } finally {
      submitting.value = false
    }
    
  } catch {
    // 用户取消确认
    console.log('用户取消审核操作')
  }
}

// 组件挂载时获取数据
onMounted(() => {
  getPendingOrders()
})
</script>

<style scoped>
.order-info {
  margin-bottom: 20px;
  background-color: #f9f9f9;
}

.order-info h4 {
  margin: 0 0 15px 0;
  color: #333;
  font-weight: 600;
}

.dialog-footer {
  text-align: right;
}

h2 {
  color: #333;
  margin-bottom: 10px;
}

h4 {
  color: #666;
  margin: 15px 0 10px 0;
  font-weight: 600;
}

.el-radio {
  margin-right: 30px;
  margin-bottom: 10px;
}

.el-descriptions {
  margin-top: 10px;
}

.el-table {
  border-radius: 8px;
  overflow: hidden;
}

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