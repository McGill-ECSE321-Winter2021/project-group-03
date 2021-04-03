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
    name: 'customerProfile',
    data() {
        return {
            profiles: [],
            firstName: '',
            lastName: '',
            email: '',
            password: '',         
            phoneNumber: '',
            isOwner: '',
            type: '',
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
   
}