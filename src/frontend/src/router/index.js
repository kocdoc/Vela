import { createRouter, createWebHistory } from 'vue-router'
import FriendsView from '@/views/FriendsView'
import ProfileView from '@/views/ProfileView'
import ProjectView from '@/views/ProjectView'
import TaskManagerView from '@/views/TaskManagerView'
import CalendarView from '@/views/CalendarView'
import LoginView from '@/views/LoginView'
import RegisterView from '@/views/RegisterView'
import NewProjectView from '@/views/NewProjectView'
import EditProjectView from '@/views/EditProjectView'
import CalendarTest from '@/views/CalendarTest'

const routes = [
  {
    path: '/',
    name: 'home',
    component: TaskManagerView
  },
  {
    path: '/calendar',
    name: 'calendar',
    component: CalendarView
  },
  {
    path: '/friends',
    name: 'friends',
    component: FriendsView
  },
  {
    path: '/profile',
    name: 'profile',
    component: ProfileView
  },
  {
    path: '/projects',
    name: 'projects',
    component: ProjectView
  },
  {
    path: '/newproject',
    name: 'newproject',
    component: NewProjectView
  },
  {
    path: '/editproject',
    name: 'editproject',
    component: EditProjectView
  },
  {
    path: '/taskmanager',
    name: 'taskmanager',
    component: TaskManagerView
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/calendarTest',
    name: 'calendarTest',
    component: CalendarTest
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
