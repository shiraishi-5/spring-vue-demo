<script setup>
import { reactive, ref } from 'vue'
import BaseButton from '@/components/parts/BaseButton.vue'
import BaseInput from '@/components/parts/BaseInput.vue'
import Error from '@/components/parts/Error.vue'
import FormField from '@/components/parts/FormField.vue'
import { getMeApi, loginApi } from '@/api/api'
import { useAuthStore } from '@/stores/auth'
import { useRouter } from 'vue-router'

const authStore = useAuthStore()
const router = useRouter()

const form = reactive({
  email: '',
  password: '',
})

const apiError = ref('')

const login = async () => {
  try {
    apiError.value = ''

    const response = await loginApi(form)

    authStore.user = response.data

    router.push('/users')
  } catch (error) {
    if (error?.response?.status === 401) {
      apiError.value = error?.response?.data?.message
    }
    console.log(error)
  }
}
</script>

<template>
  <div id="login">
    <h3>ログイン</h3>
    <div class="form-area">
      <FormField id="email" label="メールアドレス">
        <BaseInput id="email" type="email" v-model="form.email"></BaseInput>
      </FormField>

      <FormField id="password" label="パスワード">
        <BaseInput id="password" type="password" v-model="form.password"></BaseInput>
      </FormField>

      <Error :error="apiError"></Error>

      <BaseButton text="ログイン" size="xl" @click="login"></BaseButton>
    </div>
  </div>
</template>

<style scoped>
#login {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.form-area {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 16px;

  border-radius: 16px;

  background-color: rgb(238, 238, 238);

  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}
</style>
