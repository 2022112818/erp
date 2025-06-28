<template>
  <div>
    <h2>采购订单审核</h2>
    
    <el-table :data="pendingOrders" style="width: 100%; margin-top: 20px;">
      <el-table-column prop="id" label="订单ID" width="80" />
      <el-table-column prop="supplierName" label="供应商名称" width="150" />
      <el-table-column prop="productName" label="货品名称" width="150" />
      <el-table-column prop="quantity" label="货品数量" width="100" />
      <el-table-column prop="orderDate" label="订单日期" width="120" />
      <el-table-column prop="totalAmount" label="总金额" width="120" />
      <el-table-column prop="orderStatus" label="状态" width="100">
        <template #default="scope">
          <el-tag type="warning" effect="plain">
            待审核
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120">
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
            <el-descriptions-item label="供应商">{{ currentOrder.supplierName }}</el-descriptions-item>
            <el-descriptions-item label="货品名称">{{ currentOrder.productName }}</el-descriptions-item>
            <el-descriptions-item label="货品数量">{{ currentOrder.quantity }}</el-descriptions-item>
            <el-descriptions-item label="订单日期">{{ currentOrder.orderDate }}</el-descriptions-item>
            <el-descriptions-item label="总金额">¥{{ currentOrder.totalAmount }}</el-descriptions-item>
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
            确认提交
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

// 数据
const pendingOrders = ref([])
const auditDialogVisible = ref(false)
const currentOrder = ref(null)
const auditDecision = ref('')
const rejectionReason = ref('')
const submitting = ref(false)

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
function getPendingOrders() {
  request.get('/purchase/purchaseOrder/list').then(res => {
    // 只显示待审核的订单
    pendingOrders.value = res.data.filter(order => order.orderStatus === 'PENDING')
  }).catch(err => {
    ElMessage.error('获取订单列表失败')
  })
}

// 打开审核对话框
function openAuditDialog(order) {
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
function submitAudit() {
  if (!canSubmit.value) {
    ElMessage.warning('请完善审核信息')
    return
  }

  const orderId = currentOrder.value.id
  
  ElMessageBox.confirm(
    `确定要${auditDecision.value === 'approve' ? '通过' : '拒绝'}这个订单吗？`,
    '确认审核',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(() => {
    submitting.value = true
    
    if (auditDecision.value === 'approve') {
      // 审核通过
      request.put(`/purchase/purchaseOrder/approve/${orderId}`).then(() => {
        ElMessage.success('审核通过成功')
        closeAuditDialog()
        getPendingOrders() // 刷新列表
      }).catch(err => {
        ElMessage.error('审核通过失败')
      }).finally(() => {
        submitting.value = false
      })
    } else {
      // 审核拒绝
      request.put(`/purchase/purchaseOrder/reject/${orderId}`, null, {
        params: {
          reason: rejectionReason.value
        }
      }).then(() => {
        ElMessage.success('审核拒绝成功')
        closeAuditDialog()
        getPendingOrders() // 刷新列表
      }).catch(err => {
        ElMessage.error('审核拒绝失败')
      }).finally(() => {
        submitting.value = false
      })
    }
  }).catch(() => {
    // 用户取消
  })
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
</style>