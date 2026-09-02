<template>
  <table class="base-table">
    <thead>
      <slot name="head"></slot>
    </thead>
    <tbody>
      <slot name="body"></slot>
    </tbody>
  </table>
</template>

<style scoped>
.base-table {
  width: 80%;

  border-collapse: collapse;
  background-color: #ffffff;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

thead {
  width: 100%;
  background-color: #f5f5f5;
}

tbody {
  width: 100%;
}

.base-table :deep(th) {
  padding: 12px 16px;
  background: #f5f5f5;
}

.base-table :deep(td) {
  padding: 12px 16px;
}
</style>
