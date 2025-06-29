<template>
  <div>
    <el-button type="primary" @click="openDialog()">新增</el-button>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="id" label="id" />
      <el-table-column prop="name" label="name" />
      <el-table-column prop="category" label="category" />
      <el-table-column prop="unit" label="unit" />
      <el-table-column prop="price" label="price" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" @click="edit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
          <el-button size="small" type="success" @click="purchase(scope.row)">购买</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 商品新增/编辑弹窗 -->
    <el-dialog :title="dialogTitle" v-model="dialogVisible">
      <el-form :model="form" label-width="100px">
        <el-form-item label="name"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="category"><el-input v-model="form.category" /></el-form-item>
        <el-form-item label="unit"><el-input v-model="form.unit" /></el-form-item>
        <el-form-item label="price"><el-input v-model="form.price" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save()">保存</el-button>
      </template>
    </el-dialog>

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
        <el-form-item label="订单日期" required>
          <el-date-picker
            v-model="purchaseForm.orderDate"
            type="date"
            placeholder="请选择订单日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="总金额">
          <el-input v-model="purchaseForm.totalAmount" disabled />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="purchaseDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="savePurchaseOrder()">提交订单</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import request from '../../api/request.js'
import { ref, reactive } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'

const tableData = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref("")
const form = reactive({
  id: "",
  name: "",
  category: "",
  unit: "",
  price: ""
})

// 购买相关的响应式数据
const purchaseDialogVisible = ref(false)
const purchaseForm = reactive({
  id: "",
  productName: "",  // 添加productName字段
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

function getList() {
  request.get('/product/product/list').then(res => { 
    tableData.value = res.data.data
  })
}
getList()

function openDialog() {
  dialogTitle.value = "新增"
  for (let key in form) form[key] = ""
  dialogVisible.value = true
}

function edit(row) {
  dialogTitle.value = "编辑"
  for (let key in form) form[key] = row[key]
  dialogVisible.value = true
}

function save() {
  if (form.id) {
    request.put('/product/product/update', form).then(() => { 
      dialogVisible.value = false; 
      getList()
      ElMessage.success('更新成功')
    })
  } else {
    request.post('/product/product/create', form).then(() => { 
      dialogVisible.value = false; 
      getList()
      ElMessage.success('创建成功')
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
    request.delete(`/product/product/delete/${id}`).then(() => {
       getList()
      ElMessage.success('删除成功')
    })
  })
  .catch(() => {
    ElMessage.info('已取消删除')
  })
}

// 购买相关函数
function purchase(row) {
  // 重置购买表单
  for (let key in purchaseForm) {
    purchaseForm[key] = ""
  }
  
  // 设置产品信息到purchaseForm中
  purchaseForm.productName = row.name  // 将产品名称设置到purchaseForm中
  
  // 重置显示数据
  purchaseDisplay.productName = row.name
  purchaseDisplay.unitPrice = row.price
  purchaseDisplay.quantity = 1
  
  // 设置默认值
  purchaseForm.orderDate = new Date().toISOString().split('T')[0] // 默认今天
  purchaseForm.totalAmount = row.price
  
  purchaseDialogVisible.value = true
}

function calculateTotal() {
  if (purchaseDisplay.quantity && purchaseDisplay.unitPrice) {
    purchaseForm.totalAmount = (parseFloat(purchaseDisplay.unitPrice) * purchaseDisplay.quantity).toFixed(2)
  }
}

function savePurchaseOrder() {
  // 基本验证
  if (!purchaseDisplay.quantity || purchaseDisplay.quantity <= 0) {
    ElMessage.warning('请输入有效的购买数量')
    return
  }
  
  if (!purchaseForm.customerId) {
    ElMessage.warning('请输入客户ID')
    return
  }
  
  if (!purchaseForm.orderDate) {
    ElMessage.warning('请选择订单日期')
    return
  }

  // 构建发送给后端的订单数据，包含productName和quantity
  const orderData = {
    ...purchaseForm,
    quantity: purchaseDisplay.quantity  // 添加购买数量到发送数据中
  }

  if (purchaseForm.id) {
    // 更新订单
    request.put('/sales/salesOrder/update', orderData).then(() => { 
      purchaseDialogVisible.value = false
      ElMessage.success('订单更新成功')
    }).catch(() => {
      ElMessage.error('订单更新失败')
    })
  } else {
    // 创建新订单
    request.post('/sales/salesOrder/create', orderData).then(() => { 
      purchaseDialogVisible.value = false
      ElMessage.success('订单创建成功')
    }).catch(() => {
      ElMessage.error('订单创建失败')
    })
  }
}
</script>