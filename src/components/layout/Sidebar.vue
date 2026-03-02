<template>
  <div class="sidebar-container">
    <div class="logo">
      <el-icon :size="28" color="#409eff"><Opportunity /></el-icon>
      <span v-show="!isCollapse" class="logo-text">创新平台</span>
    </div>
    <el-menu
      :default-active="activeMenu"
      :collapse="isCollapse"
      :collapse-transition="false"
      router
      background-color="#304156"
      text-color="#bfcbd9"
      active-text-color="#409eff"
    >
      <el-menu-item index="/dashboard">
        <el-icon><HomeFilled /></el-icon>
        <template #title>首页</template>
      </el-menu-item>
      <el-menu-item index="/projects">
        <el-icon><Document /></el-icon>
        <template #title>项目管理</template>
      </el-menu-item>
      <el-menu-item index="/reviews">
        <el-icon><EditPen /></el-icon>
        <template #title>评审管理</template>
      </el-menu-item>
      <el-menu-item index="/achievements">
        <el-icon><Trophy /></el-icon>
        <template #title>成果管理</template>
      </el-menu-item>
      <el-menu-item index="/users">
        <el-icon><User /></el-icon>
        <template #title>用户管理</template>
      </el-menu-item>
      <el-menu-item index="/statistics">
        <el-icon><DataAnalysis /></el-icon>
        <template #title>数据统计</template>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'

defineProps<{
  isCollapse: boolean
}>()

const route = useRoute()

const activeMenu = computed(() => route.path)
</script>

<style scoped>
.sidebar-container {
  height: 100%;
}

.logo {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 16px;
  background-color: #263445;
}

.logo-text {
  margin-left: 10px;
  font-size: 16px;
  font-weight: 600;
  color: #fff;
  white-space: nowrap;
}

.el-menu {
  border-right: none;
}

:deep(.el-menu-item),
:deep(.el-sub-menu__title) {
  height: 50px;
  line-height: 50px;
}

:deep(.el-menu-item:hover),
:deep(.el-sub-menu__title:hover) {
  background-color: #263445 !important;
}

:deep(.el-menu-item.is-active) {
  background-color: #409eff !important;
}
</style>
