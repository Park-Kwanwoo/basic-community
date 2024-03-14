<script setup lang="ts">
import {onMounted, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";
import Content from "@/App.vue";

const router = useRouter();

const board = ref({
  id: 0,
  title: "",
  content: ""
})

const props = defineProps({
  boardId: {
    type: [Number, String],
    require: true,
  },
});

onMounted(() => {
  axios.get(`/api/board/${props.boardId}`)
      .then(res => {
        board.value = res.data;
      })
      .catch(err => {
        console.log(err);
      })
})

const edit = (boardId: any) => {
  router.push({name: 'BoardEdit', params: {boardId}})
}

const del = (boardId: any) => {

  axios.delete(`/api/board/${boardId}`)
      .then(res => {
        console.log(res);
        router.replace('/');
      })
      .catch(err => {
        console.log(err);
      })

}
</script>

<template>
  <el-row>
    <el-col>
      <h2 class="title">{{ board.title }}</h2>
    </el-col>
  </el-row>

  <el-row>
    <el-col>
      <div class="content">{{ board.content }}</div>
    </el-col>
  </el-row>

  <el-row>
    <el-col>
      <div class="editBtn">
        <el-button type="warning" @click="edit(boardId)">수정</el-button>
        <el-button type="danger" @click="del(boardId)">삭제</el-button>
      </div>

    </el-col>
  </el-row>
</template>

<style scoped>

.title {
  font-size: 1.6rem;
  font-weight: 600;
  color: #383838;
}

.content {
  font-size: 0.85rem;
  margin-top: 8px;
  color: #7e7e7e;
  white-space: break-spaces;
}

.editBtn {
  display: flex;
  justify-content: flex-end;
}
</style>