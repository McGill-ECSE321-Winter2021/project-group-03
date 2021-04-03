<template>
  <html lang="en">
    <div id="createAccount">
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
              placeholder="Email: abc@isotopecr.ca"
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
          <input type="checkbox" id="checkbox" v-model="isOwner" />
          <label for="checkbox" class="bold">Owner of the company?: {{ isOwner }}</label>
        </div>
      </div>
      <button class="btn btn-danger" @click="createAdminAccount(email, firstName, lastName, password, isOwner)">Sign Up</button>
      <p v-if="error" style="color: red">{{ error }}</p>
    </div>
  </html>
</template>

<script src='./createAdminProfileScript.js'>
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