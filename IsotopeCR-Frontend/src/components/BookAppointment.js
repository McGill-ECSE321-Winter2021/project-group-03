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
    name: 'bookappointment',
    data() {
        return {
            vehicles: [],
            services: [],
            licensePlate: '',
            date: '',
            startTime: '',
            serviceName: '',
            errorMessage: '',
            response: [],
            selected: null,
            fields: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'],
          items: [
            { Monday: '9:00', Tuesday: '9:00', Wednesday: '9:00', Thursday: '9:00', Friday: '9:00'},
            { Monday: '9:30', Tuesday: '9:30', Wednesday: '9:30', Thursday: '9:30', Friday: '9:30'},
          ]
        }
    },

    created: function () {
        AXIOS.get('/api/profile/customer/vehicle/get-all/'+this.$cookie.get('email'))
        .then(response => {                              
            this.vehicles = response.data
        })
        .catch(e => {
            if (e.response) {
                console.log(e.response.data)
                console.log(e.response.status)
            }
            this.errorMessage = e.response.data
        }),

        AXIOS.get('/api/autorepairshop/service/get-all')
          .then(response => {
            // JSON responses are automatically parsed.
            this.services=(response.data) //get-all returns a lit of services
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
        createAppointment: function(license,serviceName,startTime,date){
            if(license == "" || serviceName=="" || startTime==""||date=="" ) {
              this.errorMessage = 'Input cannot be empty.'
              return false
            } else {
                
                AXIOS.post(backendUrl+'/api/appointment/create/'+ this.licensePlate + '/' + this.serviceName, {},{
                params:{
                   start:startTime,
                   date:date
                }})

                .then(
                    (response) => {
                        console.log("Appointment booked created successfully!")
                        console.log(response.data)
                        this.error = ''
                    }
                )
                  
            }

        },
        selectValue (event) {
            this.selected = event.target.innerHTML;
        },
        isDisabled: function(id) {
            if(id=='1'){
                return true;
            }
            return false;
        },
        // isPrevDisa
        
    }

}
