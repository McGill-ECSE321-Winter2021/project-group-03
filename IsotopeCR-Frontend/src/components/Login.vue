<template>
    <div id="login">
        <div class="input_container">
            <div class="small_text left_text brand_color_text">Email:</div>
            <input type="text" class="input wide" v-model='email' placeholder="Email">
            <div class="small_text left_text brand_color_text">Password:</div>
            <input type="password" class="input wide" v-model='password' placeholder="Password">

            <span v-if="errorMessage" style="color:red">{{errorMessage}} </span>
            <button class="button brand_color wide" @click="login(email,password)">Log In</button>
        </div>
    </div>
</template>

<script src='./loginScript.js'>
export default {
  name: 'Login'
}
</script>

<style>
</style>