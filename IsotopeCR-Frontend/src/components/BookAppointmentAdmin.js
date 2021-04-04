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

function TimeslotDto(time, date) {
    this.time = time;
    this.date = date;
}
export default {
    name: 'bookappointment',
    data() {
        return {
            vehicles: [],
            services: [],
            licensePlate: '',
            startTime: '',
            serviceName: '',
            errorMessage: '',
            response: [],
            selected: null,
            numWeeks: '',
            weekStart: '',
            days: 0,
            disabledNum: 2,
            email: ''
        }
    },



    created: function () {
        if (this.email != '') {
            AXIOS.get('/api/profile/customer/vehicle/get-all/' + this.email)
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
        }
    },
    methods: {
        getUnavailableTimeslots: function () {

            if (this.serviceName == "") {
                this.errorMessage = 'Please select a service'
                return false
            } else if (this.numWeeks == "") {
                this.errorMessage = 'Please enter the number of weeks'
                return false
            } else {
                this.disabledNum = 1;
                let now = new Date();
                now.setDate(now.getDate() + this.numWeeks * 7);

                var day = now.getDay();
                if (day == 6) {
                    now.setDate(now.getDate() + 2);
                }
                day = now.getDay();
                var diff = now.getDate() - day + (day == 0 ? -6 : 1);
                now = new Date(now.setDate(diff));
                let ye = new Intl.DateTimeFormat('en', { year: 'numeric' }).format(now);
                let mo = new Intl.DateTimeFormat('en', { month: 'long' }).format(now);
                let da = new Intl.DateTimeFormat('en', { day: '2-digit' }).format(now);
                this.weekStart = `${mo} ${da} ${ye}`;
                console.log(this.serviceName);
                console.log(this.numWeeks)
                AXIOS.get(backendUrl + '/api/appointment/getUnavailableTimeslots/' + this.serviceName + '?numWeeks=' + this.numWeeks)
                    .then(
                        (response) => {
                            console.log("Timeslots retrieved successfully!")
                            console.log(response.data)
                            this.errorMessage = ''
                        })
                    .catch(e => {
                        console.log("error")
                        if (e.response) {
                            console.log(e.response)
                            console.log(e.response.data)
                            console.log(e.response.status)
                        }
                        this.errorMessage = e.response.data;
                    });

            }

        },
        createAppointment: function (licensePlate, serviceName, startTime) {
            if (licensePlate == "" || serviceName == "" || startTime == "") {
                this.errorMessage = 'Input cannot be empty.'
                return false
            } else {
                let now = new Date();
                now.setDate(now.getDate() + this.numWeeks * 7);

                var day = now.getDay();
                if (day == 6) {
                    now.setDate(now.getDate() + 2);
                }
                day = now.getDay();
                var diff = now.getDate() - day + (day == 0 ? -6 : 1);
                now = new Date(now.setDate(diff));
                now.setDate(now.getDate() + this.days);
                let ye = new Intl.DateTimeFormat('en', { year: 'numeric' }).format(now);
                let mo = new Intl.DateTimeFormat('en', { month: '2-digit' }).format(now);
                let da = new Intl.DateTimeFormat('en', { day: '2-digit' }).format(now);
                var dateCorrect = `${ye}-${mo}-${da}`;
                console.log(this.days);
                console.log(dateCorrect);
                console.log(licensePlate)
                console.log(serviceName)
                console.log(startTime)
                console.log(this.monday)
                AXIOS.post(backendUrl + '/api/appointment/create/' + this.licensePlate + '/' + this.serviceName
                    + '?start=' + startTime + '&date=' + dateCorrect)
                    .then(
                        (response) => {
                            console.log("Appointment booked created successfully!")
                            console.log(response.data)
                            this.errorMessage = ''
                            window.location.href = "/"
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
        selectValue(event, days) {
            this.days = days - 1;
            this.selected = event.target.innerHTML.toString().trim();
            console.log(this.selected + ":00");
            this.startTime = this.selected + ":00";
        },
        isDisabled: function (id) {
            if (id == this.disabledNum) {
                return true;
            }
            return false;
        },
        updateEmail: function (email) {
            this.email = email;
            AXIOS.get('/api/profile/customer/vehicle/get-all/' + this.email)
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
        }
    },
}
