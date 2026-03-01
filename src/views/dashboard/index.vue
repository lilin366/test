<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon" style="background: #409eff">
            <el-icon :size="28"><Document /></el-icon>
          </div>
          <div class="stat-info">
            <p class="stat-value">{{ stats.totalProjects }}</p>
            <p class="stat-label">项目总数</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon" style="background: #67c23a">
            <el-icon :size="28"><Trophy /></el-icon>
          </div>
          <div class="stat-info">
            <p class="stat-value">{{ stats.completedProjects }}</p>
            <p class="stat-label">已结题</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon" style="background: #e6a23c">
            <el-icon :size="28"><User /></el-icon>
          </div>
          <div class="stat-info">
            <p class="stat-value">{{ stats.totalUsers }}</p>
            <p class="stat-label">参与学生</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon" style="background: #f56c6c">
            <el-icon :size="28"><Medal /></el-icon>
          </div>
          <div class="stat-info">
            <p class="stat-value">{{ stats.totalCredits }}</p>
            <p class="stat-label">认定学分</p>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="16">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>项目趋势</span>
            </div>
          </template>
          <div ref="chartRef" style="height: 300px"></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>项目类型分布</span>
            </div>
          </template>
          <div ref="pieChartRef" style="height: 300px"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>待处理事项</span>
              <el-button type="primary" link>查看全部</el-button>
            </div>
          </template>
          <el-table :data="pendingTasks" style="width: 100%">
            <el-table-column prop="title" label="事项" />
            <el-table-column prop="type" label="类型" width="100">
              <template #default="{ row }">
                <el-tag :type="row.type === '评审' ? 'warning' : 'primary'" size="small">
                  {{ row.type }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="date" label="日期" width="120" />
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>最新成果</span>
              <el-button type="primary" link>查看全部</el-button>
            </div>
          </template>
          <el-table :data="recentAchievements" style="width: 100%">
            <el-table-column prop="name" label="成果名称" />
            <el-table-column prop="type" label="类型" width="120">
              <template #default="{ row }">
                <el-tag size="small">{{ row.type }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="student" label="学生" width="100" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'

const chartRef = ref<HTMLElement>()
const pieChartRef = ref<HTMLElement>()

const stats = ref({
  totalProjects: 128,
  completedProjects: 86,
  totalUsers: 342,
  totalCredits: 1560
})

const pendingTasks = ref([
  { title: '项目A-初审评审', type: '评审', date: '2024-03-01' },
  { title: '项目B-中期检查', type: '检查', date: '2024-03-02' },
  { title: '项目C-结题验收', type: '验收', date: '2024-03-03' },
  { title: '项目D-成果审核', type: '审核', date: '2024-03-04' }
])

const recentAchievements = ref([
  { name: '智能停车场系统', type: '软件著作权', student: '张三' },
  { name: '新型传感器专利', type: '发明专利', student: '李四' },
  { name: '创新创业大赛', type: '竞赛获奖', student: '王五' },
  { name: '学术论文发表', type: '学术论文', student: '赵六' }
])

onMounted(() => {
  initLineChart()
  initPieChart()
})

const initLineChart = () => {
  if (!chartRef.value) return
  const chart = echarts.init(chartRef.value)
  
  const option = {
    tooltip: { trigger: 'axis' },
    legend: { data: ['申报项目', '结题项目'] },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
    },
    yAxis: { type: 'value' },
    series: [
      { name: '申报项目', type: 'line', smooth: true, data: [12, 15, 18, 22, 28, 35, 30, 25, 20, 18, 15, 10] },
      { name: '结题项目', type: 'line', smooth: true, data: [8, 10, 12, 15, 18, 22, 20, 18, 15, 12, 10, 8] }
    ]
  }
  
  chart.setOption(option)
}

const initPieChart = () => {
  if (!pieChartRef.value) return
  const chart = echarts.init(pieChartRef.value)
  
  const option = {
    tooltip: { trigger: 'item' },
    legend: { orient: 'vertical', left: 'left' },
    series: [
      {
        name: '项目类型',
        type: 'pie',
        radius: '50%',
        data: [
          { value: 45, name: '创新训练' },
          { value: 38, name: '创业训练' },
          { value: 25, name: '创业实践' }
        ],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  }
  
  chart.setOption(option)
}
</script>

<style scoped>
.dashboard {
  padding: 0;
}

.stat-card {
  display: flex;
  align-items: center;
}

.stat-card :deep(.el-card__body) {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 20px;
}

.stat-icon {
  width: 56px;
  height: 56px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.stat-info {
  margin-left: 16px;
}

.stat-value {
  font-size: 24px;
  font-weight: 600;
  color: #303133;
  margin: 0;
}

.stat-label {
  font-size: 14px;
  color: #909399;
  margin: 4px 0 0;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
