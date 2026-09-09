<script setup>
import { onMounted } from 'vue'
import Header from './components/parts/Header.vue'
import { useAuthStore } from './stores/auth.js'

const authStore = useAuthStore()

onMounted(async () => {
  await authStore.fetchMe()
})
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
