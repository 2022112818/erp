
<template>
  <div>
    <el-button type="primary" @click="openDialog()">新增</el-button>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="id" label="id" />
      <el-table-column prop="purchaseOrderId" label="purchaseOrderId" />
      <el-table-column prop="warehouseId" label="warehouseId" />
      <el-table-column prop="quantity" label="quantity" />
      <el-table-column prop="inboundDate" label="inboundDate" />
      <el-table-column prop="operator" label="operator" />
      <el-table-column prop="remark" label="remark" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" @click="edit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="dialogTitle" v-model="dialogVisible">
      <el-form :model="form" label-width="100px">
        <el-form-item label="purchaseOrderId"><el-input v-model="form.purchaseOrderId" /></el-form-item>
      <el-form-item label="warehouseId"><el-input v-model="form.warehouseId" /></el-form-item>
      <el-form-item label="quantity"><el-input v-model="form.quantity" /></el-form-item>
      <el-form-item label="inboundDate"><el-input v-model="form.inboundDate" /></el-form-item>
      <el-form-item label="operator"><el-input v-model="form.operator" /></el-form-item>
      <el-form-item label="remark"><el-input v-model="form.remark" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save()">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import request from '../../api/request.js'
import { ref, reactive } from 'vue'

const tableData = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref("")
const form = reactive({ id: "", purchaseOrderId: "", warehouseId: "", quantity: "", inboundDate: "", operator: "", remark: "" })

function getList() {
  request.get('/inbound/list').then(res => { tableData.value = res.data })
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
    request.put('/inbound/update', form).then(() => { dialogVisible.value = false; getList() })
  } else {
    request.post('/inbound/create', form).then(() => { dialogVisible.value = false; getList() })
  }
}

function remove(id) {
  request.delete(`/{'inbound/delete/'}${id}`).then(() => { getList() })
}
</script>
