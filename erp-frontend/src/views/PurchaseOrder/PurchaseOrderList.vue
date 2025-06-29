<template>
  <div>
    <el-button type="primary" @click="openDialog()">新增</el-button>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="supplierName" label="供应商名称" />
      <el-table-column prop="productName" label="货品名称" />
      <el-table-column prop="quantity" label="货品数量" />
      <el-table-column prop="orderDate" label="预计到货日期" />
      <el-table-column prop="totalAmount" label="总金额" />
      <el-table-column prop="orderStatus" label="状态">
        <template #default="scope">
          <el-tag 
            :type="getStatusType(scope.row.orderStatus)"
            effect="plain">
            {{ getStatusText(scope.row.orderStatus) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template #default="scope">
          <!-- 待审核状态：显示编辑和删除按钮 -->
          <template v-if="scope.row.orderStatus === 'PENDING'">
            <el-button size="small" @click="edit(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
          </template>
          
          <!-- 审核通过状态：显示支付按钮 -->
          <template v-if="scope.row.orderStatus === 'APPROVED'">
            <el-button size="small" type="success" @click="showPaymentDialog(scope.row)">支付</el-button>
          </template>
          
          <!-- 审核拒绝状态：显示查看拒绝原因按钮 -->
          <template v-if="scope.row.orderStatus === 'REJECTED'">
            <el-button size="small" type="warning" @click="showRejectionReason(scope.row)">查看拒绝原因</el-button>
          </template>
          
          <!-- 已完成状态：不显示任何按钮 -->
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增/编辑对话框 -->
    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="供应商名称">
          <el-input v-model="form.supplierName" placeholder="请输入供应商名称" />
        </el-form-item>
        <el-form-item label="货品名称">
          <el-input v-model="form.productName" placeholder="请输入货品名称" />
        </el-form-item>
        <el-form-item label="货品数量">
          <el-input v-model="form.quantity" type="number" placeholder="请输入货品数量" />
        </el-form-item>
        <el-form-item label="预计到货日期">
          <el-date-picker
            v-model="form.orderDate"
            type="date"
            placeholder="选择日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="总金额">
          <el-input v-model="form.totalAmount" type="number" step="0.01" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save()">保存</el-button>
      </template>
    </el-dialog>

    <!-- 支付确认对话框 -->
    <el-dialog title="支付确认" v-model="paymentDialogVisible" width="400px">
      <div style="text-align: center; padding: 20px;">
        <el-icon size="50" color="#f56c6c"><Warning /></el-icon>
        <p style="margin: 20px 0; font-size: 16px;">
          确定要支付订单 ID: {{ currentPaymentOrder?.id }} 吗？
        </p>
        <p style="color: #666; margin-bottom: 20px;">
          金额: ¥{{ currentPaymentOrder?.totalAmount }}
        </p>
      </div>
      <template #footer>
        <el-button @click="paymentDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmPayment()">确定支付</el-button>
      </template>
    </el-dialog>

    <!-- 拒绝原因显示对话框 -->
    <el-dialog title="拒绝原因" v-model="rejectionDialogVisible" width="500px">
      <div style="padding: 20px;">
        <p style="margin-bottom: 15px; font-weight: bold;">订单ID: {{ currentRejectionOrder?.id }}</p>
        <el-card>
          <div style="line-height: 1.6;">
            {{ currentRejectionOrder?.rejectionReason || '暂无拒绝原因说明' }}
          </div>
        </el-card>
      </div>
      <template #footer>
        <el-button type="primary" @click="rejectionDialogVisible = false">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import request from '../../api/request.js'
import { ref, reactive } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { Warning } from '@element-plus/icons-vue'

const tableData = ref([])
const dialogVisible = ref(false)
const paymentDialogVisible = ref(false)
const rejectionDialogVisible = ref(false)
const dialogTitle = ref("")
const currentPaymentOrder = ref(null)
const currentRejectionOrder = ref(null)

const form = reactive({
  id: "",
  supplierName: "",
  productName: "",
  quantity: "",
  orderDate: "",
  totalAmount: ""
})

// 获取状态对应的标签类型
function getStatusType(status) {
  const statusMap = {
    'PENDING': 'warning',
    'APPROVED': 'success',
    'REJECTED': 'danger',
    'COMPLETED': 'info'
  }
  return statusMap[status] || 'info'
}

// 获取状态对应的中文文本
function getStatusText(status) {
  const statusMap = {
    'PENDING': '待审核',
    'APPROVED': '已审核',
    'REJECTED': '已拒绝',
    'COMPLETED': '已完成'
  }
  return statusMap[status] || status
}

function getList() {
  request.get('/purchase/purchaseOrder/list').then(res => {
    tableData.value = res.data.data
  })
}

getList()

function openDialog() {
  dialogTitle.value = "新增订单"
  for (let key in form) form[key] = ""
  dialogVisible.value = true
}

function edit(row) {
  dialogTitle.value = "编辑订单"
  for (let key in form) form[key] = row[key]
  dialogVisible.value = true
}

function save() {
  // 验证必填字段
  if (!form.supplierName || !form.productName || !form.quantity || !form.orderDate || !form.totalAmount) {
    ElMessage.error('请填写所有必填字段')
    return
  }

  if (form.id) {
    // 编辑模式
    request.put('/purchase/purchaseOrder/update', form).then(() => {
      dialogVisible.value = false
      getList()
      ElMessage.success('更新成功')
    }).catch(err => {
      ElMessage.error('更新失败')
    })
  } else {
    // 新增模式
    request.post('/purchase/purchaseOrder/create', form).then(() => {
      dialogVisible.value = false
      getList()
      ElMessage.success('创建成功，订单已进入待审核状态')
    }).catch(err => {
      ElMessage.error('创建失败')
    })
  }
}

function remove(id) {
  ElMessageBox.confirm(
    '确定要删除这条记录吗？',
    '删除确认',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
  .then(() => {
    request.delete(`/purchase/purchaseOrder/delete/${id}`).then(() => {
      getList()
      ElMessage.success('删除成功')
    }).catch(err => {
      ElMessage.error('删除失败')
    })
  })
  .catch(() => {
    ElMessage.info('已取消删除')
  })
}

// 显示支付确认对话框
function showPaymentDialog(row) {
  currentPaymentOrder.value = row
  paymentDialogVisible.value = true
}

// 确认支付
function confirmPayment() {
  request.put(`/purchase/purchaseOrder/pay/${currentPaymentOrder.value.id}`).then(() => {
    paymentDialogVisible.value = false
    getList()
    ElMessage.success('支付成功，订单已完成')
  }).catch(err => {
    ElMessage.error('支付失败')
  })
}

// 显示拒绝原因
function showRejectionReason(row) {
  currentRejectionOrder.value = row
  rejectionDialogVisible.value = true
}
</script>

<style scoped>
.el-table {
  margin-top: 20px;
}

.el-card {
  background-color: #f9f9f9;
}
</style>