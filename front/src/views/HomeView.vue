<script setup lang="ts">
import BoardComponent from '@/components/BoardComponent.vue'
import { onMounted, reactive } from 'vue'
import { container } from 'tsyringe'
import BoardRepository from '@/respository/BoardRepository'
import Paging from '@/entity/data/Paging'
import type Board from '@/entity/board/Board'

const BOARD_REPOSITORY = container.resolve(BoardRepository)
type StateType = {
  boardList: Paging<Board>
}

const state = reactive<StateType>({
  boardList: new Paging<Board>(),
})

function getList(page = 1) {
  BOARD_REPOSITORY.getList(page).then((boardList) => {
    state.boardList = boardList
  })
}

onMounted(() => {
  getList()
})
</script>

<template>
  <div class="content">
    <span class="totalCount">게시글 수: {{ state.boardList.totalCount }}</span>

    <ul class="posts">
      <li v-for="board in state.boardList.items" :key="board.id">
        <BoardComponent :board="board" />
      </li>
    </ul>

    <div style="width: 100%; display: flex; justify-content: center">
      <el-pagination
        :background="true"
        layout="prev, pager, next"
        v-model:current-page="state.boardList.page"
        :total="state.boardList.totalCount"
        :default-page-size="3"
        @current-change="(page: number) => getList(page)"
      />
    </div>
  </div>
</template>

<style scoped lang="scss">
.content {
  padding: 0 1rem 0 1rem;
  margin-bottom: 2rem;
}

.totalCount {
  font-size: 0.88rem;
}

.pagination {
  display: flex;
  align-items: center;
}
</style>
