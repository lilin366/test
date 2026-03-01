<template>
  <div class="project-detail">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <el-button link @click="$router.back()">
            <el-icon><ArrowLeft /></el-icon>
            返回
          </el-button>
          <span>项目详情</span>
          <div>
            <el-button type="primary" @click="editProject">编辑</el-button>
          </div>
        </div>
      </template>

      <el-descriptions :column="2" border>
        <el-descriptions-item label="项目编号">{{ project.id }}</el-descriptions-item>
        <el-descriptions-item label="项目名称">{{ project.name }}</el-descriptions-item>
        <el-descriptions-item label="项目类型">
          <el-tag>{{ project.typeName }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="项目级别">
          <el-tag :type="project.levelType">{{ project.level }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="项目负责人">{{ project.leader }}</el-descriptions-item>
        <el-descriptions-item label="指导教师">{{ project.teacher }}</el-descriptions-item>
        <el-descriptions-item label="项目状态">
          <el-tag :type="project.statusType">{{ project.statusName }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="经费预算">{{ project.budget }} 元</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ project.createTime }}</el-descriptions-item>
        <el-descriptions-item label="更新时间">{{ project.updateTime }}</el-descriptions-item>
        <el-descriptions-item label="项目简介" :span="2">
          {{ project.description }}
        </el-descriptions-item>
        <el-descriptions-item label="研究计划" :span="2">
          {{ project.plan }}
        </el-descriptions-item>
        <el-descriptions-item label="预期成果" :span="2">
          {{ project.expectedResults }}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>

    <el-card shadow="never" style="margin-top: 20px">
      <template #header>
        <span>项目成员</span>
      </template>
      <el-table :data="project.members" border>
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="studentId" label="学号" width="150" />
        <el-table-column prop="role" label="角色" width="120">
          <template #default="{ row }">
            <el-tag :type="row.role === '负责人' ? 'primary' : ''">{{ row.role }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="contribution" label="分工" />
      </el-table>
    </el-card>

    <el-card shadow="never" style="margin-top: 20px">
      <template #header>
        <span>项目进度</span>
      </template>
      <el-timeline>
        <el-timeline-item
          v-for="item in project.progress"
          :key="item.id"
          :timestamp="item.time"
          placement="top"
        >
          <el-card>
            <h4>{{ item.title }}</h4>
            <p>{{ item.content }}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </el-card>

    <el-card shadow="never" style="margin-top: 20px">
      <template #header>
        <span>附件材料</span>
      </template>
      <el-table :data="project.attachments" border>
        <el-table-column prop="name" label="文件名" />
        <el-table-column prop="size" label="大小" width="120" />
        <el-table-column prop="uploadTime" label="上传时间" width="180" />
        <el-table-column label="操作" width="120">
          <template #default>
            <el-button type="primary" link>下载</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const project = ref({
  id: 'P2024001',
  name: '智能停车场管理系统',
  typeName: '创新训练',
  level: '省级',
  levelType: 'warning',
  leader: '张三',
  teacher: '李教授',
  statusName: '进行中',
  statusType: 'primary',
  budget: 10000,
  createTime: '2024-01-15 10:30:00',
  updateTime: '2024-02-20 14:20:00',
  description: '本项目旨在开发一套智能停车场管理系统，通过物联网技术实现车位的实时监控和智能调度，提高停车场运营效率。',
  plan: '第一阶段（1-2月）：需求分析和系统设计\n第二阶段（3-4月）：核心功能开发\n第三阶段（5-6月）：测试和优化\n第四阶段（7月）：部署和验收',
  expectedResults: '1. 完整的停车场管理系统\n2. 发表学术论文1篇\n3. 申请软件著作权1项',
  members: [
    { name: '张三', studentId: '2021001001', role: '负责人', contribution: '系统架构设计、核心功能开发' },
    { name: '李四', studentId: '2021001002', role: '核心成员', contribution: '前端开发、UI设计' },
    { name: '王五', studentId: '2021001003', role: '成员', contribution: '测试和文档编写' }
  ],
  progress: [
    { id: 1, title: '项目启动', content: '完成项目立项，确定研究计划', time: '2024-01-15' },
    { id: 2, title: '需求分析完成', content: '完成用户需求调研和需求文档编写', time: '2024-02-01' },
    { id: 3, title: '系统设计完成', content: '完成系统架构设计和数据库设计', time: '2024-02-20' }
  ],
  attachments: [
    { name: '项目计划书.pdf', size: '2.5MB', uploadTime: '2024-01-15 10:30:00' },
    { name: '可行性报告.docx', size: '1.2MB', uploadTime: '2024-01-15 10:32:00' }
  ]
})

const editProject = () => {
  router.push(`/projects/${route.params.id}/edit`)
}

onMounted(() => {
  // TODO: 根据 route.params.id 加载项目详情
})
</script>

<style scoped>
.project-detail {
  padding: 0;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
