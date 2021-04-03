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
            vehicles: [],
            services: [],
            licensePlate: '',
            date: '2021-05-03',
            startTime: '',
            serviceName: '',
            errorMessage: '',
            response: [],
            selected: null,
            numWeeks: '',
            weekStart: 'Mon Jan 1st 2021',
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
        AXIOS.get('/api/autorepairshop/service/get-all')
            .then(response => {
                // JSON responses are automatically parsed.
                this.services = (response.data) //get-all returns a lit of services
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
        createAppointment: function (licensePlate, serviceName, startTime, date) {
            if (licensePlate == "" || serviceName == "" || startTime == "" || date == "") {
                this.errorMessage = 'Input cannot be empty.'
                return false
            } else {

                AXIOS.post(backendUrl + '/api/appointment/create/' + this.licensePlate + '/' + this.serviceName, {}, {
                    params: {
                        start: startTime,
                        date: date
                    }
                })

                    .then(
                        (response) => {
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
                    });

            }

        },
        getUnavailableTimeslots: function (serviceName, numWeeks) {
            console.log("called")
            if (serviceName == "") {
                this.errorMessage = 'Please select a service'
                return false
            } else if (numWeeks == "") {
                this.errorMessage = 'Please enter the number of weeks'
                return false
            } else {
                AXIOS.get(backendUrl + '/api/appointment/getUnavailableTimeslots/service/' + this.serviceName, {}, {
                    params: {
                        numWeeks: this.numWeeks
                    }
                })
                    .then(
                        (response) => {
                            console.log("Timeslots retrieved successfully!")
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

        },
        selectValue(event) {
            this.selected = event.target.innerHTML.toString().trim();
            console.log(this.selected + ":00");
            this.startTime = this.selected + ":00";
        },
        isDisabled: function (id) {
            if (id == '1') {
                return true;
            }
            return false;
        },


    }

}
