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
      error: '',
      errorAdd: ''
    }
  },

  created: function () {
    var type = localStorage.getItem('loggedIn')
    console.log(type)
    if (type != "Technician") {
      alert("You don't have permission to view services! You need to sign in as a technician")
      return
    }
    this.email = this.$cookie.get('email')
    AXIOS.get(backendUrl + '/api/profile/technician/service/get-all/' + this.email)
      .then((response) => {
        console.log("response got!")
        console.log(response.data)
        this.services = response.data
        if (response.data.legnth == 0) {
          this.error = 'no services found'
        } else {
          this.error = ''
        }
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
  },

  methods: {
    addService: function (email, serviceName) {
      if (email == "") {
        alert('Session lost, pls re-login')
        return
      } else {
        AXIOS.put(backendUrl + '/api/profile/technician/service/add/' + email, {}, {
          params: {
            serviceName: serviceName
          }
        })
          .then((response) => {
            console.log("response got!")
            console.log(response.data)
            this.services.push(response.data)
            this.errorAdd = ''
          }
          )
          .catch(e => {
            var errorMsg = "The serviceName is not valid"
            if (e.response) {
              console.log(e.response.data)
              console.log(e.response.status)
            }
            this.errorAdd = errorMsg
          });
      }
    },
  }

}