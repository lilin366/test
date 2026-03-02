import { post, get, ApiResponse } from '@/utils/request'

export interface LoginParams {
  username: string
  password: string
}

export interface RegisterParams {
  username: string
  password: string
  name: string
  role: 'student' | 'teacher' | 'admin'
  college?: string
  major?: string
  email?: string
}

export interface LoginResponse {
  token: string
  user: {
    id: string
    username: string
    name: string
    role: 'student' | 'teacher' | 'admin'
    avatar?: string
    college?: string
    major?: string
    email?: string
  }
}

export interface UserInfo {
  id: string
  username: string
  name: string
  role: 'student' | 'teacher' | 'admin'
  avatar?: string
  college?: string
  major?: string
  email?: string
}

/**
 * 用户登录
 */
export function login(username: string, password: string): Promise<ApiResponse<LoginResponse>> {
  return post<LoginResponse>('/auth/login', { username, password })
}

/**
 * 用户注册
 */
export function register(data: RegisterParams): Promise<ApiResponse<RegisterParams>> {
  return post<RegisterParams>('/auth/register', data)
}

/**
 * 用户登出
 */
export function logout(): Promise<ApiResponse<null>> {
  return post<null>('/auth/logout')
}

/**
 * 获取当前用户信息
 */
export function getCurrentUser(): Promise<ApiResponse<UserInfo>> {
  return get<UserInfo>('/auth/me')
}
