<script setup lang="ts">
import Comment from '@/entity/comment/Comment'
import { ElMessage } from 'element-plus'

const props = defineProps<{
  comment: Comment
}>()

function remove() {
  ElMessage('삭제')
}
</script>

<template>
  <div class="comment">
    <div class="header">
      <div class="section">
        <div class="author">{{ props.comment.author }}</div>
      </div>
      <div class="createdDate">{{ props.comment.getDisplayCreatedDate() }}</div>
    </div>

    <div class="delete" @click="remove()">삭제</div>
    <div class="content">{{ props.comment.content }}</div>
  </div>
</template>

<style scoped lang="scss">
.comment {
  width: 100%;
}

.header {
  display: flex;
  justify-content: space-between;
}

.author {
  font-weight: 600;
  font-size: 1.2rem;
}

.section {
  display: flex;
  flex-direction: column;
}

.createdDate {
  margin-top: 12px;
  color: #797979;
  font-size: 0.7rem;
  float: right;
}

.content {
  margin-top: 10px;
}

.delete {
  margin-top: 10px;
  font-size: 0.78rem;
  float: right;
  color: red;
}
</style>
