
<template>
  <div>
    <el-button type="primary" @click="openDialog()">新增</el-button>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="id" label="id" />
      <el-table-column prop="salesOrderId" label="salesOrderId" />
      <el-table-column prop="warehouseId" label="warehouseId" />
      <el-table-column prop="quantity" label="quantity" />
      <el-table-column prop="outboundDate" label="outboundDate" />
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
        <el-form-item label="salesOrderId"><el-input v-model="form.salesOrderId" /></el-form-item>
      <el-form-item label="warehouseId"><el-input v-model="form.warehouseId" /></el-form-item>
      <el-form-item label="quantity"><el-input v-model="form.quantity" /></el-form-item>
      <el-form-item label="outboundDate"><el-input v-model="form.outboundDate" /></el-form-item>
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
const form = reactive({ id: "", salesOrderId: "", warehouseId: "", quantity: "", outboundDate: "", operator: "", remark: "" })

function getList() {
  request.get('/outbound/list').then(res => { tableData.value = res.data })
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
    request.put('/outbound/update', form).then(() => { dialogVisible.value = false; getList() })
  } else {
    request.post('/outbound/create', form).then(() => { dialogVisible.value = false; getList() })
  }
}

function remove(id) {
  request.delete(`/{'outbound/delete/'}${id}`).then(() => { getList() })
}
</script>
