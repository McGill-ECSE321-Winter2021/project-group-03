import axios from "axios"
var config = require("../../config")
var frontendUrl = "http://" + config.dev.host + ':' + config.dev.port
var backendUrl = "http://" + config.build.backendHost
var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
})

function TechDto(firstName, lastName, email) {
    this.firstName = firstName
    this.lastName = lastName
    this.email = email
}

export default {
    name: 'createTechProfile',
    data() {
        return {
            firstName: '',
            lastName: '',
            email: '',
            password: '',
            confirmPassword: '',
            error: '',
        }
    },
    methods: {
        createTechAccount: function (email, firstName, lastName, password) {
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
                console.log("here");
                AXIOS.post(backendUrl+ "/api/profile/technician/create", {}, {
                    params: {
                        email: email,
                        firstName: firstName,
                        lastName: lastName,
                        password: password,
                    }
                })
                    .then(
                        (response) => {
                            console.log("response got!");
                            console.log(response.data);
                            this.error = ''
                        }
                    )
                    .catch(e => {
                        if (e.response) {
                            console.log(e.response)
                            console.log(e.response.data)
                            console.log(e.response.status)
                        }
                        this.error = e.response.data;
                    });
            }
        },
    },
}