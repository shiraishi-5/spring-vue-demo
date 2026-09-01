<script setup>
import BaseInput from '../parts/BaseInput.vue'
import Error from '../parts/Error.vue'
import FormField from '../parts/FormField.vue'
import BaseStep from './BaseStep.vue'

defineProps({
  form: Object,
  errors: Object,
})
</script>

<template>
  <BaseStep>
    <FormField id="name" label="ユーザー名">
      <BaseInput id="name" v-model="form.name"></BaseInput>
      <Error :error="errors.name"></Error>
    </FormField>

    <FormField id="email" label="メールアドレス">
      <BaseInput id="email" type="email" v-model="form.email"></BaseInput>
      <Error :error="errors.email"></Error>
    </FormField>

    <FormField id="password" label="パスワード">
      <BaseInput id="password" type="password" v-model="form.password"></BaseInput>
      <Error :error="errors.password"></Error>
    </FormField>
  </BaseStep>
</template>

<style scoped>
.step {
  display: flex;
  flex-direction: column;
  margin-bottom: 16px;
}
</style>
