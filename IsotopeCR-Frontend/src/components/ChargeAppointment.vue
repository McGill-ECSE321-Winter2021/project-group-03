<template>
<<<<<<< HEAD
  <div id="chargeappointment">  
    
    <div class="input" >
        <div class="inputbox">
            <input type="text" class="form-control input-style"  v-model="customeremail" placeholder="Email" />
            <button class="btn btn-danger" @click="viewPastAppointment(customeremail)">View</button>
        </div> 
    </div>
    
    <table class="table table-striped table-hover">  
      <thead>   
        
        <tr>        
=======
  <div id="chargeappointment">
      
    <table class="table table-striped table-hover">
        
      <thead>
               
        <tr>
                 
>>>>>>> a23de245116ea03266f74f3a17f660a088f94066
          <th>
            <label class="form-checkbox">
              <input type="checkbox" /><i class="form-icon"></i> </label
            >         
          </th>
                        
          <th>Date</th>
                        
          <th>StartTime</th>
                       
          <th>Customer</th>
                      
          <th>Vehicle</th>
                        
          <th>Service</th>
                          
          <th>Technician</th>
<<<<<<< HEAD
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
=======
          <th>IsCharged</th>
                   
        </tr>
            
      </thead>
              
      <tbody>
                
        <tr v-for="aptmt in futureappointments">
                                 
          <td>
            <label class="form-checkbox">
              <input
                type="checkbox"
                :value="aptmt.appointmentID"
                v-model="selected"
              />
              <i class="form-icon"></i>
            </label>
          </td>
>>>>>>> a23de245116ea03266f74f3a17f660a088f94066
                            
          <td>{{ aptmt.timeslots[0].date }}</td>
                            
          <td>{{ aptmt.timeslots[0].time }}</td>
                            
          <td>{{ aptmt.customer.email }}</td>
                            
          <td>{{ aptmt.vehicle.licensePlate }}</td>
                            
          <td>{{ aptmt.service.serviceName }}</td>

<<<<<<< HEAD
        <td>
         {{ aptmt.technician.firstName + "  " + aptmt.technician.lastName }}
        </td>

        <td>{{ aptmt.invoice.isPaid }}</td>                           
        
        </tr>        
      </tbody>     
    </table>
         
        <p v-if="errorPastappointmentc" style="color: red">{{ errorPastappointmentc }}</p>
        
    <div>
    <button class="button brand_color wide" @click="createInvoice(selected)">Charge</button>
    <p v-if="errorCharge" style="color: red">{{ errorCharge }}</p>     
=======
          <!-- <td>{{ aptmt.invoice.isPaid }}</td> -->
                            
          <td>
            {{ aptmt.technician.firstName + "  " + aptmt.technician.lastName }}
          </td>
        </tr>
                
      </tbody>
          
    </table>
         
    <p v-if="error" style="color: red">{{ error }}</p>
        
    <div>
      <button
        class="button brand_color wide"
        @click="cancelAppointment(selected)"
      >
        Cancel An Appointment
      </button>
      <p v-if="error" style="color: red">{{ error }}</p>
          
>>>>>>> a23de245116ea03266f74f3a17f660a088f94066
    </div>
  </div>
</template>

<script src="./chargeAppointmentScript.js">

</script>

<style scoped>
#createAccount {
  padding-top: 20vh;
}
.input {
  top: 20px;
  padding: 30px 30px;
  width: 450px;
}
.inputbox {
  position: relative;
  text-align: center;
  align-self: center;
  padding: 5px 5px;
}
.container-fluid {
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: auto;
  white-space: nowrap;
}
</style>