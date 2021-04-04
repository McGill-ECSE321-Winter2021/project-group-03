<template>
  <div id="futureappointment/vehicle" class="container">

  <b-card
      title="Upcoming Appointments"
      sub-title="Filter by Vehicle"
      tag="article"
      class="card"
    >
    <div class="box">
      <div class="card-button">License Plate:</div>
      <select v-model="licensePlate">
        <option v-for="vehicle in vehicles" v-bind:value="vehicle.licensePlate">
          {{ vehicle.licensePlate }}
        </option>
      </select>
      </div>
      <div/>
      <b-button
        href="#"
        variant="primary"
        class="card-button"
        @click="futureappointmentv()"
      >
        View Appointments
      </b-button>
      <table class="table table-striped table-hover">
        <thead>
          <tr>
            <th>Date</th>
            <th>Start Time</th>
            <th>Customer</th>
            <th>Vehicle</th>
            <th>Service</th>
            <th>Technician</th>
          </tr>
        </thead>
        <tbody>
           <tr v-for="futureappointmentv in futureappointments">
          <td>{{ futureappointmentv.timeslots[0].date }}</td>
          <td>{{ futureappointmentv.timeslots[0].time }}</td>
          <td>{{ futureappointmentv.customer.email }}</td>
          <td>{{ futureappointmentv.vehicle.licensePlate }}</td>
          <td>{{ futureappointmentv.service.serviceName }}</td>
          <td>
            {{
              futureappointmentv.technician.firstName +
              " " +
              futureappointmentv.technician.lastName
            }}
          </td>
        </tr>
        </tbody>
      </table>
    </b-card>
    <p v-if="errorMessage" style="color: red">{{ errorMessage }}</p>
  </div>
</template>

<script src="./futureAppointmentVehicle.js">
export default {
  name: "FutureAppointmentVehicle",
};
</script>

<style>
.card-button {
  margin: 0.5rem;
}
.box {
  display: flex;
  justify-content: center;
}
</style>