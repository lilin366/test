<template>
  <div class="credits-page">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>学分认定</span>
          <el-button type="primary" @click="showRuleDialog" v-if="userRole === 'admin'">
            <el-icon><Setting /></el-icon>
            学分规则配置
          </el-button>
        </div>
      </template>

      <!-- 学生视图：我的学分 -->
      <div v-if="userRole === 'student'">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-statistic title="累计获得学分" :value="creditInfo.total" suffix="学分" />
          </el-col>
          <el-col :span="8">
            <el-statistic title="本学期获得" :value="creditInfo.thisSemester" suffix="学分" />
          </el-col>
          <el-col :span="8">
            <el-statistic title="排名" :value="creditInfo.rank" suffix="名" />
          </el-col>
        </el-row>

        <el-divider>学分明细</el-divider>

        <el-table :data="creditList" style="width: 100%">
          <el-table-column prop="project" label="项目名称" min-width="200" />
          <el-table-column prop="type" label="学分类型" width="120" />
          <el-table-column prop="credit" label="获得学分" width="100" />
          <el-table-column prop="time" label="认定时间" width="160" />
          <el-table-column label="操作" width="100">
            <template #default>
              <el-button type="primary" link>详情</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <!-- 管理员视图：学分列表 -->
      <div v-else>
        <el-form :inline="true" :model="searchForm" class="search-form">
          <el-form-item label="学生姓名">
            <el-input v-model="searchForm.name" placeholder="请输入学生姓名" clearable />
          </el-form-item>
          <el-form-item label="学院">
            <el-select v-model="searchForm.college" placeholder="全部学院" clearable>
              <el-option label="计算机学院" value="cs" />
              <el-option label="软件学院" value="se" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">查询</el-button>
            <el-button @click="resetSearch">重置</el-button>
          </el-form-item>
        </el-form>

        <el-table :data="studentCreditList" style="width: 100%">
          <el-table-column prop="studentId" label="学号" width="120" />
          <el-table-column prop="name" label="姓名" width="100" />
          <el-table-column prop="college" label="学院" min-width="150" />
          <el-table-column prop="major" label="专业" width="120" />
          <el-table-column prop="totalCredit" label="累计学分" width="100" />
          <el-table-column prop="projectCount" label="参与项目" width="100" />
          <el-table-column label="操作" width="150">
            <template #default="{ row }">
              <el-button type="primary" link @click="viewDetail(row)">查看明细</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>

    <!-- 学分规则配置对话框 -->
    <el-dialog v-model="ruleDialogVisible" title="学分规则配置" width="600px">
      <el-table :data="creditRules" border>
        <el-table-column prop="level" label="项目级别" width="120" />
        <el-table-column prop="baseCredit" label="基础学分" width="100" />
        <el-table-column prop="leaderRatio" label="负责人系数" width="120" />
        <el-table-column prop="memberRatio" label="成员系数" width="100" />
        <el-table-column label="操作" width="80">
          <template #default>
            <el-button type="primary" link>编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'

const userRole = ref('student') // 可以切换 'student' | 'admin'
const ruleDialogVisible = ref(false)

const creditInfo = ref({
  total: 6.5,
  thisSemester: 2.5,
  rank: 15
})

const creditList = ref([
  { project: '智能停车场管理系统', type: '项目学分', credit: 2.0, time: '2024-03-15 10:00:00' },
  { project: '学术论文发表', type: '成果学分', credit: 1.5, time: '2024-02-20 14:30:00' },
  { project: '软件著作权', type: '成果学分', credit: 1.0, time: '2024-03-01 09:00:00' },
  { project: '创新创业大赛', type: '竞赛学分', credit: 2.0, time: '2024-01-15 16:00:00' }
])

const searchForm = reactive({ name: '', college: '' })

const studentCreditList = ref([
  { studentId: '2021001001', name: '张三', college: '计算机学院', major: '软件工程', totalCredit: 6.5, projectCount: 3 },
  { studentId: '2021001002', name: '李四', college: '计算机学院', major: '计算机科学', totalCredit: 4.0, projectCount: 2 },
  { studentId: '2021001003', name: '王五', college: '软件学院', major: '软件工程', totalCredit: 5.5, projectCount: 2 }
])

const creditRules = ref([
  { level: '国家级', baseCredit: 4.0, leaderRatio: 1.0, memberRatio: 0.8 },
  { level: '省级', baseCredit: 2.5, leaderRatio: 1.0, memberRatio: 0.7 },
  { level: '校级', baseCredit: 1.5, leaderRatio: 1.0, memberRatio: 0.6 }
])

const showRuleDialog = () => ruleDialogVisible.value = true
const handleSearch = () => ElMessage.info('搜索功能待实现')
const resetSearch = () => { searchForm.name = ''; searchForm.college = '' }
const viewDetail = (row: any) => ElMessage.info(`查看 ${row.name} 的学分明细`)
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 20px; }
</style>
