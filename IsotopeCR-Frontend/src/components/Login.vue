<template>
    <div id="login">
        <div class="input_container">
            <h3>Log In</h3>
            <div class="container-fluid">
                <div class="input">
                    <div class="small_text left_text brand_color_text">Email:</div>
                    <input
                    type="text"
                    class="form-control input-style"
                    v-model="email"
                    placeholder="email"
                    />
                    <div id="password" class="small_text left_text brand_color_text">Password:</div>
                    <input
                    type="text"
                    class="form-control input-style"
                    v-model="password"
                    placeholder="password"
                    />
                    <span v-if="errorMessage" style="color:red">{{errorMessage}} </span>
                </div>
            </div>
            <button class="btn btn-primary" @click="login(email,password)">Log In</button>
        </div>
    </div>
</template>

<script src='./loginScript.js'>
export default {
  name: 'Login'
}
</script>

<style scoped>
#login {
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
#password {
  padding-top: 4vh;
}
.container-fluid {
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: auto;
  white-space: nowrap;
}
</style>