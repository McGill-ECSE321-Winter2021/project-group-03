<template>
  <div id="service" class="container">
    <div class="inner_container shadow">
      <br />
      <div class="large_text"><b>Offered Services</b></div>

      <hr />
      <table id="services" class="table">
        <th>Offered Service</th>
        <th>Duration</th>
        <th>Price</th>
        <th>Frequency</th>
        <th>Resource</th>
        <tr v-for="service in services">
          <td>{{ service.serviceName }}</td>
          <td>{{ service.duration }}</td>
          <td>{{ service.price }}</td>
          <td>{{ service.frequency }}</td>
          <td>{{ service.resource.resourceType }}</td>
        </tr>
      </table>

      <hr />
      <div id="createService">
        <h3>Create Service</h3>
        <div class="container-fluid">
          <div class="input">
            <div class="inputbox">
              <input
                type="text"
                class="form-control input-style"
                v-model="serviceName"
                placeholder="Service Name"
              />
            </div>
            <div class="inputbox">
              <input
                type="number"
                class="form-control input-style"
                v-model="duration"
                placeholder="Duration: multiple of 30(minutes)"
              />
            </div>
            <div class="inputbox">
              <input
                type="number"
                class="form-control input-style"
                v-model="price"
                placeholder="Price: (natural number)"
              />
            </div>
            <div class="inputbox">
              <input
                type="number"
                class="form-control input-style"
                v-model="frequency"
                placeholder="Frequency: an integer representing months"
              />
            </div>
            <div class="inputbox">
              <input
                type="text"
                class="form-control input-style"
                v-model="resourceType"
                placeholder="Resource Type"
              />
            </div>
          </div>
        </div>
        <p v-if="errorService" style="color: red">{{ errorService }}</p>
      </div>
      <button
        class="btn btn-danger"
        @click="
          createService(serviceName, duration, price, resourceType, frequency)
        "
      >
        Create Service
      </button>
      <p v-if="errorService" style="color: red">{{ errorService }}</p>
    <div id="deleteService">
      <h3>Delete Service</h3>
      <select
        style="width: 150px; height: 36px"
        class="form-select"
        v-model="serviceName"
      >
        <option v-for="service in services" v-bind:value="service.serviceName">
          {{ service.serviceName }}
        </option>
      </select>
      <button class="btn btn-danger" @click="deleteService()">
        Delete Service
      </button>
      <p v-if="errorService" style="color: red">{{ errorService }}</p>
      </div>
    </div>
  </div>
</template>

<script 

src="./createService.js">
</script>

<style scoped>
#createService {
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
#service {
  padding-top: 6vh;
}
#deleteService {
  padding-top: 6vh;
}
</style>