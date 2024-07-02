<script setup lang="ts"></script>

<template>
  <div class="header">
    <img src="/images/logo.png" alt="logo" class="logo" />
    <div class="title">관맨 블로그</div>
  </div>
</template>

<style scoped lang="scss">
.header {
  padding: 0;
  margin: 1rem 2rem;
  height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.logo {
  width: 52px;
  object-fit: cover;
}

.title {
  font-size: 2rem;
  font-weight: 300;
  margin-left: 5px;
}
</style>
