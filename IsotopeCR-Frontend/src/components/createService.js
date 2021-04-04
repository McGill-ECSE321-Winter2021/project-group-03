import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.build.backendHost

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function ServiceDto(serviceName, duration, price, frequency, resource) {
  this.serviceName = serviceName;
  this.duration = duration;
  this.price = price;
  this.frequency = frequency;
  this.resource = resource;
}

export default {
  name: 'service',
  data() {
    return {
      services: [],
      serviceName: "",
      deletedServiceName: "",
      duration: "",
      price: "",
      frequency: "",
      resource: "",
      resourceType: "",
      errorService: '',
      response: []
    }
  },

  created: function () {
    AXIOS.get('/api/autorepairshop/service/get-all')
      .then(response => {
        // JSON responses are automatically parsed.
        this.services = (response.data) //get-all returns a lit of services
      })
      .catch(e => {
        if (e.response) {
          console.log(e.response)
          console.log(e.response.data)
          console.log(e.response.status)
        }
        this.errorService = e.response.data;
      })
  },

  methods: {
    createService: function (serviceName, duration, price, resourceType, frequency) {
      // CREATE a Service
      AXIOS.post(backendUrl + '/api/autorepairshop/service/create/' + serviceName, {}, {
        params: {
          serviceName: serviceName, //Take this out most likely
          duration: duration,
          price: price,
          frequency: frequency,
          resourceType: resourceType
        }
      })
        .then(response => {
          console.log(response)
          // JSON responses are automatically parsed.
          this.services.push(response.data)
          this.duration = ''
          this.frequency = ''
          this.price = ''
          this.errorService = ''
          this.serviceName = ''
          this.resourceType = ''
          this.deletedServiceName = ''
        })
        .catch(e => {
          if (e.response) {
            console.log(e.response)
            console.log(e.response.data)
            console.log(e.response.status)
          }
          this.errorService = e.response.data;
        });
    },

    deleteService: function () {
      // DELETE a Service
      AXIOS.delete(backendUrl + '/api/autorepairshop/service/delete/' + this.serviceName)
        .then(response => {
          console.log(response)
          // JSON responses are automatically parsed.
          this.deletedServiceName = ''
          this.duration = ''
          this.frequency = ''
          this.price = ''
          this.serviceName = ''
          this.resourceType = ''
          this.services = AXIOS.get('/api/autorepairshop/service/get-all')
            .then(response => {
              // JSON responses are automatically parsed.
              this.services = (response.data) //get-all returns a lit of services
            })
            .catch(e => {
              this.errorService = e.response.data
            })
        })
        .catch(e => {
          if (e.response) {
            console.log(e.response)
            console.log(e.response.data)
            console.log(e.response.status)
          }
          this.errorService = e.response.data;
        });
    }
  },
}