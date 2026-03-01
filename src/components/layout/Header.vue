<template>
  <div class="header-container">
    <div class="left">
      <el-icon class="toggle-btn" @click="$emit('toggle')">
        <component :is="isCollapse ? 'Expand' : 'Fold'" />
      </el-icon>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item v-for="item in breadcrumbs" :key="item.path">
          {{ item.meta?.title || item.name }}
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="right">
      <el-dropdown trigger="click">
        <div class="user-info">
          <el-avatar :size="32" :src="userStore.userInfo?.avatar">
            {{ userStore.userInfo?.name?.charAt(0) || 'U' }}
          </el-avatar>
          <span class="username">{{ userStore.userInfo?.name || '用户' }}</span>
          <el-icon><ArrowDown /></el-icon>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="goProfile">
              <el-icon><User /></el-icon>
              个人中心
            </el-dropdown-item>
            <el-dropdown-item divided @click="handleLogout">
              <el-icon><SwitchButton /></el-icon>
              退出登录
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessageBox } from 'element-plus'

defineProps<{
  isCollapse: boolean
}>()

defineEmits<{
  toggle: []
}>()

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const breadcrumbs = computed(() => {
  return route.matched.filter(item => item.meta?.title)
})

const goProfile = () => {
  router.push('/profile')
}

const handleLogout = async () => {
  try {
    await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    userStore.logout()
    router.push('/login')
  } catch {
    // 取消退出
  }
}
</script>

<style scoped>
.header-container {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.toggle-btn {
  font-size: 20px;
  cursor: pointer;
  color: #333;
}

.toggle-btn:hover {
  color: #409eff;
}

.right {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  padding: 8px 12px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.user-info:hover {
  background-color: #f5f7fa;
}

.username {
  font-size: 14px;
  color: #333;
}
</style>
