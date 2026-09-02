<script setup>
const props = defineProps({
  text: {
    type: String,
    default: 'ボタン',
  },
  type: {
    type: String,
    default: 'button',
  },
  disabled: {
    type: Boolean,
    default: false,
  },
})
</script>

<template>
  <button :type="type" :disabled="disabled">{{ text }}</button>
</template>

<style scoped>
button {
  padding: 8px;

  font-weight: bold;

  color: white;
  background-color: rgb(0, 166, 255);

  border: none;
  border-radius: 8px;
}

button:hover {
  background-color: rgb(0, 141, 217);
  transition: 0.3s;
}

button:disabled {
  background-color: gray;
}
</style>
