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
    name: 'bookappointment',
    data() {
        return {
            date: '',
            startTime: '',
            license: '',
            serviceName: '',
            errorMessage: '',
            response: []
        }
    },


    methods: {

        createAppointment: function (license, serviceName, startTime, date) {
            if (license == "" || serviceName == "" || startTime == "" || date == "") {
                this.errorMessage = 'Input cannot be empty.'
                return false
            } else {
                AXIOS.post(backendUrl + '/api/appointment/create/' + license + '/' + serviceName, {}, {
                    params: {
                        start: startTime,
                        date: date
                    }
                })
                    .then((response) => {
                        console.log("Appointment booked created successfully!")
                        console.log(response.data)
                        this.errorMessage = ''
                    })
                    .catch(e => {
                        if (e.response) {
                            console.log(e.response)
                            console.log(e.response.data)
                            console.log(e.response.status)
                        }
                        this.errorMessage = e.response.data;
                    });

            }

        }
    }

}
