<template>
  <div id="cancelappointment">  
    <table class="table table-striped table-hover">  
      <thead>         
        <tr>        
          <th>
            <label class="form-checkbox">
              <input type="checkbox" /><i class="form-icon"></i>
            </label>         
          </th>              
          <th>Date</th>              
          <th>StartTime</th>             
          <th>Customer</th>            
          <th>Vehicle</th>              
          <th>Service</th>                        
          <th>Technician</th>
          <th>IsCharged</th>         
        </tr>    
      </thead>        
      <tbody>        
        <tr v-for="aptmt in pastappointments">                       
        <td>
        <label class="form-checkbox">
            <input type="checkbox" :value="aptmt.appointmentID" v-model="selected" />
            <i class="form-icon"></i>
        </label>
        </td>
                            
        <td>{{ aptmt.timeslots[0].date }}</td>
                            
        <td>{{ aptmt.timeslots[0].time }}</td>
                            
        <td>{{ aptmt.customer.email }}</td>
                            
        <td>{{ aptmt.vehicle.licensePlate }}</td>
                            
        <td>{{ aptmt.service.serviceName }}</td>

        <td>{{ aptmt.invoice.isPaid }}</td>
                            
        <td>
         {{ aptmt.technician.firstName + "  " + aptmt.technician.lastName }}
        </td>
        </tr>        
      </tbody>     
    </table>
         
        <p v-if="errorPastappointmentc" style="color: red">{{ errorPastappointmentc }}</p>
        
    <div>
    <button class="button brand_color wide" @click="createInvoice(selected)">Cancel An Appointment</button>
    <p v-if="errorCharge" style="color: red">{{ errorCharge }}</p>     
    </div>
  </div>
</template>

<script src="./chargeAppointmentScript.js">

</script>

<style>
div.container {
      display: flex;
}
</style>