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
            <td>{{futureappointmentv.timeslots[0].aDate}}</td>
            <td>{{futureappointmentv.timeslots[0].aTime}}</td>
            <td>{{futureappointmentv.aCustomer.email}}</td>
            <td>{{futureappointmentv.aVehicle.licsencePlate}}</td>
            <td>{{futureappointmentv.aService.serviceName}}</td>
            <td>{{futureappointmentv.aTechnician.firstName+" "+futureappointmentv.aTechnician.lasttName}}</td>
        </tr>
        </thead>
        </table>
        <p v-if="error" style="color: red">{{ error }}</p>
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