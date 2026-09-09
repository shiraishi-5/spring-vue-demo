<script setup>
import { ref, reactive, onMounted } from 'vue'
import BaseButton from '../parts/BaseButton.vue'
import BaseInput from '../parts/BaseInput.vue'
import BaseSelect from '../parts/BaseSelect.vue'
import FormField from '../parts/FormField.vue'
import BaseStep from './BaseStep.vue'
import Error from '../parts/Error.vue'
import BaseTable from '../parts/BaseTable.vue'

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
    qualificationName: option?.label,
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

    <div class="btn-area">
      <BaseButton text="追加" priority="tertiary" @click="addQualification"></BaseButton>
    </div>

    <div v-if="props.form.qualifications.length >= 1" id="show-qualifications-area">
      <h4>取得資格一覧</h4>

      <BaseTable>
        <template #head>
          <tr>
            <th>資格名</th>
            <th>取得日</th>
            <th>削除</th>
          </tr>
        </template>

        <template #body>
          <tr
            v-for="qualification in props.form.qualifications"
            :key="qualification.qualificationId"
          >
            <td>{{ qualification.qualificationName }}</td>
            <td>{{ qualification.acquisitionDate }}</td>
            <td>
              <BaseButton
                text="X"
                priority="secondary"
                size="small"
                @click="emit('remove-qualification', qualification.qualificationId)"
              ></BaseButton>
            </td>
          </tr>
        </template>
      </BaseTable>
    </div>
  </BaseStep>
</template>

<style scoped>
#show-qualifications-area {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  margin-top: 8px;
  padding: 8px;
}

#show-qualifications-area h4 {
  margin-bottom: 8px;
}

.btn-area {
  display: flex;
  justify-content: right;
}

h4 {
  margin: 0;
}
</style>
