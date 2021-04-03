<template>
    <div id="bookappointment" class="container">
        <div class="input_container">
            <div class="small_text left_text brand_color_text">Vehicle:</div>
            <input type="text" class="input wide" v-model="license" placeholder="Liscence">
            <div class="small_text left_text brand_color_text">Service:</div>
            <input type="text" class="input wide" v-model ='serviceName' placeholder="Service">
              <div class="small_text left_text brand_color_text">Start Time:</div>
            <input type="text" class="input wide" v-model ='startTime' placeholder="Start Time">
              <div class="small_text left_text brand_color_text">Date:</div>
            <input type="text" class="input wide" v-model ='date' placeholder="date">

            <span v-if="errorMessage" style="color:red">{{errorMessage}} </span>
            <div>
            <button class="button brand_color wide" @click="createAppointment(license,serviceName,startTime,date)">Book An Appointment</button>
            </div>
         </div>
  </div>

</template>

<script src='./bookAppointment.js'>

export default {
    name: 'BookAppointment',
    methods: {
    
  }
}
</script>

<style>
</style>