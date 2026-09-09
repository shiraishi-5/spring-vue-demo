import CreateUser from '@/views/CreateUser.vue'
import EditUser from '@/views/EditUser.vue'
import Index from '@/views/Index.vue'
import Login from '@/views/Login.vue'
import ShowUser from '@/views/ShowUser.vue'
import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/users/create',
      component: CreateUser,
      meta: {
        requireAuth: false,
      },
    },
    {
      path: '/users',
      component: Index,
      meta: {
        requireAuth: true,
      },
    },
    {
      path: '/users/:id',
      name: 'ShowUser',
      component: ShowUser,
      meta: {
        requireAuth: true,
      },
    },
    {
      path: '/users/:id/edit',
      name: 'EditUser',
      component: EditUser,
      meta: {
        requireAuth: true,
      },
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
      meta: {
        requireAuth: false,
      },
    },
  ],
})

router.beforeEach((to) => {
  const authStore = useAuthStore()

  if (to.meta.requireAuth && !authStore.isLogin) {
    return '/login'
  }
})

export default router
