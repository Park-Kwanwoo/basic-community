<script setup lang="ts">
import { onMounted, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { container } from 'tsyringe'
import BoardRepository from '@/respository/BoardRepository'
import Board from '@/entity/board/Board'
import Comments from '@/components/Comments.vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import CommentRepository from '@/respository/CommentRepository'
import Paging from '@/entity/data/Paging'
import type Comment from '@/entity/comment/Comment'

const router = useRouter()

const COMMENT_REPOSITORY = container.resolve(CommentRepository)

const props = defineProps<{
  boardId: number
}>()

type StateType = {
  board: Board
  commentList: Paging<Comment>
}

const state = reactive<StateType>({
  board: new Board(),
  commentList: new Paging<Comment>(),
})
const BOARD_REPOSITORY = container.resolve(BoardRepository)

function getBoard() {
  BOARD_REPOSITORY.get(props.boardId)
    .then((board: Board) => {
      state.board = board
    })
    .catch((e) => {
      console.error(e)
    })
}

function getCommentList(page = 1) {
  COMMENT_REPOSITORY.getList(page, props.boardId).then((commentList) => {
    state.commentList = commentList
  })
}

onMounted(() => {
  getBoard()
  getCommentList()
})

const edit = (boardId: Number) => {
  router.push({ name: 'BoardEdit', params: { boardId } })
}

function remove() {
  ElMessageBox.confirm('정말로 삭제하시겠습니까?', 'Warning', {
    title: '삭제',
    confirmButtonText: '삭제',
    cancelButtonText: '취소',
    type: 'warning',
  }).then(() => {
    BOARD_REPOSITORY.delete(props.boardId).then(() => {
      ElMessage({ type: 'success', message: '삭제완' })
      router.back()
    })
  })
}
</script>

<template>
  <el-row>
    <div class="title">{{ state.board.title }}</div>
    <div class="regDate">Posted by {{ state.board.username }}</div>
  </el-row>

  <el-row>
    <el-col>
      <div class="content">
        {{ state.board.content }}
      </div>
    </el-col>

    <div class="footer">
      <div class="edit" @click="edit(state.board.id)">수정</div>
      <div class="delete" @click="remove()">삭제</div>
    </div>
  </el-row>

  <el-row class="comments">
    <el-col>
      <Comments :board-id="state.board.id" :comment-list="state.commentList" />
    </el-col>
  </el-row>

  <div style="width: 100%; display: flex; justify-content: center">
    <el-pagination
      :background="false"
      layout="prev, pager, next"
      v-model:current-page="state.commentList.page"
      :total="state.commentList.totalCount"
      :default-page-size="15"
      @current-change="(page: number) => getCommentList(page)"
    />
  </div>
</template>

<style scoped lang="scss">
.title {
  font-size: 1.8rem;
  font-weight: 400;
}

.content {
  margin-top: 1.88rem;
  font-weight: 300;

  word-break: break-all;
  white-space: break-spaces;
  line-height: 1.4;
  min-height: 5rem;
}

.regDate {
  display: flex;
  margin-top: 0.5rem;
  font-size: 0.78rem;
  font-weight: 300;
}

hr {
  border-color: #f9f9f9;
  margin: 1.2rem 0;
}
.footer {
  margin-top: 1rem;
  display: flex;
  font-size: 0.78rem;
  justify-content: flex-end;
  gap: 0.8rem;

  .delete {
    color: red;
  }
}

.comments {
  margin-top: 4.8rem;
}
</style>
