<script setup>
import { getQualMasterApi, getUsersApi } from '@/api/api'
import BaseButton from '@/components/parts/BaseButton.vue'
import BaseInput from '@/components/parts/BaseInput.vue'
import BaseTable from '@/components/parts/BaseTable.vue'
import PageTitle from '@/components/parts/PageTitle.vue'
import Pagination from '@/components/parts/Pagination.vue'
import { printLog } from '@/utils/debug'
import { onMounted, reactive, ref, computed } from 'vue'
import { useUserSearchStore } from '@/stores/userSearch'
import FormField from '@/components/parts/FormField.vue'
import Error from '@/components/parts/Error.vue'
import { useRouter } from 'vue-router'

const searchStore = useUserSearchStore()
const router = useRouter()

const users = ref([])
const pageInfo = reactive({
  currentPage: 0,
  totalPages: 0,
})

const masters = ref([])

const isShowFilter = ref(false)

onMounted(async () => {
  //マスタ取得
  try {
    const res = await getQualMasterApi()
    masters.value = res?.data?.data
  } catch (error) {
    printLog(error)
  }

  await setUsers(
    searchStore.page,
    searchStore.keyword,
    searchStore.filterType,
    searchStore.qualificationIds,
  )
})

//ユーザー取得して、セット
const setUsers = async (page, keyword, filterType, qualificationIds) => {
  try {
    const res = await getUsersApi({
      page: page,
      keyword: keyword,
      filterType: filterType,
      qualificationIds: qualificationIds,
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
  await setUsers(page, searchStore.keyword, searchStore.filterType, searchStore.qualificationIds)
}

//検索
const search = async () => {
  searchStore.page = 0
  await setUsers(
    searchStore.page,
    searchStore.keyword,
    searchStore.filterType,
    searchStore.qualificationIds,
  )
  printLog(users.value)
}

//storeの初期化
const resetSearchStore = async () => {
  searchStore.keyword = ''
  searchStore.page = 0
  searchStore.filterType = ''
  searchStore.qualificationIds = []
  await setUsers(0, '')
}

//フィルターエラーの表示
const filterError = computed(() => {
  if (searchStore.filterType && searchStore.qualificationIds.length === 0) {
    return '資格を選択してください'
  }

  if (!searchStore.filterType && searchStore.qualificationIds.length > 0) {
    return '絞り込み方法を選択してください'
  }

  return ''
})

//フィルターエラーがあるか
const hasFilterError = computed(() => {
  return (
    (searchStore.filterType && searchStore.qualificationIds.length === 0) ||
    (!searchStore.filterType && searchStore.qualificationIds.length > 0)
  )
})

//編集画面に遷移
const goEdit = (id) => {
  router.push({
    name: 'EditUser',
    params: { id: id },
  })
}
</script>

<template>
  <PageTitle title="ユーザー一覧"></PageTitle>
  <div id="index">
    <div id="search-area">
      <h4>検索欄</h4>
      <BaseInput
        placeholder="ユーザー名、メール、ニックネームで検索"
        size="large"
        v-model="searchStore.keyword"
      ></BaseInput>
      <BaseButton text="検索" :disabled="hasFilterError" @click="search"></BaseButton>
      <BaseButton text="リセット" priority="secondary" @click="resetSearchStore"></BaseButton>
    </div>

    <div id="filter-area">
      <div id="filter-head">
        <h4>絞り込み条件</h4>

        <div id="show-toggle">
          <p v-if="!isShowFilter" @click="isShowFilter = true">→表示</p>
          <p v-if="isShowFilter" @click="isShowFilter = false">↓非表示</p>
        </div>
      </div>

      <div v-if="isShowFilter">
        <div id="filter-type">
          <p>方法</p>

          <label>
            <input type="radio" value="all" v-model="searchStore.filterType" />
            ALL検索
          </label>

          <label>
            <input type="radio" value="any" v-model="searchStore.filterType" />
            ANY検索
          </label>
        </div>

        <p>資格</p>

        <label v-for="qual in masters" :key="qual.id">
          <input type="checkbox" :value="qual.id" v-model="searchStore.qualificationIds" />
          {{ qual.qualificationName }}
        </label>

        <Error :error="filterError"></Error>
      </div>
    </div>

    <BaseTable>
      <template #head>
        <tr>
          <td>ID</td>
          <td>ユーザー名</td>
          <td>メールアドレス</td>
          <td>ニックネーム</td>
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
          <td>{{ user.profile?.nickname }}</td>
          <td>
            <BaseButton text="編集" priority="tertiary" @click="goEdit(user.id)"></BaseButton>
          </td>
        </tr>
      </template>
    </BaseTable>

    <p v-if="!users.length" style="font-weight: bold">条件に合致するユーザーがいません</p>

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

#filter-area {
  display: flex;
  flex-direction: column;

  background-color: rgb(238, 238, 238);

  border-radius: 16px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);

  width: 400px;

  margin-bottom: 16px;
  padding: 8px;
}

#filter-head {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

#show-toggle p {
  border-bottom: 2px solid black;

  margin: 0;
}

#show-toggle p:hover {
  color: gray;
  border-color: gray;
  transition: 0.3s;
}

#filter-area h4 {
  margin: 0;
}
</style>
