<template>
  <div class="projects-page">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>项目列表</span>
          <el-button type="primary" @click="goCreate">
            <el-icon><Plus /></el-icon>
            新建项目
          </el-button>
        </div>
      </template>
      
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="项目名称">
          <el-input v-model="searchForm.name" placeholder="请输入项目名称" clearable />
        </el-form-item>
        <el-form-item label="项目类型">
          <el-select v-model="searchForm.type" placeholder="全部类型" clearable>
            <el-option label="创新训练" value="innovation" />
            <el-option label="创业训练" value="entrepreneurship_training" />
            <el-option label="创业实践" value="entrepreneurship_practice" />
          </el-select>
        </el-form-item>
        <el-form-item label="项目状态">
          <el-select v-model="searchForm.status" placeholder="全部状态" clearable>
            <el-option label="待初审" value="pending" />
            <el-option label="初审通过" value="first_approved" />
            <el-option label="进行中" value="in_progress" />
            <el-option label="已结题" value="completed" />
            <el-option label="已驳回" value="rejected" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="projectList" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="项目编号" width="120" />
        <el-table-column prop="name" label="项目名称" min-width="200" />
        <el-table-column prop="type" label="项目类型" width="120">
          <template #default="{ row }">
            <el-tag :type="getTypeTagType(row.type)">{{ getTypeName(row.type) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="level" label="项目级别" width="100">
          <template #default="{ row }">
            <el-tag :type="getLevelTagType(row.level)" size="small">{{ row.level }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="leader" label="负责人" width="100" />
        <el-table-column prop="teacher" label="指导教师" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusTagType(row.status)">{{ getStatusName(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="120" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="viewDetail(row)">查看</el-button>
            <el-button type="primary" link @click="editProject(row)" v-if="canEdit(row)">编辑</el-button>
            <el-button type="danger" link @click="deleteProject(row)" v-if="canDelete(row)">删除</el-button>
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
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const loading = ref(false)

const searchForm = reactive({
  name: '',
  type: '',
  status: ''
})

const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

const projectList = ref([
  { id: 'P2024001', name: '智能停车场管理系统', type: 'innovation', level: '省级', leader: '张三', teacher: '李老师', status: 'in_progress', createTime: '2024-01-15' },
  { id: 'P2024002', name: '校园二手交易平台', type: 'entrepreneurship_training', level: '校级', leader: '李四', teacher: '王老师', status: 'first_approved', createTime: '2024-02-01' },
  { id: 'P2024003', name: '智能健康监测手环', type: 'innovation', level: '国家级', leader: '王五', teacher: '张老师', status: 'completed', createTime: '2023-09-10' },
  { id: 'P2024004', name: '大学生创业孵化器', type: 'entrepreneurship_practice', level: '省级', leader: '赵六', teacher: '刘老师', status: 'pending', createTime: '2024-03-01' },
])

const goCreate = () => {
  router.push('/projects/create')
}

const handleSearch = () => {
  // TODO: 实现搜索逻辑
  ElMessage.info('搜索功能待实现')
}

const resetSearch = () => {
  searchForm.name = ''
  searchForm.type = ''
  searchForm.status = ''
}

const viewDetail = (row: any) => {
  router.push(`/projects/${row.id}`)
}

const editProject = (row: any) => {
  router.push(`/projects/${row.id}/edit`)
}

const deleteProject = async (row: any) => {
  try {
    await ElMessageBox.confirm('确定要删除该项目吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    // TODO: 调用删除 API
    ElMessage.success('删除成功')
  } catch {
    // 取消删除
  }
}

const canEdit = (row: any) => {
  return row.status === 'pending' || row.status === 'rejected'
}

const canDelete = (row: any) => {
  return row.status === 'pending'
}

const getTypeName = (type: string) => {
  const map: Record<string, string> = {
    innovation: '创新训练',
    entrepreneurship_training: '创业训练',
    entrepreneurship_practice: '创业实践'
  }
  return map[type] || type
}

const getTypeTagType = (type: string) => {
  const map: Record<string, string> = {
    innovation: 'primary',
    entrepreneurship_training: 'success',
    entrepreneurship_practice: 'warning'
  }
  return map[type] || ''
}

const getLevelTagType = (level: string) => {
  const map: Record<string, string> = {
    '国家级': 'danger',
    '省级': 'warning',
    '校级': ''
  }
  return map[level] || ''
}

const getStatusName = (status: string) => {
  const map: Record<string, string> = {
    pending: '待初审',
    first_approved: '初审通过',
    in_progress: '进行中',
    completed: '已结题',
    rejected: '已驳回'
  }
  return map[status] || status
}

const getStatusTagType = (status: string) => {
  const map: Record<string, string> = {
    pending: 'info',
    first_approved: 'success',
    in_progress: 'primary',
    completed: '',
    rejected: 'danger'
  }
  return map[status] || ''
}

const handleSizeChange = (val: number) => {
  pagination.pageSize = val
  // TODO: 重新加载数据
}

const handleCurrentChange = (val: number) => {
  pagination.page = val
  // TODO: 重新加载数据
}

onMounted(() => {
  // TODO: 加载项目列表
  pagination.total = projectList.value.length
})
</script>

<style scoped>
.projects-page {
  padding: 0;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-form {
  margin-bottom: 20px;
}
</style>
