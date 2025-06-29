
<template>
  <div>
    <el-button type="primary" @click="openDialog()">新增</el-button>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="id" label="id" />
      <el-table-column prop="username" label="username" />
      <el-table-column prop="password" label="password" />
      <el-table-column prop="role" label="role" />
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="small" @click="edit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="dialogTitle" v-model="dialogVisible">
      <el-form :model="form" label-width="100px">
        <el-form-item label="username"><el-input v-model="form.username" /></el-form-item>
      <el-form-item label="password"><el-input v-model="form.password" /></el-form-item>
      <el-form-item label="role"><el-input v-model="form.role" /></el-form-item>
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
import { ElMessageBox } from 'element-plus'

const tableData = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref("")
const form = reactive({ id: "", username: "", password: "", role: "" })

function getList() {
  request.get('/auth/user/list').then(res => { tableData.value = res.data.data })
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
    request.put('/auth/user/update', form).then(() => { dialogVisible.value = false; getList() })
  } else {
    request.post('/auth/user/create', form).then(() => { dialogVisible.value = false; getList() })
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
    // 用户点击确认
    request.delete(`/auth/user/delete/${id}`).then(() => { 
      getList()
      ElMessage.success('删除成功')
    })
  })
  .catch(() => {
    ElMessage.info('已取消删除')
  })
}
</script>
