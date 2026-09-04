<script setup>
import { getUserApi } from '@/api/api'
import Step1 from '@/components/form/Step1.vue'
import Step2 from '@/components/form/Step2.vue'
import Step3 from '@/components/form/Step3.vue'
import BaseButton from '@/components/parts/BaseButton.vue'
import { printLog } from '@/utils/debug'
import { onMounted, ref, reactive } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const user = ref(null)

const initialForm = {
  name: '',
  email: '',
  password: '',
  profile: {
    nickname: '',
    birthday: '',
  },
  qualifications: [],
}

const editForm = reactive({ ...initialForm })

const initialErrors = {
  name: '',
  email: '',
  password: '',
  nickname: '',
  birthday: '',
  qualifications: '',
}

const errors = reactive({ ...initialErrors })

onMounted(async () => {
  try {
    const id = Number(route.params.id)
    const res = await getUserApi(id)
    user.value = res?.data?.data[0]

    Object.assign(editForm, user.value)
  } catch (error) {
    printLog(error)
  }
})
</script>

<template>
  <h2>編集画面です</h2>
  <div class="card">
    <Step1 :form="editForm" :errors="errors"></Step1>
    <Step2 :form="editForm" :errors="errors"></Step2>
    <Step3 :form="editForm" :errors="errors"></Step3>
    <div class="btn-area">
      <BaseButton text="更新"></BaseButton>
    </div>
  </div>
</template>

<style scoped>
template {
  display: flex;
  justify-content: center;
}

.card {
  display: flex;
  flex-direction: column;

  background-color: rgb(238, 238, 238);

  border-radius: 16px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);

  width: 400px;

  margin-bottom: 16px;
  padding: 16px;
}

.btn-area {
  display: flex;
  justify-content: right;
}
</style>
