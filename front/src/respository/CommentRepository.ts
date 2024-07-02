import { inject, singleton } from 'tsyringe'
import HttpRepository from '@/respository/HttpRepository'
import Comment from '@/entity/comment/Comment'
import type CommentWrite from '@/entity/comment/CommentWrite'

@singleton()
export default class CommentRepository {
  constructor(@inject(HttpRepository) private readonly httpRepository: HttpRepository) {}

  public write(request: CommentWrite, boardId: number) {
    return this.httpRepository.post({
      path: `/api/board/${boardId}/comments`,
      body: request,
    })
  }

  public getList(page: number, boardId: number) {
    return this.httpRepository.getList<Comment>(
      {
        path: `/api/${boardId}/comments?page=${page}&size=15`,
      },
      Comment,
    )
  }
}
