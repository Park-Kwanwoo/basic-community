<script setup lang="ts">
import { onMounted, reactive } from 'vue'
import { useRouter } from 'vue-router'

import { container } from 'tsyringe'
import BoardRepository from '@/respository/BoardRepository'
import BoardEdit from '@/entity/board/BoardEdit'
import Board from '@/entity/board/Board'

const router = useRouter()

const props = defineProps<{
  boardId: number
}>()

const BOARD_REPOSITORY = container.resolve(BoardRepository)
type StateType = {
  boardEdit: BoardEdit
}

const state = reactive<StateType>({
  boardEdit: new BoardEdit(),
})

onMounted(() => {
  getBoard()
})

function getBoard() {
  BOARD_REPOSITORY.get(props.boardId)
    .then((boardEdit: BoardEdit) => {
      state.boardEdit = boardEdit
    })
    .catch((e) => {
      console.error(e)
    })
}

function edit() {
  BOARD_REPOSITORY.edit(state.boardEdit, props.boardId)
    .then(() => {
      router.push({ name: 'BoardRead', params: props.boardId })
    })
    .catch((e) => {
      console.error(e)
    })
}

function cancel() {
  router.push({ name: 'BoardRead', params: props.boardId })
}
</script>

<template>
  <div class="title">
    <el-input type="text" v-model="state.boardEdit.title" readonly></el-input>
  </div>

  <div class="content">
    <el-input type="textarea" v-model="state.boardEdit.content" rows="30"></el-input>
  </div>

  <div class="button">
    <el-button type="primary" @click="edit()">수정</el-button>
    <el-button type="danger" @click="cancel()">취소</el-button>
  </div>
</template>

<style scoped>
.content {
  margin-top: 5px;
}

.button {
  margin-top: 5px;
  display: flex;
  justify-content: end;
}
</style>
