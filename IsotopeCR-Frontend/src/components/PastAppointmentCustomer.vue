<template>
    <div id="pastappointment/customer" class="container">
        <div class="small_text left_text brand_color_text">Customer Email:
        <input type="text" class="input wide" v-model='customeremail' placeholder="Customer Email">
        <button class="button" @click="pastappointmentc(customeremail)" >Find Past Appointment</button>
        </div>
        <router-view/>

        <table class= "pastappointmentc">
        <thead>
        <tr>
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
        <tr v-for ="pastappointmentc in pastappointments">
            <td>{{pastappointmentc.date}}</td>
            <td>{{pastappointmentc.starttime}}</td>
            <td>{{pastappointmentc.customer}}</td>
            <td>{{pastappointmentc.vehicle}}</td>
            <td>{{pastappointmentc.service}}</td>
            <td>{{pastappointmentc.technician}}</td>
        </tr>
        </thead>
        </table> 
    </div>
</template>

<script src ="./pastAppointmentCustomer.js">

export default {
    name: 'PastAppointmentCustomer',

}
</script>

<style>
div.container{
    display: flex;
}
</style>