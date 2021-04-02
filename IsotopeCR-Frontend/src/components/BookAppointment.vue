<template>
    <div id="bookappointment" class="container">
        <div class="input_container">
            <div class="small_text left_text brand_color_text">Vehicle:</div>
            <input type="text" class="input wide" placeholder="Liscence">
            <div class="small_text left_text brand_color_text">Service:</div>
            <input type="password" class="input wide" placeholder="Service">
              <div class="small_text left_text brand_color_text">Start Time:</div>
            <input type="password" class="input wide" placeholder="Start Time">
              <div class="small_text left_text brand_color_text">Date:</div>
            <input type="password" class="input wide" placeholder="date">

            <span v-if="errorMessage" style="color:red">{{errorMessage}} </span>
            <div>
            <button class="button brand_color wide">Book An Appointment</button>
            </div>
         </div>
  </div>

</template>

<script>

export default {
    name: 'BookAppointment',
    methods: {
    
  }
}
</script>

<style>
</style>