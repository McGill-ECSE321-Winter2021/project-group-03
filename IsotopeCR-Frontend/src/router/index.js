import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Home from '@/components/Home'
import Login from '@/components/Login'

import Appointment from '@/components/Appointment'
import PastAppointment from '@/components/PastAppointment'
import BookAppointment from '@/components/BookAppointment'
import FutureAppointment from '@/components/FutureAppointment'
import CancelAppointment from '@/components/CancelAppointment'

import CreateCustomerProfile from '@/components/CreateCustomerProfile'
import CompanyProfile from '@/components/CompanyProfile.vue'

import PastAppointmentCustomer from '@/components/PastAppointmentCustomer'
import PastAppointmentVehicle from '@/components/PastAppointmentVehicle'
import FutureAppointmentCustomer from '@/components/FutureAppointmentCustomer'
import FutureAppointmentVehicle from '@/components/FutureAppointmentVehicle'
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
      path:"/pastappointment/customer",
      name: "PastAppointmentCustomer",
      component: PastAppointmentCustomer
    },
    {
      path:"/pastappointment/vehicle",
      name: "PastAppointmentVehicle",
      component: PastAppointmentVehicle
    },
    {
      path:"/futureappointment/customer",
      name: "FutureAppointmentCustomer",
      component: FutureAppointmentCustomer
    },
    {
      path:"/futureappointment/vehicle",
      name: "FutureAppointmentVehicle",
      component: FutureAppointmentVehicle
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
    {
      path:'/',
      name: 'CreateCustomerProfile',
      component: CreateCustomerProfile
    },
    {
      path: "/company",
      name: "Company",
      component: CompanyProfile
    },

  
  ]
})
