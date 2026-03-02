import { get, post, ApiResponse } from '@/utils/request'

export interface Achievement {
  id: string
  projectId: string
  projectName?: string
  type: 'paper' | 'patent' | 'software_copyright' | 'competition' | 'practice' | 'other'
  name: string
  description: string
  publishDate: string
  publisher: string
  status: 'pending' | 'approved' | 'rejected'
  credits?: number
  createdAt: string
}

export interface AchievementListParams {
  page?: number
  size?: number
  type?: string
  status?: string
  projectId?: string
}

export interface AchievementListResponse {
  list: Achievement[]
  total: number
  page: number
  size: number
}

export function getAchievementList(params: AchievementListParams): Promise<ApiResponse<AchievementListResponse>> {
  return get<AchievementListResponse>('/achievements', { params })
}

export function createAchievement(data: Partial<Achievement>): Promise<ApiResponse<Achievement>> {
  return post<Achievement>('/achievements', data)
}

export function reviewAchievement(id: string, status: 'approved' | 'rejected', comment?: string): Promise<ApiResponse<Achievement>> {
  return post<Achievement>(\`/achievements/\${id}/review\`, { status, comment })
}

export function getMyAchievements(): Promise<ApiResponse<Achievement[]>> {
  return get<Achievement[]>('/achievements/my')
}
