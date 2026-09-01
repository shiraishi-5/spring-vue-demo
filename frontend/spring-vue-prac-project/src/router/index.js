import CreateUser from '@/views/CreateUser.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/user/create',
      component: CreateUser,
    },
  ],
})

export default router
