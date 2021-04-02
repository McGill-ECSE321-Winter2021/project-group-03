<template>
    <div id="futureappointment/vehicle" class="container">
        <div class="input_box">License Plate:
        <input type="text" class="input wide" v-model='licenseplate' placeholder="License Plate">
        <button class="button" @click="futureappointmentv(licenseplate)" >Find Future Appointment</button>
        </div>
        <table class= "futureappointmentv">
        <thead>
        
        <th scope="col" style="text-align: left; width: 10rem;">Date
        </th>
        <th scope="col" style="text-align: left; width: 10rem;">Start time
        </th>
        <th scope="col" style="text-align: left; width: 10rem;">Customer
        </th>
        <th scope="col" style="text-align: left; width: 10rem;">Vehicle
        </th>
        <th scope="col" style="text-align: left; width: 10rem;">Service
        </th>
        <th scope="col" style="text-align: left; width: 10rem;">Technician
        </th>
          <tr v-for ="futureappointmentv in futureappointments">
            <td>{{futureappointmentv.date}}</td>
            <td>{{futureappointmentv.starttime}}</td>
            <td>{{futureappointmentv.customer}}</td>
            <td>{{futureappointmentv.vehicle}}</td>
            <td>{{futureappointmentv.service}}</td>
            <td>{{futureappointmentv.technician}}</td>
        </tr>
        </thead>
        </table>
    </div>
</template>

<script src="./futureAppointmentVehicle.js">

export default {
    name: 'FutureAppointmentVehicle',

}
</script>

<style>
div.container{
    display: flex;
}
</style>