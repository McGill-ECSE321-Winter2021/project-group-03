<template>
    <div id="login" class="container">
        <div class="input_container">
            <div class="small_text left_text brand_color_text">Username:</div>
            <input type="text" class="input wide" v-model='username' placeholder="Username">
            <div class="small_text left_text brand_color_text">Password:</div>
            <input type="password" class="input wide" v-model='password' placeholder="Password">

            <span v-if="errorMessage" style="color:red">{{errorMessage}} </span>
            <button class="button brand_color wide" @click="login(username,password)">Log In</button>
        </div>
    </div>
</template>

<script>
export default {
  name: 'Login'
}
</script>

<style>
</style>