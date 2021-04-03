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
    name: 'cancelappointmentc',

    data() {
        return {
            futureappointments: [],
            selected: [],
            selectAll: false,
            error: ''
        }
    },

    created: function () {
        AXIOS.get(backendUrl + '/api/appointment/futureappointment/customer/' + this.$cookie.get('email'))
            .then(response => {
                this.futureappointments = response.data
            })
            .catch(e => {
                console.log('Error got')
                if (e.response) {
                    console.log(e.response)
                    console.log(e.response.data)
                    console.log(e.response.status)
                }
                this.error = e.response.data
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
                var str = selected[0]
                var res = str.split("/")
                var appointmentid = res[0].hashCode().hashCode() * res[1].hashCode() * (String.valueOf(res[3]) + String.valueOf(res[4])).hashCode()
                AXIOS.post(backendUrl + '/api/appointment/cancelappointment/' + appointmentid)
                    .then(response => {
                        console.log('appointment cancelled')
                        this.error = ''
                    })
                    .catch(e => {
                        console.log('Error got')
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

}
