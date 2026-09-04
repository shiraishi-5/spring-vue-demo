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

  size: {
    type: String,
    default: 'medium',
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
    :class="['base-input', `base-input--${size}`]"
    @input="onInput"
  />
</template>

<style scoped>
.base-input {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.base-input--min {
  width: 50px;
}

.base-input--small {
  width: 100px;
}

.base-input--medium {
  width: 200px;
}

.base-input--large {
  width: 300px;
}

.base-input:focus {
  outline: none;
  border-color: #409eff;
}
</style>
