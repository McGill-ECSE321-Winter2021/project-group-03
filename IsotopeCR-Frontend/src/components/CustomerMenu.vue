<template>
  <div>
    <div class="input">
      <b-card title="My profile" class="card">
        <b-button
          variant="primary"
          class="button"
          @click="routeTo('profileInfo')"
        >
          View Profile
        </b-button>
      </b-card>
    </div>
    <div class="input">
      <b-card title="Appointments" class="card">
        <b-button
          variant="primary"
          class="button"
          @click="routeTo('bookAppointment')"
        >
          Book Appointment
        </b-button>
        <b-button
          variant="primary"
          class="button"
          @click="routeTo('cancelAppointment')"
        >
          Cancel Appointment
        </b-button>
        <b-button
          variant="primary"
          class="button"
          @click="routeTo('futureAppointments')"
        >
          Upcoming Appointments
        </b-button>
        <b-button
          variant="primary"
          class="button"
          @click="routeTo('pastAppointments')"
        >
          Appointment History
        </b-button>
      </b-card>
    </div>
  </div>
</template>

<script>
export default {
  name: "CustomerMenu",
  methods: {
    routeTo: function (to) {
      location.href = "http://127.0.0.1:8087/" + to;
    },
  },
};
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
