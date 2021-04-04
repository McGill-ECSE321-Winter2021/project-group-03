<template>
  <div id="pastappointment/vehicle" class="container">
    <b-card
      title="Past Appointments"
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
        @click="pastappointmentv()"
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
    <span v-if="errorMessage" style="color: red">{{ errorMessage }} </span>
  </div>
</template>

<script src="./pastAppointmentVehicle.js">
export default {
  name: "PastAppointmentVehicle",
};
</script>

<style scoped>
.card-button {
  margin: 0.5rem;
}
.box {
  display: flex;
  justify-content: center;
}
</style>