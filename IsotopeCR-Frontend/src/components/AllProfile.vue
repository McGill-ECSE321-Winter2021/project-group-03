<template>
  <html lang="en">
    <div id="AllProfile">
      <h3>System Account Records</h3>
      <div class="container-fluid">
        <table id="profiles" class="table">
          <th>FirstName</th>
          <th>LastName</th>
          <th>Email</th>
          <th>PhoneNumber</th>
          <th>IsOwner</th>
          <th>AccountType</th>
          <tr v-for="profile in profiles">
            <td>{{ profile.firstName }}</td>
            <td>{{ profile.lastName }}</td>
            <td>{{ profile.email }}</td>
            <td>{{ profile.phoneNumber }}</td>
            <td>{{ profile.isOwner }}</td>
            <td>{{ profile.type }}</td>
          </tr>
        </table>
      </div>
      <hr />
      <div class="container-fluid">
        <div class="inputbox">
          <input
            type="text"
            class="form-control input-style"
            v-model="email"
            placeholder="Email to be deleted"
          />
        </div>
        <button class="btn btn-danger" @click="deleteProfile(email)">
          Delete
        </button>
      </div>
      <p v-if="error" style="color: red">Error: {{ error }}</p>
    </div>
  </html>
</template>

<script src='./allProfileScript.js'>
</script>

<style scoped>
#AllProfile {
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