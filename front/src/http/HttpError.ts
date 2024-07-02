import type { AxiosError } from 'axios'

export default class HttpError {
  private readonly code: string
  private readonly message: string

  public constructor(e: any) {
    this.code = e.response?.data ?? '500'
    this.message = e.response?.data.message ?? '네트워크 상태 좋지 않음'
  }

  public getMessage() {
    return this.message
  }
}
