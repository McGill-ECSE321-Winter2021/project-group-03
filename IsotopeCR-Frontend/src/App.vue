<template>
  <div id="app">
        <NavBar/>
          <script src="https://unpkg.com/vue"></script>
          <script src="https://unpkg.com/vue-cal"></script>
          <link href="https://unpkg.com/vue-cal/dist/vuecal.css" rel="stylesheet">
        <router-view/>        
  </div>
</template>

<script>
import NavBar from './components/NavBar.vue';

export default {
  name: 'app',
  components:{
    NavBar,
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
