<!-- 完整的库存管理系统 - InventoryManagement.vue -->
<template>
  <div class="inventory-management">
    <div class="header">
      <h2>库存管理系统</h2>
      <div class="breadcrumb">
        <span v-if="currentView === 'main'">首页</span>
        <span v-else>
          <el-link @click="goToMain">首页</el-link>
          <span> / {{ getViewTitle() }}</span>
        </span>
      </div>
    </div>

    <!-- 主页面 - 选择功能 -->
    <div v-if="currentView === 'main'" class="main-content">
      <div class="module-cards">
        <div class="card" @click="goToView('inbound')">
          <div class="card-icon">
            <el-icon size="40"><Plus /></el-icon>
          </div>
          <h3>入库管理</h3>
          <p>管理商品入库记录</p>
        </div>
        
        <div class="card" @click="goToView('outbound')">
          <div class="card-icon">
            <el-icon size="40"><Minus /></el-icon>
          </div>
          <h3>出库管理</h3>
          <p>管理商品出库记录</p>
        </div>
        
        <div class="card" @click="goToView('production')">
          <div class="card-icon">
            <el-icon size="40"><Setting /></el-icon>
          </div>
          <h3>生产管理</h3>
          <p>管理生产记录和原材料</p>
        </div>
        
        <div class="card" @click="goToView('inventory')">
          <div class="card-icon">
            <el-icon size="40"><List /></el-icon>
          </div>
          <h3>库存查询</h3>
          <p>查看当前库存状态</p>
        </div>
      </div>
    </div>

    <!-- 入库管理页面 -->
    <div v-if="currentView === 'inbound'" class="sub-content">
      <div class="page-header">
        <div class="back-section">
          <el-button type="default" @click="goToMain" class="back-button">
            <el-icon><ArrowLeft /></el-icon>
            返回主页
          </el-button>
        </div>
        <div class="toolbar">
          <el-button type="primary" @click="openInboundDialog()">
            <el-icon><Plus /></el-icon>
            新增入库
          </el-button>
        </div>
      </div>
      
      <el-table :data="inboundData" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="productName" label="商品名称" />
        <el-table-column prop="warehouseId" label="仓库ID" />
        <el-table-column prop="quantity" label="数量" />
        <el-table-column prop="inboundDate" label="入库日期" />
        <el-table-column prop="operator" label="操作员" />
        <el-table-column prop="remark" label="备注" />
      </el-table>
    </div>

    <!-- 出库管理页面 -->
    <div v-if="currentView === 'outbound'" class="sub-content">
      <div class="page-header">
        <div class="back-section">
          <el-button type="default" @click="goToMain" class="back-button">
            <el-icon><ArrowLeft /></el-icon>
            返回主页
          </el-button>
        </div>
        <div class="toolbar">
          <el-button type="primary" @click="openOutboundDialog()">
            <el-icon><Plus /></el-icon>
            新增出库
          </el-button>
        </div>
      </div>
      
      <el-table :data="outboundData" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="productName" label="商品名称" />
        <el-table-column prop="warehouseId" label="仓库ID" />
        <el-table-column prop="quantity" label="数量" />
        <el-table-column prop="outboundDate" label="出库日期" />
        <el-table-column prop="operator" label="操作员" />
        <el-table-column prop="remark" label="备注" />
      </el-table>
    </div>

    <!-- 生产管理页面 -->
    <div v-if="currentView === 'production'" class="sub-content">
      <div class="page-header">
        <div class="back-section">
          <el-button type="default" @click="goToMain" class="back-button">
            <el-icon><ArrowLeft /></el-icon>
            返回主页
          </el-button>
        </div>
        <div class="toolbar">
          <el-button type="primary" @click="openProductionDialog()">
            <el-icon><Plus /></el-icon>
            新增生产记录
          </el-button>
        </div>
      </div>
      
      <el-table :data="productionData" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="productName" label="产品名称" />
        <el-table-column prop="productWarehouseId" label="产品仓库ID" />
        <el-table-column prop="productQuantity" label="产品数量" />
        <el-table-column prop="productionTime" label="生产时间" />
        <el-table-column prop="operatorName" label="操作员" />
        <el-table-column prop="remark" label="备注" />
      </el-table>
    </div>

    <!-- 库存查询页面 -->
    <div v-if="currentView === 'inventory'" class="sub-content">
      <div class="page-header">
        <div class="back-section">
          <el-button type="default" @click="goToMain" class="back-button">
            <el-icon><ArrowLeft /></el-icon>
            返回主页
          </el-button>
        </div>
      </div>
      
      <el-table :data="inventoryData" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="productName" label="商品名称" />
        <el-table-column prop="warehouseId" label="仓库ID" />
        <el-table-column prop="quantity" label="当前库存" />
        <el-table-column prop="updateTime" label="更新时间" />
      </el-table>
    </div>

    <!-- 入库对话框 -->
    <el-dialog title="新增入库" v-model="inboundDialogVisible" width="500px">
      <el-form :model="inboundForm" label-width="100px" ref="inboundFormRef">
        <el-form-item label="商品名称" required>
          <el-input v-model="inboundForm.productName" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="仓库ID" required>
          <el-input v-model="inboundForm.warehouseId" type="number" placeholder="请输入仓库ID" />
        </el-form-item>
        <el-form-item label="数量" required>
          <el-input v-model="inboundForm.quantity" type="number" placeholder="请输入数量" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="inboundForm.remark" type="textarea" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="inboundDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmInbound()">提交</el-button>
      </template>
    </el-dialog>

    <!-- 出库对话框 -->
    <el-dialog title="新增出库" v-model="outboundDialogVisible" width="500px">
      <el-form :model="outboundForm" label-width="100px" ref="outboundFormRef">
        <el-form-item label="商品名称" required>
          <el-input v-model="outboundForm.productName" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="仓库ID" required>
          <el-input v-model="outboundForm.warehouseId" type="number" placeholder="请输入仓库ID" />
        </el-form-item>
        <el-form-item label="数量" required>
          <el-input v-model="outboundForm.quantity" type="number" placeholder="请输入数量" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="outboundForm.remark" type="textarea" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="outboundDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmOutbound()">提交</el-button>
      </template>
    </el-dialog>

    <!-- 生产对话框 -->
    <el-dialog title="新增生产记录" v-model="productionDialogVisible" width="800px">
      <el-form :model="productionForm" label-width="120px" ref="productionFormRef">
        <el-form-item label="产品名称" required>
          <el-input v-model="productionForm.productName" placeholder="请输入产品名称" />
        </el-form-item>
        <el-form-item label="产品仓库ID" required>
          <el-input v-model="productionForm.productWarehouseId" type="number" placeholder="请输入产品仓库ID" />
        </el-form-item>
        <el-form-item label="产品数量" required>
          <el-input v-model="productionForm.productQuantity" type="number" placeholder="请输入产品数量" />
        </el-form-item>
        <el-form-item label="操作员姓名" required>
          <el-input v-model="productionForm.operatorName" placeholder="请输入操作员姓名" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="productionForm.remark" type="textarea" placeholder="请输入备注" />
        </el-form-item>
        
        <el-divider content-position="left">
          <span>原材料信息</span>
          <el-button type="primary" size="small" circle @click="addMaterial" style="margin-left: 10px;">
            <el-icon><Plus /></el-icon>
          </el-button>
        </el-divider>
        
        <div v-if="productionForm.materials.length === 0" class="no-materials">
          <el-empty description="暂无原材料，请点击上方按钮添加" />
        </div>
        
        <div v-for="(material, index) in productionForm.materials" :key="`material-${index}-${materialKey}`" class="material-item">
          <div class="material-header">
            <span class="material-title">原材料 {{ index + 1 }}</span>
            <el-button type="danger" size="small" circle @click="removeMaterial(index)">
              <el-icon><Minus /></el-icon>
            </el-button>
          </div>
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="原材料名称" required>
                <el-input 
                  v-model="material.materialName" 
                  placeholder="请输入原材料名称"
                  @input="handleMaterialInput(index, 'materialName', $event)"
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="仓库ID" required>
                <el-input 
                  v-model="material.materialWarehouseId" 
                  type="number" 
                  placeholder="请输入仓库ID"
                  @input="handleMaterialInput(index, 'materialWarehouseId', $event)"
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="使用数量" required>
                <el-input 
                  v-model="material.usedQuantity" 
                  type="number" 
                  placeholder="请输入使用数量"
                  @input="handleMaterialInput(index, 'usedQuantity', $event)"
                />
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </el-form>
      <template #footer>
        <el-button @click="productionDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmProduction()">提交</el-button>
      </template>
    </el-dialog>

    <!-- 确认对话框 -->
    <el-dialog title="确认提交" v-model="confirmDialogVisible" width="400px">
      <div style="text-align: center; padding: 20px;">
        <el-icon size="50" color="#E6A23C"><Warning /></el-icon>
        <p style="margin: 20px 0; font-size: 16px;">
          确定要提交这条记录吗？
        </p>
      </div>
      <template #footer>
        <el-button @click="confirmDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitRecord()">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, nextTick } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus, Minus, Setting, List, Warning, ArrowLeft } from '@element-plus/icons-vue'
import request from '../../api/request.js'

// 视图状态
const currentView = ref('main')
const inboundDialogVisible = ref(false)
const outboundDialogVisible = ref(false)
const productionDialogVisible = ref(false)
const confirmDialogVisible = ref(false)
const currentAction = ref('')
const materialKey = ref(0) // 用于强制更新材料列表

// 数据
const inboundData = ref([])
const outboundData = ref([])
const productionData = ref([])
const inventoryData = ref([])

// 表单数据
const inboundForm = reactive({
  productName: '',
  warehouseId: '',
  quantity: '',
  remark: ''
})

const outboundForm = reactive({
  productName: '',
  warehouseId: '',
  quantity: '',
  remark: ''
})

const productionForm = reactive({
  productName: '',
  productWarehouseId: '',
  productQuantity: '',
  operatorName: '',
  remark: '',
  materials: []
})

// 导航方法
function goToView(view) {
  currentView.value = view
  loadData(view)
}

function goToMain() {
  currentView.value = 'main'
}

function getViewTitle() {
  const titles = {
    inbound: '入库管理',
    outbound: '出库管理',
    production: '生产管理',
    inventory: '库存查询'
  }
  return titles[currentView.value] || ''
}

// 数据加载方法
function loadData(view) {
  switch (view) {
    case 'inbound':
      loadInboundData()
      break
    case 'outbound':
      loadOutboundData()
      break
    case 'production':
      loadProductionData()
      break
    case 'inventory':
      loadInventoryData()
      break
  }
}

function loadInboundData() {
  request.get('/inventory/inbound/list').then(res => {
    inboundData.value = res.data
  }).catch(err => {
    ElMessage.error('加载入库数据失败')
  })
}

function loadOutboundData() {
  request.get('/inventory/outbound/list').then(res => {
    outboundData.value = res.data
  }).catch(err => {
    ElMessage.error('加载出库数据失败')
  })
}

function loadProductionData() {
  request.get('/inventory/production/list').then(res => {
    productionData.value = res.data
  }).catch(err => {
    ElMessage.error('加载生产数据失败')
  })
}

function loadInventoryData() {
  request.get('/inventory/list').then(res => {
    inventoryData.value = res.data
  }).catch(err => {
    ElMessage.error('加载库存数据失败')
  })
}

// 对话框打开方法
function openInboundDialog() {
  resetInboundForm()
  inboundDialogVisible.value = true
}

function openOutboundDialog() {
  resetOutboundForm()
  outboundDialogVisible.value = true
}

function openProductionDialog() {
  resetProductionForm()
  productionDialogVisible.value = true
}

// 表单重置方法
function resetInboundForm() {
  Object.assign(inboundForm, {
    productName: '',
    warehouseId: '',
    quantity: '',
    remark: ''
  })
}

function resetOutboundForm() {
  Object.assign(outboundForm, {
    productName: '',
    warehouseId: '',
    quantity: '',
    remark: ''
  })
}

function resetProductionForm() {
  Object.assign(productionForm, {
    productName: '',
    productWarehouseId: '',
    productQuantity: '',
    operatorName: '',
    remark: '',
    materials: []
  })
  materialKey.value++
}

// 原材料管理方法
function addMaterial() {
  productionForm.materials.push({
    materialName: '',
    materialWarehouseId: '',
    usedQuantity: ''
  })
  materialKey.value++
  
  nextTick(() => {
    console.log('添加原材料后:', productionForm.materials)
  })
}

function removeMaterial(index) {
  productionForm.materials.splice(index, 1)
  materialKey.value++
  
  nextTick(() => {
    console.log('删除原材料后:', productionForm.materials)
  })
}

// 原材料输入处理
function handleMaterialInput(index, field, value) {
  if (productionForm.materials[index]) {
    productionForm.materials[index][field] = value
    console.log(`更新原材料${index}的${field}:`, value)
  }
}

// 确认提交方法
function confirmInbound() {
  if (!validateInboundForm()) return
  currentAction.value = 'inbound'
  confirmDialogVisible.value = true
}

function confirmOutbound() {
  if (!validateOutboundForm()) return
  currentAction.value = 'outbound'
  confirmDialogVisible.value = true
}

function confirmProduction() {
  if (!validateProductionForm()) return
  currentAction.value = 'production'
  confirmDialogVisible.value = true
}

// 表单验证方法
function validateInboundForm() {
  if (!inboundForm.productName.trim()) {
    ElMessage.error('请输入商品名称')
    return false
  }
  if (!inboundForm.warehouseId) {
    ElMessage.error('请输入仓库ID')
    return false
  }
  if (!inboundForm.quantity || Number(inboundForm.quantity) <= 0) {
    ElMessage.error('请输入有效的数量')
    return false
  }
  return true
}

function validateOutboundForm() {
  if (!outboundForm.productName.trim()) {
    ElMessage.error('请输入商品名称')
    return false
  }
  if (!outboundForm.warehouseId) {
    ElMessage.error('请输入仓库ID')
    return false
  }
  if (!outboundForm.quantity || Number(outboundForm.quantity) <= 0) {
    ElMessage.error('请输入有效的数量')
    return false
  }
  return true
}

function validateProductionForm() {
  if (!productionForm.productName.trim()) {
    ElMessage.error('请输入产品名称')
    return false
  }
  if (!productionForm.productWarehouseId) {
    ElMessage.error('请输入产品仓库ID')
    return false
  }
  if (!productionForm.productQuantity || Number(productionForm.productQuantity) <= 0) {
    ElMessage.error('请输入有效的产品数量')
    return false
  }
  if (!productionForm.operatorName.trim()) {
    ElMessage.error('请输入操作员姓名')
    return false
  }
  
  // 验证原材料信息
  if (productionForm.materials.length === 0) {
    ElMessage.error('请至少添加一个原材料')
    return false
  }
  
  for (let i = 0; i < productionForm.materials.length; i++) {
    const material = productionForm.materials[i]
    if (!material.materialName?.trim()) {
      ElMessage.error(`请输入第${i + 1}个原材料的名称`)
      return false
    }
    if (!material.materialWarehouseId) {
      ElMessage.error(`请输入第${i + 1}个原材料的仓库ID`)
      return false
    }
    if (!material.usedQuantity || Number(material.usedQuantity) <= 0) {
      ElMessage.error(`请输入第${i + 1}个原材料的有效使用数量`)
      return false
    }
  }
  
  return true
}

function submitRecord() {
  confirmDialogVisible.value = false
  
  switch (currentAction.value) {
    case 'inbound':
      submitInbound()
      break
    case 'outbound':
      submitOutbound()
      break
    case 'production':
      submitProduction()
      break
  }
}

function submitInbound() {
  request.post('/inventory/inbound/create', inboundForm).then(res => {
    inboundDialogVisible.value = false
    ElMessage.success('入库记录创建成功')
    loadInboundData()
  }).catch(err => {
    ElMessage.error('入库记录创建失败')
  })
}

function submitOutbound() {
  request.post('/inventory/outbound/create', outboundForm).then(res => {
    outboundDialogVisible.value = false
    ElMessage.success('出库记录创建成功')
    loadOutboundData()
  }).catch(err => {
    ElMessage.error('出库记录创建失败')
  })
}

function submitProduction() {
  request.post('/inventory/production/create', productionForm).then(res => {
    productionDialogVisible.value = false
    ElMessage.success('生产记录创建成功')
    loadProductionData()
  }).catch(err => {
    ElMessage.error('生产记录创建失败')
  })
}

// 组件挂载时加载数据
onMounted(() => {
  console.log('组件已挂载')
})
</script>

<style scoped>
.inventory-management {
  padding: 20px;
  min-height: 100vh;
  background-color: #f5f5f5;
}

.header {
  margin-bottom: 30px;
  border-bottom: 1px solid #eee;
  padding-bottom: 15px;
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.header h2 {
  margin: 0 0 10px 0;
  color: #333;
  font-size: 24px;
}

.breadcrumb {
  color: #666;
  font-size: 14px;
}

.breadcrumb .el-link {
  font-size: 14px;
}

.main-content {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 60vh;
}

.module-cards {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 30px;
  max-width: 800px;
  width: 100%;
}

.card {
  background: #fff;
  border: 1px solid #e4e7ed;
  border-radius: 12px;
  padding: 40px 30px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.card:hover {
  border-color: #409eff;
  box-shadow: 0 4px 20px rgba(64, 158, 255, 0.2);
  transform: translateY(-2px);
}

.card-icon {
  color: #409eff;
  margin-bottom: 20px;
}

.card h3 {
  margin: 0 0 15px 0;
  color: #333;
  font-size: 20px;
}

.card p {
  margin: 0;
  color: #666;
  font-size: 14px;
}

.sub-content {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #eee;
}

.back-section {
  flex-shrink: 0;
}

.back-button {
  color: #666;
  border-color: #dcdfe6;
  background-color: #fff;
  transition: all 0.3s ease;
}

.back-button:hover {
  color: #409eff;
  border-color: #409eff;
  background-color: #ecf5ff;
}

.toolbar {
  flex: 1;
  display: flex;
  justify-content: flex-end;
}

.material-item {
  background: #f9f9f9;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 15px;
  border: 1px solid #e4e7ed;
  position: relative;
}

.material-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.material-title {
  font-weight: bold;
  color: #333;
  font-size: 16px;
}

.no-materials {
  text-align: center;
  padding: 40px 20px;
  color: #999;
}

.el-table {
  border-radius: 8px;
  overflow: hidden;
}

.el-dialog {
  border-radius: 12px;
}

.el-form-item {
  margin-bottom: 18px;
}

.el-divider {
  margin: 25px 0;
}
</style>