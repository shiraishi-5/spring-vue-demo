<script setup>
const props = defineProps({
  title: {
    type: String,
    default: 'タイトル',
  },
})
</script>

<template>
  <h3>{{ title }}</h3>
</template>

<style scoped></style>
