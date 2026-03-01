<template>
  <div class="users-page">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>用户管理</span>
          <el-button type="primary" @click="showAddDialog">
            <el-icon><Plus /></el-icon>
            添加用户
          </el-button>
        </div>
      </template>

      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="用户名">
          <el-input v-model="searchForm.keyword" placeholder="请输入用户名/姓名" clearable />
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="searchForm.role" placeholder="全部角色" clearable>
            <el-option label="学生" value="student" />
            <el-option label="教师" value="teacher" />
            <el-option label="管理员" value="admin" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="全部状态" clearable>
            <el-option label="正常" value="active" />
            <el-option label="禁用" value="disabled" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="userList" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" width="120" />
        <el-table-column prop="name" label="姓名" width="100" />
        <el-table-column prop="role" label="角色" width="100">
          <template #default="{ row }">
            <el-tag :type="getRoleTagType(row.role)">{{ getRoleName(row.role) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="college" label="学院" min-width="150" />
        <el-table-column prop="email" label="邮箱" min-width="180" />
        <el-table-column prop="phone" label="电话" width="130" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 'active' ? 'success' : 'danger'">
              {{ row.status === 'active' ? '正常' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="160" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="editUser(row)">编辑</el-button>
            <el-button type="warning" link @click="resetPassword(row)">重置密码</el-button>
            <el-button type="danger" link @click="deleteUser(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="pagination.page"
        v-model:page-size="pagination.pageSize"
        :page-sizes="[10, 20, 50, 100]"
        :total="pagination.total"
        layout="total, sizes, prev, pager, next, jumper"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </el-card>

    <!-- 添加/编辑用户对话框 -->
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑用户' : '添加用户'" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" :disabled="isEdit" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="form.role" placeholder="请选择角色" style="width: 100%">
            <el-option label="学生" value="student" />
            <el-option label="教师" value="teacher" />
            <el-option label="管理员" value="admin" />
          </el-select>
        </el-form-item>
        <el-form-item label="学院" prop="college">
          <el-input v-model="form.college" placeholder="请输入学院" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item v-if="!isEdit" label="密码" prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox, FormInstance, FormRules } from 'element-plus'

const loading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()

const searchForm = reactive({
  keyword: '',
  role: '',
  status: ''
})

const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

const userList = ref([
  { id: 1, username: 'zhangsan', name: '张三', role: 'student', college: '计算机学院', email: 'zhangsan@example.com', phone: '13800138001', status: 'active', createTime: '2024-01-01 10:00:00' },
  { id: 2, username: 'lisi', name: '李四', role: 'teacher', college: '计算机学院', email: 'lisi@example.com', phone: '13800138002', status: 'active', createTime: '2024-01-02 10:00:00' },
  { id: 3, username: 'admin', name: '管理员', role: 'admin', college: '教务处', email: 'admin@example.com', phone: '13800138000', status: 'active', createTime: '2024-01-01 00:00:00' },
])

const form = reactive({
  id: 0,
  username: '',
  name: '',
  role: 'student',
  college: '',
  email: '',
  phone: '',
  password: ''
})

const rules: FormRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  role: [{ required: true, message: '请选择角色', trigger: 'change' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const getRoleTagType = (role: string) => {
  const map: Record<string, string> = { student: 'primary', teacher: 'success', admin: 'danger' }
  return map[role] || ''
}

const getRoleName = (role: string) => {
  const map: Record<string, string> = { student: '学生', teacher: '教师', admin: '管理员' }
  return map[role] || role
}

const handleSearch = () => {
  ElMessage.info('搜索功能待实现')
}

const resetSearch = () => {
  searchForm.keyword = ''
  searchForm.role = ''
  searchForm.status = ''
}

const showAddDialog = () => {
  isEdit.value = false
  Object.assign(form, { id: 0, username: '', name: '', role: 'student', college: '', email: '', phone: '', password: '' })
  dialogVisible.value = true
}

const editUser = (row: any) => {
  isEdit.value = true
  Object.assign(form, row)
  dialogVisible.value = true
}

const resetPassword = async (row: any) => {
  try {
    await ElMessageBox.confirm(`确定要重置用户 ${row.name} 的密码吗？`, '提示', { type: 'warning' })
    ElMessage.success('密码已重置为默认密码')
  } catch {}
}

const deleteUser = async (row: any) => {
  try {
    await ElMessageBox.confirm(`确定要删除用户 ${row.name} 吗？`, '提示', { type: 'warning' })
    ElMessage.success('删除成功')
  } catch {}
}

const handleSubmit = async () => {
  const valid = await formRef.value?.validate().catch(() => false)
  if (!valid) return
  ElMessage.success(isEdit.value ? '修改成功' : '添加成功')
  dialogVisible.value = false
}

onMounted(() => {
  pagination.total = userList.value.length
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-form {
  margin-bottom: 20px;
}
</style>
