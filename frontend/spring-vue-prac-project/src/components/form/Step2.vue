<script setup>
import BaseInput from '../parts/BaseInput.vue'
import FormField from '../parts/FormField.vue'
import BaseStep from './BaseStep.vue'
import Error from '../parts/Error.vue'

defineProps({
  form: Object,
  errors: Object,
})
</script>

<template>
  <BaseStep>
    <FormField id="nickname" label="ニックネーム">
      <BaseInput id="nickname" v-model="form.profile.nickname"></BaseInput>
      <Error :error="errors.nickname"></Error>
    </FormField>

    <FormField id="birthday" label="誕生日">
      <BaseInput id="birthday" type="date" v-model="form.profile.birthday"></BaseInput>
      <Error :error="errors.birthday"></Error>
    </FormField>
  </BaseStep>
</template>

<style scoped></style>
