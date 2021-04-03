import axios from 'axios'

var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port;
var backendUrl = 'http://' + config.build.backendHost;

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function AppointmentDto(date, starttime, customer, vehicle, service, technician) {
    this.date = date;
    this.starttime = starttime;
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
            date: '',
            starttime: '',
            customer: '',
            vehicle: '',
            service: '',
            technician: '',
            customeremail: '',
            errorFutureappointmentc: '',
            response: []
        }
    },
    

    created: function(){
            
        AXIOS.get(backendUrl+'/api/appointment/futureappointment/customer/' + this.$cookie.get('email'))

        .then(response => {
           this.futureappointments=response.data


          })
          .catch(e => {
            if (e.response) {
                console.log(e.response)
                console.log(e.response.data)
                console.log(e.response.status)
              }
              this.errorFutureappointmentc = e.response.data;
          });

  


    }

}
