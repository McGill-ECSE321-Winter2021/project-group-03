<template>
    <div id="futureappointment/vehicle" class="container">
       <div class="input_box">License Plate:
        <input type="text" class="input wide" v-model='licenseplate' placeholder="License Plate">
        <button class="button" @click="futureappointmentv(licenseplate)" >Find Future Appointment</button>
        </div>
        
        <table class= "futureappointmentv">
        <thead>
        
        <th scope="col" style="width: 10rem;">   Date
        </th>
        <th scope="col" style="width: 10rem;">   Start time
        </th>
        <th scope="col" style="width: 10rem;">   Customer
        </th>
        <th scope="col" style="width: 10rem;">   Vehicle
        </th>
        <th scope="col" style="width: 10rem;">   Service
        </th>
        <th scope="col" style="width: 10rem;">   Technician
        </th>
          <tr v-for ="futureappointmentv in futureappointments">
            <td>{{futureappointmentv.timeslots[0].date}}</td>
            <td>{{futureappointmentv.timeslots[0].time}}</td>
            <td>{{futureappointmentv.customer.email}}</td>
            <td>{{futureappointmentv.vehicle.licensePlate}}</td>
            <td>{{futureappointmentv.service.serviceName}}</td>
            <td>{{futureappointmentv.technician.firstName +" " + futureappointmentv.technician.lastName }}</td>
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

</style>