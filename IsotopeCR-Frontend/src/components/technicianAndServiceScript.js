import axios from "axios"
var config = require("../../config")
var frontendUrl = "http://" + config.dev.host + ':' + config.dev.port
var backendUrl = "http://" + config.build.backendHost
var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { "Access-Control-Allow-Origin": frontendUrl },
})

function ServiceDto(serviceName, duration, price, frequency, resourceName) {
    this.serviceName = serviceName;
    this.duration = duration;
    this.price = price;
    this.frequency = frequency;
    this.resourceName = resourceName;
}

export default {
  name: 'myServices',
  data() {
    return {
      services: [],
      email: '',
      serviceName: '',
      duration: '',
      price: '',
      frequency: '',
      resourceName: '',
      error: ''
    }
  },

  created: function () {
    var type = localStorage.getItem('loggedIn')
    if(type != "Technician"){
      alert("You don't have permission to view all accounts registered!")
      return
    }
    this.email = this.$cookie.get('email')
  },

  methods: {
    displayServices: function (email) {
      if (email == "") {
        alert('Session lost, pls re-login')
        return
      } else {
        AXIOS.get(backendUrl + '/api/profile/technician/service/get-all/' + email)
          .then((response) => {
              console.log("response got!")
              console.log(response.data)
              this.services = response.data
            }
          )
          .catch(e => {
            var errorMsg = "The email is not valid"
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