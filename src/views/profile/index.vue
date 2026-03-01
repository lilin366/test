<template>
  <div class="profile-page">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card shadow="hover">
          <div class="user-info">
            <el-avatar :size="100" :src="userInfo.avatar">
              {{ userInfo.name?.charAt(0) }}
            </el-avatar>
            <h2>{{ userInfo.name }}</h2>
            <el-tag :type="getRoleTagType(userInfo.role)">{{ getRoleName(userInfo.role) }}</el-tag>
            <p class="college">{{ userInfo.college }}</p>
          </div>
        </el-card>
      </el-col>

      <el-col :span="16">
        <el-card shadow="hover">
          <el-tabs v-model="activeTab">
            <el-tab-pane label="基本信息" name="basic">
              <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" style="max-width: 500px">
                <el-form-item label="用户名">
                  <el-input v-model="form.username" disabled />
                </el-form-item>
                <el-form-item label="姓名" prop="name">
                  <el-input v-model="form.name" />
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                  <el-input v-model="form.email" />
                </el-form-item>
                <el-form-item label="电话" prop="phone">
                  <el-input v-model="form.phone" />
                </el-form-item>
                <el-form-item label="学院">
                  <el-input v-model="form.college" disabled />
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="saveInfo">保存修改</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>

            <el-tab-pane label="修改密码" name="password">
              <el-form ref="pwdFormRef" :model="pwdForm" :rules="pwdRules" label-width="100px" style="max-width: 400px">
                <el-form-item label="原密码" prop="oldPassword">
                  <el-input v-model="pwdForm.oldPassword" type="password" show-password />
                </el-form-item>
                <el-form-item label="新密码" prop="newPassword">
                  <el-input v-model="pwdForm.newPassword" type="password" show-password />
                </el-form-item>
                <el-form-item label="确认密码" prop="confirmPassword">
                  <el-input v-model="pwdForm.confirmPassword" type="password" show-password />
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="changePassword">修改密码</el-button>
                </el-form-item>
              </el-form>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { ElMessage, FormInstance, FormRules } from 'element-plus'

const activeTab = ref('basic')
const formRef = ref<FormInstance>()
const pwdFormRef = ref<FormInstance>()

const userInfo = ref({
  name: '张三',
  role: 'student',
  college: '计算机学院',
  avatar: ''
})

const form = reactive({
  username: 'zhangsan',
  name: '张三',
  email: 'zhangsan@example.com',
  phone: '13800138000',
  college: '计算机学院'
})

const pwdForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const rules: FormRules = {
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  email: [{ type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }]
}

const validateConfirm = (_rule: any, value: string, callback: any) => {
  if (value !== pwdForm.newPassword) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const pwdRules: FormRules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    { validator: validateConfirm, trigger: 'blur' }
  ]
}

const getRoleTagType = (role: string) => {
  const map: Record<string, string> = { student: 'primary', teacher: 'success', admin: 'danger' }
  return map[role] || ''
}

const getRoleName = (role: string) => {
  const map: Record<string, string> = { student: '学生', teacher: '教师', admin: '管理员' }
  return map[role] || role
}

const saveInfo = async () => {
  const valid = await formRef.value?.validate().catch(() => false)
  if (!valid) return
  ElMessage.success('保存成功')
}

const changePassword = async () => {
  const valid = await pwdFormRef.value?.validate().catch(() => false)
  if (!valid) return
  ElMessage.success('密码修改成功')
  pwdForm.oldPassword = ''
  pwdForm.newPassword = ''
  pwdForm.confirmPassword = ''
}
</script>

<style scoped>
.profile-page { padding: 0; }
.user-info { text-align: center; padding: 20px 0; }
.user-info h2 { margin: 16px 0 8px; }
.user-info .college { color: #909399; margin-top: 8px; }
</style>
