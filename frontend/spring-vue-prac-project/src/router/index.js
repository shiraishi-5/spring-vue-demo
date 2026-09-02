import CreateUser from '@/views/CreateUser.vue'
import Index from '@/views/Index.vue'
import ShowUser from '@/views/ShowUser.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/user/create',
      component: CreateUser,
    },
    {
      path: '/users',
      component: Index,
    },
    {
      path: '/users/:id',
      name: 'ShowUser',
      component: ShowUser,
    },
  ],
})

export default router
