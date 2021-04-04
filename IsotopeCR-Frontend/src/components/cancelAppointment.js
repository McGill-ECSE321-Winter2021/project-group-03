import axios from "axios"
var config = require("../../config")
var frontendUrl = "http://" + config.dev.host + ':' + config.dev.port
var backendUrl = "http://" + config.build.backendHost
var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { "Access-Control-Allow-Origin": frontendUrl },
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
    name: 'cancelappointmentc',

    data() {
        return {
            futureappointments: [],
            timeslots: [],
            selected: [],
            selectAll: false,
            error: '',
        }
    },




    created: function () {
        AXIOS.get(backendUrl + '/api/appointment/futureappointment/customer/' + this.$cookie.get('email'))
            .then(response => {
                this.futureappointments = response.data
                console.log(resonse)
            })
            .catch(e => {
                if (e.response) {
                    console.log(e.response)
                    console.log(e.response.data)
                    console.log(e.response.status)
                }
                this.error = e.response
            });



    },

    methods: {
        select: function () {
            this.selected = []
            if (!this.selectAll) {
                for (let i in this.items) {
                    this.selected.push(this.items[i].id)
                }

            }
        },

        cancelAppointment: function (selected) {
            if (selected.length > 1) {
                alert('You can only cancel one appointment at a time.')
                return false
            } else {
                console.log(selected[0])
                AXIOS.put(backendUrl + '/api/appointment/cancelappointment/' + selected[0])
                    .then(response => {
                        AXIOS.get(backendUrl + '/api/appointment/futureappointment/customer/' + this.$cookie.get('email'))
                            .then(response => {
                                this.futureappointments = response.data
                            })
                            this.selected = [];
                    })
                    .catch(e => {
                        if (e.response) {
                            console.log(e.response)
                            console.log(e.response.data)
                            console.log(e.response.status)
                        }
                        this.error = e.response.data
                    });
            }
        }

    }
}