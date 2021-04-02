import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Home from '@/components/Home'
import Login from '@/components/Login'

import Appointment from '@/components/Appointment'
import PastAppointment from '@/components/PastAppointment'
import BookAppointment from '@/components/BookAppointment.vue'
import FutureAppointment from '@/components/FutureAppointment'
import CancelAppointment from '@/components/CancelAppointment'

import CreateCustomerProfile from '@/components/CreateCustomerProfile'
import CompanyProfile from '@/components/CompanyProfile.vue'

import PastAppointmentCustomer from '@/components/PastAppointmentCustomer.vue'
import PastAppointmentVehicle from '@/components/PastAppointmentVehicle.vue'
import FutureAppointmentCustomer from '@/components/FutureAppointmentCustomer.vue'
import FutureAppointmentVehicle from '@/components/FutureAppointmentVehicle.vue'

import CreateAdminProfile from '@/components/CreateAdminProfile'
import CreateTechProfile from '@/components/CreateTechProfile'
import Vehicle from '@/components/Vehicle.vue'
import Registration from '@/components/RegistrationNav.vue'
import Profile from '@/components/AllProfile.vue'

Vue.use(Router)

export default new Router({
  mode: "history",
  routes: [
    {
      path: '/',
      name: 'home',
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
    },
    {
      path: "/vehicle",
      name: "Vehicle",
      component: Vehicle
    },
    {
      path: "/signup",
      name: "Registration",
      component: Registration
    },
    {
      path: "/profileInfo",
      name: "ProfileInfo",
      component: Profile
    }
  ]
})
