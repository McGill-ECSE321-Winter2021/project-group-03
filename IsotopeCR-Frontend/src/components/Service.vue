<template>

    <div id="service" class="container">
    <div class="inner_container shadow">

    <div class="large_text"> <b>Offered Services</b> </div>

    <hr>
   
    <table id="services" class="table">
      <th>Offered Service</th>
      <th>Duration</th>
      <th>Price</th>
      <th>Frequency</th>
      <th>Resource</th>
      <tr v-for="service in services" v-bind:key="service">
        <td>{{ service.serviceName }}</td>
        <td>{{ service.duration }}</td>
        <td>{{ service.price }}</td>
        <td>{{ service.frequency }}</td>
        <td>{{ service.resource }}</td>
      </tr>
    </table>
   
    <hr>

      <div class="input_container">

        <div class="small_text left_text brand_color_text">Create Service Table</div>
        
        <table style="width:100%">
        <tr>
            <th>Service</th>
            <th>Duration</th>
            <th>Price</th>
            <th>Frequency</th>
            <th>Resource</th>
        </tr>
        <tr>
            <td>
                <input 
                type="text" 
                v-model="service" 
                placeholder="Service Name"
                >
            </td>
            <td>
                <input 
                type="number" 
                v-model="duration" 
                placeholder="Duration"
                >
            </td>
            <td>
                <input 
                type="number" 
                v-model="price" 
                placeholder="Price"
                >
            </td>
            <td>
                <input 
                type="number" 
                v-model="frequency" 
                placeholder="Frequency"
                >
            </td>
            <td>
                <input 
                type="text" 
                v-model="resource" 
                placeholder="Resource"
                >
            </td>
            <td>
                <button  
                
                @click="createService(serviceName, duration, price, frequency, resource)">Create Service

                </button>
            </td>
        </tr>
        <tr>
            <td>
                <button  
                
                @click="deleteService()">Delete Service

                </button>
            </td>
        </tr>
       
        </table>


        <hr>

        <span v-if="errorService" style="color:red">{{ errorService }} </span>

      </div>
      
      <hr>

    </div> 

  </div>

</template>

<script 

src="./service.js">

</script>

<style scoped>
#createService {
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