<template>
  <div class="achievements-page">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>成果管理</span>
          <el-button type="primary" @click="showAddDialog">
            <el-icon><Plus /></el-icon>
            录入成果
          </el-button>
        </div>
      </template>

      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="成果类型">
          <el-select v-model="searchForm.type" placeholder="全部类型" clearable>
            <el-option label="学术论文" value="paper" />
            <el-option label="发明专利" value="patent" />
            <el-option label="软件著作权" value="software" />
            <el-option label="竞赛获奖" value="competition" />
          </el-select>
        </el-form-item>
        <el-form-item label="审核状态">
          <el-select v-model="searchForm.status" placeholder="全部状态" clearable>
            <el-option label="待审核" value="pending" />
            <el-option label="已认证" value="approved" />
            <el-option label="已驳回" value="rejected" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="achievementList" style="width: 100%">
        <el-table-column prop="id" label="成果编号" width="120" />
        <el-table-column prop="name" label="成果名称" min-width="200" />
        <el-table-column prop="type" label="类型" width="120">
          <template #default="{ row }">
            <el-tag>{{ row.type }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="project" label="关联项目" width="150" />
        <el-table-column prop="student" label="学生" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="time" label="获得时间" width="120" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="viewDetail(row)">查看</el-button>
            <el-button type="success" link @click="auditAchievement(row)" v-if="row.status === '待审核'">审核</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加成果对话框 -->
    <el-dialog v-model="dialogVisible" title="录入成果" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="成果名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入成果名称" />
        </el-form-item>
        <el-form-item label="成果类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型" style="width: 100%">
            <el-option label="学术论文" value="paper" />
            <el-option label="发明专利" value="patent" />
            <el-option label="软件著作权" value="software" />
            <el-option label="竞赛获奖" value="competition" />
          </el-select>
        </el-form-item>
        <el-form-item label="关联项目" prop="projectId">
          <el-select v-model="form.projectId" placeholder="请选择关联项目" style="width: 100%">
            <el-option label="智能停车场管理系统" value="P2024001" />
            <el-option label="校园二手交易平台" value="P2024002" />
          </el-select>
        </el-form-item>
        <el-form-item label="获得时间" prop="time">
          <el-date-picker v-model="form.time" type="date" placeholder="选择日期" style="width: 100%" />
        </el-form-item>
        <el-form-item label="成果描述" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="3" placeholder="请描述成果详情" />
        </el-form-item>
        <el-form-item label="证明材料">
          <el-upload action="#" :auto-upload="false">
            <el-button type="primary">上传文件</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { ElMessage, FormInstance, FormRules } from 'element-plus'

const dialogVisible = ref(false)
const formRef = ref<FormInstance>()

const searchForm = reactive({ type: '', status: '' })

const achievementList = ref([
  { id: 'A2024001', name: '智能停车场管理系统的设计与实现', type: '学术论文', project: '智能停车场管理系统', student: '张三', status: '已认证', time: '2024-02-15' },
  { id: 'A2024002', name: '停车场智能调度系统V1.0', type: '软件著作权', project: '智能停车场管理系统', student: '张三', status: '已认证', time: '2024-03-01' },
  { id: 'A2024003', name: '互联网+创新创业大赛银奖', type: '竞赛获奖', project: '校园二手交易平台', student: '李四', status: '待审核', time: '2024-03-10' }
])

const form = reactive({
  name: '',
  type: '',
  projectId: '',
  time: '',
  description: ''
})

const rules: FormRules = {
  name: [{ required: true, message: '请输入成果名称', trigger: 'blur' }],
  type: [{ required: true, message: '请选择成果类型', trigger: 'change' }],
  projectId: [{ required: true, message: '请选择关联项目', trigger: 'change' }]
}

const getStatusType = (status: string) => {
  const map: Record<string, string> = { '已认证': 'success', '待审核': 'warning', '已驳回': 'danger' }
  return map[status] || ''
}

const handleSearch = () => ElMessage.info('搜索功能待实现')
const resetSearch = () => { searchForm.type = ''; searchForm.status = '' }
const showAddDialog = () => dialogVisible.value = true
const viewDetail = (row: any) => ElMessage.info(`查看成果: ${row.name}`)
const auditAchievement = (row: any) => ElMessage.info(`审核成果: ${row.name}`)

const handleSubmit = async () => {
  const valid = await formRef.value?.validate().catch(() => false)
  if (!valid) return
  ElMessage.success('成果录入成功')
  dialogVisible.value = false
}
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 20px; }
</style>
