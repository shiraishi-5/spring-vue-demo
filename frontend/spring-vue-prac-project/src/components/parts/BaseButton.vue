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
  priority: {
    type: String,
    default: 'primary',
  },
})
</script>

<template>
  <button :type="type" :disabled="disabled" :class="['base-button', `base-button--${priority}`]">
    {{ text }}
  </button>
</template>

<style scoped>
.base-button {
  padding: 8px;

  font-weight: bold;

  border: none;
  border-radius: 8px;
}

.base-button--primary {
  color: white;
  background-color: rgb(0, 166, 255);
}

.base-button--secondary {
  color: white;
  background-color: red;
}

.base-button--tertiary {
  color: black;
  background-color: rgb(255, 231, 46);
}

.base-button--primary:hover {
  background-color: rgb(0, 141, 217);
  transition: 0.3s;
}

.base-button--secondary:hover {
  background-color: rgb(210, 1, 1);
  transition: 0.3s;
}

.base-button--tertiary:hover {
  background-color: rgb(224, 203, 41);
  transition: 0.3s;
}

button:disabled {
  background-color: gray;
}

button:disabled:hover {
  background-color: gray;
}
</style>
