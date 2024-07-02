import type { AxiosError, AxiosInstance, AxiosResponse } from 'axios'
import axios from 'axios'
import HttpError from '@/http/HttpError'
import { singleton } from 'tsyringe'

export type HttpRequestConfig = {
  path: string
  method?: 'GET' | 'POST' | 'PATCH' | 'DELETE'
  params?: any
  body?: any
}

@singleton()
export default class AxiosHttpClient {
  private readonly client: AxiosInstance = axios.create({
    timeout: 3000,
    timeoutErrorMessage: '네트워크 연결 상태가 좋지 않앙!',
  })

  public async request(config: HttpRequestConfig) {
    return this.client
      .request({
        method: config.method,
        url: config.path,
        params: config.params,
        data: config.body,
      })
      .then((response: AxiosResponse) => {
        return response.data
      })
      .catch((e: AxiosError) => {
        return Promise.reject(new HttpError(e))
      })
  }
}
