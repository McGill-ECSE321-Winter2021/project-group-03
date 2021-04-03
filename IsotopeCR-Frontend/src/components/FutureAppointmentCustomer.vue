<template>
    <div id="futureappointment/customer" class="container">
      <div class="small_text left_text brand_color_text">Customer Email:
        <input type="text" class="input wide" v-model ='customeremail' placeholder="Customer Email">
        <button class="button" @click="futureappointmentc(customeremail)" >Find Past Appointment</button>
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
        <tr v-for ="futureappointmentc in futureappointments">
            <td>{{futureappointmentc.date}}</td>
            <td>{{futureappointmentc.starttime}}</td>
            <td>{{futureappointmentc.customer}}</td>
            <td>{{futureappointmentc.vehicle}}</td>
            <td>{{futureappointmentc.service}}</td>
            <td>{{futureappointmentc.technician}}</td>
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