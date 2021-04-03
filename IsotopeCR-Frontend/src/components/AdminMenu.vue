<template>
  <div>
    <div class="input">
      <b-card title="My profile" class="card">
        <b-button variant="primary" class="button" @click="routeTo('profileInfo')">
          View Profile
        </b-button>
      </b-card>
    </div>
    <div class="input">
      <b-card title="Appointments" class="card">
        <b-button variant="primary" class="button" @click="routeTo('bookAppointment')">
          Book Appointment
        </b-button>
        <b-button variant="primary" class="button" @click="routeTo('cancelAppointment')">
          Cancel Appointment
        </b-button>
      </b-card>
    </div>
    <div class="input">
      <b-card title="Accounts" class="card">
        <b-button variant="primary" class="button" @click="routeTo('createCustomerProfile')">
          Register Customer
        </b-button>
        <b-button variant="primary" class="button" @click="routeTo('createAdminProfile')">
          Register Admin
        </b-button>
        <b-button variant="primary" class="button" @click="routeTo('createTechnician')">
          Register Technician
        </b-button>
      </b-card>
    </div>
    <div class="input">
      <b-card title="RepairShop" class="card">
        <b-button variant="primary" class="button" @click="routeTo('createService')">
          Add Service
        </b-button>
        <b-button variant="primary" class="button" @click="routeTo('deleteService')">
          Remove Service
        </b-button>
        <b-button variant="primary" class="button" @click="routeTo('createResource')">
          Add Resource
        </b-button>
        <b-button variant="primary" class="button"  @click="routeTo('deleteResource')">
          Remove Resource
        </b-button>
      </b-card>
    </div>
  </div>
</template>

<script>
export default {
    name: 'AdminMenu',
    methods: {
        routeTo: function (to) {
        location.href='http://127.0.0.1:8087/'+to;
    }
  }
}
</script>

<style>
.input {
  display: flex;
  justify-content: center;
}
.card {
  margin-top: 20px;
}
.button {
  height: 10rem;
  width: 10rem;
  display: inline-flex;
  align-items: center;
  justify-content: center;
}
</style>
