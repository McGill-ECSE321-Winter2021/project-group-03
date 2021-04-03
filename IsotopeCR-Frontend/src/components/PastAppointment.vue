<template>
  <div id="pastappointment" class="container">
    <div>
      <input
        type="button"
        onclick="location.href='http://127.0.0.1:8087/pastappointment/vehicle';"
        value="View Past Appointment for Vehicle"
      />
    </div>
    <div>
      <input
        type="button"
        onclick="location.href='http://127.0.0.1:8087/pastappointment/customer';"
        value="View Past Appointment for Customer"
      />
    </div>
    <router-view />
  </div>
</template>

<script>
export default {
  name: "PastAppointment",
};
</script>

<style>
</style>