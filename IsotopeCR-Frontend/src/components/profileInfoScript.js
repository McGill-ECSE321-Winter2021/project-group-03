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
            myType: '',
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

    created: function(){
        console.log(this.$cookie.get('email'))
        this.email = this.$cookie.get('email')
        console.log(localStorage.getItem('loggedIn'))
        this.myType = localStorage.getItem('loggedIn')
    },

    methods: {
        displayProfile: function (email) {
            if (email == "") {
                this.error = "Session passed, please relogin";
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
                        if (e.response) {
                            console.log(e.response)
                            console.log(e.response.data)
                            console.log(e.response.status)
                          }
                          this.error = e.response.data;
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
                        if (e.response) {
                            console.log(e.response.data)
                            console.log(e.response.status)
                        }
                        this.editError = e.response.data
                    });
            }
        },

        editPassword: function (newPassword) {
            if (newPassword == "") {
                this.editError = "Please enter your newIsOwner Value";
            } else if(this.email != this.$cookie.get('email')) {
                console.log(this.$cookie.get('email'))
                alert("Current login credential does not match the input email")
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
                        if (e.response) {
                            console.log(e.response.data)
                            console.log(e.response.status)
                        }
                        this.editError = e.response.data
                    });
            }
        },
    }
}