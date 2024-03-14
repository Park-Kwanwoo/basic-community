import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import BoardWrite from "@/views/BoardWrite.vue";
import BoardRead from "@/views/BoardRead.vue";
import BoardEdit from "@/views/BoardEdit.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: HomeView
        },
        {
            path: '/write',
            name: 'BoardWrite',
            component: BoardWrite
        },
        {
            path: '/read/:boardId',
            name: 'BoardRead',
            component: BoardRead,
            props: true
        },
        {
            path: '/edit/:boardId',
            name: 'BoardEdit',
            component: BoardEdit,
            props: true
        },
    ]
})

export default router
