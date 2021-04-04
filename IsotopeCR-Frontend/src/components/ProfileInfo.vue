<template>
  <html lang="en">
    <div id="AllProfile">
      <h3>View Info</h3>
      <div>
        <div class="inputbox">
          <input
            v-if="myType === 'Admin'"
            type="text"
            class="form-control input-style"
            v-model="email"
            placeholder="Email"
          />
        </div>
        <button class="btn btn-danger" @click="displayProfile(email)">
          View My Info
        </button>
        <p v-if="error" style="color: red">Error: {{ error }}</p>
        <hr />
        <div>
          <table id="myProfile" class="table">
            <th>FirstName</th>
            <th>LastName</th>
            <th>Email</th>
            <th>PhoneNumber</th>
            <th>AccountType</th>
            <tr>
              <td>{{ firstName }}</td>
              <td>{{ lastName }}</td>
              <td>{{ email }}</td>
              <td>{{ phoneNumber }}</td>
              <td>{{ type }}</td>
            </tr>
          </table>
        </div>
      </div>
      <hr />
      <div>
        <h3>Edit Info</h3>
        <h5>Edit PhoneNumber</h5>
        <div v-if="type === 'Customer'">
          <input
            type="text"
            class="inputbox"
            v-model="newPhoneNumber"
            placeholder="new phone number"
          />
          <button
            class="btn btn-danger"
            @click="editPhoneNumber(newPhoneNumber)"
          >
            Edit PhoneNumber
          </button>
          <p v-if="editError" style="color: red">{{ editError }}</p>
        </div>
        <h5>Edit Password</h5>
        <div v-if="type">
          <input
            type="password"
            class="inputbox"
            v-model="newPassword"
            placeholder="new password"
          />
          <button class="btn btn-danger" @click="editPassword(newPassword)">
            Edit Password
          </button>
          <p v-if="editError" style="color: red">{{ editError }}</p>
        </div>
      </div>
    </div>
  </html>
</template>

<script src='./profileInfoScript.js'>
</script>

<style scoped>
#AllProfile {
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
#editInfo {
  padding-top: 5vh;
}
#editPhoneNumber {
  padding-bottom: 5vh;
}
#title {
  padding-bottom: 3vh;
}
</style>