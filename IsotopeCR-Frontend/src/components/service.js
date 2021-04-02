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
            duration: "",
            price: "",
            frequency: "",
            resource: "",
            errorService: '',
            response: []
        }
    },

    created: function () {
        AXIOS.get('/api/autorepairshop//service/get-all')
          .then(response => {
            // JSON responses are automatically parsed.
            this.services = (response.data) //get-all returns a lit of services
          })
          .catch(e => {
            this.errorService = e
          })
    },

    methods: {
        createService: function (serviceName, duration, price, frequency, resource) {
            // CREATE a Service
            AXIOS.post(backendUrl + '/api/autorepairshop/service/create', {}, {
              params: {
                serviceName: serviceName,
                duration: duration,
                price: price,
                frequency: frequency,
                resource: resource
              }
            })
              .then(response => {
                console.log('Response Got')
                console.log(response)
                // JSON responses are automatically parsed.
                this.services.push(response.data)
                this.errorService = ''
                this.serviceName = ''
              })
              .catch(e => {
                console.log('Error!')
                var errorMsg = e.message
                console.log(errorMsg)
                this.errorService = errorMsg
              });
          },
      
          deleteService: function () {
            // DELETE a Service
            AXIOS.delete(backendUrl + '/api/autorepairshop/service/delete')
              .then(response => {
                console.log('Response Got')
                console.log(response)
                // JSON responses are automatically parsed.
                this.services = []
                this.serviceName = ''
              })
              .catch(e => {
                console.log('Error!')
                if (e.response) {
                  console.log(e.response.data);
                  console.log(e.response.status);
                  console.log(e.response.message);
                }
                this.errorService = errorMsg
              });
          }
    },
}