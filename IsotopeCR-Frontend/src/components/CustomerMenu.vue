<template>
  <div>
    <div class="input">
      <b-card title="My profile" class="card">
        <b-button variant="primary" class="button">
          View Profile
        </b-button>
        <b-button variant="primary" class="button">
          Update Profile
        </b-button>
      </b-card>
    </div>
    <div class="input">
      <b-card title="Appointments" class="card">
        <b-button variant="primary" class="button">
          Book Appointment
        </b-button>
        <b-button variant="primary" class="button">
          Cancel Appointment
        </b-button>
        <b-button variant="primary" class="button">
          Upcoming Appointments
        </b-button>
        <b-button variant="primary" class="button">
          Appointment History
        </b-button>
      </b-card>
    </div>
  </div>
</template>

<script>
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
