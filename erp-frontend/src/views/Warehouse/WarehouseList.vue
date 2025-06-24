
<template>
  <div>
    <el-button type="primary" @click="openDialog()">新增</el-button>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="id" label="id" />
      <el-table-column prop="name" label="name" />
      <el-table-column prop="location" label="location" />
      <el-table-column prop="manager" label="manager" />
      <el-table-column prop="contact" label="contact" />
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
      <el-form-item label="location"><el-input v-model="form.location" /></el-form-item>
      <el-form-item label="manager"><el-input v-model="form.manager" /></el-form-item>
      <el-form-item label="contact"><el-input v-model="form.contact" /></el-form-item>
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
const form = reactive({ id: "", name: "", location: "", manager: "", contact: "" })

function getList() {
  request.get('/warehouse/list').then(res => { tableData.value = res.data })
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
    request.put('/warehouse/update', form).then(() => { dialogVisible.value = false; getList() })
  } else {
    request.post('/warehouse/create', form).then(() => { dialogVisible.value = false; getList() })
  }
}

function remove(id) {
  request.delete(`/{'warehouse/delete/'}${id}`).then(() => { getList() })
}
</script>
