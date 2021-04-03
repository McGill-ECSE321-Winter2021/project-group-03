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
    name: 'pastappointmentc',
    data() {
        return {
            pastappointments: [],
            date: '',
            starttime: '',
            customer: '',
            vehicle: '',
            service: '',
            technician: '',
            customeremail:'',
            errorPastappointmentc: '',
            response: []
        }
    },


    methods: {

        pastappointmentc: function(customeremail){
            if(customeremail == "" ) {
              this.errorPastappointmentc = 'Email cannot be empty.'
              return false
            } else {
                
                AXIOS.get(backendUrl+'/api/appointment/pastappointment/customer/' + customeremail )

                .then(response => {
                   this.pastappointments=response.data
                   this.errorPastappointmentc = ''
                  })
                  .catch(e => {
                    if (e.response) {
                        console.log(e.response)
                        console.log(e.response.data)
                        console.log(e.response.status)
                      }
                      this.errorPastappointmentc = e.response.data;
                  });

            }

        }
    }

}
