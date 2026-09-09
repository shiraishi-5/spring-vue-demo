<script setup>
import ManageStep from '@/components/form/ManageStep.vue'
import BaseButton from '@/components/parts/BaseButton.vue'
import PageTitle from '@/components/parts/PageTitle.vue'
import { onMounted, reactive, ref } from 'vue'
import { createUserApi, getQualMasterApi } from '@/api/api.js'
import BaseModal from '@/components/parts/BaseModal.vue'

const MAX_LENGTH_NAME = 20
const MIN_LENGTH_PASSWORD = 8

const isShowModal = ref(false)

const createInitialForm = () => ({
  name: '',
  email: '',
  password: '',
  profile: {
    nickname: '',
    birthday: '',
  },
  qualifications: [],
})

const form = reactive(createInitialForm())

const initialErrors = {
  name: '',
  email: '',
  password: '',
  nickname: '',
  birthday: '',
  qualifications: '',
}

const errors = reactive({ ...initialErrors })

//資格マスタ保存用
const options = ref([])

onMounted(async () => {
  const masters = await getMaster()
  options.value = masters?.map((master) => ({
    value: master.id,
    label: master.qualificationName,
  }))
})

const isValid = () => {
  resetErrors()

  validName()
  validEmail()
  validPassword()

  return !hasError()
}

const validName = () => {
  if (!form.name) {
    errors.name = '名前は必須です'
    return
  }

  if (form.name.length > MAX_LENGTH_NAME) {
    errors.name = `名前は${MAX_LENGTH_NAME}文字以下にしてください`
    return
  }
}

const validEmail = () => {
  if (!form.email) {
    errors.email = 'メールは必須です'
    return
  }
}

const validPassword = () => {
  if (!form.password) {
    errors.password = 'パスワードは必須です'
    return
  }

  if (form.password.length < MIN_LENGTH_PASSWORD) {
    errors.password = `パスワードは${MIN_LENGTH_PASSWORD}文字以上にしてください`
    return
  }
}

//エラーがあるか
const hasError = () => {
  return Object.values(errors).some(Boolean)
}

//エラー初期化
const resetErrors = () => {
  Object.assign(errors, initialErrors)
}

//ユーザー登録
const postUser = async () => {
  try {
    if (!isValid()) return
    await createUserApi(form)
    isShowModal.value = true
    resetForm()
  } catch (error) {
    if (error.response?.status === 400) {
      const apiValidateErrors = error.response?.data?.errors

      if (apiValidateErrors) {
        Object.keys(apiValidateErrors).forEach((key) => {
          errors[key] = apiValidateErrors[key]
        })
      }
    } else {
      console.log(error)
    }
  }
}

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
  form.qualifications.push(qualification)
}

//資格情報削除
const removeQualification = (id) => {
  form.qualifications = form.qualifications.filter(
    (qualification) => qualification.qualificationId !== id,
  )
}

//フォーム初期化用
const resetForm = () => {
  Object.assign(form, createInitialForm())
}

const closeModal = () => {
  isShowModal.value = false
}
</script>

<template>
  <div id="create-user">
    <PageTitle title="ユーザー登録"></PageTitle>
    <ManageStep
      :form="form"
      :errors="errors"
      :options="options"
      @add-qualification="addQualification"
      @remove-qualification="removeQualification"
    ></ManageStep>
    <div class="btn-area">
      <BaseButton text="登録" size="xl" @click="postUser"></BaseButton>
    </div>
  </div>

  <BaseModal :is-show="isShowModal" @close="closeModal">
    <h3>INFORMATION</h3>
    <p>登録しました</p>
  </BaseModal>
</template>

<style scoped>
#create-user {
  display: flex;
  flex-direction: column;

  justify-content: center;
  align-items: center;
}
.btn-area {
  margin-top: 16px;
}
</style>
