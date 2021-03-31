import 'bulma/css/bulma.css';

<template>
<div>
    <b-navbar type="dark" variant="dark">
        <b-navbar-brand href="#">Isotope Car Repair Shop</b-navbar-brand>        
        <b-collapse id="nav-collapse" is-nav>
            <b-navbar-nav>
                <b-nav-item @click="routeTo('')">Home</b-nav-item>
                <b-nav-item @click="routeTo('about')">About</b-nav-item>
            </b-navbar-nav>

            <b-navbar-nav class="ml-auto">
                <b-nav-item @click="routeTo('login')">Log In</b-nav-item>
                <b-nav-item @click="routeTo('signup')">Sign Up</b-nav-item>
            </b-navbar-nav>
        </b-collapse>
    </b-navbar>
</div>
</template>
<script>
export default {
    name: 'Nav',
    methods: {
        routeTo: function (to) {
        this.$router.push("./"+to);
    }
  }
}
</script>
<style>
  nav {
    margin-top: 25px;
    margin-bottom: 30px;
    /* a {
      font-weight: bold;
      color: #2c3e50;
      &.router-link-exact-active {
        color: #523c14;
      }
    }   */
  } 
</style>