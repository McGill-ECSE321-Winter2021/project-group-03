<template>
  <div id="futureappointment/vehicle" class="container">
    <div class="small_text left_text brand_color_text">
      Vehicle:
      <select v-model="licensePlate">
        <option v-for="vehicle in vehicles" v-bind:value="vehicle.licensePlate">
          {{ vehicle.licensePlate }}
        </option>
      </select>
      <button class="button" @click="futureappointmentv(licenseplate)">
        Find Future Appointment
      </button>
    </div>
    <table class="futureappointmentv">
      <thead>
        <th scope="col" style="width: 10rem">Date</th>
        <th scope="col" style="width: 10rem">Start time</th>
        <th scope="col" style="width: 10rem">Customer</th>
        <th scope="col" style="width: 10rem">Vehicle</th>
        <th scope="col" style="width: 10rem">Service</th>
        <th scope="col" style="width: 10rem">Technician</th>
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
      </thead>
    </table>

    <p v-if="errorMessage" style="color: red">{{ errorMessage }}</p>
  </div>
</template>

<script src="./futureAppointmentVehicle.js">
export default {
  name: "FutureAppointmentVehicle",
};
</script>

<style>
</style>