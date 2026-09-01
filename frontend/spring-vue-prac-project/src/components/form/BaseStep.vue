<script setup></script>

<template>
  <div class="base-step">
    <slot></slot>
  </div>
</template>

<style scoped>
.base-step {
  display: flex;
  flex-direction: column;

  padding-bottom: 16px;
}
</style>
