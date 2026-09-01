<script setup>
import { ref, reactive, onMounted } from 'vue'
import BaseButton from '../parts/BaseButton.vue'
import BaseInput from '../parts/BaseInput.vue'
import BaseSelect from '../parts/BaseSelect.vue'
import FormField from '../parts/FormField.vue'
import BaseStep from './BaseStep.vue'
import Error from '../parts/Error.vue'

const props = defineProps({
  form: Object,
  options: Array,
})

const emit = defineEmits(['add-qualification', 'remove-qualification'])

const qualForm = reactive({
  id: '',
  date: '',
})

const error = ref('')

const addQualification = () => {
  error.value = ''
  if (!validate()) {
    error.value = '未入力の項目があります'
    return
  }

  const exists = props.form.qualifications.some(
    (qualification) => qualification.qualificationId == qualForm.id,
  )

  if (exists) {
    error.value = '既に追加している資格です'
    return
  }

  //optionsから自分のidと同じoptionを取得する
  const option = props.options.find((item) => item.value == qualForm.id)

  emit('add-qualification', {
    qualificationId: qualForm.id,
    name: option?.label,
    acquisitionDate: qualForm.date,
  })

  qualForm.id = ''
  qualForm.date = ''
}

const validate = () => {
  return Object.values(qualForm).every((value) => value)
}
</script>

<template>
  <BaseStep>
    <FormField id="qualificatinon" label="資格名">
      <BaseSelect id="qualification" v-model="qualForm.id" :options="options"></BaseSelect>
    </FormField>

    <FormField id="acquisition-date" label="取得日">
      <BaseInput id="acquisition-date" type="date" v-model="qualForm.date"></BaseInput>
    </FormField>

    <Error :error="error"></Error>

    <BaseButton text="追加" @click="addQualification"></BaseButton>

    <div v-if="props.form.qualifications.length >= 1" id="show-qualifications-area">
      <h4>資格一覧</h4>

      <div v-for="qualification in props.form.qualifications" :key="qualification.qualificationId">
        {{ qualification.name }} ({{ qualification.acquisitionDate }})
        <BaseButton
          text="X"
          @click="emit('remove-qualification', qualification.qualificationId)"
        ></BaseButton>
      </div>
    </div>
  </BaseStep>
</template>

<style scoped>
#show-qualifications-area {
  margin-top: 16px;
}

h4 {
  margin: 0;
}
</style>
