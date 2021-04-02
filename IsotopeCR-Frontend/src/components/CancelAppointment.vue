<template>
    <div id="cancelappointment" class="container">
        <div>
        <table class= "allappiontments">
        <thead>
        <tr>
        <th scope="col" style="text-align: left; width: 10rem;">Date
        </th>
        <th scope="col" style="text-align: left; width: 10rem;">Start time
        </th>
        <th scope="col" style="text-align: left; width: 10rem;">Customer
        </th>
        <th scope="col" style="text-align: left; width: 10rem;">Vehicle
        </th>
        <th scope="col" style="text-align: left; width: 10rem;">Service
        </th>
        <th scope="col" style="text-align: left; width: 10rem;">Technician
        </th>
        </tr>
        </thead>
        </table>
        </div>
        <button class="button" >Cancel Appointment</button>
    </div>
</template>

<script>

export default {
    name: 'CancelAppointment',
}
</script>

<style>
div.container{
    display: flex;
}
</style>