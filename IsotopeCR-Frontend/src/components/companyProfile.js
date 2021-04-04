import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.build.backendHost

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})


function CompanyProfileDTO(companyName, address, workingHours) {
  this.companyName = companyName
  this.address = address
  this.workingHours = workingHours
}


export default {
  name: 'companyProfile',
  data() {
    return {
      companyProfiles: [],
      newCompanyProfile: '',
      newAddress: '',
      newWorkingHours: '',
      errorCompanyProfile: '',
      response: []
    }
  },

  created: function () {
    AXIOS.get('/api/autorepairshop/CompanyProfile/get')
      .then(response => {
        // JSON responses are automatically parsed.
        this.companyProfiles.push(response.data)
      })
      .catch(e => {
        if (e.response) {
          console.log(e.response)
          console.log(e.response.data)
          console.log(e.response.status)
        }
        this.errorCompanyProfile = e.response.data;
      })
  },

  methods: {
    createCompanyProfile: function (companyName, address, workingHours) {
      if (companyName == '') {
        this.errorCompanyProfile = 'Please enter a companyName'
      } else if (address == '') {
        this.errorCompanyProfile = 'Please enter an address'
      } else if (workingHours == '') {
        this.errorCompanyProfile = 'Please enter a workingHours'
      } else {
        // CREATE a CompanyProfile
        AXIOS.post(backendUrl + '/api/autorepairshop/CompanyProfile/create', {}, {
          params: {
            companyName: companyName,
            address: address,
            workingHours: workingHours
          }
        })
          .then(response => {
            console.log(response)
            this.companyProfiles.push(response.data)
            this.errorCompanyProfile = ''
            this.newCompanyProfile = ''
          })
          .catch(e => {
            if (e.response) {
              console.log(e.response)
              console.log(e.response.data)
              console.log(e.response.status)
            }
            this.errorCompanyProfile = e.response.data;
          });
      }
    },

    deleteCompanyProfile: function () {
      // DELETE a CompanyProfile
      AXIOS.delete(backendUrl + '/api/autorepairshop/CompanyProfile/delete')
        .then(response => {
          console.log(response)
          this.companyProfiles = []
          this.newCompanyProfile = ''
          this.errorCompanyProfile = ''
        })
        .catch(e => {
          if (e.response) {
            console.log(e.response)
            console.log(e.response.data)
            console.log(e.response.status)
          }
          this.errorCompanyProfile = e.response.data;
        });
    }

  } // end of methods

} // end of export default