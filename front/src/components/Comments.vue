<script setup lang="ts">
import CommentComponent from '@/components/CommentComponent.vue'
import { reactive } from 'vue'
import { container } from 'tsyringe'
import CommentRepository from '@/respository/CommentRepository'
import Paging from '@/entity/data/Paging'
import type Comment from '@/entity/comment/Comment'
import { ElMessage } from 'element-plus'
import CommentWrite from '@/entity/comment/CommentWrite'

const props = defineProps<{
  boardId: number
  commentList: Paging<Comment>
}>()

const COMMENT_REPOSITORY = container.resolve(CommentRepository)
type StateType = {
  commentWrite: CommentWrite
}

const state = reactive<StateType>({
  commentWrite: new CommentWrite(),
})

function write() {
  COMMENT_REPOSITORY.write(state.commentWrite, props.boardId)
    .then(() => {
      ElMessage('댓글 작성 완료')
      window.location.reload()
    })
    .catch((e) => {
      console.log('>>>', e.message)
    })
}
</script>

<template>
  <div class="totalCount">댓글 수: {{ props.commentList.totalCount }}</div>

  <div class="write">
    <div class="form">
      <div class="section">
        <div>
          <label for="author">작성자</label>
          <el-input id="author" type="text" placeholder="익명" v-model="state.commentWrite.author"></el-input>
        </div>

        <div>
          <label for="password">비밀번호</label>
          <el-input
            id="password"
            type="password"
            placeholder="비밀번호"
            v-model="state.commentWrite.password"
          ></el-input>
        </div>
      </div>

      <div class="content">
        <label for="content">내용</label>
        <el-input
          id="content"
          type="textarea"
          v-model="state.commentWrite.content"
          :rows="5"
          :autosize="{ minRows: 5, maxRows: 4 }"
        ></el-input>
      </div>
    </div>

    <el-button type="primary" class="button" @click="write()">등록하기</el-button>
  </div>

  <ul class="comments">
    <li class="comment" v-for="comment in props.commentList.items" :key="comment.id">
      <CommentComponent :comment="comment" />
    </li>
  </ul>
</template>

<style scoped lang="scss">
.totalCount {
  font-size: 1.4rem;
}

.write {
  display: flex;
  flex-direction: column;
  gap: 12px;

  .form {
    display: flex;
    gap: 12px;
    margin-top: 10px;

    .section {
      width: 140px;
      display: flex;
      gap: 5px;
      flex-direction: column;
    }

    .content {
      flex-grow: 1;
    }
  }

  .button {
    width: 100px;
    align-self: flex-end;
  }
}

label {
  font-size: 0.78rem;
}

.comments {
  margin-top: 3rem;
  list-style: none;
  padding: 0;

  .comment {
    margin-bottom: 2.4rem;

    &:last-child {
      margin-bottom: 0;
    }
  }
}

.totalCount {
  font-size: 0.88rem;
}
</style>
