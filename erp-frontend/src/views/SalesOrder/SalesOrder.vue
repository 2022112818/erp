<template>
  <div>
    <h2>销售订单管理</h2>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" sortable />
      <el-table-column prop="customerId" label="客户ID" width="120" />
      <el-table-column prop="productName" label="商品名称" width="150" />
      <el-table-column prop="orderDate" label="订单日期" width="120" sortable />

      <el-table-column prop="totalAmount" label="总金额" width="120">
        <template #default="scope">
          ¥{{ parseFloat(scope.row.totalAmount).toFixed(2) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120">
        <template #default="scope">
          <el-button size="small" type="primary" @click="viewDetail(scope.row)">查看详情</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 订单详情弹窗 -->
    <el-dialog title="订单详情" v-model="detailDialogVisible" width="600px">
      <el-descriptions :column="2">
        <el-descriptions-item label="订单ID">{{ orderDetail.id }}</el-descriptions-item>
        <el-descriptions-item label="客户ID">{{ orderDetail.customerId }}</el-descriptions-item>
        <el-descriptions-item label="商品名称">{{ orderDetail.productName }}</el-descriptions-item>
        <el-descriptions-item label="订单日期">{{ orderDetail.orderDate }}</el-descriptions-item>
        <el-descriptions-item label="总金额">
          <span style="color: #f56c6c; font-weight: bold;">¥{{ parseFloat(orderDetail.totalAmount || 0).toFixed(2) }}</span>
        </el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <el-button @click="detailDialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import request from '../../api/request.js'
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'

const tableData = ref([])
const detailDialogVisible = ref(false)
const orderDetail = reactive({
  id: "",
  customerId: "",
  productName: "",
  orderDate: "",
  quantity: "",
  totalAmount: ""
})

// 获取订单列表
function getOrderList() {
  request.get('/sales/salesOrder/list').then(res => { 
    // res 是完整的 axios 响应对象，业务数据在 res.data 中
    const businessData = res.data
    
    if (businessData && businessData.success) {
      // businessData.data 就是订单数组
      if (Array.isArray(businessData.data)) {
        tableData.value = businessData.data
      } else {
        console.error('businessData.data不是数组:', businessData.data)
        ElMessage.error('数据格式错误')
      }
    } else {
      console.error('API返回失败:', businessData)
      ElMessage.error('获取订单列表失败: ' + (businessData?.message || '未知错误'))
    }
  }).catch(error => {
    console.error('API请求异常:', error)
    ElMessage.error('网络请求失败')
  })
}

// 查看订单详情
function viewDetail(row) {
  // 将选中行的数据复制到订单详情对象中
  for (let key in orderDetail) {
    orderDetail[key] = row[key] || ""
  }
  detailDialogVisible.value = true
}

// 组件挂载时获取数据
onMounted(() => {
  getOrderList()
})
</script>

<style scoped>
h2 {
  margin-bottom: 20px;
  color: #303133;
}

.el-table {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>