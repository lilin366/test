import { post, ApiResponse } from '@/utils/request'

export interface UploadResult {
  id: string
  name: string
  url: string
  size: number
  mimeType: string
}

export function uploadFile(file: File, type?: 'project' | 'achievement' | 'avatar'): Promise<ApiResponse<UploadResult>> {
  const formData = new FormData()
  formData.append('file', file)
  if (type) {
    formData.append('type', type)
  }
  return post<UploadResult>('/upload', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

export function uploadFiles(files: File[], type?: string): Promise<ApiResponse<UploadResult[]>> {
  const formData = new FormData()
  files.forEach(file => {
    formData.append('files', file)
  })
  if (type) {
    formData.append('type', type)
  }
  return post<UploadResult[]>('/upload/batch', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
