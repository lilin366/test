<template>
  <div class="project-create">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <el-button link @click="$router.back()">
            <el-icon><ArrowLeft /></el-icon>
            返回
          </el-button>
          <span>项目申报</span>
          <div></div>
        </div>
      </template>

      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="120px"
        style="max-width: 800px"
      >
        <el-form-item label="项目名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入项目名称" maxlength="100" show-word-limit />
        </el-form-item>

        <el-form-item label="项目类型" prop="type">
          <el-radio-group v-model="form.type">
            <el-radio value="innovation">创新训练</el-radio>
            <el-radio value="entrepreneurship_training">创业训练</el-radio>
            <el-radio value="entrepreneurship_practice">创业实践</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="项目级别" prop="level">
          <el-select v-model="form.level" placeholder="请选择项目级别" style="width: 100%">
            <el-option label="国家级" value="national" />
            <el-option label="省级" value="provincial" />
            <el-option label="校级" value="school" />
          </el-select>
        </el-form-item>

        <el-form-item label="项目简介" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            :rows="4"
            placeholder="请简要描述项目背景、目的和意义"
            maxlength="500"
            show-word-limit
          />
        </el-form-item>

        <el-form-item label="研究计划" prop="plan">
          <el-input
            v-model="form.plan"
            type="textarea"
            :rows="6"
            placeholder="请详细描述研究计划、时间安排和阶段目标"
            maxlength="2000"
            show-word-limit
          />
        </el-form-item>

        <el-form-item label="预期成果" prop="expectedResults">
          <el-input
            v-model="form.expectedResults"
            type="textarea"
            :rows="4"
            placeholder="请描述预期成果，如论文、专利、软件著作权等"
            maxlength="1000"
            show-word-limit
          />
        </el-form-item>

        <el-form-item label="经费预算" prop="budget">
          <el-input-number v-model="form.budget" :min="0" :precision="2" style="width: 200px" />
          <span style="margin-left: 10px; color: #909399">元</span>
        </el-form-item>

        <el-form-item label="项目成员" prop="members">
          <el-button type="primary" plain @click="addMember">
            <el-icon><Plus /></el-icon>
            添加成员
          </el-button>
          <el-table :data="form.members" style="width: 100%; margin-top: 10px" v-if="form.members.length">
            <el-table-column prop="name" label="姓名" width="120">
              <template #default="{ row }">
                <el-input v-model="row.name" placeholder="姓名" />
              </template>
            </el-table-column>
            <el-table-column prop="studentId" label="学号" width="150">
              <template #default="{ row }">
                <el-input v-model="row.studentId" placeholder="学号" />
              </template>
            </el-table-column>
            <el-table-column prop="role" label="角色" width="150">
              <template #default="{ row }">
                <el-select v-model="row.role" placeholder="选择角色">
                  <el-option label="负责人" value="leader" />
                  <el-option label="核心成员" value="core" />
                  <el-option label="普通成员" value="member" />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column prop="contribution" label="分工">
              <template #default="{ row }">
                <el-input v-model="row.contribution" placeholder="负责内容" />
              </template>
            </el-table-column>
            <el-table-column label="操作" width="80">
              <template #default="{ $index }">
                <el-button type="danger" link @click="removeMember($index)">移除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>

        <el-form-item label="指导教师" prop="teacherId">
          <el-select v-model="form.teacherId" placeholder="请选择指导教师" style="width: 100%">
            <el-option v-for="t in teacherList" :key="t.id" :label="t.name" :value="t.id" />
          </el-select>
        </el-form-item>

        <el-form-item label="附件材料" prop="attachments">
          <el-upload
            class="upload-area"
            drag
            action="#"
            :auto-upload="false"
            :on-change="handleFileChange"
            multiple
          >
            <el-icon class="el-icon--upload"><UploadFilled /></el-icon>
            <div class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
            <template #tip>
              <div class="el-upload__tip">
                支持上传项目计划书、可行性报告等材料，单个文件不超过10MB
              </div>
            </template>
          </el-upload>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">提交申报</el-button>
          <el-button @click="handleSaveDraft">保存草稿</el-button>
          <el-button @click="$router.back()">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, FormInstance, FormRules, UploadFile } from 'element-plus'

const router = useRouter()
const formRef = ref<FormInstance>()
const loading = ref(false)

const form = reactive({
  name: '',
  type: 'innovation',
  level: '',
  description: '',
  plan: '',
  expectedResults: '',
  budget: 0,
  members: [] as Array<{ name: string; studentId: string; role: string; contribution: string }>,
  teacherId: '',
  attachments: [] as File[]
})

const rules: FormRules = {
  name: [{ required: true, message: '请输入项目名称', trigger: 'blur' }],
  type: [{ required: true, message: '请选择项目类型', trigger: 'change' }],
  level: [{ required: true, message: '请选择项目级别', trigger: 'change' }],
  description: [{ required: true, message: '请输入项目简介', trigger: 'blur' }],
  plan: [{ required: true, message: '请输入研究计划', trigger: 'blur' }],
  expectedResults: [{ required: true, message: '请输入预期成果', trigger: 'blur' }],
  teacherId: [{ required: true, message: '请选择指导教师', trigger: 'change' }]
}

const teacherList = ref([
  { id: 'T001', name: '张教授' },
  { id: 'T002', name: '李教授' },
  { id: 'T003', name: '王教授' }
])

const addMember = () => {
  form.members.push({ name: '', studentId: '', role: 'member', contribution: '' })
}

const removeMember = (index: number) => {
  form.members.splice(index, 1)
}

const handleFileChange = (file: UploadFile) => {
  if (file.raw) {
    form.attachments.push(file.raw)
  }
}

const handleSubmit = async () => {
  const valid = await formRef.value?.validate().catch(() => false)
  if (!valid) return

  loading.value = true
  try {
    // TODO: 调用创建项目 API
    await new Promise(resolve => setTimeout(resolve, 1000))
    ElMessage.success('项目申报提交成功')
    router.push('/projects')
  } catch (error) {
    ElMessage.error('提交失败，请稍后重试')
  } finally {
    loading.value = false
  }
}

const handleSaveDraft = () => {
  ElMessage.success('草稿保存成功')
}

onMounted(() => {
  // TODO: 加载教师列表
})
</script>

<style scoped>
.project-create {
  padding: 0;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.upload-area {
  width: 100%;
}

:deep(.el-upload-dragger) {
  width: 100%;
}
</style>
