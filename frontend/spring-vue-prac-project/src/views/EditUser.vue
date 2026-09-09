<script setup>
import { getUserApi, getQualMasterApi, updateUserApi } from '@/api/api'
import Step1 from '@/components/form/Step1.vue'
import Step2 from '@/components/form/Step2.vue'
import Step3 from '@/components/form/Step3.vue'
import BaseButton from '@/components/parts/BaseButton.vue'
import BaseModal from '@/components/parts/BaseModal.vue'
import { printLog } from '@/utils/debug'
import { onMounted, ref, reactive } from 'vue'
import { useRoute } from 'vue-router'

const MAX_LENGTH_NAME = 20
const MIN_LENGTH_PASSWORD = 8

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
const options = ref([])
const isShowModal = ref(false)

onMounted(async () => {
  const masters = await getMaster()
  options.value = masters?.map((master) => ({
    value: master.id,
    label: master.qualificationName,
  }))

  try {
    const id = Number(route.params.id)
    const res = await getUserApi(id)
    user.value = res?.data?.data[0]

    Object.assign(editForm, user.value)
  } catch (error) {
    printLog(error)
  }
})

//資格マスタから取得
const getMaster = async () => {
  try {
    const res = await getQualMasterApi()
    return res.data.data
  } catch (error) {
    console.log(error)
  }
}

//資格情報追加
const addQualification = (qualification) => {
  editForm.qualifications.push(qualification)
}

//資格情報削除
const removeQualification = (id) => {
  editForm.qualifications = editForm.qualifications.filter(
    (qualification) => qualification.qualificationId !== id,
  )
}

//エラーがあるか
const hasError = () => {
  return Object.values(errors).some(Boolean)
}

//エラー初期化
const resetErrors = () => {
  Object.assign(errors, initialErrors)
}

const isValid = () => {
  resetErrors()

  validName()
  validEmail()
  validPassword()

  return !hasError()
}

const validName = () => {
  if (!editForm.name) {
    errors.name = '名前は必須です'
    return
  }

  if (editForm.name.length > MAX_LENGTH_NAME) {
    errors.name = `名前は${MAX_LENGTH_NAME}文字以下にしてください`
    return
  }
}

const validEmail = () => {
  if (!editForm.email) {
    errors.email = 'メールは必須です'
    return
  }
}

const validPassword = () => {
  if (!editForm.password) {
    return
  }

  if (editForm.password.length < MIN_LENGTH_PASSWORD) {
    errors.password = `パスワードは${MIN_LENGTH_PASSWORD}文字以上にしてください`
    return
  }
}

// ユーザー更新
const updateUser = async () => {
  try {
    const res = await updateUserApi(route.params.id, editForm)
    console.log(editForm)
    isShowModal.value = true
    editForm.password = ''
  } catch (error) {
    console.log(error)
  }
}

const closeModal = () => {
  isShowModal.value = false
}
</script>

<template>
  <div id="edit-user">
    <h2>ユーザー編集　ID:{{ user?.id }}</h2>
    <div class="card">
      <Step1 :form="editForm" :errors="errors"></Step1>
      <Step2 :form="editForm" :errors="errors"></Step2>
      <Step3
        :form="editForm"
        :options="options"
        :errors="errors"
        @add-qualification="addQualification"
        @remove-qualification="removeQualification"
      ></Step3>
      <div class="btn-area">
        <BaseButton text="更新" @click="updateUser"></BaseButton>
      </div>
    </div>
  </div>

  <BaseModal :is-show="isShowModal" @close="closeModal">
    <h3>INFORMATION</h3>
    更新しました
  </BaseModal>
</template>

<style scoped>
#edit-user {
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

h3 {
  margin: 0;
  margin-bottom: 8px;
}
</style>
