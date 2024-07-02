import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import BoardWrite from '@/views/BoardWrite.vue'
import BoardRead from '@/views/BoardRead.vue'
import BoardEdit from '@/views/BoardEdit.vue'
import LoginView from '@/views/LoginView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/write',
      name: 'BoardWrite.ts',
      component: BoardWrite,
    },
    {
      path: '/board/:boardId',
      name: 'BoardRead',
      component: BoardRead,
      props: true,
    },
    {
      path: '/edit/:boardId',
      name: 'BoardEdit',
      component: BoardEdit,
      props: true,
    },
  ],
})

export default router
