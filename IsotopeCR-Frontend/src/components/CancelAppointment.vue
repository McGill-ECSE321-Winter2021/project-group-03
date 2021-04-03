<template>
    <div id="cancelappointment">
	<div class="text-uppercase text-bold">appointment selected: {{selected}}</div>
	<table class="table table-striped table-hover">
		<thead>
			<tr>
				<th>
					<label class="form-checkbox">
                         <input type="checkbox" v-model="selectAll" @click="select">
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
    					<input type="checkbox" :value="i.id" v-model="selected">
						<i class="form-icon"></i>
  					</label>
				</td>
				<td>{{items[0].id}}</td>
				<td>{{i.name}}</td>
				<td>{{i.email}}</td>
                <td>{{i.id}}</td>
				<td>{{i.name}}</td>
				<td>{{i.email}}</td>
			</tr>
		</tbody>
	</table>
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