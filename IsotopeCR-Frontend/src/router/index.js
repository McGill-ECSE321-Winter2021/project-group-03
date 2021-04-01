import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Home from '@/components/Home'
import Login from '@/components/Login'
import CreateCustomerProfile from '@/components/CreateCustomerProfile'
import CompanyProfile from '@/components/CompanyProfile.vue'
import CreateAdminProfile from '@/components/CreateAdminProfile'
import CreateTechProfile from '@/components/CreateTechProfile'
Vue.use(Router)

export default new Router({
  mode: "history",
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/app',
      name: 'Home',
      component: Home
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: "/company",
      name: "Company",
      component: CompanyProfile
    },
    {
      path: '/createCustomerProfile',
      name: 'CreateCustomerProfile',
      component: CreateCustomerProfile
    },
    {
      path: "/createAdminProfile",
      name: "CreateAdminProfile",
      component: CreateAdminProfile
    },
    {
      path: "/createTechProfile",
      name: "CreateTechProfile",
      component: CreateTechProfile
    }
  ]
})
