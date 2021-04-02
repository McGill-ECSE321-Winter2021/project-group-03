import axios from "axios"
var config = require("../../config")
var frontendUrl = "http://" + config.dev.host + ':' + config.dev.port
var backendUrl = "http://" + config.build.backendHost
var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
})

function ProfileDto(firstName, lastName, email, password, phoneNumber) {
    this.firstName = firstName
    this.lastName = lastName
    this.email = email
}

export default {
    name: 'customerProfile',
    data() {
        return {
            profiles: [],
            firstName: '',
            lastName: '',
            email: '',
            password: '',         
            phoneNumber: '',
            error: ''
        }
    },

    created: function () {
        AXIOS.get('/api/profile/profiles/get-all')
          .then(response => {  
            console.log('response got')          
            this.profiles = response.data
            console.log(response.data)           
          })
          .catch(e => {
            if (e.response) {
                console.log(e.response.data)
                console.log(e.response.status)
            }
            this.error = e
          })
      },

    methods: {
        displayInfo: function (email) {
            AXIOS.get('/api/profile/profiles/get-all')
                .then(response => {                              
                    this.profiles = response.data
                    alert("Vehicles displayed!")
                })
                .catch(e => {
                    if (e.response) {
                        console.log(e.response.data)
                        console.log(e.response.status)
                    }
                    this.error = e
                })
        },

        createAdminAccount: function (email, firstName, lastName, password, isOwner) {
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
                AXIOS.post(backendUrl+ "/api/profile/admin/create", {}, {
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
                            alert("Registration complete!")
                            console.log(response.data)
                            this.error = ''
                        }
                    )
                    .catch(e => {
                        var errorMsg = "Please enter a valid email, name and password"
                        console.log(e)
                        if (e.response) {
                            console.log(e.response.data)
                            console.log(e.response.status)
                            console.log(e.response.message)
                        }
                        this.error = errorMsg;
                    });
            }
        },
    },
}