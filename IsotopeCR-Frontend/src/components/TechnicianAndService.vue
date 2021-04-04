<template>
  <html lang="en">
    <div id="Top">
      <h3>View My Services</h3>
      <div>
        <div class="inputbox">
          <input
              type="text"
              class="form-control input-style"
              v-model="email"
              placeholder="Email of technician"
          />
        </div>
        <button class="btn btn-danger" @click="displayServices(email)">View</button>
        <p v-if="error" style="color: red">Error: {{ error }}</p>

        <hr />

        <div>
          <table id="myServices" class="table">
            <th>ServiceName</th>
            <th>Duration</th>
            <th>Price</th>
            <th>ResourceName</th>
            <tr v-for="service in services">
              <td>{{ service.serviceName }}</td>
              <td>{{ service.duration }}</td>
              <td>{{ service.price }}</td>
              <td>{{ service.resource.resourceType }}</td>
            </tr>
          </table>
        </div>

        <hr />

        <h3>Add Services</h3>
        <div>
          <input
            type="text"
            class="inputbox"
            v-model="serviceName"
            placeholder="A service name"
          />
          <button
            class="btn btn-danger"
            @click="addService(email, serviceName)"
          >
            Add
          </button>
          <p v-if="errorAdd">Error: {{ errorAdd }}</p>
        </div>
      </div>
    </div>
  </html>
</template>

<script src='./technicianAndServiceScript.js'>
</script>

<style scoped>
#Top {
  padding-top: 5vh;
}
.input {
  top: 20px;
  padding: 20px 30px;
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