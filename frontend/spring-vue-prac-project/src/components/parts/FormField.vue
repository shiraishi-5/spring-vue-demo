<script setup>
const props = defineProps({
  id: {
    type: String,
    default: '',
  },
  label: {
    type: String,
    default: '',
  },
})
</script>

<template>
  <div class="form-field">
    <label :for="id">{{ label }}</label>
    <slot></slot>
  </div>
</template>

<style scoped>
.form-field {
  display: flex;
  flex-direction: column;
  margin-bottom: 8px;
}
</style>
