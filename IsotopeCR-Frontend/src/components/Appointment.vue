<template>
    <div id="appointment" class="container">
        <div>
           <input type="button" onclick="location.href='http://127.0.0.1:8087/bookappointment';" value="BookAppointment"/>
        </div>
        <div>
            <input type="button" onclick="location.href='http://127.0.0.1:8087/pastappointment';" value="PastAppointment"/>
        </div>
        <div>
            <input type="button" onclick="location.href='http://127.0.0.1:8087/futureappointment';" value="FutureAppointment"/>
        </div>
        <div>
            <input type="button" onclick="location.href='http://127.0.0.1:8087/cancelappointment';" value="CancelAppointment"/>
        </div>
        <router-view/>
    </div>
</template>

<script>

export default {
    name: 'Appointment',
  
}
</script>

<style>
</style>
