import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
<<<<<<< HEAD
import Login from '@/components/Login'
import CompanyProfile from '@/components/CompanyProfile.vue'
=======
import Login from '@/components/Login.vue'
>>>>>>> e3291bd973793055e7c143dc2457e2f67a177ba4

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
      path: "/login",
      name: "Login",
      component: Login
    },
    {
      path: "/company",
      name: "Company",
      component: CompanyProfile
    }
  ]
})
