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
    name: 'futureappointmentv',
    data() {
        return {
            futureappointments: [],
            date: '',
            starttime: '',
            customer: '',
            vehicle: '',
            service: '',
            technician: '',
            timeslots: [],
            licensePlate: '',
            response: [],
            vehicles: [],
            errorMessage: ''
        }
    },


    created: function () {
        AXIOS.get('/api/profile/customer/vehicle/get-all/' + this.$cookie.get('email'))
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
        futureappointmentv: function () {

            console.log(this.licensePlate)
            AXIOS.get(backendUrl + '/api/appointment/futureappointment/vehicle/' + this.licensePlate)
                .then(response => {
                    this.futureappointments = response.data
                })
                .catch(e => {
                    if (e.response) {
                        console.log(e.response)
                        console.log(e.response.data)
                        console.log(e.response.status)
                    }
                    this.error = e.response.data;
                });

        }
    }
}
