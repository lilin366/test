import { get, post, put, del, ApiResponse } from '@/utils/request'

export interface Project {
  id: string
  name: string
  type: 'innovation' | 'entrepreneurship_training' | 'entrepreneurship_practice'
  level: 'national' | 'provincial' | 'school'
  status: 'draft' | 'pending' | 'approved' | 'rejected' | 'in_progress' | 'completed'
  description: string
  plan: string
  expectedResults: string
  budget: number
  members: ProjectMember[]
  teacherId: string
  teacherName?: string
  attachments: Attachment[]
  createdAt: string
  updatedAt: string
}

export interface ProjectMember {
  id?: string
  name: string
  studentId: string
  role: 'leader' | 'core' | 'member'
  contribution: string
}

export interface Attachment {
  id: string
  name: string
  url: string
  size: number
}

export interface ProjectListParams {
  page?: number
  size?: number
  status?: string
  type?: string
  level?: string
  keyword?: string
}

export interface ProjectListResponse {
  list: Project[]
  total: number
  page: number
  size: number
}

export function getProjectList(params: ProjectListParams): Promise<ApiResponse<ProjectListResponse>> {
  return get<ProjectListResponse>('/projects', { params })
}

export function getProjectDetail(id: string): Promise<ApiResponse<Project>> {
  return get<Project>(\`/projects/\${id}\`)
}

export function createProject(data: Partial<Project>): Promise<ApiResponse<Project>> {
  return post<Project>('/projects', data)
}

export function updateProject(id: string, data: Partial<Project>): Promise<ApiResponse<Project>> {
  return put<Project>(\`/projects/\${id}\`, data)
}

export function deleteProject(id: string): Promise<ApiResponse<null>> {
  return del<null>(\`/projects/\${id}\`)
}

export function submitProject(id: string): Promise<ApiResponse<Project>> {
  return post<Project>(\`/projects/\${id}/submit\`)
}

export function getMyProjects(): Promise<ApiResponse<Project[]>> {
  return get<Project[]>('/projects/my')
}
