<script setup>
import { ref, reactive } from 'vue'
import Step1 from '@/components/form/Step1.vue'
import Step2 from '@/components/form/Step2.vue'
import Step3 from '@/components/form/Step3.vue'

const props = defineProps({
  form: Object,
  errors: Object,
  options: Array,
})

const emit = defineEmits(['add-qualification', 'remove-qualification'])

const currentStep = ref(1)

const steps = [
  {
    id: 1,
    name: '基本情報',
  },
  {
    id: 2,
    name: 'プロフィール',
  },
  {
    id: 3,
    name: '資格情報',
  },
]

const changeStep = (stepNum) => {
  currentStep.value = stepNum
}

//さらに親に渡す　追加
const eventAddQualification = (qualification) => {
  emit('add-qualification', qualification)
}

//さらに親に渡す 削除
const eventRemoveQualification = (id) => {
  emit('remove-qualification', id)
}
</script>

<template>
  <div class="card">
    <div class="tab">
      <h3
        v-for="step in steps"
        :key="step.id"
        :class="{ active: currentStep === step.id }"
        @click="changeStep(step.id)"
      >
        {{ step.name }}
      </h3>
    </div>

    <Step1 v-if="currentStep === 1" :form="form" :errors="errors"></Step1>
    <Step2 v-if="currentStep === 2" :form="form" :errors="errors"></Step2>
    <Step3
      v-if="currentStep === 3"
      :form="form"
      :options="options"
      @add-qualification="eventAddQualification"
      @remove-qualification="eventRemoveQualification"
    ></Step3>
  </div>
</template>

<style scoped>
.card {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  border: none;
  border-radius: 16px;

  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);

  padding: 8px;
}

.tab {
  display: flex;
  gap: 8px;
  padding: 0px;
  margin: 0;

  margin-bottom: 8px;
}

h3 {
  margin: 0;
  padding: 8px;
}

.active {
  color: white;
  background: gray;

  border-radius: 8px;
}
</style>
