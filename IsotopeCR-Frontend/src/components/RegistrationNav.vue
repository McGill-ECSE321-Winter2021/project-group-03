<template>
  <html lang="en">
    <h2>Profile Navigation</h2>
    <div class="inputbox">
    <a href="http://127.0.0.1:8087/createCustomerProfile" class="btn btn-primary">Client Entry</a>
    </div>
    <a href="http://127.0.0.1:8087/createAdminProfile" class="btn btn-primary">Admin Entry</a>
    <div class="inputbox">
    <a href="http://127.0.0.1:8087/createTechProfile" class="btn btn-primary">Technician Entry</a>
    </div>
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