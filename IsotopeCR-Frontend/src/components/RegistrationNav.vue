<template>
  <html lang="en">
    <h2>Profile Navigation</h2>
    <!-- <div id="createAccount">
      <h3>Create Admin Profile</h3>
      <div class="container-fluid">
        <div class="input">
          <div class="inputbox">
            <input
              type="text"
              class="form-control input-style"
              v-model="firstName"
              placeholder="First Name"
            />
          </div>
          <div class="inputbox">
            <input
              type="text"
              class="form-control input-style"
              v-model="lastName"
              placeholder="Last Name"
            />
          </div>
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
              type="password"
              class="form-control input-style"
              v-model="password"
              placeholder="Password"
            />
          </div>
          <div class="inputbox">
            <input
              type="password"
              class="form-control input-style"
              v-model="confirmPassword"
              placeholder="Confirm Password"
            />
          </div>
          <input type="checkbox" id="checkbox" v-model="isOwner" />
          <label for="checkbox" class="bold">Owner of the company?: {{ isOwner }}</label>
        </div>
      </div> -->
      <div class="inputbox">
    <a href="http://127.0.0.1:8087/createCustomerProfile" class="btn btn-primary">Client Entry</a>
    </div>
    <a href="http://127.0.0.1:8087/createAdminProfile" class="btn btn-primary">Admin Entry</a>
    <div class="inputbox">
    <a href="http://127.0.0.1:8087/createTechProfile" class="btn btn-primary">Technician Entry</a>
    </div>
    <!-- </div> -->
  </html>
</template>

<script src=>
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