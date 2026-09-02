<script setup>
import BaseButton from './BaseButton.vue'

const props = defineProps({
  totalPages: Number,
  currentPage: Number,
})

const emit = defineEmits(['changePage'])
</script>

<template>
  <div id="pagination-area">
    <BaseButton
      v-for="page in totalPages"
      :key="page"
      :text="String(page)"
      @click="emit('changePage', page - 1)"
      :disabled="currentPage == page - 1"
    ></BaseButton>
  </div>
</template>

<style scoped>
#pagination-area {
  display: flex;
  justify-content: center;

  margin-top: 16px;
  gap: 8px;
}
</style>
