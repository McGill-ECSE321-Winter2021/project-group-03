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
  name: 'allProfiles',
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
    var type = localStorage.getItem('loggedIn')
    if(type != "Admin"){
      alert("You don't have permission to view all accounts registered!")
      return
    }
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
    deleteProfile: function (email) {
      if (email == "") {
        this.error = "Please enter your email";
      } else {
        AXIOS.delete(backendUrl + '/api/profile/profiles/delete/' + email)
          .then((response) => {
              console.log("response got!")
              console.log(response.data)
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
    }
  }

}