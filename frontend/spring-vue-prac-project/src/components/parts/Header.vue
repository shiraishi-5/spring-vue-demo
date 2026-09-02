<script setup>
import { RouterLink } from 'vue-router'
</script>

<template>
  <div id="header">
    <div class="title">
      <h2>ユーザー管理</h2>
    </div>
    <div class="link">
      <RouterLink to="/users">ユーザー一覧</RouterLink>
      <RouterLink to="/user/create">ユーザー登録</RouterLink>
    </div>
  </div>
</template>

<style scoped>
#header {
  display: flex;
  align-items: center;
  justify-content: space-between;

  padding: 16px;

  background-color: aquamarine;
}

.link {
  display: flex;
  gap: 32px;
}

a {
  text-decoration: none;

  color: white;
  background-color: gray;

  padding: 8px;
  border-radius: 8px;
}

a:hover {
  background-color: rgb(201, 201, 201);
}
</style>
