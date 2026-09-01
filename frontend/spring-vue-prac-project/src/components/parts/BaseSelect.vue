<script setup>
defineProps({
  modelValue: {
    type: [String, Number],
    default: '',
  },

  id: {
    type: String,
    default: '',
  },

  options: {
    type: Array,
    default: () => [],
  },
})

const emit = defineEmits(['update:modelValue'])

const onChange = (event) => {
  emit('update:modelValue', event.target.value)
}
</script>

<template>
  <select :id="id" :value="modelValue" class="base-select" @change="onChange">
    <option value="">選択してください</option>

    <option v-for="option in options" :key="option.value" :value="option.value">
      {{ option.label }}
    </option>
  </select>
</template>

<style scoped>
.base-select {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.base-select:focus {
  outline: none;
  border-color: #409eff;
}
</style>
