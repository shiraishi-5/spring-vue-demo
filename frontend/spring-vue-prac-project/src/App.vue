<script setup>
import Header from './components/parts/Header.vue'
</script>

<template>
  <Header></Header>
  <main>
    <router-view></router-view>
  </main>
</template>

<style scoped>
main {
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 16px;
}
</style>
