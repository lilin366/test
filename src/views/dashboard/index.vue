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
            <el-icon :size="28"><Clock /></el-icon>
          </div>
          <div class="stat-info">
            <p class="stat-value">{{ stats.inProgressProjects }}</p>
            <p class="stat-label">进行中</p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-icon" style="background: #f56c6c">
            <el-icon :size="28"><User /></el-icon>
          </div>
          <div class="stat-info">
            <p class="stat-value">{{ stats.studentCount }}</p>
            <p class="stat-label">参与学生</p>
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
          <div ref="chartRef" style="height: 300px" v-loading="chartLoading"></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>项目类型分布</span>
            </div>
          </template>
          <div ref="pieChartRef" style="height: 300px" v-loading="chartLoading"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>待处理事项</span>
              <el-button type="primary" link @click="$router.push('/reviews')">查看全部</el-button>
            </div>
          </template>
          <el-table :data="pendingTasks" style="width: 100%" v-loading="tasksLoading">
            <el-table-column prop="title" label="事项" />
            <el-table-column prop="type" label="类型" width="100">
              <template #default="{ row }">
                <el-tag :type="getTaskTagType(row.type)" size="small">{{ row.type }}</el-tag>
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
              <el-button type="primary" link @click="$router.push('/achievements')">查看全部</el-button>
            </div>
          </template>
          <el-table :data="recentAchievements" style="width: 100%" v-loading="achievementsLoading">
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
import { ref, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'
import { getStatisticsOverview, getStatisticsData, getMyReviews, getAchievementList } from '@/api'
import type { ECharts } from 'echarts'

const chartRef = ref<HTMLElement>()
const pieChartRef = ref<HTMLElement>()
let lineChart: ECharts | null = null
let pieChart: ECharts | null = null

const chartLoading = ref(false)
const tasksLoading = ref(false)
const achievementsLoading = ref(false)

const stats = ref({
  totalProjects: 0,
  completedProjects: 0,
  inProgressProjects: 0,
  studentCount: 0
})

const pendingTasks = ref<Array<{ title: string; type: string; date: string }>>([])
const recentAchievements = ref<Array<{ name: string; type: string; student: string }>>([])

const getTaskTagType = (type: string) => {
  const map: Record<string, string> = { 评审: 'warning', 检查: 'primary', 验收: 'success', 审核: 'info' }
  return map[type] || ''
}

const fetchData = async () => {
  chartLoading.value = true
  try {
    const [overviewRes, dataRes] = await Promise.all([
      getStatisticsOverview(),
      getStatisticsData()
    ])
    
    if (overviewRes.data) {
      stats.value = {
        totalProjects: overviewRes.data.totalProjects,
        completedProjects: overviewRes.data.completedProjects,
        inProgressProjects: overviewRes.data.inProgressProjects,
        studentCount: overviewRes.data.studentCount
      }
    }
    
    if (dataRes.data) {
      initLineChart(dataRes.data.projectTrend)
      initPieChart(dataRes.data.projectTypeDistribution)
    }
  } catch (error) {
    stats.value = { totalProjects: 128, completedProjects: 86, inProgressProjects: 42, studentCount: 342 }
    initLineChart()
    initPieChart()
  } finally {
    chartLoading.value = false
  }
}

const fetchPendingTasks = async () => {
  tasksLoading.value = true
  try {
    const res = await getMyReviews()
    if (res.data) {
      pendingTasks.value = res.data.slice(0, 4).map(r => ({
        title: r.projectName || '项目评审',
        type: '评审',
        date: r.createdAt?.split('T')[0] || ''
      }))
    }
  } catch {
    pendingTasks.value = [
      { title: '项目A-初审评审', type: '评审', date: '2024-03-01' },
      { title: '项目B-中期检查', type: '检查', date: '2024-03-02' }
    ]
  } finally {
    tasksLoading.value = false
  }
}

const fetchRecentAchievements = async () => {
  achievementsLoading.value = true
  try {
    const res = await getAchievementList({ size: 4 })
    if (res.data) {
      recentAchievements.value = res.data.list.map(a => ({
        name: a.name,
        type: getAchievementTypeName(a.type),
        student: ''
      }))
    }
  } catch {
    recentAchievements.value = [
      { name: '智能停车场系统', type: '软件著作权', student: '张三' },
      { name: '新型传感器专利', type: '发明专利', student: '李四' }
    ]
  } finally {
    achievementsLoading.value = false
  }
}

const getAchievementTypeName = (type: string) => {
  const map: Record<string, string> = {
    paper: '学术论文', patent: '发明专利', software_copyright: '软件著作权',
    competition: '竞赛获奖', practice: '创业实践', other: '其他'
  }
  return map[type] || type
}

const initLineChart = (data?: Array<{ month: string; submitted: number; completed: number }>) => {
  if (!chartRef.value) return
  if (!lineChart) lineChart = echarts.init(chartRef.value)
  
  const months = ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
  const submitted = data?.map(d => d.submitted) || [12, 15, 18, 22, 28, 35, 30, 25, 20, 18, 15, 10]
  const completed = data?.map(d => d.completed) || [8, 10, 12, 15, 18, 22, 20, 18, 15, 12, 10, 8]
  
  lineChart.setOption({
    tooltip: { trigger: 'axis' },
    legend: { data: ['申报项目', '结题项目'] },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: { type: 'category', boundaryGap: false, data: months },
    yAxis: { type: 'value' },
    series: [
      { name: '申报项目', type: 'line', smooth: true, data: submitted, itemStyle: { color: '#409eff' } },
      { name: '结题项目', type: 'line', smooth: true, data: completed, itemStyle: { color: '#67c23a' } }
    ]
  })
}

const initPieChart = (data?: Array<{ name: string; value: number }>) => {
  if (!pieChartRef.value) return
  if (!pieChart) pieChart = echarts.init(pieChartRef.value)
  
  const chartData = data || [
    { value: 45, name: '创新训练' },
    { value: 38, name: '创业训练' },
    { value: 25, name: '创业实践' }
  ]
  
  pieChart.setOption({
    tooltip: { trigger: 'item' },
    legend: { orient: 'vertical', left: 'left' },
    series: [{
      name: '项目类型',
      type: 'pie',
      radius: '50%',
      data: chartData,
      emphasis: { itemStyle: { shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgba(0, 0, 0, 0.5)' } }
    }]
  })
}

const handleResize = () => {
  lineChart?.resize()
  pieChart?.resize()
}

onMounted(() => {
  fetchData()
  fetchPendingTasks()
  fetchRecentAchievements()
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
  lineChart?.dispose()
  pieChart?.dispose()
})
</script>

<style scoped>
.dashboard { padding: 0; }
.stat-card { display: flex; align-items: center; }
.stat-card :deep(.el-card__body) { display: flex; align-items: center; width: 100%; padding: 20px; }
.stat-icon { width: 56px; height: 56px; border-radius: 8px; display: flex; align-items: center; justify-content: center; color: #fff; }
.stat-info { margin-left: 16px; }
.stat-value { font-size: 24px; font-weight: 600; color: #303133; margin: 0; }
.stat-label { font-size: 14px; color: #909399; margin: 4px 0 0; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
</style>
