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
            licenseplate:'',
            timeslots: [] ,
            error: '',
            response: []
        }
    },


    methods: {

        pastappointmentv: function(licenseplate){
            if(licenseplate == "" ) {
              this.errorMessage = 'License cannot be empty.'
              return false
            } else {
                
                AXIOS.get(backendUrl+'/api/appointment/pastappointment/vehicle/' + licenseplate )
                .then(response => {
                   this.pastappointments=response.data

                  })
                  .catch(e => {
                    console.log('Ahoh! Error got')
                    var errorMsg = e.message
                    console.log(errorMsg)
                    this.errorPerson = errorMsg
                  });

            }

        }
    }

}
