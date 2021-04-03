import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.build.backendHost

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function ResourceDto(serviceType, maxAvailable) {
    this.serviceType = serviceType;
    this.maxAvailable = maxAvailable;
}

export default {
    name: 'Resource',
    data() {
        return {
            resources: [],
            resourceType: "",
            deletedResourceType: "",
            maxAvailable: "",
            errorResource: '',
            response: []
        }
    },

    created: function () {
        AXIOS.get('/api/autorepairshop/resource/get-all')
          .then(response => {
            // JSON responses are automatically parsed.
            this.resources=(response.data) //get-all returns a lit of resources
          })
          .catch(e => {
            this.errorResource = e
          })
    },

    methods: {
        createResource: function (resourceType, maxAvailable) {
            // CREATE a Resource
            AXIOS.post(backendUrl + '/api/autorepairshop/resource/create/' + resourceType + "/" + maxAvailable, {}, {
              params: {
                resourceType: resourceType,
                maxAvailable: maxAvailable
              }
            })
              .then(response => {
                console.log('Response Got')
                console.log(response)
                // JSON responses are automatically parsed.
                this.resources.push(response.data)
                this.errorResource = ''
                this.resourceType = ''
                this.maxAvailable = ''
                this.deletedResourceType = ''
              })
              .catch(e => {
                console.log('Error!')
                var errorMsg = e.message
                console.log(errorMsg)
                this.errorResource = errorMsg
              });
          },
      
          deleteResource: function (deletedResourceType) {
            // DELETE a Service
            AXIOS.delete(backendUrl + '/api/autorepairshop/resource/delete/' + deletedResourceType, {}, {
              params: {
                deletedResourceType: deletedResourceType,
              }
            })
              .then(response => {
                console.log('Response Got')
                console.log(response)
                // JSON responses are automatically parsed.
                this.errorResource = ''
                this.deletedResourceType = ''
                this.resourceType = ''
                this.maxAvailable = ''
                this.resources = AXIOS.get('/api/autorepairshop/resource/get-all')
                .then(response => {
                  // JSON responses are automatically parsed.
                  this.resources=(response.data) //get-all returns a lit of resources
                })
                .catch(e => {
                  this.errorResource = e
                })
              })
              .catch(e => {
                console.log('Error!')
                if (e.response) {
                  console.log(e.response.data);
                  console.log(e.response.status);
                  console.log(e.response.message);
                }
                this.errorResource = "Error!"
              });
          }
    },
}