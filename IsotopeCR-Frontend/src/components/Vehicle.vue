<template>
  <html lang="en">
    <div id="vehicle">
      <h3>Vehicle</h3>
      <div class="container-fluid">
        <div class="input">
          <div class="inputbox">
            <input
              type="email"
              class="form-control input-style"
              v-model="email"
              placeholder="Email"
            />
          </div>

          <div class="inputbox">
            <input
              type="text"
              class="form-control input-style"
              v-model="licensePlate"
              placeholder="LicensePlate"
            />
          </div>
          <div class="inputbox">
            <input
              type="text"
              class="form-control input-style"
              v-model="year"
              placeholder="year"
            />
          </div>
          <div class="inputbox">
            <input
              type="text"
              class="form-control input-style"
              v-model="model"
              placeholder="model"
            />
          </div>
          <div class="inputbox">
            <input
              type="text"
              class="form-control input-style"
              v-model="brand"
              placeholder="brand"
            />
          </div>
        </div>
      </div>

      <div class="inputbox">
        <button
          class="btn btn-danger"
          @click="createVehicle(email, licensePlate, year, model, brand)"
        >
          Register
        </button>
        <p v-if="errorVehicle" style="color: red">Error: {{ errorVehicle }}</p>
      </div>

      <hr />  

        <table id="vehicles" class="table">
          <th>LicencePlate</th>
          <th>Year</th>
          <th>Model</th>
          <th>Model</th>
          <tr v-for="vehicle in vehicles">
            <td>{{ vehicle.licensePlate }}</td>
            <td>{{ vehicle.year }}</td>
            <td>{{ vehicle.model }}</td>
            <td>{{ vehicle.brand }}</td>
          </tr>
        </table>
         <button class="btn btn-danger" @click="displayVehicle(email)">
          Display My Vehicles
        </button>
      </div>
    </div>
  </html>
</template>

<script src='./vehicleScript.js'>
</script>

<style scoped>
#vehicle {
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
</style>