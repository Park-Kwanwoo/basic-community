<script setup lang="ts">
import {ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const title = ref("")
const content = ref("")

const router = useRouter();
const write = function () {
  axios.post('/api/board', {
    title: title.value,
    content: content.value
  }).then(function () {
    router.replace({name: "home"})
  }).catch(function (err) {
    console.log(err)
  })
};
</script>

<template>
  <div>
    <el-input type="text" v-model="title" placeholder="제목을 입력해주세요."/>
  </div>
  <div>
    <el-input type="textarea" v-model="content" rows="15" resize="none"></el-input>
  </div>

  <div>
    <el-button type="primary" @click="write()">글 작성 완료</el-button>
  </div>
</template>

<style scoped>

</style>