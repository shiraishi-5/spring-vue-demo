<script setup>
const props = defineProps(['title'])
</script>

<template>
  <div class="section-area">
    <h4>{{ title }}</h4>
    <div class="body">
      <slot></slot>
    </div>
  </div>
</template>

<style scoped>
h4 {
  border-left: 8px solid rgb(0, 225, 255);
}

.section-area {
  display: flex;
  flex-direction: column;
}

.body {
  display: flex;
  justify-content: left;
  background-color: rgb(213, 213, 213);
  padding-left: 16px;
  border-radius: 16px;

  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.118);
}
</style>
