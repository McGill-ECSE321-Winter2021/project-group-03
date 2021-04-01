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
    
    created: function() {
        AXIOS.get('/api/autorepairshop/CompanyProfile/get')
            .then(response => {
                // JSON responses are automatically parsed.
                this.companyProfiles.push(response.data)
            })
            .catch(e => {
                this.errorCompanyProfile = e
            })  
    },

    methods: { 
        createCompanyProfile: function (companyName, address, workingHours) {
        // CREATE a CompanyProfile
        AXIOS.post(backendUrl+'/api/autorepairshop/CompanyProfile/create', {}, {
          params: {
            companyName: companyName,   
            address: address,
            workingHours: workingHours
          }
        })
              .then(response => {
                console.log('response got')
                console.log(response)
                // JSON responses are automatically parsed.
                this.companyProfiles.push(response.data)
                this.errorCompanyProfile = ''
                this.newCompanyProfile = ''
              })
              .catch(e => {
                console.log('Ahoh! Error got')
                var errorMsg = e.message
                console.log(errorMsg)
                this.errorPerson = errorMsg
              });
        },

        deleteCompanyProfile: function (companyName, address, workingHours) {
          // CREATE a CompanyProfile
          AXIOS.post(backendUrl+'/api/autorepairshop/CompanyProfile/create', {}, {
            params: {
              companyName: companyName,
              address: address,
              workingHours: workingHours
            }
          })
                .then(response => {
                  console.log('responsed got')
                  console.log(response)
                  // JSON responses are automatically parsed.
                  this.companyProfiles.push(response.data)
                  this.errorCompanyProfile = ''
                  this.newCompanyProfile = ''
                })
                .catch(e => {
                  console.log('Ahoh! Error got')
                  var errorMsg = e.message
                  console.log(errorMsg)
                  this.errorPerson = errorMsg
                });
          }
  
    } // end of methods
  
  } // end of export default