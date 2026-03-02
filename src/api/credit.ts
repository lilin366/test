import { get, post, ApiResponse } from '@/utils/request'

export interface Credit {
  id: string
  studentId: string
  studentName?: string
  projectId: string
  projectName?: string
  baseCredits: number
  coefficient: number
  finalCredits: number
  status: 'pending' | 'confirmed' | 'appealed'
  createdAt: string
}

export interface CreditRule {
  id: string
  projectLevel: 'national' | 'provincial' | 'school'
  projectType: 'innovation' | 'entrepreneurship_training' | 'entrepreneurship_practice'
  baseCredits: number
  leaderCoefficient: number
  coreCoefficient: number
  memberCoefficient: number
}

export interface CreditListParams {
  page?: number
  size?: number
  studentId?: string
  status?: string
}

export interface CreditListResponse {
  list: Credit[]
  total: number
  page: number
  size: number
}

export function getCreditList(params: CreditListParams): Promise<ApiResponse<CreditListResponse>> {
  return get<CreditListResponse>('/credits', { params })
}

export function getMyCredits(): Promise<ApiResponse<Credit[]>> {
  return get<Credit[]>('/credits/my')
}

export function getCreditRules(): Promise<ApiResponse<CreditRule[]>> {
  return get<CreditRule[]>('/credits/rules')
}

export function updateCreditRule(id: string, data: Partial<CreditRule>): Promise<ApiResponse<CreditRule>> {
  return post<CreditRule>(\`/credits/rules/\${id}\`, data)
}

export function appealCredit(id: string, reason: string): Promise<ApiResponse<Credit>> {
  return post<Credit>(\`/credits/\${id}/appeal\`, { reason })
}

export function confirmCredit(id: string): Promise<ApiResponse<Credit>> {
  return post<Credit>(\`/credits/\${id}/confirm\`)
}
