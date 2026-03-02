import { get, post, ApiResponse } from '@/utils/request'

export interface Review {
  id: string
  projectId: string
  projectName?: string
  reviewerId: string
  reviewerName?: string
  status: 'pending' | 'completed'
  score?: number
  comment?: string
  result?: 'pass' | 'fail' | 'revise'
  createdAt: string
}

export interface ReviewListParams {
  page?: number
  size?: number
  status?: string
}

export interface ReviewListResponse {
  list: Review[]
  total: number
  page: number
  size: number
}

export function getReviewList(params: ReviewListParams): Promise<ApiResponse<ReviewListResponse>> {
  return get<ReviewListResponse>('/reviews', { params })
}

export function submitReview(id: string, data: { score: number; comment: string; result: 'pass' | 'fail' | 'revise' }): Promise<ApiResponse<Review>> {
  return post<Review>(\`/reviews/\${id}/submit\`, data)
}

export function getMyReviews(): Promise<ApiResponse<Review[]>> {
  return get<Review[]>('/reviews/my')
}
