<template>
    <div id="cancelappointment">
	<div class="text-bold">appointment selected: {{selected}}</div>
	<table class="table table-striped table-hover">
		<thead>
			<tr>
				<th>
					<label class="form-checkbox">
                         <input type="checkbox">
                         <i class="form-icon"></i>
                     </label>
				</th>
				<th>Date</th>
				<th>StartTime</th>
				<th>Customer</th>
                <th>Vehicle</th>
                <th>Service</th>
                <th>Technician</th>
			</tr>
		</thead>
		<tbody>
			<tr v-for ="aptmt in futureappointments">
         
				<td>
					<label class="form-checkbox">
    					<input type="checkbox" :value="aptmt.appointmentID" v-model="selected">
						<i class="form-icon"></i>
  					</label>
				</td>
				  <td>{{aptmt.timeslots[0].date}}</td>
                  <td>{{aptmt.timeslots[0].time}}</td>
                  <td>{{aptmt.customer.email}}</td>
                  <td>{{aptmt.vehicle.licensePlate}}</td>
                  <td>{{aptmt.service.serviceName}}</td>
                  <td>{{aptmt.technician.firstName + "  " + aptmt.technician.lastName }}</td>
			</tr>
		</tbody>
	</table>
    <div>
            <button class="button brand_color wide" @click="cancelAppointment(selected)">Cancel An Appointment</button>
			 <p v-if="error" style="color: red">{{ error }}</p>
    </div>
</div>
</template>

<script src="./cancelAppointment.js">

export default {
    name: 'CancelAppointment',
    
}
</script>

<style>
div.container{
    display: flex;
}
</style>