<template>
  <div id="pastappointment" class="container">
    <b-card title="Appointment History" tag="article" class="card">
      <b-button
        href="#"
        variant="primary"
        class="card-button"
        onclick="location.href='http://127.0.0.1:8087/pastappointment/customer';"
      >
        View All
      </b-button>
      <b-button
        href="#"
        variant="primary"
        class="card-button"
        onclick="location.href='http://127.0.0.1:8087/pastappointment/vehicle';"
      >
        Filter by Vehicle
      </b-button>
    </b-card>
  </div>
</template>

<script>
export default {
  name: "PastAppointment",
};
</script>

<style>
.container {
  margin-top: 1rem;
  display: grid;
}
.card {
  display: flex;
}
.card-button {
  margin-top: 1.2rem;
}
</style>