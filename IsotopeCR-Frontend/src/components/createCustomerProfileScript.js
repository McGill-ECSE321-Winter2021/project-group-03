import axios from "axios";
var config = require("../../config");
var frontendUrl = "http://" + config.dev.host + ':' + config.dev.port;
var backendUrl = "http://" + config.build.backendHost;
var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { "Access-Control-Allow-Origin": frontendUrl },
});

function CustomerDto(firstName, lastName, email, phoneNumber) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
}

export default {
    name: 'createcustomerprofile',
    data() {
        return {
            firstName: "",
            lastName: "",
            email: "",
            phoneNumber: "",
            password: "",
            error: "",
            confirmPassword: ""
        }
    },
    methods: {
        createAc: function () {
            if (this.firstName == "") {
                this.error = "Please enter your first name";
            } else if (this.lastName == "") {
                this.error = "Please enter your last name";
            } else if (this.email == "") {
                this.error = "Please enter your email";
            } else if (this.password != this.confirmPassword) {
                this.error = "Your passwords do not match";
            } else {
                AXIOS.post(backendUrl + "/api/profile/customer/create", {}, {
                    params: {
                        email: this.email,
                        firstName: this.firstName,
                        lastName: this.lastName,
                        phoneNumber: this.phoneNumber,
                        password: this.password,
                    }
                })
                    .then((response) => {
                        console.log(response.data);
                        this.error = '';
                        window.location.href = "/"
                    }
                    )
                    .catch((e) => {
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