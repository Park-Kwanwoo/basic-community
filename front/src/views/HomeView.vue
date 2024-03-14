<script setup lang="ts">

import axios from "axios";
import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();

const boards = ref([]);

axios.get("/api/board")
    .then(res => {
      res.data.forEach((r: any) => {
        boards.value.push(r);
      })
    })
    .catch(err => {
      console.log(err);
    })

const moveToRead = (index: number) => {

  const boardId = boards.value.splice(index, 1)[0].id;
  router.push({name: 'BoardRead', params: {boardId}});
}
</script>

<template>
  <el-table :data="boards" style="width: 60%">
    <el-table-column prop="id" label="글번호" width="120" />
    <el-table-column label="제목" style="width: 60%">
      <template #default="scope">
        <el-button link type="primary" @click="moveToRead(scope.$index)">{{ scope.row.title }}</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped>
</style>