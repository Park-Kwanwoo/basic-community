<script setup lang="ts">
import BoardWrite from '@/entity/board/BoardWrite'
import { reactive } from 'vue'
import { container } from 'tsyringe'
import BoardRepository from '@/respository/BoardRepository'
import { ElMessage } from 'element-plus'
import type HttpError from '@/http/HttpError'
import { useRouter } from 'vue-router'

const state = reactive({
  boardWrite: new BoardWrite(),
})

const router = useRouter()

const BOARD_REPOSITORY = container.resolve(BoardRepository)
function write() {
  BOARD_REPOSITORY.write(state.boardWrite)
    .then(() => {
      ElMessage({ message: '등록 완료' })
      router.replace('/')
    })
    .catch((e: HttpError) => {
      ElMessage.error({ message: '등록 실패' })
    })
}
</script>

<template>
  <el-form label-position="top">
    <el-form-item label="제목">
      <el-input class="title" type="text" v-model="state.boardWrite.title" placeholder="제목을 입력해주세요." />
    </el-form-item>

    <el-form-item label="내용">
      <el-input type="textarea" v-model="state.boardWrite.content" rows="15" resize="none"></el-input>
    </el-form-item>

    <el-form-item>
      <el-button class="button" type="primary" @click="write()">글 작성 완료</el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped lang="scss"></style>
