<template>
  <div id="pastappointment/customer" class="container">
    <b-card title="Past Appointments" tag="article" class="card">
      <table class="table table-striped table-hover">
        <thead>
          <tr>
            <th>Date</th>
            <th>StartTime</th>
            <th>Customer</th>
            <th>Vehicle</th>
            <th>Service</th>
            <th>Technician</th>
          </tr>
        </thead>
        <tbody>
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
        </tbody>
      </table>
    </b-card>
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
.container {
  display: grid;
}
.card {
  margin: 1.2rem;
}
</style>