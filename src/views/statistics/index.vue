<template>
  <div class="statistics-page">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <el-statistic title="项目总数" :value="stats.totalProjects" />
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <el-statistic title="参与学生" :value="stats.totalStudents" />
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <el-statistic title="成果数量" :value="stats.totalAchievements" />
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <el-statistic title="认定学分" :value="stats.totalCredits" />
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <span>学院项目分布</span>
          </template>
          <div ref="collegeChartRef" style="height: 300px"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <span>项目状态统计</span>
          </template>
          <div ref="statusChartRef" style="height: 300px"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="16">
        <el-card shadow="hover">
          <template #header>
            <span>项目趋势分析</span>
          </template>
          <div ref="trendChartRef" style="height: 350px"></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <span>成果类型分布</span>
          </template>
          <div ref="achievementChartRef" style="height: 350px"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="24">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>学院数据排名</span>
              <el-button type="primary" link>导出报表</el-button>
            </div>
          </template>
          <el-table :data="collegeRanking" border>
            <el-table-column type="index" label="排名" width="60" />
            <el-table-column prop="college" label="学院" />
            <el-table-column prop="projectCount" label="项目数量" width="100" />
            <el-table-column prop="studentCount" label="参与学生" width="100" />
            <el-table-column prop="achievementCount" label="成果数量" width="100" />
            <el-table-column prop="avgCredit" label="平均学分" width="100" />
            <el-table-column prop="completionRate" label="结题率" width="100">
              <template #default="{ row }">
                <el-progress :percentage="row.completionRate" :stroke-width="8" />
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'

const collegeChartRef = ref<HTMLElement>()
const statusChartRef = ref<HTMLElement>()
const trendChartRef = ref<HTMLElement>()
const achievementChartRef = ref<HTMLElement>()

const stats = ref({
  totalProjects: 128,
  totalStudents: 342,
  totalAchievements: 86,
  totalCredits: 1560
})

const collegeRanking = ref([
  { college: '计算机学院', projectCount: 45, studentCount: 120, achievementCount: 28, avgCredit: 3.5, completionRate: 78 },
  { college: '软件学院', projectCount: 32, studentCount: 85, achievementCount: 20, avgCredit: 3.2, completionRate: 72 },
  { college: '电子工程学院', projectCount: 28, studentCount: 70, achievementCount: 18, avgCredit: 2.8, completionRate: 68 },
  { college: '机械工程学院', projectCount: 15, studentCount: 42, achievementCount: 12, avgCredit: 2.5, completionRate: 65 },
  { college: '经济管理学院', projectCount: 8, studentCount: 25, achievementCount: 8, avgCredit: 2.2, completionRate: 60 }
])

onMounted(() => {
  initCollegeChart()
  initStatusChart()
  initTrendChart()
  initAchievementChart()
})

const initCollegeChart = () => {
  if (!collegeChartRef.value) return
  const chart = echarts.init(collegeChartRef.value)
  const option = {
    tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
    xAxis: { type: 'category', data: ['计算机', '软件', '电子', '机械', '经管'] },
    yAxis: { type: 'value' },
    series: [{ data: [45, 32, 28, 15, 8], type: 'bar', itemStyle: { color: '#409eff' } }]
  }
  chart.setOption(option)
}

const initStatusChart = () => {
  if (!statusChartRef.value) return
  const chart = echarts.init(statusChartRef.value)
  const option = {
    tooltip: { trigger: 'item' },
    legend: { orient: 'vertical', left: 'left' },
    series: [{
      type: 'pie',
      radius: '50%',
      data: [
        { value: 20, name: '待初审' },
        { value: 35, name: '进行中' },
        { value: 60, name: '已结题' },
        { value: 13, name: '已驳回' }
      ]
    }]
  }
  chart.setOption(option)
}

const initTrendChart = () => {
  if (!trendChartRef.value) return
  const chart = echarts.init(trendChartRef.value)
  const option = {
    tooltip: { trigger: 'axis' },
    legend: { data: ['申报项目', '结题项目', '成果数量'] },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: { type: 'category', boundaryGap: false, data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'] },
    yAxis: { type: 'value' },
    series: [
      { name: '申报项目', type: 'line', smooth: true, data: [12, 15, 18, 22, 28, 35, 30, 25, 20, 18, 15, 10] },
      { name: '结题项目', type: 'line', smooth: true, data: [8, 10, 12, 15, 18, 22, 20, 18, 15, 12, 10, 8] },
      { name: '成果数量', type: 'line', smooth: true, data: [5, 7, 9, 12, 15, 18, 16, 14, 12, 10, 8, 6] }
    ]
  }
  chart.setOption(option)
}

const initAchievementChart = () => {
  if (!achievementChartRef.value) return
  const chart = echarts.init(achievementChartRef.value)
  const option = {
    tooltip: { trigger: 'item' },
    series: [{
      type: 'pie',
      radius: ['40%', '70%'],
      data: [
        { value: 30, name: '学术论文' },
        { value: 25, name: '软件著作权' },
        { value: 15, name: '发明专利' },
        { value: 16, name: '竞赛获奖' }
      ],
      label: { show: true, formatter: '{b}: {c}' }
    }]
  }
  chart.setOption(option)
}
</script>

<style scoped>
.statistics-page { padding: 0; }
.stat-card { text-align: center; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
</style>
