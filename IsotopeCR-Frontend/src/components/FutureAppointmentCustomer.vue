<template>
  <div id="futureappointment/customer" class="container">

  <b-card title="Upcoming Appointments" tag="article" class="card">
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
          <tr v-for="futureappointmentc in futureappointments">
          <td>{{ futureappointmentc.timeslots[0].date }}</td>
          <td>{{ futureappointmentc.timeslots[0].time }}</td>
          <td>{{ futureappointmentc.customer.email }}</td>
          <td>{{ futureappointmentc.vehicle.licensePlate }}</td>
          <td>{{ futureappointmentc.service.serviceName }}</td>
          <td>
            {{
              futureappointmentc.technician.firstName +
              "  " +
              futureappointmentc.technician.lastName
            }}
          </td>
        </tr>
        </tbody>
      </table>
    </b-card>
    <p v-if="errorFutureappointmentc" style="color: red">
      {{ errorFutureappointmentc }}
    </p>
  </div>
</template>

<script src ="./futureAppointmentCustomer.js">
</script>

<style>
.container {
  display: grid;
}
.card {
  margin: 1.2rem;
}
</style>