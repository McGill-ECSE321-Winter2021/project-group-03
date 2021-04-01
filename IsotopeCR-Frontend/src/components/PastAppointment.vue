<template>
    <div id="pastappointment" class="container">
        <div>
            <input type="button" onclick="location.href='http://127.0.0.1:8087/appointment';" value="Back"/>
        </div>
        <router-view/>
    </div>
</template>

<script>

export default {
    name: 'PastAppointment',
    methods: {
        routeTo: function (to) {
        this.$router.push("./"+to);
    }
  }
}
</script>

<style>
</style>