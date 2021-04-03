import axios from "axios"
var config = require("../../config")
var frontendUrl = "http://" + config.dev.host + ':' + config.dev.port
var backendUrl = "http://" + config.build.backendHost
var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { "Access-Control-Allow-Origin": frontendUrl },
})

function ProfileDto(firstName, lastName, email, password, phoneNumber, isOwner, type) {
    this.firstName = firstName
    this.lastName = lastName
    this.email = email,
    this.password = password,
    this.phoneNumber = phoneNumber,
    this.isOwner = isOwner,
    this.type = type
}

export default {
    name: 'profileInfo',
    data() {
        return {
            profile:'',
            firstName: '',
            lastName: '',
            email: '',
            password: '',         
            phoneNumber: '',
            isOwner: '',
            newPhoneNumber: '',
            newPassword: '',
            type: '',
            error: '',
            editError:''
        }
    },

    methods: {
        displayProfile: function (email) {
            if (email == "") {
                this.error = "Please enter your email";
            } else {
                AXIOS.get(backendUrl + '/api/profile/profiles/get/' + email)
                    .then(
                        (response) => {
                            console.log("response got!")
                            console.log(response.data)
                            this.firstName = response.data.firstName
                            this.lastName = response.data.lastName
                            this.email = response.data.email
                            this.phoneNumber = response.data.phoneNumber
                            this.isOwner = response.data.isOwner
                            this.type = response.data.type
                            this.error = ''
                        }
                    )
                    .catch(e => {
                        var errorMsg = "Please enter a valid email"
                        if (e.response) {
                            console.log(e.response.data)
                            console.log(e.response.status)
                        }
                        this.error = errorMsg;
                    });
            }
        },

        editPhoneNumber: function (newPhoneNumber) {
            if (newPhoneNumber == "") {
                this.editError = "Please enter your new phone number";
            } else {
                AXIOS.put(backendUrl + '/api/profile/profiles/edit-phonenumber/', {}, {
                    params: {
                        email : this.email,
                        phoneNumber : newPhoneNumber 
                    }
                })
                    .then(
                        (response) => {
                            console.log("response got!")
                            console.log(response.data)
                            this.phoneNumber = response.data.phoneNumber
                            this.editError = ''                   
                        }
                    )
                    .catch(e => {
                        var errorMsg = "Please enter a valid Phone Number"
                        if (e.response) {
                            console.log(e.response.data)
                            console.log(e.response.status)
                        }
                        this.editError = errorMsg;
                    });
            }
        },

        editPassword: function (newPassword) {
            if (newPassword == "") {
                this.editError = "Please enter your newIsOwner Value";
            } else {
                AXIOS.put(backendUrl + '/api/profile/profiles/edit-password', {}, {
                    params: {
                        email : this.email,
                        password : newPassword 
                    }
                })
                    .then(
                        (response) => {
                            console.log("response got!")
                            console.log(response.data)
                            this.editError = ''                   
                        }
                    )
                    .catch(e => {
                        var errorMsg = "Please enter a valid password"
                        if (e.response) {
                            console.log(e.response.data)
                            console.log(e.response.status)
                        }
                        this.editError = errorMsg;
                    });
            }
        },
    }
}