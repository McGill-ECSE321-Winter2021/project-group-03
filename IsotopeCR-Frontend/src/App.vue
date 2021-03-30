<template>
  <div id="app">
      <NavBar />
        <router-view/>        
  </div>
</template>

<script>
import Login from './components/Login'
import NavBar from './components/NavBar.vue';

export default {
  name: 'app',
  components:{
    Login,
    NavBar
  },

}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
