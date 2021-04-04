<template>
  <div id="futureappointment" class="container">
    <b-card title="Upcoming Appointments" tag="article" class="card">
      <b-button href="#" variant="primary" class="card-button" onclick="location.href='http://127.0.0.1:8087/futureappointment/customer';">
        View All
      </b-button>
      <b-button href="#" variant="primary" class="card-button" onclick="location.href='http://127.0.0.1:8087/futureappointment/vehicle';">
        Filter by Vehicle
      </b-button>
  </b-card>
  </div>
</template>

<script>
export default {
  name: "FutureAppointment",
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