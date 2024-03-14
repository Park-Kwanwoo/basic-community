<script setup lang="ts">
import {onMounted, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const router = useRouter();

const props = defineProps({
  boardId: {
    type: [Number, String],
    require: true,
  }
})

const board = ref({
  id: Number,
  title: String,
  content: String
})

onMounted(() => {
  axios.get(`/api/board/${props.boardId}`)
      .then(res => {
        board.value = res.data;
      })
      .catch(err => {
        console.log(err);
      })
})

const edit = () => {
  axios.patch(`/api/board/${props.boardId}`, board.value)
      .then(res => {
        router.replace({name: 'BoardRead'})
      })
      .catch(err => {
        console.log(err);
      })
}

const cancel = () => {
  axios.get(`/api/board/${props.boardId}`)
      .then(res => {
        router.replace({name: 'BoardRead'})
      })
      .catch(err => {
        console.log(err);
      })
}
</script>

<template>
  <div class="title">
    <el-input type="text" v-model="board.title" readonly></el-input>
  </div>

  <div class="content">
    <el-input type="textarea" v-model="board.content" rows="30"></el-input>
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