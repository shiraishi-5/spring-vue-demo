<script setup>
const props = defineProps({
  error: String,
})
</script>

<template>
  <p v-if="error">{{ error }}</p>
</template>

<style scoped>
p {
  margin: 0;
  color: red;
}
</style>
