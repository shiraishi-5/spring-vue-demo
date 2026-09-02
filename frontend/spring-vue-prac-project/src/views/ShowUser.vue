<script setup>
import { getUserApi } from '@/api/api'
import PageTitle from '@/components/parts/PageTitle.vue'
import SectionArea from '@/components/parts/SectionArea.vue'
import { printLog } from '@/utils/debug'
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const user = ref(null)

onMounted(async () => {
  try {
    const id = Number(route.params.id)
    const res = await getUserApi(id)
    user.value = res?.data?.data[0]
  } catch (error) {
    printLog(error)
  }
})
</script>

<template>
  <PageTitle title="ユーザー詳細"></PageTitle>

  <div class="card">
    <SectionArea title="ID">
      <p>{{ user?.id }}</p>
    </SectionArea>

    <SectionArea title="ユーザー名">
      <p>{{ user?.name }}</p>
    </SectionArea>

    <SectionArea title="メールアドレス">
      <p>{{ user?.email }}</p>
    </SectionArea>

    <SectionArea title="ニックネーム">
      <p>{{ user?.profile?.nickname }}</p>
    </SectionArea>

    <SectionArea title="誕生日">
      <p>{{ user?.profile?.birthday }}</p>
    </SectionArea>

    <SectionArea title="取得資格">
      <ul v-if="user?.qualifications.length !== 0">
        <li v-for="qual in user?.qualifications">
          {{ qual.qualificationName }}　取得日：{{ qual.acquisitionDate }}
        </li>
      </ul>
      <p v-else>なし</p>
    </SectionArea>
  </div>
</template>

<style scoped>
.card {
  padding: 16px;
  margin: 16px;

  background-color: rgb(250, 250, 250);

  border-radius: 16px;

  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.103);
}

ul {
  padding-left: 16px;
}
</style>
