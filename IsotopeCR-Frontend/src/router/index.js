import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Login from '@/components/Login'

import PastAppointment from '@/components/PastAppointment'
import BookAppointment from '@/components/BookAppointment.vue'
import FutureAppointment from '@/components/FutureAppointment'
import CancelAppointment from '@/components/CancelAppointment.vue'

import CreateCustomerProfile from '@/components/CreateCustomerProfile'
import CompanyProfile from '@/components/CompanyProfile.vue'

import PastAppointmentCustomer from '@/components/PastAppointmentCustomer.vue'
import PastAppointmentVehicle from '@/components/PastAppointmentVehicle.vue'
import FutureAppointmentCustomer from '@/components/FutureAppointmentCustomer.vue'
import FutureAppointmentVehicle from '@/components/FutureAppointmentVehicle.vue'
import ChargeAppointment from '@/components/ChargeAppointment.vue'

import CreateAdminProfile from '@/components/CreateAdminProfile'
import CreateTechProfile from '@/components/CreateTechProfile'
import Vehicle from '@/components/Vehicle.vue'

import CreateService from '@/components/CreateService.vue'
import CreateResource from '@/components/CreateResource.vue'

import Profile from '@/components/AllProfile.vue'
import ProfileInfo from '@/components/ProfileInfo.vue'
import TechAndService from '@/components/TechnicianAndService.vue'
import AdminMenu from '@/components/AdminMenu.vue'
import CustomerMenu from '@/components/CustomerMenu.vue'

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
      path: "/pastappointment",
      name: "PastAppointment",
      component: PastAppointment
    },
    {
      path: "/pastappointment/customer",
      name: "PastAppointmentCustomer",
      component: PastAppointmentCustomer
    },
    {
      path: "/pastappointment/vehicle",
      name: "PastAppointmentVehicle",
      component: PastAppointmentVehicle
    },
    {
      path: "/futureappointment/customer",
      name: "FutureAppointmentCustomer",
      component: FutureAppointmentCustomer
    },
    {
      path: "/futureappointment/vehicle",
      name: "FutureAppointmentVehicle",
      component: FutureAppointmentVehicle
    },
    {
      path: "/bookAppointment",
      name: "BookAppointment",
      component: BookAppointment
    },
    {
      path: "/futureappointment",
      name: "FutureAppointment",
      component: FutureAppointment
    },
    {
      path: "/cancelappointment",
      name: "CancelAppointment",
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
      name: "CreateCustomerProfile",
      component: CreateCustomerProfile
    },
    {
      path: "/allProfiles",
      name: "AllProfiles",
      component: Profile
    },
    {
      path: "/createService",
      name: "CreateService",
      component: CreateService
    },
    {
      path: "/profileInfo",
      name: "ProfileInfo",
      component: ProfileInfo
    },
    {
      path: "/createResource",
      name: "CreateResource",
      component: CreateResource
    },
    {
      path: "/techAndService",
      name: "TechAndService",
      component: TechAndService
    },
    {
      path: "/adminMenu",
      name: "AdminMenu",
      component: AdminMenu
    },
    {
      path: "/customerMenu",
      name: "CustomerMenu",
      component: CustomerMenu
    },
    {
      path: "/chargeAppointment",
      name: "ChargeAppointment",
      component: ChargeAppointment
    }
  ]
})
