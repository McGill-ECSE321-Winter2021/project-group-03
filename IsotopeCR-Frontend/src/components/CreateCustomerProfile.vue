<template>
  <html lang="en">
    <div id="createAccount">
      <h3>Create Customer Profile</h3>
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
              placeholder="Email: abc@mail.com"
            />
          </div>
          <div class="inputbox">
            <input
              type="text"
              class="form-control input-style"
              v-model="phoneNumber"
<<<<<<< HEAD
              placeholder="Phone Number: 5555555555"
=======
              placeholder="Phone Number (optional)"
>>>>>>> 1bf6167186983676502773784494ae78db438e02
            />
          </div>
          <div class="inputbox">
            <input
              type="password"
              class="form-control input-style"
              v-model="password"
              placeholder="Password:8~20length.1upper/1lower/no space"
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
        </div>
      </div>     
      <button class="btn btn-danger" @click="createAc">Sign Up</button>
      <p v-if="error" style="color:red">{{ error }}</p>
    </div>
  </html>
</template>

<script src="./createCustomerProfileScript.js">
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