import axios from "axios"
var config = require("../../config")
var frontendUrl = "http://" + config.dev.host + ':' + config.dev.port
var backendUrl = "http://" + config.build.backendHost
var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
})

function InvoiceDto(cost, isPaid, invoiceID) {
    this.cost = cost
    this.isPaid = isPaid
    this.invoiceID = invoiceID
}

export default {
    name: 'createAdminProfile',
    data() {
        return {
            pastappointments: [],
            selected: [],
            sessionemail: '',
            invoice: '',
            customeremail: '',
            cost: '',
            isPaid: '',
            invoiceID: '',   
            errorPastappointmentc: '',      
            errorCharge: '',
        }
    },

    created: function(){      
        this.sessionemail = this.$cookie.email
    },

    methods: {
        viewPastAppointment: function(customeremail){      
            AXIOS.get(backendUrl+'/api/appointment/pastappointment/customer/' + customeremail)
            .then(response => {
               this.pastappointments=response.data
               console.log(response.data)
              })
              .catch(e => {
                if (e.response) {
                    console.log(e.response)
                    console.log(e.response.data)
                    console.log(e.response.status)
                  }
                  this.errorPastappointmentc = e.response.data;
              });
        },

        createInvoice: function (selected) {
            if (selected == "") {
                this.error = "Please select an Appointment";
            } else {
                AXIOS.post(backendUrl+ "/api/appointment/createInvoice/"+selected[0])
                    .then(response => {
                            console.log("response got!")
                            console.log(response.data)
                            this.errorCharge = ''
                            AXIOS.get(backendUrl+'/api/appointment/pastappointment/customer/' + this.customeremail)
                                .then(response =>{
                                    this.pastappointments = response.data
                                    this.selected = []
                                })                
                        }
                    )
                    .catch(e => {
                        if (e.response) {
                            console.log(e.response)
                            console.log(e.response)
                            console.log(e.response.status)
                        }
                        this.errorCharge = e.response.data;
                    });
            }
        },
    },
}