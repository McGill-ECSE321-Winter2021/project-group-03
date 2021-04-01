import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Login from '@/components/Login'
import Appointment from '@/components/Appointment'
import PastAppointment from '@/components/PastAppointment'
import BookAppointment from '@/components/BookAppointment'
import FutureAppointment from '@/components/FutureAppointment'
import CancelAppointment from '@/components/CancelAppointment'


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
      path:"/appointment",
      name:"Appointment",
      component: Appointment
    },
    {
      path:"/pastappointment",
      name:"PastAppointment",
      component: PastAppointment
    },
    {
      path:"/bookappointment",
      name:"BookAppointment",
      component: BookAppointment
    },
    {
      path:"/futureappointment",
      name:"FutureAppointment",
      component: FutureAppointment
    },
    {
      path:"/cancelappointment",
      name:"CancelAppointment",
      component: CancelAppointment
    },

  ]
})
