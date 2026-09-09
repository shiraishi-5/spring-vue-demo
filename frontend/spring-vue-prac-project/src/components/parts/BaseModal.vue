<script setup>
import BaseButton from './BaseButton.vue'

defineProps({
  isShow: Boolean,
})

const emit = defineEmits(['close'])
</script>

<template>
  <div v-if="isShow" class="modal-overlay" @click="emit('close')">
    <div class="modal" @click.stop>
      <slot />

      <div class="btn-area">
        <BaseButton
          @click="emit('close')"
          priority="secondary"
          size="large"
          text="閉じる"
        ></BaseButton>
      </div>
    </div>
  </div>
</template>

<style scoped>
.modal-overlay {
  position: fixed;
  inset: 0;

  background: rgba(0, 0, 0, 0.5);

  display: flex;
  justify-content: center;
  align-items: center;
}

.modal {
  display: flex;
  flex-direction: column;

  background: white;

  width: 200px;
  padding: 24px;
  border-radius: 8px;
}

.btn-area {
  display: flex;
  justify-content: right;
}
</style>
