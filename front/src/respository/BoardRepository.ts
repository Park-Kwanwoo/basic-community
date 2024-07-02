import { inject, singleton } from 'tsyringe'
import HttpRepository from '@/respository/HttpRepository'
import type BoardWrite from '@/entity/board/BoardWrite'
import Board from '@/entity/board/Board'
import BoardEdit from '@/entity/board/BoardEdit'

@singleton()
export default class BoardRepository {
  constructor(@inject(HttpRepository) private readonly httpRepository: HttpRepository) {}

  public write(request: BoardWrite) {
    return this.httpRepository.post({
      path: 'api/board',
      body: request,
    })
  }

  public get(boardId: number) {
    return this.httpRepository.get<Board>(
      {
        path: `/api/board/${boardId}`,
      },
      Board,
    )
  }

  public getList(page: number) {
    return this.httpRepository.getList<Board>(
      {
        path: `/api/board?page=${page}&size=3`,
      },
      Board,
    )
  }

  public edit(request: BoardEdit, boardId: number) {
    return this.httpRepository.patch<BoardEdit>(
      {
        path: `/api/board/${boardId}`,
        body: request,
      },
      BoardEdit,
    )
  }
  public delete(boardId: number) {
    return this.httpRepository.delete({
      path: `/api/board/${boardId}`,
    })
  }
}
