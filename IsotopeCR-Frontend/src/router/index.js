import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Home from '@/components/Home'
import Login from '@/components/Login'
import CreateCustomerProfile from '@/components/CreateCustomerProfile'
import CompanyProfile from '@/components/CompanyProfile.vue'
Vue.use(Router)

export default new Router({
  mode: "history",
  routes: [
    {
      path: '/hello',
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
      path: '/',
      name: 'CreateCustomerProfile',
      component: CreateCustomerProfile
    },
    {
      path: "/company",
      name: "Company",
      component: CompanyProfile
    }
  ]
})
