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
        this.resources = (response.data) //get-all returns a lit of resources
        this.errorResource = ''
      })
      .catch(e => {
        if (e.response) {
          console.log(e.response)
          console.log(e.response.data)
          console.log(e.response.status)
        }
        this.errorResource = e.response.data;
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
          this.resources.push(response.data)
          this.errorResource = ''
          this.resourceType = ''
          this.maxAvailable = ''
          this.deletedResourceType = ''
        })
        .catch(e => {
          console.log('Error!')
          if (e.response) {
            console.log(e.response)
            console.log(e.response.data)
            console.log(e.response.status)
          }
          this.errorResource = e.response.data;
        });
    },

    deleteResource: function () {
      // DELETE a Service
      AXIOS.delete(backendUrl + '/api/autorepairshop/resource/delete/' + this.resourceType)
        .then(response => {
          console.log('Response Got')
          console.log(response)
          this.errorResource = ''
          this.deletedResourceType = ''
          this.resourceType = ''
          this.maxAvailable = ''
          this.resources = AXIOS.get('/api/autorepairshop/resource/get-all')
            .then(response => {
              this.resources = (response.data) //get-all returns a lit of resources
            })
            .catch(e => {
              this.errorResource = e.response.data
            })
        })
        .catch(e => {
          console.log('Error!')
          if (e.response) {
            console.log(e.response)
            console.log(e.response.data)
            console.log(e.response.status)
          }
          this.errorResource = e.response.data;
        });
    }
  },
}