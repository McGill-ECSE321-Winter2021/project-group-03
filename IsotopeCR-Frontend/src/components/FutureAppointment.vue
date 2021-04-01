<template>
    <div id="futureappointment" class="container">
        <div>
         
        </div>
    </div>
</template>

<script>

export default {
    name: 'BookAppointment',
    methods: {
        routeTo: function (to) {
        this.$router.push("./"+to);
    }
  }
}
</script>

<style>
</style>