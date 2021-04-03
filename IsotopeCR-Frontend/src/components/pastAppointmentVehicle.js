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
    name: 'pastappointmentv',
    data() {
        return {
            pastappointments: [],
            date: '',
            starttime: '',
            customer: '',
            vehicle: '',
            service: '',
            technician: '',
            licensePlate:'',
            timeslots: [] ,
            errorMessage: '',
            response: [],
            vehicles: [],
        }
    },

    created: function () {
        AXIOS.get('/api/profile/customer/vehicle/get-all/'+this.$cookie.get('email'))
        .then(response => {                              
            this.vehicles = response.data
        })
        .catch(e => {
            if (e.response) {
                console.log(e.response.data)
                console.log(e.response.status)
            }
            this.errorMessage = e.response.data
        })

      
    },

    methods: {

        pastappointmentv: function(licenseplate){
         
                
                AXIOS.get(backendUrl+'/api/appointment/pastappointment/vehicle/' + this.licensePlate )
                .then(response => {
                   this.pastappointments=response.data

                  })
                  .catch(e => {
                    if (e.response) {
                        console.log(e.response)
                        console.log(e.response.data)
                        console.log(e.response.status)
                      }
                      this.errorPastappointmentv = e.response.data;
                  });
            

        }
    }

}
