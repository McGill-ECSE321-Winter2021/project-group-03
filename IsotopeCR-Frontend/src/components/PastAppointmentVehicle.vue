<template>
    <div id="pastappointment/vehicle" class="container">
        <div class="input_box">License Plate:
        <input type="text" class="input wide" v-model='licenseplate' placeholder="License Plate">
        <button class="button" @click="pastappointmentv(licenseplate)" >Find Past Appointment</button>
        </div>
        
        <table class= "pastappointmentv">
        <thead>
        <tr>
        <th scope="col" style="text-align: left; width: 10rem;">  Date
        </th>
        <th scope="col" style="text-align: left; width: 10rem;">  Start time
        </th>
        <th scope="col" style="text-align: left; width: 10rem;">  Customer
        </th>
        <th scope="col" style="text-align: left; width: 10rem;">  Vehicle
        </th>
        <th scope="col" style="text-align: left; width: 10rem;">  Service
        </th>
        <th scope="col" style="text-align: left; width: 10rem;">  Technician
        </th>
        </tr>
        <tr v-for ="pastappointmentv in pastappointments">
            <td>{{pastappointmentv.timeslots[0].date}}</td>
            <td>{{pastappointmentv.timeslots[0].time}}</td>
            <td>{{pastappointmentv.customer.email}}</td>
            <td>{{pastappointmentv.vehicle.licensePlate}}</td>
            <td>{{pastappointmentv.service.serviceName}}</td>
            <td>{{pastappointmentv.technician.firstName + " " +pastappointmentv.technician.lastName}}</td>
        </tr>
    
        </thead>
        </table>   
        <span v-if="error" style="color:red">{{error}} </span>      
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