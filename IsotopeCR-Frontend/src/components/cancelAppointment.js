import axios from 'axios'

var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port;
var backendUrl = 'http://' + config.build.backendHost;

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function AppointmentDto(date, starttime, customer, vehicle,service,technician) {
   this.date=date;
   this.starttime=starttime;
   this.customer = customer;
   this.vehicle = vehicle;
   this.service = service;
   this.technician = technician;
}

export default {
    name: 'cancelappointmentc',

	data(){
        return{
		items: [
			{
				id: "id1vaevaa",
				name: "John Doe",
				email: "email@example.com"
			},
			{
				id: "id2",
				name: "Jone Doe",
				email: "email2@example.com"
			}
		],
		selected: [],
		selectAll: false
    }
	},
	methods: {
		select: function() {
			this.selected = [];
			if (!this.selectAll) {
				for (let i in this.items) {
					this.selected.push(this.items[i].id);
				}
			}
		},

        cancelAppointment: function(selected){
            if(selected.length>1){
                alert( 'You can only cancel one appointment at a time.')
              return false
            }

        }
	}

}
