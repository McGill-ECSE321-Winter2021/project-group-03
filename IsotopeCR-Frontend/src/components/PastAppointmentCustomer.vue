<template>
  <div id="pastappointment/customer" class="container">
    <table class="pastappointmentc">
      <thead>
        <tr>
          <th scope="col" style="text-align: left; width: 10rem">Date</th>
          <th scope="col" style="text-align: left; width: 10rem">Start time</th>
          <th scope="col" style="text-align: left; width: 10rem">Customer</th>
          <th scope="col" style="text-align: left; width: 10rem">Vehicle</th>
          <th scope="col" style="text-align: left; width: 10rem">Service</th>
          <th scope="col" style="text-align: left; width: 10rem">Technician</th>
        </tr>

        <tr v-for="pastappointmentc in pastappointments">
          <td>{{ pastappointmentc.timeslots[0].date }}</td>
          <td>{{ pastappointmentc.timeslots[0].time }}</td>
          <td>{{ pastappointmentc.customer.email }}</td>
          <td>{{ pastappointmentc.vehicle.licensePlate }}</td>
          <td>{{ pastappointmentc.service.serviceName }}</td>
          <td>
            {{
              pastappointmentc.technician.firstName +
              " " +
              pastappointmentc.technician.lastName
            }}
          </td>
        </tr>
      </thead>
    </table>
    <p v-if="errorPastappointmentc" style="color: red">
      {{ errorPastappointmentc }}
    </p>
  </div>
</template>

<script src ="./pastAppointmentCustomer.js">
export default {
  name: "PastAppointmentCustomer",
};
</script>

<style>
div.container {
  display: flex;
}
</style>