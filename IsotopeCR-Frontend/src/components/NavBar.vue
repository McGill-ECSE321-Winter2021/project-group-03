import 'bulma/css/bulma.css';

<template>


<div>
    <div style="text-align: right;" id="header"><b>Contact Us at <a href="tel:555-555-5555">555-555-5555</a></b></div>
    <b-navbar type="dark" variant="dark">
        <b-navbar-brand href="#"><img class="avatar" src="../assets/repairShopLogo.png" alt>Isotope Car Repair Shop</b-navbar-brand>        
        <b-collapse id="nav-collapse" is-nav>
            <b-navbar-nav>
                <b-nav-item @click="routeTo('')">Home</b-nav-item>
                <b-nav-item @click="routeTo('about')">About</b-nav-item>
            </b-navbar-nav>

            <b-navbar-nav class="ml-auto">
                <b-nav-item v-if="loggedIn" @click="routeTo('appointment')">Appointment</b-nav-item>
                <b-nav-item v-if="!loggedIn" @click="routeTo('login')">Log In</b-nav-item>
                <b-nav-item v-if="!loggedIn" @click="routeTo('signup')">Sign Up</b-nav-item>
                <b-nav-item v-if="loggedIn" @click="logOut()" >Log Out</b-nav-item>
            </b-navbar-nav>
        </b-collapse>
    </b-navbar>
</div>

</template>

<script>
export default {
<<<<<<< HEAD
  name: 'Nav',
  computed: {
    loggedIn() {
        if (this.$cookie.get('email') != null) {
            return true
        }
        return false
    }
  },
  methods: {
    routeTo: function (to) {        
    location.href= 'http://127.0.0.1:8087/' + to;
    },
    logOut: function() {
      this.$cookie.delete('email')
      window.location.href = "/"
    }
  }
}
</script>

<style>
  .avatar {
    height: 2em;
    width: 2em;
    border-radius: 5.0em;
    margin-right: 1em;
    margin-left: 1em;
    margin-bottom: 1px
  }
  #header {
    background-color: rgb(236, 166, 34);
  }
</style>