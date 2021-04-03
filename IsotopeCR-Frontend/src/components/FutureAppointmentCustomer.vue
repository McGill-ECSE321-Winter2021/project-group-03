<template>
    <div id="futureappointment/customer" class="container">        
        <table class= "pastappointmentc">
        <thead>
        <tr>
        <th scope="col" style=" width: 10rem;">Date
        </th>
        <th scope="col" style=" width: 10rem;">Start time
        </th>
        <th scope="col" style=" width: 10rem;">Customer
        </th>
        <th scope="col" style=" width: 10rem;">Vehicle
        </th>
        <th scope="col" style=" width: 10rem;">Service
        </th>
        <th scope="col" style=" width: 10rem;">Technician
        </th>
        <tr v-for ="futureappointmentc in futureappointments">
            <td>{{futureappointmentc.timeslots[0].date}}</td>
            <td>{{futureappointmentc.timeslots[0].time}}</td>
            <td>{{futureappointmentc.customer.email}}</td>
            <td>{{futureappointmentc.vehicle.licensePlate}}</td>
            <td>{{futureappointmentc.service.serviceName}}</td>
            <td>{{futureappointmentc.technician.firstName + "  " + futureappointmentc.technician.lastName }}</td>
        </tr>
        </thead>
        </table> 
        <p v-if="errorFutureappointmentc" style="color: red">{{ errorFutureappointmentc }}</p>
    </div>
</template>

<script src ="./futureAppointmentCustomer.js">

export default {
    name: 'FutureAppointmentCustomer',

  
}
</script>

<style>
div.container{
    display: flex;
}
</style>