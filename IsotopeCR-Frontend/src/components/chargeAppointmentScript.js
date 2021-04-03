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
            invoice: '',
            customeremail: '',
            cost: '',
            isPaid: '',
            invoiceID: '',   
            errorPastappointmentc: '',      
            errorCharge: '',
        }
    },
    methods: {
        pastappointmentc: function(customeremail){
            if(customeremail == "" ) {
              this.errorPastappointmentc = 'Email cannot be empty.'
              return false
            } else {  
                AXIOS.get(backendUrl+'/api/appointment/pastappointment/customer/' + customeremail )
                .then(response => {
                   this.pastappointments=response.data
                   this.errorPastappointmentc = ''
                  })
                  .catch(e => {
                    if (e.response) {
                        console.log(e.response)
                        console.log(e.response.data)
                        console.log(e.response.status)
                      }
                      this.errorPastappointmentc = e.response.data;
                  });
            }
        },

        createInvoice: function (email, firstName, lastName, password, isOwner) {
            if (firstName == "") {
                this.error = "Please enter your first name";
            } else if (lastName == "") {
                this.error = "Please enter your last name";
            } else if (email == "") {
                this.error = "Please enter your email";
            } else if (password == "") {
                this.error = "Please enter a password";
            } else if (password != this.confirmPassword) {
                this.error = "Your passwords do not match";
            } else {
                AXIOS.post(backendUrl+ "/api/appointment/createInvoice/", {}, {
                    params: {
                        email: email,
                        firstName: firstName,
                        lastName: lastName,
                        password: password,
                        isOwner: isOwner
                    }
                })
                    .then(
                        (response) => {
                            console.log("response got!")
                            console.log(response.data)
                            this.errorCharge = ''
                        }
                    )
                    .catch(e => {
                        if (e.response) {
                            console.log(e.response)
                            console.log(e.response.data)
                            console.log(e.response.status)
                        }
                        this.errorCharge = e.response.data;
                    });
            }
        },
    },
}