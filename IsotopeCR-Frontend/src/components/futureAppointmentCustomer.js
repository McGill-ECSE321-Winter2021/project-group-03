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


    methods: {
        futureappointmentc: function (customeremail) {
            if (customeremail == "") {
                this.errorMessage = 'Email cannot be empty.'
                return false
            } else {
                AXIOS.get(backendUrl + '/api/appointment/futureappointment/customer/' + customeremail)
                    .then(response => {
                        this.futureappointments = response.data
                        this.errorFutureappointmentc = ''
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
    }

}
