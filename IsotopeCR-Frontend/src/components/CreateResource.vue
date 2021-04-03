<template>

    <div id="service" class="container">
    <div class="inner_container shadow">

    <div class="large_text"> <b>Resources</b> </div>

    <hr>
   
    <table id="services" class="table">
      <th>Resource Type</th>
      <th>Maximum Available</th>
      <tr v-for="resource in resources">
        <td>{{ resource.resourceType }}</td>
        <td>{{ resource.maxAvailable }}</td>
      </tr>
    </table>
   
    <hr>
    <div id="createResource">
      <h3>Create Resource</h3>
      <div class="container-fluid">
        <div class="input">
          <div class="inputbox">
            <input
              type="text"
              class="form-control input-style"
              v-model="resourceType"
              placeholder="Resource Type"
            />
          </div>
          <div class="inputbox">
            <input
              type="number"
              class="form-control input-style"
              v-model="maxAvailable"
              placeholder="Maximum Available"
            />
          </div>
        </div>
      </div>
      <button class="btn btn-danger" @click="createResource(resourceType, maxAvailable)">Create Resource</button>
      <p>{{ errorResource }}</p>
    </div>

        <div id="deleteResource">
      <h3>Delete Resource</h3>
      <div class="container-fluid">
        <div class="input">
          <div class="inputbox">
            <input
              type="text"
              class="form-control input-style"
              v-model="deletedResourceType"
              placeholder="Resource Type"
            />
          </div>
        </div>
      </div>
      <button class="btn btn-danger" @click="deleteResource(deletedResourceType)">Delete Resource</button>
      <p>{{ errorResource }}</p>
    </div>
      
      <hr>

    </div> 

  </div>
  
</template>

<script 

src="./createResource.js">

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