<template>
<nav class="navbar container" role="navigation" aria-label="main navigation">
  <div class="navbar-brand">
    <a class="navbar-item" href="/">
      <strong class="is-size-4">Isotope Car Repair Shop</strong>
    </a>
  </div>
  <div class="nav">
    <ul class="navbar-start">
      <router-link to="/" class="navbar-item">Home</router-link>
      <router-link to="/about" class="navbar-item">About</router-link>
    </ul>
    <div class="navbar-end">
      <div class="navbar-item">
        <div class="buttons">
          <a class="button is-dark">
            <a @click="routeTo('login')" class="button is-dark"><strong>Login</strong></a>
          </a>
        </div>
      </div>
    </div>
  </div>
</nav>
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