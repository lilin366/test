<template>
  <div class="reviews-page">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>评审管理</span>
        </div>
      </template>

      <el-tabs v-model="activeTab">
        <el-tab-pane label="待评审项目" name="pending">
          <el-table :data="pendingList" style="width: 100%">
            <el-table-column prop="id" label="项目编号" width="120" />
            <el-table-column prop="name" label="项目名称" min-width="200" />
            <el-table-column prop="type" label="类型" width="100" />
            <el-table-column prop="leader" label="负责人" width="100" />
            <el-table-column prop="submitTime" label="提交时间" width="160" />
            <el-table-column label="操作" width="150" fixed="right">
              <template #default="{ row }">
                <el-button type="primary" link @click="startReview(row)">开始评审</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>

        <el-tab-pane label="已评审项目" name="completed">
          <el-table :data="completedList" style="width: 100%">
            <el-table-column prop="id" label="项目编号" width="120" />
            <el-table-column prop="name" label="项目名称" min-width="200" />
            <el-table-column prop="result" label="评审结果" width="100">
              <template #default="{ row }">
                <el-tag :type="row.result === '通过' ? 'success' : 'danger'">{{ row.result }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="score" label="评分" width="80" />
            <el-table-column prop="reviewTime" label="评审时间" width="160" />
            <el-table-column label="操作" width="100" fixed="right">
              <template #default="{ row }">
                <el-button type="primary" link @click="viewReview(row)">查看</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-card>

    <!-- 评审对话框 -->
    <el-dialog v-model="reviewDialogVisible" title="项目评审" width="700px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="项目名称">{{ currentProject.name }}</el-descriptions-item>
        <el-descriptions-item label="项目类型">{{ currentProject.type }}</el-descriptions-item>
        <el-descriptions-item label="负责人">{{ currentProject.leader }}</el-descriptions-item>
        <el-descriptions-item label="指导教师">{{ currentProject.teacher }}</el-descriptions-item>
        <el-descriptions-item label="项目简介" :span="2">{{ currentProject.description }}</el-descriptions-item>
      </el-descriptions>

      <el-divider>评审意见</el-divider>

      <el-form ref="reviewFormRef" :model="reviewForm" :rules="reviewRules" label-width="80px">
        <el-form-item label="评分" prop="score">
          <el-rate v-model="reviewForm.score" :max="100" show-score />
        </el-form-item>
        <el-form-item label="评审结果" prop="result">
          <el-radio-group v-model="reviewForm.result">
            <el-radio value="pass">通过</el-radio>
            <el-radio value="modify">修改后通过</el-radio>
            <el-radio value="reject">不通过</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="评审意见" prop="comment">
          <el-input v-model="reviewForm.comment" type="textarea" :rows="4" placeholder="请输入评审意见" />
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="reviewDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitReview">提交评审</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { ElMessage, FormInstance, FormRules } from 'element-plus'

const activeTab = ref('pending')
const reviewDialogVisible = ref(false)
const reviewFormRef = ref<FormInstance>()

const pendingList = ref([
  { id: 'P2024004', name: '大学生创业孵化器', type: '创业实践', leader: '赵六', submitTime: '2024-03-01 10:00:00' },
  { id: 'P2024005', name: '校园二手交易平台', type: '创业训练', leader: '孙七', submitTime: '2024-03-02 14:30:00' }
])

const completedList = ref([
  { id: 'P2024001', name: '智能停车场管理系统', result: '通过', score: 92, reviewTime: '2024-02-15 16:00:00' },
  { id: 'P2024002', name: '校园二手交易平台', result: '不通过', score: 65, reviewTime: '2024-02-20 10:00:00' }
])

const currentProject = ref<any>({})
const reviewForm = reactive({
  score: 80,
  result: 'pass',
  comment: ''
})

const reviewRules: FormRules = {
  score: [{ required: true, message: '请评分', trigger: 'change' }],
  result: [{ required: true, message: '请选择评审结果', trigger: 'change' }],
  comment: [{ required: true, message: '请输入评审意见', trigger: 'blur' }]
}

const startReview = (row: any) => {
  currentProject.value = { ...row, teacher: '李教授', description: '这是项目简介...' }
  reviewDialogVisible.value = true
}

const viewReview = (row: any) => {
  ElMessage.info('查看评审详情')
}

const submitReview = async () => {
  const valid = await reviewFormRef.value?.validate().catch(() => false)
  if (!valid) return
  ElMessage.success('评审提交成功')
  reviewDialogVisible.value = false
}
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
