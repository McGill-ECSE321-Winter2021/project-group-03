<template>
    <div id="pastappointment/vehicle" class="container">
        <div class="input_box">License Plate:
        <input type="text" class="input wide" v-model='licenseplate' placeholder="License Plate">
        <button class="button" @click="pastappointmentv(licenseplate)" >Find Past Appointment</button>
        </div>
        
        <table class= "pastappointmentv">
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
        <tr v-for ="pastappointmentv in pastappointments">
            <td>{{pastappointmentv.date}}</td>
            <td>{{pastappointmentv.starttime}}</td>
            <td>{{pastappointmentv.customer}}</td>
            <td>{{pastappointmentv.vehicle}}</td>
            <td>{{pastappointmentv.service}}</td>
            <td>{{pastappointmentv.technician}}</td>
        </tr>
        </thead>
        </table>         
    </div>
</template>

<script src="./pastAppointmentVehicle.js">

export default {
    name: 'PastAppointmentVehicle',

}
</script>

<style scoped>
div.container{
    display: flex;
}
</style>