<template>
    <div id="futureappointment" class="container">
        <div>
         <div>
           <input type="button" onclick="location.href='http://127.0.0.1:8087/futureappointment/vehicle';" value="View Future Appointment for Vehicle"/>
        </div>
        <div>
            <input type="button" onclick="location.href='http://127.0.0.1:8087/futureappointment/customer';" value="View Future Appointment for Customer"/>
        </div>
        </div>
    </div>
</template>

<script>

export default {
    name: 'FutureAppointment',
}
</script>

<style>
</style>