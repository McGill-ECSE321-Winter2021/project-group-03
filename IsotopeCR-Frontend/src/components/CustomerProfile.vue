<template>
  <html lang="en">
    <div id="createAccount">
      <h3>My Information</h3>
      <div class="container-fluid">
        <div class="input">

         <table id="vehicle" class="table">
          <th>FirstName</th>
          <th>LastName</th>
          <th>Email</th>
          <th>Password</th>
          <th>PhoneNumber</th>
          <tr v-for="profile in profiles">
            <td>{{ profile.firstName }}</td>
            <td>{{ profile.lastName }}</td>
            <td>{{ profile.email }}</td>
          </tr>
        </table>

        <!--
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
          -->
         
        </div>
      </div>
      <!-- <button class="btn btn-danger" @click="createAdminAccount(email, firstName, lastName, password, isOwner)">Create Account</button> -->
      <p v-if="error" style="color: red">Error: {{ error }}</p>
    </div>
  </html>
</template>

<script src='./customerProfileScript.js'>
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