import { defineStore } from 'pinia'

export const useUserSearchStore = defineStore('userSearch', {
  state: () => ({
    keyword: '',
    page: 0,
    filterType: '',
    qualificationIds: [],
  }),
})
