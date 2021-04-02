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
            appointments: [],
            date: '',
            starttime: '',
            customer: '',
            vehicle: '',
            service: '',
            technician: '',
            errorPastappointmentc: '',
            response: []
        }
    },


    methods: {

        pastappointmentc: function(customeremail){
            if(customeremail == "" ) {
              this.errorMessage = 'Email cannot be empty.'
              return false
            } else {
                
                AXIOS.post(backendUrl+'/api/appointment/futureappointment/customer/{customer}', {},{params:{
                    email: customeremail,
                }})

                .then(response => {
                    // JSON responses are automatically parsed.
                    // this.profile = response.data
                    // this.email = ''
                    // this.password = ''
                    // this.errorMessage = ''
                    console.log(response)

                  })
                  .catch(e => {
                    console.log("hello")
                    console.log(e) 
                });

            }

        }
    }

}
