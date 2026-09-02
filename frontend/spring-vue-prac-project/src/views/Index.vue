<script setup>
import { getUsersApi } from '@/api/api'
import BaseButton from '@/components/parts/BaseButton.vue'
import BaseInput from '@/components/parts/BaseInput.vue'
import BaseTable from '@/components/parts/BaseTable.vue'
import PageTitle from '@/components/parts/PageTitle.vue'
import Pagination from '@/components/parts/Pagination.vue'
import { printLog } from '@/utils/debug'
import { onMounted, reactive, ref } from 'vue'
import { useUserSearchStore } from '@/stores/userSearch'

const searchStore = useUserSearchStore()

const users = ref([])
const pageInfo = reactive({
  currentPage: 0,
  totalPages: 0,
})

const keyword = ref('')

onMounted(async () => {
  setUsers(searchStore.page, searchStore.keyword)
})

const setUsers = async (page = 0, keyword = '') => {
  try {
    const res = await getUsersApi({
      page: page,
      keyword: keyword,
    })
    users.value = res?.data?.data
    Object.assign(pageInfo, res.data.pageInfo)
  } catch (error) {
    printLog(error)
  }
}

//ページ変更
const changePage = async (page) => {
  searchStore.page = page
  await setUsers(page, keyword.value)
}

const search = async () => {
  searchStore.page = 0
  await setUsers(searchStore.page, searchStore.keyword)
}

const resetSearchStore = async () => {
  searchStore.keyword = ''
  searchStore.page = 0
  await setUsers(0, '')
}
</script>

<template>
  <PageTitle title="ユーザー一覧"></PageTitle>
  <div id="index">
    <div id="search-area">
      <h4>検索欄</h4>
      <BaseInput
        placeholder="ユーザー名またはメールアドレスを入力して下さい"
        v-model="searchStore.keyword"
      ></BaseInput>
      <BaseButton text="検索" @click="search"></BaseButton>
      <BaseButton text="リセット" @click="resetSearchStore"></BaseButton>
    </div>

    <BaseTable>
      <template #head>
        <tr>
          <td>ID</td>
          <td>ユーザー名</td>
          <td>メールアドレス</td>
          <td>操作</td>
        </tr>
      </template>

      <template #body>
        <tr v-for="user in users" :key="user.id">
          <td>
            <RouterLink
              :to="{
                name: 'ShowUser',
                params: { id: user.id },
              }"
              >{{ user.id }}
            </RouterLink>
          </td>
          <td>{{ user.name }}</td>
          <td>{{ user.email }}</td>
          <td><button>編集</button></td>
        </tr>
      </template>
    </BaseTable>

    <Pagination
      :current-page="pageInfo.currentPage"
      :total-pages="pageInfo.totalPages"
      @change-page="changePage"
    ></Pagination>
  </div>
</template>

<style scoped>
#index {
  display: flex;
  flex-direction: column;

  justify-content: center;
  align-items: center;
}

#search-area {
  display: flex;
  align-items: center;

  height: 40px;

  gap: 8px;

  margin: 16px 0;
}
</style>
