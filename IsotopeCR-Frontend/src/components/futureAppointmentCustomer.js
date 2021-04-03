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
    name: 'futureappointmentc',
    data() {
        return {
            futureappointments: [],
            id:'',
            date: '',
            starttime: '',
            customer: '',
            vehicle: '',
            service: '',
            technician: '',
            timeslots: [],
            errorFutureappointmentc: '',
            response: []
        }
    },
  created: function(){
                
                AXIOS.get(backendUrl+'/api/appointment/futureappointment/customer/' + this.$cookie.get('email'))

                .then(response => {
                    console.log(response.data)
                   this.futureappointments=response.data

                  })
                  .catch(e => {
                    console.log('Ahoh! Error got')
                    var errorMsg = e.message
                    console.log(errorMsg)
                    this.errorPerson = errorMsg
                  });

            

        },

    methods: {

      
    }

}
