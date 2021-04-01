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
            confirmPassword: "",
            error: "",
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
            } else if (this.phoneNumber == "") {
                this.error = "Please enter a phone number";
            } else if (this.password == "") {
                this.error = "Please enter a password";
            } else if (this.password != this.confirmPassword) {
                this.error = "Your passwords do not match";
            } else {
                console.log("here");
                AXIOS.post(backendUrl+ "/api/profile/customer/create", {}, {
                    params: {
                    email: this.email,
                    firstName: this.firstName,
                    lastName: this.lastName,
                    phoneNumber: this.phoneNumber,
                    password: this.password,
                    }
                })
                    .then(
                        (response) => {
                            console.log("hello");
                            console.log(response.data);
                        },
                        () => {
                            console.log("not");
                        }
                    )
                    .catch((e) => {
                        var errorMsg = "Please enter a valid email, name and password";
                        console.log(e);
                        this.error = errorMsg;
                    });
            }
        },
    },
}