<template>
<<<<<<< HEAD

    <div id="resource" class="container">
=======
  <div id="service" class="container"> 
>>>>>>> 11cc278518b8932da41c1d3a37825e60a8447d5e
    <div class="inner_container shadow">
      <br>
      <div class="large_text"><b>Resources</b></div>
      <hr />
      <table id="services" class="table">
        <th>Resource Type</th>
        <th>Maximum Available  </th>
        <tr v-for="resource in resources">
          <td>{{ resource.resourceType }}</td>
          <td>{{ resource.maxAvailable }}</td>
        </tr>
      </table>

<<<<<<< HEAD
    <div class="large_text"> <b>Resources</b> </div>

    <hr>
   
    <table id="resources" class="table">
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
=======
      <hr />
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
                placeholder="Maximum Available (>=1)"
              />
            </div>
>>>>>>> 11cc278518b8932da41c1d3a37825e60a8447d5e
          </div>
        </div>
        <button
          class="btn btn-danger"
          @click="createResource(resourceType, maxAvailable)"
        >
          Create Resource
        </button>
        <p v-if="errorcreateResource" style="color: red">{{ errorcreateResource }}</p>
      </div>
<<<<<<< HEAD
      <button class="btn btn-danger" @click="createResource(resourceType, maxAvailable)">Create Resource</button>
      <p v-if="errorResource" style="color: red">{{ errorResource }}</p>
    </div>

        <div id="deleteResource">
      <h3>Delete Resource</h3>
      <select style="width: 150px; height: 36px;" class="form-select" v-model="resourceType">
        <option v-for="resource in resources" v-bind:value="resource.resourceType">
          {{ resource.resourceType }}
        </option>
      </select>
      <button class="btn btn-danger" @click="deleteResource(deletedResourceType)">Delete Resource</button>
      <p v-if="errorResource" style="color: red">{{ errorResource }}</p>
    </div>
      
      <hr>

    </div> 
=======
<br>
<br>
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
        <button
          class="btn btn-danger"
          @click="deleteResource(deletedResourceType)"
        >
          Delete Resource
        </button>
        <p v-if="errorResource" style="color: red">{{ errorResource }}</p>
      </div>
>>>>>>> 11cc278518b8932da41c1d3a37825e60a8447d5e

      <hr />
    </div>
  </div>
</template>

<script 

src="./createResource.js">
</script>

<style scoped>
#createResource {
  padding-top: 5vh;
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
#resource {
  padding-top: 6vh;
}
#deleteResource {
  padding-top: 6vh;
}
</style>