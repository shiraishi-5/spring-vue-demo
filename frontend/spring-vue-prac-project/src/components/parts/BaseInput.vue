<script setup>
defineProps({
  modelValue: {
    type: String,
    default: '',
  },

  id: {
    type: String,
    default: '',
  },

  type: {
    type: String,
    default: 'text',
  },

  placeholder: {
    type: String,
    default: '',
  },
})

const emit = defineEmits(['update:modelValue'])

const onInput = (event) => {
  emit('update:modelValue', event.target.value)
}
</script>

<template>
  <input
    :id="id"
    :type="type"
    :value="modelValue"
    :placeholder="placeholder"
    class="base-input"
    @input="onInput"
  />
</template>

<style scoped>
.base-input {
  width: 200px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.base-input:focus {
  outline: none;
  border-color: #409eff;
}
</style>
