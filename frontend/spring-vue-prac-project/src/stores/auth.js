import { getMeApi } from '@/api/api'
import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null,
  }),

  getters: {
    isLogin(state) {
      return state.user != null
    },
  },

  actions: {
    async fetchMe() {
      try {
        const res = await getMeApi()
        this.user = res.data
        console.log(this.user)
      } catch (error) {
        this.user = null
      }
    },
  },
})
