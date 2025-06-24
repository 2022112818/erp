
<template>
  <div>
    <el-button type="primary" @click="openDialog()">新增</el-button>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="id" label="id" />
      <el-table-column prop="name" label="name" />
      <el-table-column prop="address" label="address" />
      <el-table-column prop="contactPerson" label="contactPerson" />
      <el-table-column prop="phone" label="phone" />
      <el-table-column prop="bankAccount" label="bankAccount" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" @click="edit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="dialogTitle" v-model="dialogVisible">
      <el-form :model="form" label-width="100px">
        <el-form-item label="name"><el-input v-model="form.name" /></el-form-item>
      <el-form-item label="address"><el-input v-model="form.address" /></el-form-item>
      <el-form-item label="contactPerson"><el-input v-model="form.contactPerson" /></el-form-item>
      <el-form-item label="phone"><el-input v-model="form.phone" /></el-form-item>
      <el-form-item label="bankAccount"><el-input v-model="form.bankAccount" /></el-form-item>
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
const form = reactive({ id: "", name: "", address: "", contactPerson: "", phone: "", bankAccount: "" })

function getList() {
  request.get('/supplier/list').then(res => { tableData.value = res.data })
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
    request.put('/supplier/update', form).then(() => { dialogVisible.value = false; getList() })
  } else {
    request.post('/supplier/create', form).then(() => { dialogVisible.value = false; getList() })
  }
}

function remove(id) {
  request.delete(`/{'supplier/delete/'}${id}`).then(() => { getList() })
}
</script>
