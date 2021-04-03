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
			<tr v-for="i in items">
				<td>
					<label class="form-checkbox">
    					<input type="checkbox" :value="i.name+'/'+i.id" v-model="selected">
						<i class="form-icon"></i>
  					</label>
				</td>
				<td>{{i.id}}</td>
				<td>{{i.name}}</td>
				<td>{{i.email}}</td>
                <td>{{i.id}}</td>
				<td>{{i.name}}</td>
				<td>{{i.email}}</td>
                
			</tr>
		</tbody>
	</table>
    <div>
            <button class="button brand_color wide" @click="cancelAppointment(selected)">Cancel An Appointment</button>
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