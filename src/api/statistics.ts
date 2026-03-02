import { get, ApiResponse } from '@/utils/request'

export interface StatisticsOverview {
  totalProjects: number
  completedProjects: number
  inProgressProjects: number
  totalUsers: number
  studentCount: number
  teacherCount: number
  totalCredits: number
  totalAchievements: number
}

export interface ProjectTrend {
  month: string
  submitted: number
  completed: number
}

export interface ProjectDistribution {
  name: string
  value: number
}

export interface StatisticsData {
  overview: StatisticsOverview
  projectTrend: ProjectTrend[]
  projectTypeDistribution: ProjectDistribution[]
  projectLevelDistribution: ProjectDistribution[]
  achievementDistribution: ProjectDistribution[]
}

export function getStatisticsOverview(): Promise<ApiResponse<StatisticsOverview>> {
  return get<StatisticsOverview>('/statistics/overview')
}

export function getStatisticsData(): Promise<ApiResponse<StatisticsData>> {
  return get<StatisticsData>('/statistics/data')
}

export function getProjectTrend(year?: number): Promise<ApiResponse<ProjectTrend[]>> {
  return get<ProjectTrend[]>('/statistics/project-trend', { params: { year } })
}

export function getAchievementStats(): Promise<ApiResponse<ProjectDistribution[]>> {
  return get<ProjectDistribution[]>('/statistics/achievements')
}

export function exportStatisticsReport(type: 'pdf' | 'excel'): Promise<ApiResponse<string>> {
  return get<string>(\`/statistics/export/\${type}\`)
}
