
<template>
  <div>
    <el-button type="primary" @click="openDialog()">新增</el-button>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="id" label="id" />
      <el-table-column prop="productId" label="productId" />
      <el-table-column prop="warehouseId" label="warehouseId" />
      <el-table-column prop="minThreshold" label="minThreshold" />
      <el-table-column prop="maxThreshold" label="maxThreshold" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" @click="edit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="dialogTitle" v-model="dialogVisible">
      <el-form :model="form" label-width="100px">
        <el-form-item label="productId"><el-input v-model="form.productId" /></el-form-item>
      <el-form-item label="warehouseId"><el-input v-model="form.warehouseId" /></el-form-item>
      <el-form-item label="minThreshold"><el-input v-model="form.minThreshold" /></el-form-item>
      <el-form-item label="maxThreshold"><el-input v-model="form.maxThreshold" /></el-form-item>
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
const form = reactive({ id: "", productId: "", warehouseId: "", minThreshold: "", maxThreshold: "" })

function getList() {
  request.get('/stockalert/list').then(res => { tableData.value = res.data })
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
    request.put('/stockalert/update', form).then(() => { dialogVisible.value = false; getList() })
  } else {
    request.post('/stockalert/create', form).then(() => { dialogVisible.value = false; getList() })
  }
}

function remove(id) {
  request.delete(`/{'stockalert/delete/'}${id}`).then(() => { getList() })
}
</script>
