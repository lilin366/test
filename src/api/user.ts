import { get, post, put, del, ApiResponse } from '@/utils/request'

export interface User {
  id: string
  username: string
  name: string
  role: 'student' | 'teacher' | 'admin'
  avatar?: string
  college?: string
  major?: string
  email?: string
  phone?: string
  studentId?: string
  teacherId?: string
  status: 'active' | 'inactive'
  createdAt: string
}

export interface UserListParams {
  page?: number
  size?: number
  role?: string
  college?: string
  keyword?: string
}

export interface UserListResponse {
  list: User[]
  total: number
  page: number
  size: number
}

export function getUserList(params: UserListParams): Promise<ApiResponse<UserListResponse>> {
  return get<UserListResponse>('/users', { params })
}

export function getUserDetail(id: string): Promise<ApiResponse<User>> {
  return get<User>(\`/users/\${id}\`)
}

export function createUser(data: Partial<User>): Promise<ApiResponse<User>> {
  return post<User>('/users', data)
}

export function updateUser(id: string, data: Partial<User>): Promise<ApiResponse<User>> {
  return put<User>(\`/users/\${id}\`, data)
}

export function deleteUser(id: string): Promise<ApiResponse<null>> {
  return del<null>(\`/users/\${id}\`)
}

export function getTeacherList(): Promise<ApiResponse<User[]>> {
  return get<User[]>('/users/teachers')
}

export function changePassword(oldPassword: string, newPassword: string): Promise<ApiResponse<null>> {
  return post<null>('/users/change-password', { oldPassword, newPassword })
}

export function updateProfile(data: Partial<User>): Promise<ApiResponse<User>> {
  return put<User>('/users/profile', data)
}
