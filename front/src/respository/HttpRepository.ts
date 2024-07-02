import type { HttpRequestConfig } from '@/http/AxiosHttpClient'
import AxiosHttpClient from '@/http/AxiosHttpClient'
import { inject, singleton } from 'tsyringe'
import { plainToInstance } from 'class-transformer'
import Null from '@/entity/data/Null'
import Paging from '@/entity/data/Paging'

@singleton()
export default class HttpRepository {
  constructor(@inject(AxiosHttpClient) private readonly httpClient: AxiosHttpClient) {
    this.httpClient = httpClient
  }

  public get<T>(config: HttpRequestConfig, clazz: { new (...args: any[]) }): Promise<T> {
    return this.httpClient.request({ ...config, method: 'GET' }).then((res) => plainToInstance(clazz, res))
  }

  public getList<T>(config: HttpRequestConfig, clazz: { new (...args: any[]) }): Promise<Paging<T>> {
    return this.httpClient.request({ ...config, method: 'GET' }).then((res) => {
      const paging = plainToInstance<Paging<T>, any>(Paging, res)
      const items = plainToInstance<T, any>(clazz, res.items)
      paging.setItems(items)
      return paging
    })
  }

  public post<T>(config: HttpRequestConfig, clazz: { new (...args: any[]) } | null = null): Promise<T> {
    return this.httpClient
      .request({ ...config, method: 'POST' })
      .then((res) => plainToInstance(clazz !== null ? clazz : Null, res))
  }

  public patch<T>(config: HttpRequestConfig, clazz: { new (...args: any[]) } | null = null): Promise<T> {
    return this.httpClient
      .request({ ...config, method: 'PATCH' })
      .then((res) => plainToInstance(clazz !== null ? clazz : Null, res))
  }

  public delete<T>(config: HttpRequestConfig, clazz: { new (...args: any[]) } | null = null): Promise<T> {
    return this.httpClient
      .request({ ...config, method: 'DELETE' })
      .then((res) => plainToInstance(clazz !== null ? clazz : Null, res))
  }
}
